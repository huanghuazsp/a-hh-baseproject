package com.hh.usersystem.util.steady;

import java.util.ArrayList;
import java.util.List;

import com.hh.usersystem.bean.usersystem.SysMenu;
import com.hh.usersystem.bean.usersystem.SysOper;

public class StaticProperties {
	// 默认背景
	public final static String HHXT_USERSYSTEM_ZMBJ = "/hhcommon/opensource/ext/desktop/wallpapers/sky.jpg";
	// 用户图片（男）
	public final static String HHXT_USERSYSTEM_NAN = "/hhcommon/images/myimage/user.gif";
	// 用户图片（女）
	public final static String HHXT_USERSYSTEM_NV = "/hhcommon/images/myimage/user_female.gif";
	// 用户图片（不在线）
	public static final String HHXT_NO_ON_LINE_USER_ICON = "/hhcommon/images/icons/user/no_on_line_user.jpg";
	
	
	public static List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
	public static List<SysOper> sysOperList = new ArrayList<SysOper>();

	public static enum OperationLevel {
		ALL,BR, BGW, BBM, BJG, BJT
	}

	public static int findOperationLevel(String operString) {
		if ("BR".equals(operString)) {
			return 1;
		} else if ("BGW".equals(operString)) {
			return 2;
		} else if ("BBM".equals(operString)) {
			return 3;
		} else if ("BJG".equals(operString)) {
			return 4;
		}else if ("ALL".equals(operString)) {
			return 5;
		} else {
			return 0;
		}
	}

}
