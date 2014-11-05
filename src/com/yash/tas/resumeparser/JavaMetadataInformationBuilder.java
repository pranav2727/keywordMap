package com.yash.tas.resumeparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class JavaMetadataInformationBuilder {

	private static final String COMMA = ",";
	private static final String SPACE = " ";

	public static void main(String[] args) throws Throwable {

		List<String> metadataList = new ArrayList<String>();
		metadataList.add("java");
		metadataList.add("spring");
		metadataList.add("spring mvc");
		metadataList.add("struts");
		metadataList.add("ajax");
		metadataList.add("jquery");
		metadataList.add("angular js");
		metadataList.add("sql");
		metadataList.add("db2");
		metadataList.add("oracle");
		metadataList.add("visio");
		metadataList.add("maven");
		metadataList.add("tomcat");
		metadataList.add("web sphere");
		metadataList.add("WAS");
		metadataList.add("RAD");
		metadataList.add("jboss");
		metadataList.add("jrun");
		metadataList.add("hibernate");
		metadataList.add("fitness");
		metadataList.add("xml");
		metadataList.add("esb");
		metadataList.add("jpa");
		metadataList.add("data mining");
		metadataList.add("jbi");
		metadataList.add("junit");
		metadataList.add("mockito");
		metadataList.add("rmi");
		metadataList.add("web service");
		metadataList.add("REST");
		metadataList.add("agile");
		metadataList.add("cucumber");

		String fileLoc = ".\\ReadThis\\CVSample.doc";
		displayMetadataMap(metadataList, fileLoc);

		fileLoc = ".\\ReadThis\\rsample.doc";
		displayMetadataMap(metadataList, fileLoc);
	}

	private static void displayMetadataMap(List<String> metadataList, String fileLoc) throws FileNotFoundException, IOException,
			TikaException {
		File file = new File(fileLoc);
		InputStream ipStream = new FileInputStream(file);

		JavaMetadataInformationBuilder handsOn = new JavaMetadataInformationBuilder();
		long timeStart = new Date().getTime();
		Map<String, Integer> metadataMap = handsOn.getMetadataMap(ipStream, metadataList);
		System.out.println("processedTime in MilliSeconds: " + (new Date().getTime() - timeStart));
		System.out.println(metadataMap);
	}

	public Map<String, Integer> getMetadataMap(InputStream ipStream, List<String> metadataList) throws IOException, TikaException {
		String text = getTextFromFile(ipStream);
		text = text.toLowerCase();

		Map<String, Integer> metadataMap = processTextForMetadata(metadataList, text);
		return metadataMap;
	}

	private Map<String, Integer> processTextForMetadata(List<String> metadataList, String text) {

		Map<String, Integer> metadataMap = new HashMap<String, Integer>();

		for (String metadata : metadataList) {
			String lowerCaseMetadata = metadata.toLowerCase();

			String metadataWithDelimiter = SPACE + lowerCaseMetadata + SPACE;
			int weight = getMetadataWeight(text, metadataWithDelimiter);

			metadataWithDelimiter = SPACE + lowerCaseMetadata + COMMA;
			weight = weight + getMetadataWeight(text, metadataWithDelimiter);

			metadataWithDelimiter = COMMA + lowerCaseMetadata + COMMA;
			weight = weight + getMetadataWeight(text, metadataWithDelimiter);

			metadataMap.put(metadata, weight);
		}

		return metadataMap;
	}

	private int getMetadataWeight(String text, String lowerCaseMetadata) {
		int weight = -1;
		int indexOf = 0;

		while (indexOf != -1) {
			weight++;
			int metadataLength = lowerCaseMetadata.length();
			indexOf = text.indexOf(lowerCaseMetadata, indexOf + metadataLength);
		}
		return weight;
	}

	private String getTextFromFile(InputStream ipStream) throws IOException, TikaException {
		Tika tika = new Tika();
		String text = tika.parseToString(ipStream);
		return text;
	}

}
