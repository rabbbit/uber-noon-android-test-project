package biz.our.application.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PubSubManager;

import android.widget.Toast;
import biz.our.application.Talk;
import biz.our.application.data.ConnectionSettings;

public class ChatSingl {

	private static Chat chat;

	private ChatSingl() {

	}

	public static Chat getChat() {

		if (chat == null) {
			XMPPConnection xmppConn = ConnSingl.getConnection();
			ChatManager chatmanager = xmppConn.getChatManager();
			chat = chatmanager.createChat(ConnectionSettings.getTarget(), new MessageListener() {
				// THIS CODE NEVER GETS CALLED FOR SOME REASON
				@Override
				public void processMessage(Chat chat, Message message) {
				}
			});
		}

		return chat;
	}

	public static Chat getChatForced() {
		XMPPConnection xmppConn = ConnSingl.getConnection();
		ChatManager chatmanager = xmppConn.getChatManager();
		chat = chatmanager.createChat(ConnectionSettings.getTarget(), new MessageListener() {
			// THIS CODE NEVER GETS CALLED FOR SOME REASON
			@Override
			public void processMessage(Chat chat, Message message) {
			}
		});

		return chat;
	}
	
	@SuppressWarnings("unused")
	private Node getLeafNode() {
		
		XMPPConnection xmppConn = ConnSingl.getConnection();

		String pubSubAddress = "pubsub." + xmppConn.getServiceName();
		PubSubManager mgr = new PubSubManager(xmppConn, pubSubAddress);
		try {
			Node node = mgr.getNode("testNode");
			return node;
		} catch (Exception e) {
//			updateText(e.getMessage());
			Toast.makeText(Talk.getContext().getApplicationContext(),"not founD " + e.getMessage(),Toast.LENGTH_LONG); 
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
				Toast.makeText(Talk.getContext().getApplicationContext(),"Couldn't create new node " + e.getMessage(),Toast.LENGTH_LONG);
			}
		}
		return null;
	}

}
