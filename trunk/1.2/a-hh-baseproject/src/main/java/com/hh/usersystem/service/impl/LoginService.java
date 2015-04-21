package com.hh.usersystem.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.util.Check;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.model.MsgProperties;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtJs;
import com.hh.usersystem.bean.usersystem.HhXtJsCd;
import com.hh.usersystem.bean.usersystem.HhXtJsCz;
import com.hh.usersystem.bean.usersystem.HhXtOrgJs;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.bean.usersystem.HhXtYhCdZmtb;
import com.hh.usersystem.bean.usersystem.HhXtYhJs;
import com.hh.usersystem.bean.usersystem.Organization;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ActionContext;

@Service
public class LoginService {
	@Autowired
	private IHibernateDAO<HhXtYhJs> xtyhjsdao;
	@Autowired
	private IHibernateDAO<HhXtYh> xtyhdao;
	@Autowired
	private IHibernateDAO<HhXtJs> xtjsdao;
	@Autowired
	private IHibernateDAO<HhXtJsCd> xtjscddao;
	@Autowired
	private IHibernateDAO<HhXtJsCz> xtjsczdao;
	@Autowired
	private IHibernateDAO<HhXtYhCdZmtb> xtyhcdzmtb;
	@Autowired
	private IHibernateDAO<Organization> hhXtOrgDAO;
	@Autowired
	private IHibernateDAO<HhXtCd> hhxtcdDao;
	@Autowired
	private UserService userService;
	@Autowired
	private IHibernateDAO<HhXtOrgJs> hhxtorgjsdao;
	private static final Logger logger = Logger.getLogger(BaseAction.class);

