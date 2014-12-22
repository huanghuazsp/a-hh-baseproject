<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>在线用户</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function doDelete() {
		PageUtil.deleteData({
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
		PageUtil.callRow("pagelist", function(row) {
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
				+ '<img src="/hhcommon/opensource/ext/shared/icons/fam/'
				+ (value == 1 ? 'user' : 'user_female') + '.gif" />';
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
		<span xtype="button" config="onClick:doAdd,text:'添加' , color:'add' "></span> <span
			xtype="button" config="onClick:doEdit,text:'修改' , color:'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , color:'delete' "></span> <span
			xtype="button" config="onClick: doQuery ,text:'查询' , color:'query' "></span> <span
			xtype="button"
			config="onClick: PageUtil.doUp , params:{ pageid :'pagelist',action:'usersystem-user-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: PageUtil.doDown , params:{ pageid :'pagelist',action:'usersystem-user-order'} , icon : 'hh_down' "></span>
	</div>
	<table xtype="form" id="queryForm">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td xtype="label">性别：</td>
			<td><span xtype="radio"
				config="name: 'nxb'  , value : 2 , data :[{id:2,text:'所有'},{id:1,text:'男'},{id:0,text:'女'}]"></span></td>
		</tr>
	</table>
	<div id="pagelist" xtype="pagelist"
		config=" params :  {nxb:2},url: 'usersystem-user-queryPagingData' ,column : [
		{
			name : 'nxb' ,
			text : '性别',
			render:'rendersex'
		},{
			name : 'nzt' ,
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
			text : '电话'
		},{
			name : 'dsr' ,
			text : '生日',
			render:'date'
		}
	]">
	</div>
</body>
</html>