<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>岗位切换</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var data = [];
	for (var i = 0; i < params.currOrg.length; i++) {
		data.push({id:params.currOrg[i].id,text:params.currOrg[i].text});
	}
	var configcombobox = {
		required : true,
		name : 'currOrgId',
		data : data
	}
	function submit() {
		$.hh.validation.check('form', function(formData) {
			Request.request('usersystem-App-changeOrg', {
				data : formData,
				callback : function(result) {
						Dialog.close();
				}
			});
		});
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<table xtype="form">
				<tbody>
					<tr>
						<td xtype="label">选择机构：</td>
						<td><span configVar="configcombobox"  xtype="combobox"></span></td>
						<td width="60px"><span
							config=" name : 'remember'  ,  data : [{id:1,text:'记住'}] "
							xtype="checkbox"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'确定' , onClick : submit "></span> <span
			xtype="button" config="text:'取消' , onClick : Dialog.close"></span>
	</div>
</body>
</html>