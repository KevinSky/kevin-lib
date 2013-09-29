package kevin.lib.pool;

import kevin.lib.pool.exception.GetConnectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonPool<T> extends GenericConnectionProvider implements
		ConnectionProvider<T> {
	private static final Logger log = LoggerFactory.getLogger(CommonPool.class);

	@Override
	public T getConnection() throws GetConnectionException {
		try {
			return (T) objectPool.borrowObject();
		} catch (Exception e) {
			log.error("getConnection() error", e);
			throw new GetConnectionException("getConnection() error", e);
		}
	}

	@Override
	public void returnCon(T t) {
		try {
			objectPool.returnObject(t);
		} catch (Exception e) {
			log.error("returnCon() error", e);
		}
	}
}
