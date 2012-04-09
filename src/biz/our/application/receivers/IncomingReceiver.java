package biz.our.application.receivers;

import org.jivesoftware.smack.packet.Message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import biz.our.application.MyChat;
import biz.our.application.Talk;
import biz.our.application.cep.CEPCommand;
import biz.our.application.data.ChatHistory;
import biz.our.application.data.PointsToDraw;

import com.google.android.maps.GeoPoint;
import com.google.gson.Gson;

public class IncomingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {

		processIncoming();

	}

	private void processIncoming() {

		// the name of the incoming, without app ID

		Message msg = ChatHistory.getHistory().get(ChatHistory.getHistory().size() - 1);

		Gson gson = new Gson();
		try {
			CEPCommand command = gson.fromJson(msg.getBody(), CEPCommand.class);
			displayToast("Command: " + command.getCommandType());
			if (command.getCommandType().equals(CEPCommand.CommandType.SendLoc)) {
				// reply back with localization
				Intent intent = new Intent(Talk.getContext(), OutgoingReceiver.class);
				intent.setAction("biz.our.application.xmpp.outgoing");
				intent.putExtra("message", "back - request.");
				Talk.getContext().sendBroadcast(intent);
			} else if (command.getCommandType().equals(CEPCommand.CommandType.ShowPointsOnTheMap)) {
				// display marker
				double longitude = Double.valueOf(command.getParameters().get(CEPCommand.ParameterType.GPS2Lon));
				double latitude = Double.valueOf(command.getParameters().get(CEPCommand.ParameterType.GPS1Lat));
				if (command.getParameters().get(CEPCommand.ParameterType.Text) != null) {
					// with line if true 
					PointsToDraw.add(new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000)), true);
				} else {
					// just marker 
					PointsToDraw.add(new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000)), false);
				}
			} else if (command.getCommandType().equals(CEPCommand.CommandType.ShowPopup)) {
				// popup
				displayToast(command.getParameters().get(CEPCommand.ParameterType.Text));
			}
		} catch (Exception e) {
			displayToast("Invaild JSON received");
		}

		MyChat.updateChat();

	}

	private void displayToast(final String str) {
		Toast.makeText(Talk.getContext().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

}
