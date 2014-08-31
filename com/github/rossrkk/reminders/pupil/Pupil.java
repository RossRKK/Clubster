package com.github.rossrkk.reminders.pupil;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.rossrkk.reminders.activities.Activity;
import com.github.rossrkk.reminders.lib.Reference;
import com.github.rossrkk.reminders.util.XMLParser;

public class Pupil {
	
	public static ArrayList<Pupil> pupils;
	
	public String id;
	public String firstName;
	public String lastName;
	public String form;
	public String email;
	public String sms;
	public ArrayList<Activity> activities = new ArrayList<Activity>();
	public int noActs;
	
	public Pupil(String ID) throws Exception {
		id = ID;
		String path = Reference.defPupLoc + id;
		try {
			firstName = XMLParser.read(0, "firstname", path);
			lastName = XMLParser.read(0, "lastname", path);
			form = XMLParser.read(0, "form", path);
			email = XMLParser.read(0, "email", path);
			sms = XMLParser.read(0, "sms", path);
			
			noActs = XMLParser.getNoActs(path);
			System.out.println(noActs);
			if (noActs > 0)
			for (int i = 0; i < noActs; i++) {
				String act = XMLParser.read(i, "activity", path);
				
				for (int j = 0; j < Activity.activities.size(); j++) {
					if (Activity.activities.get(j).name.equals(act)) {
						activities.add(Activity.activities.get(j));
					}
				}
			}
		} catch (FileNotFoundException e){
		}
		
		System.out.println(activities.size());
	}
	
	public void save() {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("pupil");
			doc.appendChild(rootElement);

	 
			// firstname elements
			Element firstnameE = doc.createElement("firstname");
			firstnameE.appendChild(doc.createTextNode(firstName));
			rootElement.appendChild(firstnameE);
	 
			// lastname elements
			Element lastnameE = doc.createElement("lastname");
			lastnameE.appendChild(doc.createTextNode(lastName));
			rootElement.appendChild(lastnameE);
	 
			// form elements
			Element formE = doc.createElement("form");
			formE.appendChild(doc.createTextNode(form));
			rootElement.appendChild(formE);
	 
			// email elements
			Element emailE = doc.createElement("email");
			emailE.appendChild(doc.createTextNode(email));
			rootElement.appendChild(emailE);
			
			// sms elements
			Element smsE = doc.createElement("sms");
			smsE.appendChild(doc.createTextNode(sms));
			rootElement.appendChild(smsE);
			
			//activities elements
			for (int i = 0; i < noActs; i++) {
				Element actE = doc.createElement(activities.get(i).id);
				actE.appendChild(doc.createTextNode(activities.get(i).name));
				rootElement.appendChild(actE);
			}
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			String path = Reference.defPupLoc + id + ".xml";
			StreamResult result = new StreamResult(new File(path));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
	
	public static ArrayList<Pupil> loadPupils() throws Exception {
		pupils = new ArrayList<Pupil>();
		
		File folder = new File(Reference.defPupLoc);
		File[] listOfFiles = folder.listFiles(); 
		
		for (int i = 0; i < listOfFiles.length; i++) {
			pupils.add(new Pupil(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().lastIndexOf("."))));
		}
		
		return pupils;
	}
}
