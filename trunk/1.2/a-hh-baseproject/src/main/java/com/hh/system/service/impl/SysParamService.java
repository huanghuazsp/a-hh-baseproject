package com.hh.system.service.impl;

import org.springframework.stereotype.Service;

import com.hh.system.bean.HhSysParam;
import com.hh.system.util.MessageException;
import com.hh.system.util.SysParam;

@Service
public class SysParamService extends BaseService<HhSysParam> {

	@Override
	public HhSysParam findObjectById(String id) {
		return SysParam.hhSysParam;
	}

	@Override
	public HhSysParam save(HhSysParam entity) throws MessageException {
		SysParam.hhSysParam = super.save(entity);
		return SysParam.hhSysParam;
	}
}
