package com.hh.usersystem.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.base.BaseAction;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.model.MsgProperties;
import com.hh.system.util.model.ReturnModel;
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsRoleMenu;
import com.hh.usersystem.bean.usersystem.UsRoleOper;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserMenuZmtb;
import com.hh.usersystem.util.app.LoginUser;
import com.hh.usersystem.util.steady.StaticProperties;
import com.opensymphony.xwork2.ActionContext;

@Service
public class LoginService {
	@Autowired
	private IHibernateDAO<UsUser> xtyhdao;
	@Autowired
	private IHibernateDAO<UsRole> xtjsdao;
	@Autowired
	private IHibernateDAO<UsRoleMenu> xtjscddao;
	@Autowired
	private IHibernateDAO<UsRoleOper> xtjsczdao;
	@Autowired
	private IHibernateDAO<UsUserMenuZmtb> xtyhcdzmtb;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private IHibernateDAO<SysMenu> hhxtcdDao;
	@Autowired
	private UserService userService;

	private static final Logger logger = Logger.getLogger(BaseAction.class);

	public ReturnModel savefindLogin(UsUser xtYh) {
		ReturnModel returnModel = new ReturnModel();
		List<UsUser> xtYhList = xtyhdao.queryList(UsUser.class, ParamFactory.getParamHb().is("vdlzh", xtYh.getVdlzh()));
		if (Check.isEmpty(xtYhList)) {
			// returnModel.setType(ReturnModel.TYPE_OK);
			returnModel.setMsg(MsgProperties.system_userlogin_notexist);
		} else {
			if (xtYhList.size() > 1) {
				returnModel.setMsg(MsgProperties.system_userlogin_repeat);
			} else {
				if (LoginUser.loginUserMap.containsKey(xtYhList.get(0).getId())) {
					logger.info("用户已经在线（" + xtYhList.get(0).getText() + "：" + xtYhList.get(0).getVdlzh() + "），重新登录！！");
					if (ActionContext.getContext().getSession().get("loginuser") == null) {
						LoginUser.loginUserSession.get(xtYhList.get(0).getId()).invalidate();
					}
				}
				if (xtYhList.get(0).getVmm().equals(xtYh.getVmm())) {
					returnModel.setTitleMsg(MsgProperties.system_please_later);
					returnModel.setMsg(MsgProperties.system_is_jump);

					UsUser hhXtYh = xtYhList.get(0);
					if (hhXtYh.getState() == 1) {
						returnModel.setMsg("您的账号已经被冻结，请联系管理员！");
						return returnModel;
					}

					returnModel.setHref("webapp-desktop-desktop" );

					List<String> jsids = Convert.strToList(hhXtYh.getRoleIds());

					addOrg(hhXtYh);

					if (hhXtYh.getOrg() != null) {
						jsids.addAll(Convert.strToList(hhXtYh.getOrg().getRoleIds()));
					}
					if (hhXtYh.getDept() != null) {
						jsids.addAll(Convert.strToList(hhXtYh.getDept().getRoleIds()));
					}
					if (hhXtYh.getJob() != null) {
						jsids.addAll(Convert.strToList(hhXtYh.getJob().getRoleIds()));
					}

					List<UsRole> hhXtJsList = new ArrayList<UsRole>();
					List<UsRoleMenu> hhXtJsCdList = new ArrayList<UsRoleMenu>();
					List<UsRoleOper> hhXtJsCzList = new ArrayList<UsRoleOper>();
					if (jsids.size() != 0) {
						hhXtJsList = xtjsdao.queryList(UsRole.class,
								ParamFactory.getParamHb().nis("state", 1).in("id", jsids));
						if (hhXtJsList.size() != 0) {
							hhXtJsCdList = xtjscddao.queryList(UsRoleMenu.class,
									ParamFactory.getParamHb().in("jsId", jsids));
							hhXtJsCzList = xtjsczdao.queryList(UsRoleOper.class,
									ParamFactory.getParamHb().in("jsId", jsids));
						}
					}

					Map<String, SysOper> hhXtCzMap = new HashMap<String, SysOper>();
					List<SysMenu> hhXtCdZmtbList = new ArrayList<SysMenu>();

					List<String> hhxtcdIdList = new ArrayList<String>();

					for (UsRoleMenu hhXtJsCd : hhXtJsCdList) {
						hhxtcdIdList.add(hhXtJsCd.getCdId());
					}
					List<SysMenu> hhXtCdList = new ArrayList<SysMenu>();
					if (hhxtcdIdList.size() > 0) {
						hhXtCdList = hhxtcdDao.queryList(SysMenu.class, "id", hhxtcdIdList);
						for (SysMenu sysMenu : hhXtCdList) {
							SysOper sysOper = new SysOper();
							sysOper.setType(1);
							hhXtCzMap.put(sysMenu.getVsj(), sysOper);
						}
					}
					// 请求的控制
					List<String> hhXtCzUrlList = new ArrayList<String>();
					// 根据字符串控制页面
					List<String> hhXtCzPageTextList = new ArrayList<String>();
					Map<String, List<String>> hhXtCzPageTextMap = new HashMap<String, List<String>>();

					// 多个角色 获取权限最大的操作级别
					for (UsRoleOper hhXtJsCz : hhXtJsCzList) {
						SysOper hhXtCz = hhXtJsCz.getHhXtCz();
						if (Check.isEmpty(hhXtCz.getOperLevel())) {
							hhXtCz.setOperLevel(hhXtJsCz.getOperLevel());
						} else {
							if (StaticProperties.findOperationLevel(hhXtJsCz.getOperLevel()) > StaticProperties
									.findOperationLevel(hhXtCz.getOperLevel())) {
								hhXtCz.setOperLevel(hhXtJsCz.getOperLevel());
							}
						}
						if (!hhXtCzUrlList.contains(hhXtCz.getVurl())
								|| !hhXtCzPageTextList.contains(hhXtCz.getPageText())) {
							if (Check.isNoEmpty(hhXtCz.getVurl())) {
								hhXtCzUrlList.add(hhXtCz.getVurl());
							}
							if (Check.isNoEmpty(hhXtCz.getPageText())) {
								hhXtCzPageTextList.add(hhXtCz.getPageText());
								List<String> menuCzList = hhXtCzPageTextMap.get(hhXtCz.getMenuUrl());
								if (menuCzList == null) {
									hhXtCzPageTextMap.put(hhXtCz.getMenuUrl(), new ArrayList<String>());
								}
								hhXtCzPageTextMap.get(hhXtCz.getMenuUrl()).add(hhXtCz.getPageText());
							}
							hhXtCzMap.put(hhXtCz.getVurl(), hhXtCz);
						}
					}
					hhXtCdZmtbList = queryZmtbList(hhXtYh, hhxtcdIdList);

					hhXtYh.setHhXtCdList(hhXtCdList);
					hhXtYh.setHhXtCzUrlList(hhXtCzUrlList);

					hhXtYh.setHhXtCzPageTextList(hhXtCzPageTextList);
					hhXtYh.setHhXtCzPageTextMap(hhXtCzPageTextMap);

					hhXtYh.setHhXtCzMap(hhXtCzMap);
					hhXtYh.setHhXtYhCdZmtbList(hhXtCdZmtbList);

					hhXtYh.setHhXtJsList(hhXtJsList);

					// createZmsx(hhXtYh);
					UsUser hhXtYh2 = new UsUser();
					try {
						BeanUtils.copyProperties(hhXtYh2, hhXtYh);
						hhXtYh2.setVmm("");
						// hhXtYh2.setHhXtZmsx(hhXtYh.getHhXtZmsx());
						ActionContext.getContext().getSession().put("loginuser", hhXtYh2);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					returnModel.setMsg(MsgProperties.system_userlogin_wrong_password);
				}
			}
		}
		return returnModel;
	}

	private void addOrg(UsUser hhXtYh) {
		if (Check.isNoEmpty(hhXtYh.getOrgId())) {
			UsOrganization organization = organizationService.findObjectById(hhXtYh.getOrgId());
			hhXtYh.setOrg(organization);
		}
		if (Check.isNoEmpty(hhXtYh.getDeptId())) {
			UsOrganization organization = organizationService.findObjectById(hhXtYh.getDeptId());
			hhXtYh.setDept(organization);
		}
		if (Check.isNoEmpty(hhXtYh.getJobId())) {
			UsOrganization organization = organizationService.findObjectById(hhXtYh.getJobId());
			hhXtYh.setJob(organization);
		}
	}

	private List<SysMenu> queryZmtbList(UsUser hhXtYh, List<String> hhxtcdIdList) {
		List<UsUserMenuZmtb> hhXtYhCdZmtbList = xtyhcdzmtb.queryList(UsUserMenuZmtb.class,
				ParamFactory.getParamHb().is("yhId", hhXtYh.getId())
		// .addCondition(Order.asc(StaticVar.ORDER))
		);

		List<SysMenu> hhXtCdZmtbList = new ArrayList<SysMenu>();
		for (UsUserMenuZmtb hhXtYhCdZmtb : hhXtYhCdZmtbList) {
			if (hhXtYhCdZmtb.getHhXtCd() != null) {
				if (hhxtcdIdList.contains(hhXtYhCdZmtb.getHhXtCd().getId())) {
					hhXtCdZmtbList.add(hhXtYhCdZmtb.getHhXtCd());
				}

			}
		}

		return hhXtCdZmtbList;
	}
}
