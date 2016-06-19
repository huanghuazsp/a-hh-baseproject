package com.hh.system.service.impl;

import org.springframework.stereotype.Service;

import com.hh.system.bean.SysParams;
import com.hh.system.bean.SystemFile;
import com.hh.system.service.inf.IFileOper;
import com.hh.system.util.MessageException;
import com.hh.system.util.SysParam;
import com.hh.system.util.dto.ParamFactory;
import com.hh.usersystem.bean.usersystem.UsUser;

@Service
public class SysParamService extends BaseService<SysParams> implements
		IFileOper {

	@Override
	public SysParams findObjectById(String id) {
		return SysParam.sysParam;
	}

	@Override
	public SysParams save(SysParams entity) throws MessageException {
		SysParam.sysParam = super.save(entity);
		return SysParam.sysParam;
	}

	@Override
	public void fileOper(SystemFile systemFile) {
		int count = findCount(ParamFactory.getParamHb().or(
				ParamFactory.getParamHb().is("sysImg", systemFile.getId())
						.is("sysIcon", systemFile.getId())));
		if (count == 0) {
			systemFile.setDestroy(1);
		}
	}
}
