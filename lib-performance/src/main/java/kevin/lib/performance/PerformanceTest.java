package kevin.lib.performance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * testClass 与 perform 二选一
 * testClass 表示每次要new；perform则不需要
 * @author kevin
 *
 */
public class PerformanceTest implements PerformPool{
	private static Logger log = LoggerFactory.getLogger(PerformanceTest.class);
	
	private Class testClass;
	private Perform perform;
	private int n;
	private int c;
	private long lessTime;
	
	List<Future> futureList = null;
	ExecutorService executorService = null;
	private int count = 0;
	private byte[] countLock = new byte[0];
	private long totalTime = 0;
	private long totalSTime = 0;
	private long totalFTime = 0;
	private int totalSCount = 0;
	private int totalFCount = 0;
	
	private List<Map<String,Object>> result;
	private String report;
	
	
	public PerformanceTest(Class testClass,int n, int c, long lessTime) {
		this.testClass = testClass;
		this.n = n;
		this.c = c;
		this.lessTime = lessTime;
	}
	public PerformanceTest(Perform perform,int n, int c, long lessTime) {
		this.perform = perform;
		this.n = n;
		this.c = c;
		this.lessTime = lessTime;
	}
	
	
	
	public void start() {
		try {
			getPerform().perform();
		} catch(Exception e) {
			log.error("test perform error",e);
		}
		
		long startTime = System.currentTimeMillis();
		
		log.info("performance test is starting: -n:{} -c:{}", new Object[]{n,c});
		executorService = Executors.newFixedThreadPool(c);
		result = new ArrayList<Map<String,Object>>();
		futureList = new ArrayList<Future>();
		for(int i=1; i<=c; i++) {
			Performer performmer = new Performer(this);
			Future f = executorService.submit(performmer);
			futureList.add(f);
		}
		
		for(Future f : futureList){
			try {
				Map<String,Object> r = (Map<String,Object>) f.get();
				result.add(r);
			} catch (InterruptedException e) {
				log.error("get future result error",e);
			} catch (ExecutionException e) {
				log.error("get future result error",e);
			}
		}
		totalTime = System.currentTimeMillis() - startTime;
		
		genResult();
		log.info("{}",report);
		executorService.shutdown();
	}
	
	
	public void saveReport(String filePath) throws IOException {
		FileWriter out = new FileWriter(filePath); 
		out.write(report);
		out.flush();
		out.close();
	}
	
	public String getReport() {
		return report;
	}
	
	private void genResult() {
		String str = "";
		str += "the performance test result\r\nhere is thread's data\r\n";
		for(Map<String,Object> r : result) {
			str += genOneResult(r) + "\r\n";
		}
		str += "here is the total data\r\nrequest:"+n+" concurrent:"+c+" totalTime:"+(totalTime/1000)+"s("+totalTime+"ms)\tTPS:"+(1000*n/totalTime)+"\r\n";
		str += "total success:\tcount:" + totalSCount + "\ttime:" +(totalSTime/1000) + "s("+(totalSTime)+"ms)\t";
		if(totalSCount!=0)
			str += "\taverage:" + totalSTime/totalSCount+"ms";
		str += "\r\n";
		
		str += "total failure:\tcount:" + totalFCount + "\ttime:" +(totalFTime/1000) + "s("+(totalFTime)+"ms)\t";
		if(totalFCount!=0)
			str += "\tavarage:" + totalFTime/totalFCount+"ms";
		str += "\r\n";
		report = str;
	}

	
	private String genOneResult(Map<String,Object> result) {
		String name = (String) result.get(Performer.NAME);
		int count = (Integer) result.get(Performer.COUNT);
		List<Long> sTime = (List<Long>) result.get(Performer.SUCCESSTIME);
		List<Long> fTime = (List<Long>) result.get(Performer.FAILURETIME);
		Collections.sort(sTime);
		Collections.sort(fTime);
		
		String str = "thread:" + name + ", it run " + count + " times\r\n";
		long sTotal = 0;
		for(Long t : sTime) {
			sTotal += t;
		}
		long fTotal = 0;
		for(Long t: fTime) {
			fTotal += t;
		}
		if(sTime.size()>0) {
			str += "success:\ncount:"+sTime.size() + "\ttotalTime:" +(sTotal/1000) + "s("+sTotal+"ms)\taverage:" + (sTotal/sTime.size()) +"ms\t";
			str += "maxTime:"+sTime.get(sTime.size()-1) + "ms\tminTime:" + sTime.get(0) +"ms\r\n"; 
			int c = 0;
			String s = "";
			for(int i=sTime.size()-1; i>=0; i--) {
				long t = sTime.get(i);
				if(t>lessTime) {
					c++;
					s += t + "\t";
				} else break;
			}
			str += "more than " + lessTime + "ms:" + c +", "+ s + "\r\n";
		} else {
			str += "no success!!!\r\n";
		}
		
		
		if(fTime.size()>0) {
			str += "fialure:\ncount:"+fTime.size() + "\ttotalTime:" +(fTotal/1000) + "s("+fTotal+"ms)\taverage:" + (fTotal/fTime.size()) +"ms\t";
			str += "maxTime:"+fTime.get(fTime.size()-1) + "ms\tminTime:" + fTime.get(0) +"ms\r\n"; 
		} else {
			str += "no failure, good.\r\n";
		}
		
		totalSTime += sTotal;
		totalFTime += fTotal;
		totalSCount += sTime.size();
		totalFCount += fTime.size();
		
		return str;
	}
	
	private Perform getPerform() {
		Perform p = null;
		if(testClass!=null) {
			try {
				p = (Perform) testClass.newInstance();
			} catch (InstantiationException e) {
				log.error("new Perform error",e);
			} catch (IllegalAccessException e) {
				log.error("new Perform error",e);
			}
		} else {
			p = this.perform;
		}
		return p;
	}
	

	@Override
	public Perform getNext() {
		Perform p = null;
		synchronized (countLock) {
			if(count<n) {
				p = getPerform();
				count++;
			}
			if(n>10 && ((count%(n/10)) == 0) ) 
				log.info("Complete {} requests",count);
		}
		return p;
	}
	
}
