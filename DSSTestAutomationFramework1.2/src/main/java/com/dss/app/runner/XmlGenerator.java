package com.dss.app.runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.dss.app.apputilities.GlobalValues;
import com.dss.app.coreutilities.CoreUtility;

/*
 * This class will create a dynamic XML file based on the Jenkin parameter
 */

public class XmlGenerator {

	public static Map marketkey, groupkey, browserkey;

	public static List<XmlSuite> createXML(String JenkinString)
			throws Exception {

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		int jenkinGroupCount = splitStringWithSpace(JenkinString).length;
		XmlSuite[] xmlSuite = new XmlSuite[jenkinGroupCount];
		XMLDataStructure[] objectarray = new XMLDataStructure[jenkinGroupCount];
		String[] jenkinSplitGroup = splitStringWithSpace(JenkinString);
		marketkey = loadMarketKeyResolver("MarketKeywords");
		groupkey = loadMarketKeyResolver("Groups");
		browserkey = loadMarketKeyResolver("Browsers");

		// For iterating through groups
		for (int i = 0; i < jenkinGroupCount; i++) {
			objectarray[i] = new XMLDataStructure();
			String[] CommaSplitSubGroup = splitStringWithComma(jenkinSplitGroup[i]);

			// This will fetch all the market names
			if (CommaSplitSubGroup[0].equalsIgnoreCase("all")) {

				objectarray[i].markets = splitStringWith2Char(marketkey
						.keySet().toString().replaceAll("[^A-Za-z]+", ""));
			}

			else {
				objectarray[i].markets = splitStringWith2Char(CommaSplitSubGroup[0]);
			}

			objectarray[i].enviroment = CommaSplitSubGroup[1];
			objectarray[i].browsers = splitStringWith2Char(CommaSplitSubGroup[2]);
			objectarray[i].platform = CommaSplitSubGroup[3];
			objectarray[i].groups = splitStringWith2Char(CommaSplitSubGroup[4]);

			// Creating <Suite> tag for each Jenkin parameter token
			xmlSuite[i] = new XmlSuite();
			xmlSuite[i].setParallel(ParallelMode.TESTS);
		    xmlSuite[i].setThreadCount(10);
			xmlSuite[i].setName(objectarray[i].enviroment);
			xmlSuite[i].setVerbose(2);
			
			List<String> groups = new ArrayList<String>();
			for (String group : objectarray[i].groups) {
				groups.add(groupkey.get(group).toString());
			}

			xmlSuite[i].setIncludedGroups(groups);
			// Creating <Test> tag for each browser
			for (String browser : objectarray[i].browsers) {

				String browserName = browserkey.get(browser).toString();



				XmlTest[] xmlTest = new XmlTest[objectarray[i].markets.length];

				for (int j = 0; j < objectarray[i].markets.length; j++) {

					String url = createURL(objectarray[i].enviroment,
							objectarray[i].markets[j]);

					xmlTest[j] = new XmlTest(xmlSuite[i]);
					xmlTest[j].setName(objectarray[i].markets[j] + "-"
							+ objectarray[i].enviroment
							+ ": Test execution on " + browserName);
					Map<String, String> setTestParamater = new HashMap<String, String>();
					setTestParamater.put("url", url);
					setTestParamater.put("browser", browserName);
					setTestParamater.put("platform", objectarray[i].platform);
					xmlTest[j].setParameters(setTestParamater);



					List<XmlClass> testclasses = new ArrayList<XmlClass>();
					List<XmlPackage> testPackage = new ArrayList<XmlPackage>();
					testPackage.add(new XmlPackage("com.dss.app.test.login"));
					xmlTest[j].setPackages(testPackage);
					
				}

			}

		}

		for (XmlSuite x : xmlSuite) {
			suites.add(x);
		}
		return suites;

	}

	// -----------XML Generator supporting
	// methods----------------------------------

	public static String[] splitStringWithSpace(String jenkinArray) {
		String[] split = jenkinArray.split("\\s+");
		return split;
	}

	public static String[] splitStringWithComma(String singleJenkinArray) {
		String[] split = singleJenkinArray.split(",");
		return split;
	}

	public static String[] splitStringWith2Char(String str) {
		String[] splitstr = null;
		splitstr = str.split("(?<=\\G..)");
		return splitstr;
	}

	public static String createURL(String env, String market) throws Exception {
		String url = "";
		String marketname = marketkey.get(market).toString();
		switch (env.toUpperCase()) {
		case "STAGE":
			url = "http://nguxbeta:nguxtr!b@ngux." + marketname
					+ ".stage.tribdev.com";
			break;

		case "PROD":
			url = "http://www." + marketname + ".com";
			break;

		default:
			System.out.println("Invalid enviroment");
		}

		return url;
	}

	public static Map<String, String> loadMarketKeyResolver(String sheetname)
			throws Exception {
		Map<String, String> marketkey = new HashMap<String, String>();
		FileInputStream fis = new FileInputStream(new File(GlobalValues.excelPath));

		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetname);
		int rowcount = sheet.getLastRowNum();

		for (int i = 1; i <= rowcount; i++) {
			marketkey.put(sheet.getRow(i).getCell(0).toString(), sheet
					.getRow(i).getCell(1).toString());
		}
		return marketkey;
	}

	public static void createParentSuite(List<String> allRunTimeXML)
			throws IOException {

		XmlSuite parentSuite = new XmlSuite();
		parentSuite.setName("TestNG");
		parentSuite.setThreadCount(1);
		parentSuite.addListener("com.dss.app.failedrerun.AnnotationTransformer");
		parentSuite.setSuiteFiles(allRunTimeXML);
	

		CoreUtility.createPhysicalXMLFileOfSuite(parentSuite, "TestNG.xml");
		
		
	}

	
	
}
