package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysMessage;
import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SysMessageService extends BaseService<SysMessage> implements LoadDataTime {
	@Autowired
	private LoginUserUtilService loginUserUtilService;

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

	public Map<String, Object> load() {

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> mapparam = new HashMap<String, Object>();

		mapparam.put("userId", loginUserUtilService.findUserId());
		mapparam.put("orgId", loginUserUtilService.findOrgId());
		mapparam.put("deptId", loginUserUtilService.findDeptId());

		List<Map<String, Object>> sysMessagesList = this.getDao().queryList(
				"select sendObjectType as lx_,sendObjectId as id,sendObjectName as text,count(id) as count  from "
						+ SysMessage.class.getName()
						+ " where (userId=:userId or orgId=:orgId or deptId=:deptId) and sendUserId!=:userId and readUserId not like '%"
						+ loginUserUtilService.findUserId() + "%' GROUP BY sendObjectType,sendObjectId,sendObjectName",
				mapparam);
		int shouCount = 0;
		for (Map<String, Object> map2 : sysMessagesList) {
			shouCount += Convert.toInt(map2.get("count"));
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
		
//		ParamInf paramInf = ParamFactory.getParamHb();
		
		ParamInf paramInf2 = ParamFactory.getParamHb();
		paramInf2.or(ParamFactory.getParamHb().is("sendObjectId",object.getSendObjectId()),ParamFactory.getParamHb().is("sendObjectId", loginUserUtilService.findDeptId()));
		
//		ParamInf paramInf3 = ParamFactory.getParamHb();
//		paramInf3.or(ParamFactory.getParamHb().is("sendObjectId", loginUserUtilService.findOrgId()),ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findUserId()));
//		
//		paramInf.or(paramInf2,paramInf3);
		
		return queryList(paramInf2, pageRange);
	}
}
