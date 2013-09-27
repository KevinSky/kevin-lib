/*
 * Copyright (c) 2013 duowan.com. 
 * All Rights Reserved.
 * This program is the confidential and proprietary information of 
 * duowan. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with duowan.com.
 */
package kevin.lib.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

/**
 * @author wangke
 * 
 */
public class HttpClientFactory {

    private HttpClient httpClient;

    /**
     * 
     * @param maxTotalConnections HttpClient中最大连接数
     * @param maxConnectionsPerHost HttpClient中每个远程host最大连接数
     * @param connectionTimeout 建立http连接的超时时间
     * @param socketTimeOut socket读取的超时时间（0为无限）
     * @param connectionManagerTimeout 从连接池获取连接的超时时间
     * @param staleCheckingEnabled 是否检查旧连接是否可用
     */
    public HttpClientFactory(int maxTotalConnections, int maxConnectionsPerHost, int connectionTimeout,
            int socketTimeOut, int connectionManagerTimeout, boolean staleCheckingEnabled) {
        MultiThreadedHttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpClientManager.getParams();
        params.setStaleCheckingEnabled(staleCheckingEnabled);
        params.setMaxTotalConnections(maxTotalConnections);// "http.max.total.connections"
        params.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);// "http.max.connection.perhost"
        params.setConnectionTimeout(connectionTimeout);// "http.connection.timeout"
        params.setSoTimeout(socketTimeOut);// "http.socket.timeout"
        httpClient = new HttpClient(httpClientManager);
        HttpClientParams httpClientParams = httpClient.getParams();
        httpClientParams.setConnectionManagerTimeout(connectionManagerTimeout);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

}
