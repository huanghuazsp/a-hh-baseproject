<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
				Dialog.open({
					url : 'jsp-oa-meetingapply-MeetingApplyView',
					urlParams : {
						id : row.id
					}
				});
		});
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	function openMeeting(params) {
		Dialog.open({
			url : 'jsp-oa-meeting-MeetingView' ,
			urlParams : {
				id : params
			}
		});
	}
	
	function renderMeeting(value,data){
		return '<a href="javascript:openMeeting(\''+data.meetingId+'\')">'+value+'</a>';
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button"
			config="onClick: doView ,text:'查看' , itype :'view' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config=" url: 'oa-MeetingApplyUser-queryShouPage' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '会议主题'
			},
		
			{
				name : 'attendUserText' ,
				text : '出席人员'
			},
		
		
			{
				name : 'attendOrgText' ,
				text : '出席部门'
			},
		
			{
				name : 'start' ,
				text : '开始时间',
				render:'datetime',
				width:120
			},
		
			{
				name : 'end' ,
				text : '结束时间',
				render:'datetime',
				width:120
			},
		
			{
				name : 'describe' ,
				text : '描述'
			},
		
			{
				name : 'meetingIdText' ,
				text : '会议名称',
				render : renderMeeting
			}
		
	]">
	</div>
</body>
</html>