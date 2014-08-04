package com.github.rossrkk.reminders.messages;

import com.github.rossrkk.reminders.activities.Activity;
import com.github.rossrkk.reminders.pupil.Pupil;

public class Notify implements Runnable {

	public String message;
	public Activity act;
	
	public Notify(String message, Activity act) {
		this.message = message;
		this.act = act;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < Pupil.pupils.size(); i ++) {
			for (int j = 0; j < Pupil.pupils.get(i).activities.size(); j++) {
				if (Pupil.pupils.get(i).activities.get(j).id.equals(act.id)) {
					Email.send(act.name, message, Pupil.pupils.get(i).email, "rossrkk", "photosynthesis");
				}
			}
		}
	}

}
