package kevin.lib.pool;

import kevin.lib.pool.exception.GetConnectionException;

public interface ConnectionProvider<T> {
	/**
	 * 取链接池中的一个链接
	 * 
	 * @return
	 */
	public T getConnection() throws GetConnectionException;

	/**
	 * 返回链接
	 * 
	 * @param socket
	 */
	public void returnCon(T t);
}
