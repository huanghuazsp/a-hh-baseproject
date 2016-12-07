<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>发邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function recovery() {
		var data = {};
		$.hh.pagelist.callRows('pagelist', function(rows) {
			var ids = $.hh.objsToStr(rows);
			data.ids = ids;
			Request.request('message-Email-recovery', {
				data : data
			}, function(result) {
				if (result.success != false) {
					$("#pagelist").loadData();
				}
			});
		});
	}
	function doDelete() {
		var data = {};
		$.hh.pagelist.callRows('pagelist', function(rows) {
			Dialog.confirm({
				message : '您确认要删除数据吗？',
				yes : function(result) {
					var ids = $.hh.objsToStr(rows);
					data.ids = ids;
					Request.request('message-Email-thoroughDelete', {
						data : data
					}, function(result) {
						if (result.success != false) {
							$("#pagelist").loadData();
						}
					});
				}
			});
		});
	}
	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.viewemail({
				id:row.id,
				type:'deleteemaillist'
			});
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
		<span xtype="button" config="onClick:recovery,text:'恢复'"></span> <span
			xtype="button" config="onClick:doDelete,text:'彻底删除'"></span>
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
		config=" url: 'message-Email-queryDeletePage' ,column : [
		{
			name : 'sendUserName' ,
			text : '发件人',
			width:100
		},{
			name : 'userNames' ,
			text : '收件人',
			width:100
		},{
			name : 'title' ,
			text : '标题',
			align : 'left'
		},{
			name : 'createTime' ,
			text : '时间',
			render:'datetime',
			width:120
		}
	]">
	</div>
</body>
</html>