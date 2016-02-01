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
//import com.hh.usersystem.bean.usersystem.HHXtZmsx;
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
	private OrganizationService organizationService;
	@Autowired
	private IHibernateDAO<HhXtCd> hhxtcdDao;
	@Autowired
	private UserService userService;
	@Autowired
	private IHibernateDAO<HhXtOrgJs> hhxtorgjsdao;
	private static final Logger logger = Logger.getLogger(BaseAction.class);

	public ReturnModel savefindLogin(HhXtYh xtYh) {
		ReturnModel returnModel = new ReturnModel();
		List<HhXtYh> xtYhList = xtyhdao.queryList(HhXtYh.class, ParamFactory
				.getParamHb().is("vdlzh", xtYh.getVdlzh()));
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

					if (hhXtYh.getState() == 1) {
						returnModel.setMsg("您的账号已经被冻结，请联系管理员！");
						return returnModel;
					}

					String desktop = "desktop";
					if (Check.isNoEmpty(hhXtYh.getDesktopType())) {
						desktop = hhXtYh.getDesktopType();
					}
					returnModel.setHref("webapp-desktop-" + desktop);

					List<HhXtYhJs> hhXtYhJsList = xtyhjsdao.queryList(
							HhXtYhJs.class,
							ParamFactory.getParamHb()
									.is("yhId", hhXtYh.getId()));

					List<String> jsids = new ArrayList<String>();
					for (HhXtYhJs hhXtYhJs : hhXtYhJsList) {
						if (!jsids.contains(hhXtYhJs.getJsId())) {
							jsids.add(hhXtYhJs.getJsId());
						}
					}

					List<String> orgIdList = new ArrayList<String>();
					addOrg(hhXtYh);
					if (hhXtYh.getJob() != null) {
						orgIdList.add(hhXtYh.getJob().getId());
					}
					if (hhXtYh.getDept() != null) {
						orgIdList.add(hhXtYh.getDept().getId());
					}
					if (hhXtYh.getOrg() != null) {
						orgIdList.add(hhXtYh.getOrg().getId());
					}

					List<HhXtOrgJs> hhXtOrgJsList = new ArrayList<HhXtOrgJs>();

					if (Check.isNoEmpty(orgIdList)) {
						hhxtorgjsdao.queryList(HhXtOrgJs.class, "orgId",
								orgIdList);
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
								ParamFactory.getParamHb().nis("state", 1)
										.in("id", jsids));
						if (hhXtJsList.size() != 0) {
							hhXtJsCdList = xtjscddao
									.queryList(HhXtJsCd.class, ParamFactory
											.getParamHb().in("jsId", jsids));
							hhXtJsCzList = xtjsczdao
									.queryList(HhXtJsCz.class, ParamFactory
											.getParamHb().in("jsId", jsids));
						}
					}

					Map<String, HhXtCz> hhXtCzMap = new HashMap<String, HhXtCz>();
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
					HhXtYh hhXtYh2 = new HhXtYh();
					try {
						BeanUtils.copyProperties(hhXtYh2, hhXtYh);
						hhXtYh2.setVmm("");
						// hhXtYh2.setHhXtZmsx(hhXtYh.getHhXtZmsx());
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

	private void addOrg(HhXtYh hhXtYh) {
		if (Check.isNoEmpty(hhXtYh.getOrgId())) {
			Organization organization = organizationService
					.findObjectById(hhXtYh.getOrgId());
			hhXtYh.setOrg(organization);
		}
		if (Check.isNoEmpty(hhXtYh.getDeptId())) {
			Organization organization = organizationService
					.findObjectById(hhXtYh.getDeptId());
			hhXtYh.setDept(organization);
		}
		if (Check.isNoEmpty(hhXtYh.getJobId())) {
			Organization organization = organizationService
					.findObjectById(hhXtYh.getJobId());
			hhXtYh.setJob(organization);
		}
	}

	private List<HhXtCd> queryZmtbList(HhXtYh hhXtYh, List<String> hhxtcdIdList) {
		List<HhXtYhCdZmtb> hhXtYhCdZmtbList = xtyhcdzmtb.queryList(
				HhXtYhCdZmtb.class,
				ParamFactory.getParamHb().is("yhId", hhXtYh.getId())
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
