<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>消息管理</title>
<%=SystemUtil.getBaseJs()%>
<script type="text/javascript">
	function titleRender(a, data) {
		var isRead = data.isRead==1?'<font class="hh_green">已读</font>':'<font class="hh_red">未读</font>';
		var content =isRead+ '<h3>【' + data.createUserName + '】于<font class="hh_green">'
				+ $.hh.renderValue(data.dcreate, 'datetime')
				+ '</font>向您发送了一条提醒【<font class="hh_blue">' + data.title
				+ '</font>】！</h3>';
		return content;
	}
	function itemClick(row) {
		var param = row.rowdata;

		Request.request('message-SysMessage-updateRead', {
			data : {
				id : param.id
			},
			defaultMsg:false
		},function(result){
			if(result.success!=false){
				$('#pagelist').loadData();
			}
		});

		var object = $.hh.listToObject(param.params);
		if (param.path) {
			Dialog.open({
				width : param.width || 600,
				height : param.height || 400,
				url : param.path,
				params : object
			});
		} else {
			Dialog.infomsg('没有关联的业务！');
		}
	}
</script>
</head>
<body>
	<div id="pagelist" xtype="pagelist"
		config=" url: 'message-SysMessage-queryMyMessage' , title : false , radio : true , itemClick : itemClick ,column : [
		{
			render : titleRender
		}
	]">
	</div>
</body>
</html>