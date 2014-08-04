package com.github.rossrkk.reminders.util;

import java.util.Calendar;

import com.github.rossrkk.reminders.messages.Email;
import com.github.rossrkk.reminders.messages.MessageBuilder;
import com.github.rossrkk.reminders.pupil.Pupil;

public class Timer implements Runnable {
	
	public void run() {
		Calendar cal = Calendar.getInstance();
		
		while (true) {
			if (cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.HOUR_OF_DAY) == 13 && cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
				for (int i = 0; i < Pupil.pupils.size(); i++) {
					Email.send("School Activity Reminder", MessageBuilder.weekSum(Pupil.pupils.get(i)), Pupil.pupils.get(i).email, "rossrkk@gmail.com", "photosynthesis");
				}
			}
		}
	}
}
