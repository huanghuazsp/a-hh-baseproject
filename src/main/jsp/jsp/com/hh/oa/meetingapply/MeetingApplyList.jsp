<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()%>

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
		config=" url: 'oa-MeetingApply-queryPagingData' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '会议主题'
			},
		
			{
				name : 'AttendUser' ,
				text : '出席人员'
			},
		
			{
				name : 'AttendUserText' ,
				text : '出席人员名称'
			},
		
			{
				name : 'AttendOrg' ,
				text : '出席部门'
			},
		
			{
				name : 'AttendOrgText' ,
				text : '出席部门名称'
			},
		
			{
				name : 'startDate' ,
				text : '开始时间'
			},
		
			{
				name : 'endDate' ,
				text : '结束时间'
			},
		
			{
				name : 'describe' ,
				text : '描述'
			},
		
			{
				name : 'meetingId' ,
				text : '会议id'
			},
		
			{
				name : 'meetingIdText' ,
				text : '会议名称'
			}
		
	]">
	</div>
</body>
</html>