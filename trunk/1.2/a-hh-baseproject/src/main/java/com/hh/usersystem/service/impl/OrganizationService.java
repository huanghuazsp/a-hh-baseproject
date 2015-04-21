package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
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
import com.hh.usersystem.bean.usersystem.HhXtOrgJs;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhOrg;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class OrganizationService  extends BaseService<Organization>  {
	@Autowired
	private IHibernateDAO<HhXtYhOrg> hhXtYhOrgDAO;
	@Autowired
	private IHibernateDAO<HhXtYh> xtyhdao;
	@Autowired
	private IHibernateDAO<HhXtOrgJs> hhxtorgjsdao;
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	
	public List<Organization> queryTreeList(Organization object, boolean isNoLeaf) {
		return organizationToIconCls(queryTreeList(object.getNode(),isNoLeaf, ParamFactory.getParamHb()),null);
	}
	
	public List<Organization> queryTreeListByLx(Organization object, boolean isNoLeaf) {
		return organizationToIconCls(queryTreeList(object.getNode(),isNoLeaf, ParamFactory.getParamHb().le("lx_", object.getLx_())),null);
	}
	
	public List<Organization> organizationToIconCls(
			List<Organization> organizationList, String selectType) {
		for (Organization organization : organizationList) {
			if (organization.getChildren() != null) {
				organizationToIconCls(organization.getChildren(),selectType);
			}
			if (organization != null) {
				String clasString = organization.getLx_() == 0 ? "group"
						: organization.getLx_() == 1 ? "org" : organization
								.getLx_() == 2 ? "dept"
								: organization.getLx_() == 3 ? "job" : "";
				organization.setIconCls(clasString);
				organization.setIcon(organization.getLx_() == 0 ? "/hhcommon/images/myimage/org/group.png"
						: organization.getLx_() == 1 ? "/hhcommon/images/myimage/org/org.png" : organization
								.getLx_() == 2 ? "/hhcommon/images/myimage/org/dept.png"
								: organization.getLx_() == 3 ? "/hhcommon/images/myimage/org/job.png" : "");
				if (selectType!=null) {
					if (!selectType.equals(clasString)) {
						organization.setNocheck(true);
					}
				}
			}
		}
		return organizationList;
	}

	public PagingData<Organization> queryPagingData(Organization organization,
			PageRange pageRange) {
		return dao.queryPagingData(Organization.class, pageRange);
	}

	public List<Organization> queryOrgListByPidAndLx(Organization organization,
			String node) {
		String pid = !Check.isEmpty(node)
				&& "root".equals(organization.getNode()) ? node : organization
				.getNode();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		// pid = "root".equals(pid) ? "0" : pid;
		hqlParamList.is("node", pid);
		hqlParamList.is("lx_", organization.getLx_());
		// hqlParamList.add(Restrictions.eq("zt_", 0));
		// hqlParamList.add(Order.desc(StaticVar.ORDER));
		return organizationToIconCls(dao.queryTreeList(Organization.class,
				hqlParamList),null);
	}

	public List<Organization> queryOrgListByPid(Organization organization , String orgs, String selectType) {
		String node = organization.getNode();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		List<Organization> organizationList = null;
		if (Check.isNoEmpty(orgs)) {
			hqlParamList.in("id", Convert.strToList(orgs));
		}
		hqlParamList.is("zt_", 0);
		hqlParamList.is("node", node);
		hqlParamList.order("lx_");
		organizationList = dao.queryTreeList(Organization.class,
				hqlParamList);
		return organizationToIconCls(organizationList,selectType);
	}

	public Organization findObjectById(String id) {
		Organization organizationResult = dao.findEntityByPK(Organization.class, id);
		Organization organization = new Organization();
		if (organizationResult != null) {
			BeanUtils.copyProperties( organizationResult,organization);
			Organization p_organization = dao.findEntityByPK(
					Organization.class, organization.getNode());
			if (p_organization != null) {
				organization.setPname_(p_organization.getText());
				organization.setSjbm_(p_organization.getCode_());
				organization.setNode(p_organization.getId());
				organization.setCode_(organization.getCode_().replace(p_organization.getCode_(), ""));
			}
			List<HhXtOrgJs> hhXtYhJsList = hhxtorgjsdao.queryList(
					HhXtOrgJs.class, ParamFactory.getParamHb().is("orgId", id));
			
			String jss = "";
			
			for (HhXtOrgJs hhXtOrgJs : hhXtYhJsList) {
				jss+=hhXtOrgJs.getJsId()+",";
				organization.getJsList().add(hhXtOrgJs.getJsId());
			}
			if (Check.isNoEmpty(jss)) {
				jss.substring(0,jss.length()-1);
				organization.setJsIdsStr(jss);
			}
		}
		return organization;
	}

	public Organization save(Organization organization) throws MessageException {
		if (Check.isEmpty(organization.getNode())) {
			organization.setNode("root");
		}
		organization
				.setCode_(organization.getSjbm_() + organization.getCode_());
		if (checkCodeOnly(organization)) {
			throw new MessageException("编码重复，请更换！");
		}
		if (checkTextOnly(organization)) {
			throw new MessageException("同级下名称不能一样，请更换！");
		}
		if (organization.getId().equals(organization.getNode())) {
			throw new MessageException("父节点不能选择自己，请更换！");
		}
		if (checkParentNotLeaf(organization)) {
			throw new MessageException("父节点不能是自己的子节点，请更换！");
		}
		
		if (Check.isNoEmpty(organization.getNode()) && !"root".equals(organization.getNode())) {
			if (this.findObjectById(organization.getNode()).getLx_()>organization.getLx_()) {
				throw new MessageException("父节点的类型不能小于本节点的类型！");
			}
		}

		if (Check.isEmpty(organization.getId())) {
			organization.setId(UUID.randomUUID().toString());
			dao.createEntity(organization);
		} else {
			Organization old_organization = dao.findEntityByPK(
					Organization.class, organization.getId());
			if (organization.getId().equals(organization.getNode())) {
				throw new MessageException("不能选自己为上级！");
			}
			dao.updateEntity("update Organization o set  o.code_=  replace(o.code_,'"
					+ old_organization.getCode_()
					+ "','"
					+ organization.getCode_()
					+ "') where o.code_ like '"
					+ old_organization.getCode_()
					+ "%' and o.code_ !='"
					+ old_organization.getCode_() + "' ");

			dao.mergeEntity(organization);

			hhxtorgjsdao.deleteEntity(HhXtOrgJs.class, "orgId",
					organization.getId());
		}
		List<String> jsList = organization.getJsList();
		if (Check.isEmpty(jsList)) {
			jsList = Convert.strToList(organization.getJsIdsStr());
		}
		if (!Check.isEmpty(jsList)) {
			for (String jsid : jsList) {
				if (Check.isEmpty(jsid)) {
					continue;
				}
				HhXtOrgJs hhXtOrgJs = new HhXtOrgJs();
				hhXtOrgJs.setOrgId(organization.getId());
				hhXtOrgJs.setJsId(jsid);
				hhxtorgjsdao.createEntity(hhXtOrgJs);
			}
		}
		return organization;
	}

	private boolean checkCodeOnly(Organization organization) {
		return dao.findWhetherData("select count(o) from "
				+ organization.getClass().getName() + " o "
				+ "where o.code_=:code_ and (o.id!=:id or :id is null)",
				organization);
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteYzNode(idList);
		if (!Check.isEmpty(idList)) {
			dao.deleteEntity(Organization.class, "id", idList);
			hhXtYhOrgDAO.deleteEntity(HhXtYhOrg.class, "orgId", idList);
			hhxtorgjsdao.deleteEntity(HhXtOrgJs.class, "orgId", idList);
		}

	}

	private void deleteYzNode(List<String> idList) {
		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
			List<Organization> hhxtcdList = dao.queryList(Organization.class,
					Restrictions.in("node", idList));
			for (Organization hhXtCd : hhxtcdList) {
				yzIdList.add(hhXtCd.getId());
			}
			if (!Check.isEmpty(yzIdList)) {
				dao.deleteEntity(Organization.class, "id", yzIdList);
				hhXtYhOrgDAO.deleteEntity(HhXtYhOrg.class, "orgId", yzIdList);
				hhxtorgjsdao.deleteEntity(HhXtOrgJs.class, "orgId", yzIdList);
			}
			deleteYzNode(yzIdList);

		}

	}

	public List<Organization> queryOrgAndUsersList(Organization organization1) {
		List<Organization> organizations = this.queryOrgListByPid(organization1, null,null);
		for (Organization organization : organizations) {
			addOrgUser(organization);
		}
		return organizations;
	}

	private void addOrgUser(Organization organization) {
		if (organization.getChildren() != null) {
			for (Organization organization2 : organization.getChildren()) {
				addOrgUser(organization2);
			}
		}
			if (organization.getLx_() == 3) {
				List<HhXtYhOrg> hhXtYhOrgs = hhXtYhOrgDAO.queryList(
						HhXtYhOrg.class, ParamFactory.getParamHb().is("orgId",
										organization.getId()));
				List<String> yhid = new ArrayList<String>();
				for (HhXtYhOrg hhXtYhOrg : hhXtYhOrgs) {
					yhid.add(hhXtYhOrg.getYhId());
				}
				if (!Check.isEmpty(yhid)) {
					organization.setExpanded(1);
					List<HhXtYh> hhXtYhs = xtyhdao.queryList(HhXtYh.class,
							ParamFactory.getParamHb().in(
									"id", yhid));
					for (HhXtYh hhXtYh : hhXtYhs) {
						Organization extTree = new Organization();
						extTree.setId("{orgid : '" + organization.getId()
								+ "' ,userid : '" + hhXtYh.getId() + "'}");
						extTree.setText(hhXtYh.getText());
						if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
							extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
									: StaticProperties.HHXT_USERSYSTEM_NAN);
						} else {
							extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
						}
						extTree.setLeaf(1);
						
						if (organization.getChildren()==null) {
							organization.setChildren(	organizationToIconCls(this.queryList(Restrictions.eq("node", organization.getId())),null));
						}
						organization.getChildren().add(extTree);
					}
				}
		}
	}

	public List<Organization> queryCurrOrgTree(String node, String action) {
		List<Organization> organizations = new ArrayList<Organization>();
		if ("root".equals(node)) {
			organizations
					.add(loginUserUtilService.queryDataSecurityOrg(action));
		} else {
			ParamInf hqlParamList = ParamFactory.getParamHb();
			hqlParamList.likenoreg("code_", node + "___");
			organizations = dao.queryList(Organization.class, hqlParamList);
		}
		for (Organization organization : organizations) {
			if (organization != null) {
				organization.setId(organization.getCode_());
			}
		}
		return organizationToIconCls(organizations,null);
	}

}
