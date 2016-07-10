<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>个人邮件</title>
<%=SystemUtil.getBaseJs("layout")%>
<% 
	EmailService emailService = BeanFactoryHelper.getBean( EmailService.class);

	Map<String, Object> map =emailService.count();
	

%>
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
			rightText:'<font class="hh_red"><%=map.get("wdCount")%></font>/<font class="hh_green"><%=map.get("sjCount")%></font>',
			img : '/hhcommon/images/icons/email/email_close.gif',
			onClick : function() {
				shoujianlist();
			}
		},{
			text : '已发送',
			rightText:'<font class="hh_green"><%=map.get("fsCount")%></font>',
			img : '/hhcommon/images/icons/email/email_go.png',
			onClick : function() {
				sendemaillist();
			}
		},{
			text : '草稿箱',
			rightText:'<font class="hh_green"><%=map.get("cgxCount")%></font>',
			img : '/hhcommon/images/icons/email/email_link.png',
			onClick : function() {
				cgxemaillist();
			}
		},{
			text : '已删除',
			rightText:'<font class="hh_green"><%=map.get("scCount")%></font>',
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
	
	var params = $.hh.getIframeParams();
	var paramsData = {
			
	};
	if(params.userids && params.usernames ){
		paramsData.writeObject = {
				users :{
					id:params.userids,
					text:params.usernames
				}
		};
	}
	
	function writeemail(params){
		if(params && params.id){
			paramsData.writeObject = null;
			$('#email').attr('src','jsp-message-email-writeemail?id='+params.id);
		}else{
			paramsData.writeObject = params;
			$('#email').attr('src','jsp-message-email-writeemail');
		}
	}
	
	<%
		String baseurl = "jsp-message-email-shouemaillist";
		if("write".equals(request.getParameter("type"))){
			baseurl = "jsp-message-email-writeemail";
		}
	%>
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:165">
			<span xtype=menu  configVar="emailMenu"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="email" name="email" width=100%
				height=100% frameborder=0 src="<%=baseurl%>"></iframe>
		</div>
	</div>
</body>
</html>