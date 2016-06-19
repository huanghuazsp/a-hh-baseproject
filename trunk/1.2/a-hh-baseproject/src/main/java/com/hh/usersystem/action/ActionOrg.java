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
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.MessageException;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.ExportSetInfo;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.model.ResultFile;
import com.hh.system.util.model.ReturnModel;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.service.impl.OrganizationService;
import com.hh.usersystem.service.impl.RoleService;

@SuppressWarnings("serial")
public class ActionOrg extends BaseServiceAction<UsOrganization> {
	private String orgs;
	private String selectType;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private RoleService roleService;

	@Override
	public BaseService<UsOrganization> getService() {
		return organizationService;
	}

	public Object queryPagingData() {
		return organizationService.queryPagingData(object, this.getPageRange());
	}

	public Object queryOrgListByPidAndLx() {
		List<UsOrganization> organizationList = organizationService.queryOrgListByPidAndLx(object,
				paramsMap.get("node"));
		return organizationList;
	}

	public Object queryOrgAndUsersList() {
		return organizationService.queryOrgAndUsersList(object);
	}

	public Object queryOrgListByPid() {
		List<UsOrganization> organizationList = organizationService.queryOrgListByPid(object, orgs, selectType);
		return organizationList;
	}

	public Object findObjectById() {
		UsOrganization hhXtCd = organizationService.findObjectById(this.object.getId());
		return hhXtCd;
	}

	public void deleteByIds() {
		organizationService.deleteByIds(this.getIds());
	}

	@Override
	public Object queryTreeList() {
		List<UsOrganization> organizationList = organizationService.queryTreeList(object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
		return organizationList;
	}

	public Object queryStateTreeList() {
		return organizationService.queryTreeList(ParamFactory.getParamHb().is("state", 0).is("node", object.getNode()));
	}

	public Object queryTreeListByLx() {
		return organizationService.queryTreeListByLx(object, Convert.toBoolean(request.getParameter("isNoLeaf")));
	}

	public Object save() {
		try {
			UsOrganization hhXtYh = organizationService.save(this.object);
			return null;
		} catch (MessageException e) {
			return new ReturnModel(e.getMessage());
		}
	}

	public void restCode() {
		organizationService.restCode(this.object.getNode());
	}

	public Object findOrgTextByIds() {
		String returnTextString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();
			List<UsOrganization> organizationList = organizationService
					.queryList(ParamFactory.getParamHb().in(StaticVar.entityId, Convert.strToList(object.getId())));
			for (UsOrganization organization : organizationList) {
				texts.append(organization.getText() + ",");
			}
			if (Check.isNoEmpty(texts)) {
				returnTextString = texts.substring(0, texts.length() - 1);
			}
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("text", returnTextString);
		returnMap.put("id", object.getId());
		return returnMap;
	}

	public String getOrgs() {
		return orgs;
	}

	public void setOrgs(String orgs) {
		this.orgs = orgs;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	private File attachment;
	private String attachmentFileName;
	private String type;

	public Object importData() throws Exception {
		response.setContentType("text/html");
		String path = FileUtil.uploadFile(attachment, attachmentFileName, type);
		File file = new File(StaticVar.filepath + path);
		List<Map<String, Object>> mapList = ExcelUtil.getMapList(file, 1);
		try {
			organizationService.save(mapList);
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
		String title = "机构数据";
		ExportSetInfo setInfo = new ExportSetInfo();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<String[]> headNameList = new ArrayList<String[]>();
		List<String[]> fieldNameList = new ArrayList<String[]>();
		headNameList.add(new String[] { "标识", "名称", "上级名称", "类型", "状态", "机构角色", "自定义编码", "简称", "备注" });
		fieldNameList.add(new String[] { "id", "text", "sjmc", "lx", "zt", "jgjs", "zdybm", "jc", "bz" });

		List<UsOrganization> organizations = new ArrayList<UsOrganization>();
		organizationService.queryAllList(organizations, "root");
		for (UsOrganization organization : organizations) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", organization.getId());
			map2.put("text", organization.getText());
			map2.put("zdybm", organization.getZdybm_());
			map2.put("jc", organization.getJc_());
			map2.put("bz", organization.getMs_());
			map2.put("zt", organization.getState() == 1 ? "禁用" : "正常");

			String lx = "机构";
			if (organization.getLx_() == 1) {
				lx = "机构";
			} else if (organization.getLx_() == 2) {
				lx = "部门";
			} else if (organization.getLx_() == 3) {
				lx = "岗位";
			}

			map2.put("lx", lx);

			if (Check.isNoEmpty(organization.getNode())) {
				if (orgMapNameId.keySet().contains(organization.getNode())) {
					map2.put("sjmc", orgMapNameId.get(organization.getNode()));
				} else {
					List<UsOrganization> usOrganizations = organizationService
							.queryListByIds(Convert.strToList(organization.getNode()));
					String text = "";
					if (usOrganizations.size() > 0) {
						text = usOrganizations.get(0).getText();
					}
					map2.put("sjmc", text);
					orgMapNameId.put(organization.getNode(), text);
				}
			}

			if (Check.isNoEmpty(organization.getRoleIds())) {
				if (roleMapNameId.keySet().contains(organization.getRoleIds())) {
					map2.put("jgjs", roleMapNameId.get(organization.getRoleIds()));
				} else {
					List<UsRole> usRoles = roleService.queryListByIds(Convert.strToList(organization.getRoleIds()));
					String text = Convert.objectListToString(usRoles, "text");
					map2.put("jgjs", text);
					roleMapNameId.put(organization.getRoleIds(), text);
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

		ResultFile resultFile = new ResultFile("机构数据.xls", new ByteArrayInputStream(baos.toByteArray()),
				"application/vnd.ms-excel");
		return resultFile;
	}

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

}
