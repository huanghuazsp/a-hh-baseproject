<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>个人邮件</title>
<%=SystemUtil.getBaseJs("layout")%>
<script type="text/javascript">
	var emailMenu = {
		data : [ {
			text : '写信',
			img : '/hhcommon/images/icons/email/email_go.png',
			onClick : function() {
			}
		},{
			text : '收件',
			img : '/hhcommon/images/icons/email/email_close.gif',
			onClick : function() {
			}
		} ]
	};
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:140">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="email" name="email" width=100%
				height=100% frameborder=0 src="jsp-message-emain-shouemail"></iframe>
		</div>
	</div>
</body>
</html>