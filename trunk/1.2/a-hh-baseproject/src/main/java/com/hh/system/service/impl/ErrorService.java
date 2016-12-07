package com.hh.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SysError;
import com.hh.system.util.Check;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.UserService;

@Service
public class ErrorService extends BaseService<SysError> {
	@Autowired
	private UserService userService;

	@Override
	public PagingData<SysError> queryPagingData(SysError entity,
			PageRange pageRange) {
		PagingData<SysError> pageData = super.queryPagingData(entity,
				pageRange);
		List<SysError> sysErrors = pageData.getItems();
		for (SysError sysError : sysErrors) {
			UsUser hhXtYh = userService.findObjectById_user(sysError
					.getCreateUser());
			if (Check.isNoEmpty(hhXtYh)) {
				sysError.setCreateUserName(hhXtYh.getText());
			}
		}
		return pageData;
	}

}
