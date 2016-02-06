<%@page import="com.hh.system.util.PrimaryKey"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>写邮件</title>
<%=SystemUtil.getBaseJs("checkform", "ckeditor","fileUpload")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 800;
	var height = 450;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var writeObject = parent.paramsData.writeObject||{};

	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.type = 'cgx';
			var userData = $('#shoujianrenspan').getValueData();
			if(userData){
				formData.userNames = BaseUtil.objsToStr(userData,'text');
			}
			Request.request('message-Email-sendEmail', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						if(params.callback){
							params.callback();
						}
						shoujianlist();
					}
				}
			});
		});
	}

	function write() {
		$.hh.validation.check('form', function(formData) {
			formData.type = 'yfs';
			var userData = $('#shoujianrenspan').getValueData();
			if(userData){
				formData.userNames = BaseUtil.objsToStr(userData,'text');
			}
			Request.request('message-Email-sendEmail', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						if(params.callback){
							params.callback();
						}
						shoujianlist();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('message-Email-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.file = result.id;
					$('#form').setValue(result);
				}
			});
		}else{
			writeObject = writeObject || {}; 
			writeObject.bid = uuid;
			writeObject.file = uuid;
			$('#form').setValue(writeObject);
		}
	}

	function init() {
		findData();
	}
	
	function shoujianlist(){
		parent.shoujianlist();
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<span id="bidspan" xtype="text" config=" hidden:true,name : 'bid' "></span>
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
					<td><span xtype="fileUpload"
						config=" name : 'file',type:'email' "></span></td>
				</tr>
				<tr>
					<td xtype="label">内容：</td>
					<td><span xtype="ckeditor" config="name: 'content' ,nheight:300 ,mheight:200"></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
	<span xtype="button" config="text:'发送' , onClick : write "></span>
		<span xtype="button" config="text:'暂存' , onClick : save "></span> <span
			xtype="button" config="text:'返回收件列表' , onClick : shoujianlist "></span>
	</div>
</body>
</html>