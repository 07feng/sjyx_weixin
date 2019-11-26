package com.sunnet.code.framework;


import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/*/spring-*.xml"})
public class EnvTest
{
	@Resource
	private ComboPooledDataSource dataSource;
	
	@Test
	public void dataSourceTest()
	{
		System.out.println(dataSource.getDataSourceName());
	}
}
