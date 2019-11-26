package com.sunnet.org.util;

public class GeoUtil {

	/**地址获取
	 * 根据经纬度和距离返回一个矩形范围
	 * 
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @param distance
	 *            距离(单位为米)
	 * @return [lng1,lat1, lng2,lat2] 矩形的左下角(lng1,lat1)和右上角(lng2,lat2)
	 */
	public static double[] getRectangle(double lng, double lat, long distance) {
		float delta = 111000;
		if (lng != 0 && lat != 0) {
			double lng1 = lng - distance
					/ Math.abs(Math.cos(Math.toRadians(lat)) * delta);
			double lng2 = lng + distance
					/ Math.abs(Math.cos(Math.toRadians(lat)) * delta);
			double lat1 = lat - (distance / delta);
			double lat2 = lat + (distance / delta);
			return new double[] { lng1, lat1, lng2, lat2 };
		} else {
			// TODO ZHCH 等于0时的计算公式
			double lng1 = lng - distance / delta;
			double lng2 = lng + distance / delta;
			double lat1 = lat - (distance / delta);
			double lat2 = lat + (distance / delta);
			return new double[] { lng1, lat1, lng2, lat2 };
		}
	}

	/**
	 * 得到两点间的距离 米
	 * 
	 * @param lat1
	 *            第一点纬度
	 * @param lng1
	 *            第一点经度
	 * @param lat2
	 *            第二点纬度
	 * @param lng2
	 *            第二点经度
	 * @return
	 */
	public static double getDistanceOfMeter(double lat1, double lng1,
			double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10;
		s = Math.round(s / 100d) / 10d;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 地球半径：6378.137KM
	 */
	private static double EARTH_RADIUS = 6378.137;

	// lng1 第一个点经度，lat1第一点纬度；lng2第二点经度，lat2第二点纬度
	public static double getShortestDistance(double lng1, double lat1,
			double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10;
		s = Math.round(s / 100d) / 10d;
		// s = s * 1000; //换算成米
		return s; // 得到千米数

	}
	
	
	//第二种工具栏
	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球的半径

	
	public static double getDistance(double longt1, double lat1, double longt2,
			double lat2) {
		double x, y, distance;
		x = (longt2 - longt1) * PI * R
				* Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
		y = (lat2 - lat1) * PI * R / 180;
		distance = Math.hypot(x, y);
		return distance;
	}

	/**
	 * 得到两点间的距离 米
	 * 
	 * @param lat1
	 *            第一点纬度
	 * @param lng1
	 *            第一点经度
	 * @param lat2
	 *            第二点纬度
	 * @param lng2
	 *            第二点经度
	 * @return
	 */
	public static double cal2PDis(double lat1, double lon1, double lat2,
			double lon2) {
		double result = 0;
		double lat = Math.abs(lat1 - lat2);
		double lon = Math.abs(lon1 - lon2);
		double latRadian = Math.toRadians(lat1);
		double kmPerLat = 111.13295 - 0.55982 * Math.cos(2 * latRadian)
				+ 0.00117 * Math.cos(4 * latRadian);
		double latKm = lat * kmPerLat;
		double kmPerLon = 111.41288 * Math.cos(latRadian) - 0.09350
				* Math.cos(3 * latRadian) + 0.00012 * Math.cos(5 * latRadian);
		double lonKm = lon * kmPerLon;
		result = Math.sqrt(latKm * latKm + lonKm * lonKm) * 1000;
		return result;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		double lng1 =22.54347;
		double lat1 = 113.958725;
		double lng2 = 22.54080510;
		double lat2 = 113.94752660;
		double q = GeoUtil.getShortestDistance(lng1, lat1, lng2, lat2);
		System.out.println(q);
		q = GeoUtil.getDistanceOfMeter(lng1, lat1, lng2, lat2);
		System.out.println(q);
		System.out.println(getDistance(lng1, lat1, lng2, lat2));

		System.out.println(getDistance(22.54347, 113.958725, 22.54080510,
				113.94752660));
		System.out.println(cal2PDis(22.54347, 113.958725, 22.54080510,
				113.94752660));
	}
}