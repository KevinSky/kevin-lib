package kevin.lib.performance;

/**
 * 测试者需要继承此方法，此方法即为被测试内容
 * @author kevin
 *
 */
public interface Perform {

	/**
	 * 需要测试的性能方法
	 * @throws PerformFailure 执行失败抛出此异常
	 */
	public void perform() throws PerformFailure;
}
