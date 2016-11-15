package com.hh.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.oa.bean.OaMeetingApply;
import com.hh.oa.bean.OaMeetingApplyUser;
import com.hh.oa.service.impl.OaMeetingApplyService;
import com.hh.oa.service.impl.OaMeetingApplyUserService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.dto.PageRange;

@SuppressWarnings("serial")
public class ActionMeetingApplyUser extends
		BaseServiceAction<OaMeetingApplyUser> {
	@Autowired
	private OaMeetingApplyUserService oameetingapplyuserService;
	
	private String textStr;

	public BaseService<OaMeetingApplyUser> getService() {
		return oameetingapplyuserService;
	}

	@Autowired
	private OaMeetingApplyService oameetingapplyService;

	public Object queryShouPage() {
		PageRange pageRange = this.getPageRange();
		OaMeetingApply oaMeetingApply = new OaMeetingApply();
		oaMeetingApply.setText(textStr);
		return oameetingapplyService.queryShouPage(oaMeetingApply, pageRange);
	}

	@Override
	public Object queryPagingData() {
		PageRange pageRange = this.getPageRange();
		OaMeetingApply oaMeetingApply = new OaMeetingApply();
		oaMeetingApply.setText(textStr);
		return oameetingapplyService.queryPagingData(oaMeetingApply, pageRange);
	}

	public String getTextStr() {
		return textStr;
	}

	public void setTextStr(String textStr) {
		this.textStr = textStr;
	}
	
	
}
