<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>收邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function readrender(value) {
		return value == 0 ? '<img src="'+$.hh.property.img_email_close+'" />'
				: '<img src="'+$.hh.property.img_email_open+'" />';
	}

	function doView() {
		$.hh.pagelist.callRow("pagelist", function(row) {
			parent.viewemail({
				id:row.id,
				type:'shouemaillist'
			});
		});
	}

	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'message-Email-deleteByIds'
		});
	}
	function doQuery() {
		var params = $('#queryForm').getValue();
		$('#pagelist').loadData({
			params : params
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick: doView ,text:'查看'"></span> <span
			xtype="button" config="onClick: doDelete ,text:'删除'"></span>
	</div>
	<table xtype="form" id="queryForm" style="width: 700px;">
		<tr>
			<td xtype="label">标题：</td>
			<td><span xtype="text"
				config=" name : 'title' ,enter: doQuery "></span></td>
				<td><span xtype="button"
				config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-queryShouPage' ,column : [
		{
			name : 'read' ,
			text : '状态',
			width :30,
			render : 'readrender'
		},{
			name : 'sendUserName' ,
			text : '发件人',
			width:100
		},{
			name : 'createTime' ,
			text : '时间',
			render:'datetime',
			width:120
		},{
			name : 'title' ,
			text : '标题',
			align : 'left'
		}
	]">
	</div>
</body>
</html>