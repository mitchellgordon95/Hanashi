package me.mitchgordon.hanashi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Model for a user. Manages all interaction with the socket. 
 */
public class User {

	String mName;
	Socket mSocket;
	
	PrintStream mOut;
	BufferedReader mIn;
	
	public User(Socket mSocket) throws IOException {
		this.mSocket = mSocket;
		
		mOut = new PrintStream(mSocket.getOutputStream());
		mIn = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
	}
	
	/** Gets the user's name. */
	public String getName() {
		return mName;
	}
	
	/** Gets the user's IP. */
	public String getIP() {
		return mSocket.getInetAddress().toString();
	}
	
	/** Initializes interaction with the user */
	public void hello() {
		try {
			sendLine("Who are you?");
			
			mName = mIn.readLine();
			
			sendLine(Options.WELCOME_MESSAGE);
		}
		catch (IOException e) {
			close();
		}
	}
	
	/** Sends a message to the user */
	public void sendLine(String msg) {
		mOut.println("\r" + msg);
		mOut.flush();
	}
	
	/** Sends a message without the new line */
	public void send(String msg) {
		mOut.println(msg);
		mOut.flush();
	}
	
	/** Gets a message from the user, or blocks until one is available. 
	 *  Returns null on failure. */
	public String getMessage() {
		try {
			return mIn.readLine();
		}
		catch (IOException e) {
			close();
			return null;
		}
	}
	
	/** Clean up */
	private void close() {
		try {
			mSocket.close();
		}
		catch (IOException e) {
			// What could we even do?
		}
	}
}
