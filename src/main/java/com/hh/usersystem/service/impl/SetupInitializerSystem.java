package com.hh.usersystem.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializerSystem {
	@Autowired
	private SystemService systemService;

	@PostConstruct
	public void initialize() {
		SysMenu rootHhXtCd = new SysMenu("b77305ce-b345-46d7-8735-6968f616ad62",
				"系统管理", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/17460336.png", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());

		
		rootHhXtCd.getChildren().add(
				new SysMenu("d45831d2-b887-40f1-9a3f-450298ac3f92", "计划任务",
						"jsp-system-systemplantask-SystemPlantaskList",
						"/hhcommon/images/icons/world/world.png", 0, 1));
		
		rootHhXtCd.getChildren().add(
				new SysMenu("d45831d2-b887-40f1-9a3f-450298ac3f91", "取色工具",
						"jsp-system-tools-color",
						"/hhcommon/images/icons/world/world.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("4c95c4de-fd06-4982-a4ca-aedf5aaa2f0d", "SQL监控",
						"jsp-system-sql-sqllist",
						"/hhcommon/images/icons/script/script.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("abe56eb1-c873-40e6-965e-abc0113d0393", "错误追踪",
						"jsp-system-error-errorlist",
						"/hhcommon/images/icons/bug/bug.png", 0, 1));

		rootHhXtCd
				.getChildren()
				.add(new SysMenu("bc55cc4f-7a56-4089-affb-899f92e6c5f4", "系统工具",
						"jsp-system-tools-systemtools",
						"/hhcommon/images/extjsico/application_16x16.gif", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("5b32bc7c-2a19-4aea-a12e-d07092737d9d", "系统参数",
						"jsp-system-sysparam-paramedit",
						"/hhcommon/images/extjsico/bogus.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("7cc2cd43-d297-419d-9386-8129f887b2b3", "消息管理",
						"jsp-message-message-messagelist",
						"/hhcommon/images/extjsico/17460341.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("886a29ca-49f1-4703-9577-f639926178fd", "在线用户",
						"jsp-usersystem-user-onlineuser",
						"/hhcommon/images/icons/user/user.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("a4846b3a-edbd-4d49-ac57-ca6537cd85bf", "缓存管理",
						"jsp-system-tools-cachelist",
						"/hhcommon/images/extjsico/bogus.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("5c88da68-d911-4372-b421-80b308b0c34e", "数据字典",
						"jsp-system-data-datalist",
						"/hhcommon/images/extjsico/17460321.png", 0, 1));
		
		StaticProperties.hhXtCds.add(rootHhXtCd);

		//菜单
		
		SysMenu menurootHhXtCd = new SysMenu("94805849-70ae-4504-8192-2ab56fc83bb5",
				"表单设计", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/icons/world/world.png", 1, 0);
		menurootHhXtCd.setChildren(new ArrayList<SysMenu>());
		
		menurootHhXtCd.getChildren().add(
				new SysMenu("8d86355a-0c6e-4f2a-a1c7-f4c31e744988", "表单结果",
						"jsp-form-service-formtree",
						"/hhcommon/images/icons/world/world.png", 0, 1));

		menurootHhXtCd.getChildren().add(
				new SysMenu("0233e554-5735-4586-a100-a62a84c0f71b",
						"表单设计器CkEditor", "jsp-form-ckeditor-ckEditor",
						"/hhcommon/images/icons/world/world.png", 0, 1));

		menurootHhXtCd.getChildren().add(
				new SysMenu("8bc6e72d-27b2-46eb-aef7-d073d89bbd1b", "表单模板",
						"jsp-form-ckeditor-formmodel",
						"/hhcommon/images/icons/world/world.png", 0, 1));

		for (SysMenu hhXtCd : StaticProperties.hhXtCds) {
			if ("系统管理".equals(hhXtCd.getText())) {
				hhXtCd.getChildren().add(menurootHhXtCd);
				break;
			}
		}
		
		
		com.hh.system.util.StaticProperties.loadDataTimeList.add(systemService);
		
	}
}