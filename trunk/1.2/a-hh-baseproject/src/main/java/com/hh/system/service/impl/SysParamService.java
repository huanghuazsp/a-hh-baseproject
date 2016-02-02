package com.hh.system.service.impl;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysParams;
import com.hh.system.util.MessageException;
import com.hh.system.util.SysParam;

@Service
public class SysParamService extends BaseService<SysParams> {

	@Override
	public SysParams findObjectById(String id) {
		return SysParam.sysParam;
	}

	@Override
	public SysParams save(SysParams entity) throws MessageException {
		SysParam.sysParam = super.save(entity);
		return SysParam.sysParam;
	}
}
