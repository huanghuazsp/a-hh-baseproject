package com.hh.usersystem.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.util.pk.PrimaryKey;
import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializer {

	@Autowired
	private SystemService systemService;

	@PostConstruct
	public void initialize() {
		SysMenu rootHhXtCd = new SysMenu("I4KSvNsmnxvusQknuik",
				"用户系统", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/setting.gif", 0, 0);
		rootHhXtCd.setChildren(new ArrayList<SysMenu>());


		rootHhXtCd.getChildren().add(
				new SysMenu("9Mm6RhhwRCRw9Vb2KUt","菜单管理",
						"jsp-usersystem-menu-menulist",
						"/hhcommon/images/extjsico/ascx.gif", 0, 1));

		rootHhXtCd.getChildren().add(
				new SysMenu("QwbhOcRrgYlMxgwq0nz","用户组",
						"jsp-usersystem-group-grouplist",
						"/hhcommon/images/icons/group/group.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu( "pufT5Yx41PmzocjHt5L","机构管理",
						"jsp-usersystem-org-orglist",
						"/hhcommon/images/icons/user/group.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu( "JtQxmni2zrCPD1Lway9","角色管理",
						"jsp-usersystem-role-rolelist",
						"/hhcommon/images/icons/user/user_role.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("lSQ7gLo3buUs69IFSrx","用户管理",
						"jsp-usersystem-user-userlist",
						"/hhcommon/images/icons/user/user.png", 0, 1));
		rootHhXtCd.getChildren().add(
				new SysMenu("AtEKV96zooROUbwJnkW", "分管配置",
						"jsp-usersystem-user-usLeaderList",
						"/hhcommon/images/icons/user/user_gray.png", 0, 1));
		StaticProperties.sysMenuList.add(rootHhXtCd);

	}
	
	public static void main(String[] args) {
		System.err.println(PrimaryKey.getUUID());
	}

}