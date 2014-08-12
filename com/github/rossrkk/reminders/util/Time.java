package com.github.rossrkk.reminders.util;

public class Time {
	public int startHr;
	public int endHr;
	
	public int startMin;
	public int endMin;
	
	public Time(int startHr, int startMin,int endHr, int endMin) {
		this.endHr = endHr;
		this.endMin = endMin;
		this.startHr = startHr;
		this.startMin = startMin;
	}
	
	public String getStart() {
		String out = "";
		String min = String.format("%02d", startMin);
		String hr = String.format("%02d",  startHr);
		out = out + hr + ":" + min;
		return out;
	}
	
	public String getEnd() {
		String out = "";
		String min = String.format("%02d", endMin);
		String hr = String.format("%02d", endHr);
		out = out + hr + ":" + min;
		return out;
	}
	
	public Time(String start, String end) {
		String[] stTimes = start.split(":");
		String[] enTimes = end.split(":");
		startHr = Integer.parseInt(stTimes[0]);
		startMin = Integer.parseInt(stTimes[1]);
		endHr = Integer.parseInt(enTimes[0]);
		endMin = Integer.parseInt(enTimes[1]);
	}
	
	public String toString() {
		return String.format("%02d", startHr) + ":" + String.format("%02d", startMin) + "\n" + String.format("%02d", endHr) + ":" + String.format("%02d", endMin);
	}
}
