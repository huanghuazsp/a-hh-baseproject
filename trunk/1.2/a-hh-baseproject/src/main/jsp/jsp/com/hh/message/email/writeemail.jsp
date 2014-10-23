<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>写邮件</title>
<%=SystemUtil.getBaseJs("checkform", "ckeditor")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 800;
	var height = 450;

	var objectid = params.row ? params.row.id : '';
	var writeObject = params.writeObject||{};
	var shouuser = params.shouuser;

	function save() {
		FormUtil.check('form', function(formData) {
			Request.request('message-SendEmail-sendEmail', {
				data : formData,
				callback : function(result) {
					if (result.success) {
						if(params.callback){
							params.callback();
						}
						Dialog.close();
					}
				}
			});
		});
	}

	function write() {
		FormUtil.check('form', function(formData) {
			formData.leixing = '0';
			formData.type = 'yfs';
			Request.request('message-SendEmail-sendEmail', {
				data : formData,
				callback : function(result) {
					if (result.success) {
						if(params.callback){
							params.callback();
						}
						Dialog.close();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('message-SendEmail-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		}else{
			$('#form').setValue(writeObject);
		}
	}

	function init() {
		if(shouuser){
			writeObject.users=shouuser;
		}
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				<tr>
					<td xtype="label" style="width: 80px;">标题：</td>
					<td><span xtype="text" config=" name : 'title',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">收件人：</td>
					<td><span id="shoujianrenspan" xtype="selectUser" config="name: 'users' "></span></td>
				</tr>
				<tr>
					<td xtype="label">附件：</td>
					<td><span xtype="file"
						config="name: 'files' ,filePath:'sys_send_email#sys_shou_email/files' "></span></td>
				</tr>
				<tr>
					<td xtype="label">内容：</td>
					<td><span xtype="ckeditor" config="name: 'content' "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
	<span xtype="button" config="text:'发送' , onClick : write "></span>
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>