package kevin.lib.pool.thrift;

import java.lang.reflect.Constructor;

import kevin.lib.pool.ConnectionProvider;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
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
public class NonblockingClientFactory<T> implements ThriftClientFactory<T> {
    private static final Logger log = LoggerFactory.getLogger(NonblockingClientFactory.class);

    /**
     * Thrift的生成代码的Client的构造函数。构造函数必须支持一个参数Client(TProtocol tprotocol)。
     */
    private Constructor<? extends T> constructor;
    private ConnectionProvider<TSocket> connectionProvider;

    public NonblockingClientFactory(ConnectionProvider<TSocket> connectionProvider, String clientClassName)
            throws Exception {
        this(connectionProvider, (Class<T>) Class.forName(clientClassName));
    }

    public NonblockingClientFactory(ConnectionProvider<TSocket> connectionProvider, Class<? extends T> clientClass)
            throws Exception {
        this.connectionProvider = connectionProvider;
        this.constructor = clientClass.getConstructor(TProtocol.class);
    }

    @Override
    public ThriftClientWrap<T> getClientWrap() throws Exception {
        TSocket socket = connectionProvider.getConnection();
        TFramedTransport frame = new TFramedTransport(socket);
        TBinaryProtocol protocol = new TBinaryProtocol(frame);
        Object client = constructor.newInstance(protocol);
        return new ThriftClientWrap(connectionProvider, socket, client);
    }
}
