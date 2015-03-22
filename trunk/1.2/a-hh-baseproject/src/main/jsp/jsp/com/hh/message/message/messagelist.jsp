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
	
	function renderRead(value){
		return value == 1 ? '<font class=hh_green >是</font>'
				: '<font class=hh_red>否</font>';
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加'"></span> <span
			xtype="button" config="onClick:doEdit,text:'修改'"></span> <span
			xtype="button" config="onClick:doDelete,text:'删除'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-SysMessage-queryPagingData' ,column : [
		{
			name : 'title' ,
			text : '标题'
		},{
			name : 'isRead' ,
			text : '已读',
			width:40,
			render : renderRead
		},{
			name : 'createUserName' ,
			text : '发送人'
		},{
			name : 'shouUserName' ,
			text : '接收人'
		},{
			name : 'path' ,
			text : '地址',
			contentwidth: '150'
		},{
			name : 'jsCode' ,
			text : 'js代码',
			contentwidth: '150'
		},{
			name : 'params' ,
			text : '参数',
			contentwidth: '150'
		}
	]">
	</div>
</body>
</html>