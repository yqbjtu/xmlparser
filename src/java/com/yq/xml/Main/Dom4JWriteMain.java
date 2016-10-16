package com.yq.xml.Main;

import org.dom4j.DocumentException;


import com.yq.xml.common.Dom4jWriter;

public class Dom4JWriteMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Dom4jWriter demo = new Dom4jWriter();
		try {
            demo.writeXmlDemo01("outputbooks.xml");
        }
        catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
