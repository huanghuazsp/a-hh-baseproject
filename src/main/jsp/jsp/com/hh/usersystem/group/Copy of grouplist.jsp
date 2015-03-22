<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>用户组管理</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doAdd() {
		Dialog.open({
			url : 'jsp-usersystem-group-groupedit',
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
				url : 'jsp-usersystem-group-groupedit',
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
			action : 'usersystem-Group-deleteByIds'
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加'"></span> <span
			xtype="button" config="onClick:doEdit,text:'修改'"></span> <span
			xtype="button" config="onClick:doDelete,text:'删除'"></span> <span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'usersystem-Group-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'usersystem-Group-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
	</div>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'usersystem-Group-queryPagingData' ,column : [
		{
			name : 'text' ,
			text : '名称'
		},{
			name : 'remark' ,
			text : '备注',
			contentwidth: '250'
		}
	]">
	</div>
</body>
</html>