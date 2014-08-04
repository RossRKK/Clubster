package com.github.rossrkk.reminders.util;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLParser
{
	public double out;
	public static NodeList desNodes;
	
	public static int getNoActs(String path)
	{
		try {
			InputStream is = new FileInputStream(path);

        	Document doc = parseXML(is);
        	desNodes = doc.getElementsByTagName("activity");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desNodes.getLength();
	}
    
    public static String read(int toGet, String tag, String path) throws Exception{

    	InputStream is = new FileInputStream(path);

        Document doc = parseXML(is);
        NodeList descNodes = doc.getElementsByTagName(tag);

        for(int i = 0; i < descNodes.getLength(); i++) {
            if (i == toGet) {
                //System.out.println(content);
                String content = descNodes.item(i).getTextContent();
            	return content;
            }
        }
		return null;
    }

    public static Document parseXML(InputStream stream)
    //throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            try {
				throw ex;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }       
        return doc;
    }
}