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

import com.hh.system.bean.SysParams;
import com.hh.system.service.inf.SystemServiceInf;
import com.hh.system.task.FileCopyDeleteTask;
import com.hh.system.util.Convert;
import com.hh.system.util.FileUtil;
import com.hh.system.util.StaticVar;
import com.hh.system.util.SysParam;

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
				logger.error("菜单初始化失败！！");
				e.printStackTrace();
			}
			
			try {
				SystemServiceInf systemService = BeanFactoryHelper.getBeanFactory()
						.getBean(SystemServiceInf.class);
				if (systemService != null) {
					systemService.initDataList();
				}
			} catch (Exception e) {
				logger.error("数据字典初始化失败！！");
				e.printStackTrace();
			}
		}
	}

	public int initSysParam() {
		List<SysParams> sysParams = sysParamService.queryAllList();
		int size = sysParams.size();
		if (size == 0) {
			SysParams sysParam = new SysParams();
			sysParam.setSysName("信息管理系统");
			sysParam.setSysIcon("/hhcommon/images/extjsico/17460336.png");
			sysParam.setSysImg("/hhcommon/images/big/apple/19.png");
			sysParamService.createEntity(sysParam);
			SysParam.sysParam = sysParam;
		} else {
			SysParam.sysParam = sysParams.get(0);
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
		classPath = classPath.substring(0, classPath.indexOf("/WEB-INF"));
		StaticVar.contextPath =  classPath;
		logger.info("项目绝对路径：" + StaticVar.contextPath);
		classPath = classPath.substring(0, classPath.lastIndexOf("/"));
		StaticVar.webappPath = classPath;
		logger.info("web服务器绝对路径：" + StaticVar.webappPath);
		StaticVar.filepath = StaticVar.webappPath + StaticVar.filebasepath;
		logger.info("附件绝对路径：" + StaticVar.filepath);
		StaticVar.deletefilepath = StaticVar.webappPath + StaticVar.deletefilebasepath;
		logger.info("删除附件绝对路径：" + StaticVar.deletefilepath);
		FileUtil.mkdirs(StaticVar.filepath);
		FileUtil.mkdirs(StaticVar.deletefilepath);
	}
}
