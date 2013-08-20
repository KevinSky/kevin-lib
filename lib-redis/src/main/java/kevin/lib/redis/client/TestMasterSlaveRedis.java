package kevin.lib.redis.client;

public class TestMasterSlaveRedis {

	public static void main(String[] args) {
		RedisPoolConfig config = new RedisPoolConfig("ubuntu");
		MasterSlaveRedis redis = new MasterSlaveRedis(config);
		
		redis.set("me", "黄锦捷");
		System.out.println(redis.get("me"));
	}
}
