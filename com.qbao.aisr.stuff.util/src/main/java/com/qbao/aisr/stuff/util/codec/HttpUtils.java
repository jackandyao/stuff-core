package com.qbao.aisr.stuff.util.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author by zhangchanghong on 15/12/6.
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static <T> List<T> get(String url, Class<T> clazz) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try {

            httpGet.setURI(new URI(httpGet.getURI().toString()));
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            if (entity != null) {
                entity.consumeContent();
            }
            return JSON.parseArray(body, clazz);
        } catch (Exception e) {
            log.error("httputils get to list error : {}", e.getMessage());
            throw new Exception(e);
        } finally {
            httpGet.abort();
        }
    }

    public static String post(String url, List<NameValuePair> params) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            return body;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception(e);
        } finally {
            post.abort();
        }
    }

    public static String postByBody(String url, JSONObject json) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-Type", "application/json");
            post.setURI(new URI(post.getURI().toString()));
            StringEntity se = new StringEntity(json.toString(), "utf-8");
            post.setEntity(se);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity, "utf-8");
            return str;
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            post.abort();
        }
    }

    public static String postByJson(String url, String data) {
        String body = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(data, "utf-8");
        entity.setContentType("application/json");
//        entity.setContentEncoding("utf-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        try {
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity respEntity = response.getEntity();
                body = EntityUtils.toString(respEntity);
                if (respEntity != null) {
                    respEntity.consumeContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public static String get(String url) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try {
            httpGet.setURI(new URI(httpGet.getURI().toString()));
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            if (null != entity) {
                entity.consumeContent();
            }
            return body;
        } catch (IOException e) {
            log.error("httputils get to string error : {}", e.getMessage());
            throw new IOException(e);
        } catch (URISyntaxException e) {
            log.error("httputils URISyntaxException error : {}", e.getMessage());
            throw new IOException(e);
        } finally {
            httpGet.abort();
        }
    }

    public static String doPost(String reqUrl, Map<String, Object> parameters) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        return post(reqUrl, params);
    }

    public static String doPostStr(String reqUrl, Map<String, String> parameters) throws Exception {
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return post(reqUrl, params);
    }
}
