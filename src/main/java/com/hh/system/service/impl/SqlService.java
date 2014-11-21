package com.hh.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.bean.HHXtSql;
import com.hh.system.util.Check;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.usersystem.bean.usersystem.HhXtYh;
import com.hh.usersystem.service.impl.UserService;

@Service
public class SqlService extends BaseService<HHXtSql> {
	@Autowired
	private UserService userService;
	@Override
	public PagingData<HHXtSql> queryPagingData(HHXtSql entity,
			PageRange pageRange) {
		PagingData<HHXtSql> pageData = super.queryPagingData(entity,
				pageRange);
		List<HHXtSql> hhXtErrors = pageData.getItems();
		for (HHXtSql hhXtError : hhXtErrors) {
			HhXtYh hhXtYh = userService.findObjectById_user(hhXtError
					.getVcreate());
			if (Check.isNoEmpty(hhXtYh)) {
				hhXtError.setCreateUserName(hhXtYh.getText());
			}
		}
		return pageData;
	}
}
