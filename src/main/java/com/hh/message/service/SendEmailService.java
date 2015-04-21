package com.hh.message.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.message.bean.SysSendEmail;
import com.hh.message.bean.SysShouEmail;
import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.MessageException;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.statics.StaticVar;
import com.hh.usersystem.service.impl.LoginUserUtilService;

@Service
public class SendEmailService extends BaseService<SysSendEmail> {
	@Autowired
	private IHibernateDAO<SysShouEmail> sysShouDao;
	@Autowired
	private LoginUserUtilService loginUserUtilService;
	@Override
	public PagingData<SysSendEmail> queryPagingData(SysSendEmail entity,
			PageRange pageRange) {
		return dao.queryPagingData(this.getGenericType(0),
				ParamFactory.getParamHb().orderDesc("dcreate"),
				pageRange, new String[] { "id", "title", "dcreate", "type" });
	}

	@Transactional
	public SysSendEmail sendEmail(SysSendEmail entity, String leixing)
			throws MessageException {
		if (Check.isEmpty(entity.getId())) {
			dao.createEntity(entity);
		} else {
			dao.updateEntity(entity);
		}
		if ("0".equals(leixing)) {
			String sendUserId = loginUserUtilService.findLoginUserId();
			List<String> idList = Convert.strToList(entity.getUsers());
			for (String userid : idList) {
				SysShouEmail shouEmail = new SysShouEmail();
				try {
					BeanUtils.copyProperties(shouEmail, entity);
					shouEmail.setId(UUID.randomUUID().toString());
					shouEmail.setShouUser(userid);
					shouEmail.setSendUser(sendUserId);
					shouEmail.setType("zc");
					sysShouDao.createEntity(shouEmail);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return entity;
	}
}
