 package com.hh.oa.service.impl;
import java.util.List;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.ThreadUtil;
import com.hh.usersystem.bean.usersystem.UsUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.message.bean.SysMessage;
import com.hh.message.service.MessageThread;
import com.hh.message.service.SysMessageService;
import com.hh.oa.bean.OaMeetingApply;
import com.hh.oa.bean.OaMeetingApplyUser;
import com.hh.oa.bean.OaNoticeUser;

@Service
public class OaMeetingApplyUserService extends BaseService<OaMeetingApplyUser> {

	@Autowired
	private SysMessageService sysMessageService;
	
	public void addUserByMeetingApply(OaMeetingApply entity,
			List<UsUser> userList) {
		deleteByProperty("objectId", entity.getId());
		sysMessageService.deleteByProperty("params", entity.getId());
		for (UsUser usUser : userList) {
			OaMeetingApplyUser oaMeetingApplyUser = new OaMeetingApplyUser();
			oaMeetingApplyUser.setObjectId(entity.getId());
			oaMeetingApplyUser.setUserId(usUser.getId());
			oaMeetingApplyUser.setUserName(usUser.getText());
			save(oaMeetingApplyUser);
			SysMessage message = sysMessageService.findMessage(14,
					entity.getText(), oaMeetingApplyUser.getUserId(),
					oaMeetingApplyUser.getUserName(), "");
			message.setObjectId("meeting");
			message.setObjectName("会议");
			message.setParams(entity.getId());
			ThreadUtil.getThreadPool().execute(new MessageThread(message, 0));
		}
	}
}
 