package biz.our.application;

import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PubSubManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import biz.our.application.data.ChatHistory;
import biz.our.application.data.ConnectionSettings;
import biz.our.application.xmpp.ConnSingl;

public class MyChat extends Activity {

	public int state = 0;
	private XMPPConnection xmppConn;
	//

	private static Handler mHandler;

	private static TextView text;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		text = new TextView(this);
		text.setText("No messages sent so far");
		setContentView(text);

		mHandler = new Handler();

	}

	public void stopChat() {

		if (xmppConn != null) {
			xmppConn.disconnect();
		}
	}

	public boolean chatAvailability() {
		Roster roster = ConnSingl.getConnection().getRoster();
		Presence presence = roster.getPresence(ConnectionSettings.getLogin());
		if (presence.getType() == Presence.Type.available) {
			return true;
		}
		return false;
	}

	public void displayBuddyList() {
		Roster roster = xmppConn.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		StringBuilder builder = new StringBuilder();

		System.out.println("\n\n" + entries.size() + " buddy(ies):");
		for (RosterEntry r : entries) {
			builder.append(r.getUser()).append(" ").append(roster.getPresence(r.getUser())).append(" ").append("\n");
		}
		updateText(builder.toString());
	}

	public static void updateChat() {
		StringBuilder builder = new StringBuilder();
		for (Message msg : ChatHistory.getHistory()) {
			builder.append(msg.getFrom().split("/")[0]).append(": ").append(msg.getBody()).append("\n");
		}
		updateText(builder.toString());
	}

	private static void updateText(final String str) {
		if ((mHandler != null) && (text != null)) {
			mHandler.post(new Runnable() {
				public void run() {
					text.setText(str);
				}
			});
		}
	}

//	@SuppressWarnings("unused")
//	private Node getLeafNode() {
//
//		String pubSubAddress = "pubsub." + xmppConn.getServiceName();
//		PubSubManager mgr = new PubSubManager(xmppConn, pubSubAddress);
//		try {
//			Node node = mgr.getNode("testNode");
//			return node;
//		} catch (Exception e) {
//			updateText(e.getMessage());
//			try {
//				// ConfigureForm form ?= new ConfigureForm(FormType.submit);
//				// form.setAccessModel(AccessModel.open);
//				// form.setDeliverPayloads(true);
//				// form.setNotifyRetract(true);
//				// form.setPersistentItems(true);
//				// form.setPublishModel(PublishModel.open);
//				LeafNode leaf = mgr.createNode("testNode");
//				// leaf.sendConfigurationForm(form);
//				return leaf;
//
//			} catch (Exception e2) {
//				updateText(e2.getMessage());
//			}
//		}
//		return null;
//	}

	// private void displayToast(final String str) {
	// mHandler.post(new Runnable() {
	// public void run() {
	// Toast.makeText(Talk.getContext(), str , Toast.LENGTH_SHORT).show();
	// }
	// });
	// }

}