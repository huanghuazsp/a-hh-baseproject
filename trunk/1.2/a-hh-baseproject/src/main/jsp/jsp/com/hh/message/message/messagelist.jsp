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
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-SysMessage-deleteByIds'
		});
	}
	function doQuery() {
		var paramsObj =  $('#queryForm').getValue();
		$.extend(paramsObj,params);
		$('#pagelist').loadData({
			params : paramsObj
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		 <span
			xtype="button" config="onClick:doDelete,text:'删除'"></span>
	</div> 
	<table xtype="form" id="queryForm" style="width: 700px;">
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text"
						config=" name : 'content' ,enter: doQuery "></span></td>
					<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
				</tr>
	</table>
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