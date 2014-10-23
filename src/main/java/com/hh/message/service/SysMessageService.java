package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.hibernate.util.dto.PagingData;
import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SysMessageService extends BaseService<SysMessage> implements
		LoadDataTime {
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private UserService userService;

	@Override
	public SysMessage save(SysMessage entity) throws MessageException {
		entity.setCreateUserName(loginUserUtilService.findLoginUser().getText());
		return super.save(entity);
	}

	@Override
	public PagingData<SysMessage> queryPagingData(SysMessage entity,
			PageRange pageRange) {
		PagingData<SysMessage> pagingData = super.queryPagingData(entity,
				pageRange);
		List<SysMessage> messageList = pagingData.getItems();
		for (SysMessage sysMessage : messageList) {
			HhXtYh hhXtYh = userService.findObjectById_user(sysMessage
					.getShouUser());
			if (hhXtYh != null) {
				sysMessage.setShouUserName(hhXtYh.getText());
			}
		}
		return pagingData;
	}

	public PagingData<SysMessage> queryMyMessage(SysMessage entity,
			PageRange pageRange) {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		PagingData<SysMessage> pagingData = super.queryPagingData(entity,
				pageRange, new HQLParamList().addCondition(new HQLParamList()
						.addCondition(
								Restrictions.eq("shouUser", hhXtYh.getId()))
						.addCondition(Restrictions.eq("isRead", 0))));
//		List<SysMessage> messageList = pagingData.getItems();
//		for (SysMessage sysMessage : messageList) {
//			sysMessage.setShouUserName(hhXtYh.getText());
//		}
		return pagingData;
	}

	@Transactional
	public void deleteReadData() {
		dao.deleteEntity(SysMessage.class, "isRead", 1);
	}

	public void updateRead(String id) {
		dao.updateProperty(SysMessage.class, id, "isRead", 1);
	}

	public Map<String, Object> load() {
		Map<String, Object> map = new HashMap<String, Object>();
		int shouCount = findCount(new HQLParamList().addCondition(
				Restrictions.eq("shouUser",
						loginUserUtilService.findLoginUserId())).addCondition(
				Restrictions.eq("isRead", 0)));
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd101");
		map2.put("text", "消息提醒");
		map2.put("vsj", "jsp-message-message-messagelistview");
		map.put("message", map2);
		return map;
	}
}
