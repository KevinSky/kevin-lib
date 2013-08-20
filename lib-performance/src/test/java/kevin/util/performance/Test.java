package kevin.util.performance;

import kevin.lib.performance.Perform;
import kevin.lib.performance.PerformFailure;
import kevin.lib.performance.PerformanceTest;

public class Test implements Perform{

	@Override
	public void perform() throws PerformFailure {
		System.out.println("go");
	}
	public static void main(String[] args) {
		Test t = new Test();
		PerformanceTest t1 = new PerformanceTest(t, 5, 1, 5);
		t1.start();
	}
	
}

