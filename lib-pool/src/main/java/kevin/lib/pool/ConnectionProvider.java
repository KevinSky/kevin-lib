package kevin.lib.pool;

import kevin.lib.pool.exception.GetConnectionException;

public interface ConnectionProvider<T> {

    T getConnection() throws GetConnectionException;

    void returnCon(T t);

}
