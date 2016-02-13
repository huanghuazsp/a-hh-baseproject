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
	
	function inExcel(){
		Dialog.open({
			url : 'jsp-system-tools-file',
			width : 450,
			height : 270,
			params : {
				saveUrl : 'usersystem-user-importData',
				type : 'user',
				callback : function(data) {
					if(data.returnModel && data.returnModel.msg){
						Dialog.alert(data.returnModel.msg);
					}else{
						doQuery();
					}
				}
			}
		});
	}
	
	function downloadExcel(){
		Request.downloadFile('system-File-downloadFile',{path:'temp/用户数据.xls'});
	}
	function outExcel(){
		Request.downloadFile('usersystem-user-download',{});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype="button" config="onClick:doAdd,text:'添加' , itype :'add' "></span> <span
			xtype="button" config="onClick:doEdit,text:'修改' , itype :'edit' "></span> <span
			xtype="button" config="onClick:doDelete,text:'删除' , itype :'delete' "></span>  <span
			xtype="button"
			config="onClick: $.hh.pagelist.doUp , params:{ pageid :'pagelist',action:'usersystem-user-order'}  ,  icon : 'hh_up' "></span>
		<span xtype="button"
			config="onClick: $.hh.pagelist.doDown , params:{ pageid :'pagelist',action:'usersystem-user-order'} , icon : 'hh_down' "></span>
		<span xtype=menu    config=" id:'menu1', data : [ { img : StaticVar.img_excel , text : '导入' , onClick : inExcel } 
		,{ img : StaticVar.img_excel ,text : '导出' , onClick : outExcel } 
		,{ img : StaticVar.img_excel ,text : '下载模板' , onClick : downloadExcel } 
				  ]"></span>
		<span xtype="button"
					config=" text:'更多',icon : 'ui-icon-triangle-1-s' ,menuId:'menu1' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:700px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text' ,enter: doQuery "></span></td>
			<td xtype="label">性别：</td>
			<td><span xtype="radio"
				config="name: 'nxb'  ,defaultValue : 2 , data :[{id:2,text:'所有'},{id:1,text:'男'},{id:0,text:'女'}]"></span></td>
			<td><span
			xtype="button" config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
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
			name : 'vdlzh' ,
			text : '账号'
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