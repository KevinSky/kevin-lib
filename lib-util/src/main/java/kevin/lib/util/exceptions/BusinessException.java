package kevin.lib.util.exceptions;

/**
 * 业务或逻辑上的错误
 * 如参数不正确，如分页最多设置limit为50，请求却传入limit为60的情况
 * 这种错误可返回用调用者，以便使其知道错误原因
 * 
 * @author huangjinjie@yy.com
 * 
 */
public class BusinessException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 5986022058406379696L;
    private int code;
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }
}
