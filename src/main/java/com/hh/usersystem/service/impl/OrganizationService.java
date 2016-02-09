package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.PrimaryKey;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class OrganizationService extends BaseService<UsOrganization> {
	@Autowired
	private IHibernateDAO<UsUser> xtyhdao;
	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Autowired
	private RoleService roleService;

	public List<UsOrganization> queryTreeList(UsOrganization object,
			boolean isNoLeaf) {
		return organizationToIconCls(
				queryTreeList(object.getNode(), isNoLeaf,
						ParamFactory.getParamHb()), null);
	}
	
	public List<UsOrganization> queryTreeList(ParamInf paramList) {
		
		return organizationToIconCls(super.queryTreeList( paramList), null);
	}


	public List<UsOrganization> queryTreeListByLx(UsOrganization object,
			boolean isNoLeaf) {
		return organizationToIconCls(
				queryTreeList(object.getNode(), isNoLeaf, ParamFactory
						.getParamHb().le("lx_", object.getLx_())), null);
	}

	public List<UsOrganization> organizationToIconCls(
			List<UsOrganization> organizationList, String selectType) {
		for (UsOrganization organization : organizationList) {
			if (organization.getChildren() != null) {
				organizationToIconCls(organization.getChildren(), selectType);
			}
			if (organization != null) {
				String clasString = organization.getLx_() == 0 ? "group"
						: organization.getLx_() == 1 ? "org" : organization
								.getLx_() == 2 ? "dept"
								: organization.getLx_() == 3 ? "job" : "";
				organization.setIconCls(clasString);
				organization
						.setIcon(organization.getLx_() == 0 ? "/hhcommon/images/myimage/org/group.png"
								: organization.getLx_() == 1 ? "/hhcommon/images/myimage/org/org.png"
										: organization.getLx_() == 2 ? "/hhcommon/images/myimage/org/dept.png"
												: organization.getLx_() == 3 ? "/hhcommon/images/myimage/org/job.png"
														: "");
				if (selectType != null) {
					if (!selectType.equals(clasString)) {
						organization.setNocheck(true);
					}
				}
			}
		}
		return organizationList;
	}

	public PagingData<UsOrganization> queryPagingData(UsOrganization organization,
			PageRange pageRange) {
		return dao.queryPagingData(UsOrganization.class, pageRange);
	}

	public List<UsOrganization> queryOrgListByPidAndLx(UsOrganization organization,
			String node) {
		String pid = !Check.isEmpty(node)
				&& "root".equals(organization.getNode()) ? node : organization
				.getNode();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hqlParamList.is("node", pid);
		hqlParamList.nis("state", 1);
		hqlParamList.is("lx_", organization.getLx_());
		return organizationToIconCls(
				dao.queryTreeList(UsOrganization.class, hqlParamList), null);
	}

	public List<UsOrganization> queryOrgListByPid(UsOrganization organization,
			String orgs, String selectType) {
		String node = organization.getNode();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		List<UsOrganization> organizationList = null;
		if (Check.isNoEmpty(orgs)) {
			hqlParamList.in("id", Convert.strToList(orgs));
		}
		hqlParamList.is("node", node);
		hqlParamList.nis("lx_", 3);
		hqlParamList.nis("state", 1);
		hqlParamList.order("lx_");
		organizationList = dao.queryTreeList(UsOrganization.class, hqlParamList);
		return organizationToIconCls(organizationList, selectType);
	}

	public UsOrganization findObjectById(String id) {
		UsOrganization organizationResult = dao.findEntityByPK(
				UsOrganization.class, id);
		return organizationResult;
	}

	public UsOrganization save(UsOrganization organization) throws MessageException {

		if (Check.isEmpty(organization.getNode())) {
			organization.setNode("root");
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

		if (Check.isNoEmpty(organization.getNode())
				&& !"root".equals(organization.getNode())) {
			if (this.findObjectById(organization.getNode()).getLx_() > organization
					.getLx_()) {
				throw new MessageException("父节点的类型不能小于本节点的类型！");
			}
		}

		if (Check.isEmpty(organization.getId())) {
			organization.setId(UUID.randomUUID().toString());
			dao.createEntity(organization);
		} else {
			if (organization.getId().equals(organization.getNode())) {
				throw new MessageException("不能选自己为上级！");
			}
			dao.mergeEntity(organization);
		}
//		UsOrganization parentOrganization = new UsOrganization();
//		parentOrganization.setId("root");
//
//		if (!"root".equals(organization.getNode())) {
//			parentOrganization = dao.findEntityByPK(UsOrganization.class,
//					organization.getNode());
//		}
//		updateSubCode(parentOrganization);
		return organization;
	}
	
	@Transactional
	public void restCode(String node) {
		UsOrganization parentOrganization = new UsOrganization();
		parentOrganization.setId(node);
		if (!"root".equals(node)) {
			parentOrganization = findObjectById(node);
		}
		updateSubCode(parentOrganization);
	}

	public void updateSubCode(UsOrganization parentOrganization) {
		if (Check.isNoEmpty(parentOrganization)) {
			List<UsOrganization> orgList = dao.queryList(
					UsOrganization.class,
					ParamFactory.getParamHb().is("node",
							parentOrganization.getId()));
			int i = 1;
			for (UsOrganization organization : orgList) {
				organization.setCode_(Convert.toString(parentOrganization
						.getCode_()) + Convert.complete(i, 3, 0));
				i++;
				dao.updateEntity(organization);
				updateSubCode(organization);
			}
		}
	}

	public void deleteByIds(String ids) {
		List<String> idList = Convert.strToList(ids);
		deleteYzNode(idList);
		if (!Check.isEmpty(idList)) {

			List<String> nodeList = new ArrayList<String>();
			List<UsOrganization> hhxtcdList = dao.queryList(UsOrganization.class,
					Restrictions.in("id", Convert.strToList(ids)));
			for (UsOrganization organization2 : hhxtcdList) {
				nodeList.add(organization2.getNode());
			}

			dao.deleteEntity(UsOrganization.class, "id", idList);

//			List<UsOrganization> organizations = dao.queryList(
//					UsOrganization.class,
//					ParamFactory.getParamHb().in("id", nodeList));
//			if (nodeList.contains("root")) {
//				UsOrganization parentOrganization = new UsOrganization();
//				parentOrganization.setId("root");
//				updateSubCode(parentOrganization);
//			}
//			for (UsOrganization organization2 : organizations) {
//				updateSubCode(organization2);
//			}

		}

	}

	private void deleteYzNode(List<String> idList) {
		List<String> yzIdList = new ArrayList<String>();
		if (!Check.isEmpty(idList)) {
			List<UsOrganization> hhxtcdList = dao.queryList(UsOrganization.class,
					Restrictions.in("node", idList));
			for (UsOrganization hhXtCd : hhxtcdList) {
				yzIdList.add(hhXtCd.getId());
			}
			if (!Check.isEmpty(yzIdList)) {
				dao.deleteEntity(UsOrganization.class, "id", yzIdList);
			}
			deleteYzNode(yzIdList);

		}

	}

	public List<UsOrganization> queryOrgAndUsersList(UsOrganization organization1) {
		List<UsOrganization> organizations = this.queryOrgListByPid(
				organization1, null, null);
		for (UsOrganization organization : organizations) {
			addOrgUser(organization);
		}
		return organizations;
	}

	private void addOrgUser(UsOrganization organization) {
		if (organization.getChildren() != null) {
			for (UsOrganization organization2 : organization.getChildren()) {
				addOrgUser(organization2);
			}
		}
		List<UsUser> hhXtYhs = xtyhdao.queryList(UsUser.class, ParamFactory
				.getParamHb().in("deptId", organization.getId().split(",")));
		if (!Check.isEmpty(hhXtYhs)) {
			organization.setExpanded(1);
			for (UsUser hhXtYh : hhXtYhs) {
				UsOrganization extTree = new UsOrganization();
				extTree.setId(hhXtYh.getId());
				extTree.setText(hhXtYh.getText());
				if (LoginUser.getLoginUserId().contains(hhXtYh.getId())) {
					extTree.setIcon(hhXtYh.getNxb() == 0 ? StaticProperties.HHXT_USERSYSTEM_NV
							: StaticProperties.HHXT_USERSYSTEM_NAN);
				} else {
					extTree.setIcon(StaticProperties.HHXT_NO_ON_LINE_USER_ICON);
				}
				extTree.setLeaf(1);

				if (organization.getChildren() == null) {
					organization
							.setChildren(organizationToIconCls(
									this.queryList(ParamFactory.getParamHb()
											.is("node", organization.getId())
											.nis("lx_", 3).nis("state", 1)
											.order("lx_")), null));
				}
				organization.getChildren().add(extTree);
			}
		}
	}

	public List<UsOrganization> queryCurrOrgTree(String node, String action) {
		List<UsOrganization> organizations = new ArrayList<UsOrganization>();
		if ("root".equals(node)) {
			organizations
					.add(loginUserUtilService.queryDataSecurityOrg(action));
		} else {
			ParamInf hqlParamList = ParamFactory.getParamHb();
			hqlParamList.likenoreg("code_", node + "___");
			organizations = dao.queryList(UsOrganization.class, hqlParamList);
		}
		for (UsOrganization organization : organizations) {
			if (organization != null) {
				organization.setId(organization.getCode_());
			}
		}
		return organizationToIconCls(organizations, null);
	}

	public void save(List<Map<String, Object>> mapList) {
		Map<String, String> orgMapNameId = new HashMap<String, String>();
		Map<String, String> roleMapNameId = new HashMap<String, String>();
		for (Map<String, Object> map : mapList) {
			
			String node = "root";
			String sjmc = Convert.toString(map.get("上级名称"));
			if (Check.isNoEmpty(sjmc)) {
				if (!orgMapNameId.containsKey(sjmc)) {
					List<UsOrganization> organizationList = queryListByProperty(
							"text", sjmc);
					if (organizationList.size() > 0) {
						orgMapNameId.put(sjmc, organizationList.get(0).getId());
					} else {
						orgMapNameId.put(sjmc, "root");
					}
				}

				node = orgMapNameId.get(sjmc);
			} 
			
			
			UsOrganization organization = null;
			String id = PrimaryKey.getPrimaryKeyUUID();
			String name = Convert.toString(map.get("名称"));
			if (Check.isNoEmpty(map.get("标识"))) {
				id = Convert.toString(map.get("标识"));
				organization = findObjectById(id);
			} else {
				List<UsOrganization> organizations = queryList(ParamFactory.getParamHb().is("text", name).is("node", node));
				if (organizations.size() > 0) {
					organization = organizations.get(0);
				}
			}

			int iscreate = 0;

			if (organization == null) {
				iscreate = 1;
				organization = new UsOrganization();
				organization.setId(id);
			} 

			organization.setText(name);
			organization.setJc_(Convert.toString(map.get("简称")));
			organization.setZdybm_(Convert.toString(map.get("自定义编码")));
			organization.setMs_(Convert.toString(map.get("备注")));
			int zt = 0;
			if ("禁用".equals(Convert.toString(map.get("状态")))) {
				zt = 1;
			}
			organization.setState(zt);
			int lx = 1;
			// if ("集团".equals(Convert.toString(map.get("类型")))) {
			// lx=0;
			// }else
			if ("部门".equals(Convert.toString(map.get("类型")))) {
				lx = 2;
			} else if ("岗位".equals(Convert.toString(map.get("类型")))) {
				lx = 3;
			}
			organization.setLx_(lx);

			organization.setNode(node);

			String jgjs = Convert.toString(map.get("机构角色"));
			if (Check.isNoEmpty(jgjs)) {
				if (roleMapNameId.keySet().contains(jgjs)) {
					organization.setRoleIds(roleMapNameId.get(jgjs));
				}else {
					String[] jsArr = jgjs.split(",");
					List<UsRole> usRoles = roleService.queryListByProperty(
							"text", Convert.arrayToList(jsArr));
					String roleIds  = Convert.objectListToString(usRoles, "id");
					organization.setRoleIds(roleIds);
					roleMapNameId.put(jgjs,roleIds);
				}
			}
			if (iscreate == 1) {
				createEntity(organization);
			} else {
				getDao().updateEntity(organization);
			}

		}

//		UsOrganization parentOrganization = new UsOrganization();
//		parentOrganization.setId("root");
//		updateSubCode(parentOrganization);
	}

}
