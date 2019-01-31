package util.http.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

	//private static Logger log = Logger.getLogger(HttpClientUtils.class);
	public final static String UTF8 = "UTF-8";
	public final static String GBK = "GBK";
	public final static String ISO = "ISO-8859-1";
	private final static int TIME_OUT = 60000;  //超时时间设置 默认60秒

	private static PoolingHttpClientConnectionManager cm;
	private static RequestConfig requestConfig;
	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	static{
		cm = new PoolingHttpClientConnectionManager();
		//Increase max total connection to 200
		cm.setMaxTotal(200);//总共保持200个连接(对于多个通过该http访问的网站)
		//Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20); //每个网站的默认连接最多20个
		//Increase max connections for localhost:80 to 50
		//连接池里面可以保持长连接到"http://open.wo.com.cn/"地址的最大数是50个,如果你请求"http://open.wo.com.cn/"这个地址的量很大，
		//把50个HTTP连接都占完了，那新的请求过来就需要等到其他使用连接池里面到这个地址的HTTP连接释放了才行
		//HttpHost wo = new HttpHost("http://open.wo.com.cn/", 80);
		//cm.setMaxPerRoute(new HttpRoute(wo), 80);

		//设置超时时间
		requestConfig = RequestConfig.custom()
				.setSocketTimeout(TIME_OUT)
				.setConnectTimeout(TIME_OUT)
				.build();
	}


	/**
	 * 发送单个HTTP请求
	 *
	 * @param url  请求地址
	 * @param json 请求数据JSON格式
	 * @throws Exception
	 */
	public static String doPost(String url, String json, String encode) throws Exception {
		CloseableHttpClient httpclient = bulidHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+encode+"");

		StringEntity entity = new StringEntity(json,encode);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String content = null;
		try {
			HttpEntity entity2 = response.getEntity();
			if(entity2==null){
				return "";
			}
			// do something useful with the response body
			content = getContent(response,encode);
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response.close();
		}
		return content;
	}

	/**
	 * 发送验证信息HTTP请求
	 *
	 * @param url  请求地址
	 * @param json 请求数据JSON格式
	 * @throws Exception
	 */
	public static String doAuthPost(String url, String json,Map<String, String> authMap, String encode) throws Exception {
		CloseableHttpClient httpclient = bulidHttpClient();
		log.debug("HttpClient:DoPost::" + url);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+encode+"");
