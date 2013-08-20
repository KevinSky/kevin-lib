package kevin.lib.performance;

public interface PerformPool {

	/** 
	 * 拿到下一个任务任务
	 * @return perform 下一个执行对象 若为null，表示已经无任务
	 */
	public Perform getNext();
}
