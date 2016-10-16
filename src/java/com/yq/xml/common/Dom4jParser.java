package com.yq.xml.common;



import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Dom4jParser {
	
	public void parseDemo01(String file) {
		SAXReader reader = new SAXReader();
		File xmlFile = new File(file);
		Document doc = null;
		try {
		   doc = reader.read(xmlFile);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();
		StringBuffer rootValue = new StringBuffer()
				.append("attr exportDate:" + root.attribute("exportDate").getValue());
		List attrList = root.attributes();
		int index = 0;
		for(Object obj : attrList) {
			Attribute attr = (Attribute) attrList.get(index);
			index++;
			rootValue.append(",Attr Name=" + attr.getName() + ", Value=" + attr.getValue());
		}
	    
		System.out.println("root " + rootValue );
		
		Element comment = root.element("comments");
		String commentValue = comment.getText();
		System.out.println("comment:[" + commentValue + "]");
		
		Element operator = root.element("operator");
		String operValue = "text:["+ operator.getText() + "], attr role=[" + operator.attributeValue("role") +"]";
		System.out.println("operator " + operValue );
		
		
		Element data = root.element("data");
		List dataList = data.elements("book");
		System.out.println("book size in data element:[" + dataList.size() + "]");
		
		for(Object obj : dataList) {
			if (obj instanceof Element) {
				Element element = (Element)obj;
				Element name = element.element("name");
				Element price = element.element("price");
				Attribute bookidAttr = element.attribute("bookid");
				Attribute markAttr = element.attribute("mark");
				
				StringBuffer bookValue = new StringBuffer();
				bookValue.append("name:["+ name.getText() + "] price:[" + price.getText() + "] ");
			
				bookValue.append("attr bookid:["+ bookidAttr.getText() + "] ");
				
				String markValue = "";				
				if (markAttr != null) {
					markValue = markAttr.getText();
				}
				
				bookValue.append("attr mark:["+ markValue + "] ");
				Element position = element.element("position");
				if (position != null) {
					List propList = position.elements("property");
					for(Object object : propList) {
						Element ele = (Element)object;
						Attribute nameAttr = ele.attribute("name");
						
						bookValue.append("property text:["+ ele.getText() + "] ");
						bookValue.append("property attr name:["+ nameAttr.getText() + "] ");
					}				
				}
				
				System.out.println("book element:" + bookValue);
			}		
		}		
		
	}
}
