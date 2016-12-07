package com.hh.usersystem.service.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.base.BaseBean;
import com.hh.system.service.impl.SysDataTypeService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.service.inf.SystemServiceInf;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.StaticVar;
import com.hh.system.util.pk.PrimaryKey;
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsRoleMenu;
import com.hh.usersystem.bean.usersystem.UsRoleOper;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;

@Service
public class SystemService implements LoadDataTime, SystemServiceInf {
	@Autowired
	private IHibernateDAO<UsUser> xtyhdao;
	@Autowired
	private IHibernateDAO<UsRole> roledao;
	@Autowired
	private IHibernateDAO<UsRoleMenu> jscddao;
	@Autowired
	private IHibernateDAO<UsRoleOper> jsczdao;
	@Autowired
	private IHibernateDAO<SysMenu> menuService;

	@Autowired
	private OperateService operateService;

	@Autowired
	private SysDataTypeService sysDataTypeService;
	
	@Transactional
	public void initMenuAndUser() {
		List<String> menuIdList = new ArrayList<String>();
		initMenu(menuIdList);
		initOper();
		UsRole hhXtJs = initRole(menuIdList);
		initSysUser(hhXtJs);
		operateService.initOperPower();
	}
	private void initOper() {
		for (SysOper sysOper : StaticProperties.sysOperList) {
			setBeanSysFields( sysOper);
			operateService.saveOrUpdate(sysOper);
		}

	}

	private void initMenu(List<String> menuIdList) {
		saveMenu(StaticProperties.sysMenuList, "root", menuIdList);
	}

	private void saveMenu(List<SysMenu> hhXtCds, String node, List<String> menuIdList) {
		for (SysMenu hhXtCd : hhXtCds) {
			menuIdList.add(hhXtCd.getId());
			hhXtCd.setNode(node);
			setBeanSysFields(hhXtCd);
			menuService.saveOrUpdateEntity(hhXtCd);
			if (Check.isNoEmpty(hhXtCd.getChildren())) {
				saveMenu(hhXtCd.getChildren(), hhXtCd.getId(), menuIdList);
			}
		}
	}

	private UsRole initRole(List<String> menuIdList) {
		UsRole hhXtJs = new UsRole();
		setBeanSysFields(hhXtJs);
		hhXtJs.setId(StaticVar.role_admin_id);
		hhXtJs.setText(StaticVar.role_admin_text);
		hhXtJs.setVbz(StaticVar.role_admin_text);
		hhXtJs.setJssx(StaticVar.role_admin_id);
		hhXtJs.setNlx(3);
		roledao.saveOrUpdateEntity(hhXtJs);

		UsRole hhXtJs2 = new UsRole();
		setBeanSysFields(hhXtJs2);
		hhXtJs2.setId(StaticVar.role_default_id);
		hhXtJs2.setText(StaticVar.role_default_text);
		hhXtJs2.setVbz(StaticVar.role_default_text);
		hhXtJs2.setJssx(StaticVar.role_default_id);
		hhXtJs2.setNlx(3);
		roledao.saveOrUpdateEntity(hhXtJs2);

		jscddao.deleteEntity(UsRoleMenu.class, "jsId", hhXtJs.getId());
		for (String string : menuIdList) {
			UsRoleMenu hhXtJsCd = new UsRoleMenu();
			setBeanSysFields(hhXtJsCd);
			hhXtJsCd.setJsId(hhXtJs.getId());
			hhXtJsCd.setCdId(string);
			hhXtJsCd.setId(PrimaryKey.getUUID());
			jscddao.saveOrUpdateEntity(hhXtJsCd);
		}
		jsczdao.deleteEntity(UsRoleOper.class, "jsId", hhXtJs.getId());
		for (SysOper sysOper : StaticProperties.sysOperList) {
			UsRoleOper usRoleOper = new UsRoleOper();
			setBeanSysFields( usRoleOper);
			
			usRoleOper.setJsId(hhXtJs.getId());
			usRoleOper.setCzId(sysOper.getId());
			usRoleOper.setId(PrimaryKey.getUUID());
			usRoleOper.setCdId(sysOper.getMenuId());
			usRoleOper.setOperLevel(OperationLevel.ALL+"");
			
			jsczdao.saveOrUpdateEntity( usRoleOper);
		}
		return hhXtJs;
	}

	private void initSysUser(UsRole hhXtJs) {
		UsUser hhXtYh = new UsUser();
		hhXtYh.setDsr(new Date());
		hhXtYh.setNxb(1);
		hhXtYh.setState(0);
		hhXtYh.setId("admin");
		hhXtYh.setText("超级管理员");
		hhXtYh.setVdh("123456");
		hhXtYh.setVdlzh("admin");
		hhXtYh.setVdzyj("admin@hh.com");
		hhXtYh.setHeadpic("/hhcommon/images/big/qq/10.gif");
		setBeanSysFields(hhXtYh);
		// HHXtZmsx hhXtZmsx = new HHXtZmsx();
		// hhXtYh.setHhXtZmsx(hhXtZmsx);
		hhXtYh.setVmm("123456");
		hhXtYh.setId("admin");
		// hhXtZmsx.setId(hhXtYh.getId());
		hhXtYh.setTheme("base");
		hhXtYh.setRoleIds(hhXtJs.getId());
		hhXtYh.setRoleIdsText(hhXtJs.getText());
		xtyhdao.saveOrUpdateEntity(hhXtYh);
	}

	public Map<String, Object> queryCacheListPage() {
		Map<String, Object> map = new HashMap<String, Object>();

		Set<String> keySet = xtyhdao.getSessionFactory().getAllClassMetadata().keySet();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (String key : keySet) {
			Annotation[] a = null;
			try {
				a = Class.forName(key).getAnnotations();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for (Annotation annotation : a) {
				if (annotation instanceof Cache) {
					Map<String, Object> value = new HashMap<String, Object>();
					value.put("id", key);
					list.add(value);
				}
			}
		}
		map.put("items", list);
		return map;
	}

	public void deleteCacheByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String id : idList) {
			xtyhdao.getSessionFactory().evictEntity(id);
		}
		xtyhdao.getSessionFactory().evictQueries();
	}

	public Map<String, Object> load() {
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", LoginUser.getLoginUserCount());
		return map2;
	}

	public static void setBeanSysFields(BaseBean object) {
		object.setCreateUser("system");
		object.setUpdateUser("system");
		object.setOrgid("system");
		object.setCreateTime(new Date());
		object.setUpdateTime(new Date());
		object.setOrder(PrimaryKey.getTime());
	}
	@Override
	public void initDataList() {
		sysDataTypeService.save(StaticProperties.dataList);
	}

}
