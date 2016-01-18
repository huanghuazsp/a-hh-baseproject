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
			img : '/hhcommon/images/icons/email/email_edit.png',
			onClick : function() {
				paramsData.writeObject=null;
				$('#email').attr('src','jsp-message-email-writeemail');
			}
		},{
			text : '收件',
			img : '/hhcommon/images/icons/email/email_close.gif',
			onClick : function() {
				shoujianlist();
			}
		},{
			text : '已发送',
			img : '/hhcommon/images/icons/email/email_go.png',
			onClick : function() {
				sendemaillist();
			}
		},{
			text : '草稿箱',
			img : '/hhcommon/images/icons/email/email_link.png',
			onClick : function() {
				cgxemaillist();
			}
		},{
			text : '已删除',
			img : '/hhcommon/images/icons/email/email_delete.png',
			onClick : function() {
				deleteemaillist();
			}
		}
		]
	};
	
	function shoujianlist(){
		$('#email').attr('src','jsp-message-email-shouemaillist');
	}
	
	function sendemaillist(){
		$('#email').attr('src','jsp-message-email-sendemaillist');
	}
	
	function cgxemaillist(){
		$('#email').attr('src','jsp-message-email-cgxemaillist');
	}
	
	function deleteemaillist(){
		$('#email').attr('src','jsp-message-email-deleteemaillist');
	}
	
	function viewemail(params){
		$('#email').attr('src','jsp-message-email-shouemail?'+$.param(params));
	}
	
	
	var paramsData = {
			
	};
	
	function writeemail(params){
		paramsData.writeObject = params;
		$('#email').attr('src','jsp-message-email-writeemail');
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:140">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="email" name="email" width=100%
				height=100% frameborder=0 src="jsp-message-email-shouemaillist"></iframe>
		</div>
	</div>
</body>
</html>