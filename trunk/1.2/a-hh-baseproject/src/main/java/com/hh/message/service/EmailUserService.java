package com.hh.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.message.bean.SysEmail;
import com.hh.message.bean.SysEmailUser;
import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.ThreadUtil;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class EmailUserService extends BaseService<SysEmailUser> {

	@Autowired
	private SysMessageService sysMessageService;
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	public void addUserByMail(SysEmail entity) {
		List<String> ids = Convert.strToList(entity.getUsers());
		List<String> names = Convert.strToList(entity.getUserNames());
		if (ids.size() != names.size()) {
			throw new MessageException("接收人数据异常！");
		}
		for (int i = 0; i < ids.size(); i++) {
			SysEmailUser sysEmailUser = new SysEmailUser();
			sysEmailUser.setEmailId(entity.getId());
			sysEmailUser.setUserId(ids.get(i));
			sysEmailUser.setUserName(names.get(i));
			dao.createEntity(sysEmailUser);
//			UsUser user = loginUserUtilService.findLoginUser();
//			String content = user.getText()+"向您发送了一封邮件<br>"+entity.getTitle();
//			SysMessage message = sysMessageService.findMessage(11, content, sysEmailUser.getUserId(), sysEmailUser.getUserName(), "");
//
//			
//			message.setToObjectId(toObjectId);
//			message.setToObjectName(toObjectName);
//			message.setToObjectHeadpic(toObjectHeadpic);
//			
//			ThreadUtil.getThreadPool().execute(new MessageThread(message, 0));
		}
	}

}
