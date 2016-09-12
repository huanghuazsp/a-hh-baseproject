package com.hh.message.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.ThreadUtil;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserCyLxr;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UsUserCylxrService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SysMessageService extends BaseService<SysMessage> implements LoadDataTime {
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private UserService userService;

	@Autowired
	private UsUserCylxrService usUserCylxrService;

	@Override
	public PagingData<SysMessage> queryPagingData(SysMessage entity, PageRange pageRange) {
		UsUser user = loginUserUtilService.findLoginUser();
		ParamInf paramInf = findParamList(entity.getSendObjectType(), entity.getToObjectId(), user);
		paramInf.like("content", entity.getContent());
		return this.queryPagingData(pageRange, paramInf);
	}

	@Transactional
	public SysMessage save(SysMessage entity, String leixing) throws MessageException {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		if (Check.isEmpty(entity.getSendUserId())) {
			entity.setSendUserId(hhXtYh.getId());
		}
		if (Check.isEmpty(hhXtYh.getText())) {
			entity.setSendUserName(hhXtYh.getText());
		}

		if (Check.isEmpty(entity.getId())) {
			dao.createEntity(entity);
		} else {
			dao.updateEntity(entity);
		}
		return entity;
	}

	@Transactional
	public void save(SysMessage sysMessage, int addCylxr) {
		this.save(sysMessage);
		if (addCylxr == 1) {
			UsUserCyLxr usUserCyLxr = new UsUserCyLxr();

			usUserCyLxr.setVcreate(sysMessage.getVcreate());
			usUserCyLxr.setVupdate(sysMessage.getVupdate());
			usUserCyLxr.setVorgid(sysMessage.getVorgid());
			usUserCyLxr.setVdeptid(sysMessage.getVdeptid());
			usUserCyLxr.setVjobid(sysMessage.getVjobid());

			usUserCyLxr.setYhId(sysMessage.getSendUserId());
			usUserCyLxr.setCylxrId(sysMessage.getToObjectId());
			usUserCyLxr.setCylxrName(sysMessage.getToObjectName());
			usUserCyLxr.setHeadpic(sysMessage.getToObjectHeadpic());
			usUserCyLxr.setType(sysMessage.getSendObjectType());
			userService.addCylxrObject(usUserCyLxr);
		}
	}

	public Map<String, Object> load() {
		UsUser user = loginUserUtilService.findLoginUser();
		String userId = user.getId();
		String orgId = user.getOrgId();
		String deptId = user.getDeptId();
		String sysGroupIds = user.getSysGroupIds();
		String usGroupIds = user.getUsGroupIds();
		List<String> toObjectIdList = new ArrayList<String>();
		toObjectIdList.add(userId);
		if (Check.isNoEmpty(orgId)) {
			toObjectIdList.add(orgId);
		}
		if (Check.isNoEmpty(deptId)) {
			toObjectIdList.add(deptId);
		}

		String whereSql = " toObjectId in (:toObjectIdList) ";

		if (Check.isNoEmpty(sysGroupIds)) {
			toObjectIdList.addAll(Convert.strToList(sysGroupIds));
		}
		if (Check.isNoEmpty(usGroupIds)) {
			toObjectIdList.addAll(Convert.strToList(usGroupIds));
		}
		Map<String, Object> mapparam = new HashMap<String, Object>();
		mapparam.put("toObjectIdList", toObjectIdList);
		mapparam.put("userId", userId);
		mapparam.put("likeUserId", "%" + userId + "%");

		List<Map<String, Object>> sysMessagesList = this.getDao().queryList(
				"select sendObjectType as sendObjectType,objectId as id,objectName as text,objectHeadpic as headpic,count(id) as count  from "
						+ SysMessage.class.getName() + " where (" + whereSql
						+ ") and sendUserId!=:userId and readUserId not like :likeUserId GROUP BY sendObjectType,objectId,objectName,objectHeadpic",
				mapparam);

		Map<String, Map<String, Object>> mapMap = Convert.listMapToMap(sysMessagesList, "id");

		List<UsUserCyLxr> usUserCyLxrList = usUserCylxrService.queryListByProperty("yhId", userId);

		Map<String, UsUserCyLxr> usUserCyLxrMap = Convert.listToMap(usUserCyLxrList, "getCylxrId");

		int shouCount = 0;
		for (Map<String, Object> map2 : sysMessagesList) {
			shouCount += Convert.toInt(map2.get("count"));
			UsUserCyLxr usUserCyLxr = usUserCyLxrMap.get(Convert.toString(map2.get("id")));
			if (usUserCyLxr == null) {
				usUserCyLxr = new UsUserCyLxr();
				usUserCyLxr.setYhId(userId);
				usUserCyLxr.setCylxrId(Convert.toString(map2.get("id")));
				usUserCyLxr.setCylxrName(Convert.toString(map2.get("text")));
				usUserCyLxr.setHeadpic(Convert.toString(map2.get("headpic")));
				usUserCyLxr.setType(Convert.toInt(map2.get("sendObjectType")));
				userService.addCylxrObject(usUserCyLxr);
			}
		}
		for (UsUserCyLxr usUserCyLxr : usUserCyLxrList) {
			if (mapMap.get(usUserCyLxr.getCylxrId()) == null) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("id", usUserCyLxr.getCylxrId());
				map2.put("text", usUserCyLxr.getCylxrName());
				map2.put("headpic", usUserCyLxr.getHeadpic());
				map2.put("sendObjectType", usUserCyLxr.getType());
				sysMessagesList.add(map2);
			}
		}

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd101");
		map2.put("text", "消息提醒");
		map2.put("vsj", "jsp-message-message-messagelistview");
		map2.put("allMessage", sysMessagesList);
		return map2;
	}

	@Transactional
	public SysMessage findObjectById(String id) {
		String userId = loginUserUtilService.findUserId();
		SysMessage sysEmail = super.findObjectById(id);
		if (Convert.toString(sysEmail.getReadUserId()).indexOf(userId) == -1) {
			sysEmail.setReadUserId(sysEmail.getReadUserId() + "," + userId);
			dao.updateEntity(sysEmail);
		}
		return sysEmail;
	}

	public List<SysMessage> queryMyPagingDataBySendObjectId(SysMessage object, PageRange pageRange) {
		UsUser user = loginUserUtilService.findLoginUser();
		ParamInf paramInf = findParamList(object.getSendObjectType(), object.getToObjectId(), user);

		String userId = user.getId();
		String orgId = user.getOrgId();
		String deptId = user.getDeptId();
		String sysGroupIds = user.getSysGroupIds();
		String usGroupIds = user.getUsGroupIds();
		List<String> toObjectIdList = new ArrayList<String>();
		toObjectIdList.add(userId);
		if (Check.isNoEmpty(orgId)) {
			toObjectIdList.add(orgId);
		}
		if (Check.isNoEmpty(deptId)) {
			toObjectIdList.add(deptId);
		}
		String whereSql = " toObjectId in (:toObjectIdList) ";
		if (Check.isNoEmpty(sysGroupIds)) {
			toObjectIdList.addAll(Convert.strToList(sysGroupIds));
		}
		if (Check.isNoEmpty(usGroupIds)) {
			toObjectIdList.addAll(Convert.strToList(usGroupIds));
		}

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", user.getId());
		paramsMap.put("orgId", Convert.toString(user.getOrgId()));
		paramsMap.put("deptId", Convert.toString(user.getDeptId()));
		paramsMap.put("likeUserId", "%" + userId + "%");
		paramsMap.put("toObjectIdList", toObjectIdList);

		this.getDao()
				.updateEntity("update " + SysMessage.class.getName()
						+ " set readUserId = CONCAT(readUserId,:userId,',') where (" + whereSql
						+ ") and readUserId not like :likeUserId ", paramsMap);

		return queryList(paramInf, pageRange);
	}

	private ParamInf findParamList(int sendObjectType, String toObjectId, UsUser user) {
		ParamInf paramInf = ParamFactory.getParamHb();
		if (sendObjectType == 0) {
			ParamInf paramInf2 = ParamFactory.getParamHb();
			paramInf2.is("sendUserId", user.getId());
			paramInf2.is("toObjectId", toObjectId);

			ParamInf paramInf3 = ParamFactory.getParamHb();
			paramInf3.is("sendUserId", toObjectId);
			paramInf3.is("toObjectId", user.getId());

			paramInf.or(paramInf2, paramInf3);
		} else if (sendObjectType == 11 || sendObjectType == 12) {
			paramInf.is("toObjectId", user.getId());
		} else {
			paramInf.is("toObjectId", toObjectId);
		}
		paramInf.is("sendObjectType", sendObjectType);
		return paramInf;
	}

	public SysMessage saveMessage(Map<String, Object> paramMap) {

		SysMessage message = findMessage(Convert.toInt(paramMap.get("sendObjectType")),
				Convert.toString(paramMap.get("message")), Convert.toString(paramMap.get("toObjectId")),
				Convert.toString(paramMap.get("toObjectName")), Convert.toString(paramMap.get("toObjectHeadpic")));

		ThreadUtil.getThreadPool().execute(new MessageThread(message, Convert.toInt(paramMap.get("addCylxr"))));
		return message;
	}

	public SysMessage findMessage(int sendObjectType, String content, String toObjectId, String toObjectName,
			String toObjectHeadpic) {
		UsUser user = loginUserUtilService.findLoginUser();
		SysMessage message = new SysMessage();
		String currUserId = user.getId();
		message.setVcreate(currUserId);
		message.setVupdate(currUserId);
		message.setVorgid(user.getOrgId());
		message.setVdeptid(user.getDeptId());
		message.setVjobid(user.getJobId());
		message.setVcreateName(user.getText());

		message.setSendUserId(currUserId);
		message.setSendUserName(user.getText());
		message.setSendHeadpic(user.getHeadpic());

		message.setSendUserId(currUserId);
		message.setSendUserName(user.getText());
		message.setSendHeadpic(user.getHeadpic());

		message.setContent(content);
		message.setToObjectId(toObjectId);
		message.setToObjectName(toObjectName);
		message.setToObjectHeadpic(toObjectHeadpic);
		message.setSendObjectType(sendObjectType);

		if (message.getSendObjectType() == 0) {
			message.setObjectId(message.getSendUserId());
			message.setObjectName(message.getSendUserName());
			message.setObjectHeadpic(message.getSendHeadpic());
		} else {
			message.setObjectId(message.getToObjectId());
			message.setObjectName(message.getToObjectName());
			message.setObjectHeadpic(message.getToObjectHeadpic());
		}

		return message;
	}

}
