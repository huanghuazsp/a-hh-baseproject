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
	var height = 450;

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
					if (result.success) {
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
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span> <span
				xtype="text" config=" hidden:true,name : 'vmm'"></span>
			<table xtype="form">
				<tr>
					<td xtype="label">用户名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
					<td colspan="2" rowspan="6"><span xtype="uploadpic"
						config="name: 'headpic' , filePath : 'hh_xt_yh/headpic'  ,path:'/hhcommon/images/big/qq' "></span></td>
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
					<td xtype="label">电话：</td>
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
						config="name: 'jsIdsStr'  , url:'usersystem-role-queryPagingData' ,tableName:'HH_XT_JS' "></td>
				</tr>
				<tr trtype="systemmanager" >
					<td xtype="label">岗位：</td>
					<td colspan="3"><span xtype="selectOrg"
						config="name: 'orgIdsStr'  , many : true , params_selectType : 'job' "></td>
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