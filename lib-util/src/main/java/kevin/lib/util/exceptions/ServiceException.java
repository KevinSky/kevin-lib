package kevin.lib.util.exceptions;

/**
 * 调用服务出错,一般都是内部服务有问题，如调用Thrift获取数据超时，读db不成功
 * 出现此错误表示数据已无法正确获取，建议后面的操作也不要进行，而直接返回错误给用户
 * 如调用Thrift获取其他系统数据超时的时候，关键数据没取到，后面的操作继续下去也无意义
 * 这种错误可内部消化，也可返回给调用者以告知其原因，视具体场景而定
 * 
 * @author huangjinjie@yy.com
 * 
 */
public class ServiceException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

}
