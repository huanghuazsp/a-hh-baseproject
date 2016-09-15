package com.hh.usersystem.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.util.PrimaryKey;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializer {

	@Autowired
	private SystemService systemService;

	@PostConstruct
	public void initialize() {
		SysMenu rootHhXtCd = new SysMenu("36a9a107-1052-482e-8c41-58ee01caffbd",
				"用户系统", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/setting.gif", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());


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
		System.err.println(PrimaryKey.getUUID());
	}

}