package com.hh.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.SysSql;
import com.hh.system.util.Check;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.usersystem.bean.usersystem.UsUser;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SqlService extends BaseService<SysSql> {
	@Autowired
	private UserService userService;
	@Override
	public PagingData<SysSql> queryPagingData(SysSql entity,
			PageRange pageRange) {
		PagingData<SysSql> pageData = super.queryPagingData(entity,
				pageRange);
		List<SysSql> sysErrors = pageData.getItems();
		for (SysSql sysError : sysErrors) {
			UsUser hhXtYh = userService.findObjectById_user(sysError
					.getVcreate());
			if (Check.isNoEmpty(hhXtYh)) {
				sysError.setCreateUserName(hhXtYh.getText());
			}
		}
		return pageData;
	}
}
