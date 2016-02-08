<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>在线用户</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		$.hh.pagelist.deleteData({
			pageid : 'pagelist',
			action : 'usersystem-user-deleteByIds'
		});
	}
	function doAdd() {
		Dialog.open({
			url : 'jsp-usersystem-user-useredit',
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
				url : 'jsp-usersystem-user-useredit',
				params : {
					row : row,
					callback : function() {
						$("#pagelist").loadData();
					}
				}
			});
		});
	}
	function rendersex(value) {
		return (value == 1 ? '男' : '女')
		+ '<img src="/hhcommon/images/myimage/sex/'
		+ (value == 1 ? 'nan' : 'nv') + '.png" />';
	}
	function renderzt(value) {
		return value == 0 ? '正常' : '<font color=red>冻结</font>';
	}
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span> <span
			xtype="button" config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span> <span
			xtype="button" config="onClick: doQuery ,text:'查询' , itype :'query' "></span> <span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'usersystem-user-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'usersystem-user-order'} , icon : 'hh_down' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td xtype="label">性别：</td>
			<td><span xtype="radio"
				config="name: 'nxb'  ,defaultValue : 2 , data :[{id:2,text:'所有'},{id:1,text:'男'},{id:0,text:'女'}]"></span></td>
		</tr>
	</table>
	<div id="pagelist" xtype="pagelist"
		config=" params :  {nxb:2},url: 'usersystem-user-queryPagingData' ,column : [
		{
			name : 'nxb' ,
			text : '性别',
			render:'rendersex'
		},{
			name : 'state' ,
			text : '状态',
			render:'renderzt'
		},{
			name : 'text' ,
			text : '用户名称'
		},{
			name : 'vdzyj' ,
			text : '电子邮件'
		},{
			name : 'vdh' ,
			text : '联系电话'
		},{
			name : 'dsr' ,
			text : '生日',
			render:'date'
		}
	]">
	</div>
</body>
</html>