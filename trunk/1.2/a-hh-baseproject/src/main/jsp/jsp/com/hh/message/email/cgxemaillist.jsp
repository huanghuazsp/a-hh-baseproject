<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>发邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-Email-deleteByIds'
		});
	}
	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.viewemail({
				id:row.id,
				type:'cgxemaillist'
			});
		});
	}
	
	function doEdit(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.writeemail({id:row.id});
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick: doView ,text:'查看'"></span>
		<span xtype="button" config="onClick: doEdit ,text:'编辑'"></span>
		<span xtype="button" config="onClick:doDelete,text:'删除'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-queryCGXPage' ,column : [
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
			name : 'dcreate' ,
			text : '时间',
			render:'datetime',
			width:120
		}
	]">
	</div>
</body>
</html>