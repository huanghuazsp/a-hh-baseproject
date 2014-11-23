package com.hh.system.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.system.bean.HhSysParam;
import com.hh.system.service.inf.SystemServiceInf;
import com.hh.system.task.FileCopyDeleteTask;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.SysParam;
import com.hh.system.util.statics.StaticVar;

@Service
public class DataInitializationService {
	private static final Logger logger = Logger
			.getLogger(DataInitializationService.class);
	@Autowired
	private SysParamService sysParamService;

	@Transactional
	public void dataInitialization() throws IOException {
		initProperties();
		initTask();
		int size = initSysParam();
		if (size == 0) {
			try {
				SystemServiceInf systemService = BeanFactoryHelper.getBeanFactory()
						.getBean(SystemServiceInf.class);
				if (systemService != null) {
					systemService.initMenuAndUser();
				}
			} catch (Exception e) {
				logger.error("菜单加载失败！！");
				e.printStackTrace();
			}
		}
	}

	public int initSysParam() {
		List<HhSysParam> hhSysParams = sysParamService.queryAllList();
		int size = hhSysParams.size();
		if (size == 0) {
			HhSysParam hhSysParam = new HhSysParam();
			hhSysParam.setSysName("信息管理系统");
			hhSysParam.setSysIcon("/hhcommon/images/extjsico/17460336.png");
			hhSysParam.setSysImg("/hhcommon/images/big/apple/25.png");
			sysParamService.createEntity(hhSysParam);
			SysParam.hhSysParam = hhSysParam;
		} else {
			SysParam.hhSysParam = hhSysParams.get(0);
		}
		return size;
	}

	private void initTask() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Timer fileCopyDeleteTask = new Timer();
		fileCopyDeleteTask.schedule(new FileCopyDeleteTask(), cal.getTime(),
				24 * 60 * 60 * 1000);
	}

	private void initProperties() throws IOException {
		String classPath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		classPath = classPath.substring(1, classPath.indexOf("/WEB-INF"));
		StaticVar.contextPath = classPath;
		logger.info("项目绝对路径：" + StaticVar.contextPath);
		classPath = classPath.substring(0, classPath.lastIndexOf("/"));
		StaticVar.webappPath = classPath;
		logger.info("web服务器绝对路径：" + StaticVar.webappPath);
		StaticVar.filepath = StaticVar.webappPath + StaticVar.filebasepath;
		logger.info("附件绝对路径：" + StaticVar.filepath);
		FileUtil.isExist(StaticVar.filepath);
		FileUtil.isExist(StaticVar.filepath + "/task");
	}
}
