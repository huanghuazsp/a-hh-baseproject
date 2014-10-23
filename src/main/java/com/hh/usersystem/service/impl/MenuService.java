package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.hibernate.util.dto.PagingData;
import com.hh.system.service.impl.BaseTreeService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.ExtCheckTree;
import com.hh.system.util.dto.PageRange;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtJsCd;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.hh.usersystem.bean.usersystem.Organization;
import com.opensymphony.xwork2.ActionContext;

@Service
public class MenuService extends BaseTreeService<HhXtCd> {
	@Autowired
	private IHibernateDAO<HhXtCd> dao;
	@Autowired
	private IHibernateDAO<HhXtJsCd> xtjscddao;
	@Autowired
	private IHibernateDAO<HhXtYhCdZmtb> xtyhcdzmtb;
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Autowired
	private IHibernateDAO<HhXtCz> czdao;
	@Autowired
	private LoginUserUtilService loginUserService;
	@Autowired
	private ZmtbService zmtbService;
	@Autowired
	private OperateService operateService;
	
	public PagingData<HhXtCd> queryPagingData(HhXtCd hhXtCd, PageRange pageRange) {
		HQLParamList hqlParamList = new HQLParamList();
		if (!Check.isEmpty(hhXtCd.getText())) {
			hqlParamList.add(Restrictions.like("text", "%" + hhXtCd.getText()
					+ "%"));
		}
		if (!Check.isEmpty(hhXtCd.getLeaf())) {
			hqlParamList.add(Restrictions.eq("leaf", 1));
		}

		if (!Check.isEmpty(hhXtCd.getNode())) {
			hqlParamList.add(Restrictions.eq("node", hhXtCd.getNode()));
		}

		if (!Check.isEmpty(hhXtCd.getOrgCode())) {
			DetachedCriteria ownerCriteria = DetachedCriteria
					.forEntityName(Organization.class.getName());
			ownerCriteria.setProjection(Property.forName("id"));
			ownerCriteria.add(Restrictions.like("code_", hhXtCd.getOrgCode()
					+ "%"));
			hqlParamList.add(Restrictions.or(
					Property.forName("vorgid").in(ownerCriteria), Property
							.forName("vorgid").isNull()));
		}
		return dao.queryPagingData(HhXtCd.class, hqlParamList, pageRange);
	}

	public HhXtCd findObjectById(String id) {
		HhXtCd hhXtCd = dao.findEntityByPK(HhXtCd.class, id);
		if (!"root".equals(hhXtCd.getNode())) {
			HhXtCd parenthhXtCd = dao.findEntityByPK(HhXtCd.class,
					hhXtCd.getNode());
			hhXtCd.setVpname(parenthhXtCd.getText());
		}
		return hhXtCd;
	}
	public HhXtCd findObjectById2(String id) {
		HhXtCd hhXtCd = dao.findEntityByPK(HhXtCd.class, id);
		return hhXtCd;
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteYzNode(idList);
		if (!Check.isEmpty(idList)) {
			// czdao.deleteEntity(HhXtCz.class,"node",idList);
			xtjscddao.deleteEntity(HhXtJsCd.class, "cdId", idList);
			xtyhcdzmtb.deleteEntity(HhXtYhCdZmtb.class, "cdId", idList);
			dao.deleteEntity(HhXtCd.class, "id", idList);
		}

	}

