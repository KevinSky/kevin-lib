package kevin.lib.pool.thrift;

/**
 * 用于判断池中连接(TSocket)是否可用的接口。
 * @author hefeng
 *
 * @param <T> 被检测的对象。
 */
public interface ObjectValidator<T> {
    public boolean isValid(T object);
}
