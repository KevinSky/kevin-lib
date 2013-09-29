package kevin.lib.pool.thrift;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * thrift TThreadPoolServer's pool
 * 
 * @author kevin
 * 
 */
public class ThreadClientPool extends ThriftClientPool {

	@Override
	public PoolableObjectFactory getPoolableObjectFactory() {
		return new NonblockingClientFactory(clientClazz, server, serverPort,
				timeout);
	}
}
