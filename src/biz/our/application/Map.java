package biz.our.application;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import biz.our.application.data.PointsToDraw;
import biz.our.application.receivers.OutgoingReceiver;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class Map extends MapActivity implements LocationListener {

	private double latitude = 43.615, longitude = 7.0543;
	private EditText txted = null;
	private MapView gMapView = null;
	private MapController mc = null;
	private GeoPoint p = null;
	private Button button = null;

	// final Handler handler = new Handler() {
	// public void handleMessage(String msg) {
	// // int total = msg.getData().getInt("total");
	// // progressDialog.setProgress(total);
	// // if (total >= 100){
	// // dismissDialog(PROGRESS_DIALOG);
	// // progressThread.setState(ProgressThread.STATE_DONE);
	// }
	// };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);

		p = new GeoPoint((int) (latitude * 1000000), (int) (longitude * 1000000));

		button = (Button) findViewById(R.id.sendButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Talk.getContext(), OutgoingReceiver.class);
				intent.setAction("biz.our.application.xmpp.outgoing");
				intent.putExtra("message", "Wake up.");
				Talk.getContext().sendBroadcast(intent);
			}
		});

		// Creating TextBox displying Lat, Long
		txted = (EditText) findViewById(R.id.id1);
		String currentLocation = "Lat: " + latitude + " Lng: " + longitude;
		txted.setText(currentLocation);

		// Creating and initializing Map
		gMapView = (MapView) findViewById(R.id.myGMap);

		gMapView.setSatellite(true);
		mc = gMapView.getController();
		mc.setCenter(p);
		mc.setZoom(16);

		// Add a location mark
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
		List<Overlay> list = gMapView.getOverlays();
		list.add(myLocationOverlay);

		gMapView.setBuiltInZoomControls(true);

		// Getting locationManager and reflecting changes over map if distance
		// travel by
		// user is greater than 500m from current location.
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);

	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			String currentLocation = "Lat: " + lat + " Lng: " + lng;
			txted.setText(currentLocation);
			p = new GeoPoint((int) lat * 1000000, (int) lng * 1000000);
			mc.animateTo(p);
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	/* Class overload draw method which actually plot a marker,text etc. on Map */
	protected class MyLocationOverlay extends com.google.android.maps.Overlay {

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
			Paint paint = new Paint();

			super.draw(canvas, mapView, shadow);
			// Converts lat/lng-Point to OUR coordinates on the screen.

			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 255, 255);
			paint.setStyle(Paint.Style.STROKE);

			Point myScreenCoords = new Point();
			mapView.getProjection().toPixels(p, myScreenCoords);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

			canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("I am here", myScreenCoords.x, myScreenCoords.y, paint);

			GeoPoint p2;
			for (int i = 0; i < PointsToDraw.getPoints().size(); i++) {
				p2 = PointsToDraw.getPoints().get(i);
				Point myScreenCoords2 = new Point();
				mapView.getProjection().toPixels(p2, myScreenCoords2);
				if (i % 2 == 0) {
					bmp = BitmapFactory.decodeResource(getResources(), R.drawable.taxi);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(), R.drawable.taxi2);
				}

				canvas.drawBitmap(bmp, myScreenCoords2.x, myScreenCoords2.y, paint);

				if (PointsToDraw.closeEnough(i)) {
					Projection projection = mapView.getProjection();
					Path path = new Path();

					Point from = new Point();
					Point to = new Point();
					projection.toPixels(p, from);
					projection.toPixels(p2, to);
					path.moveTo(from.x, from.y);
					path.lineTo(to.x, to.y);

					Paint mPaint = new Paint();
					mPaint.setStyle(Style.FILL);
					mPaint.setColor(0xFFFF0000);
					mPaint.setAntiAlias(true);
					canvas.drawPath(path, mPaint);
					super.draw(canvas, mapView, shadow);
					canvas.drawLine(myScreenCoords.x+30, myScreenCoords.y+30, myScreenCoords2.x+30, myScreenCoords2.y+30, mPaint);
				}

			}
			return true;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
