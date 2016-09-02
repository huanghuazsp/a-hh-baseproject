package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysEmail;
import com.hh.message.bean.SysEmailUser;
import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.IFileOper;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class EmailService extends BaseService<SysEmail> implements LoadDataTime, IFileOper {

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private EmailUserService mailUserService;

	@Override
	public PagingData<SysEmail> queryPagingData(SysEmail entity, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0), ParamFactory.getParamHb().orderDesc("dcreate"), pageRange,
				new String[] { "id", "title", "dcreate", "type", "sendUserName", "userNames" });
	}

	@Transactional
	public SysEmail sendEmail(SysEmail entity) throws MessageException {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
		if (Check.isEmpty(entity.getSendUserId())) {
			entity.setSendUserId(hhXtYh.getId());
			entity.setSendUserName(hhXtYh.getText());
		}
		if (Check.isEmpty(entity.getId())) {
			dao.createEntity(entity);
			mailUserService.addUserByMail(entity);
		} else {
			dao.updateEntity(entity);
			mailUserService.deleteByProperty("emailId", entity.getId());
			mailUserService.addUserByMail(entity);
		}
		return entity;
	}

	public Map<String, Object> load() {
		return load2(loginUserUtilService.findUserId());
	}

	public Map<String, Object> count() {
		String userId = loginUserUtilService.findUserId();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		
		String sjhqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where a.type='yfs' and b.type=0 and a.id=b.emailId and b.userId=:userId";

		int sjCount = dao.findCount(sjhqlCount, paramsMap);

		int fsCount = findCount(ParamFactory.getParamHb().is("sendUserId", userId).is("meDelete", 0).is("type", "yfs"));

		int cgxCount = findCount(
				ParamFactory.getParamHb().is("sendUserId", userId).is("meDelete", 0).is("type", "cgx"));

		
		String scHqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where  b.type=8 and a.id=b.emailId and b.userId=:userId  ";
		
		int scCount =  dao.findCount(scHqlCount, paramsMap);
		
		
		String wdHqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where a.type='yfs' and b.type=0 and b.read=0 and a.id=b.emailId and b.userId=:userId";

		int wdCount = dao.findCount(wdHqlCount, paramsMap);
		returnMap.put("wdCount", wdCount);
		returnMap.put("sjCount", sjCount);
		returnMap.put("fsCount", fsCount);
		returnMap.put("cgxCount", cgxCount);
		returnMap.put("scCount", scCount);

		return returnMap;
	}

	@Transactional
	public Map<String, Object> load2(String userId) {
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		
		String sjhqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where a.type='yfs' and b.type=0 and b.read=0 and a.id=b.emailId and b.userId='" + userId + "'";

		int sjCount = dao.findCount(sjhqlCount, paramsMap);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", sjCount);
		map2.put("id", "e9fa8689-c362-4c66-bd75-d1b132bd5211");
		map2.put("text", "个人邮件");
		map2.put("vsj", "jsp-message-email-emailmain");
		return map2;
	}

	@Transactional
	public SysEmail findObjectById(String id) {
		SysEmail sysEmail = super.findObjectById(id);
		return sysEmail;
	}

	@Transactional
	public SysEmail findReadObjectById(String id) {
		String userId = loginUserUtilService.findUserId();
		SysEmail sysEmail = super.findObjectById(id);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("emailId", id);
		dao.updateEntity(
				"update " + SysEmailUser.class.getName() + " set read=1 where userId=:userId and emailId=:emailId ",
				paramsMap);
		return sysEmail;
	}


	public PagingData<Map<String, Object>> queryDeletePage(SysEmail object, PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();

		String hql = "select a.id as id, a.title  as title, a.dcreate  as dcreate, a.type as type,  a.sendUserName as sendUserName, a.userNames as userNames,b.read as read from "
				+ SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where  (b.type=8 and a.id=b.emailId and b.userId=:userId) ";
		String hqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where  (b.type=8 and a.id=b.emailId and b.userId=:userId)  ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		
		String whereSql = "";
		if (Check.isNoEmpty(object.getTitle())) {
			paramMap.put("title","%"+ object.getTitle()+"%");
			whereSql+=" and a.title like :title ";
		}

		PagingData<Map<String, Object>> page = dao.queryPagingDataByHql(hql+whereSql+" ORDER BY b.dupdate DESC", hqlCount+whereSql, paramMap, pageRange);

		return page;
	}

	public PagingData<SysEmail> queryCGXPage(SysEmail object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findUserId())
						.is("type", "cgx").is("meDelete", 0)
						.orderDesc("dcreate").like("title", object.getTitle()),
				pageRange, new String[] { "id", "title", "dcreate", "type", "sendUserName", "userNames" });
	}

	public PagingData<SysEmail> querySendPage(SysEmail object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findUserId())
						.is("type", "yfs").is("meDelete", 0)
						.orderDesc("dcreate").like("title", object.getTitle()),
				pageRange, new String[] { "id", "title", "dcreate", "type", "sendUserName", "userNames" });
	}


	public PagingData<Map<String, Object>> queryShouPage(SysEmail object, PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();

		String hql = "select a.id as id, a.title  as title, a.dcreate  as dcreate, a.type as type , a.sendUserName as sendUserName, a.userNames as userNames,b.read as read from "
				+ SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where a.type='yfs' and b.type=0 and a.id=b.emailId and b.userId=:userId ";
		String hqlCount = "select count(b) from " + SysEmail.class.getName() + " a , " + SysEmailUser.class.getName()
				+ " b  where a.type='yfs' and b.type=0 and a.id=b.emailId and b.userId=:userId";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String whereSql = "";
		if (Check.isNoEmpty(object.getTitle())) {
			paramMap.put("title","%"+ object.getTitle()+"%");
			whereSql+=" and a.title like :title ";
		}

		PagingData<Map<String, Object>> page = dao.queryPagingDataByHql(hql+whereSql+" ORDER BY b.dupdate DESC", hqlCount+whereSql, paramMap, pageRange);

		return page;
	}

	@Override
	public void deleteByIds(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("userId", userId);
			paramsMap.put("emailIdList", Convert.strToList(ids));
			dao.updateEntity("update " + SysEmailUser.class.getName()
					+ " set type=8 where userId=:userId and emailId in :emailIdList ", paramsMap);
		}
	}

	@Transactional
	public void thoroughDelete(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("userId", userId);
			paramsMap.put("emailIdList", Convert.strToList(ids));
			dao.updateEntity("update " + SysEmailUser.class.getName()
					+ " set type=9 where userId=:userId and emailId in :emailIdList ", paramsMap);
		}
	}


	@Transactional
	public void recovery(String ids) {
		if (Check.isNoEmpty(ids)) {
				String userId = loginUserUtilService.findUserId();
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("userId", userId);
				paramsMap.put("emailIdList", Convert.strToList(ids));
				dao.updateEntity("update " + SysEmailUser.class.getName()
						+ " set type=0 where userId=:userId and emailId in :emailIdList ", paramsMap);
				
				update("id", Convert.strToList(ids), "meDelete", 0);
		}
	}

	public void deleteMeByIds(String ids) {
		if (Check.isNoEmpty(ids)) {
			if (Check.isNoEmpty(ids)) {
				update("id", Convert.strToList(ids), "meDelete", 1);
			}
		}

	}

	@Override
	public void fileOper(SystemFile systemFile) {
		SysEmail sysEmail = dao.findEntityByPK(SysEmail.class, systemFile.getServiceId());
		if (sysEmail == null) {
			systemFile.setDestroy(1);
		}
	}

}
