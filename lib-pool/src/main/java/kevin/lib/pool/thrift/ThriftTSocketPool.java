package kevin.lib.pool.thrift;

import kevin.lib.pool.CommonPool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;

public class ThriftTSocketPool extends CommonPool<TSocket>{

	/** 服务的IP */
	private String server;
	/** 服务的端口 */
	private int serverPort;
	/** 超时设置 */
	private int timeout;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		PoolableObjectFactory poolableObjectFactory = new TSocketFactory(
				server, serverPort, timeout);
		setPoolableObjectFactory(poolableObjectFactory);
		super.afterPropertiesSet();
	}
}
