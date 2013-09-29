package kevin.lib.pool.thrift;

import java.lang.reflect.Constructor;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * thrift TThreadPoolServer's pool
 * 
 * @author kevin
 * 
 */
public class ThreadClientFactory implements PoolableObjectFactory {
	private static final Logger log = LoggerFactory
			.getLogger(ThreadClientFactory.class);

	/** thrift client class */
	private String clientClazz;
	/** 服务的地址 */
	private String server;
	/** 服务的端口 */
	private int serverPort;
	/** 超时 */
	private int timeout;

	@Override
	public Object makeObject() throws Exception {
		TSocket socket = new TSocket(server, serverPort, timeout);
		TBinaryProtocol protocol = new TBinaryProtocol(socket);
		Class[] params = new Class[1];
		params[0] = org.apache.thrift.protocol.TProtocol.class;
		Constructor constructor = Class.forName(clientClazz).getConstructor(params);
		Object[] instances = new Object[1];
		instances[0] = protocol;
		Object client = constructor.newInstance(instances);
		socket.open();
		return client;
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validateObject(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void passivateObject(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

}
