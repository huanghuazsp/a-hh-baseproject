package com.hh.usersystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.system.service.impl.BaseService;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.dto.PageRange;
import com.hh.system.util.dto.PagingData;
import com.hh.system.util.dto.ParamFactory;
import com.hh.system.util.dto.ParamInf;
import com.hh.usersystem.bean.usersystem.UsLeader;
import com.hh.usersystem.bean.usersystem.UsUser;

@Service
public class UsLeaderService extends BaseService<UsLeader> {

	@Autowired
	private UserService userService;

	public void addLeaders(UsLeader object) {
		List<UsLeader> usLeaders = queryListByProperty("objectId", object.getObjectId());
		Map<String, UsLeader> usMap = Convert.listToMap(usLeaders, "getLeaderId");
		List<String> leaderList = Convert.strToList(object.getLeaderId());
		for (String string : leaderList) {
			if (usMap.get(string) == null) {
				UsLeader usLeader = new UsLeader();
				usLeader.setObjectId(object.getObjectId());
				usLeader.setLeaderId(string);
				save(usLeader);
			}
		}
	}

	public void deleteLeaders(List<String> userIdList) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("objectId", userIdList);
		paramsMap.put("leaderId", userIdList);
		dao.deleteEntity("delete from " + UsLeader.class.getName()
				+ " where objectId in (:objectId) or leaderId in (:leaderId) ", paramsMap);
	}

	public void deleteLeaders(String userId) {
		deleteLeaders(Convert.strToList(userId));
	}

	@Override
	public PagingData<UsLeader> queryPagingData(UsLeader entity, PageRange pageRange) {
		ParamInf paramInf = ParamFactory.getParamHb();
		if (Check.isNoEmpty(entity.getObjectId())) {
			paramInf.is("objectId", entity.getObjectId());
		}
		PagingData<UsLeader> pageData = super.queryPagingData(pageRange, paramInf);
		List<UsLeader> usLeaderList = pageData.getItems();
		List<String> leaderIdList = new ArrayList<String>();
		for (UsLeader usLeader : usLeaderList) {
			leaderIdList.add(usLeader.getLeaderId());
		}
		if (leaderIdList.size() > 0) {
			List<UsUser> users = userService.queryListByIds(leaderIdList);
			Map<String, UsUser> userMap = Convert.listToMap(users);

			for (UsLeader usLeader : usLeaderList) {
				UsUser user = userMap.get(usLeader.getLeaderId());
				if (user != null) {
					usLeader.setLeaderText(user.getText());
					usLeader.setLeaderVdh(user.getVdh());
					usLeader.setLeaderVdzyj(user.getVdzyj());
				}
			}
		}

		return pageData;
	}

	public void deleteLeaders(UsLeader object) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("objectId", object.getObjectId());
		paramsMap.put("leaderId", Convert.strToList(object.getLeaderId()));
		dao.deleteEntity(
				"delete from " + UsLeader.class.getName() + " where objectId = :objectId and leaderId in (:leaderId) ",
				paramsMap);
	}

}