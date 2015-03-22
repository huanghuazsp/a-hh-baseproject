<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>修改密码</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	function updatePassWord() {
		$.hh.validation.check('form', function(formData) {
			if (formData.vmm != formData.mm) {
				Dialog.infomsg("新密码输入不一致！");
			} else {
				Request.request('usersystem-user-updatePassWord', {
					data : formData,
					defaultMsg : true,
					callback : function(result) {
						if(result.success){
							Dialog.close();
						}
					}
				});
			}
		});
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form xtype="form" id="form">
			<table xtype="form">
				<tbody>
					<tr>
						<td class="label">旧密码：</td>
						<td><span
							config="required :true, name : 'oldPassword' "
							xtype="password"></span></td>
					</tr>
					<tr>
						<td class="label">新密码：</td>
						<td><span
							config=" required :true , name : 'vmm' "
							xtype="password"></span></td>
					</tr>
					<tr>
						<td class="label">重复新密码：</td>
						<td><span
							config=" required :true , name : 'mm' "
							xtype="password"></span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button"
			config="text:'确定' , onClick : updatePassWord "></span>
		<span xtype="button"
			config="text:'取消' , onClick : Dialog.close"></span>
	</div>
</body>
</html>