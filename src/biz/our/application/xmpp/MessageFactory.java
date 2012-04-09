package biz.our.application.xmpp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Message;

import biz.our.application.cep.CEPCommand;
import biz.our.application.cep.CEPCommand.CommandType;
import biz.our.application.cep.CEPCommand.ParameterType;
import biz.our.application.data.ConnectionSettings;

import com.google.gson.Gson;

public class MessageFactory {
	
	private Map<ParameterType, String> params; 

	public MessageFactory() {
		params = new HashMap<ParameterType, String>();
	}

	public Message getDefaultMessage() {

		
		params.put(ParameterType.GPS1Lat, "43.626646");
		params.put(ParameterType.GPS2Lon, "7.04763");
		CEPCommand location = new CEPCommand(ConnectionSettings.getLogin(), "TOKEN", CommandType.SendLoc, new Date(), params);

		Gson gson = new Gson();
		String json = gson.toJson(location);

		Message msg = new Message(ConnectionSettings.getTarget(), Message.Type.chat);
		msg.setFrom(ConnectionSettings.getLogin());
		msg.setBody(json);

		return msg;
	}

}
