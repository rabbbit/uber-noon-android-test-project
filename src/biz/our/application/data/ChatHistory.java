package biz.our.application.data;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.Message;

public class ChatHistory {

	private static List<Message> history;

	private ChatHistory() {
	}

	public static List<Message> getHistory() {

		if (history == null) {
			history = new ArrayList<Message>();
		}

		return history;
	}

	public static void add(Message msg) {

		if (history == null) {
			history = new ArrayList<Message>();
		}
		
		history.add(msg);
	}

}
