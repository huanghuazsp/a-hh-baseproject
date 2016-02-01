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
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.bean.usersystem.HhXtGroup;
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.hh.usersystem.bean.usersystem.HhXtYhCyLxr;
import com.hh.usersystem.bean.usersystem.HhXtYhJs;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ActionContext;

@Service
public class UserService extends BaseService<HhXtYh> {
	@Autowired
	private IHibernateDAO<HhXtYh> xtyhdao;
	@Autowired
	private IHibernateDAO<HhXtYhJs> hhXtYhJsDAO;
	@Autowired
	private IHibernateDAO<Organization> hhXtOrgDAO;

	@Autowired
	private IHibernateDAO<HhXtYhCdZmtb> xtyhcdzmtb;

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private IHibernateDAO<HhXtYhCyLxr> cylxrdao;
	
	
	@Autowired
	private GroupService groupService;

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
		ParamInf hqlParamList = ParamFactory.getParamHb();
		if (!Check.isEmpty(hhXtYh.getText())) {
			hqlParamList.like("text", hhXtYh.getText());
		}

		if (hhXtYh.getNxb() != 2) {
			hqlParamList.add(Restrictions.eq("nxb", hhXtYh.getNxb()));
		}

		List<String> idList = Convert.strToList(ids);

		boolean param = false;

		if (Check.isNoEmpty(orgs)) {
			ParamInf hqlParamList2 = ParamFactory.getParamHb();
			hqlParamList2.is("orgId", orgs);
			ParamInf hqlParamList3 = ParamFactory.getParamHb();
			hqlParamList3.is("jobId", orgs);
			ParamInf hqlParamList4 = ParamFactory.getParamHb();
			hqlParamList4.is("deptId", orgs);
			ParamInf hqlParamList5 = ParamFactory.getParamHb();
			hqlParamList.or(hqlParamList5.or(hqlParamList2,hqlParamList3),hqlParamList4);
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
			String groups, ParamInf hqlParamList) {
		List<Criterion> orCriterion = new ArrayList<Criterion>();

		if (Check.isNoEmpty(users)) {
			Criterion userCriterion = Restrictions.in("id",
					Convert.strToList(users));
			orCriterion.add(userCriterion);
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
//			HHXtZmsx hhXtZmsx = new HHXtZmsx();
//			hhXtYh.setHhXtZmsx(hhXtZmsx);
			hhXtYh.setVmm("123456");
			hhXtYh.setId(UUID.randomUUID().toString());
			hhXtYh.setDesktopType("jquerydesktop");
			hhXtYh.setTheme("base");
			xtyhdao.createEntity(hhXtYh);
		} else {
			xtyhdao.updateEntity(hhXtYh);
		}
		return hhXtYh;
	}

	public HhXtYh save(HhXtYh hhXtYh) throws MessageException {

		if (Check.isNoEmpty(hhXtYh.getId())) {
			hhXtYhJsDAO.deleteEntity(HhXtYhJs.class, "yhId", hhXtYh.getId());
		}

		save2(hhXtYh);

		List<String> jsList = hhXtYh.getJsList();
		if (Check.isEmpty(jsList)) {
			jsList = Convert.strToList(hhXtYh.getJsIdsStr());
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
				ParamFactory.getParamHb().is("yhId", id));

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
//		hhXtYh.setOrgIdsStr(findOrgIdsStr(hhXtYh.getId()));
		return hhXtYh;
	}

	public HhXtYh findObjectById_user(String id) {
		HhXtYh hhXtYh = xtyhdao.findEntityByPK(HhXtYh.class, id);
		return hhXtYh;
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		if (!Check.isEmpty(idList)) {
			xtyhdao.deleteEntity(HhXtYh.class, "id", idList);
			hhXtYhJsDAO.deleteEntity(HhXtYhJs.class, "yhId", idList);
			xtyhcdzmtb.deleteEntity(HhXtYhCdZmtb.class, "yhId", idList);
//			zmsxdao.deleteEntity(HHXtZmsx.class, "id", idList);
			cylxrdao.deleteEntity(HhXtYhCyLxr.class, "yhId", idList);
		}

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
				ParamFactory.getParamHb().is("yhId",
						hhXtYh.getId()));

		List<String> cylxrIdList = new ArrayList<String>();
		for (HhXtYhCyLxr hhXtYhCyLxr : hhXtYhCyLxrs) {
			cylxrIdList.add(hhXtYhCyLxr.getCylxrId());
		}

		if (!Check.isEmpty(cylxrIdList)) {
			return xtyhdao.queryList(HhXtYh.class, ParamFactory.getParamHb().in("id", cylxrIdList));
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
				ParamFactory.getParamHb().in("id", users));
	}

	public List<HhXtYh> queryItemsByIdsStr(String ids) {
		if (Check.isEmpty(ids)) {
			return null;
		}
		return xtyhdao.queryList(
				HhXtYh.class,
				ParamFactory.getParamHb().in("id",
						Convert.strToList(ids)));
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
		List<HhXtYh> hhXtYhList = this.queryList(ParamFactory.getParamHb().in("orgId", orgIdList));
		for (HhXtYh hhXtYh : hhXtYhList) {
			yhIdList.add(hhXtYh.getId());
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
		HhXtGroup hhXtGroup = groupService.findObjectById(groupId);
		if (hhXtGroup!=null && Check.isNoEmpty(hhXtGroup.getUsers())) {
			yhIdList = Convert.strToList(hhXtGroup.getUsers());
		}
		return yhIdList;
	}
	
	
	public void updateZmbj(HhXtYh hhXtZmsx) {
		hhXtZmsx.setId(loginUserUtilService.findLoginUser().getId());
		dao.updateEntity("update " + HhXtYh.class.getName()
				+ " o set o.vzmbj=:vzmbj where o.id=:id", hhXtZmsx);
	}

	public void updateDefaultOrg(String userId, String orgid) {
		dao.updateEntity("update " + HhXtYh.class.getName()
				+ " o set o.defaultOrgId=? where o.id=?", new Object[] { orgid,
				userId });
	}

	public void updateTheme(HhXtYh hhXtZmsx) {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		hhXtZmsx.setId(hhXtYh.getId());
		dao.updateEntity("update " + HhXtYh.class.getName()
				+ " o set o.theme=:theme where o.id=:id", hhXtZmsx);
		hhXtYh.setTheme(hhXtZmsx.getTheme());
		ActionContext.getContext().getSession().put("loginuser", hhXtYh);
	}

}
