package biz.our.application.xmpp;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.pubsub.packet.PubSubNamespace;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionProvider;

import biz.our.application.data.ConnectionSettings;

public class ConnSingl {

	private static XMPPConnection xmppConn;

	private ConnSingl() {
	};

	public static XMPPConnection getConnection() {

		if (xmppConn == null) {

			SASLAuthentication.supportSASLMechanism("PLAIN");
	        
			
			registerProviders(); 

			// NetworkInfo active =
			// ((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
			// if (active==null || !active.isAvailable()) {
			// // Log.e("connection request, but no network available");
			// Toast.makeText(this, "Waiting for network to become available.",
			// Toast.LENGTH_SHORT).show();
			// }

			if (xmppConn == null) {
				xmppConn = new XMPPConnection(ConnectionSettings.getServer());
				try {
					xmppConn.connect();
					xmppConn.login(ConnectionSettings.getLogin(), ConnectionSettings.getPassword());
					xmppConn.sendPacket(new Presence(Presence.Type.available));
				} catch (XMPPException e) {
					// xmppe.printStackTrace();
					xmppConn.disconnect();
					// Log.e(LOG_TAG, "xmpp login failed: " + e);
					if (e.getMessage().indexOf("SASL authentication") == -1) {
						// doesn't look like a bad username/password, so retry
						// Toast.makeText(this, "Login failed",
						// Toast.LENGTH_SHORT).show();
						// maybeStartReconnect();
					} else {
						// Toast.makeText(this, "Invalid username or password",
						// Toast.LENGTH_SHORT).show();
						// onDestroy();
					}
					return null;
				}
			}

		}
		return xmppConn;

	}

	public static XMPPConnection getConnectionForced() {
		xmppConn = new XMPPConnection(ConnectionSettings.getServer());
		try {
			xmppConn.connect();
			xmppConn.login(ConnectionSettings.getLogin(), ConnectionSettings.getPassword());
			xmppConn.sendPacket(new Presence(Presence.Type.available));
		} catch (XMPPException e) {
			// xmppe.printStackTrace();
			xmppConn.disconnect();
			// Log.e(LOG_TAG, "xmpp login failed: " + e);
			if (e.getMessage().indexOf("SASL authentication") == -1) {
				// doesn't look like a bad username/password, so retry
				// Toast.makeText(this, "Login failed",
				// Toast.LENGTH_SHORT).show();
				// maybeStartReconnect();
			} else {
				// Toast.makeText(this, "Invalid username or password",
				// Toast.LENGTH_SHORT).show();
				// onDestroy();
			}
			return null;
		}

		return xmppConn;

	}

	// private void maybeStartReconnect() {
	// if (mCurrentRetryCount > 5) {
	// // we failed after all the retries - just die.
	// // Log.v(LOG_TAG, "maybeStartReconnect ran out of retrys");
	// // updateStatus(DISCONNECTED);
	// Toast.makeText(this, "Failed to connect.", Toast.LENGTH_SHORT).show();
	// onDestroy();
	// return;
	// } else {
	// mCurrentRetryCount += 1;
	// // a simple linear-backoff strategy.
	// // int timeout = 5000 * mCurrentRetryCount;
	// // Log.e(LOG_TAG, "maybeStartReconnect scheduling retry in " +
	// // timeout);
	// // mReconnectHandler.postDelayed(mReconnectRunnable, timeout);
	// }
	// }
	
	private static void registerProviders() { 
		ProviderManager pm = ProviderManager.getInstance();
        pm.addIQProvider(
            "query", "http://jabber.org/protocol/disco#items",
             new org.jivesoftware.smackx.provider.DiscoverItemsProvider()
        );
        
        pm.addIQProvider("query",
                "http://jabber.org/protocol/disco#info",
                new org.jivesoftware.smackx.provider.DiscoverInfoProvider());
        
        pm.addIQProvider("pubsub",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.PubSubProvider());

        pm.addExtensionProvider("subscription", PubSubNamespace.BASIC.getXmlns() , new SubscriptionProvider());
        
        pm.addExtensionProvider(
                "create",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());
        
        pm.addExtensionProvider("items",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.ItemsProvider());
        
        pm.addExtensionProvider("item",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());
        
        pm.addExtensionProvider("item", "",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());
        
        pm.addExtensionProvider(
                        "subscriptions",
                        "http://jabber.org/protocol/pubsub",
                        new org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider());

        pm.addExtensionProvider(
                        "subscriptions",
                        "http://jabber.org/protocol/pubsub#owner",
                        new org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider());

        pm.addExtensionProvider(
                        "affiliations",
                        "http://jabber.org/protocol/pubsub",
                        new org.jivesoftware.smackx.pubsub.provider.AffiliationsProvider());
        
        pm.addExtensionProvider(
                        "affiliation",
                        "http://jabber.org/protocol/pubsub",
                        new org.jivesoftware.smackx.pubsub.provider.AffiliationProvider());
        
        pm.addExtensionProvider("options",
                "http://jabber.org/protocol/pubsub",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());
        
        pm.addIQProvider("pubsub",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.PubSubProvider());
        
        pm.addExtensionProvider("configure",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());
        
        pm.addExtensionProvider("default",
                "http://jabber.org/protocol/pubsub#owner",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());


        pm.addExtensionProvider("event",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.EventProvider());
        
        pm.addExtensionProvider(
                        "configuration",
                        "http://jabber.org/protocol/pubsub#event",
                        new org.jivesoftware.smackx.pubsub.provider.ConfigEventProvider());
        
        pm.addExtensionProvider(
                        "delete",
                        "http://jabber.org/protocol/pubsub#event",
                        new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());
        
        pm.addExtensionProvider("options",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.FormNodeProvider());
        
        pm.addExtensionProvider("items",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.ItemsProvider());
        
        pm.addExtensionProvider("item",
                "http://jabber.org/protocol/pubsub#event",
                new org.jivesoftware.smackx.pubsub.provider.ItemProvider());

        pm.addExtensionProvider("headers",
                "http://jabber.org/protocol/shim",
                new org.jivesoftware.smackx.provider.HeaderProvider());

        pm.addExtensionProvider("header",
                "http://jabber.org/protocol/shim",
                new org.jivesoftware.smackx.provider.HeadersProvider());
        
        
        pm.addExtensionProvider(
                        "retract",
                        "http://jabber.org/protocol/pubsub#event",
                        new org.jivesoftware.smackx.pubsub.provider.RetractEventProvider());
        
        pm.addExtensionProvider(
                        "purge",
                        "http://jabber.org/protocol/pubsub#event",
                        new org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider());
        
        pm.addExtensionProvider(
                "x",
                "jabber:x:data",
                new org.jivesoftware.smackx.provider.DataFormProvider());

        SmackConfiguration.setKeepAliveInterval(-1);
	}

}
