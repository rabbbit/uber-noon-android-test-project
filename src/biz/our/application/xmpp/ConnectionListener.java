package biz.our.application.xmpp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.content.Intent;
import biz.our.application.Talk;
import biz.our.application.data.ChatHistory;
import biz.our.application.data.ConnectionSettings;
import biz.our.application.receivers.IncomingReceiver;

public class ConnectionListener {

	private XMPPConnection xmppConn;

	public ConnectionListener() {

		// new Thread(new Runnable() {
		// public void run() {

		xmppConn = ConnSingl.getConnection();

		// Accept only messages from TARGET
		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class), new FromContainsFilter(ConnectionSettings.getTarget()));

		PacketListener myListener = new PacketListener() {
			public void processPacket(Packet packet) {
				if (packet instanceof Message) {
					Message msg = (Message) packet;
					if (msg != null && msg.getBody() != null && msg.getType().equals(Message.Type.chat)) {
						ChatHistory.add(msg);
						Intent intent = new Intent(Talk.getContext(), IncomingReceiver.class);
						intent.setAction("biz.our.application.xmpp.incoming");
						intent.putExtra("message", "Wake up.");
						Talk.getContext().sendBroadcast(intent);
					}
				}
			}
		};
		// Register the listener.
		xmppConn.addPacketListener(myListener, filter);

		// }
		// }).start();
	}
}
