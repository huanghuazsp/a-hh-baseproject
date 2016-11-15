 package com.hh.oa.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.service.SysMessageService;
import com.hh.oa.bean.OaMeetingApply;
import com.hh.oa.bean.OaMeetingApplyUser;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;
import com.hh.usersystem.service.impl.UserService;

@Service
public class OaMeetingApplyService extends BaseService<OaMeetingApply> {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SysMessageService sysMessageService;
	
	@Autowired
	private OaMeetingApplyUserService oaMeetingApplyUserService;
	
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	
	public void updateDate(OaMeetingApply object) {
		dao.updateEntity("update "+OaMeetingApply.class.getName()+" o set o.start=:start,o.end=:end where o.id=:id ", object);
	}

	@Override
	public OaMeetingApply save(OaMeetingApply entity) throws MessageException {
		List<UsUser> userList = userService.queryListByOrgIds(entity
				.getAttendOrg(),entity.getAttendUser());
		oaMeetingApplyUserService.addUserByMeetingApply(entity, userList);
		return super.save(entity);
	}
	
	@Override
	public void deleteByIds(String ids) {
		oaMeetingApplyUserService.deleteByProperty("objectId", Convert.strToList(ids));
		sysMessageService.deleteByProperty("params",Convert.strToList(ids));
		super.deleteByIds(ids);
	}
	
	@Transactional
	public OaMeetingApply findReadObjectById(String id) {
		String userId = loginUserUtilService.findUserId();
		OaMeetingApply object = super.findObjectById(id);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("userId", userId);
		paramsMap.put("objectId", id);
		dao.updateEntity(
				"update " + OaMeetingApplyUser.class.getName() + " set read=1 where userId=:userId and objectId=:objectId ",
				paramsMap);
		return object;
	}
	
	public PagingData<OaMeetingApply> queryPagingData(OaMeetingApply entity,
			PageRange pageRange) {

		ParamInf hqlParamList = ParamFactory.getParamHb();

		if (Check.isNoEmpty(entity.getText())) {
			hqlParamList.like("text", entity.getText());
		}
		return queryPagingData(pageRange, hqlParamList);
	}

	public PagingData<Map<String, Object>> queryShouPage(OaMeetingApply object,
			PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();

		String hql = "select a.id as id, a.text  as text, a.dcreate  as dcreate , a.vcreateName as vcreateName,b.read as read from "
				+ OaMeetingApply.class.getName()
				+ " a , "
				+ OaMeetingApplyUser.class.getName()
				+ " b  where  a.id=b.objectId and b.userId=:userId";
		String hqlCount = "select count(b) from "
				+ OaMeetingApply.class.getName()
				+ " a , "
				+ OaMeetingApplyUser.class.getName()
				+ " b  where  a.id=b.objectId and b.userId=:userId ";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String whereSql = "";
		if (Check.isNoEmpty(object.getText())) {
			paramMap.put("text", "%" + object.getText() + "%");
			whereSql += " and a.text like :text ";
		}

		PagingData<Map<String, Object>> page = dao.queryPagingDataByHql(hql
				+ whereSql + " ORDER BY b.dupdate DESC", hqlCount + whereSql,
				paramMap, pageRange);

		return page;
	}
	
}
 