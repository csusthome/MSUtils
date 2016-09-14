package com.ms.utils.sax.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ms.utils.handler.XMLHandler;

public class SaxService {

	public SaxService() {
		// TODO Auto-generated constructor stub
	}

	public static List<Map<String, String>> readXML(InputStream inputStream,
			String nodeName) {
		try {
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();// 解析xml
			XMLHandler handler = new XMLHandler(nodeName);
			parser.parse(inputStream, handler);
			inputStream.close();
			return handler.getList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
