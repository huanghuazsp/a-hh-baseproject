<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程编辑</title>
<%=SystemUtil.getBaseJs("checkform", "date")%>
<%
	int ctype = Convert.toInt(request.getParameter("ctype"));
%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 650;
	var height = 300

	var object = params.object;
	var objectid = object ? object.id : '';

	var yxjconfig = {
		name : 'level',
		data : [
				{
					'id' : '4',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_red.png\'/>重要紧急'
				},
				{
					'id' : '3',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_yellow.png\'/>重要不紧急'
				},
				{
					'id' : '2',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_blue.png\'/>紧急不重要'
				},
				{
					'id' : '1',
					'text' : '<img src=\'/hhcommon/images/icons/flag/flag_green.png\'/>不重要不紧急'
				}, {
					'id' : '',
					'text' : '无优先级'
				} ]
	}

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('oa-Schedule-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						params.callback(result);
						Dialog.close();
					}
				}
			});
		});
	}

	function ok(ok) {
		if (objectid) {
			Request.request('oa-Schedule-ok', {
				data : {
					id : objectid,
					isOk : ok
				}
			}, function(result) {
				if (result.success!=false) {
					object.isOk = ok;
					object.start = $.hh.stringToDate(object.start);
					object.end = $.hh.stringToDate(object.end);
					params.callback(object);
					Dialog.close();
				}
			});
		}
	}
	function doDelete() {
		if (objectid) {
			Request.request('oa-Schedule-deleteByIds', {
				data : {
					ids : objectid
				}
			}, function(result) {
				if (result.success!=false) {
					params.callback('delete');
					Dialog.close();
				}
			});
		}
	}

	function findData() {
		if (object) {
			if (object.id) {
				if (object.isOk == 1) {
					$('#nookspan').show();
				} else {
					$('#okspan').show();
				}
				$('#deletespan').show();
			}
			if (object.ctype == 1) {
				$('#savespan').hide();
				$('#deletespan').hide();
				$('#okspan').hide();
				$('#nookspan').hide();
				$('#form').setValue(object, {view:true});
			} else {
				$('#form').setValue(object);
			}
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
					<td xtype="label">内容：</td>
					<td colspan="3"><span xtype="text"
						config=" name : 'content' ,required :true "></span></td>
				</tr>
				<%
					if (ctype == 1) {
				%>
				<tr>
					<td xtype="label">创建人：</td>
					<td colspan="3"><span xtype="selectUser"
						config="name: 'userId' "></span></td>
				</tr>
				<%
					}
				%>
				<!-- <tr>
					<td xtype="label">发布范围：</td>
					<td colspan="3"><span xtype="selectUser"
						config="name: 'participants' "></span></td>
				</tr> -->
				<tr>
					<td xtype="label">开始：</td>
					<td><span xtype="date"
						config="name: 'start'  ,type:'datetime' ,required :true"></span></td>
					<td xtype="label">结束：</td>
					<td><span xtype="date"
						config="name: 'end'  ,type:'datetime'  "></span></td>
				</tr>
				<tr>
					<td xtype="label">优先级：</td>
					<td colspan="3"><span xtype="radio" configVar="yxjconfig"></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span id="okspan" xtype="button"
			config="text:'完成' , onClick : ok ,hidden:true ,params:1"></span><span
			id="nookspan" xtype="button"
			config="text:'未完成' , onClick : ok ,hidden:true,params:0"></span> <span
			id="deletespan" xtype="button"
			config="text:'删除' , onClick : doDelete ,hidden:true"></span> <span
			id="savespan" xtype="button" config="text:'保存' , onClick : save "></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>