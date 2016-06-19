package com.hh.usersystem.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.BeanUtils;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.MessageException;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.ExportSetInfo;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.model.ExtTree;
import com.hh.system.util.model.ResultFile;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.bean.usersystem.UsUserCyLxr;
import com.hh.usersystem.service.impl.OrganizationService;
import com.hh.usersystem.service.impl.RoleService;
import com.hh.usersystem.service.impl.UserService;

@SuppressWarnings("serial")
public class Actionuser extends BaseServiceAction<UsUser>  {
	private String orgs;
	private String roles;
	private String groups;
	private String usgroups;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;

	private String oldPassword;

	@Override
	public BaseService<UsUser> getService() {
		return userService;
	}

	public Object queryPagingData() {
		return userService.queryPagingData(object, this.getPageRange(), this.getIds(), orgs, roles, groups,usgroups);
	}

	public Object queryPagingDataCombox() {
		if (Check.isNoEmpty(this.object.getId())) {
			return userService.queryItemsByIdsStr(object.getId());
		} else {
			return userService.queryPagingData(object, this.getPageRange(), null, null, null, null,null);
		}
	}

	public Object queryPagingDataList() {
		return userService.queryPagingData(object, this.getPageRange(), null, null, null, null,null);
	}

	public Object queryOnLinePagingData() {
		return userService.queryOnLinePagingData(object, this.getPageRange());
	}

	public void deleteOnLineByIds() {
		userService.deleteOnLineByIds(this.getIds());
	}

