package com.hh.system.service.impl;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysData;

@Service
public class SysDataService  extends BaseTreeService<SysData> {
	protected boolean checkTextOnly(SysData hhXtData) {
		return dao
				.findWhetherData(
						"select count(o) from "
								+ hhXtData.getClass().getName()
								+ " o "
								+ "where o.text=:text and (o.id!=:id or :id is null) and node = :node and dataTypeId=:dataTypeId",
						hhXtData);
	}
}
