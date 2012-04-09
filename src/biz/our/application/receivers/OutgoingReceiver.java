package biz.our.application.receivers;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PubSubManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import biz.our.application.MyChat;
import biz.our.application.Talk;
import biz.our.application.data.ChatHistory;
import biz.our.application.xmpp.ChatSingl;
import biz.our.application.xmpp.ConnSingl;
import biz.our.application.xmpp.MessageFactory;

public class OutgoingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		Chat newChat = ChatSingl.getChat();

		try {
			MessageFactory factory = new MessageFactory();
			Message msg = factory.getDefaultMessage();
			newChat.sendMessage(msg);
			ChatHistory.add(msg);
			MyChat.updateChat();
			displayToast("Message sent");
		} catch (XMPPException e) {
			displayToast("Message not sent");
		}

		// PUBSUB STUFF, GETTING NODE SEEMS TO BE FINALLY WORKING ( PM in CONN SINGL CLASS !!!!! ) 
//		displayToast("kurwa");
//		LeafNode node = (LeafNode) getLeafNode();
//		if (node == null) {
//			betaLog("no node");
//		}
//		try {
//			node.send(new Item("123abc"));
//		} catch (Exception e) {
//			betaLog("Nie poszlo " + e.getMessage());
//			e.printStackTrace();
//		}

	}

	private void displayToast(final String str) {
		Toast.makeText(Talk.getContext().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	private void betaLog(String str) {
		// ChatHistory.add(new Message(str));
		// MyChat.updateChat();
		displayToast(str);
	}

	private Node getLeafNode() {

		XMPPConnection xmppConn = ConnSingl.getConnection();

		ServiceDiscoveryManager man = new ServiceDiscoveryManager(xmppConn);

		String pubSubAddress = "pubsub." + xmppConn.getServiceName();
		PubSubManager mgr = new PubSubManager(xmppConn, pubSubAddress);
		try {
			Node node = mgr.getNode("testNode");
			return node;
		} catch (Exception e) {
			// updateText(e.getMessage());
			betaLog("Node not found" + e.getMessage());
			try {
				// ConfigureForm form ?= new ConfigureForm(FormType.submit);
				// form.setAccessModel(AccessModel.open);
				// form.setDeliverPayloads(true);
				// form.setNotifyRetract(true);
				// form.setPersistentItems(true);
				// form.setPublishModel(PublishModel.open);
				LeafNode leaf = mgr.createNode("testNode");
				// leaf.sendConfigurationForm(form);
				return leaf;

			} catch (Exception e2) {
				betaLog("Couldn't create new node " + e2.getMessage());
			}
		}
		return null;
	}

}
