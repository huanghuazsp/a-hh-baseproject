<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform","date","fileUpload", "ckeditor")%>
<%
	String type =   Convert.toString(request.getParameter("type"));
%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 800;
	var height = 600;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';
	var view = '<%=Convert.toString(request.getParameter("view"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('system-Resources-save', {
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
			Request.request('system-Resources-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					if(view){
						$('#form').setValue(result, {view:true});
						$('#div_toolbar').hide();
					}else{
						$('#form').setValue(result);
					}
					
				}
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
		<form id="form" xtype="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
					<tr>
						<td xtype="label">名称：</td>
						<td style="width:280px;"><span xtype="text" config=" name : 'text' "></span></td>
						<td xtype="label">类型：</td>
						<td style="width:250px;"><span id="node_span" xtype="selectTree"
							config="  value:'<%=type %>' , name: 'type' , findTextAction : 'system-ResourcesType-findObjectById' , url : 'edu-SubjectType-queryTreeList' ,required :true "></span>
						</td>
					</tr>
					<tr>
						<td xtype="label">资源：</td>
						<td style="text-align:center;">
						<span config="'name':'files','type':'sysresources' "  xtype="fileUpload" ></span>
						</td>
						<td  style="text-align:center;" colspan="2">
						<span xtype="uploadpic"
						config="name: 'img' , type : 'sysresources' ,width:200,height:200 "></span>
						</td>
					</tr>
					<tr>
						<td xtype="label">资源描述：</td>
						<td colspan="3" style="width:700px;">
						<span xtype="ckeditor" config="name: 'content' , nheight :300 ,mheight:200"></span>
						</td>
					</tr>
			</table>
		</form>
	</div>
	<div id="div_toolbar" xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>

 
 