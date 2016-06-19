package com.hh.system.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.bean.SysData;
import com.hh.system.bean.SysDataType;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.impl.SysDataService;
import com.hh.system.service.impl.SysDataTypeService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.MessageException;
import com.hh.system.util.StaticVar;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.document.ExcelUtil;
import com.hh.system.util.document.ExportSetInfo;
import com.hh.system.util.model.ResultFile;

@SuppressWarnings("serial")
public class ActionSysDataType extends BaseServiceAction<SysDataType> {

	public BaseService<SysDataType> getService() {
		return sysDataTypeService;
	}

	@Autowired
	private SysDataTypeService sysDataTypeService;

	@Autowired
	private SysDataService sysDataService;
	
	public Object queryTreeListCode() {
		return sysDataTypeService.queryTreeListCode(this.object,
				Convert.toBoolean(request.getParameter("isNoLeaf")));
	}

	public Object findObjectByCode() {
		return sysDataTypeService.findObjectByProperty("code", object.getId());
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

	public Object importData() throws Exception {
		response.setContentType("text/html");
		String path = FileUtil.uploadFile(attachment, attachmentFileName,
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

	public Object download() {
		Map<String, String> typeMapNameId = new HashMap<String, String>();
		Map<String, String> dataMapNameId = new HashMap<String, String>();
		Map<String, String> typeMapCodeName = new HashMap<String, String>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String title = "数据字典";
		ExportSetInfo setInfo = new ExportSetInfo();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		List<String[]> headNameList = new ArrayList<String[]>();
		List<String[]> fieldNameList = new ArrayList<String[]>();
		headNameList.add(new String[] { "标识", "名称", "上级名称", "类型", "所属类别", "编码" });
		fieldNameList
				.add(new String[] { "id", "text", "sjmc", "lx", "sxlb", "code" });
		List<SysDataType> sysDataTypeList = sysDataTypeService.queryAllList();
		for (SysDataType sysDataType : sysDataTypeList) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", sysDataType.getId());
			map2.put("text", sysDataType.getText());
			map2.put("code", sysDataType.getCode());
			map2.put("lx", "类别");
			if (Check.isNoEmpty(sysDataType.getNode())) {
				if (typeMapNameId.keySet().contains(sysDataType.getNode())) {
					map2.put("sjmc", typeMapNameId.get(sysDataType.getNode()));
				} else {
					List<SysDataType> sysDataTypes = sysDataTypeService
							.queryListByIds(Convert.strToList(sysDataType
									.getNode()));
					String text = "";
					if (sysDataTypes.size() > 0) {
						text = sysDataTypes.get(0).getText();
					}
					map2.put("sjmc", text);
					typeMapNameId.put(sysDataType.getNode(), text);
				}
			}
			typeMapCodeName.put( sysDataType.getCode(), sysDataType.getText());
			map2.put("sxlb", "");
			dataList.add(map2);
		}
		
		List<SysData> sysDataList = sysDataService.queryAllList();
		for (SysData sysData : sysDataList) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("id", sysData.getId());
			map2.put("text", sysData.getText());
			map2.put("code", sysData.getCode());
			map2.put("lx", "字典");
			if (Check.isNoEmpty(sysData.getNode())) {
				if (dataMapNameId.keySet().contains(sysData.getNode())) {
					map2.put("sjmc", dataMapNameId.get(sysData.getNode()));
				} else {
					List<SysData> sysDataTypes = sysDataService
							.queryListByIds(Convert.strToList(sysData
									.getNode()));
					String text = "";
					if (sysDataTypes.size() > 0) {
						text = sysDataTypes.get(0).getText();
					}
					map2.put("sjmc", text);
					dataMapNameId.put(sysData.getNode(), text);
				}
			}
			map2.put("sxlb", Convert.toString(typeMapCodeName.get(sysData.getDataTypeId())));
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
		ResultFile resultFile = new ResultFile("数据字典.xls", new ByteArrayInputStream(baos.toByteArray()), "application/vnd.ms-excel");
		return resultFile;
	}

}
