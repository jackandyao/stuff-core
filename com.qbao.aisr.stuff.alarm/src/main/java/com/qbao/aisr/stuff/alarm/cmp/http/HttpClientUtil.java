package com.qbao.aisr.stuff.alarm.cmp.http;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
 
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * 
     * @author 贾红平
     * HTTP协议请求解析工具类
     * $LastChangedDate: 2017-03-11 16:02:45 +0800 (Sat, 11 Mar 2017) $
     * $LastChangedRevision: 138 $
     * $LastChangedBy: allen $
 */
@SuppressWarnings("all")
public class HttpClientUtil {
    
   
	private HttpClientUtil(){}
	
	static class InnerHttpClinet{
	    public static HttpClientUtil instance = new HttpClientUtil();
	}
	
	public synchronized static HttpClientUtil getHttpClientInstance(){
		return InnerHttpClinet.instance;
	}
	
	 /**
     * 设置请求配置参数呢
     */
    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();
    
	
	/**
	 * 发送 post请求
	 * @param httpUrl 地址
	 */
	public String sendHttpPost(String httpUrl) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost  
		return executeHttpPost(httpPost);
	}
	
	/**
	 * 发送Post请求
	 * @param httpPost
	 * @return
	 */
	private String executeHttpPost(HttpPost httpPost) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			httpPost.setConfig(requestConfig);
			// 执行请求
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(ExceptionUtils.getFullStackTrace(e));
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 发送 get请求
	 * @param httpUrl
	 */
	public String sendHttpGet(String httpUrl) {
		HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
		return executeHttpGet(httpGet);
	}
	
 
	
	/**
	 * 发送Get请求
	 * @param httpPost
	 * @return
	 */
	private String executeHttpGet(HttpGet httpGet) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建默认的httpClient实例 内部自身已经封装好了(线程安全 创建实例个数)
			httpClient = HttpClients.createDefault();
			httpGet.setConfig(requestConfig);
			// 执行请求
			response = httpClient.execute(httpGet);
			entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(ExceptionUtils.getFullStackTrace(e));
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
	
	
	/**
	 * 并发获取http结果
	 * @param url
	 * @author sjzhangjun
	 * @return
	 */
	public String getHttpResponse(String url) {
		String result = null;
		HttpClient httpclient = getHttpClient();   
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            // 执行get请求.    
            HttpResponse response = httpclient.execute(httpget);  
            /*读返回数据*/  
            result = EntityUtils.toString(response.getEntity()); 
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(Exception e){
        	e.printStackTrace();
        }
		return result;
	}
	//连接池
  	private static ThreadSafeClientConnManager cm = null;  
      static {  
          SchemeRegistry schemeRegistry = new SchemeRegistry();  
          schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory  
                  .getSocketFactory()));  
    
          cm = new ThreadSafeClientConnManager(schemeRegistry);  
          try {  
              int maxTotal = 100;  
              cm.setMaxTotal(maxTotal);  
          } catch (NumberFormatException e) {  
          }  
          // 每条通道的并发连接数设置（连接池）  
          try {  
              int defaultMaxConnection = 50;  
              cm.setDefaultMaxPerRoute(defaultMaxConnection);  
          } catch (NumberFormatException e) {  
          }  
      }  
      public HttpClient getHttpClient() {  
          HttpParams params = new BasicHttpParams();  
          params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,  
                  HttpVersion.HTTP_1_1);
          params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); // 3000ms  
          return new DefaultHttpClient(cm, params);
      }
}
