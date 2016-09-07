package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsRoleMenu;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserMenuZmtb;
import com.opensymphony.xwork2.ActionContext;

@Service
public class MenuService extends BaseService<SysMenu> {
	@Autowired
	private IHibernateDAO<SysMenu> dao;
	@Autowired
	private IHibernateDAO<UsRoleMenu> xtjscddao;
	@Autowired
	private IHibernateDAO<UsUserMenuZmtb> xtyhcdzmtb;
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private IHibernateDAO<SysOper> czdao;
	@Autowired
	private LoginUserUtilService loginUserService;
	@Autowired
	private ZmtbService zmtbService;
	@Autowired
	private OperateService operateService;

	public SysMenu findObjectById(String id) {
		SysMenu hhXtCd = dao.findEntityByPK(SysMenu.class, id);
		if (!"root".equals(hhXtCd.getNode())) {
			SysMenu parenthhXtCd = dao.findEntityByPK(SysMenu.class, hhXtCd.getNode());
			hhXtCd.setVpname(parenthhXtCd.getText());
		}
		return hhXtCd;
	}

	public SysMenu findObjectById2(String id) {
		SysMenu hhXtCd = dao.findEntityByPK(SysMenu.class, id);
		return hhXtCd;
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteYzNode(idList);
		if (!Check.isEmpty(idList)) {
			// czdao.deleteEntity(HhXtCz.class,"node",idList);
			xtjscddao.deleteEntity(UsRoleMenu.class, "cdId", idList);
			xtyhcdzmtb.deleteEntity(UsUserMenuZmtb.class, "cdId", idList);
			dao.deleteEntity(SysMenu.class, "id", idList);
		}

	}

