package kevin.lib.pool.thrift;

/**
 * Config comsumed by TSocketFactory to create TSocket.
 * @author hefeng
 */
public class TSocketConfig {
    private String host;
    private int port;
    private int soTimeout = 0;
    
    public TSocketConfig(String host, int port) {
	this.host = host;
	this.port = port;
    }

    public TSocketConfig(String host, int port, int soTimeout) {
	super();
	this.host = host;
	this.port = port;
	this.soTimeout = soTimeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }
}
