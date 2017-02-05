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
			action : 'project-Approval-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-project-approval-ApprovalEdit',
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
				url : 'jsp-project-approval-ApprovalEdit',
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
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'project-Approval-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'project-Approval-order'} , icon : 'hh_down' "></span>
	</div>
	<!-- <table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">test：</td>
			<td><span xtype="text" config=" name : 'test'"></span></td>
		</tr>
	</table> -->
	<div id="pagelist" xtype="pagelist"
		config=" url: 'project-Approval-queryPagingData' ,column : [
		
		
		
			{
				name : 'applyUser' ,
				text : '申请人'
			},
		
			{
				name : 'applyUserText' ,
				text : '申请人名称'
			},
		
			{
				name : 'applyDate' ,
				text : '申请时间'
			},
		
			{
				name : 'approvalDate' ,
				text : '立项时间'
			},
		
			{
				name : 'applyComment' ,
				text : '申请内容'
			},
		
			{
				name : 'deptManager' ,
				text : '部门经理'
			},
		
			{
				name : 'deptManagerText' ,
				text : '部门经理'
			},
		
			{
				name : 'deptManagerComment' ,
				text : '部门经理意见'
			},
		
			{
				name : 'branchDeputyManager' ,
				text : '分管副总'
			},
		
			{
				name : 'branchDeputyManagerText' ,
				text : '分管副总'
			},
		
			{
				name : 'branchDeputyManagerComment' ,
				text : '分管副总意见'
			},
		
			{
				name : 'overallManager' ,
				text : '总经理'
			},
		
			{
				name : 'overallManagerText' ,
				text : '总经理'
			},
		
			{
				name : 'overallManagerComment' ,
				text : '总经理意见'
			},
		
			{
				name : 'projectId' ,
				text : '项目ID'
			}
		
	]">
	</div>
</body>
</html>