	public ReturnModel savefindLogin(HhXtYh xtYh) {
		ReturnModel returnModel = new ReturnModel();
		List<HhXtYh> xtYhList = xtyhdao.queryList(
				HhXtYh.class,
				ParamFactory.getParamHb().is("vdlzh",
						xtYh.getVdlzh()));
		if (Check.isEmpty(xtYhList)) {
			returnModel.setType(ReturnModel.TYPE_OK);
			returnModel.setMsg(MsgProperties.system_userlogin_notexist);
		} else {
			if (xtYhList.size() > 1) {
				returnModel.setMsg(MsgProperties.system_userlogin_repeat);
			} else {
				if (LoginUser.loginUserMap.containsKey(xtYhList.get(0).getId())) {
					logger.info("用户已经在线（" + xtYhList.get(0).getText() + "："
							+ xtYhList.get(0).getVdlzh() + "），重新登录！！");
					if (ActionContext.getContext().getSession()
							.get("loginuser") == null) {
						LoginUser.loginUserSession.get(xtYhList.get(0).getId())
								.invalidate();
					}
				}
				if (xtYhList.get(0).getVmm().equals(xtYh.getVmm())) {
					returnModel.setTitleMsg(MsgProperties.system_please_later);
					returnModel.setMsg(MsgProperties.system_is_jump);

					HhXtYh hhXtYh = xtYhList.get(0);

					if (hhXtYh.getNzt() == 1) {
						returnModel.setMsg("您的账号已经被冻结，请联系管理员！");
						return returnModel;
					}

					String desktop = "desktop";
					if (Check.isNoEmpty(hhXtYh.getHhXtZmsx().getDesktopType())) {
						desktop = hhXtYh.getHhXtZmsx().getDesktopType();
					}
					returnModel.setHref("webapp-desktop-" + desktop);

					List<HhXtYhJs> hhXtYhJsList = xtyhjsdao.queryList(
							HhXtYhJs.class, ParamFactory.getParamHb().is("yhId",
											hhXtYh.getId()));

					List<String> jsids = new ArrayList<String>();
					for (HhXtYhJs hhXtYhJs : hhXtYhJsList) {
						if (!jsids.contains(hhXtYhJs.getJsId())) {
							jsids.add(hhXtYhJs.getJsId());
						}
					}

					Organization organization = addGw(hhXtYh);

					if (organization != null) {
						List<String> orgIdList = new ArrayList<String>();
						orgIdList.add(organization.getId());
						if (organization.getJt() != null) {
							orgIdList.add(organization.getJt().getId());
						}
						if (organization.getJg() != null) {
							orgIdList.add(organization.getJg().getId());
						}
						if (organization.getBm() != null) {
							orgIdList.add(organization.getBm().getId());
						}

						List<HhXtOrgJs> hhXtOrgJsList = hhxtorgjsdao.queryList(
								HhXtOrgJs.class, "orgId", orgIdList);
						for (HhXtOrgJs hhXtOrgJs : hhXtOrgJsList) {
							if (!jsids.contains(hhXtOrgJs.getJsId())) {
								jsids.add(hhXtOrgJs.getJsId());
							}
						}
					}

					List<HhXtJs> hhXtJsList = new ArrayList<HhXtJs>();
					List<HhXtJsCd> hhXtJsCdList = new ArrayList<HhXtJsCd>();
					List<HhXtJsCz> hhXtJsCzList = new ArrayList<HhXtJsCz>();
					if (jsids.size() != 0) {
						hhXtJsList = xtjsdao.queryList(
								HhXtJs.class,
								ParamFactory.getParamHb().is("nzt", 0)
										.in("id", jsids));
						if (hhXtJsList.size() != 0) {
							hhXtJsCdList = xtjscddao.queryList(HhXtJsCd.class,
									ParamFactory.getParamHb().in(
													"jsId", jsids));
							hhXtJsCzList = xtjsczdao.queryList(HhXtJsCz.class,
									ParamFactory.getParamHb().in(
													"jsId", jsids));
						}
					}

					List<HhXtCz> hhXtCzList = new ArrayList<HhXtCz>();
					List<HhXtCd> hhXtCdZmtbList = new ArrayList<HhXtCd>();

					List<String> hhxtcdIdList = new ArrayList<String>();

					for (HhXtJsCd hhXtJsCd : hhXtJsCdList) {
						hhxtcdIdList.add(hhXtJsCd.getCdId());
					}
					List<HhXtCd> hhXtCdList = new ArrayList<HhXtCd>();
					if (hhxtcdIdList.size() > 0) {
						hhXtCdList = hhxtcdDao.queryList(HhXtCd.class, "id",
								hhxtcdIdList);
					}
					// 请求的控制
					List<String> hhXtCzUrlList = new ArrayList<String>();
					// 根据字符串控制页面
					List<String> hhXtCzPageTextList = new ArrayList<String>();
					Map<String, List<String>> hhXtCzPageTextMap = new HashMap<String, List<String>>();

					// 多个角色 获取权限最大的操作级别
					for (HhXtJsCz hhXtJsCz : hhXtJsCzList) {
						HhXtCz hhXtCz = hhXtJsCz.getHhXtCz();
						if (Check.isEmpty(hhXtCz.getOperLevel())) {
							hhXtCz.setOperLevel(hhXtJsCz.getOperLevel());
						} else {
							if (StaticProperties.findOperationLevel(hhXtJsCz
									.getOperLevel()) > StaticProperties
									.findOperationLevel(hhXtCz.getOperLevel())) {
								hhXtCz.setOperLevel(hhXtJsCz.getOperLevel());
							}
						}
						if (!hhXtCzUrlList.contains(hhXtCz.getVurl())
								|| !hhXtCzPageTextList.contains(hhXtCz
										.getPageText())) {
							if (Check.isNoEmpty(hhXtCz.getVurl())) {
								hhXtCzUrlList.add(hhXtCz.getVurl());
							}
							if (Check.isNoEmpty(hhXtCz.getPageText())) {
								hhXtCzPageTextList.add(hhXtCz.getPageText());
								List<String> menuCzList = hhXtCzPageTextMap
										.get(hhXtCz.getMenuUrl());
								if (menuCzList == null) {
									hhXtCzPageTextMap.put(hhXtCz.getMenuUrl(),
											new ArrayList<String>());
								}
								hhXtCzPageTextMap.get(hhXtCz.getMenuUrl()).add(
										hhXtCz.getPageText());
							}
							hhXtCzList.add(hhXtCz);
						}
					}
					hhXtCdZmtbList = queryZmtbList(hhXtYh, hhxtcdIdList);

					hhXtYh.setHhXtCdList(hhXtCdList);
					hhXtYh.setHhXtCzUrlList(hhXtCzUrlList);

					hhXtYh.setHhXtCzPageTextList(hhXtCzPageTextList);
					hhXtYh.setHhXtCzPageTextMap(hhXtCzPageTextMap);

					hhXtYh.setHhXtCzList(hhXtCzList);
					hhXtYh.setHhXtYhCdZmtbList(hhXtCdZmtbList);

					hhXtYh.setHhXtJsList(hhXtJsList);

					createZmsx(hhXtYh);
					HhXtYh hhXtYh2 = new HhXtYh();
					try {
						BeanUtils.copyProperties(hhXtYh2, hhXtYh);
						hhXtYh2.setVmm("");
						hhXtYh2.setHhXtZmsx(hhXtYh.getHhXtZmsx());
						ActionContext.getContext().getSession()
								.put("loginuser", hhXtYh2);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					returnModel
							.setMsg(MsgProperties.system_userlogin_wrong_password);
				}
			}
		}
		return returnModel;
	}

	private Organization addGw(HhXtYh hhXtYh) {
		// 岗位
		userService.editHhXtYh_orgList(hhXtYh);

		for (int i = 0; i < hhXtYh.getOrganizationList().size(); i++) {
			List<Organization> organizations1 = hhXtOrgDAO
					.queryList(
							"select o from Organization o where ? like '%'|| o.code_||'%' and length(o.code_) in (select max(length(o.code_)) from Organization o where ? like '%'|| o.code_||'%' group by lx_) ",
							new Object[] {
									hhXtYh.getOrganizationList().get(i)
											.getCode_(),
									hhXtYh.getOrganizationList().get(i)
											.getCode_() }, true);
			for (Organization organization : organizations1) {
				if (organization.getLx_() == 0) {
					hhXtYh.getOrganizationList().get(i).setJt(organization);
				} else if (organization.getLx_() == 1) {
					hhXtYh.getOrganizationList().get(i).setJg(organization);
				} else if (organization.getLx_() == 2) {
					hhXtYh.getOrganizationList().get(i).setBm(organization);
				}
			}
		}

		List<Organization> organizations = hhXtYh.getOrganizationList();

		if (organizations.size() == 1) {
			hhXtYh.setOrganization( hhXtYh.getOrganizationList().get(0));
			return hhXtYh.getOrganizationList().get(0);
		} else {
			if (!Check.isEmpty(hhXtYh.getHhXtZmsx().getDefaultOrgId())) {
				for (Organization organization : organizations) {
					if (hhXtYh.getHhXtZmsx().getDefaultOrgId()
							.equals(organization.getId())) {
						hhXtYh.setOrganization( organization);
						return organization;
					}
				}
			}
			return null;
		}
	}

	private void createZmsx(HhXtYh hhXtYh) {
		if (hhXtYh.getHhXtZmsx() == null) {
			HHXtZmsx hhXtZmsx = new HHXtZmsx();
			hhXtZmsx.setId(UUID.randomUUID().toString());
			hhXtYh.setHhXtZmsx(hhXtZmsx);
			xtyhdao.updateEntity(hhXtYh);
		}
	}

	private List<HhXtCd> queryZmtbList(HhXtYh hhXtYh, List<String> hhxtcdIdList) {
		List<HhXtYhCdZmtb> hhXtYhCdZmtbList = xtyhcdzmtb.queryList(
				HhXtYhCdZmtb.class,
				ParamFactory.getParamHb().is("yhId",
						hhXtYh.getId())
		// .addCondition(Order.asc(StaticVar.ORDER))
				);

		List<HhXtCd> hhXtCdZmtbList = new ArrayList<HhXtCd>();
		for (HhXtYhCdZmtb hhXtYhCdZmtb : hhXtYhCdZmtbList) {
			if (hhXtYhCdZmtb.getHhXtCd() != null) {
				if (hhxtcdIdList.contains(hhXtYhCdZmtb.getHhXtCd().getId())) {
					hhXtCdZmtbList.add(hhXtYhCdZmtb.getHhXtCd());
				}

			}
		}

		return hhXtCdZmtbList;
	}
}
