<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>sql监控</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'system-Sql-deleteByIds'
		});
	}

	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				width : $.hh.browser.getWidth() * 0.9,
				height : $.hh.browser.getHeight() * 0.85,
				url : 'jsp-system-sql-sqlview',
				params : row
			});
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doDelete,text:'删除'"></span> <span
			xtype="button" config="onClick:doView,text:'查看'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'system-Sql-queryPagingData' ,column : [
		{
			name : 'sql' ,
			text : 'sql',
			width: 200
		},{
			name : 'elapsedTime' ,
			text : '耗时（毫秒）'
		},{
			name : 'createTime' ,
			text : '生成时间',
			render:'datetime'
		},{
			name : 'createUserName' ,
			text : '操作人'
		}
	]">
	</div>
</body>
</html>