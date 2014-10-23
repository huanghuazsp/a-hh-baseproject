<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>收邮件</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 800;
	var height = 450;

	var objectid = params.row ? params.row.id : '';
	if(objectid==''){
		objectid=params.objectId;
	}
	var shouObject = null;
	function findData() {
		if (objectid) {
			Request.request('message-ShouEmail-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					shouObject = result;
					$('#form').setValue(result, true);
				}
			});
		}
	}

	function reply() {
		var userObject = {
			id : shouObject.sendUser,
			text : shouObject.sendUserName
		};
		var title = "回复-->" + shouObject.title;
		//var content = $('#form').html()||'';
		var data = $('#form').getValue();
		var filehtml='';
		var files = BaseUtil.toObject(data.files);
		if(files){
			for(var i=0;i<files.length;i++){
				var fileitem = files[i];
				filehtml+='<a href="'+fileitem.path+'">'+fileitem.attachmentFileName+'</a>&nbsp;；';
			}
		}
		var content ='<h3>【'+data.userNames+'】于'+ data.dcreate+'向【'+data.sendUserName+'】发送了一封邮件【'+data.title+'】！</h3><br/><br/>';
		if(filehtml){
			content+='附件：'+filehtml+'<br/>'
		}
		content+='内容：'+data.content+'<br/>';
		
		Dialog
				.open({
					url : 'jsp-message-email-writeemail',
					params : {
						writeObject : {
							users : userObject,
							title : title,
							content : "<br/><br/><br/><br/><br/><h2 style='text-align:center;'>------------------ 原始邮件 ------------------</h2>"
									+( content)
						},
						callback : function() {
							Dialog.close();
						}
					}
				});
	}

	function init() {
		findData();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<table xtype="form">
				<tr>
					<td xtype="label" style="width: 80px;">标题：</td>
					<td><span xtype="text" config=" name : 'title',required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label">发件人：</td>
					<td><span xtype="text" config="name: 'sendUserName' "></span></td>
				</tr>
				<tr>
					<td xtype="label">时间：</td>
					<td><span xtype="date" config="name: 'dcreate' ,type:'datetime'"></span></td>
				</tr>
				<tr>
					<td xtype="label">收件人：</td>
					<td><span xtype="text" config="name: 'userNames' "></span></td>
				</tr>
				<tr>
					<td xtype="label">附件：</td>
					<td><span xtype="file"
						config="name: 'files' ,filePath:'task/sys_send_email#sys_shou_email/files' "></span></td>
				</tr>
				<tr>
					<td xtype="label">内容：</td>
					<td ><span xtype="textarea" config="name: 'content' "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'回复' , onClick : reply "></span><span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>