package com.hh.usersystem.service.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.base.BaseBean;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.service.inf.SystemServiceInf;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.PrimaryKey;
import com.hh.system.util.SystemUtil;
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.bean.usersystem.HhXtJs;
import com.hh.usersystem.bean.usersystem.HhXtJsCd;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhJs;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SystemService implements LoadDataTime ,SystemServiceInf{
	@Autowired
	private IHibernateDAO<HhXtYh> xtyhdao;
	@Autowired
	private IHibernateDAO<HhXtJs> roledao;
	@Autowired
	private IHibernateDAO<HhXtJsCd> jscddao;
	@Autowired
	private IHibernateDAO<HhXtYhJs> yhjsdao;
	@Autowired
	private IHibernateDAO<HhXtCd> menuService;

	@Transactional
	public void initMenuAndUser() {
		List<String> menuIdList = new ArrayList<String>();
		initMenu(menuIdList);
		HhXtJs hhXtJs = initRole(menuIdList);
		initSysUser(hhXtJs);
	}

	private void initMenu(List<String> menuIdList) {
		saveMenu(StaticProperties.hhXtCds, "root", menuIdList);
	}

	private void saveMenu(List<HhXtCd> hhXtCds, String node,
			List<String> menuIdList) {
		for (HhXtCd hhXtCd : hhXtCds) {
			menuIdList.add(hhXtCd.getId());
			hhXtCd.setNode(node);
			setBeanSysFields(hhXtCd);
			menuService.saveOrUpdateEntity(hhXtCd);
			if (Check.isNoEmpty(hhXtCd.getChildren())) {
				saveMenu(hhXtCd.getChildren(), hhXtCd.getId(), menuIdList);
			}
		}
	}

	private HhXtJs initRole(List<String> menuIdList) {
		HhXtJs hhXtJs = new HhXtJs();
		setBeanSysFields(hhXtJs);
		hhXtJs.setId("4c49311b-186e-486b-a837-7d3ad2a8c89f");
		hhXtJs.setText("超级管理员");
		hhXtJs.setVbz("超级管理员");
		hhXtJs.setNlx(3);
		roledao.saveOrUpdateEntity(hhXtJs);
		for (String string : menuIdList) {
			HhXtJsCd hhXtJsCd = new HhXtJsCd();
			setBeanSysFields(hhXtJsCd);
			hhXtJsCd.setJsId(hhXtJs.getId());
			hhXtJsCd.setCdId(string);
			hhXtJsCd.setId(string.replaceAll("-", "_"));
			jscddao.saveOrUpdateEntity(hhXtJsCd);
		}
		return hhXtJs;
	}

	private void initSysUser(HhXtJs hhXtJs) {
		HhXtYh hhXtYh = new HhXtYh();
		hhXtYh.setDsr(new Date());
		hhXtYh.setNxb(1);
		hhXtYh.setState(0);
		hhXtYh.setId("92b38970-69d8-4f5e-b7fc-f06f458a9f1f");
		hhXtYh.setText("超级管理员");
		hhXtYh.setVdh("123456");
		hhXtYh.setVdlzh("admin");
		hhXtYh.setVdzyj("admin@hh.com");
		hhXtYh.setHeadpic("/hhcommon/images/big/qq/10.gif");
		setBeanSysFields(hhXtYh);
//		HHXtZmsx hhXtZmsx = new HHXtZmsx();
//		hhXtYh.setHhXtZmsx(hhXtZmsx);
		hhXtYh.setVmm("123456");
		hhXtYh.setId("92b38970-69d8-4f5e-b7fc-f06f458a9f1f");
//		hhXtZmsx.setId(hhXtYh.getId());
		hhXtYh.setDesktopType("jquerydesktop");
		hhXtYh.setTheme("base");
		HhXtYhJs hhXtYhJs = new HhXtYhJs();
		setBeanSysFields(hhXtYhJs);
		hhXtYhJs.setId(hhXtYh.getId().replaceAll("-", ""));
		hhXtYhJs.setYhId(hhXtYh.getId());
		hhXtYhJs.setJsId(hhXtJs.getId());
		yhjsdao.saveOrUpdateEntity(hhXtYhJs);
		xtyhdao.saveOrUpdateEntity(hhXtYh);
	}

	public Map<String, Object> queryCacheListPage() {
		Map<String, Object> map = new HashMap<String, Object>();

		Set<String> keySet = xtyhdao.getSessionFactory().getAllClassMetadata()
				.keySet();

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
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", LoginUser.getLoginUserCount());
		map.put("onlineuser", map2);
		return map;
	}

	public static void setBeanSysFields(BaseBean object) {
		object.setVcreate("system");
		object.setVupdate("system");
		object.setVorgid("system");
		object.setDcreate(new Date());
		object.setDupdate(new Date());
		object.setOrder(PrimaryKey.getPrimaryKeyTime()
				+ UUID.randomUUID().toString().replace("-", ""));
	}
	
}
