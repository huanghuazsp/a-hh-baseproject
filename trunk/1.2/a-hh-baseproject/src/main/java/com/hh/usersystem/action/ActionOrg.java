package com.hh.usersystem.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.action.ActionFile;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.Json;
import com.hh.system.util.MessageException;
import com.hh.system.util.PrimaryKey;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.FileUpload;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.system.util.model.ReturnModel;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsOrgRole;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.service.impl.OrganizationService;
import com.hh.usersystem.service.impl.RoleService;

@SuppressWarnings("serial")
public class ActionOrg extends BaseServiceAction<UsOrganization> {
	private String orgs;
	private String selectType;

	@Autowired
	private OrganizationService organizationService;
	

	@Override
	public BaseService<UsOrganization> getService() {
		return organizationService;
	}

	public Object queryPagingData() {
		return organizationService.queryPagingData(object,
				this.getPageRange());
	}

	public Object queryOrgListByPidAndLx() {
		List<UsOrganization> organizationList = organizationService
				.queryOrgListByPidAndLx(object, paramsMap.get("node"));
		return organizationList;
	}

	public Object queryOrgAndUsersList() {
		return organizationService.queryOrgAndUsersList(object);
	}

	public Object queryOrgListByPid() {
		List<UsOrganization> organizationList = organizationService
				.queryOrgListByPid(object, orgs, selectType);
		return organizationList;
	}

	public Object findObjectById() {
		UsOrganization hhXtCd = organizationService.findObjectById(this.object
				.getId());
		return hhXtCd;
	}

	public void deleteByIds() {
		organizationService.deleteByIds(this.getIds());
	}

	@Override
	public Object queryTreeList() {
		return organizationService.queryTreeList(object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}
	
	public Object queryStateTreeList() {
		return organizationService.queryTreeList(ParamFactory.getParamHb().is("state", 0).is("node", object.getNode()));
	}

	public Object queryTreeListByLx() {
		return organizationService.queryTreeListByLx(object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}

	public Object save() {
		try {
			UsOrganization hhXtYh = organizationService.save(this.object);
			return null;
		} catch (MessageException e) {
			return new ReturnModel(e.getMessage());
		}
		
	}

	public Object findOrgTextByIds() {
		String returnTextString = "";
		if (Check.isNoEmpty(object.getId())) {
			StringBuffer texts = new StringBuffer();
			List<UsOrganization> organizationList = organizationService
					.queryList(ParamFactory.getParamHb().in(StaticVar.entityId,
							Convert.strToList(object.getId())));
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
	
	
	private byte[] bytes = null;
	private String name;
	private File attachment;
	private String attachmentFileName;
	private String filePath;
	public Object importData() {
		response.setContentType("text/html");
		String path = FileUpload.uploadFile(attachment, attachmentFileName,
				filePath);
		File file = new File(StaticVar.filepath+path);
		
		
		
		try {
			List<Map<String, Object>> mapList = ExcelUtil.getMapList(file, 1);
			organizationService.save(mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("path", path);
		returnMap.put("attachmentFileName", attachmentFileName);
		returnMap.put("name", name);
		return returnMap;
	}

	

	public String download() {
		this.setName("test");
		return "file";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}



}
