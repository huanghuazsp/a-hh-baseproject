package com.hh.usersystem.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializerSystem {
	@Autowired
	private SystemService systemService;

	@PostConstruct
	public void initialize() {
		HhXtCd rootHhXtCd = new HhXtCd("b77305ce-b345-46d7-8735-6968f616ad62",
				"系统管理", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/17460336.png", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<HhXtCd>());
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("20eb2825-26a6-4119-8b4e-813c6d18b87c", "错误追踪",
		// "com.hh.system.error.ErrorList",
		// "/hhcommon/images/icons/bug/bug.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("c88ef678-2785-4e24-b949-fbb0b72de837", "系统参数",
		// "com.hh.system.sysparam.ParamList",
		// "/hhcommon/images/extjsico/bogus.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("eeb2a494-50a6-46e7-8fa2-cd1d95c8c04d", "SQL监控",
		// "com.hh.system.sql.SqlList",
		// "/hhcommon/images/icons/script/script.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("613c3476-3721-493d-a688-6cd1d3ed8348", "消息管理",
		// "com.hh.message.message.MessageList",
		// "/hhcommon/images/extjsico/17460341.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("a55f050a-f6bc-4751-9d5c-557ac9c6a328", "在线用户",
		// "com.hh.usersystem.user.OnlineUserList",
		// "/hhcommon/images/icons/user/user.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new HhXtCd("d45831d2-b887-40f1-9a3f-450298ac3f91", "取色工具",
						"jsp-system-tools-color",
						"/hhcommon/images/icons/world/world.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new HhXtCd("4c95c4de-fd06-4982-a4ca-aedf5aaa2f0d", "SQL监控",
						"jsp-system-sql-sqllist",
						"/hhcommon/images/icons/script/script.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new HhXtCd("abe56eb1-c873-40e6-965e-abc0113d0393", "错误追踪",
						"jsp-system-error-errorlist",
						"/hhcommon/images/icons/bug/bug.png", 0, 1));

		rootHhXtCd
				.getChildren()
				.add(new HhXtCd("bc55cc4f-7a56-4089-affb-899f92e6c5f4", "系统工具",
						"jsp-system-tools-systemtools",
						"/hhcommon/images/extjsico/application_16x16.gif", 0, 1));
		rootHhXtCd.getChildren().add(
				new HhXtCd("5b32bc7c-2a19-4aea-a12e-d07092737d9d", "系统参数",
						"jsp-system-sysparam-paramedit",
						"/hhcommon/images/extjsico/bogus.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new HhXtCd("7cc2cd43-d297-419d-9386-8129f887b2b3", "消息管理",
						"jsp-message-message-messagelist",
						"/hhcommon/images/extjsico/17460341.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new HhXtCd("886a29ca-49f1-4703-9577-f639926178fd", "在线用户",
						"jsp-usersystem-user-onlineuser",
						"/hhcommon/images/icons/user/user.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new HhXtCd("a4846b3a-edbd-4d49-ac57-ca6537cd85bf", "缓存管理",
						"jsp-system-tools-cachelist",
						"/hhcommon/images/extjsico/bogus.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new HhXtCd("5c88da68-d911-4372-b421-80b308b0c34e", "数据字典",
						"jsp-system-data-datalist",
						"/hhcommon/images/extjsico/17460321.png", 0, 1));
		
		StaticProperties.hhXtCds.add(rootHhXtCd);

		
		com.hh.system.util.StaticProperties.loadDataTimeList.add(systemService);
	}
}