//		StringBuffer buffer = new StringBuffer();
//		for (String key : authMap.keySet()) {
//			buffer.append(key).append("=\"").append(authMap.get(key)).append("\"").append(",");
//		}
//		if(buffer.toString().endsWith(",")){
//			buffer.deleteCharAt(buffer.length()-1);
//		}
//		httpPost.setHeader("Authorization", buffer.toString());

		for (Map.Entry<String, String> auth: authMap.entrySet()) {
			httpPost.setHeader(auth.getKey(), auth.getValue());
		}
		StringEntity entity = new StringEntity(json,encode);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String content = null;
		try {
			HttpEntity entity2 = response.getEntity();
			// do something useful with the response body
			content = getContent(response,encode);
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response.close();
		}
		return content;
	}


	/**
	 * 发送单个HTTP请求
	 *
	 * @param url
	 *            请求地址
	 * @throws Exception
	 */
	public static String doPostParams(String url, Map<String, String> params,Map<String, String> authMap,String encode) throws Exception {

		log.debug("HttpClient:DoPost::" + url);
		CloseableHttpClient httpclient = bulidHttpClient();
		HttpPost httpPost = new HttpPost(url);
		for (Map.Entry<String, String> auth: authMap.entrySet()) {
			httpPost.setHeader(auth.getKey(), auth.getValue());
		}
		List<NameValuePair> nvp = setParams(params);
		httpPost.setEntity(new UrlEncodedFormEntity(nvp, encode));
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String content = null;
		try {
			HttpEntity entity2 = response.getEntity();
			content = getContent(response,encode);
			if(content.equals("") || content==null){
//				content = response.getHeaders("Location").toString();
				for(Header header : response.getHeaders("Location")) {
					content = header.getValue();
				}
			}
			EntityUtils.consume(entity2);
		} finally {
			response.close();
		}
		return content;
	}


	/**
	 * 发送Get请求
	 *
	 * @param url  请求地址
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url,String encode) throws Exception {
		CloseableHttpClient httpclient = bulidHttpClient();

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		String content = null;

		try {
			HttpEntity entity = response.getEntity();
			content = getContent(response,encode);
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}
		return content;
	}

	/**
	 * 发送http delete请求
	 */
	public static String httpDelete(String url,Map<String,String> headers,String encode){
		if(encode == null){
			encode = "utf-8";
		}
		String content = null;
		//since 4.3 不再使用 DefaultHttpClient
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		HttpDelete httpdelete = new HttpDelete(url);
		//设置header
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpdelete.setHeader(entry.getKey(),entry.getValue());
			}
		}
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = closeableHttpClient.execute(httpdelete);
			HttpEntity entity = httpResponse.getEntity();
			content = EntityUtils.toString(entity, encode);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {   //关闭连接、释放资源
			closeableHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 获取返回数据
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private static String getContent(HttpResponse response, String encode) {
		HttpEntity entity = response.getEntity();
		byte[] bytes;
		String content = null;
		try {
			bytes = EntityUtils.toByteArray(entity);
			content = new String(bytes, encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	private static List<NameValuePair> setParams(Map<String, String> map) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (null != map) {
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = map.get(key);
				nvps.add(new BasicNameValuePair(key, value));
			}
		}
		return nvps;
	}


	/**
	 * 获取HttpClient对象
	 * @return
	 */
	private static CloseableHttpClient bulidHttpClient(){
		//清除过期连接
		//IdleConnectionEvictor ice = IdleConnectionEvictor.getInstance(cm);
		//ice.closed();
		cm.closeExpiredConnections(); //清除过期链接
		//cm.closeIdleConnections(5, TimeUnit.SECONDS); //一段时间内不活动的连接

		CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .setDefaultRequestConfig(requestConfig)
		        .build();

		return httpClient;
	}

	public static void downloadFile(String destFile,String url,Map<String, String> headerMap,String body,String encode) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = bulidHttpClient();

		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+encode+"");
		if(headerMap != null){
			for(String key : headerMap.keySet()){
				String value = headerMap.get(key);
				httpPost.setHeader(key, value);
			}
		}
		StringEntity entity = new StringEntity(body, encode);
		httpPost.setEntity(entity);

		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			InputStream is = response.getEntity().getContent();
			FileOutputStream bytestream = new FileOutputStream(destFile);
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			bytestream.close();
			bytestream.flush();
			// do something useful with the response body
			// and ensure it is fully consumed
			is.close();
		} finally {
			response.close();
		}
	}
	public static String uploadFile(String url, Map<String, String> headerMap,File file,String encode) {
		CloseableHttpClient httpclient = bulidHttpClient();

		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+encode+"");
		if(headerMap != null){
			for(String key : headerMap.keySet()){
				String value = headerMap.get(key);
				httpPost.setHeader(key, value);
			}
		}
		try {
			InputStreamEntity inputStreamEntity = new InputStreamEntity(new FileInputStream(file),file.length());
			httpPost.setEntity(inputStreamEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String content = getContent(response,encode);
			EntityUtils.consume(entity);
			response.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
    }


	public static String doPostByBody(String url, Map<String, String> headerMap,String body,String encode) throws Exception {
		log.debug("HttpClient:DoPost::" + url);
		CloseableHttpClient httpclient = bulidHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+encode+"");
		if(headerMap != null){
			for(String key : headerMap.keySet()){
				String value = headerMap.get(key);
				httpPost.setHeader(key, value);
			}
		}
		StringEntity entity = new StringEntity(body,encode);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String content = null;

		try {
			HttpEntity entity2 = response.getEntity();
			content = getContent(response,encode);
			if(content.equals("") || content==null){
//				content = response.getHeaders("Location").toString();
				for(Header header : response.getHeaders("Location")) {
					content = header.getValue();
				}
			}
			EntityUtils.consume(entity2);
		} finally {
			response.close();
		}
		return content;
	}

	public static String doPost(String url,String json,String requestCode,String responseCode) throws Exception {
		CloseableHttpClient httpclient = bulidHttpClient();

		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json;charset="+requestCode);
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		String content = null;
		try {
			HttpEntity entity2 = response.getEntity();
			// do something useful with the response body
			content = getContent(response,responseCode);
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response.close();
		}
		return content;
	}
}
