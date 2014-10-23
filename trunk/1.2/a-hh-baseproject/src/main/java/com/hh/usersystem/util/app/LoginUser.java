package com.hh.usersystem.util.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.hh.usersystem.bean.usersystem.HhXtYh;

public class LoginUser {
	public static Map<String, HhXtYh> loginUserMap = new HashMap<String, HhXtYh>();
	public static Map<String, HttpSession> loginUserSession = new HashMap<String, HttpSession>();

	public static Set<String> getLoginUserId() {
		return loginUserMap.keySet();
	}

	public static void put(String key, HhXtYh value, HttpSession session) {
		loginUserMap.put(key, value);
		loginUserSession.put(key, session);
	}

	public static void remove(String key) {
		loginUserMap.remove(key);
		loginUserSession.remove(key);
	}

	public static int getLoginUserCount() {
		return loginUserMap.size();
	}

	public static List<HhXtYh> getLoginUserList() {
		List<HhXtYh> hhXtYhs = new ArrayList<HhXtYh>();
		Set<String> set = loginUserMap.keySet();
		for (String string : set) {
			hhXtYhs.add(loginUserMap.get(string));
		}
		return hhXtYhs;
	}
}
