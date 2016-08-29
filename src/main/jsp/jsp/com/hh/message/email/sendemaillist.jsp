<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>发邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.viewemail({
				id:row.id,
				type:'sendemaillist'
			});
		});
	}
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-Email-deleteMeByIds'
		});
	}
	function doQuery() {
		var params = $('#queryForm').getValue();
		$('#pagelist').loadData({
			params : params
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick: doView ,text:'查看'"></span>
		<span xtype="button" config="onClick:doDelete,text:'测底删除'"></span>
	</div>
	<table xtype="form" id="queryForm" style="width: 700px;">
		<tr>
			<td xtype="label">标题：</td>
			<td><span xtype="text"
				config=" name : 'title' ,enter: doQuery "></span></td>
				<td><span xtype="button"
				config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-querySendPage' ,column : [
		{
			name : 'userNames' ,
			text : '收件人',
			width:100
		},{
			name : 'dcreate' ,
			text : '时间',
			render:'datetime',
			width:120
		},{
			name : 'title' ,
			align : 'left',
			text : '标题',
			align : 'left'
		}
	]">
	</div>
</body>
</html>