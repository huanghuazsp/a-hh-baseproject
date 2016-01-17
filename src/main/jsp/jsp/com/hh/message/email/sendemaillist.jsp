<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>发邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	
	function doAdd(){
		Dialog.open({
			url : 'jsp-message-email-writeemail',
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
				url : 'jsp-message-email-writeemail',
				params : {
					row : row,
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-Email-deleteByIds'
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doDelete,text:'删除'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-querySendPage' ,column : [
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