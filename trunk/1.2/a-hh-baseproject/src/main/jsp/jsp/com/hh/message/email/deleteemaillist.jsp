<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>发邮件列表</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function typerender(value){
		return value == 'yfs' ? '<font class=hh_red >已发送</font>'
				: '<font class=hh_green>未发送</font>';
	}
	
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
			action : 'message-SendEmail-deleteByIds'
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
	 <span
			xtype="button" config="onClick:doEdit,text:'恢复'"></span> <span
			xtype="button" config="onClick:doDelete,text:'彻底删除'"></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-Email-queryDeletePage' ,column : [
		{
			name : 'type' ,
			text : '状态',
			width : 60 ,
			render : 'typerender'
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