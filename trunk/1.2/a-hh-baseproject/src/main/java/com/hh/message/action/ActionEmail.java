package com.hh.message.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.message.bean.SysEmail;
import com.hh.message.service.EmailService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ReturnModel;

@SuppressWarnings("serial")
public class ActionEmail extends BaseServiceAction<SysEmail> {
	@Autowired
	private EmailService sendEmailService;

	public BaseService<SysEmail> getService() {
		return sendEmailService;
	}
	
	public Object findReadObjectById() {
		SysEmail object = sendEmailService.findReadObjectById(this.object.getId());
		return object;
	}

	public Object queryShouPage() {
		return sendEmailService.queryShouPage(object, this.getPageRange());
	}

	public Object querySendPage() {
		return sendEmailService.querySendPage(object, this.getPageRange());
	}

	public Object queryCGXPage() {
		return sendEmailService.queryCGXPage(object, this.getPageRange());
	}

	public Object queryDeletePage() {
		return sendEmailService.queryDeletePage(object, this.getPageRange());
	}

	public Object sendEmail() {
		try {
			SysEmail object = sendEmailService.sendEmail(this.object);
		} catch (MessageException e) {
			return e;
		}
		return null;
	}

	public void recovery() {
		sendEmailService.recovery(this.getIds());
	}
	public void deleteMeByIds() {
		sendEmailService.deleteMeByIds(this.getIds());
	}
	
	
	public void thoroughDelete() {
		sendEmailService.thoroughDelete(this.getIds());
	}

}
