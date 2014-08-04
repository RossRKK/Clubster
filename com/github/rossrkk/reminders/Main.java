package com.github.rossrkk.reminders;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.github.rossrkk.reminders.activities.Activity;
import com.github.rossrkk.reminders.pupil.Pupil;
import com.github.rossrkk.reminders.util.ConnectionThread;
import com.github.rossrkk.reminders.util.Timer;

public class Main {
	
	public static ArrayList<ConnectionThread> threads;
	
	public static void main(String[] args) throws Exception {
		Activity.loadActivities();
		Pupil.loadPupils();
		
		Timer timer = new Timer();
		Thread i = new Thread(timer);
		i.start();
		
		
		int port = 3000;
		
		ServerSocket ss = new ServerSocket(port);
		threads = new ArrayList<ConnectionThread>();
		
		try {
			System.out.println("Clubster 0.1");
			System.out.println("Listening on port " + port);
			while (true) {
				Socket s = ss.accept();
				System.out.println("Connection established!");
				ConnectionThread thread = new ConnectionThread(s);
				Thread t = new Thread(thread);
				t.start();
				threads.add(thread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}