package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysEmail;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class EmailService extends BaseService<SysEmail> implements LoadDataTime {

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Override
	public PagingData<SysEmail> queryPagingData(SysEmail entity, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0), ParamFactory.getParamHb().orderDesc("dcreate"), pageRange,
				new String[] { "id", "title", "dcreate", "type" });
	}

	@Transactional
	public SysEmail sendEmail(SysEmail entity, String leixing) throws MessageException {
		HhXtYh hhXtYh = loginUserUtilService.findLoginUser();
		entity.setSendUserId(hhXtYh.getId());
		entity.setSendUserName(hhXtYh.getText());

		if (Check.isEmpty(entity.getId())) {
			dao.createEntity(entity);
		} else {
			dao.updateEntity(entity);
		}
		return entity;
	}

	public Map<String, Object> load() {

		Map<String, Object> map = new HashMap<String, Object>();
		int shouCount = findCount(ParamFactory.getParamHb().is("users", loginUserUtilService.findLoginUserId())
				.nolike("readUserId", loginUserUtilService.findLoginUserId())
				.nolike("deleteUserId", loginUserUtilService.findLoginUserId())
				.nolike("thoroughDeleteUserId", loginUserUtilService.findLoginUserId()));
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd107");
		map2.put("text", "收件箱");
		map2.put("vsj", "jsp-message-email-shouemaillist");
		map.put("email", map2);
		return map;
	}

	@Transactional
	public SysEmail findObjectById(String id) {
		String userId = loginUserUtilService.findLoginUserId();
		SysEmail sysEmail = super.findObjectById(id);
		if (Convert.toString(sysEmail.getReadUserId()).indexOf(userId) == -1) {
			sysEmail.setReadUserId(sysEmail.getReadUserId() + "," + userId);
			dao.updateEntity(sysEmail);
		}
		return sysEmail;
	}

	public PagingData<SysEmail> queryDeletePage(SysEmail object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().like("deleteUserId", loginUserUtilService.findLoginUserId())
						.nolike("thoroughDeleteUserId", loginUserUtilService.findLoginUserId()).orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	public PagingData<SysEmail> queryCGXPage(SysEmail object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findLoginUserId())
						.nolike("deleteUserId", loginUserUtilService.findLoginUserId())
						.nolike("thoroughDeleteUserId", loginUserUtilService.findLoginUserId()).is("type", "cgx")
						.orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	public PagingData<SysEmail> querySendPage(SysEmail object, PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().is("sendUserId", loginUserUtilService.findLoginUserId())
						.nolike("deleteUserId", loginUserUtilService.findLoginUserId())
						.nolike("thoroughDeleteUserId", loginUserUtilService.findLoginUserId()).is("type", "yfs")
						.orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	public PagingData<SysEmail> queryShouPage(SysEmail object, PageRange pageRange) {
		String userId = loginUserUtilService.findLoginUserId();
		PagingData<SysEmail> page = dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().like("users", loginUserUtilService.findLoginUserId())
						.nolike("deleteUserId", loginUserUtilService.findLoginUserId())
						.nolike("thoroughDeleteUserId", loginUserUtilService.findLoginUserId()).is("type", "yfs")
						.orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type", "readUserId" });

		for (SysEmail sysEmail : page.getItems()) {
			if (Convert.toString(sysEmail.getReadUserId()).indexOf(userId) > -1) {
				sysEmail.setRead(1);
			}
		}

		return page;
	}

	@Override
	public void deleteByIds(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findLoginUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId()).indexOf(userId) == -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId() + "," + userId);
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Transactional
	public void thoroughDelete(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findLoginUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getThoroughDeleteUserId()).indexOf(userId) == -1) {
					sysEmail.setThoroughDeleteUserId(sysEmail.getThoroughDeleteUserId() + "," + userId);
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Transactional
	public void recovery(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findLoginUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId()).indexOf(userId) > -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId().replaceAll("," + userId, ""));
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

}
