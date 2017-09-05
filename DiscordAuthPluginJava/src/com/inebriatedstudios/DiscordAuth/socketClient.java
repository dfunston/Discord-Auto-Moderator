package com.inebriatedstudios.DiscordAuth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class socketClient implements Runnable{
	//Client stuff
	private  Socket conClient = null;
	private  String host = "localhost";
	private  int sendPort = 1011;
	private String msg;
	
	public socketClient(String msg){
		this.msg = msg;
	}

	@Override
	public void run() {
		try {
			System.out.println("[Connecting to socket...]");
			conClient = new Socket(host, sendPort);
			PrintWriter out = new PrintWriter(conClient.getOutputStream(), true);
			out.println(msg);
			System.out.println("[Data sent.  Disconnecting...]");
			conClient.close();
			conClient = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}