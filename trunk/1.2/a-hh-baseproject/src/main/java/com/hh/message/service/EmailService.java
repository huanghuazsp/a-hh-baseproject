package com.hh.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.message.bean.SysEmail;
import com.hh.system.bean.SystemFile;
import com.hh.system.service.impl.BaseService;
import com.hh.system.service.inf.IFileOper;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.ThreadUtil;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class EmailService extends BaseService<SysEmail> implements
		LoadDataTime, IFileOper {

	@Autowired
	private LoginUserUtilService loginUserUtilService;

	@Override
	public PagingData<SysEmail> queryPagingData(SysEmail entity,
			PageRange pageRange) {
		return dao
				.queryPagingData(this.getGenericType(0), ParamFactory
						.getParamHb().orderDesc("dcreate"), pageRange,
						new String[] { "id", "title", "dcreate", "type",
								"sendUserName", "userNames" });
	}

	@Transactional
	public SysEmail sendEmail(SysEmail entity, String leixing)
			throws MessageException {
		UsUser hhXtYh = loginUserUtilService.findLoginUser();
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
		return load2(loginUserUtilService.findUserId());
	}

	public Map<String, Object> count() {
		String userId = loginUserUtilService.findUserId();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		int sjCount = findCount(ParamFactory.getParamHb().like("users", userId)
				.nolike("deleteUserId", userId)
				.nolike("thoroughDeleteUserId", userId).is("type", "yfs"));

		int fsCount = findCount(ParamFactory.getParamHb()
				.is("sendUserId", userId).nolike("deleteUserId", userId)
				.nolike("thoroughDeleteUserId", userId).is("type", "yfs"));

		int cgxCount = findCount(ParamFactory.getParamHb()
				.is("sendUserId", userId).nolike("deleteUserId", userId)
				.nolike("thoroughDeleteUserId", userId).is("type", "cgx"));

		int scCount = findCount(ParamFactory.getParamHb()
				.like("deleteUserId", userId)
				.nolike("thoroughDeleteUserId", userId));

		int wdCount = findCount(ParamFactory.getParamHb().like("users", userId)
				.nolike("readUserId", userId).nolike("deleteUserId", userId)
				.is("type", "yfs").nolike("thoroughDeleteUserId", userId));
		returnMap.put("wdCount", wdCount);
		returnMap.put("sjCount", sjCount);
		returnMap.put("fsCount", fsCount);
		returnMap.put("cgxCount", cgxCount);
		returnMap.put("scCount", scCount);

		return returnMap;
	}

	@Transactional
	public Map<String, Object> load2(String userId) {

		Map<String, Object> map = new HashMap<String, Object>();
		int shouCount = findCount(ParamFactory.getParamHb()
				.like("users", userId).nolike("readUserId", userId)
				.nolike("deleteUserId", userId).is("type", "yfs")
				.nolike("thoroughDeleteUserId", userId));
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", shouCount);
		map2.put("id", "93bb64fe-e50a-40b2-ab59-b1ae543cd107");
		map2.put("text", "收件箱");
		map2.put("vsj", "jsp-message-email-emailmain");
		map.put("email", map2);
		map.put("userId", userId);
		return map;
	}

	@Transactional
	public SysEmail findObjectById(String id) {
		String userId = loginUserUtilService.findUserId();
		SysEmail sysEmail = super.findObjectById(id);
		if (sysEmail != null) {
			if (Convert.toString(sysEmail.getReadUserId()).indexOf(userId) == -1) {
				sysEmail.setReadUserId(sysEmail.getReadUserId() + "," + userId);
				dao.updateEntity(sysEmail);
				ThreadUtil.getThreadPool().execute(
						new EmailThread(loginUserUtilService.findUserId()));
			}
		}
		return sysEmail;
	}

	public PagingData<SysEmail> queryDeletePage(SysEmail object,
			PageRange pageRange) {
		return dao
				.queryPagingData(
						this.getGenericType(0),
						ParamFactory
								.getParamHb()
								.like("deleteUserId",
										loginUserUtilService.findUserId())
								.nolike("thoroughDeleteUserId",
										loginUserUtilService.findUserId())
								.orderDesc("dcreate").like("title", object.getTitle()), pageRange, new String[] {
								"id", "title", "dcreate", "type",
								"sendUserName", "userNames" });
	}

	public PagingData<SysEmail> queryCGXPage(SysEmail object,
			PageRange pageRange) {
		return dao.queryPagingData(
				this.getGenericType(0),
				ParamFactory
						.getParamHb()
						.is("sendUserId", loginUserUtilService.findUserId())
						.nolike("deleteUserId",
								loginUserUtilService.findUserId())
						.nolike("thoroughDeleteUserId",
								loginUserUtilService.findUserId())
						.is("type", "cgx").orderDesc("dcreate").like("title", object.getTitle()), pageRange,
				new String[] { "id", "title", "dcreate", "type",
						"sendUserName", "userNames" });
	}

	public PagingData<SysEmail> querySendPage(SysEmail object,
			PageRange pageRange) {
		return dao.queryPagingData(
				this.getGenericType(0),
				ParamFactory
						.getParamHb()
						.is("sendUserId", loginUserUtilService.findUserId())
						.nolike("deleteUserId",
								loginUserUtilService.findUserId())
						.nolike("thoroughDeleteUserId",
								loginUserUtilService.findUserId())
						.is("type", "yfs").orderDesc("dcreate").like("title", object.getTitle()), pageRange,
				new String[] { "id", "title", "dcreate", "type",
						"sendUserName", "userNames" });
	}

	public PagingData<SysEmail> queryShouPage(SysEmail object,
			PageRange pageRange) {
		String userId = loginUserUtilService.findUserId();
		ParamInf paramInf = ParamFactory
				.getParamHb()
				.like("users", loginUserUtilService.findUserId())
				.nolike("deleteUserId",
						loginUserUtilService.findUserId())
				.nolike("thoroughDeleteUserId",
						loginUserUtilService.findUserId())
				.is("type", "yfs").orderDesc("dcreate");
		paramInf.like("title", object.getTitle());
		PagingData<SysEmail> page = dao.queryPagingData(
				this.getGenericType(0),
				paramInf, pageRange,
				new String[] { "id", "title", "dcreate", "type", "readUserId",
						"sendUserName", "userNames" });

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
			String userId = loginUserUtilService.findUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId())
						.indexOf(userId) == -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId() + ","
							+ userId);
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Transactional
	public void thoroughDelete(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getThoroughDeleteUserId())
						.indexOf(userId) == -1) {
					sysEmail.setThoroughDeleteUserId(sysEmail
							.getThoroughDeleteUserId() + "," + userId);
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Transactional
	public void recovery(String ids) {
		if (Check.isNoEmpty(ids)) {
			String userId = loginUserUtilService.findUserId();
			List<SysEmail> sysEmails = queryListByIds(Convert.strToList(ids));
			for (SysEmail sysEmail : sysEmails) {
				if (Convert.toString(sysEmail.getDeleteUserId())
						.indexOf(userId) > -1) {
					sysEmail.setDeleteUserId(sysEmail.getDeleteUserId()
							.replaceAll("," + userId, ""));
					dao.updateEntity(sysEmail);
				}
			}
		}
	}

	@Override
	public void fileOper(SystemFile systemFile) {
		SysEmail sysEmail = dao.findEntityByPK(SysEmail.class,
				systemFile.getServiceId());
		if (sysEmail == null) {
			systemFile.setDestroy(1);
		}
	}

}