	private void deleteYzNode(List<String> idList) {

		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
		 	operateService.deleteByPidList(idList);
			List<HhXtCd> hhxtcdList = dao.queryList(HhXtCd.class,
					Restrictions.in("node", idList));
			for (HhXtCd hhXtCd : hhxtcdList) {
				yzIdList.add(hhXtCd.getId());
			}
			deleteYzNode(yzIdList);
			if (!Check.isEmpty(yzIdList)) {
				xtjscddao.deleteEntity(HhXtJsCd.class, "cdId", yzIdList);
				xtyhcdzmtb.deleteEntity(HhXtYhCdZmtb.class, "cdId", yzIdList);
				dao.deleteEntity(HhXtCd.class, "id", yzIdList);
			}
		}

	}

	public List<HhXtCd> queryMenuListByPid(String node) {
		List<HhXtCd> hhxtcdList = new ArrayList<HhXtCd>();
		HQLParamList hqlParamList = new HQLParamList()
				.addCondition(Restrictions.eq("leaf", 0));
		if (Check.isEmpty(node) || "root".equals(node)) {
			hhxtcdList = treedao.queryTreeList(HhXtCd.class,
					hqlParamList.addCondition(Restrictions.eq("node", "root")));
		} else {
			hhxtcdList = treedao.queryTreeList(HhXtCd.class,
					hqlParamList.addCondition(Restrictions.eq("node", node)));
		}

		return hhxtcdList;
	}

	public List<ExtCheckTree> queryMenuAllListByPid(String node, String roleid) {
		List<HhXtCd> hhxtcdList = new ArrayList<HhXtCd>();
		HQLParamList hqlParamList = new HQLParamList();
		if (Check.isEmpty(node) || "root".equals(node)) {
			hhxtcdList = treedao.queryAllTreeList(HhXtCd.class,
					hqlParamList.addCondition(Restrictions.eq("node", "root")));
		} else {
			hhxtcdList = treedao.queryAllTreeList(HhXtCd.class,
					hqlParamList.addCondition(Restrictions.eq("node", node)));
		}
		List<ExtCheckTree> extTreeList = new ArrayList<ExtCheckTree>();

		List<String> cdidList = new ArrayList<String>();

		for (HhXtCd hhXtCd : hhxtcdList) {
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
			HQLParamList hqlParamList2 = new HQLParamList();
			hqlParamList2.add(Restrictions.in("cdId", cdidList));
			hqlParamList2.add(Restrictions.eq("jsId", roleid));
			List<HhXtJsCd> hhXtJsCdList = xtjscddao.queryList(HhXtJsCd.class,
					hqlParamList2);

			List<String> jscdidList = new ArrayList<String>();
			if (!Check.isEmpty(hhXtJsCdList)) {
				for (HhXtJsCd hhXtJsCd : hhXtJsCdList) {
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

	private void checkMenuByCdId(List<ExtCheckTree> children,
			List<String> jscdidList) {
		for (ExtCheckTree extCheckTree : children) {
			if (jscdidList.contains(extCheckTree.getId())) {
				extCheckTree.setChecked(true);
			}
			if (extCheckTree.getChildren() != null) {
				checkMenuByCdId(extCheckTree.getChildren(), jscdidList);
			}
		}
	}

	private void addChildren(ExtCheckTree parentextTree, List<HhXtCd> children,
			List<String> cdidList) {
		for (HhXtCd hhXtCd : children) {
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

	public void addZmtb(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String id : idList) {
			HhXtYhCdZmtb hhXtYhCdZmtb = new HhXtYhCdZmtb();
			HhXtCd hhXtCd = dao.findEntityByPK(HhXtCd.class, id);
			hhXtYhCdZmtb.setHhXtCd(hhXtCd);
			hhXtYhCdZmtb.setYhId(loginUserUtilService.findLoginUser().getId());
			xtyhcdzmtb.createEntity(hhXtYhCdZmtb);
		}
		HhXtYh hhXtYh = loginUserService.findLoginUser();
		hhXtYh.setHhXtYhCdZmtbList(zmtbService.queryZmtbByUserId(hhXtYh.getId()));
		ActionContext.getContext().getSession().put("loginuser", hhXtYh);
	}

	public void deleteZmtb(String id) {
		if (Check.isNoEmpty(id)) {
			xtyhcdzmtb.deleteEntity(HhXtYhCdZmtb.class, "cdId",
					Convert.strToList(id));
			HhXtYh hhXtYh = loginUserService.findLoginUser();
			hhXtYh.setHhXtYhCdZmtbList(zmtbService.queryZmtbByUserId(hhXtYh.getId()));
			ActionContext.getContext().getSession().put("loginuser", hhXtYh);
		}
	}
}