	public Object save() {
		try {
			UsUser hhXtYh = userService.save(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object save2() {
		try {
			UsUser hhXtYh = userService.save(this.object);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object updatePassWord() {
		try {
			userService.updatePassWord(this.object, oldPassword);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object vdlzhOnly() {
		boolean result = userService.checkVdlzhOnly(object);
		return result;
	}

	public Object findObjectById() {
		UsUser hhXtYh = userService.findObjectById(this.object.getId());
		return hhXtYh;
	}

	public Object findObjectById2() {
		UsUser hhXtYh = userService.findObjectById_user(this.object.getId());
		return hhXtYh;
	}

	public void deleteByIds() {
		userService.deleteByIds(this.getIds());
	}

	public Object queryCylxrs() {
		List<UsUser> hhXtYhs = userService.queryCylxrs();
		return hhXtYhs;
	}

	public Object queryCylxrTree() {
		List<ExtTree> hhXtYhs = userService.queryCylxrTree();
		return hhXtYhs;
	}

	
	public Object addCylxrObject() {
		try {
			UsUserCyLxr usUserCyLxr = new UsUserCyLxr();
			BeanUtils.copyMap(usUserCyLxr, this.getParamsMap());
			userService.addCylxrObject(usUserCyLxr);
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object deleteCylxr() {
		try {
			userService.deleteCylxr(this.getParamsMap().get("cylxrid"));
			return null;
		} catch (MessageException e) {
			return e;
		}
	}

	public Object queryUserByOrgId() {
		List<UsUser> hhXtYhList = userService.queryUserByOrgId(request.getParameter("code"));
		return hhXtYhList;
	}

	public Object queryUserByGroup() {
		List<UsUser> hhXtYhList = userService.queryUserByGroup(request.getParameter("groupId"));
		return hhXtYhList;
	}

	public Object findUserTextByIds() {
		String returnTextString = "";
		String returnIdString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();
			StringBuffer ids = new StringBuffer();

			List<UsUser> hhXtYhList = userService
					.queryList(ParamFactory.getParamHb().in(StaticVar.entityId, Convert.strToList(object.getId())));
			for (UsUser hhXtYh : hhXtYhList) {
				texts.append(hhXtYh.getText() + ",");
				ids.append(hhXtYh.getId() + ",");
			}
			if (Check.isNoEmpty(texts)) {
				returnTextString = texts.substring(0, texts.length() - 1);
				returnIdString = ids.substring(0, ids.length() - 1);
			}
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("text", returnTextString);
		returnMap.put("id", returnIdString);
		return returnMap;
	}

	public Object findUserListByIds() {
		List<UsUser> hhXtYhList = userService
				.queryList(ParamFactory.getParamHb().in(StaticVar.entityId, Convert.strToList(object.getId())));
		return hhXtYhList;
	}

	public void updateZmbj() {
		userService.updateZmbj(object);
	}

	public void updateTheme() {
		userService.updateTheme(object);
	}

	public Object importData() throws Exception {
		response.setContentType("text/html");
		String path = FileUtil.uploadFile(attachment, attachmentFileName, type);
		File file = new File(StaticVar.filepath + path);
		try {
			List<Map<String, Object>> mapList = ExcelUtil.getMapList(file, 1);
			userService.save(mapList);
		} catch (MessageException e) {
			return e;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("path", path);
		returnMap.put("attachmentFileName", attachmentFileName);
		return returnMap;
	}

	public Object download() {

		Map<String, String> orgMapNameId = new HashMap<String, String>();
		Map<String, String> roleMapNameId = new HashMap<String, String>();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String title = "用户数据";
		ExportSetInfo setInfo = new ExportSetInfo();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<String[]> headNameList = new ArrayList<String[]>();
		List<String[]> fieldNameList = new ArrayList<String[]>();
		headNameList.add(new String[] { "标识", "名称", "账号", "性别", "状态", "联系电话", "电子邮件", "生日", "角色", "机构", "部门", "岗位" });
		fieldNameList
				.add(new String[] { "id", "text", "zh", "xb", "zt", "lxdh", "dzyj", "sr", "js", "jg", "bm", "gw" });
		List<UsUser> users = userService.queryAllList();
		for (UsUser usUser : users) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", usUser.getId());
			map2.put("text", usUser.getText());
			map2.put("zh", usUser.getVdlzh());
			map2.put("xb", usUser.getNxb() == 1 ? "男" : "女");
			map2.put("zt", usUser.getState() == 1 ? "禁用" : "正常");
			map2.put("lxdh", usUser.getVdh());
			map2.put("dzyj", usUser.getVdzyj());
			if (usUser.getDsr() == null) {
				map2.put("sr", "");
			} else {
				map2.put("sr", DateFormat.dateToStr(usUser.getDsr(), "yyyy-MM-dd"));
			}

			if (Check.isNoEmpty(usUser.getRoleIds())) {
				if (roleMapNameId.keySet().contains(usUser.getRoleIds())) {
					map2.put("js", roleMapNameId.get(usUser.getRoleIds()));
				} else {
					List<UsRole> usRoles = roleService.queryListByIds(Convert.strToList(usUser.getRoleIds()));
					String text = Convert.objectListToString(usRoles, "text");
					map2.put("js", text);
					roleMapNameId.put(usUser.getRoleIds(), text);
				}
			}

			if (Check.isNoEmpty(usUser.getOrgId())) {
				if (orgMapNameId.keySet().contains(usUser.getOrgId())) {
					map2.put("jg", orgMapNameId.get(usUser.getOrgId()));
				} else {
					List<UsOrganization> usRoles = organizationService
							.queryListByIds(Convert.strToList(usUser.getOrgId()));
					String text = Convert.objectListToString(usRoles, "text");
					map2.put("jg", text);
					orgMapNameId.put(usUser.getOrgId(), text);
				}
			}

			if (Check.isNoEmpty(usUser.getDeptId())) {
				if (orgMapNameId.keySet().contains(usUser.getDeptId())) {
					map2.put("bm", orgMapNameId.get(usUser.getDeptId()));
				} else {
					List<UsOrganization> usRoles = organizationService
							.queryListByIds(Convert.strToList(usUser.getDeptId()));
					String text = Convert.objectListToString(usRoles, "text");
					map2.put("bm", text);
					orgMapNameId.put(usUser.getDeptId(), text);
				}
			}

			if (Check.isNoEmpty(usUser.getJobId())) {
				if (orgMapNameId.keySet().contains(usUser.getJobId())) {
					map2.put("gw", orgMapNameId.get(usUser.getJobId()));
				} else {
					List<UsOrganization> usRoles = organizationService
							.queryListByIds(Convert.strToList(usUser.getJobId()));
					String text = Convert.objectListToString(usRoles, "text");
					map2.put("gw", text);
					orgMapNameId.put(usUser.getJobId(), text);
				}
			}

			dataList.add(map2);
		}

		map.put(title, dataList);
		setInfo.setObjsMap(map);
		setInfo.setFieldNames(fieldNameList);
		setInfo.setTitles(new String[] { title });
		setInfo.setHeadNames(headNameList);
		setInfo.setOut(baos);
		try {
			ExcelUtil.export2Excel(setInfo);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		ResultFile resultFile = new ResultFile("用户数据.xls", new ByteArrayInputStream(baos.toByteArray()), "application/vnd.ms-excel");
		return resultFile;
	}


	private File attachment;
	private String attachmentFileName;
	private String type;

	public File getAttachment() {
		return attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentFileName() {
		return attachmentFileName;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getUsgroups() {
		return usgroups;
	}

	public void setUsgroups(String usgroups) {
		this.usgroups = usgroups;
	}

}
