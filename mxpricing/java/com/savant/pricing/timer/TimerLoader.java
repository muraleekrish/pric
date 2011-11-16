/*
 * Created on Dec 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * @author jvediyappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TimerLoader {
	
	private Logger log = Logger.getLogger(TimerLoader.class);
	private TimerParser parser;
	
	/**
	 * Constructor with repository paths and timer configuration file as 
	 * parameters
	 * @param timerConfig
	 */
	
	public TimerLoader(File timerConfig) {
		parser = new TimerParser(timerConfig);
	}
	
	/**
	 * Initialize the timers by obtaining TimerTasks from TimerParser
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void init() throws FileNotFoundException, IOException, 
	SAXException, ClassNotFoundException,
	InstantiationException,
	IllegalAccessException,ParserConfigurationException {
		parser.init();
		TimerTaskInfo[] tasks = parser.parseTimers();		
		//Initialize each timertask
		for (int i = 0; i < tasks.length; i ++) 
			if (tasks[i] != null)
				initTimer(tasks[i]);		
	}
	
	/**
	 * Initialize the specified task by creating an instance of its class,
	 * scheduling it at its hour, minute, by its frequency
	 * @param task
	 */
	private void initTimer(TimerTaskInfo taskInfo) throws ClassNotFoundException,
	InstantiationException,
	IllegalAccessException {		
		if (taskInfo.isEnabled()) {
			//Locate and instantiate the class to be scheduled
			Class c = Class.forName(taskInfo.getClassName());			
			Object o = c.newInstance();
			MXenergyTimerTask task = (MXenergyTimerTask) o;
			task.setTimerTaskInfo(taskInfo);
			
			//set time
			Calendar calendar = Calendar.getInstance();
			if (!taskInfo.getFrequency().equalsIgnoreCase("hourly"))
				calendar.set(Calendar.HOUR_OF_DAY, taskInfo.getHour());
			calendar.set(Calendar.MINUTE, taskInfo.getMinute());
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime();
			
			//schedule task
			log.debug("Now scheduling " + taskInfo.getClassName() 
					+ " for " + taskInfo.getFrequency());
			long period = 86400*1000; //daily
			if (taskInfo.getFrequency().equalsIgnoreCase("hourly"))
				period = 3600000; //hourly
			new Timer(true).scheduleAtFixedRate(task, time, period);
		}
	}
	
	
}
