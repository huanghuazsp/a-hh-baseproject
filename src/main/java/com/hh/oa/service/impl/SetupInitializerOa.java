package com.hh.oa.service.impl;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.message.service.SysMessageService;
import com.hh.message.service.SysShouEmailService;
import com.hh.usersystem.bean.usersystem.HhXtCd;
import com.hh.usersystem.util.steady.StaticProperties;

@Service
public class SetupInitializerOa {

	@Autowired
	private SysShouEmailService sysShouEmailService;
	@Autowired
	private SysMessageService sysMessageService;

	@PostConstruct
	public void initialize() {
		HhXtCd rootHhXtCd = new HhXtCd("c4ef89f5-b94a-4737-a935-e49bfce0105e",
				"个人办公", "com.hh.global.NavigAtionWindow",
				"/hhcommon/images/extjsico/17460359.png", 1, 0);
		rootHhXtCd.setChildren(new ArrayList<HhXtCd>());
		// rootHhXtCd.getChildren().add(
		// new HhXtCd("63467b5b-ce83-4c56-a3e4-70e9e01739d5", "日程安排",
		// "jsp/calendar.jsp",
		// "/hhcommon/images/extjsico/17460310.png", 0, 1));

		HhXtCd rootMailHhXtCd = new HhXtCd(
				"a4ebc255-2045-4bb9-ace0-dabbb3e72b64", "邮箱",
				"com.hh.global.NavigAtionWindow",
				"/hhcommon/images/icons/email/email.png", 1, 0);
		rootMailHhXtCd.setChildren(new ArrayList<HhXtCd>());
		rootMailHhXtCd.getChildren().add(
				new HhXtCd("93bb64fe-e50a-40b2-ab59-b1ae543cd107", "收件箱",
						"jsp-message-email-shouemaillist",
						"/hhcommon/images/icons/email/email.png", 0, 1));
		rootMailHhXtCd.getChildren().add(
				new HhXtCd("bb84e514-2f94-4b02-96fe-9af208166f11", "发件箱",
						"jsp-message-email-sendemaillist",
						"/hhcommon/images/icons/email/email_edit.png", 0, 1));
		rootHhXtCd.getChildren().add(rootMailHhXtCd);

		rootHhXtCd.getChildren().add(
				new HhXtCd("fb7ac8da-63f1-467c-8822-791591f00559", "日程安排",
						"jsp-oa-schedule-fullcalendar",
						"/hhcommon/images/extjsico/17460310.png", 0, 1));

		StaticProperties.hhXtCds.add(rootHhXtCd);
		com.hh.system.util.StaticProperties.loadDataTimeList
				.add(sysShouEmailService);
		com.hh.system.util.StaticProperties.loadDataTimeList
				.add(sysMessageService);
	}
}