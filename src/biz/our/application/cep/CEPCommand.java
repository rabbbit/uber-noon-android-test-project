package biz.our.application.cep;

import java.util.Date;
import java.util.Map;

/**
 * Communication class allowing sending events
 * @author Lukas
 *
 */
public class CEPCommand {
	
	/**
	 * Enum to represent available commands
	 */
	public enum CommandType {
		/**
		 * Send location
		 */
		SendLoc, 
		
		ShowPopup, 
		
		ShowPointsOnTheMap, 
		
		ShowDialog};
	
	/**
	 * Enum to represent parameter types
	 *
	 */
	public enum ParameterType {
		/**
		 * Lattitude (1st coordinate)
		 */
		GPS1Lat, 
		/**
		 * Longitude (2nd coordinate)
		 */
		GPS2Lon,
		
		/**
		 * Regular string for popup, dialog, name of point to be shown on the map (coordinates in GPS#)
		 */
		Text}
	
	public CEPCommand(){}
	public CEPCommand( String userID, String token, CommandType commandType, Date time, Map<ParameterType,String> parameters)
	{
		this.setUserID(userID);
		this.setToken(token);
		this.setCommandType(commandType);
		this.setTime(time);
		this.setParameters(parameters);		
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}
	public CommandType getCommandType() {
		return commandType;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public Date getTime() {
		return time;
	}

	public void setParameters(Map<ParameterType,String> parameters) {
		this.parameters = parameters;
	}
	public Map<ParameterType,String> getParameters() {
		return parameters;
	}

	/**
	 * User ID
	 */
	private String userID;
	
	/**
	 * User token (for security)
	 */
	private String token;
	/**
	 * Command type
	 */
	private CommandType commandType;
	
	/**
	 * Date of sending event
	 */
	private Date time;
	
	/**
	 * List of parameters in format "name;parameter" ex. "gpsloc;43.626646,7.04763"
	 */
	private Map<ParameterType,String> parameters;

}
