 package com.hh.oa.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.system.util.base.BaseServiceAction;
import com.hh.message.bean.SysEmail;
import com.hh.oa.bean.OaNotice;
import com.hh.system.service.impl.BaseService;
import com.hh.oa.service.impl.OaNoticeService;

@SuppressWarnings("serial")
public class ActionNotice extends BaseServiceAction< OaNotice > {
	@Autowired
	private OaNoticeService oanoticeService;
	public BaseService<OaNotice> getService() {
		return oanoticeService;
	}
	public Object queryShouPage() {
		return oanoticeService.queryShouPage(object,
				this.getPageRange());
	}
	
	public Object findReadObjectById() {
		OaNotice object = oanoticeService.findReadObjectById(this.object.getId());
		return object;
	}
}
 