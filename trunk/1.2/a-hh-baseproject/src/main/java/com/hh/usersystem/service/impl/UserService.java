package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.hibernate.util.dto.PagingData;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.ExtTree;
import com.hh.system.util.dto.PageRange;
import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.hh.usersystem.bean.usersystem.HhXtYhCyLxr;
import com.hh.usersystem.bean.usersystem.HhXtYhGroup;
import com.hh.usersystem.bean.usersystem.HhXtYhJs;
import com.hh.usersystem.bean.usersystem.HhXtYhOrg;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class UserService extends BaseService<HhXtYh>{
	@Autowired
	private IHibernateDAO<HhXtYh> xtyhdao;
	@Autowired
	private IHibernateDAO<HhXtYhJs> hhXtYhJsDAO;
	@Autowired
	private IHibernateDAO<HhXtYhOrg> hhXtYhOrgDAO;
	@Autowired
	private IHibernateDAO<Organization> hhXtOrgDAO;

	@Autowired
	private IHibernateDAO<HhXtYhCdZmtb> xtyhcdzmtb;
	@Autowired
	private IHibernateDAO<HHXtZmsx> zmsxdao;

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private IHibernateDAO<HhXtYhCyLxr> cylxrdao;

	@Autowired
	private IHibernateDAO<HhXtYhGroup> hhxtyhGroup;

	public Map<? extends String, ? extends Object> queryOnLinePagingData(
			HhXtYh hhXtYh, PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("items", LoginUser.getLoginUserList());
		resultMap.put("total", LoginUser.getLoginUserCount());
		return resultMap;
	}

	public void deleteOnLineByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		for (String string : idList) {
			LoginUser.loginUserSession.get(string).invalidate();
			LoginUser.remove(string);
		}
	}

	public PagingData<HhXtYh> queryPagingData(HhXtYh hhXtYh,
			PageRange pageRange, String ids, String orgs, String roles,
			String groups) {
		HQLParamList hqlParamList = new HQLParamList();
		if (!Check.isEmpty(hhXtYh.getText())) {
			hqlParamList.add(Restrictions.like("text", "%" + hhXtYh.getText()
					+ "%"));
		}

		if (hhXtYh.getNxb() != 2) {
			hqlParamList.add(Restrictions.eq("nxb", hhXtYh.getNxb()));
		}

		List<String> idList = Convert.strToList(ids);

		boolean param = false;

		if (Check.isNoEmpty(orgs)) {
			param = true;
			idList.addAll(queryUserIdListByOrgCode(orgs));
		}

		if (Check.isNoEmpty(roles)) {
			param = true;
			idList.addAll(queryUserIdListByRole(roles));
		}

		if (Check.isNoEmpty(groups)) {
			param = true;
			idList.addAll(queryUserIdListByGroup(groups));
		}

		List<String> idParamList = new ArrayList<String>();
		for (String id : idList) {
			if (!idParamList.contains(id)) {
				idParamList.add(id);
			}
		}
		if (idParamList.size() == 0 && param == true) {
			return new PagingData<HhXtYh>();
		}
		if (Check.isNoEmpty(idParamList)) {
			hqlParamList.add(Restrictions.in("id", idParamList));
		}
		// tiaojian(orgs, roles, users, groups, hqlParamList);

		// if (roleCriterion != null && orgCriterion != null) {
		// hqlParamList.add(Restrictions.or(roleCriterion, orgCriterion));
		// } else if (roleCriterion != null) {
		// hqlParamList.add(roleCriterion);
		// } else if (orgCriterion != null) {
		// hqlParamList.add(orgCriterion);
		// }
		return xtyhdao.queryPagingData(HhXtYh.class, hqlParamList, pageRange);
	}

	private void tiaojian(String orgs, String roles, String users,
			String groups, HQLParamList hqlParamList) {
		List<Criterion> orCriterion = new ArrayList<Criterion>();

		if (Check.isNoEmpty(users)) {
			Criterion userCriterion = Restrictions.in("id",
					Convert.strToList(users));
			orCriterion.add(userCriterion);
		}

		if (Check.isNoEmpty(groups)) {
			List<String> groupIdList = Convert.strToList(groups);
			DetachedCriteria ownerCriteria = DetachedCriteria
					.forEntityName(HhXtYhGroup.class.getName());
			ownerCriteria.setProjection(Property.forName("yhId"));
			ownerCriteria.add(Restrictions.in("groupId", groupIdList));
			Criterion groupCriterion = Property.forName("id").in(ownerCriteria);
			orCriterion.add(groupCriterion);
		}

		if (!Check.isEmpty(orgs)) {
			List<String> orgIdList = Convert.strToList(orgs);
			List<Organization> organizations = hhXtOrgDAO.queryList(
					Organization.class, Restrictions.in("id", orgIdList));
			String hql = "select o from " + Organization.class.getName()
					+ " o where ";
			for (int i = 0; i < organizations.size(); i++) {
				if (i != 0) {
					hql += " or ";
				}
				hql += " code_ like '" + organizations.get(i).getCode_() + "%'";
			}
			List<Organization> organizationList = hhXtOrgDAO.queryList(hql);
			List<String> orgIdList2 = Convert.strToList(orgs);
			for (Organization organization : organizationList) {
				orgIdList2.add(organization.getId());
			}
			DetachedCriteria ownerCriteria = DetachedCriteria
					.forEntityName(HhXtYhOrg.class.getName());
			ownerCriteria.setProjection(Property.forName("yhId"));
			ownerCriteria.add(Restrictions.in("orgId", orgIdList2));
			Criterion orgCriterion = Property.forName("id").in(ownerCriteria);
			orCriterion.add(orgCriterion);
		}
		if (!Check.isEmpty(roles)) {
			List<String> roleIdList = Convert.strToList(roles);
			DetachedCriteria ownerCriteria = DetachedCriteria
					.forEntityName(HhXtYhJs.class.getName());
			ownerCriteria.setProjection(Property.forName("yhId"));
			ownerCriteria.add(Restrictions.in("jsId", roleIdList));
			Criterion roleCriterion = Property.forName("id").in(ownerCriteria);
			orCriterion.add(roleCriterion);
		}

		if (orCriterion.size() > 1) {
			Criterion criterion = null;
			for (int i = 1; i < orCriterion.size(); i++) {
				if (criterion == null) {
					criterion = Restrictions.or(orCriterion.get(i - 1),
							orCriterion.get(i));
				} else {
					criterion = Restrictions.or(criterion, orCriterion.get(i));
				}
			}
			if (criterion != null) {
				hqlParamList.add(criterion);
			}
		} else if (orCriterion.size() == 1) {
			hqlParamList.add(orCriterion.get(0));
		}
	}

	public HhXtYh save2(HhXtYh hhXtYh) throws MessageException {
		if (checkVdlzhOnly(hhXtYh)) {
			throw new MessageException("用户名已存在，请更换！");
		}
		if (Check.isEmpty(hhXtYh.getId())) {
			HHXtZmsx hhXtZmsx = new HHXtZmsx();
			hhXtYh.setHhXtZmsx(hhXtZmsx);
			hhXtYh.setVmm("123456");
			hhXtYh.setId(UUID.randomUUID().toString());
			hhXtZmsx.setId(hhXtYh.getId());
			hhXtZmsx.setDesktopType("jquerydesktop");
			hhXtZmsx.setTheme("base");
			xtyhdao.createEntity(hhXtYh);
		} else {
			xtyhdao.updateEntity(hhXtYh);
		}
		return hhXtYh;
	}

	public HhXtYh save(HhXtYh hhXtYh) throws MessageException {

		if (Check.isNoEmpty(hhXtYh.getId())) {
			hhXtYhJsDAO.deleteEntity(HhXtYhJs.class, "yhId", hhXtYh.getId());
			hhXtYhOrgDAO.deleteEntity(HhXtYhOrg.class, "yhId", hhXtYh.getId());
		}

		save2(hhXtYh);

		List<String> jsList = hhXtYh.getJsList();
		if (Check.isEmpty(jsList)) {
			jsList = Convert.strToList(hhXtYh.getJsIdsStr());
		}

		List<String> orgIdList = Convert.strToList(hhXtYh.getOrgIdsStr());
		if (Check.isNoEmpty(orgIdList)) {
			for (String orgId : orgIdList) {
				HhXtYhOrg hhXtYhOrg = new HhXtYhOrg();
				hhXtYhOrg.setYhId(hhXtYh.getId());
				hhXtYhOrg.setOrgId(orgId);
				hhXtYhOrgDAO.createEntity(hhXtYhOrg);
			}
		}

		if (!Check.isEmpty(jsList)) {
			for (String jsid : jsList) {
				if (Check.isEmpty(jsid)) {
					continue;
				}
				HhXtYhJs hhXtYhJs = new HhXtYhJs();
				hhXtYhJs.setYhId(hhXtYh.getId());
				hhXtYhJs.setJsId(jsid);
				// hhXtYh.getJsList().add(jsid);
				hhXtYhJsDAO.createEntity(hhXtYhJs);
			}
		}
		return hhXtYh;
	}

	public boolean checkVdlzhOnly(HhXtYh hhXtYh) {
		return xtyhdao
				.findWhetherData(
						"select count(o) from "
								+ hhXtYh.getClass().getName()
								+ " o "
								+ "where o.vdlzh=:vdlzh and (o.id!=:id or :id is null)",
						hhXtYh);
	}

	public HhXtYh findObjectById(String id) {
		HhXtYh hhXtYh = xtyhdao.findEntityByPK(HhXtYh.class, id);
		List<HhXtYhJs> hhXtYhJsList = hhXtYhJsDAO.queryList(HhXtYhJs.class,
				new HQLParamList().addCondition(Restrictions.eq("yhId", id)));

		String jsidsStr = "";
		for (HhXtYhJs hhXtYhJs : hhXtYhJsList) {
			hhXtYh.getJsList().add(hhXtYhJs.getJsId());
			jsidsStr += hhXtYhJs.getJsId() + ",";
		}

		if (Check.isNoEmpty(jsidsStr)) {
			jsidsStr = jsidsStr.substring(0, jsidsStr.length() - 1);
		}
		hhXtYh.setJsIdsStr(jsidsStr);
		// editHhXtYh_orgList(hhXtYh);
		hhXtYh.setOrgIdsStr(findOrgIdsStr(hhXtYh.getId()));
		return hhXtYh;
	}

	public HhXtYh findObjectById_user(String id) {
		HhXtYh hhXtYh = xtyhdao.findEntityByPK(HhXtYh.class, id);
		return hhXtYh;
	}

	private String findOrgIdsStr(String userid) {
		List<HhXtYhOrg> hhXtYhOrgs = hhXtYhOrgDAO
				.queryList(HhXtYhOrg.class, new HQLParamList()
						.addCondition(Restrictions.eq("yhId", userid)));
		String orgstr = "";
		for (HhXtYhOrg hhXtYhOrg : hhXtYhOrgs) {
			orgstr += hhXtYhOrg.getOrgId() + ",";
		}
		if (Check.isNoEmpty(orgstr)) {
			return orgstr.substring(0, orgstr.length() - 1);
		}
		return "";
	}

	public void editHhXtYh_orgList(HhXtYh hhXtYh) {
		List<String> orgList = new ArrayList<String>();
		List<HhXtYhOrg> hhXtYhOrgs = hhXtYhOrgDAO.queryList(
				HhXtYhOrg.class,
				new HQLParamList().addCondition(Restrictions.eq("yhId",
						hhXtYh.getId())));
		for (HhXtYhOrg hhXtYhOrg : hhXtYhOrgs) {
			orgList.add(hhXtYhOrg.getOrgId());
		}

		List<Organization> organizationList = new ArrayList<Organization>();

		if (!Check.isEmpty(orgList)) {
			organizationList = hhXtOrgDAO.queryList(Organization.class,
					new HQLParamList().addCondition(Restrictions.in("id",
							orgList)));
		}

		hhXtYh.setOrganizationList(organizationList);
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		if (!Check.isEmpty(idList)) {
			xtyhdao.deleteEntity(HhXtYh.class, "id", idList);
			hhXtYhJsDAO.deleteEntity(HhXtYhJs.class, "yhId", idList);
			xtyhcdzmtb.deleteEntity(HhXtYhCdZmtb.class, "yhId", idList);
			zmsxdao.deleteEntity(HHXtZmsx.class, "id", idList);
			hhXtYhOrgDAO.deleteEntity(HhXtYhOrg.class, "yhId", idList);
			cylxrdao.deleteEntity(HhXtYhCyLxr.class, "yhId", idList);
		}

	}

	public void insertYhOrg(Map<String, String> paramsMap) {
		HhXtYhOrg hhXtYhOrg = new HhXtYhOrg();
		hhXtYhOrg.setYhId(paramsMap.get("id1"));
		hhXtYhOrg.setOrgId(paramsMap.get("id2"));
		hhXtYhOrgDAO.createEntity(hhXtYhOrg);
	}

	public void deleteYhOrg(Map<String, String> paramsMap) {
		hhXtYhOrgDAO.deleteEntity(
				"delete HhXtYhOrg o where o.yhId = :id1 and  o.orgId = :id2",
				paramsMap);
	}

	public void updatePassWord(HhXtYh hhXtYh, String oldPass)
			throws MessageException {
		hhXtYh.setId(loginUserUtilService.findLoginUserId());
		boolean as = xtyhdao.isExist(
				"select count(o) from HhXtYh o where o.id=? and o.vmm=?",
				new Object[] { hhXtYh.getId(), oldPass });
		if (!as) {
			throw new MessageException("旧密码错误！");
		} else {
			xtyhdao.updateEntity(
					"update HhXtYh o set o.vmm=:vmm where o.id=:id", hhXtYh);
		}
	}

	public List<HhXtYh> queryCylxrs() {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		List<HhXtYhCyLxr> hhXtYhCyLxrs = cylxrdao.queryList(
				HhXtYhCyLxr.class,
				new HQLParamList().addCondition(Restrictions.eq("yhId",
						hhXtYh.getId())));

		List<String> cylxrIdList = new ArrayList<String>();
		for (HhXtYhCyLxr hhXtYhCyLxr : hhXtYhCyLxrs) {
			cylxrIdList.add(hhXtYhCyLxr.getCylxrId());
		}

		if (!Check.isEmpty(cylxrIdList)) {
			return xtyhdao.queryList(HhXtYh.class, new HQLParamList()
					.addCondition(Restrictions.in("id", cylxrIdList)));
		} else {
			return new ArrayList<HhXtYh>();
		}

	}

	public List<ExtTree> queryCylxrTree() {
		List<HhXtYh> hhXtYhs = this.queryCylxrs();
		List<ExtTree> extTrees = new ArrayList<ExtTree>();
		for (HhXtYh hhXtYh : hhXtYhs) {
			ExtTree extTree = new ExtTree();
			extTree.setId(hhXtYh.getId());
			extTree.setText(hhXtYh.getText());
			// extTree.setIcon(hhXtYh.getNxb() == 0 ?
			// StaticProperties.HHXT_USERSYSTEM_NV
			// : StaticProperties.HHXT_USERSYSTEM_NAN);
			if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
				extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
						: StaticProperties.HHXT_USERSYSTEM_NAN);
			} else {
				extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
			}
			extTree.setLeaf(1);
			extTrees.add(extTree);
		}
		return extTrees;
	}

	public void addCylxr(String string) throws MessageException {
		boolean as = cylxrdao.isExist(
				"select count(o) from HhXtYhCyLxr o where o.cylxrId = ?",
				new Object[] { string });
		if (as) {
			throw new MessageException("此人已经是您的常用联系人了！");
		} else {
			HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
			HhXtYhCyLxr hhXtYhCyLxr = new HhXtYhCyLxr();
			hhXtYhCyLxr.setCylxrId(string);
			hhXtYhCyLxr.setYhId(hhXtYh.getId());
			cylxrdao.createEntity(hhXtYhCyLxr);
		}
	}

	public void deleteCylxr(String string) throws MessageException {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("yhId", hhXtYh.getId());
		paramsMap.put("cylxrId", string);
		cylxrdao.deleteEntity(
				"delete HhXtYhCyLxr o where o.yhId=:yhId and o.cylxrId=:cylxrId",
				paramsMap);
	}

	public List<HhXtYh> queryListByIds(String[] users) {
		return xtyhdao.queryList(HhXtYh.class,
				new HQLParamList().addCondition(Restrictions.in("id", users)));
	}

	public List<HhXtYh> queryItemsByIdsStr(String ids) {
		if (Check.isEmpty(ids)) {
			return null;
		}
		return xtyhdao.queryList(
				HhXtYh.class,
				new HQLParamList().addCondition(Restrictions.in("id",
						Convert.strToList(ids))));
	}

	public List<HhXtYh> queryUserByOrcCode(String code) {
		List<String> yhIdList = queryUserIdListByOrgCode(code);
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<HhXtYh>();
		}
		List<HhXtYh> hhXtYhList = xtyhdao.queryList(HhXtYh.class,
				Restrictions.in("id", yhIdList));
		return hhXtYhList;
	}

	private List<String> queryUserIdListByOrgCode(String code) {
		List<Organization> organizations = hhXtOrgDAO.queryList(
				Organization.class, Restrictions.like("code_", code + "%"));
		List<String> orgIdList = new ArrayList<String>();
		for (Organization organization : organizations) {
			orgIdList.add(organization.getId());
		}
		if (Check.isEmpty(orgIdList)) {
			return new ArrayList<String>();
		}
		List<String> yhIdList = new ArrayList<String>();
		List<HhXtYhOrg> hhXtYhOrgList = hhXtYhOrgDAO.queryList(HhXtYhOrg.class,
				Restrictions.in("orgId", orgIdList));
		for (HhXtYhOrg hhXtYhOrg : hhXtYhOrgList) {
			yhIdList.add(hhXtYhOrg.getYhId());
		}
		return yhIdList;
	}

	public List<HhXtYh> queryUserByRole(String roleId) {
		List<String> yhIdList = queryUserIdListByRole(roleId);
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<HhXtYh>();
		}
		List<HhXtYh> hhXtYhList = xtyhdao.queryList(HhXtYh.class,
				Restrictions.in("id", yhIdList));
		return hhXtYhList;
	}

	private List<String> queryUserIdListByRole(String roleId) {
		List<String> yhIdList = new ArrayList<String>();
		List<HhXtYhJs> hhXtYhJList = hhXtYhJsDAO.queryList(HhXtYhJs.class,
				Restrictions.eq("jsId", roleId));
		for (HhXtYhJs hhXtYhJs : hhXtYhJList) {
			yhIdList.add(hhXtYhJs.getYhId());
		}
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<String>();
		}
		return yhIdList;
	}

	public List<HhXtYh> queryUserByGroup(String groupId) {
		List<String> yhIdList = queryUserIdListByGroup(groupId);
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<HhXtYh>();
		}
		List<HhXtYh> hhXtYhList = xtyhdao.queryList(HhXtYh.class,
				Restrictions.in("id", yhIdList));
		return hhXtYhList;
	}

	private List<String> queryUserIdListByGroup(String groupId) {
		List<String> yhIdList = new ArrayList<String>();
		List<HhXtYhGroup> hhXtYhJList = hhxtyhGroup.queryList(
				HhXtYhGroup.class, Restrictions.eq("groupId", groupId));
		for (HhXtYhGroup hhXtYhJs : hhXtYhJList) {
			yhIdList.add(hhXtYhJs.getYhId());
		}
		if (Check.isEmpty(yhIdList)) {
			return new ArrayList<String>();
		}
		return yhIdList;
	}

}
