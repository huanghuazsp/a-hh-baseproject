<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>网盘</title>
<%=SystemUtil.getBaseJs("layout")%>
<script type="text/javascript">
	var emailMenu = {
		data : [ {
			text : '文件夹管理',
			img : '/hhcommon/images/icons/folder/folder.png',
			url : 'jsp-system-resourcestype-ResourcesTypeList',
			onClick : onClick
		},{
			text : '文件管理',
			img : '/hhcommon/images/icons/folder/folder_page.png',
			url : 'jsp-system-resources-main',
			onClick : onClick
		},{
			text : '共享文件',
			img : '/hhcommon/images/icons/folder/folder_magnify.png',
			url : 'jsp-system-resources-sharemain',
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
				height=100% frameborder=0 src="jsp-system-resources-main"></iframe>
		</div>
	</div>
</body>
</html>