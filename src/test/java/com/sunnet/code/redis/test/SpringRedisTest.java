package com.sunnet.code.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sunnet.framework.redis.service.RedisDataSource;
import com.sunnet.framework.redis.web.RedisClientTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/*/spring-*.xml"})
public class SpringRedisTest
{
	
	@Autowired
    private RedisClientTemplate redisClientTemplate;
    
	@Test
	public void findcomplainAllTest()
	{
		redisClientTemplate.set("huang", "junquan");
		System.out.println("结束");
	}
	
	@Test
	public void findcomplaintRecordAllTest()
	{
		
	}
	
	
}
