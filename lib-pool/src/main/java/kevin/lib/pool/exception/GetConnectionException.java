package kevin.lib.pool.exception;

/**
 * 无取连接出错
 * 
 * @author kevin
 * 
 */
public class GetConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7848809291650780035L;

	public GetConnectionException() {
		super();
	}

	public GetConnectionException(String msg) {
		super(msg);
	}

	public GetConnectionException(String msg, Throwable e) {
		super(msg, e);
	}

}
