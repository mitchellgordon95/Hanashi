package me.mitchgordon.hanashi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The main class, duh.
 */
public class Main {

	public static void main(String args[]) {
		try	{
			// Listen on 777
			ServerSocket server = new ServerSocket(777);
			
			while(true) {
				Socket socket = server.accept();
				User user;
				try {
					user = new User(socket);
				}
				catch (IOException e) {
					// If we failed to open the user's streams, just keep going.
					continue;
				}
				
				user.hello();
				Switch.subscribe(user);
				Switch.broadcast(user.getName() + "@" + user.getIP() + " joined." + "\n");
				new Thread( () -> { 
					while (true) {
						String msg = user.getMessage();
						if (msg == null) {
							Switch.unsubscribe(user);
							Switch.broadcast(user.getName() + "@" + user.getIP() + " left." + "\n");
							break;
						}
						else
							Switch.broadcast(user.getName() + ": " + msg + "\n");
					}
				}).start();
				
			}
		}
		catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
	}
}
