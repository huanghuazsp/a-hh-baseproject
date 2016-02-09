package com.hh.usersystem.service.impl;

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
		return xtjsdao.queryList(UsRole.class,
				ParamFactory.getParamHb().nis("state", 1));
	}

	public PagingData<UsRole> queryPagingData(UsRole hhXtJs, String roles,
			PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(hhXtJs.getText())) {
			hqlParamList.like("text",  hhXtJs.getText()
					);
		}
		if (!Check.isEmpty(roles)) {
			hqlParamList.add(Restrictions.in("id", Convert.strToList(roles)));
		}
		
		if (hhXtJs.getState()==1) {
			hqlParamList.nis("state", 1);
		}
		return xtjsdao.queryPagingData(UsRole.class, hqlParamList, pageRange);
	}

	public void deleteByIds(String ids) {
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
			hhXtJsCddao.deleteEntity(
					"delete HhXtJsCd o where jsId=:roleid and cdId=:menuid",
					paramsMap);
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

	public void saveJsOper(String menuIds, String jsid, String czid_operlevel) {
		List<ExtTree>  extTrees = operateService.queryOperateListByPid(menuIds);
		for (ExtTree extTree : extTrees) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("roleid", jsid);
			paramsMap.put("czid", extTree.getId());
			hhxtjsczDao
			.deleteEntity(
					"delete HhXtJsCz o where jsId=:roleid and hhXtCz.id=:czid",
					paramsMap);
		}
		List<String> czid_operlevelList = Convert.strToList(czid_operlevel);
		for (String czid_oper : czid_operlevelList) {
			String czid = czid_oper.split("_")[0];
			String operlevel = "";
			if (czid_oper.split("_").length>1) {
				operlevel = czid_oper.split("_")[1];
			}
			UsRoleOper hhXtJsCd = new UsRoleOper();
			SysOper hhXtCd = xtczdao.loadEntityByPK(SysOper.class, czid);
			hhXtJsCd.setHhXtCz(hhXtCd);
			hhXtJsCd.setJsId(jsid);
			hhXtJsCd.setOperLevel(operlevel);
			hhxtjsczDao.createEntity(hhXtJsCd);
		}

	}

	public void insertOrdeleteOperate(Map<String, String> paramsMap) {
		if ("true".equals(paramsMap.get("checked"))) {
			UsRoleOper hhXtJsCd = new UsRoleOper();
			SysOper hhXtCd = xtczdao.loadEntityByPK(SysOper.class,
					paramsMap.get("menuid"));
			// hhXtCd.setId(paramsMap
			// .get("menuid"));
			hhXtJsCd.setHhXtCz(hhXtCd);
			hhXtJsCd.setJsId(paramsMap.get("roleid"));
			hhxtjsczDao.createEntity(hhXtJsCd);
		} else {
			hhxtjsczDao
					.deleteEntity(
							"delete HhXtJsCz o where jsId=:roleid and hhXtCz.id=:menuid",
							paramsMap);
		}
	}

	public void updateOperateLevel(Map<String, String> paramsMap) {
		if ("true".equals(paramsMap.get("checked"))) {
			hhxtjsczDao
					.updateEntity(
							"update HhXtJsCz o set o.operLevel=:operateLevel where o.jsId=:roleid and o.hhXtCz.id=:menuid",
							paramsMap);
		} else {
			hhxtjsczDao
					.updateEntity(
							"update HhXtJsCz o set o.operLevel='' where o.jsId=:roleid and o.hhXtCz.id=:menuid",
							paramsMap);
		}
	}

}
