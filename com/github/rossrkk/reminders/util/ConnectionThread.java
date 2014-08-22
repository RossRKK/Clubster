package com.github.rossrkk.reminders.util;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.github.rossrkk.reminders.activities.Activity;
import com.github.rossrkk.reminders.messages.Notify;
import com.github.rossrkk.reminders.pupil.Pupil;

public class ConnectionThread implements Runnable {
	private Socket s;
	
	public boolean isPupil;
	
	public String pupName;
	public Pupil pup;
	
	public String actName;
	public Activity act;
	
	public ConnectionThread(Socket socket) {
		this.s = socket;
	}
	
	public void run() {
		String client = s.getInetAddress().toString();
		System.out.println("Connected to " +  client);
		
		try {
			Scanner in = new Scanner(s.getInputStream());
			PrintWriter out;
			out = new PrintWriter(s.getOutputStream(), true);
			
			out.println("go");
			String input;
			
			boolean done = false;
			while (!done) {
				input = in.nextLine();
				input.trim();
				System.out.println(input);
				if (!input.isEmpty())
				if (input.equals("pupil")) {
					isPupil = true;
					done = true;
					out.println("isPupil=true");
				} else if (input.equals("activity")) {
					isPupil = false;
					done = true;
					out.println("isPupil=false");
				}
			}
			System.out.println("isPupil=" + isPupil);
			
			out.println("go");
			done = false;
			while (!done) {
				input = in.nextLine();
				input.trim();
				input.toLowerCase();
				if (!input.isEmpty())
				if (isPupil) {
					pupName = input;
					done = true;
				} else {
					actName = input;
					done = true;
				}
			}
			
			if (isPupil) {
				for (int i = 0; i < Pupil.pupils.size(); i++) {
					if (Pupil.pupils.get(i) != null &&
							(Pupil.pupils.get(i).firstName + " " +
								Pupil.pupils.get(i).lastName).equals(pupName)) {
						pup = Pupil.pupils.get(i);
						break;
					}
				}
				if (pup == null) {
					pup = new Pupil(pupName);
					pup.firstName = actName.split(" ")[0];
					pup.lastName = actName.split(" ")[1];
					Pupil.pupils.add(pup);
				}
			} else {
				for (int i = 0; i < Activity.activities.size(); i++) {
					if (Activity.activities.get(i) != null &&
							Activity.activities.get(i).name.equals(actName)) {
						act = Activity.activities.get(i);
						break;
					}
				}
				if (act == null) {
					act = new Activity(actName);
					act.name = actName;
					Activity.activities.add(act);
				}
			}
			/*
			if (isPupil) {
				out.println(pup.firstName);
				out.println(pup.lastName);
				out.println(pup.form);
				out.println(pup.email);
				out.println(pup.sms);
				
				for (int i = 0; i < pup.activities.size(); i++) {
					out.println(pup.activities.get(i).name);
				}
			} else {
				out.println(act.name);
				out.println(act.desc);
				out.println(act.reqKit);
				out.println(act.time);
				
				for (int i = 0; i < act.days.length; i++) {
					out.println(act.days[i]);
				}
			}
			*/
			
			
			System.out.println("loop about to start");
			while (true) {
				out.println("go");
				input = in.nextLine();
				input.trim();
				input = input.substring(input.lastIndexOf("\n") + 1);
				System.out.println(input);
				if (isPupil) {
					if(!input.isEmpty())
					if(input.startsWith("firstname:")) {
						pup.firstName = input.split(":")[1];
					} else if(input.startsWith("lastname:")) {
						pup.lastName = input.split(":")[1];
					} else if(input.startsWith("firstname:")) {
						pup.firstName = input.split(":")[1];
					} else if(input.startsWith("form:")) {
						pup.form = input.split(":")[1];
					} else if(input.startsWith("email:")) {
						pup.email = input.split(":")[1];
					} else if(input.startsWith("sms:")) {
						pup.sms = input.split(":")[1];
					} else if(input.startsWith("activity:")) {
						String[] args = input.split(":");
						if (args[1].equals("add")) {
							for (int j = 0; j < Activity.activities.size(); j++) {
								if (Activity.activities.get(j).name.equals(args[2])) {
									pup.activities.add(Activity.activities.get(j));
								}
							}
						} else if (args[1].equals("remove")) {
							pup.activities.remove(args[2]);
						}
					} else if(input.equals("getinfo")){
						out.println(pup.firstName);
						out.println(pup.lastName);
						out.println(pup.form);
						out.println(pup.email);
						out.println(pup.sms);
						for (int i = 0; i < pup.activities.size(); i++) {
							out.println(pup.activities.get(i).name);
						}
					} else if (input.equals("save")){
						pup.save();
						out.println("saved");
					} else if (input.equals("exit")){
						s.close();
					}
				} else {
					try {
						if(!input.isEmpty())
							if(input.startsWith("name:")) {
								act.name = input.split(":")[1];
							} else if(input.startsWith("desc:")) {
								act.desc = input.split(":")[1];
							} else if(input.startsWith("reqkit:")) {
								act.reqKit = (input.split(":")[1]).equals("true");
							} else if(input.startsWith("time:")) {
								act.time = new Time(Integer.parseInt(input.split(":")[1]), Integer.parseInt(input.split(":")[2]), Integer.parseInt(input.split(":")[3]), Integer.parseInt(input.split(":")[4]));
							} else if(input.startsWith("days:")) {
								for (int i = 1; i < act.days.length + 1; i ++) {
									if ((input.split(":")[i]).equals("true")) {
										act.days[i] = true;
									} else {
										act.days[i] = false;
									}
								}
							} else if(input.startsWith("notify:")) {
								Notify thread = new Notify(input.split(":")[1], act);
								Thread t = new Thread(thread);
								t.start();
							} else if(input.equals("getinfo")) {
								out.println(act.name);
								out.println(act.desc);
								out.println(act.reqKit);
								out.println(act.time);
								
								for (int i = 0; i < act.days.length; i++) {
									out.println(act.days[i]);
								}
							} else  if (input.equals("save")){
								act.save();
								out.println("saved");
							} else if(input.equals("exit")) {
								in.close();
								s.close();
							} else {
								
							}
					} catch (ArrayIndexOutOfBoundsException e) {
						//swallowed
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}