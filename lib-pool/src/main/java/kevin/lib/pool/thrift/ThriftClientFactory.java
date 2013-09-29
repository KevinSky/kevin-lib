package kevin.lib.pool.thrift;

public interface ThriftClientFactory<T> {

    ThriftClientWrap<T> getClientWrap() throws Exception;

}
