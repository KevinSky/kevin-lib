package kevin.lib.pool.thrift;

import java.lang.reflect.Constructor;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * thrift NonblockingServer's factory
 * 
 * @author kevin
 * 
 */
public class NonblockingClientFactory implements PoolableObjectFactory {
	private static final Logger log = LoggerFactory
			.getLogger(NonblockingClientFactory.class);

	/** thrift client class */
	private String clientClazz;
	private Object clientClassObj;
	/** 服务的地址 */
	private String server;
	/** 服务的端口 */
	private int serverPort;
	/** 超时 */
	private int timeout;

	public NonblockingClientFactory() {
	}

	public NonblockingClientFactory(String clientClazz, String server,
			int serverPort, int timeout) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		this.clientClazz = clientClazz;
		this.clientClassObj = Class.forName(
				clientClazz.substring(0, clientClazz.indexOf("$")))
				.newInstance();
		this.server = server;
		this.serverPort = serverPort;
	}

	@Override
	public Object makeObject() throws Exception {
		TFramedTransport frame = new TFramedTransport(new TSocket(server,
				serverPort, timeout));
		TBinaryProtocol protocol = new TBinaryProtocol(frame);
		Class[] params = new Class[1];
		params[0] = org.apache.thrift.protocol.TProtocol.class;
		Constructor constructor = Class.forName(clientClazz).getConstructor(
				params);
		Object[] instances = new Object[1];
		instances[0] = protocol;
		Object client = constructor.newInstance(instances);
		frame.open();
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
