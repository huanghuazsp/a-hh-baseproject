<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>附件编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date","fileUpload")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			formData.projectId = projectId;
			Request.request('project-ProjectFile-save', {
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
			Request.request('project-ProjectFile-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.fileStr = result.id;
					$('#form').setValue(result);
				}
			});
		}else{
			$('#form').setValue({
				bid : uuid,
				fileStr : uuid
			});
		}
	}
	
	function fileChange(data){
		var oldValue = $('#span_text').getValue();
		if(oldValue){
			oldValue=oldValue+','+data.text;
		}else{
			oldValue = data.text;
		}
		$('#span_text').setValue(oldValue);
	}

	function init() {
		findData();
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
						<td xtype="label">类型：</td>
						<td><span xtype="radio" config=" name : 'type' ,required :true , data : <%=SystemUtil.getJsonDataByCode("fujianleixing")%>   "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">附件：</td>
						<td><span xtype="fileUpload"
						config=" name : 'fileStr',type:'projectFile' ,request:true ,parentServiceId: projectId , onChange : fileChange "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">文档名称：</td>
						<td><span xtype="text" config=" name : 'text',required :true  "></span></td>
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

 
 