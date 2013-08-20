package kevin.util.redis.test;

import kevin.lib.redis.client.MasterSlaveRedis;
import kevin.lib.redis.client.RedisPoolConfig;
import kevin.util.StringUtil;
import kevin.util.performance.Perform;
import kevin.util.performance.PerformFailure;
import kevin.util.performance.PerformanceTest;

public class Performance implements Perform{
	MasterSlaveRedis redis;

	public Performance(MasterSlaveRedis redis) {
		this.redis = redis;
	}
	
	@Override
	public void perform() throws PerformFailure {
		String key = StringUtil.genRandomString(20, 30);
		String value = StringUtil.genRandomString(10, 30);
		redis.set(key, value);
		String v = redis.get(key);
		if(!value.equals(v))
			throw new PerformFailure("值不相等,key:"+key+",value:"+value+",remoteValue:"+v);
	}
	
	public static void main(String[] args) throws PerformFailure {
		
		RedisPoolConfig masterConfig = new RedisPoolConfig("ubuntu");
		RedisPoolConfig slaveConfig = new RedisPoolConfig("ubuntu",7379);
		
		MasterSlaveRedis redis = new MasterSlaveRedis(masterConfig,slaveConfig);
		Performance p = new Performance(redis);
		
		int n = 100000;
		int c = 16;
		long lessTime = 2;
		PerformanceTest test = new PerformanceTest(p, n, c, lessTime);
		test.start();
	}

	
	
}
