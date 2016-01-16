<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>收邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function readrender(value) {
		return value == 0 ? '<img src="'+StaticVar.img_email_close+'" />'
				: '<img src="'+StaticVar.img_email_open+'" />';
	}

	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.viewemail({
				id:row.id
			});
		});
	}

	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-ShouEmail-deleteByIds'
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick: doView ,text:'查看'"></span> <span
			xtype="button" config="onClick: doDelete ,text:'删除'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-ShouEmail-queryPagingData' ,column : [
		{
			name : 'read' ,
			text : '状态',
			contentwidth:30,
			render : 'readrender'
		},{
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