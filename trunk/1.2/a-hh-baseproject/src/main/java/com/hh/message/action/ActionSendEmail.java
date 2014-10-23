package com.hh.message.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hh.message.bean.SysSendEmail;
import com.hh.message.service.SendEmailService;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.base.BaseServiceAction;
import com.hh.system.util.model.ReturnModel;

@SuppressWarnings("serial")
public class ActionSendEmail extends BaseServiceAction<SysSendEmail> {
	@Autowired
	private SendEmailService sendEmailService;

	public BaseService<SysSendEmail> getService() {
		return sendEmailService;
	}

	public void sendEmail() {
		try {
			String leixing = Convert.toString(request.getParameter("leixing"));
			SysSendEmail object = sendEmailService.sendEmail(this.object,leixing);
			this.getResultMap().put("object", object);
		} catch (MessageException e) {
			this.getResultMap().put("returnModel",
					new ReturnModel(e.getMessage()));
		}
		this.returnResult();
	}

}
