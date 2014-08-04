package com.github.rossrkk.reminders.messages;

import com.github.rossrkk.reminders.activities.Activity;
import com.github.rossrkk.reminders.pupil.Pupil;

public class MessageBuilder {
	public final static String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	
	public static String weekSum(Pupil pupil) {
		String out = "";
		for (int i = 0; i < 7; i++) {
			out = out + days[i] + ":\n";
			for (int j = 0; j < pupil.activities.size(); j++) {
				Activity act = pupil.activities.get(j);
				if (act.days[i]) {
					out = out + act.name + 
							" from " + act.time.getStart() + 
							" until " + act.time.getEnd() +
							". " + act.desc;
					if (act.reqKit) {
						out = out + ". You will need PE kit.";
					}
				}
				out = out + "\n\n";
			}
		}
		return out;
	}
}
