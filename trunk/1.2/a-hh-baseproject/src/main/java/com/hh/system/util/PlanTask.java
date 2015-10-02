package com.hh.system.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.hh.hibernate.dao.inf.IHibernateDAO;
import com.hh.system.bean.SystemPlantask;
import com.hh.system.service.impl.BeanFactoryHelper;

public class PlanTask extends TimerTask {
	private SystemPlantask taskConfig;
	private IHibernateDAO dao;

	public PlanTask(SystemPlantask taskConfig) {
		this.taskConfig = taskConfig;
	}

	public IHibernateDAO findDao() {
		if (this.dao == null) {
			this.dao = BeanFactoryHelper.getBeanFactory().getBean(
					IHibernateDAO.class);
		}
		return this.dao;
	}

	public void run() {
		String formula = this.taskConfig.getFormula();
		List<Map<String, Object>> mapList = Json.toMapList(formula);
		for (Map<String, Object> map1 : mapList) {
			String type = Convert.toString(map1.get("type"));
			String value = Convert.toString(map1.get("value"));
			if ("class".equals(type)) {
				String[] formulaArr = value.split("#");
				if (formulaArr.length > 1) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (formulaArr.length > 2)
						map = Json.toMap(formulaArr[2]);
					try {
						ClassReflex.executeSpringClass(formulaArr[0],
								formulaArr[1], map);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					findDao().executeSql(value);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}