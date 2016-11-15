 package com.hh.oa.service.impl;
import com.hh.system.service.impl.BaseService;
import org.springframework.stereotype.Service;

import com.hh.oa.bean.OaMeetingApply;

@Service
public class OaMeetingApplyService extends BaseService<OaMeetingApply> {

	public void updateDate(OaMeetingApply object) {
		dao.updateEntity("update "+OaMeetingApply.class.getName()+" o set o.start=:start,o.end=:end where o.id=:id ", object);
		
	}
}
 