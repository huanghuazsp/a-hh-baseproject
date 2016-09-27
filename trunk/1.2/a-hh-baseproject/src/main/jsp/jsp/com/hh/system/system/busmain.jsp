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
			text : '节假日管理',
			img : '/hhcommon/images/extjsico/17460310.png',
			url : 'jsp-system-tools-calendar?edit=1',
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
				height=100% frameborder=0 src="jsp-system-tools-calendar?edit=1"></iframe>
		</div>
	</div>
</body>
</html>