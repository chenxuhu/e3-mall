package cn.e3mall.jedis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.jedis.JedisClientCluster;
import cn.e3mall.common.jedis.JedisClientPool;

public class JedisClientTest {
	//@Test
	public void testJedis(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClientPool jedisClientPool = applicationContext.getBean(JedisClientPool.class);
		jedisClientPool.set("mytest1", "陈许湖");
		String string = jedisClientPool.get("mytest1");
		System.out.println(string);
	}
	
	//@Test
	public void testJedisClientPool(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		jedisClient.set("chen", "xuhu");
		String string = jedisClient.get("chen");
		System.out.println(string);
	}

}
