package com.qbao.aisr.stuff.utils;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HttpUtil {
    static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    public static final String URL_KEYWORD_DIRIDS = SetSystemProperty.readValue(SetSystemProperty.profilepath, "URL_KEYWORD_DIRIDS");
        
    
	public static String getHttpResponse(String url) {
		
		String result = "";
		HttpClient httpclient = getHttpClient();   
        try {  
            
            HttpGet httpget = new HttpGet(url);  
            
            HttpResponse response = httpclient.execute(httpget);  
                
                int code = response.getStatusLine().getStatusCode();
                
                logger.info("HTTPSTATUS: "+ code);  
                if (response.getStatusLine().getStatusCode() == 200) {  
                    /*读返回数据*/  
                    result = EntityUtils.toString(response  
                            .getEntity()); 
                }
                Thread.sleep(100);
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(Exception e){
        	e.printStackTrace();
        }
        
		return result.length()>0?result.toString():"{\"message\":\"error\",\"errorCode\":\"-1\"}";
	}
	
	
    
	private static ThreadSafeClientConnManager cm = null;  
    static {  
        SchemeRegistry schemeRegistry = new SchemeRegistry();  
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory  
                .getSocketFactory()));  
  
        cm = new ThreadSafeClientConnManager(schemeRegistry);  
        try {  
            int maxTotal = 40;  
            cm.setMaxTotal(maxTotal);  
        } catch (NumberFormatException e) {  
            logger.error(  
                    "Key[httpclient.max_total] Not Found in systemConfig.properties",  
                    e);  
        }  
        
        try {  
            int defaultMaxConnection = 20;  
            cm.setDefaultMaxPerRoute(defaultMaxConnection);  
        } catch (NumberFormatException e) {  
            logger.error(  
                    "Key[httpclient.default_max_connection] Not Found in systemConfig.properties",  
                    e);  
        }  
    }  
  
    public static HttpClient getHttpClient() {  
        HttpParams params = new BasicHttpParams();  
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,  
                HttpVersion.HTTP_1_1);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
        return new DefaultHttpClient(cm, params);
    }
}
