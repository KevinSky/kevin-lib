package kevin.lib.performance;

/**
 * 执行失败
 * @author kevin
 *
 */
public class PerformFailure extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4430156434707415258L;
	
	public PerformFailure(String msg){
		super(msg);
	}

}
