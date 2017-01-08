package com.hh.usersystem.util.steady;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hh.system.util.Convert;
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
	
	
	public static List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	
	static{
		addData("基础数据字典", "", "类别", "", "", "");
		addData("性别", "基础数据字典", "类别", "", "", "");
		
		addData("男", "", "字典", "性别", "1", "");
		addData("女", "", "字典", "性别", "0", "");
		
		addData("是否", "基础数据字典", "类别", "", "", "");
		addData("是", "", "字典", "是否", "1", "");
		addData("否", "", "字典", "是否", "0", "");
		
		
		addData("角色属性", "基础数据字典", "类别", "", "", "");
		addData("机构属性", "基础数据字典", "类别", "", "", "");
	}
	
	public static void addData(String text ,String parentText,String type,String parentType,String code,String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("类型",type);
		map.put("上级名称",parentText);
		map.put("标识",id);
		map.put("名称",text);
		map.put("编码",code);
		map.put("所属类别",parentType);
		dataList.add(map);
	}

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
