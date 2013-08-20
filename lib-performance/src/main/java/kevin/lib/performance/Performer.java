package kevin.lib.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Performer implements Callable<Map<String,Object>> {
	public static final String FAILURETIME = "failuretime";
	public static final String COUNT = "count";
	public static final String SUCCESSTIME = "successtime";
	public static final String NAME = "name";

	private static Logger log = LoggerFactory.getLogger(Performer.class);

	private String name;
	
	private int count;
	private List<Long> successTime;
	private List<Long> failureTime;
	private PerformPool performPool;
	
	public Performer(PerformPool performPool) {
		this.performPool = performPool;
	}
	
	
	@Override
	public Map<String, Object> call() throws Exception {
		name = Thread.currentThread().getName();
		log.info("Task using {} starting...",name);
		
		successTime = new ArrayList<Long>();
		failureTime = new ArrayList<Long>();
		Perform p = performPool.getNext();
		while(p!=null) {
			_run(p);
			p = performPool.getNext();
		}
		log.info("Task using {} finished.",name);
		return getResult();
	}
	
	public void _run(Perform p) {
		count++;
		log.debug("{}: start run for {} times.",new Object[]{name,count});
		boolean success = true;
		long startTime = System.currentTimeMillis();
		try {
			p.perform();
		} catch (PerformFailure e) {
			success = false;
			log.error(name+": run error.",e);
		}
		long cost = System.currentTimeMillis() - startTime;
		
		if(success) {
			successTime.add(cost);
		} else {
			failureTime.add(cost);
		}
		log.debug("{}: the {} times is success:{} and cost time:{}",new Object[]{name,count,success,cost});
	}


	public Map<String,Object> getResult(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(SUCCESSTIME, successTime);
		result.put(FAILURETIME, failureTime);
		result.put(COUNT,count);
		result.put(NAME,name);
		return result;
	}
}
