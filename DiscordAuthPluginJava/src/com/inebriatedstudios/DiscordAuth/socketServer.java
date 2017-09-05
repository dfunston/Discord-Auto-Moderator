package com.inebriatedstudios.DiscordAuth;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



//TODO:
//Create initiator and shutdown functions
//Thread this sucka to keep it listening

//Thread currently closes after first servClient
//Make loop?
//Yes.  Make loop.

//Class extends thread to run concurrently with other processes
//Otherwise, hangs the server on startup
public class socketServer extends Thread {
	//Create needed variables.
	//Server stuff
	private static ServerSocket server;
	private static Socket servClient;
	//Parsing data
	private static InputStream input;
	//Thread
	private static Thread t;
	//Unimplemented for now?
	private static boolean runFlag = true;
	//Server listening port
	private static int listenPort = 1010;
	
	//Threaded process.  Loops when finished
	public void run(){
		//Do something
		while(runFlag){
			try{
				//Wait for servClient connection
				servClient = server.accept();
				
				//Get servClient input
				input = servClient.getInputStream();
				//Parse servClient input
				String inputString = socketServer.inputStreamAsString(input);
				//Do something with it (debug for now)
				JsonParser parse = new JsonParser();
				JsonObject obj = parse.parse(inputString).getAsJsonObject();
				System.out.println(inputString);
				/*switch(obj.get("cmd").toString()){
					
				}*/
				//Drop the servClient
				servClient.close();
			} catch (Exception e){
				//Maybe ignore
				//Todo: Don't ignore, filter
				e.printStackTrace();
			}
		}
	}
	
	public static void startSocket() throws InterruptedException {
		try{
			//Open the server for new connections on port 1010
			server = new ServerSocket(listenPort);
			System.out.println("Websocket Server has started on Localhost.\r\nWaiting for a connection...");
		}catch (Exception e){
			e.printStackTrace();
		}
		//Start the thread
		t = new Thread(new socketServer());
		t.start();
	}
	
	public static void closeSocket(){
		runFlag = false;
		//Maybe try creating a dummy connection to the server?
		
		
		
		//Interrupt the thread if it's still running
		if(t.isAlive()){
			t.interrupt();
		}
		try{
			//Close servClient
			//May just always throw an error.  Deal with it.
			//This is really messy, but everyone seems to agree it's just the best way
			servClient.shutdownInput();
			servClient.close();
		}catch(IOException e){
			System.out.println("No longer accepting connections.  Shutting down with grace.");
		}
		try{
			//Close server
			server.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String inputStreamAsString(InputStream stream) throws IOException{
		//This will read the servClient input and return a string
		//Eventually will parse JSON
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		
		while((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		
		br.close();
		return sb.toString();
	}
}


