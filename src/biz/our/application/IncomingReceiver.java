package biz.our.application;

import org.jivesoftware.smack.packet.Message;

import biz.our.application.cep.CEPCommand;
import biz.our.application.data.ChatHistory;

import com.google.gson.Gson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class IncomingReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		processIncoming(); 
		
	}
	
	private void processIncoming() {

		// the name of the incoming, without app ID
		
		Message msg = ChatHistory.getHistory().get(ChatHistory.getHistory().size()-1);
		
		Gson gson = new Gson();
		try {
			CEPCommand command = gson.fromJson(msg.getBody(), CEPCommand.class);
			displayToast("Command: " + command.getCommandType());
		} catch (Exception e) {
			 displayToast("Invaild JSON received");
		}
		
		MyChat.updateChat(); 
		

	}
	
	private void displayToast(final String str) {
				Toast.makeText(Talk.getContext().getApplicationContext(), str , Toast.LENGTH_SHORT).show();
	}
	

}
