<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=SystemUtil.getBaseJs()%>

<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'oa-MeetingApply-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-oa-meetingapply-MeetingApplyEdit',
			params : {
				callback : function() {
					$("#pagelist").loadData();
				}
			}
		});
	}
	function doEdit() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-oa-meetingapply-MeetingApplyEdit',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
						$("#pagelist").loadData();
					}
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
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span>
		<span xtype="button"
			config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>
		<!--  <span
			xtype="button" config="onClick: doQuery ,text:'查询' , itype :'query' "></span> --> <span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'oa-MeetingApply-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'oa-MeetingApply-order'} , icon : 'hh_down' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config=" url: 'oa-MeetingApplyUser-queryPagingData' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '会议主题'
			},
		
			{
				name : 'attendUserText' ,
				text : '参与人员'
			},
		
		
			{
				name : 'attendOrgText' ,
				text : '参与部门'
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