package me.mitchgordon.hanashi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Multiplexes User interaction.
 * @author Mitchell
 *
 */
public class Switch {
	static List<User> mUsers = new ArrayList<User>();
	
	public static void subscribe(User user) {
		mUsers.add(user);
	}
	
	public static void unsubscribe(User user) {
		mUsers.remove(user);
	}
	
	public static void broadcast(String msg) {
		System.out.println(msg);
		
		for (User u : mUsers)
			u.sendLine(msg);
	}
	
}
