<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>错误追踪</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'system-Error-deleteByIds'
		});
	}

	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				width : $.hh.browser.getWidth() * 0.9,
				height : $.hh.browser.getHeight() * 0.85,
				url : 'jsp-system-error-errorview',
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
		config=" url: 'system-Error-queryPagingData' ,column : [
		{
			name : 'message' ,
			text : '异常内容',
			valueType : 'text'
		},{
			name : 'name' ,
			text : '异常类名',
			width: 150
		},{
			name : 'dcreate' ,
			text : '异常生成时间',
			render:'datetime',
			width: 150
		},{
			name : 'createUserName' ,
			text : '操作人',
			width: 150
		}
	]">
	</div>
</body>
</html>