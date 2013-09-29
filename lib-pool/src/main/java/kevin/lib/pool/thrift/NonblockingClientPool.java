package kevin.lib.pool.thrift;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * thrift NonblockingServer's pool
 * @author kevin
 *
 */
public class NonblockingClientPool extends ThriftClientPool {

	@Override
	public PoolableObjectFactory getPoolableObjectFactory() {
		return new NonblockingClientFactory(clientClazz, server, serverPort,
				timeout);
	}

}
