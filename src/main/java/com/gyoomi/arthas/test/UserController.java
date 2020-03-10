package com.gyoomi.arthas.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2020/3/1 21:39
 */
@RestController
public class UserController
{

	@Autowired
	private UserService userService;

	@GetMapping(value = "/user")
	public HashMap<String, Object> getUser(Integer uid) throws Exception
	{
		// 模拟用户查询
		userService.get(uid);
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("uid", uid);
		hashMap.put("name", "name" + uid);
		return hashMap;
	}

}
