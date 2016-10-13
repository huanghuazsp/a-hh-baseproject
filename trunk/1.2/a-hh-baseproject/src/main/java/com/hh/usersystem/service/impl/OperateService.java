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
import com.hh.system.util.StaticVar;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.aop.interceptor.SecurityInterceptor;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsRoleOper;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;

@Service
public class OperateService extends BaseService<SysOper> {
	@Autowired
	private IHibernateDAO<SysOper> dao;
	@Autowired
	private IHibernateDAO<UsRoleOper> hhxtjsczDao;

	@Autowired
	private MenuService menuService;

	public List<ExtTree> queryOperateListByPid(String node) {
		List<SysOper> hhXtCzList = new ArrayList<SysOper>();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (Check.isNoEmpty(node)) {
			hqlParamList.is("menuId", node);
		}
		hqlParamList.order("menuIdText");
		hhXtCzList = dao.queryList(SysOper.class, hqlParamList);
		List<ExtTree> extTreeList = new ArrayList<ExtTree>();
		for (SysOper hhXtCd : hhXtCzList) {
			ExtTree extTree = new ExtTree();
			extTree.setId(hhXtCd.getId());

			String text = hhXtCd.getText();
			if (Check.isNoEmpty(hhXtCd.getPageText())) {
				text += "（名称）";
			}
			if (Check.isNoEmpty(hhXtCd.getVurl())) {
				text += "（请求）";
			}
			text = "【" + hhXtCd.getMenuIdText() + "】" + text;
			extTree.setText(text);
			extTree.setLeaf(1);
			extTreeList.add(extTree);
		}
		return extTreeList;
	}

	public List<SysOper> queryCheckOperateListByPid(String menuId, String roleid, String node) {
		List<SysOper> hhXtCzList = new ArrayList<SysOper>();

		List<String> czidList = new ArrayList<String>();
		ParamInf hqlParamList = ParamFactory.getParamHb();

		if (Check.isNoEmpty(menuId)) {
			hqlParamList.is("menuId", menuId);
		}
		hqlParamList.order("menuIdText");
		hhXtCzList = dao.queryList(SysOper.class, hqlParamList);

		for (SysOper hhXtCd : hhXtCzList) {
			String text = hhXtCd.getText();
			if (Check.isEmpty(menuId)) {
				if (Check.isNoEmpty(hhXtCd.getMenuIdText())) {
					text = "【" + hhXtCd.getMenuIdText() + "】" + text;
				}
			}
			if (Check.isNoEmpty(hhXtCd.getPageText())) {
				text += "（名称）";
			}
			if (Check.isNoEmpty(hhXtCd.getVurl())) {
				text += "（请求）";
			}
			hhXtCd.setText(text);

			czidList.add(hhXtCd.getId());
		}

		Map<String, UsRoleOper> UsRoleOperMap = new HashMap<String, UsRoleOper>();

		if (Check.isNoEmpty(czidList)) {
			ParamInf hqlParamList2 = ParamFactory.getParamHb();
			hqlParamList2.in("czId", czidList);
			hqlParamList2.is("jsId", roleid);
			List<UsRoleOper> hhXtJsCdList = hhxtjsczDao.queryList(UsRoleOper.class, hqlParamList2);
			for (UsRoleOper hhXtJsCd : hhXtJsCdList) {
				UsRoleOperMap.put(hhXtJsCd.getCzId(), hhXtJsCd);
			}

			for (SysOper hhXtCd : hhXtCzList) {
				if (UsRoleOperMap.get(hhXtCd.getId()) != null) {
					hhXtCd.setOperLevel(UsRoleOperMap.get(hhXtCd.getId()).getOperLevel());
				}
			}
		}

		return hhXtCzList;

	}

	public SysOper findObjectById(String id) {
		SysOper hhXtCz = dao.findEntityByPK(SysOper.class, id);
		return hhXtCz;
	}

	public List<SysOper> queryOperateListByPids(List<String> pids) {
		return this.queryList(ParamFactory.getParamHb().in("menuId", pids));
	}

	public SysOper save(SysOper hhXtCz) {
		if (Check.isEmpty(hhXtCz.getId())) {
			dao.createEntity(hhXtCz);
		} else {
			dao.updateEntity(hhXtCz);
		}
		initOperPower();
		return hhXtCz;
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteByIdList(idList);
	}

	public void deleteByIdList(List<String> idList) {
		if (!Check.isEmpty(idList)) {
			hhxtjsczDao.deleteEntity(UsRoleOper.class, "czId", idList);
			dao.deleteEntity(SysOper.class, "id", idList);
			initOperPower();
		}
	}

	public void deleteByMenuIdList(List<String> idList) {
		if (!Check.isEmpty(idList)) {
			hhxtjsczDao.deleteEntity(UsRoleOper.class, "cdId", idList);
			dao.deleteEntity(SysOper.class, "menuId", idList);
			initOperPower();
		}
	}

	public void deleteByPidList(List<String> pidList) {
		List<String> idList = new ArrayList<String>();
		List<SysOper> hhXtCzList = queryOperateListByPids(pidList);
		for (SysOper hhXtCz : hhXtCzList) {
			idList.add(hhXtCz.getId());
		}
		deleteByIdList(idList);
	}

	public void initOperPower() {
		SecurityInterceptor.all_manage_request.clear();
		SecurityInterceptor.all_manage_page_text_map.clear();
		List<SysOper> xtczList = dao.queryList(SysOper.class);
		for (SysOper hhXtCz : xtczList) {
			if (Check.isNoEmpty(hhXtCz.getVurl())) {
				SecurityInterceptor.all_manage_request.add(hhXtCz.getVurl());
			}

			if (Check.isNoEmpty(hhXtCz.getPageText())) {
				List<String> menuCzList = SecurityInterceptor.all_manage_page_text_map.get(hhXtCz.getMenuUrl());
				if (menuCzList == null) {
					SecurityInterceptor.all_manage_page_text_map.put(hhXtCz.getMenuUrl(), new ArrayList<String>());
				}
				SecurityInterceptor.all_manage_page_text_map.get(hhXtCz.getMenuUrl()).add(hhXtCz.getPageText());
			}
		}

		List<SysMenu> sysMenus = menuService.queryAllList();
		for (SysMenu sysMenu : sysMenus) {
			if (sysMenu.getLeaf() == 1 && Check.isNoEmpty(sysMenu.getVsj())) {
				SecurityInterceptor.all_manage_request.add(sysMenu.getVsj());
			}
		}

	}

}
