package kevin.lib.redis.client;

import kevin.lib.redis.config.RedisConfig;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 再封装一层，以防jedis升级，使原有接口不可用 不可用的接口在此加以实现，即可保持无缝升级
 * 
 * @author kevin
 * 
 */
public class RedisPoolConfig extends JedisPoolConfig {

	private String host;
	private int port;
	private int timeout;
	private String password;
	private int database;

	public RedisPoolConfig(String host) {
		this(host,RedisConfig.REDIS_DEFAULT_PORT);
	}

	public RedisPoolConfig(String host, int port) {
		this(host,port,RedisConfig.REDIS_DEFAULT_DATABASE);
	}

	public RedisPoolConfig(String host, int port, int database) {
		this(host,port,database,RedisConfig.REDIS_DEFAULT_TIMEOUT);
	}

	public RedisPoolConfig(String host, int port, int database, int timeout) {
		this(host,port,database,timeout,null);
	}

	public RedisPoolConfig(String host, int port, int database, int timeout,
			String password) {
		super();
		this.host = host;
		this.port = port;
		this.database = database;
		this.timeout = timeout;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public int getTimeout() {
		return timeout;
	}

	public String getPassword() {
		return password;
	}

	public int getDatabase() {
		return database;
	}
	
	

}
