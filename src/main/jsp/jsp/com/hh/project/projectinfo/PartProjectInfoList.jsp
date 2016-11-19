<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs()+SystemUtil.getUser()%>

<script type="text/javascript">
	function doQuery() {
		$('#pagelist').loadData({
			params : $('#queryForm').getValue()
		});
	}
	
	function renderMoney(value){
		return value + '万';
	}
	
	function setUserInfo(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectuserinfo-ProjectUserInfoList',
				urlParams : {
					projectId : row.id,
					oper : loginUser.id==row.vcreate ||  loginUser.id==row.manager ? 'all':''
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function setModular(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectmodular-ProjectModularList',
				urlParams : {
					projectId : row.id,
					oper : loginUser.id==row.vcreate ||  loginUser.id==row.manager ? 'all':''
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function setFile(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectfile-ProjectFileList',
				urlParams : {
					projectId : row.id,
					oper : loginUser.id==row.vcreate ||  loginUser.id==row.manager ? 'all':''
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
	
	function doView(){
		$.hh.pagelist.callRow("pagelist", function(row) {
			Dialog.open({
				url : 'jsp-project-projectinfo-ProjectInfoView',
				urlParams : {
					id : row.id
				},
				params : {
					callback : function() {
					}
				}
			});
		});
	}
</script>
</head>
<body>
	<div xtype="toolbar" config="type:'head'">
		<span xtype=menu    config=" id:'menu1', data : [ 
		{ text : '编辑参与者' , onClick : setUserInfo } ,
		{ text : '编辑模块' , onClick : setModular },
		{ text : '编辑附件' , onClick : setFile } ]"></span>
		<span xtype="button" config=" text:'项目信息添加/修改',icon : 'ui-icon-triangle-1-s' ,menuId:'menu1' "></span>
		
		<span xtype="button" config="onClick:doView,text:'查看' , itype :'view' "></span>
	</div>
	<table xtype="form" id="queryForm" style="width:600px;">
		<tr>
			<td xtype="label">名称：</td>
			<td><span xtype="text" config=" name : 'text'"></span></td>
			<td><span xtype="button"
						config="onClick: doQuery ,text:'查询' , itype :'query' "></span></td>
		</tr>
	</table> 
	<div id="pagelist" xtype="pagelist"
		config=" url: 'project-ProjectInfo-queryPartPage' ,column : [
		
		
		
			{
				name : 'text' ,
				text : '项目名称'
			},
		
		
			{
				name : 'startDate' ,
				text : '开始日期',
				render : 'date',
				width:80
			},
		
			{
				name : 'managerText' ,
				text : '项目经理'
			},
		
			{
				name : 'client' ,
				text : '客户'
			},
		
			{
				name : 'money' ,
				text : '项目金额',
				width:100,
				render : renderMoney
			}
		
	]">
	</div>
</body>
</html>