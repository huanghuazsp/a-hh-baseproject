<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>系统工具</title>
<%=SystemUtil.getBaseJs("layout")%>
<script type="text/javascript">
	var emailMenu = {
		data : [ {
			text : '系统工具',
			img : '/hhcommon/images/extjsico/application_16x16.gif',
			url : 'jsp-system-tools-systemtools',
			onClick : onClick
		},{
			text : '取色工具',
			img : '/hhcommon/images/icons/world/world.png',
			url : 'jsp-system-tools-color',
			onClick : onClick
		},{
			text : '系统参数',
			img : '/hhcommon/images/extjsico/bogus.png',
			url : 'jsp-system-sysparam-paramedit',
			onClick : onClick
		},{
			text : '在线用户',
			img : '/hhcommon/images/icons/user/user.png',
			url : 'jsp-usersystem-user-onlineuser',
			onClick : onClick
		},{
			text : '缓存管理',
			img : '/hhcommon/images/extjsico/bogus.png',
			url : 'jsp-system-tools-cachelist',
			onClick : onClick
		},{
			text : '操作日志',
			img : '/hhcommon/images/icons/world/world.png',
			url : 'jsp-system-operlog-OperLogList',
			onClick : onClick
		},{
			text : 'SQL监控',
			img : '/hhcommon/images/icons/script/script.png',
			url : 'jsp-system-sql-sqllist',
			onClick : onClick
		},{
			text : '错误追踪',
			img : '/hhcommon/images/icons/bug/bug.png',
			url : 'jsp-system-error-errorlist',
			onClick : onClick
		}
		]
	};
	
	function onClick(){
		$('#system').attr('src',this.url);
	}
	
	
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:140">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="system" name="system" width=100%
				height=100% frameborder=0 src="jsp-system-tools-systemtools"></iframe>
		</div>
	</div>
</body>
</html>