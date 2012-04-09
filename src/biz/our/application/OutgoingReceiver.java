package biz.our.application;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import biz.our.application.data.ChatHistory;
import biz.our.application.xmpp.ChatSingl;
import biz.our.application.xmpp.MessageFactory;

public class OutgoingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
		Chat newChat = ChatSingl.getChat(); 
		
		try {
			MessageFactory factory = new MessageFactory(); 
			Message msg = factory.getDefaultMessage();
			newChat.sendMessage(msg);
			ChatHistory.add(msg);
			MyChat.updateChat();
			
			displayToast("Message sent.");
		} catch (XMPPException e) {
//			Log.v(TAG, "couldn't send:" + e.toString());
		}

	}
	
	private void displayToast(final String str) {
		Toast.makeText(Talk.getContext().getApplicationContext(), str , Toast.LENGTH_SHORT).show();
}

}
