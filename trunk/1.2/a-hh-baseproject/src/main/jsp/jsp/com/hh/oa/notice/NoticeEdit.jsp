<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","ueditor","fileUpload")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 850;
	var height = 600;
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('oa-Notice-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						if(params.callback){
							params.callback();
						}
						wfbd();
					}
				}
			});
		});
	}

	function wfbd(){
		parent.wfbd();
	}
	function deploy() {
		$.hh.validation.check('form', function(formData) {
			formData.deploy=1;
			Request.request('oa-Notice-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						if(params.callback){
							params.callback();
						}
						wfbd();
					}
				}
			});
		});
	}

	function findData() {
		if (objectid) {
			Request.request('oa-Notice-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.file = result.id;
					$('#form').setValue(result);
					rangeTypeChange();
				}
			});
		}else{
			$('#form').setValue({
				bid : uuid,
				file : uuid,
				rangeType : 1
			});
			rangeTypeChange();
		}
	}
	
	function rangeTypeChange(value){
		var v = $('#span_rangeType').getValue();
		if(v==0){
			$('#span_range').undisabled();
			$('#span_range').render({
				required:true
			});
		}else{
			$('#span_range').disabled();
			$('#span_range').render({
				required:false
			});
		}
	}

	function init() {
		findData();
	}
	
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<span id="bidspan" xtype="text" config=" hidden:true,name : 'bid' "></span>
			<table xtype="form">
				<tr>
					<td xtype="label" style="width: 80px;">标题：</td>
					<td colspan="2"><span xtype="text" config=" name : 'title' ,required :true"></span></td>
				</tr>
				<tr>
					<td xtype="label" style="width: 80px;">公告类型：</td>
					<td colspan="2"><span xtype="combobox" config=" name : 'type' ,required :true  , data : <%=SystemUtil.getJsonDataByCode("gonggaoleixing")%> "></span></td>
				</tr>
				<tr>
					<td xtype="label">发布范围：</td>
					<td width=170>
						<span xtype="radio"
						config="  onChange: rangeTypeChange , name: 'rangeType' ,value : 1 ,data : [{id:1,text:'所有'},{id:0,text:'选择机构/部门'}] ">
						</span>
					</td>
					<td><span  xtype="selectOrg" config="name: 'range',many:true  "></span></td>
				</tr>
				<tr>
					<td xtype="label">附件：</td>
					<td colspan="2"><span xtype="fileUpload"
						config=" name : 'file',type:'notice' ,request:true"></span></td>
				</tr>
				<tr>
					<td xtype="label">内容：</td>
					<td colspan="2">
					<span xtype="ckeditor" config="name: 'content' ,required :true "></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
	<span xtype="button" config="text:'发送' , onClick : deploy "></span>
		<span xtype="button" config="text:'暂存' , onClick : save "></span> 
		<span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>