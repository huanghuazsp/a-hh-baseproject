package com.hh.message.service;

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
		return dao.queryPagingData(this.getGenericType(0), ParamFactory.getParamHb().orderDesc("dcreate"), pageRange,
				new String[] { "id", "title", "dcreate", "type" });
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

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> mapparam = new HashMap<String, Object>();
		String userId = loginUserUtilService.findUserId();

		String orgId = loginUserUtilService.findOrgId();
		String deptId = loginUserUtilService.findDeptId();

		if (Check.isEmpty(orgId)) {
			orgId = "1111111";
		}
		if (Check.isEmpty(deptId)) {
			deptId = "1111111";
		}

		mapparam.put("userId", userId);
		mapparam.put("orgId", orgId);
		mapparam.put("deptId", deptId);

		List<Map<String, Object>> sysMessagesList = this.getDao().queryList(
				"select sendObjectType as sendObjectType,objectId as id,objectName as text,objectHeadpic as headpic,count(id) as count  from "
						+ SysMessage.class.getName()
						+ " where (toObjectId=:userId or toObjectId=:orgId or toObjectId=:deptId) and sendUserId!=:userId and readUserId not like '%"
						+ userId + "%' GROUP BY sendObjectType,objectId,objectName,objectHeadpic",
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

		// queryList(ParamFactory.getParamHb().is("userId",
		// loginUserUtilService.findUserId())
		// .is("deptId", loginUserUtilService.findDeptId()).is("orgId",
		// loginUserUtilService.findOrgId())
		// .nis("sendUserId", loginUserUtilService.findUserId())
		// .nolike("readUserId", loginUserUtilService.findUserId())
		// .nolike("deleteUserId", loginUserUtilService.findUserId()));
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd101");
		map2.put("text", "消息提醒");
		map2.put("vsj", "jsp-message-message-messagelistview");
		map2.put("message", "jsp-message-message-messagelistview");

		map.put("allMessage", sysMessagesList);
		map.put("message", map2);
		return map;
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

	public PagingData<SysMessage> queryDeletePage(SysMessage object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().like("deleteUserId", loginUserUtilService.findUserId()).orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	public PagingData<SysMessage> querySendPage(SysMessage object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findUserId())
						.nolike("deleteUserId", loginUserUtilService.findUserId()).orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	public PagingData<SysMessage> queryShouPage(SysMessage object, PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();
		PagingData<SysMessage> page = dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("userId", loginUserUtilService.findUserId())

						.is("deptId", loginUserUtilService.findDeptId()).is("orgId", loginUserUtilService.findOrgId())
						.nolike("deleteUserId", loginUserUtilService.findUserId())

						.orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type", "readUserId" });

		for (SysMessage sysEmail : page.getItems()) {
			if (Convert.toString(sysEmail.getReadUserId()).indexOf(userId) > -1) {
				sysEmail.setRead(1);
			}
		}

		return page;
	}

	@Override
	public void deleteByIds(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			List<SysMessage> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysMessage sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId()).indexOf(userId) == -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId() + "," + userId);
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Transactional
	public void recovery(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			List<SysMessage> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysMessage sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId()).indexOf(userId) > -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId().replaceAll("," + userId, ""));
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	public List<SysMessage> queryMyPagingDataBySendObjectId(SysMessage object, PageRange pageRange) {
		UsUser user = loginUserUtilService.findLoginUser();
		ParamInf paramInf = ParamFactory.getParamHb();
		if (object.getSendObjectType() == 0) {
			ParamInf paramInf2 = ParamFactory.getParamHb();
			paramInf2.is("sendUserId", user.getId());
			paramInf2.is("toObjectId", object.getToObjectId());
			
			
			ParamInf paramInf3 = ParamFactory.getParamHb();
			paramInf3.is("sendUserId", object.getToObjectId());
			paramInf3.is("toObjectId", user.getId());
			
			
			
			paramInf.or(paramInf2,paramInf3);
		}else{
			paramInf.is("toObjectId",  object.getToObjectId());
		}
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId",user.getId());
		paramsMap.put("orgId",Convert.toString(user.getOrgId()));
		paramsMap.put("deptId",Convert.toString(user.getDeptId()));
//		paramsMap.put("toObjectId", object.getToObjectId());

		this.getDao()
				.updateEntity("update " + SysMessage.class.getName()
						+ " set readUserId = CONCAT(readUserId,:userId,',') where (toObjectId=:userId or toObjectId=:orgId or toObjectId=:deptId) and readUserId not like '%"
						+ loginUserUtilService.findUserId() + "%'", paramsMap);


		return queryList(paramInf, pageRange);
	}
}
