package biz.our.application.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.android.maps.GeoPoint;

public class PointsToDraw {

	private static List<GeoPoint> points;
	private static Set<Integer> closeOnes; 

	private PointsToDraw() {
	}

	public static List<GeoPoint> getPoints() {
		if (points == null) {
			points = new ArrayList<GeoPoint>();
			closeOnes = new HashSet<Integer>(); 
		}
		return points;
	}

	public static void add(GeoPoint point) {
		if (points == null) {
			points = new ArrayList<GeoPoint>();
			closeOnes = new HashSet<Integer>();
		}
		points.add(point);
	}

	public static void add(GeoPoint point, boolean proximity) {
		if (points == null) {
			points = new ArrayList<GeoPoint>();
			closeOnes = new HashSet<Integer>();
		}
		if (proximity == false) {
			points.add(point);
		} else { 
			points.add(point);
			closeOnes.add(points.indexOf(point));
		}

	}

	public static boolean closeEnough(int i) { 
		if (closeOnes.contains(i)) { 
			return true; 
		}
		return false;
	}
}
