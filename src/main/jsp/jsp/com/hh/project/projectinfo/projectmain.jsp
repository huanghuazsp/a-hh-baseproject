<%@page import="com.hh.oa.service.impl.OaNoticeService"%>
<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>项目管理</title>
<%=SystemUtil.getBaseJs("layout")%>
<% 

%>
<script type="text/javascript">
	var emailMenu = {
		data : [{
			text : '创建项目',
			img : '/hhcommon/images/icons/project/project-plus.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-project-projectinfo-ProjectInfoEdit');
			}
		}, {
			text : '我相关的',
			img : '/hhcommon/images/icons/project/project-horizontal.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-project-projectinfo-PartProjectInfoList');
			}
		}, {
			text : '我的项目',
			img : '/hhcommon/images/icons/project/project.png',
			onClick : function() {
				wdxm();
			}
		}
		]
	};
	
	function wdxm(){
		$('#iframe').attr('src','jsp-project-projectinfo-ProjectInfoList');
	}
	
	<%
		String baseurl = "jsp-project-projectinfo-PartProjectInfoList";
	%>
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:165">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="iframe" name="iframe" width=100%
				height=100% frameborder=0 src="<%=baseurl%>"></iframe>
		</div>
	</div>
</body>
</html>