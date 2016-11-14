package com.hh.oa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.message.bean.SysEmail;
import com.hh.message.bean.SysEmailUser;
import com.hh.message.bean.SysMessage;
import com.hh.message.service.MessageThread;
import com.hh.message.service.SysMessageService;
import com.hh.oa.bean.OaNotice;
import com.hh.oa.bean.OaNoticeUser;
import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.IFileOper;
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
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class OaNoticeService extends BaseService<OaNotice> implements LoadDataTime, IFileOper {

	@Autowired
	private UserService userService;

	@Autowired
	private IHibernateDAO<OaNoticeUser> noticeUserDao;

	@Autowired
	private SysMessageService sysMessageService;

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Override
	public PagingData<OaNotice> queryPagingData(OaNotice entity,
			PageRange pageRange) {

		ParamInf hqlParamList = ParamFactory.getParamHb();

		if ("wfbd".equals(entity.getType())) {
			String userId = loginUserUtilService.findUserId();
			hqlParamList.is("vcreate", userId);
		}
		
		if (Check.isNoEmpty(entity.getTitle())) {
			hqlParamList.like("title", entity.getTitle());
		}
		return queryPagingData(pageRange, hqlParamList);
	}

	public PagingData<Map<String, Object>> queryShouPage(OaNotice object,
			PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();

		String hql = "select a.id as id, a.title  as title, a.dcreate  as dcreate, a.type as type , a.vcreateName as vcreateName,b.read as read from "
				+ OaNotice.class.getName()
				+ " a , "
				+ OaNoticeUser.class.getName()
				+ " b  where a.deploy=1 and a.id=b.objectId and b.userId=:userId ";
		String hqlCount = "select count(b) from "
				+ OaNotice.class.getName()
				+ " a , "
				+ OaNoticeUser.class.getName()
				+ " b  where a.deploy=1 and a.id=b.objectId and b.userId=:userId";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String whereSql = "";
		if (Check.isNoEmpty(object.getTitle())) {
			paramMap.put("title", "%" + object.getTitle() + "%");
			whereSql += " and a.title like :title ";
		}

		PagingData<Map<String, Object>> page = dao.queryPagingDataByHql(hql
				+ whereSql + " ORDER BY b.dupdate DESC", hqlCount + whereSql,
				paramMap, pageRange);

		return page;
	}

	@Override
	public OaNotice save(OaNotice entity) throws MessageException {
		UsUser user = loginUserUtilService.findLoginUser();
		entity.setDeployDept(user.getOrgText()+"/"+user.getDeptText());
		OaNotice OaNotice = super.save(entity);
		if (entity.getDeploy() == 1) {
			if (entity.getRangeType() == 1) {
				List<UsUser> userList = userService.queryAllList();
				addUserByNotice(entity, userList);
			} else if (Check.isNoEmpty(entity.getRange())) {
				List<UsUser> userList = userService.queryListByOrgIds(entity
						.getRange());
				addUserByNotice(entity, userList);
			}

		}
		return OaNotice;
	}

	public void addUserByNotice(OaNotice entity, List<UsUser> userList) {
		noticeUserDao.deleteEntity(OaNoticeUser.class, "objectId",
				entity.getId());
		for (UsUser usUser : userList) {
			OaNoticeUser noticeUser = new OaNoticeUser();
			noticeUser.setObjectId(entity.getId());
			noticeUser.setUserId(usUser.getId());
			noticeUser.setUserName(usUser.getText());
			noticeUserDao.createEntity(noticeUser);
			SysMessage message = sysMessageService.findMessage(13,
					entity.getTitle(), noticeUser.getUserId(),
					noticeUser.getUserName(), "");
			message.setObjectId("notice");
			message.setObjectName("公告");
			message.setParams(entity.getId());
			ThreadUtil.getThreadPool().execute(new MessageThread(message, 0));
		}
	}

	public Map<String, Object> count() {
		String userId = loginUserUtilService.findUserId();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);

		String sjhqlCount = "select count(b) from "
				+ OaNotice.class.getName()
				+ " a , "
				+ OaNoticeUser.class.getName()
				+ " b  where a.deploy=1 and a.id=b.objectId and b.userId=:userId";

		int sjCount = dao.findCount(sjhqlCount, paramsMap);

		String wdHqlCount = "select count(b) from "
				+ OaNotice.class.getName()
				+ " a , "
				+ OaNoticeUser.class.getName()
				+ " b  where a.deploy=1 and b.read=0 and a.id=b.objectId and b.userId=:userId";

		int wdCount = dao.findCount(wdHqlCount, paramsMap);
		returnMap.put("wdCount", wdCount);
		returnMap.put("sjCount", sjCount);
		return returnMap;
	}
	
	@Transactional
	public OaNotice findReadObjectById(String id) {
		String userId = loginUserUtilService.findUserId();
		OaNotice object = super.findObjectById(id);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("objectId", id);
		dao.updateEntity(
				"update " + OaNoticeUser.class.getName() + " set read=1 where userId=:userId and objectId=:objectId ",
				paramsMap);
		return object;
	}

	@Override
	public void deleteByIds(String ids) {
		noticeUserDao.deleteEntity(OaNoticeUser.class, "objectId",
				Convert.strToList(ids));
		super.deleteByIds(ids);
	}

	@Override
	public Map<String, Object> load() {
		String userId = loginUserUtilService.findUserId();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		String wdHqlCount = "select count(b) from "
				+ OaNotice.class.getName()
				+ " a , "
				+ OaNoticeUser.class.getName()
				+ " b  where a.deploy=1 and b.read=0 and a.id=b.objectId and b.userId=:userId";

		int wdCount = dao.findCount(wdHqlCount, paramsMap);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", wdCount);
		map2.put("id", "QoGVp009MF5vDM5qCQs");
		map2.put("text", "公告通知");
		map2.put("vsj", "jsp-oa-notice-noticemain");
		return map2;
	}	
	
	@Override
	public void fileOper(SystemFile systemFile) {
		OaNotice OaNotice = findObjectById(systemFile.getServiceId());
		if (OaNotice == null) {
			systemFile.setDestroy(1);
		}
	}
	
}
