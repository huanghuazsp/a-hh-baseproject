package com.hh.usersystem.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.util.PrimaryKey;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializerUserSystem {

	@Autowired
	private SystemService systemService;

	@PostConstruct
	public void initialize() {
		SysMenu rootHhXtCd = new SysMenu("36a9a107-1052-482e-8c41-58ee01caffbd",
				"用户系统", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/setting.gif", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("1bdb25bc-1cc1-442d-9ac2-91e0e8ec5fe5", "消息工具",
		// "com.hh.message.main.MainMessageWindow",
		// "/hhcommon/images/icons/world/world.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("2520c1a3-4a8b-47dd-bd06-b2e8eadd2595", "用户管理",
		// "com.hh.usersystem.user.UserList",
		// "/hhcommon/images/icons/user/user.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("f51d71e5-7f88-4664-a4d4-b2881a34895e", "角色管理",
		// "com.hh.usersystem.role.RoleList",
		// "/hhcommon/images/icons/user/user_role.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("7a5c86f0-cc39-42f0-9d09-704507d69342", "菜单管理",
		// "com.hh.usersystem.menu.MenuList",
		// "/hhcommon/images/extjsico/ascx.gif",
		// 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("86bb6c09-f914-4173-b012-f20a007d6851", "机构管理",
		// "com.hh.usersystem.org.OrgList",
		// "/hhcommon/images/icons/user/group.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("4174be2a-71d8-4626-8d75-cd229d521b16", "用户组",
		// "com.hh.usersystem.group.GroupList",
		// "/hhcommon/images/icons/group/group.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("15e100a9-bfb2-47b8-a524-b986d532fb49", "数据字典类别管理",
		// "com.hh.system.data.DataTree",
		// "/hhcommon/images/extjsico/17460321.png", 0, 1));
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("c075d7c3-21a6-4814-a340-cebe43f9f348", "数据字典管理",
		// "com.hh.system.data.DataList",
		// "/hhcommon/images/extjsico/17460321.png", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("5adce781-54ed-412c-8e1d-18357841f527", "菜单管理",
						"jsp-usersystem-menu-menulist",
						"/hhcommon/images/extjsico/ascx.gif", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("b8f2ce50-2064-485f-876d-bde6e3110061", "用户组",
						"jsp-usersystem-group-grouplist",
						"/hhcommon/images/icons/group/group.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("02c802fa-a9a2-4cbd-af98-0df20ef54e6c", "机构管理",
						"jsp-usersystem-org-orglist",
						"/hhcommon/images/icons/user/group.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("6f6d7739-8e54-4ace-928d-3db6f29635c8", "角色管理",
						"jsp-usersystem-role-rolelist",
						"/hhcommon/images/icons/user/user_role.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("5fe8a25c-696e-41c6-b510-090e9e2f8dc6", "用户管理",
						"jsp-usersystem-user-userlist",
						"/hhcommon/images/icons/user/user.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("98c4457260f9427eb229ad282adef958", "分管配置",
						"jsp-usersystem-user-usLeaderList",
						"/hhcommon/images/icons/user/user_gray.png", 0, 1));
		StaticProperties.hhXtCds.add(rootHhXtCd);

	}
	
	public static void main(String[] args) {
		System.err.println(PrimaryKey.getPrimaryKeyUUID());
	}

}