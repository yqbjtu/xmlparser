package com.yq.xml.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jWriter {
    public void writeXmlDemo01(String xmlOutputFile) throws DocumentException {
        
        SAXReader reader = new SAXReader();
        File xmlFile = new File(xmlOutputFile);
        if (xmlFile.exists()) {
            System.out.println("File:" + xmlOutputFile + " already exists, it will be overritten.");
        }
        
        Document doc = null;
        doc = DocumentHelper.createDocument();
        
        //the second way to create document
        DocumentFactory docFactory = DocumentFactory.getInstance();
        docFactory.createDocument();
        
        //step 1, we need to create a normal element, the let it be root element
        
        

        Element root = DocumentHelper.createElement("books");
        doc.setRootElement(root);
        root.addComment("这是根节点的注释2");
        root.addAttribute("version", "1.0.2版本");
        DateFormat dateFmt = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date now = new Date();
        root.addAttribute("exportDate", dateFmt.format(now));
        
        Element comment = root.addElement("comments");
        comment.setText("This is for java xml parse 例子");
        comment.addCDATA("你好 & hello");
        
        Element operator = root.addElement("operator");
        operator.setText("root");
        operator.addAttribute("role", "admin");

        Element data = root.addElement("data");
        
        for(int i = 1; i < 5; i++) {
            Element book = data.addElement("book");
            book.addAttribute("bookid", i + "");
            book.addAttribute("mark",  "00" + i + "m");
            
            Element name = book.addElement("name");
            Element price = book.addElement("price");
            if (i == 1) {
                name.setText("Netty in action");
                price.setText("35");
            }
            else {
                name.setText("book-name" + i);
                price.setText(15 + "");
            }    
        }

        Element book4 = (Element)data.elements("book").get(3);
        Attribute attrMark = book4.attribute("mark");
        if (attrMark != null) {
            book4.remove(attrMark);
        }

        Element position = book4.addElement("position");
        position.addAttribute("url", "http://www.jd.com");
        for(int i=1; i < 3 ;i++ ) {
            Element property = position.addElement("property");
            property.addAttribute("name", "prop" + i);
            property.setText("value0" + i);
        }

        //为了将来的xml文件格式清晰，保留缩进，使用prettyprint
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            //if format is not needed, you can omit it. 为了支持中文，我们必须使用utf8和FileOutputStream

            //XMLWriter xmlWriter = new XMLWriter(new FileWriter(fileName), format);
            //---使用fileWriter无法正确保存中文字符因为java中由Writer类继承下来的子类没有提供编码格式处理
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(xmlOutputFile), format);
            xmlWriter.write(doc);
            xmlWriter.close();

            //XMLWriter consoleWriter = new XMLWriter(new OutputStreamWriter(System.out, "UTF-8"), format);
            XMLWriter consoleWriter = new XMLWriter(System.out, format);
            consoleWriter.write(doc);
            consoleWriter.flush();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
        
                
         System.out.println("Xml is written to " + xmlFile.getAbsolutePath() + " successfully.");       
    }

}
