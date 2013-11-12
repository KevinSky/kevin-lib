package kevin.lib.util.http;

public class HttpClientConfig {

    private int maxTotalConnections = 360;
    private int maxConnectionsPerHost = 120;
    private int connectionTimeout = 10000;
    private int socketTimeOut = 5000;
    private int connectionManagerTimeout = 10000;
    private boolean staleCheckingEnabled = true;

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * 连接建立超时时间
     * @param connectionTimeout
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    /**
     * 设置请求超时时间，单位是ms
     * @param socketTimeOut
     */
    public void setSocketTimeOut(int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getConnectionManagerTimeout() {
        return connectionManagerTimeout;
    }

    public void setConnectionManagerTimeout(int connectionManagerTimeout) {
        this.connectionManagerTimeout = connectionManagerTimeout;
    }

    public boolean isStaleCheckingEnabled() {
        return staleCheckingEnabled;
    }

    public void setStaleCheckingEnabled(boolean staleCheckingEnabled) {
        this.staleCheckingEnabled = staleCheckingEnabled;
    }
}