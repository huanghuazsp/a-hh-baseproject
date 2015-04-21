package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysShouEmail;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SysShouEmailService extends BaseService<SysShouEmail> implements
		LoadDataTime {
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private UserService userService;

	@Override
	public PagingData<SysShouEmail> queryPagingData(SysShouEmail entity,
			PageRange pageRange) {

		return dao.queryPagingData(
				this.getGenericType(0),
				ParamFactory.getParamHb()
						.is("shouUser", loginUserUtilService.findLoginUserId())
						.orderDesc("dcreate"), pageRange);
	}

	@Override
	@Transactional
	public SysShouEmail findObjectById(String id) {
		SysShouEmail shouEmail = super.findObjectById(id);
		String[] users = (shouEmail.getUsers() + "," + shouEmail.getSendUser())
				.split(",");

		List<HhXtYh> hhXtYhList = userService.queryListByIds(users);
		Map<String, HhXtYh> hhxtyhMap = Convert.listToMap(hhXtYhList);
		shouEmail.setSendUserName(hhxtyhMap.get(shouEmail.getSendUser())
				.getText());
		List<String> userList = Convert.strToList(shouEmail.getUsers());
		if (!userList.contains(shouEmail.getSendUser())) {
			hhxtyhMap.remove(shouEmail.getSendUser());
		}
		String userstr = Convert.mapToStr(hhxtyhMap, "getText");
		shouEmail.setUserNames(userstr);

		if (shouEmail.getRead() == 0) {
			dao.updateEntity("update " + SysShouEmail.class.getName()
					+ " set read=1 where id=?", new Object[] { id });
		}

		return shouEmail;
	}

	public Map<String, Object> load() {

		Map<String, Object> map = new HashMap<String, Object>();
		int shouCount = findCount(ParamFactory.getParamHb()
				.is("shouUser", loginUserUtilService.findLoginUserId())
				.is("read", 0));
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd107");
		map2.put("text", "收件箱");
		map2.put("vsj", "jsp-message-email-shouemaillist");
		map.put("email", map2);
		return map;
	}
}
