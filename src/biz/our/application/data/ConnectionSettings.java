package biz.our.application.data;


public class ConnectionSettings {
	
	private static String server = "192.168.1.105";
	private static String login = "pub2";
	private static String password = "test";
	private static String target = "sub2@localhost";

	private ConnectionSettings() {
	};
	
	public static String getServer () { 
		return server; 
	}
	
	public static void setServer( String server ) { 
		ConnectionSettings.server = server; 
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		ConnectionSettings.login = login;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ConnectionSettings.password = password;
	}

	public static String getTarget() {
		return target;
	}

	public static void setTarget(String target) {
		ConnectionSettings.target = target;
	}
	
}
