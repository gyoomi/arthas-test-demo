package com.gyoomi.arthas.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2020/3/1 21:40
 */
@Service
public class UserService
{

	private static final Logger lg = LoggerFactory.getLogger(ArthasTest.class);

	public void get(Integer uid) throws Exception
	{
		check(uid);
		service(uid);
		redis(uid);
		mysql(uid);
	}

	public void service(Integer uid) throws Exception
	{
		int count = 0;
		for (int i = 0; i < 10; i++)
		{
			count += i;
		}
		lg.info("service  end {}", count);
	}

	public void redis(Integer uid) throws Exception
	{
		int count = 0;
		for (int i = 0; i < 10000; i++)
		{
			count += i;
		}
		lg.info("redis  end {}", count);
	}

	public void mysql(Integer uid) throws Exception
	{
		long count = 0;
		for (int i = 0; i < 10000000; i++)
		{
			count += i;
		}
		lg.info("mysql end {}", count);
	}

	public boolean check(Integer uid) throws Exception
	{
		if (uid == null || uid < 0)
		{
			lg.error("uid不正确，uid:{}", uid);
			throw new Exception("uid不正确");
		}
		return true;
	}

}
