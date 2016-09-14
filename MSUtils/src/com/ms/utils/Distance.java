package com.ms.utils;

import com.ms.utils.bean.Fencing;
import com.ms.utils.bean.Point;

public class Distance {

	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(Point point1, Point point2) {

		double radLat1 = rad(point1.getLatitude());
		double radLat2 = rad(point2.getLatitude());
		double difference = radLat1 - radLat2;
		double mdifference = rad(point1.getLongitude())
				- rad(point2.getLongitude());
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS * 1000;

		return distance;
	}

	public static double getDistance(Fencing fence, Point point) {

		double radLat1 = rad(fence.getLatitude());
		double radLat2 = rad(point.getLatitude());
		double difference = radLat1 - radLat2;
		double mdifference = rad(fence.getLongitude())
				- rad(point.getLongitude());
		double distance = 2 * Math.asin(Math.sqrt(Math.pow(
				Math.sin(difference / 2), 2)
				+ Math.cos(radLat1)
				* Math.cos(radLat2)
				* Math.pow(Math.sin(mdifference / 2), 2)));
		distance = distance * EARTH_RADIUS * 1000;

		return distance;
	}

	public static boolean inFence(Fencing fence, Point point) {
		boolean flag = false;
		if (fence.getRadius() > getDistance(fence, point)) {
			flag = true;
		}
		return flag;
	}

}
