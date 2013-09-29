package kevin.lib.pool.thrift;

import kevin.lib.pool.CommonPool;

import org.apache.commons.pool.PoolableObjectFactory;

public abstract class ThriftClientPool extends CommonPool {

	/** thrift client class */
	protected String clientClazz;

	/** 服务的地址 */
	protected String server;
	/** 服务的端口 */
	protected int serverPort;
	/** 超时 */
	protected int timeout;

	public String getClientClazz() {
		return clientClazz;
	}

	public void setClientClazz(String clientClazz) {
		this.clientClazz = clientClazz;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public abstract PoolableObjectFactory getPoolableObjectFactory();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		PoolableObjectFactory poolableObjectFactory = getPoolableObjectFactory();
		setPoolableObjectFactory(poolableObjectFactory);
		super.afterPropertiesSet();
	}
}
