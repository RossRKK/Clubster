package com.github.rossrkk.reminders.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.rossrkk.reminders.lib.Reference;
import com.github.rossrkk.reminders.util.Time;
import com.github.rossrkk.reminders.util.XMLParser;

public class Activity {
	public String id;
	public String name;
	public boolean[] days;
	public Time time;
	public String desc;
	public boolean reqKit;
	
	public static ArrayList<Activity> activities;
	
	public Activity(String ID) throws Exception {
		String[] week = new String[]{"mon", "tue", "wed", "thur", "fri", "sat", "sun"};
		days = new boolean[7];
		id = ID;
		try {
			String path = Reference.defActLoc + id;
			name = XMLParser.read(0, "name", path);
			time = new Time(XMLParser.read(0, "time_strt", path), XMLParser.read(0, "time_end", path));
			desc = XMLParser.read(0, "desc", path);
			if (desc == null) {
				desc = "";
			}
			reqKit = XMLParser.read(0, "reqkit", path).equals("true");
			
			for (int i = 0; i < 7; i ++) {
				if (XMLParser.read(0, week[i], path).equals("true")) {
					days[i] = true;
				}
			}
		} catch (FileNotFoundException e){
			name = "";
			time = new Time(0, 0, 0, 0);
			desc =	"";
			reqKit = false;
			for (int i = 0; i < days.length; i++) {
				days[i] = false;
			}
			save();
		}
	}
	
	public void save() {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("activity");
			doc.appendChild(rootElement);

	 
			// name elements
			Element nameE = doc.createElement("name");
			nameE.appendChild(doc.createTextNode(name));
			rootElement.appendChild(nameE);
	 
			// description elements
			Element descE = doc.createElement("desc");
			descE.appendChild(doc.createTextNode(desc));
			rootElement.appendChild(descE);
	 
			// time elements
			Element timeStrtE = doc.createElement("time_strt");
			timeStrtE.appendChild(doc.createTextNode(time.getStart()));
			rootElement.appendChild(timeStrtE);
			
			Element timeEndE = doc.createElement("time_end");
			timeEndE.appendChild(doc.createTextNode(time.getEnd()));
			rootElement.appendChild(timeEndE);
	 
			// kit required elements
			Element kitReqE = doc.createElement("reqkit");
			kitReqE.appendChild(doc.createTextNode(String.valueOf(reqKit)));
			rootElement.appendChild(kitReqE);
			
			//activities elements
			for (int i = 0; i < days.length; i++) {
				String day = "";
				switch (i) {
				case 0: day = "mon";
					break;
				case 1: day = "tue";
					break;
				case 2: day = "wed";
					break;
				case 3: day = "thur";
					break;
				case 4: day = "fri";
					break;
				case 5: day = "sat";
					break;
				case 6: day = "sun";
					break;
				}
				Element daysE = doc.createElement(day);
				daysE.appendChild(doc.createTextNode(String.valueOf(days[i])));
				rootElement.appendChild(daysE);
			}
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			String path = Reference.defActLoc + id;
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
	
	public static ArrayList<Activity> loadActivities() throws Exception {
		activities = new ArrayList<Activity>();
		
		File folder = new File(Reference.defActLoc);
		File[] listOfFiles = folder.listFiles(); 
		
		for (int i = 0; i < listOfFiles.length; i++) {
			activities.add(new Activity(listOfFiles[i].getName()));
		}
		
		return activities;
	}
}
