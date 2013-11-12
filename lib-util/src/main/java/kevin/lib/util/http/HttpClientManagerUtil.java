/*
 * Copyright (c) 2013 duowan.com. 
 * All Rights Reserved.
 * This program is the confidential and proprietary information of 
 * duowan. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with duowan.com.
 */
package kevin.lib.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangke
 * 
 */
public class HttpClientManagerUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientManagerUtil.class);
    private HttpClient httpClient;

    /**
     * 
     * @param maxTotalConnections HttpClient中最大连接数
     * @param maxConnectionsPerHost HttpClient中每个远程host最大连接数
     * @param connectionTimeout 建立http连接的超时时间
     * @param socketTimeOut socket读取的超时时间（0为无限）
     * @param connectionManagerTimeout 从连接池获取连接的超时时间
     * @param staleCheckingEnabled 是否检查旧连接是否可用，默认true
     */
    public HttpClientManagerUtil(HttpClientConfig httpClientConfig) {
        MultiThreadedHttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpClientManager.getParams();
        params.setStaleCheckingEnabled(httpClientConfig.isStaleCheckingEnabled());
        params.setMaxTotalConnections(httpClientConfig.getMaxTotalConnections());
        params.setDefaultMaxConnectionsPerHost(httpClientConfig.getMaxConnectionsPerHost());
        params.setConnectionTimeout(httpClientConfig.getConnectionTimeout());
        params.setSoTimeout(httpClientConfig.getSocketTimeOut());
        httpClient = new HttpClient(httpClientManager);
        HttpClientParams httpClientParams = httpClient.getParams();
        httpClientParams.setConnectionManagerTimeout(httpClientConfig.getConnectionManagerTimeout());
    }

    /**
     * 注意releaseConnection
     * 
     * @return 池化的httpClient
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 
     * @param method HttpMethod
     * @return the method's response code
     * @throws IOException
     * @throws HttpException
     */
    public int executeMethod(HttpMethod method) throws HttpException, IOException {
        return httpClient.executeMethod(method);
    }

    public String doGet(String url) throws HttpException, IOException, SocketTimeoutException {
        GetMethod get = new GetMethod(url);
        int statusCode = httpClient.executeMethod(get);
        if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_NOT_MODIFIED) {
            return get.getResponseBodyAsString();
        }
        log.error("get data from url: {} fail, statusCode: {}", new Object[] { url, statusCode });
        return null;
    }

    public InputStream getResponseStream(String url, int[] statusArray) throws HttpException, IOException,
            SocketTimeoutException {
        GetMethod get = new GetMethod(url);
        int statusCode = httpClient.executeMethod(get);
        if (isInStatusArray(statusCode, statusArray)) {
            return get.getResponseBodyAsStream();
        }
        log.error("get data from url: {} fail, statusCode: {}", new Object[] { url, statusCode });
        return null;
    }

    public boolean isGetOK(String url) throws HttpException, IOException, SocketTimeoutException {
        GetMethod get = new GetMethod(url);
        int statusCode = httpClient.executeMethod(get);
        if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_NOT_MODIFIED) {
            return true;
        }
        log.error("get data from url: {} fail, statusCode: {}", new Object[] { url, statusCode });
        return false;
    }

    public Map doGet2Map(String url) throws HttpException, IOException, SocketTimeoutException {
        GetMethod get = new GetMethod(url);
        httpClient.executeMethod(get);
        String responseBody = get.getResponseBodyAsString();
        log.info("requestUrl={} HttpRequest return:{}", url, responseBody);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper.readValue(responseBody, Map.class);
    }

    public String doPost(String url, Map<String, Object> parameters) throws HttpException, IOException,
            SocketTimeoutException {
        PostMethod post = new PostMethod(url);
        if (parameters != null) {
            Iterator<String> iterator = parameters.keySet().iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                post.addParameter(name, parameters.get(name).toString());
            }
        }
        httpClient.executeMethod(post);
        String responseBody = post.getResponseBodyAsString();
        log.info("requestUrl={} HttpRequest return:{}", url, responseBody);
        return responseBody;
    }

    public Map doPost2Map(String url) throws HttpException, IOException {
        return doPost2Map(url, null);
    }

    public Map doPost2Map(String url, Map<String, Object> parameters) throws HttpException, IOException,
            SocketTimeoutException {
        String responseBody = doPost(url, parameters);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper.readValue(responseBody, Map.class);
    }

    public <T> T getObjectByPost(String url, Class<T> clazz) throws HttpException, IOException, SocketTimeoutException {
        String responseBody = doPost(url, null);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return mapper.readValue(responseBody, clazz);
    }

    private boolean isInStatusArray(int status, int[] statusArray) {
        for (int i = 0; i < statusArray.length; i++) {
            if (status == statusArray[i]) {
                return true;
            }
        }
        return false;
    }
}
