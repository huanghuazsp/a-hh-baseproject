<%@page import="com.hh.system.util.SystemUtil"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>编辑项目信息</title>
<%=SystemUtil.getBaseJs("checkform", "date", "ueditor") + SystemUtil.getUser()%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 950;
	var height = 650;

	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('project-ProjectInfo-save', {
				data : formData,
				callback : function(result) {
					if (result.success != false) {
						if (params.callback) {
							params.callback(formData);
						}
						wdxm();
					}
				}
			});
		});
	}

	function wdxm() {
		if (parent.wdxm) {
			parent.wdxm();
		} else {
			Dialog.close();
		}
	}

	function findData() {
		if (objectid) {
			Request.request('project-ProjectInfo-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					$('#form').setValue(result);
				}
			});
		} else {
			$('#form').setValue({
				manager : loginUser.id,
				managerText : loginUser.text,
				bid : uuid
			});
		}
	}

	function init() {
		findData();
	}

	var modularrender = false;
	var userrender = false;
	var filerender = false;
	var tabconfig = {
		activate : function(ui) {
			var newPanel = ui.newPanel;
			var id = newPanel.attr('id');
			var projectId =objectid || $('#bidspan').getValue();
			if (id == 'modularDiv' && modularrender == false) {
				modularrender = true;
				$('#modularDiv').find('iframe').attr(
						'src',
						'jsp-project-projectmodular-ProjectModularList?projectId='
								+ projectId + '&oper=all');
			} else if (id == 'userDiv' && userrender == false) {
				userrender = true;
				$('#userDiv').find('iframe').attr(
						'src',
						'jsp-project-projectuserinfo-ProjectUserInfoList?projectId='
								+ projectId + '&oper=all');
			} else if (id == 'fileDiv' && filerender == false) {
				filerender = true;
				$('#fileDiv').find('iframe').attr(
						'src',
						'jsp-project-projectfile-ProjectFileList?projectId='
								+ projectId + '&oper=all');
			}
		}
	}

	function renderFile(value) {
		var str = '';

		if (value) {
			var fileList = $.hh.toObject(value);
			for (var i = 0; i < fileList.length; i++) {
				var data = fileList[i];
				str += $.hh.property.getFileTypeIcon(data.fileType)
						+ data.text
						+ '&nbsp;&nbsp;<a href="javascript:Request.download(\''
						+ data.id
						+ '\');">下载</a>&nbsp;&nbsp;<a href="javascript:Request.viewFile(\''
						+ data.id + '\');">查看</a><br>'
			}
		}
		return str;
	}
</script>
</head>
<body>
	<div xtype="hh_main_content">
		<div id="tabs" xtype="tab" configVar="tabconfig">
			<ul>
				<li><a href="#formDiv">项目信息</a></li>
				<li><a href="#modularDiv">模块信息</a></li>
				<li><a href="#userDiv">参与者信息</a></li>
				<li><a href="#fileDiv">附件文档</a></li>
			</ul>
			<div id="formDiv">
				<form id="form" xtype="form" class="form">
					<span xtype="text" config=" hidden:true,name : 'id'"></span>
					<span id="bidspan" xtype="text" config=" hidden:true,name : 'bid' "></span>
					<table xtype="form">
						<tr>
							<td xtype="label">项目名称：</td>
							<td colspan="3"><span xtype="text"
								config=" name : 'text' ,required :true "></span></td>
						</tr>
						<tr>
							<td xtype="label">开始日期：</td>
							<td><span xtype="date"
								config="name: 'startDate'  ,type:'date' ,required :true"></span></td>
							<td xtype="label">（计划）<br>结束日期：
							</td>
							<td><span xtype="date"
								config="name: 'planEndDate'  ,type:'date' ,required :true "></span></td>
						</tr>
						<tr>
							<td xtype="label">项目经理：</td>
							<td><span xtype="selectUser"
								config=" name : 'manager' ,required :true "></span></td>
							<td xtype="label">所有人可见：</td>
							<td><span xtype="check"
								config=" name : 'allUserRead',num:1 "></span></td>
						</tr>

						<tr>
							<td xtype="label">客户名称：</td>
							<td><span xtype="text" config=" name : 'client' "></span></td>
							<td xtype="label">（万）<br>项目金额：
							</td>
							<td><span xtype="text" config=" name : 'money',number:true "></span></td>
						</tr>

						<tr>
							<td xtype="label">描述：</td>
							<td colspan="3"><span xtype="ckeditor"
								config=" name : 'describe' "></span></td>
						</tr>


					</table>
				</form>
				<div xtype="toolbar">
					<span xtype="button" config="text:'保存' , onClick : save "></span> <span
						xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
				</div>
			</div>

			<div id="modularDiv" style="padding: 0px;">
				<iframe frameborder=0 width=100% height=100% src="about:blank"></iframe>
			</div>

			<div id="userDiv">
				<iframe frameborder=0 width=100% height=100% src="about:blank"></iframe>
			</div>

			<div id="fileDiv">
				<iframe frameborder=0 width=100% height=100% src="about:blank"></iframe>
			</div>

		</div>
	</div>

</body>
</html>