	private void deleteYzNode(List<String> idList) {

		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
			operateService.deleteByPidList(idList);
			List<SysMenu> menuList = dao.queryList(SysMenu.class, Restrictions.in("node", idList));
			for (SysMenu hhXtCd : menuList) {
				yzIdList.add(hhXtCd.getId());
			}
			deleteYzNode(yzIdList);
			if (!Check.isEmpty(yzIdList)) {
				xtjscddao.deleteEntity(UsRoleMenu.class, "cdId", yzIdList);
				xtyhcdzmtb.deleteEntity(UsUserMenuZmtb.class, "cdId", yzIdList);
				dao.deleteEntity(SysMenu.class, "id", yzIdList);
			}
		}

	}

	public List<SysMenu> queryMenuListByPid(String node) {
		List<SysMenu> menuList = new ArrayList<SysMenu>();
		if (Check.isEmpty(node) || "root".equals(node)) {
			menuList = dao.queryTreeList(SysMenu.class, ParamFactory.getParamHb().is("node", "root"));
		} else {
			menuList = dao.queryTreeList(SysMenu.class, ParamFactory.getParamHb().is("node", node));
		}

		return menuList;
	}

	public List<ExtCheckTree> queryMenuAllListByPid(String node, String roleid) {
		List<SysMenu> menuList = new ArrayList<SysMenu>();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (Check.isEmpty(node) || "root".equals(node)) {
			menuList = dao.queryAllTreeList(SysMenu.class, hqlParamList.is("node", "root"));
		} else {
			menuList = dao.queryAllTreeList(SysMenu.class, hqlParamList.is("node", node));
		}
		List<ExtCheckTree> extTreeList = new ArrayList<ExtCheckTree>();

		List<String> cdidList = new ArrayList<String>();

		for (SysMenu hhXtCd : menuList) {
			ExtCheckTree extTree = new ExtCheckTree();
			extTree.setId(hhXtCd.getId());
			extTree.setText(hhXtCd.getText());
			extTree.setIcon(hhXtCd.getIcon());
			extTree.setExpanded(1);
			extTree.setLeaf(hhXtCd.getLeaf());
			extTreeList.add(extTree);
			cdidList.add(hhXtCd.getId());
			if (hhXtCd.getChildren() != null) {
				extTree.setChildren(new ArrayList<ExtCheckTree>());
				addChildren(extTree, hhXtCd.getChildren(), cdidList);
			}
		}

		if (!Check.isEmpty(cdidList)) {
			ParamInf hqlParamList2 = ParamFactory.getParamHb();
			hqlParamList2.in("cdId", cdidList);
			hqlParamList2.is("jsId", roleid);
			List<UsRoleMenu> hhXtJsCdList = xtjscddao.queryList(UsRoleMenu.class, hqlParamList2);

			List<String> jscdidList = new ArrayList<String>();
			if (!Check.isEmpty(hhXtJsCdList)) {
				for (UsRoleMenu hhXtJsCd : hhXtJsCdList) {
					jscdidList.add(hhXtJsCd.getCdId());
				}
			}

			for (ExtCheckTree extCheckTree : extTreeList) {
				if (jscdidList.contains(extCheckTree.getId())) {
					extCheckTree.setChecked(true);
				}
				if (extCheckTree.getChildren() != null) {
					checkMenuByCdId(extCheckTree.getChildren(), jscdidList);
				}
			}
		}

		return extTreeList;
	}

	private void checkMenuByCdId(List<ExtCheckTree> children, List<String> jscdidList) {
		for (ExtCheckTree extCheckTree : children) {
			if (jscdidList.contains(extCheckTree.getId())) {
				extCheckTree.setChecked(true);
			}
			if (extCheckTree.getChildren() != null) {
				checkMenuByCdId(extCheckTree.getChildren(), jscdidList);
			}
		}
	}

	private void addChildren(ExtCheckTree parentextTree, List<SysMenu> children, List<String> cdidList) {
		for (SysMenu hhXtCd : children) {
			ExtCheckTree extTree = new ExtCheckTree();
			extTree.setId(hhXtCd.getId());
			extTree.setText(hhXtCd.getText());
			extTree.setIcon(hhXtCd.getIcon());
			extTree.setExpanded(1);
			extTree.setLeaf(hhXtCd.getLeaf());
			parentextTree.getChildren().add(extTree);
			cdidList.add(hhXtCd.getId());
			if (hhXtCd.getChildren() != null) {
				extTree.setChildren(new ArrayList<ExtCheckTree>());
				addChildren(extTree, hhXtCd.getChildren(), cdidList);
			}
		}
	}

	public List<SysMenu> addZmtb(String ids) {
		if (Check.isNoEmpty(ids)) {
			List<String> idList = Convert.strToList(ids);
			for (String id : idList) {
				UsUserMenuZmtb hhXtYhCdZmtb = new UsUserMenuZmtb();
				// SysMenu hhXtCd = dao.findEntityByPK(SysMenu.class, id);
				hhXtYhCdZmtb.setCdId(id);
				hhXtYhCdZmtb.setYhId(loginUserUtilService.findLoginUser().getId());
				xtyhcdzmtb.createEntity(hhXtYhCdZmtb);
			}
			UsUser hhXtYh = loginUserService.findLoginUser();
			hhXtYh.setDesktopQuickList(zmtbService.queryZmtbByUserId(hhXtYh.getId()));
			ActionContext.getContext().getSession().put("loginuser", hhXtYh);
			return hhXtYh.getDesktopQuickList();
		}
		return new ArrayList<SysMenu>();
	}

	public void deleteZmtb(String id) {
		if (Check.isNoEmpty(id)) {
			xtyhcdzmtb.deleteEntity(UsUserMenuZmtb.class, "cdId", Convert.strToList(id));
			UsUser hhXtYh = loginUserService.findLoginUser();
			hhXtYh.setDesktopQuickList(zmtbService.queryZmtbByUserId(hhXtYh.getId()));
			ActionContext.getContext().getSession().put("loginuser", hhXtYh);
		}
	}

	public static List<SysMenu> getSubNodes(String parentId, List<SysMenu> sysMenuList) {
		List<SysMenu> returnsysMenuList = new ArrayList<SysMenu>();
		for (SysMenu sysMenu : sysMenuList) {
			if (Convert.toString(sysMenu.getNode()).equals(parentId) && Check.isNoEmpty(sysMenu.getMobileUrl())) {
				returnsysMenuList.add(sysMenu);
				getSubNodes(sysMenu.getId(), sysMenuList, returnsysMenuList);
			}
		}
		return returnsysMenuList;
	}

	public static void getSubNodes(String parentId, List<SysMenu> sysMenuList, List<SysMenu> returnsysMenuList) {
		for (SysMenu sysMenu : sysMenuList) {
			if (Convert.toString(sysMenu.getNode()).equals(parentId) && Check.isNoEmpty(sysMenu.getMobileUrl())) {
				returnsysMenuList.add(sysMenu);
				getSubNodes(sysMenu.getId(), sysMenuList, returnsysMenuList);
			}
		}
	}

	public List<SysMenu> queryListByZmtb(List<UsUserMenuZmtb> desktopQuickList) {
		List<SysMenu> hhXtCdZmtbList = new ArrayList<SysMenu>();
		if (desktopQuickList.size() > 0) {
			List<String> cdidList = new ArrayList<String>();
			for (UsUserMenuZmtb usUserMenuZmtb : desktopQuickList) {
				cdidList.add(usUserMenuZmtb.getCdId());
			}
			hhXtCdZmtbList = queryListByIds(cdidList);
		}
		return hhXtCdZmtbList;
	}
}
