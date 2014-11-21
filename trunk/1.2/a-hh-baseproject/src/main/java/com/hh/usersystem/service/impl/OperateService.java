package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.hibernate.util.dto.HQLParamList;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.model.ExtCheckTree;
import com.hh.system.util.model.ExtTree;
import com.hh.usersystem.aop.interceptor.SecurityInterceptor;
import com.hh.usersystem.bean.usersystem.HhXtCz;
import com.hh.usersystem.bean.usersystem.HhXtGroup;
import com.hh.usersystem.bean.usersystem.HhXtJsCz;
import com.hh.usersystem.util.steady.StaticProperties.OperationLevel;

@Service
public class OperateService  extends BaseService<HhXtCz> {
	@Autowired
	private IHibernateDAO<HhXtCz> dao;
	@Autowired
	private IHibernateDAO<HhXtJsCz> hhxtjsczDao;

	public List<ExtTree> queryOperateListByPid(String node) {
		List<HhXtCz> hhXtCzList = new ArrayList<HhXtCz>();
		HQLParamList hqlParamList = new HQLParamList();
		hhXtCzList = dao.queryList(HhXtCz.class,
				hqlParamList.addCondition(Restrictions.eq("vpid", node)));
		List<ExtTree> extTreeList = new ArrayList<ExtTree>();
		for (HhXtCz hhXtCd : hhXtCzList) {
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
			List<HhXtCz> hhXtCzList = new ArrayList<HhXtCz>();
			HQLParamList hqlParamList = new HQLParamList();
			hhXtCzList = dao.queryList(HhXtCz.class,
					hqlParamList.addCondition(Restrictions.eq("vpid", vpid)));

			List<String> czidList = new ArrayList<String>();

			for (HhXtCz hhXtCd : hhXtCzList) {
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
				HQLParamList hqlParamList2 = new HQLParamList();
				hqlParamList2.add(Restrictions.in("hhXtCz.id", czidList));
				hqlParamList2.add(Restrictions.eq("jsId", roleid));
				List<HhXtJsCz> hhXtJsCdList = hhxtjsczDao.queryList(
						HhXtJsCz.class, hqlParamList2);

				List<String> jscdidList = new ArrayList<String>();
				if (!Check.isEmpty(hhXtJsCdList)) {
					for (HhXtJsCz hhXtJsCd : hhXtJsCdList) {
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
				HQLParamList hqlParamList2 = new HQLParamList();
				hqlParamList2.add(Restrictions.eq("hhXtCz.id",
						extCheckTree_parent.getId()));
				hqlParamList2.add(Restrictions.eq("jsId", roleid));
				HhXtJsCz hhXtJsCz = hhxtjsczDao.findEntity(HhXtJsCz.class,
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
				ExtCheckTree extTree4 = new ExtCheckTree();
				extTree4.setId(extCheckTree_parent.getId() + "_"
						+ OperationLevel.BJT.toString());
				extTree4.setText("本集团");
				extTree4.setLeaf(1);
				extTreeListchild.add(extTree4);
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

	public HhXtCz findObjectById(String id) {
		HhXtCz hhXtCz = dao.findEntityByPK(HhXtCz.class, id);
		return hhXtCz;
	}
	
	public List<HhXtCz> queryOperateListByPids(List<String> pids) {
		return this.queryList(Restrictions.in("vpid", pids));
	}

	public HhXtCz save(HhXtCz hhXtCz) {
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
			hhxtjsczDao.deleteEntity(HhXtJsCz.class, "czId", idList);
			dao.deleteEntity(HhXtCz.class, "id", idList);
			initOperPower();
		}
	}
	
	public void deleteByPidList(List<String> pidList) {
		List<String> idList = new ArrayList<String>();
		List<HhXtCz> hhXtCzList = queryOperateListByPids(pidList);
		for (HhXtCz hhXtCz : hhXtCzList) {
			idList.add(hhXtCz.getId());
		}
		deleteByIdList(idList);
	}

	public void initOperPower() {
		SecurityInterceptor.all_manage_request.clear();
		SecurityInterceptor.all_manage_page_text_map.clear();
		List<HhXtCz> xtczList = dao.queryList(HhXtCz.class);
		for (HhXtCz hhXtCz : xtczList) {
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
