<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>异常查看</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	function init() {
		$("#table").setValueName(params);
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<table xtype="form" id="table">
			<tr>
				<td xtype="label" width=100px>创建人</td>
				<td domName="createUserName"></td>
				<td xtype="label">生成时间</td>
				<td domName="dcreate" render="datetime"></td>
			</tr>
			<tr>
				<td xtype="label">异常类名</td>
				<td colspan="3" domName="name"></td>
			</tr>
			<tr>
				<td xtype="label">异常内容</td>
				<td colspan="3" domName="message"></td>
			</tr>
			<tr>
				<td xtype="label">异常完整内容</td>
				<td colspan="3"  style="word-break: keep-all;white-space:nowrap;width:600px">
				<div  domName="allMessage" style="width:600px;text-align:center;vertical-align:middle;overflow:hidden;text-overflow:ellipsis;">
				
				</div>
				</td>
			</tr>
		</table>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>