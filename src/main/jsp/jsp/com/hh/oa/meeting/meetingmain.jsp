<%@page import="com.hh.oa.service.impl.OaNoticeService"%>
<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>会议管理</title>
<%=SystemUtil.getBaseJs("layout")%>
<script type="text/javascript">
	var emailMenu = {
		data : [ {
			text : '会议申请',
			img : '/hhcommon/images/icons/telephone/telephone-plus.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-meetingapply-main');
			}
		},{
			text : '我参与的',
			img : '/hhcommon/images/icons/telephone/telephone-off.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-meetingapply-PartMeetingApplyList');
			}
		},{
			text : '我申请的',
			img : '/hhcommon/images/icons/telephone/telephone-arrow.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-meetingapply-MeetingApplyList');
			}
		},{
			text : '会议室管理',
			img : '/hhcommon/images/icons/telephone/telephone.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-meeting-MeetingList');
			}
		}
		]
	};
	
	<%
		String baseurl = "jsp-oa-meeting-MeetingList";
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