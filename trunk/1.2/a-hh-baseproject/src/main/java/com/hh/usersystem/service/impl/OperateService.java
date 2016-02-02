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
import com.hh.system.util.model.ExtTree;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.aop.interceptor.SecurityInterceptor;
import com.hh.usersystem.bean.usersystem.SysOper;
import com.hh.usersystem.bean.usersystem.UsRoleOper;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;

@Service
public class OperateService  extends BaseService<SysOper> {
	@Autowired
	private IHibernateDAO<SysOper> dao;
	@Autowired
	private IHibernateDAO<UsRoleOper> hhxtjsczDao;

	public List<ExtTree> queryOperateListByPid(String node) {
		List<SysOper> hhXtCzList = new ArrayList<SysOper>();
		ParamInf hqlParamList = ParamFactory.getParamHb();
		hhXtCzList = dao.queryList(SysOper.class,
				hqlParamList.is("vpid", node));
		List<ExtTree> extTreeList = new ArrayList<ExtTree>();
		for (SysOper hhXtCd : hhXtCzList) {
			ExtTree extTree = new ExtTree();
			extTree.setId(hhXtCd.getId());
			
			String text = hhXtCd.getText();
			if (Check.isNoEmpty(hhXtCd.getPageText())) {
				text+="（名称）";
			}
			if (Check.isNoEmpty(hhXtCd.getVurl())) {
				text+="（请求）";
			}
			extTree.setText(text);
			extTree.setLeaf(1);
			extTreeList.add(extTree);
		}
		return extTreeList;
	}

	public List<ExtCheckTree> queryCheckOperateListByPid(String vpid,
			String roleid, String node) {
		List<ExtCheckTree> extTreeList = new ArrayList<ExtCheckTree>();
		if ("root".equals(node)) {
			List<SysOper> hhXtCzList = new ArrayList<SysOper>();
			ParamInf hqlParamList = ParamFactory.getParamHb();
			hhXtCzList = dao.queryList(SysOper.class,
					hqlParamList.is("vpid", vpid));

			List<String> czidList = new ArrayList<String>();

			for (SysOper hhXtCd : hhXtCzList) {
				ExtCheckTree extTree = new ExtCheckTree();
				extTree.setId(hhXtCd.getId());
				String text = hhXtCd.getText();
				if (Check.isNoEmpty(hhXtCd.getPageText())) {
					text+="（名称）";
				}
				if (Check.isNoEmpty(hhXtCd.getVurl())) {
					text+="（请求）";
				}
				extTree.setText(text);
				extTree.setLeaf(0);
				extTree.setExpanded(1);
				extTreeList.add(extTree);
				extTree.setNocheck(true);
				czidList.add(hhXtCd.getId());
			}

			if (!Check.isEmpty(czidList)) {
				ParamInf hqlParamList2 = ParamFactory.getParamHb();
				hqlParamList2.in("hhXtCz.id", czidList);
				hqlParamList2.is("jsId", roleid);
				List<UsRoleOper> hhXtJsCdList = hhxtjsczDao.queryList(
						UsRoleOper.class, hqlParamList2);

				List<String> jscdidList = new ArrayList<String>();
				if (!Check.isEmpty(hhXtJsCdList)) {
					for (UsRoleOper hhXtJsCd : hhXtJsCdList) {
						jscdidList.add(hhXtJsCd.getHhXtCz().getId());
					}
				}

				for (ExtCheckTree extCheckTree : extTreeList) {
					if (jscdidList.contains(extCheckTree.getId())) {
						extCheckTree.setChecked(true);
					}
				}
			}

			for (ExtCheckTree extCheckTree_parent : extTreeList) {
				List<ExtCheckTree> extTreeListchild = new ArrayList<ExtCheckTree>();
				extCheckTree_parent.setChildren(extTreeListchild);
				ParamInf hqlParamList2 = ParamFactory.getParamHb();
				hqlParamList2.is("hhXtCz.id",
						extCheckTree_parent.getId());
				hqlParamList2.is("jsId", roleid);
				UsRoleOper hhXtJsCz = hhxtjsczDao.findEntity(UsRoleOper.class,
						hqlParamList2);

				ExtCheckTree extTree5 = new ExtCheckTree();
				extTree5.setId(extCheckTree_parent.getId() + "_");
				extTree5.setText("本人");
				extTree5.setLeaf(1);
				extTreeListchild.add(extTree5);

				ExtCheckTree extTree1 = new ExtCheckTree();
				extTree1.setId(extCheckTree_parent.getId() + "_"
						+ OperationLevel.BGW.toString());
				extTree1.setText("本岗位");
				extTree1.setLeaf(1);
				extTreeListchild.add(extTree1);
				ExtCheckTree extTree2 = new ExtCheckTree();
				extTree2.setId(extCheckTree_parent.getId() + "_"
						+ OperationLevel.BBM.toString());
				extTree2.setText("本部门");
				extTree2.setLeaf(1);
				extTreeListchild.add(extTree2);
				ExtCheckTree extTree3 = new ExtCheckTree();
				extTree3.setId(extCheckTree_parent.getId() + "_"
						+ OperationLevel.BJG.toString());
				extTree3.setText("本机构");
				extTree3.setLeaf(1);
				extTreeListchild.add(extTree3);
//				ExtCheckTree extTree4 = new ExtCheckTree();
//				extTree4.setId(extCheckTree_parent.getId() + "_"
//						+ OperationLevel.BJT.toString());
//				extTree4.setText("本集团");
//				extTree4.setLeaf(1);
//				extTreeListchild.add(extTree4);
				if (hhXtJsCz != null) {
					for (ExtCheckTree extCheckTree : extTreeListchild) {
						if (extCheckTree
								.getId()
								.substring(
										extCheckTree.getId().indexOf("_") + 1)
								.equals(hhXtJsCz.getOperLevel())) {
							extCheckTree.setChecked(true);
							break;
						}
					}
				}
			}

		}
		return extTreeList;
	}

	public SysOper findObjectById(String id) {
		SysOper hhXtCz = dao.findEntityByPK(SysOper.class, id);
		return hhXtCz;
	}
	
	public List<SysOper> queryOperateListByPids(List<String> pids) {
		return this.queryList(ParamFactory.getParamHb().in("vpid", pids));
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
				List<String> menuCzList = SecurityInterceptor.all_manage_page_text_map
						.get(hhXtCz.getMenuUrl());
				if (menuCzList == null) {
					SecurityInterceptor.all_manage_page_text_map.put(
							hhXtCz.getMenuUrl(), new ArrayList<String>());
				}
				SecurityInterceptor.all_manage_page_text_map.get(
						hhXtCz.getMenuUrl()).add(hhXtCz.getPageText());
			}
		}
	}

}
