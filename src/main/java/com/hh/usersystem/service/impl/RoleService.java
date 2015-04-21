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
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtJs;
import com.hh.usersystem.bean.usersystem.HhXtJsCd;
import com.hh.usersystem.bean.usersystem.HhXtJsCz;
import com.hh.usersystem.bean.usersystem.HhXtOrgJs;
import com.hh.usersystem.bean.usersystem.HhXtYhJs;

@Service
public class RoleService extends BaseService<HhXtJs> {
	@Autowired
	private IHibernateDAO<HhXtJs> xtjsdao;
	@Autowired
	private IHibernateDAO<HhXtYhJs> hhXtYhJsdao;
	@Autowired
	private IHibernateDAO<HhXtJsCd> hhXtJsCddao;
	@Autowired
	private IHibernateDAO<HhXtJsCz> hhxtjsczDao;
	@Autowired
	private IHibernateDAO<HhXtCz> xtczdao;
	@Autowired
	private IHibernateDAO<HhXtOrgJs> hhxtorgjsdao;
	
	@Autowired
	private OperateService operateService;

	public List<HhXtJs> queryAllRoleList() {
		return xtjsdao.queryList(HhXtJs.class,
				ParamFactory.getParamHb().is("nzt", 0));
	}

	public PagingData<HhXtJs> queryPagingData(HhXtJs hhXtJs, String roles,
			PageRange pageRange) {
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(hhXtJs.getText())) {
			hqlParamList.like("text",  hhXtJs.getText()
					);
		}
		if (!Check.isEmpty(roles)) {
			hqlParamList.add(Restrictions.in("id", Convert.strToList(roles)));
		}
		
		if (hhXtJs.getNzt()==1) {
			hqlParamList.add(Restrictions.eq("nzt", 0));
		}
		return xtjsdao.queryPagingData(HhXtJs.class, hqlParamList, pageRange);
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		if (!Check.isEmpty(idList)) {
			xtjsdao.deleteEntity(HhXtJs.class, "id", idList);
			hhXtYhJsdao.deleteEntity(HhXtYhJs.class, "jsId", idList);
			hhXtJsCddao.deleteEntity(HhXtJsCd.class, "jsId", idList);
			hhxtjsczDao.deleteEntity(HhXtJsCz.class, "jsId", idList);
			hhxtorgjsdao.deleteEntity(HhXtOrgJs.class, "jsId", idList);
		}

	}

	public void insertOrdeleteMenu(Map<String, String> paramsMap) {

		if ("true".equals(paramsMap.get("checked"))) {
			HhXtJsCd hhXtJsCd = new HhXtJsCd();
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

	public void saveJsMenu(HhXtJs object, String menuIds) {
		hhXtJsCddao.deleteEntity(HhXtJsCd.class, "jsId", object.getId());
		List<String> menuList = Convert.strToList(menuIds);
		for (String menuId : menuList) {
			HhXtJsCd hhXtJsCd = new HhXtJsCd();
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
			HhXtJsCz hhXtJsCd = new HhXtJsCz();
			HhXtCz hhXtCd = xtczdao.loadEntityByPK(HhXtCz.class, czid);
			hhXtJsCd.setHhXtCz(hhXtCd);
			hhXtJsCd.setJsId(jsid);
			hhXtJsCd.setOperLevel(operlevel);
			hhxtjsczDao.createEntity(hhXtJsCd);
		}

	}

	public void insertOrdeleteOperate(Map<String, String> paramsMap) {
		if ("true".equals(paramsMap.get("checked"))) {
			HhXtJsCz hhXtJsCd = new HhXtJsCz();
			HhXtCz hhXtCd = xtczdao.loadEntityByPK(HhXtCz.class,
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
