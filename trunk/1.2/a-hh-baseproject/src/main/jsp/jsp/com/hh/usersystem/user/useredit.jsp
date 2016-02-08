<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform", "date")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 600;
	var height = 500;

	var objectid = params.row ? params.row.id : '';
	var systemmanagerhide=params.systemmanagerhide;
	if(objectid==''){
		objectid = params.objectId||'';
	}
	
	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('usersystem-user-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback(formData);
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('usersystem-user-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
					$('#span_deptId').setConfig({params:{node:result.orgId}});
					$('#span_jobId').setConfig({params:{node:result.deptId}});
				}
			});
		}
	}

	function init() {
		if(systemmanagerhide==true){
			$('[trtype=systemmanager]').hide();
		}
		findData();
	}
	
	function orgChange(data){
		$('#span_deptId').setConfig({params:{node:data.id}});
		$('#span_deptId').setValue('');
		
		$('#span_jobId').setConfig({params:{node:''}});
		$('#span_jobId').setValue('');
	}
	
	function deptChange(data){
		$('#span_jobId').setConfig({params:{node:data.id}});
		$('#span_jobId').setValue('');
	}
	
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span> <span
				xtype="text" config=" hidden:true,name : 'vmm'"></span>
			<span
				xtype="text" config=" hidden:true,name : 'theme'"></span>
			<span xtype="text" config=" hidden:true,name : 'desktopType'"></span>
			<table xtype="form">
				<tr>
					<td xtype="label">用户名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
					<td colspan="2" rowspan="6"><span xtype="uploadpic"
						config="name: 'headpic' , type : 'headpic'  ,path:'/hhcommon/images/big/qq' "></span></td>
				</tr>
				<tr>
					<td xtype="label">登录帐号：</td>
					<td><span xtype="text"
						config="name: 'vdlzh' ,required :true ,maxSize:16"></span></td>
				</tr>
				<tr>
					<td xtype="label">性别：</td>
					<td><span xtype="radio"
						config="name: 'nxb' ,defaultValue : 1, data :[{id:1,text:'男'},{id:0,text:'女'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">联系电话：</td>
					<td><span xtype="text" config="name: 'vdh' ,maxSize:20 "></td>
				</tr>
				<tr>
					<td xtype="label">电子邮件：</td>
					<td><span xtype="text" config="name: 'vdzyj'  ,email:true"></span>
					</td>

				</tr>
				<tr>
					<td xtype="label">生日：</td>
					<td><span xtype="date" config="name: 'dsr'  "></span></td>
				</tr>
				<tr trtype="systemmanager" >
					<td xtype="label">角色：</td>
					<td colspan="3"><span xtype="selectPageList"
						config="name: 'jsIdsStr'  , url:'usersystem-role-queryPagingData' ,tableName:'US_ROLE' "></td>
				</tr>
				<tr trtype="systemmanager" >
					<td xtype="label">机构：</td>
					<td colspan="3"><span xtype="selectOrg"  config="name: 'orgId' ,selectType:'org' ,onChange: orgChange  "></td>
				</tr>
				<tr trtype="systemmanager" >
					<td xtype="label">部门：</td>
					<td colspan="3"><span xtype="selectOrg"  config="name: 'deptId' ,selectType:'dept'  ,onChange : deptChange "></td>
				</tr>
				<tr trtype="systemmanager" >
					<td xtype="label">岗位：</td>
					<td colspan="3"><span xtype="selectOrg"  config="name: 'jobId' ,selectType:'job'   "></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>