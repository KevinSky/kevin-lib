package kevin.lib.pool.thrift;

import kevin.lib.pool.ConnectionProvider;

import org.apache.thrift.transport.TSocket;

/**
 * 封装好，调用者就只需要关注业务，不需理pool
 * 用完需要调用close，以防连接耗光
 * @author huangjinjie@yy.com
 *
 * @param <T>
 */
public class ThriftClientWrap<T> {

    private ConnectionProvider<TSocket> connectionProvider;
    private TSocket socket;
    private T t;

    public ThriftClientWrap(ConnectionProvider<TSocket> connectionProvider, TSocket socket, T t) {
        this.connectionProvider = connectionProvider;
        this.socket = socket;
        this.t = t;
    }

    public void close() {
        connectionProvider.returnCon(socket);
    }

    public T getClient() {
        return t;
    }
}
