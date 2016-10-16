package com.yq.xml.common;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Dom4jParser {
	
	public void parseDemo01(String file) {
		SAXReader reader = new SAXReader();
		File xmlFile = new File(file);
		Document doc = null;
		try {		    
		   doc = reader.read(xmlFile);
		   doc.setXMLEncoding("UTF-8");
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		

       /* String str = readFile(file);
        try {
            doc = DocumentHelper.parseText(str);
            doc.setXMLEncoding("UTF-8");
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }*/

        Element root = doc.getRootElement();

        StringBuffer rootValue = new StringBuffer()
            .append("attr exportDate:" + root.attribute("exportDate").getValue());
        List attrList = root.attributes();
        int index = 0;
        for (Object obj : attrList) {
            Attribute attr = (Attribute)attrList.get(index);
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
		
		//需要在Eclipse的perference中的General--Workspace-Text file encoding中将编码设置为utf-8,否则中文显示和读取有问题
	    Element cnElement = root.element("项目");
	    String cnValue = "text:["+ cnElement.getText() + "], attr 语言=[" + cnElement.attributeValue("语言") +"]";
	    System.out.println("项目 " + cnValue );
	        
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
	
	private String readFile(String file) {
	    FileInputStream inStream;
        try {
            inStream = new FileInputStream(file);
            String myString = IOUtils.toString(inStream, "UTF-8");
            System.out.println("myString:" + myString);

            InputStreamReader isRead = new InputStreamReader(new FileInputStream(file), "utf-8");
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(isRead);
            line = reader.readLine(); // 读取第一行
            StringBuffer buf = new StringBuffer();
            while (line != null) {
                buf.append(line);
                buf.append("\n");
                line = reader.readLine();
            }
            reader.close();
            isRead.close();

            return myString;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return "";  
	}
}
