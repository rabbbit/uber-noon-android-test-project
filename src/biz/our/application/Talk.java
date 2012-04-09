package biz.our.application;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import biz.our.application.data.PointsToDraw;
import biz.our.application.xmpp.ConnSingl;
import biz.our.application.xmpp.ConnectionListener;

import com.google.android.maps.GeoPoint;

public class Talk extends TabActivity {
	
	private static Context context;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		context = this.getApplicationContext();

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, Map.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("Map").setIndicator("Map", res.getDrawable(R.drawable.ic_tab_map)).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, MyChat.class);
		spec = tabHost.newTabSpec("Log").setIndicator("Log", res.getDrawable(R.drawable.ic_tab_mychat)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, Conf.class);
		spec = tabHost.newTabSpec("Conf").setIndicator("Conf", res.getDrawable(R.drawable.ic_tab_mychat)).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
		
		ConnSingl.getConnection();
		
		ConnectionListener listener = new ConnectionListener();
		
		
		double latitude = 43.610, longitude = 7.0503;
		PointsToDraw.add(new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000)));
		
//		this.startActivity(new Intent(this, MyChat.class));
	}
	
	public static Context getContext() { 
		return context;
	}

}