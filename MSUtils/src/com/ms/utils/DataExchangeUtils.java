package com.ms.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;

import com.ms.utils.bean.AnomalyRecord;
import com.ms.utils.bean.Entity;
import com.ms.utils.bean.Fencing;
import com.ms.utils.bean.FencingRecord;
import com.ms.utils.bean.Point;
import com.ms.utils.bean.Pupillus;
import com.ms.utils.bean.User;
import com.ms.utils.bean.WhiteNum;
import com.ms.utils.sax.service.SaxService;

public class DataExchangeUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 注册
	 * 
	 * @param role
	 *            （用户或被监护端）
	 * @param param1
	 *            （手机号码或移动设备唯一标识吗）
	 * @param param2
	 *            （密码或验证码）
	 * @return
	 * @throws IOException
	 */
	public static String userRegister(User user) throws IOException {
		// 获得URL
		String url = getURL("Register");
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", "user");

		map.put("phoneNumber", user.getPhone_number());
		map.put("pwd", user.getPwd());
		map.put("name", user.getName());
		
		InputStream inputStream = HttpUtils.sendPost(url, map);
		String str = HttpUtils.changeInputStream(inputStream);
		// 返回请求结果
		return str;
	}

	/**
	 * 
	 * @param pupillus
	 * @return
	 * @throws IOException
	 */
	public static String pupillusRegister(Pupillus pupillus) throws Exception {
		// 获得URL
		String url = getURL("Register");
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", "pupillus");

		map.put("meid", pupillus.getMeid());
		map.put("verification_code", pupillus.getVerification_code());
		InputStream inputStream = HttpUtils.sendPost(url, map);
		String result = HttpUtils.changeInputStream(inputStream);
		// 返回请求结果
		return result;
	}

	/**
	 * 用户登录
	 * 
	 * @param role
	 *            角色
	 * @param phoneNumber
	 *            手机号码
	 * @param pwd
	 *            密码
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static User userLogin(User user) throws FileNotFoundException,
			IOException {
		// 获得URL
		String url = getURL("Login");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", "user");
		map.put("phoneNumber", user.getPhone_number());
		map.put("pwd", user.getPwd());
		// 返回请求结果
		List<Map<String, String>> m_users = SaxService.readXML(
				HttpUtils.sendPost(url, map), "user");
		if (m_users == null || m_users.size() == 0) {
			user.setId(-1);
		} else {
			user.setId(Integer.parseInt(m_users.get(0).get("id")));
			user.setName(m_users.get(0).get("name"));
		}
		return user;
	}

	/**
	 * 被监护端登录
	 * 
	 * @param meid
	 *            移动设备唯一标识码
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String pupillusLogin(Pupillus pupillus)
			throws FileNotFoundException, IOException {
		// 获得URL
		String url = getURL("Login");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("role", "pupillus");
		map.put("meid", pupillus.getMeid());
		map.put("verification_code", pupillus.getVerification_code());
		// 返回请求结果
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 绑定子端
	 * 
	 * @param entity
	 * @return
	 * @throws IOException
	 */
	public static String banding(Entity entity) throws IOException {
		// 获得URL
		String url = getURL("MonitoringEntity");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "banding");
		map.put("user_id", entity.getUser().getId().toString());
		map.put("meid", entity.getPupillus().getMeid());
		map.put("verification_code", entity.getPupillus()
				.getVerification_code());
		map.put("alias", entity.getAlias());
		// 返回请求结果
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws IOException
	 */
	public static String removeBanding(Entity entity) throws IOException {
		// 获得URL
		String url = getURL("MonitoringEntity");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "removeBanding");
		map.put("entity_id", entity.getId().toString());
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 获得实体集
	 * 
	 * @param user_id
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<Entity> getEntitiesByUser(Integer user_id)
			throws ClientProtocolException, IOException {
		List<Entity> entities = new ArrayList<Entity>();
		Entity entity = null;

		String url = getURL("MonitoringEntity");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getEntities");
		map.put("user_id", user_id.toString());

		// 返回请求结果

		List<Map<String, String>> m_entities = SaxService.readXML(
				HttpUtils.sendPost(url, map), "entity");
		if (m_entities != null && m_entities.size() > 0) {
			for (Map<String, String> m_entity : m_entities) {
				entity = new Entity();
				entity.setAlias(m_entity.get("alias"));
				entity.setId(Integer.parseInt(m_entity.get("id")));
				Pupillus pupillus = new Pupillus();
				pupillus.setId(Integer.parseInt(m_entity.get("pupillus_id")));
				pupillus.setMeid(m_entity.get("meid"));
				entity.setPupillus(pupillus);
				User user = new User();
				user.setId(Integer.parseInt(m_entity.get("user_id")));
				entity.setUser(user);
				entities.add(entity);
			}
		}

		return entities;
	}

	public static List<Entity> getEntitiesByPupillus(Integer pupillus_id)
			throws ClientProtocolException, IOException {
		List<Entity> entities = new ArrayList<Entity>();
		Entity entity = null;

		String url = getURL("MonitoringEntity");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getEntities");
		map.put("pupillus_id", pupillus_id.toString());

		// 返回请求结果
		System.out.println(HttpUtils.changeInputStream(HttpUtils.sendPost(url,
				map)));
		List<Map<String, String>> m_entities = SaxService.readXML(
				HttpUtils.sendPost(url, map), "entity");
		if (m_entities != null && m_entities.size() > 0) {
			for (Map<String, String> m_entity : m_entities) {
				entity = new Entity();
				// entity.setAlias(m_entity.get("alias"));
				entity.setId(Integer.parseInt(m_entity.get("id")));
				// Pupillus pupillus = new Pupillus();
				// pupillus.setId(Integer.parseInt(m_entity.get("pupillus_id")));
				// pupillus.setMeid(m_entity.get("meid"));
				// entity.setPupillus(pupillus);
				User user = new User();
				user.setId(Integer.parseInt(m_entity.get("user_id")));
				user.setPhone_number(m_entity.get("phone_number"));
				entity.setUser(user);
				entities.add(entity);
			}
		}

		return entities;
	}

	/**
	 * 获得历史定位点
	 * 
	 * @param pupillus_id
	 *            被监护端ID
	 * @param date
	 *            定位时间
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<Point> getHistory(Integer pupillus_id, Date date)
			throws IOException, ParseException {
		List<Point> points = new ArrayList<Point>();
		Point point = null;
		// 获得URL
		String url = getURL("Point");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getHistory");
		map.put("pupillus_id", pupillus_id.toString());
		map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date));

		// 返回请求结果
		String str = HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
		// System.out.println(str);
		List<Map<String, String>> m_points = SaxService.readXML(
				new ByteArrayInputStream(str.getBytes("utf-8")), "point");
		// System.out.println(m_points.size());
		if (m_points != null && m_points.size() > 0) {
			for (Map<String, String> m_point : m_points) {
				point = new Point();
				point.setId(Integer.parseInt(m_point.get("id")));

				// System.out.println(m_point.get("id"));
				// System.out.println(m_point.get("date"));
				// System.out.println(sdf.parse(m_point.get("date")));
				// point.setDate(sdf.parse(m_point.get("date")));

				point.setLatitude(Double.parseDouble(m_point.get("latitude")));
				point.setLongitude(Double.parseDouble(m_point.get("longitude")));
				Pupillus pupillus = new Pupillus();
				pupillus.setId(pupillus_id);
				point.setPupillus(pupillus);
				points.add(point);

			}
		}
		return points;
	}

	public static List<Point> getRealTime(Integer pupillus_id, Date date)
			throws IOException, ParseException {
		List<Point> points = new ArrayList<Point>();
		Point point = null;
		// 获得URL
		String url = getURL("Point");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getRealTime");
		map.put("pupillus_id", pupillus_id.toString());
		map.put("date", new SimpleDateFormat("yyyy-MM-dd HH").format(date));

		// System.out.println(HttpUtils.changeInputStream(HttpUtils.sendPost(url,
		// map)));
		// 返回请求结果
		List<Map<String, String>> m_points = SaxService.readXML(
				HttpUtils.sendPost(url, map), "point");
		if (m_points != null && m_points.size() > 0) {
			for (Map<String, String> m_point : m_points) {
				point = new Point();
				point.setId(Integer.parseInt(m_point.get("id")));
				// point.setDate(sdf.parse(m_point.get("date")));
				point.setLatitude(Double.parseDouble(m_point.get("latitude")));
				point.setLongitude(Double.parseDouble(m_point.get("longitude")));
				Pupillus pupillus = new Pupillus();
				pupillus.setId(pupillus_id);
				point.setPupillus(pupillus);
				points.add(point);
			}
		}
		return points;
	}

	public static String addPoints(List<Point> points) throws IOException {

		if (points.size() <= 0 || points == null) {
			return "error";
		}

		StringBuilder xmlBuilder = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuilder.append("<points>");
		for (Point point : points) {
			xmlBuilder.append("<point>");
			xmlBuilder.append("<latitude>" + point.getLatitude()
					+ "</latitude>");
			xmlBuilder.append("<longitude>" + point.getLongitude()
					+ "</longitude>");
			xmlBuilder.append("<date>" + sdf.format(point.getDate())
					+ "</date>");
			xmlBuilder.append("<pupillus_id>" + point.getPupillus().getId()
					+ "</pupillus_id>");
			xmlBuilder.append("</point>");
		}
		xmlBuilder.append("</points>");

		String url = DataExchangeUtils.getURL("Point") + "?operation=addPoints";
		return HttpUtils.changeInputStream(HttpUtils.sendXML(
				xmlBuilder.toString(), url));
	}

	/**
	 * 获得围栏
	 * 
	 * @param entity_id
	 * @param date
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static List<Fencing> getFencings(Integer entity_id, String name)
			throws IOException {
		List<Fencing> fencings = new ArrayList<Fencing>();
		Fencing fencing = null;
		// 获得URL
		String url = getURL("GeoFencing");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getFencings");
		map.put("entity_id", entity_id.toString());
		map.put("name", name);

		// 返回请求结果
		List<Map<String, String>> m_Fencings = SaxService.readXML(
				HttpUtils.sendPost(url, map), "fencing");
		if (m_Fencings != null && m_Fencings.size() > 0) {
			for (Map<String, String> m_fencing : m_Fencings) {
				fencing = new Fencing();
				fencing.setId(Integer.parseInt(m_fencing.get("id")));
				fencing.setLatitude(Double.parseDouble(m_fencing
						.get("latitude")));
				fencing.setLongitude(Double.parseDouble(m_fencing
						.get("longitude")));
				fencing.setName(m_fencing.get("name"));
				fencing.setRadius(Double.parseDouble(m_fencing.get("radius")));

				Entity entity = new Entity();
				entity.setId(Integer.parseInt(m_fencing.get("entity_id")));
				fencing.setEntity(entity);
				fencings.add(fencing);
			}
		}
		return fencings;
	}

	public static List<Fencing> getFencings(List<Entity> entities)
			throws IOException {
		List<Fencing> fencings = new ArrayList<Fencing>();
		for (Entity entity : entities) {
			List<Fencing> temp = getFencings(entity.getId(), null);
			if (temp != null && temp.size() > 0) {
				fencings.addAll(temp);
			}
		}
		return fencings;
	}

	/**
	 * 删除围栏
	 * 
	 * @param fencing
	 * @return
	 * @throws IOException
	 */
	public static String addFencings(Fencing fencing) throws IOException {
		// 获得URL
		String url = getURL("GeoFencing") + "?operation=addFencings";

		// 设置参数
		Map<String, String> map = PackUtils.packingFencing(fencing.getName(),
				fencing.getLongitude(), fencing.getLatitude(),
				fencing.getRadius(), fencing.getEntity().getId());

		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 删除围栏
	 * 
	 * @param id
	 *            围栏ID
	 * @return
	 * @throws IOException
	 */
	public static String delFencings(Integer id) throws IOException {
		String url = getURL("GeoFencing") + "?operation=delFencing";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id.toString());
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 添加警报信息
	 * 
	 * @param date
	 * @param fencing_id
	 * @param message
	 * @return
	 * @throws IOException
	 */
	public static String addFencingRecord(Date date, Integer fencing_id,
			String message) throws IOException {
		String url = getURL("FencingRecord") + "?operation=addRecord";
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", sdf.format(date));
		map.put("fencing_id", fencing_id.toString());
		map.put("message", message);
		
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 
	 * @param fencing_id
	 * @param date
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<FencingRecord> getHistoryFencingRecord(
			Integer fencing_id, Date date) throws IOException, ParseException {
		List<FencingRecord> records = new ArrayList<FencingRecord>();
		FencingRecord record;
		
		String url = getURL("FencingRecord") + "?operation=getHistory";
		Map<String, String> map = new HashMap<String, String>();
		map.put("fencing_id", fencing_id.toString());
		map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date));

		List<Map<String, String>> m_Records = SaxService.readXML(
				HttpUtils.sendPost(url, map), "record");
		if (m_Records != null && m_Records.size() > 0) {

			for (Map<String, String> m_Record : m_Records) {
				record = new FencingRecord();
				record.setId(Integer.parseInt(m_Record.get("id")));
				record.setMessage(m_Record.get("message"));
				record.setDate(new Date(Long.parseLong(m_Record.get("date"))));
				Fencing fencing = new Fencing();
				fencing.setId(Integer.parseInt(m_Record.get("fencing_id")));
				record.setFencing(fencing);

				records.add(record);
			}
		}
		return records;
	}

	/**
	 * 添加白名单
	 * 
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static String addWhiteNum(WhiteNum num) throws IOException {
		String url = getURL("WhiteList") + "?operation=addNum";
		Map<String, String> map = new HashMap<String, String>();
		map.put("pupillus_id", num.getPupillus().getId().toString());
		map.put("phone_number", num.getPhone_number());
		map.put("note", num.getNote());
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 删除白名单
	 * 
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static String delWhiteNum(WhiteNum num) throws IOException {
		String url = getURL("WhiteList") + "?operation=delNum";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", num.getId().toString());
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 获得白名单
	 * 
	 * @param pupillus
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<WhiteNum> getWhiteList(Pupillus pupillus)
			throws ClientProtocolException, IOException {
		List<WhiteNum> whiteList = new ArrayList<WhiteNum>();
		WhiteNum num = null;
		// 获得URL
		String url = getURL("WhiteList");
		// 设置post参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("operation", "getNums");
		map.put("pupillus_id", pupillus.getId().toString());

		// 返回请求结果
		List<Map<String, String>> m_Nums = SaxService.readXML(
				HttpUtils.sendPost(url, map), "num");
		if (m_Nums != null && m_Nums.size() > 0) {
			for (Map<String, String> m_Num : m_Nums) {
				num = new WhiteNum();
				num.setId(Integer.parseInt(m_Num.get("id")));
				num.setNote(m_Num.get("note"));
				num.setPhone_number(m_Num.get("phone_number"));
				whiteList.add(num);
			}
		}
		return whiteList;
	}

	public static String addAnomalyRecord(AnomalyRecord record)
			throws IOException {
		String url = getURL("AnomalyRecord") + "?operation=addRecord";
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", sdf.format(record.getDate()));
		map.put("pupillus_id", record.getPupillus().getId().toString());
		map.put("message", record.getMessage());
		return HttpUtils.changeInputStream(HttpUtils.sendPost(url, map));
	}

	/**
	 * 
	 * @param fencing_id
	 * @param date
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<AnomalyRecord> getHistoryAnomalyRecord(
			Integer pupillus_id, Date date) throws IOException, ParseException {
		List<AnomalyRecord> records = new ArrayList<AnomalyRecord>();
		AnomalyRecord record;
		String url = getURL("AnomalyRecord") + "?operation=getHistory";
		Map<String, String> map = new HashMap<String, String>();
		map.put("pupillus_id", pupillus_id.toString());
		if (date != null) {
			map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date));
		}

		List<Map<String, String>> m_Records = SaxService.readXML(
				HttpUtils.sendPost(url, map), "record");
		if (m_Records != null && m_Records.size() > 0) {
			for (Map<String, String> m_Record : m_Records) {
				record = new AnomalyRecord();
				record.setId(Integer.parseInt(m_Record.get("id")));
				record.setDate(sdf.parse(m_Record.get("date")));
				record.setMessage(m_Record.get("message"));
				Pupillus pupillus = new Pupillus();
				pupillus.setId(Integer.parseInt(m_Record.get("pupillus_id")));
				record.setPupillus(pupillus);

				records.add(record);
			}
		}
		return records;
	}

	/**
	 * 获得URL
	 * 
	 * @param servletName
	 * @return
	 * @throws IOException
	 */
	public static String getURL(String servletName) throws IOException {
		// 初始化Properties
		Properties properties = new Properties();
		InputStream inStream = DataExchangeUtils.class
				.getResourceAsStream("connection-config.properties");
		properties.load(inStream);

		return properties.getProperty("URL")
				+ properties.getProperty(servletName);
	}

	public static void main(String[] args) throws IOException, ParseException {
		List<FencingRecord> list = DataExchangeUtils.getHistoryFencingRecord(20, new Date(1466524800000l));
		for (FencingRecord fencingRecord : list) {
			System.out.println(fencingRecord.getMessage() + fencingRecord.getDate());
		}
	}
}
