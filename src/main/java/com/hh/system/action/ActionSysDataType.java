package com.hh.system.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysDataType;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataTypeService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.date.DateFormat;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.ExportSetInfo;
import com.hh.system.util.document.FileUpload;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.bean.usersystem.UsOrganization;
import com.hh.usersystem.bean.usersystem.UsRole;
import com.hh.usersystem.bean.usersystem.UsUser;

@SuppressWarnings("serial")
public class ActionSysDataType extends BaseServiceAction<SysDataType> {

	public BaseService<SysDataType> getService() {
		return sysDataTypeService;
	}

	@Autowired
	private SysDataTypeService sysDataTypeService;

	public Object queryTreeListCode() {
		return sysDataTypeService.queryTreeListCode(this.object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}
	public Object findObjectByCode() {
		return sysDataTypeService.findObjectByProperty("code", object.getId());
	}
	

	private byte[] bytes = null;
	private String title;
	private File attachment;
	private String attachmentFileName;
	private String type;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	public Object importData() throws Exception {
		response.setContentType("text/html");
		String path = FileUpload.uploadFile(attachment, attachmentFileName,
				type);
		File file = new File(StaticVar.filepath + path);
		try {
			List<Map<String, Object>> mapList = ExcelUtil.getMapList(file, 1);
			sysDataTypeService.save(mapList);
		} catch (MessageException e) {
			return e;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("path", path);
		returnMap.put("attachmentFileName", attachmentFileName);
		return returnMap;
	}

	public String download() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String title = "用户数据";
		ExportSetInfo setInfo = new ExportSetInfo();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<String[]> headNameList = new ArrayList<String[]>();
		List<String[]> fieldNameList = new ArrayList<String[]>();
		headNameList.add(new String[] { "标识", "名称", "账号", "性别", "状态", "联系电话",
				"电子邮件", "生日", "角色", "机构", "部门", "岗位" });
		fieldNameList.add(new String[] { "id", "text", "zh", "xb", "zt",
				"lxdh", "dzyj", "sr", "js", "jg", "bm", "gw" });
		List<SysDataType> sysDataTypeList = sysDataTypeService.queryAllList();
		for (SysDataType sysDataType : sysDataTypeList) {
			Map<String, Object> map2 = new HashMap<String, Object>();

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

		this.setBytes(baos.toByteArray());
		this.setTitle("用户数据");
		return "excel";
	}

	

}
