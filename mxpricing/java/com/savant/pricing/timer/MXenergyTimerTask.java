package com.savant.pricing.timer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimerTask;

import org.apache.log4j.Logger;
/**
 * @author jvediyappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public abstract class MXenergyTimerTask extends TimerTask {
	
	private Logger log = Logger.getLogger(MXenergyTimerTask.class);
	protected TimerTaskInfo timerTaskInfo;
	public void run() {
		Calendar rightNow = new GregorianCalendar();
		if (rightNow.get(Calendar.MINUTE) == timerTaskInfo.getMinute())
			if (timerTaskInfo.getFrequency().equalsIgnoreCase("hourly"))
				runTask();
			else if (rightNow.get(Calendar.HOUR_OF_DAY) == timerTaskInfo.getHour()) {
				if (timerTaskInfo.getFrequency().equalsIgnoreCase("daily"))    
					runTask();
				else if (timerTaskInfo.getFrequency().equalsIgnoreCase("weekly")) {
					String[] days = timerTaskInfo.getDays();
					int dow = -999999;

					if (days != null) {
						for (int i = 0; i < days.length; i ++) {							
							if (days[i].equalsIgnoreCase("monday"))
								dow = Calendar.MONDAY;
							else if (days[i].equalsIgnoreCase("tuesday"))
								dow = Calendar.TUESDAY;
							else if (days[i].equalsIgnoreCase("wednesday"))
								dow = Calendar.WEDNESDAY;
							else if (days[i].equalsIgnoreCase("thursday"))
								dow = Calendar.THURSDAY;
							else if (days[i].equalsIgnoreCase("friday"))
								dow = Calendar.FRIDAY;
							else if (days[i].equalsIgnoreCase("saturday"))
								dow = Calendar.SATURDAY;
							else if (days[i].equalsIgnoreCase("sunday"))
								dow = Calendar.SUNDAY;
							if (rightNow.get(Calendar.DAY_OF_WEEK) == dow) {
								runTask();
								break;
							}					
						} 				
					}
				} 
				else if (timerTaskInfo.getFrequency().equalsIgnoreCase("monthly")) {
					String[] days = timerTaskInfo.getDays();
					int day;
					if (days != null) {
						for (int i = 0; i < days.length; i ++) {		
							try {
								day = Integer.parseInt(days[i]);
								if (rightNow.get(Calendar.DATE) == day) {
									runTask();
									break;
								} 						 
							} catch (NumberFormatException e) {
								
							}
						}
					}
				}
			}
	}
	
	public abstract void runTask();
	public TimerTaskInfo getTimerTaskInfo() {
		return timerTaskInfo;
	}
	public void setTimerTaskInfo(TimerTaskInfo timerTaskInfo) {
		this.timerTaskInfo = timerTaskInfo;
	}

	
	
}
