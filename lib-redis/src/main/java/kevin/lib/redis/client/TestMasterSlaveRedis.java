package kevin.lib.redis.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestMasterSlaveRedis {

	public static void main(String[] args) {
		RedisPoolConfig config = new RedisPoolConfig("192.168.137.11");
		MasterSlaveRedis redis = new MasterSlaveRedis(config);
		
//		redis.set("me", "黄锦捷");
//		System.out.println(redis.get("me"));
		
		JedisPool pool = redis.getMasterPool();
		Jedis j = pool.getResource();
		
		try {
		    List<String> testList = new ArrayList<String>();
		    List<Long> uidList = new ArrayList<Long>();
		    testList.add("name");
		    testList.add("hwhw");
		    uidList.add(1l);
		    uidList.add(2l);
            byte[][] keys = new byte[testList.size()][];
            byte[][] keysvalues = new byte[testList.size() * 2][];
            for (int i = 0; i < testList.size(); i++) {
                String value = testList.get(i);
                byte[] serialize = SerializationUtils.serialize(value);
                int index = i * 2;
                byte[] key = ("test_"+uidList.get(i)).getBytes();
                keys[i] = key;
                keysvalues[index] = key;
                keysvalues[index + 1] = serialize;
            }
            j.mset(keysvalues);
            
            keys = new byte[3][];
            keys[0] = ("test_"+uidList.get(0)).getBytes();
            keys[1] = ("test_3").getBytes();
            keys[2] = ("test_"+uidList.get(1)).getBytes();
            List<byte[]> result = j.mget(keys);
            System.out.println(result.size());
            System.out.println(result);
        } catch (Throwable e) {
            e.printStackTrace();
        } 
	}
}
