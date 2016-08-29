package com.hh.message.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hh.message.bean.SysEmail;
import com.hh.message.bean.SysEmailUser;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;

@Service
public class EmailUserService extends BaseService<SysEmailUser>   {

	public void addUserByMail(SysEmail entity) {
		List<String> ids = Convert.strToList(entity.getUsers());
		List<String> names = Convert.strToList(entity.getUserNames());
		if (ids.size()!=names.size()) {
			throw new MessageException("接收人数据异常！");
		}
		for (int i=0;i<  ids.size();i++) {
			SysEmailUser sysEmailUser = new SysEmailUser();
			sysEmailUser.setEmailId(entity.getId());
			sysEmailUser.setUserId(ids.get(i));
			sysEmailUser.setUserName(names.get(i));
			dao.createEntity(sysEmailUser);
		}
	}



}
