package com.ms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PackUtils {
	
	/**
	 * 包装定位点
	 * @param longitude
	 * @param latitude
	 * @param date
	 * @return
	 */
	public static Map<String, String> packingPoint( String longitude,
			String latitude, Date date, String pupillus_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		map.put("pupillus_id", pupillus_id);
		return map;
	}
	
	/**
	 * 包装围栏
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param date
	 * @param entity_id
	 * @return
	 */
	public static Map<String, String> packingFencing(String name, double longitude,
			double latitude, double radius,  Integer entity_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("longitude", String.valueOf(longitude));
		map.put("latitude", String.valueOf(latitude));
		map.put("radius", String.valueOf(radius));
		map.put("entity_id", String.valueOf(entity_id));
		return map;
	}
}
