/*
 * Created on Dec 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.timer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author jvediyappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TimerParser {
	
	private Document document;
	private TimerTaskInfo[] tasks;
	private File timerConfig;
	
	public TimerParser(File timerConfig) {
		this.timerConfig = timerConfig;
	}
	
	public void init() throws FileNotFoundException, IOException, SAXException,ParserConfigurationException {
		FileInputStream in = new FileInputStream(timerConfig);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.parse(in);
	}
	
	public TimerTaskInfo[] parseTimers() {
		
		NodeList timers = document.getElementsByTagName("timers")
										.item(0).getChildNodes();
		Node node;
		
		//init timertasks	
		tasks = new TimerTaskInfo[timers.getLength()];
		//for each timer node, build Timer
		for (int i = 0; i < timers.getLength(); i ++) {
			node = timers.item(i);
			if (node.getNodeName().equalsIgnoreCase("timer"))
				tasks[i] = processTimer(node);
			
		}
		return tasks;
	}
	
	/**
	 * Populate a TimerTask with information from the specified
	 * NodeList
	 * @param list
	 * @return
	 */
	private TimerTaskInfo processTimer(Node node) {
		TimerTaskInfo task = new TimerTaskInfo();
		
		String nodeName;
		NodeList time, list, days;
		String[] dayArr;
		//Check if enabled		
		task.setEnabled("true".equalsIgnoreCase(node.getAttributes().
				getNamedItem("enabled").getNodeValue().trim()));
			
		list = node.getChildNodes();
		//for each element, add that element to a TimerTask
		for (int j = 0; j < list.getLength(); j ++) {
			node = list.item(j);
			nodeName = node.getNodeName();			
			//Set class
			if (nodeName.equalsIgnoreCase("class"))
				task.setClassName(node.getFirstChild().getNodeValue());
				
			//Set time info
			else if (nodeName.equalsIgnoreCase("time")) {
				time = node.getChildNodes();
				//Pull out each time element
				for (int l = 0; l < time.getLength(); l ++) {
					node = time.item(l);
					nodeName = node.getNodeName();
					//Set hour
					if (nodeName.equalsIgnoreCase("hour"))
						task.setHour(Integer.parseInt(
								node.getFirstChild().getNodeValue()));
					//Set minute
					else if (nodeName.equalsIgnoreCase("minute"))
						task.setMinute(Integer.parseInt(
								node.getFirstChild().getNodeValue()));
					//Set frequency
					else if (nodeName.equalsIgnoreCase("frequency"))
						task.setFrequency(node.getFirstChild().getNodeValue());
					
					else if (nodeName.equalsIgnoreCase("days")) {
						days = node.getChildNodes();
						//for each day, add to array
						dayArr = new String[days.getLength()];
						for (int k = 0; k < days.getLength(); k ++) {
							dayArr[k] = 
								days.item(k).getFirstChild().getNodeValue();
						}
						task.setDays(dayArr);
					}
				}
			}
				
		}
		return task;
	}
}
