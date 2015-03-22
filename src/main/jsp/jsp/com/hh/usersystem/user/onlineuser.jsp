<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>在线用户</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.callRows("pagelist", function(rows) {
			Dialog.confirm({
				message : '您确认要强制选中的用户下线吗？',
				yes : function(result) {
					var ids = BaseUtil.objsToStr(rows);
					Request.request('usersystem-user-deleteOnLineByIds', {
						data : {
							ids : ids
						}
					}, function(result) {
						if (result.success) {
							$("#pagelist").loadData();
						}
					});
				}
			});
		});
	}

	function rendersex(value) {
		return (value == 1 ? '男' : '女')
				+ '<img src="/hhcommon/opensource/ext/shared/icons/fam/'
				+ (value == 1 ? 'user' : 'user_female') + '.gif" />';
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doDelete,text:'强制下线'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'usersystem-user-queryOnLinePagingData' ,column : [
		{
			name : 'nxb' ,
			text : '性别',
			render:'rendersex'
		},{
			name : 'text' ,
			text : '用户名称'
		},{
			name : 'vdzyj' ,
			text : '电子邮件'
		},{
			name : 'vdh' ,
			text : '电话'
		}
	]">
	</div>
</body>
</html>