<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>消息管理</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doAdd() {
		Dialog.open({
			url : 'jsp-message-message-messageedit',
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
				url : 'jsp-message-message-messageedit',
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
			action : 'message-SysMessage-deleteByIds'
		});
	}
	
</script>
</head>
<body>
	<!-- <div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加'"></span> <span
			xtype="button" config="onClick:doEdit,text:'修改'"></span> <span
			xtype="button" config="onClick:doDelete,text:'删除'"></span>
	</div> -->
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-SysMessage-queryPagingData' ,column : [
		{
			name : 'title' ,
			text : '标题'
		},{
			name : 'content' ,
			text : '内容'
		},{
			name : 'type' ,
			text : '类型'
		},{
			name : 'dcreate' ,
			text : '时间',
			render:'datetime'
		},{
			name : 'sendUserName' ,
			text : '发送人'
		},{
			name : 'userNames' ,
			text : '接收人'
		}
	]">
	</div>
</body>
</html>