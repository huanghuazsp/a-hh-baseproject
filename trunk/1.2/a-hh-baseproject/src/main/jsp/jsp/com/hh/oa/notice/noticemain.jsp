<%@page import="com.hh.oa.service.impl.OaNoticeService"%>
<%@page import="com.hh.message.service.EmailService"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>公告通知</title>
<%=SystemUtil.getBaseJs("layout")%>
<% 
	OaNoticeService noticeService = BeanFactoryHelper.getBean( OaNoticeService.class);

	Map<String, Object> map =noticeService.count();
	String id = request.getParameter("id");

%>
<script type="text/javascript">
	var id = '<%=id%>'
	var emailMenu = {
		data : [ {
			text : '发布公告',
			img : '/hhcommon/images/icons/newspaper/newspaper_add.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-notice-NoticeEdit');
			}
		},{
			text : '公告通知',
			rightText:'<font class="hh_red"><%=map.get("wdCount")%></font>/<font class="hh_green"><%=map.get("sjCount")%></font>',
			img : '/hhcommon/images/icons/email/email_close.gif',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-notice-NoticeList?type=ggtz');
			}
		},{
			text : '我发布的',
			img : '/hhcommon/images/icons/newspaper/newspaper_go.png',
			onClick : function() {
				wfbd();
			}
		},{
			text : '所有公告',
			img : '/hhcommon/images/icons/newspaper/newspaper.png',
			onClick : function() {
				$('#iframe').attr('src','jsp-oa-notice-NoticeList?type=sygg');
			}
		}
		]
	};
	
	function wfbd(){
		$('#iframe').attr('src','jsp-oa-notice-NoticeList?type=wfbd');
	}
	
	function updateGG(id){
		$('#iframe').attr('src','jsp-oa-notice-NoticeEdit?id='+id);
	}
	
	
	<%
		String baseurl = "jsp-oa-notice-NoticeList?type=ggtz";
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