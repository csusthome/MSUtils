package com.ms.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpUtils {

	private final static String encode = "utf-8";

	/**
	 * 从网页获取XML数据
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static InputStream getXML(String path) throws IOException {
		InputStream inputStream = null;

		URL url = new URL(path);
		if (url != null) {
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 200) {
				inputStream = connection.getInputStream();
			}
		}
		return inputStream;
	}

	/**
	 * 以post向服务器提交数据
	 * 
	 * @param path
	 * @param map
	 *            <参数名，参数值>
	 * @param encode
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static InputStream sendPost(String url, Map<String, String> map)
			throws ClientProtocolException, IOException {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (map != null && !map.isEmpty()) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 实现将请求的参数封装到表单中
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
		// 使用Post方式提交数据
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		// 指定post请求
		DefaultHttpClient client = new DefaultHttpClient();

		// 设置超时
		HttpParams params = client.getParams();
		if (params == null) {
			params = new BasicHttpParams();
		}
		
		// 连接超时
		HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
		// 返回超时
		HttpConnectionParams.setSoTimeout(params, 10 * 1000);
		client.setParams(params);

		HttpResponse httpResponse = client.execute(httpPost);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			return httpResponse.getEntity().getContent();
		}

		return null;
	}

	/**
	 * 将一个输入流转换成指定编码的字符串
	 * 
	 * @param inputStream
	 * @param encode
	 * @return 指定编码的字符串
	 */
	public static String changeInputStream(InputStream inputStream) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "false";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向服务器发送XML数据
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream sendXML(String xml, String url)
			throws MalformedURLException, IOException {

		byte[] entity;
		entity = xml.getBytes(encode);

		HttpURLConnection conn = (HttpURLConnection) new URL(url)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// 指定发送的内容类型为xml
		conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entity);
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}
}
