package com.hh.message.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.message.bean.SysMessage;
import com.hh.message.service.MessageService;
import com.hh.message.service.SysMessageService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.BeanUtils;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.request.Request;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.util.app.LoginUser;

@SuppressWarnings("serial")
public class ActionSysMessage extends BaseServiceAction<SysMessage> {
	@Autowired
	private SysMessageService service;

	public static Map<String, List<Map<String, Object>>> messageMap = new HashMap<String, List<Map<String, Object>>>();

	private int addCylxr = 0;

	@Autowired
	private LoginUserUtilService userService;

	public BaseService<SysMessage> getService() {
		return service;
	}

	public Object queryMyPagingDataBySendObjectId() {
		return service.queryMyPagingDataBySendObjectId(object,
				this.getPageRange());
	}

	public Object poll() {
		String userId = userService.findUserId();
		for (int i = 0; i < 60; i++) {
			try {
				if (messageMap.get(userId) != null
						&& messageMap.get(userId).size() > 0) {
					List<Map<String, Object>> returnMap = messageMap
							.get(userId);
					messageMap.remove(userId);
					return returnMap;
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", "test");
		return map;
	}

	public Object sendMessage() {
		Map<String, Object> paramMap = Request.getParamMapByRequest(request);

		paramMap.put("date", DateFormat.getDate("yyyy-MM-dd HH::mm:ss"));
		paramMap.put("type", "you");
		SysMessage sysMessage = service.saveMessage(paramMap);
		paramMap.put("objectId", sysMessage.getObjectId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", paramMap);

		String toObjectId = sysMessage.getToObjectId();
		String sendUserId = sysMessage.getSendUserId();
		if (sysMessage.getSendObjectType() == 0) {
			UsUser user = LoginUser.loginUserMap.get(toObjectId);
			sendMessage(map, toObjectId, sendUserId, user);
		} else {
			for (String userId : LoginUser.getLoginUserId()) {
				UsUser user = LoginUser.loginUserMap.get(userId);
				sendMessage(map, toObjectId, sendUserId, user);
			}
		}
		return null;
	}

	private void sendMessage(Map<String, Object> map, String toObjectId,
			String sendUserId, UsUser user) {
		if (user != null) {
			if ((user.getId().equals(toObjectId)
					|| (Convert.toString(user.getOrgId())).equals(toObjectId)
					|| (Convert.toString(user.getDeptId())).equals(toObjectId)
					|| (Convert.toString(user.getUsGroupIds()))
							.contains(toObjectId) || (Convert.toString(user
					.getSysGroupIds())).contains(toObjectId))
					&& !sendUserId.equals(user.getId())) {

				List<Map<String, Object>> returnMaps = messageMap.get(user
						.getId());
				if (returnMaps == null) {
					messageMap.put(user.getId(),
							new ArrayList<Map<String, Object>>());
				}
				messageMap.get(user.getId()).add(map);
			}
		}
	}

	public int getAddCylxr() {
		return addCylxr;
	}

	public void setAddCylxr(int addCylxr) {
		this.addCylxr = addCylxr;
	}

}
