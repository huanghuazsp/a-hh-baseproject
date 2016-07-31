<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>消息管理</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	var params = {
			toObjectId : '<%=request.getParameter("toObjectId")%>',
			sendObjectType : '<%=request.getParameter("sendObjectType")%>'
	}
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
		config=" params : params ,url: 'message-SysMessage-queryPagingData' ,column : [
		{
			name : 'content' ,
			text : '内容',
			align:'left'
		},{
			name : 'dcreate' ,
			text : '时间',
			render:'datetime',
			width: 120
		},{
			name : 'sendUserName' ,
			text : '发送人',
			width: 80
		},{
			name : 'toObjectName' ,
			text : '接收对象',
			width: 80
		}
	]">
	</div>
</body>
</html>