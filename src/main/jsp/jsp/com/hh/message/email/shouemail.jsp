<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>收邮件</title>
<%=SystemUtil.getBaseJs("checkform","fileUpload")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 800;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var type = '<%=Convert.toString(request.getParameter("type"))%>';
	var shouObject = null;
	function findData() {
		if (objectid) {
			Request.request('message-Email-findReadObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.file=result.id;
					shouObject = result;
					$('#form').setValue(result, {view:true});
				}
			});
		}
	}

	function reply() {
		var userObject = {
			id : shouObject.sendUserId,
			text : shouObject.sendUserName
		};
		var title = "回复-->" + shouObject.title;
		//var content = $('#form').html()||'';
		var data = $('#form').getValue();
		var filehtml='';
		/* var files = $.hh.toObject(data.files);
		if(files){
			for(var i=0;i<files.length;i++){
				var fileitem = files[i];
				filehtml+='<a href="'+fileitem.path+'">'+fileitem.attachmentFileName+'</a>&nbsp;；';
			}
		} */
		var content ='<h3>【'+data.sendUserName+'】于'+ ($.hh.formatDate(data.vcreate, 'yyyy年MM月dd日 （EEE） HH:mm'))+'向【'+data.userNames+'】发送了一封邮件【'+data.title+'】！</h3><br/><br/>';
		if(filehtml){
			content+='附件：'+filehtml+'<br/>'
		}
		content+='内容：'+data.content+'<br/>';
		
		
		parent.writeemail({
			users : userObject,
			title : title,
			content : "<br/><br/><br/><br/><br/><h2 style='text-align:center;'>------------------ 原始邮件 ------------------</h2>"
					+( content)
		});
	}

	function init() {
		findData();
		$('table').css('background',$.hh.property.classObject.themeContent)
	}
	
	function shoujianlist(){
		if(type=='sendemaillist'){
			parent.sendemaillist();
		}else if(type=='cgxemaillist'){
			parent.cgxemaillist();
		}else if(type=='deleteemaillist'){
			parent.deleteemaillist();
		}else{
			parent.shoujianlist();
		}
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<div></div>
			<span xtype="text" config=" name : 'sendUserId' ,hidden:true "></span>
			
			<table width=100%>
				<tr>
					<td xtype="label" style="text-align:left;padding:10px;">
						<div style="text-align:center;">
						<font size=4 ><b><span xtype="html" config=" name : 'title' "></span></b></font>
						</div>
						发&nbsp;件&nbsp;人：<span xtype="html" config="name: 'sendUserName' "></span><br>
						时&nbsp;&nbsp;&nbsp;间：<span xtype="html" config="name: 'dcreate' ,dateType:'yyyy年MM月dd日 （EEE） HH:mm'"></span><br>
						收&nbsp;件&nbsp;人：<span xtype="html" config="name: 'userNames' "></span><br>
						附&nbsp;&nbsp;&nbsp;件： <span xtype="fileUpload"
						config="name: 'file' , type:'email' ,request:true"></span>
					</td>
					<td xtype="label">
					</td>
				</tr>
			</table>
			<div style="padding:25px;">
						<span xtype="html" config="name: 'content' "></span>
			</div>
		</form>
	</div>
	<div xtype="toolbar">
		<% if(Convert.toString(request.getParameter("type")).equals("shouemaillist")){ %>
		<span id='reply' xtype="button" config="text:'回复' , onClick : reply "></span>
		<%} %>
		<span id="back"
			xtype="button" config="text:'返回列表' , onClick : shoujianlist "></span>
	</div>
</body>
</html>