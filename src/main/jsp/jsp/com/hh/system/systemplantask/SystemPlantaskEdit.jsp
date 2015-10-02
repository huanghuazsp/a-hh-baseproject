<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=BaseSystemUtil.getBaseJs("checkform", "date")%>

<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var width = 600;
	var height = 450;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('system-SystemPlantask-save', {
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
			Request.request('system-SystemPlantask-findObjectById', {
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
		findData();
	}

	var formulaConfig = {
		name : 'formula',
		trhtml : '<tr><td xtype="label">类型：</td><td><span xtype="combobox" valuekey="type" '
		+' config=" data : [ {id : \'sql\',		text : \'sql\'		}, {		id : \'class\',text : \'class\'} ] "></span></td>'
		+ '<td xtype="label">执行：</td><td><span xtype="text" valuekey="value" config=" "></span></td></tr>'
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
					<td><span xtype="text"
						config=" name : 'name'  ,required : true"></span></td>
				</tr>

				<tr>
					<td xtype="label">是否有效：</td>
					<td><span xtype="check" config=" name : 'valid' , num : true"></span></td>
				</tr>

				<tr>
					<td xtype="label">隔多久：</td>
					<td><span xtype="text"
						config=" name : 'hour' , number :true ,width:70"></span>&nbsp;时 <span
						xtype="text" config=" name : 'minute' , number :true ,width:70"></span>&nbsp;分
						<span xtype="text"
						config=" name : 'second' , number :true ,width:70"></span>&nbsp;秒
					</td>
				</tr>

				<tr>
					<td xtype="label">注册时间：</td>
					<td><span xtype="text"
						config=" name : 'hourRegister' , number :true ,width:70"></span>&nbsp;时
						<span xtype="text"
						config=" name : 'minuteRegister' , number :true ,width:70"></span>&nbsp;分
						<span xtype="text"
						config=" name : 'secondRegister' , number :true,width:70 "></span>&nbsp;秒
					</td>
				</tr>

				<tr>
					<td colspan="2"><span xtype="tableitem" configVar="formulaConfig"></span></td>
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


