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
			var ids = BaseUtil.objsToStr(rows);
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
					var ids = BaseUtil.objsToStr(rows);
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
				id:row.id
			});
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
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-queryDeletePage' ,column : [
		{
			name : 'title' ,
			text : '标题'
		},{
			name : 'dcreate' ,
			text : '时间',
			render:'datetime'
		}
	]">
	</div>
</body>
</html>