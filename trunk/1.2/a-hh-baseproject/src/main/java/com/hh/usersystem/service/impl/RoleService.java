package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsRoleMenu;
import com.hh.usersystem.bean.usersystem.UsRoleOper;

@Service
public class RoleService extends BaseService<UsRole> {
	@Autowired
	private IHibernateDAO<UsRole> xtjsdao;
	@Autowired
	private IHibernateDAO<UsRoleMenu> hhXtJsCddao;
	@Autowired
	private IHibernateDAO<UsRoleOper> hhxtjsczDao;
	@Autowired
	private IHibernateDAO<SysOper> xtczdao;

	@Autowired
	private OperateService operateService;

	public List<UsRole> queryAllRoleList() {
		return xtjsdao.queryList(UsRole.class, ParamFactory.getParamHb().nis("state", 1));
	}

	public PagingData<UsRole> queryPagingData(UsRole hhXtJs, String roles, PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(hhXtJs.getText())) {
			hqlParamList.like("text", hhXtJs.getText());
		}
		if (!Check.isEmpty(roles)) {
			hqlParamList.add(Restrictions.in("id", Convert.strToList(roles)));
		}

		if (hhXtJs.getState() == 1) {
			hqlParamList.nis("state", 1);
		}
		return xtjsdao.queryPagingData(UsRole.class, hqlParamList, pageRange);
	}

	public void deleteByIds(String ids) {
		if (ids.indexOf("default")>-1) {
			throw new MessageException("默认角色不能删除！");
		}
		if (ids.indexOf("admin")>-1) {
			throw new MessageException("超级管理员角色不能删除！");
		}
		List<String> idList = Convert.strToList(ids);
		if (!Check.isEmpty(idList)) {
			xtjsdao.deleteEntity(UsRole.class, "id", idList);
			hhXtJsCddao.deleteEntity(UsRoleMenu.class, "jsId", idList);
			hhxtjsczDao.deleteEntity(UsRoleOper.class, "jsId", idList);
		}
	}

	public void insertOrdeleteMenu(Map<String, String> paramsMap) {

		if ("true".equals(paramsMap.get("checked"))) {
			UsRoleMenu hhXtJsCd = new UsRoleMenu();
			// HhXtCd hhXtCd = xtcddao.loadEntityByPK(HhXtCd.class,
			// paramsMap.get("menuid"));
			// hhXtCd.setId(paramsMap
			// .get("menuid"));
			hhXtJsCd.setCdId(paramsMap.get("menuid"));
			hhXtJsCd.setJsId(paramsMap.get("roleid"));
			hhXtJsCddao.createEntity(hhXtJsCd);
		} else {
			hhXtJsCddao.deleteEntity("delete HhXtJsCd o where jsId=:roleid and cdId=:menuid", paramsMap);
		}
	}

	public void saveJsMenu(UsRole object, String menuIds) {
		hhXtJsCddao.deleteEntity(UsRoleMenu.class, "jsId", object.getId());
		List<String> menuList = Convert.strToList(menuIds);
		for (String menuId : menuList) {
			UsRoleMenu hhXtJsCd = new UsRoleMenu();
			hhXtJsCd.setCdId(menuId);
			hhXtJsCd.setJsId(object.getId());
			hhXtJsCddao.createEntity(hhXtJsCd);
		}
	}

	public void saveJsOper(String menuId, String jsid, String czid_operlevel) {
		List<String> czid_operlevelList = Convert.strToList(czid_operlevel);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("roleid", jsid);
		if (Check.isNoEmpty(menuId)) {
			paramsMap.put("menuId", menuId);
			hhxtjsczDao.deleteEntity("delete UsRoleOper o where jsId=:roleid and cdId =:menuId", paramsMap);
		} else {
			hhxtjsczDao.deleteEntity("delete UsRoleOper o where jsId=:roleid ", paramsMap);
		}

		for (String czid_oper : czid_operlevelList) {
			String cdid = czid_oper.split("_")[0];
			String czid = czid_oper.split("_")[1];
			String operlevel = "";
			if (czid_oper.split("_").length > 1) {
				operlevel = czid_oper.split("_")[2];
			}
			UsRoleOper hhXtJsCd = new UsRoleOper();
			hhXtJsCd.setCzId(czid);
			hhXtJsCd.setCdId(cdid);
			hhXtJsCd.setJsId(jsid);
			hhXtJsCd.setOperLevel(operlevel);
			hhxtjsczDao.createEntity(hhXtJsCd);
		}

	}


}
