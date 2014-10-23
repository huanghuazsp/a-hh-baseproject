<%@page import="com.hh.usersystem.bean.usersystem.HhXtCd"%>
<%@page import="com.hh.usersystem.bean.usersystem.HhXtYh"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>组织机构树</title>
<%=SystemUtil.getBaseJs("ztree", "right_menu")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var orgtreeconfig = {
		id : 'orgTree',
		url : 'usersystem-Org-queryOrgAndUsersList',
		rightItems : [ {
			text : '发送邮件',
			img : StaticVar.img_email,
			onClick : function(data) {
				var userids = '';
				var usernames = '';
				if (data.code_) {
					Request.request('usersystem-user-queryUserByOrcCode', {
						data : {
							code : data.code_
						},
						async : false
					}, function(result) {
						userids = BaseUtil.objsToStr(result);
						usernames = BaseUtil.objsToStr(result, 'text');
					});
				} else {
					userids = BaseUtil.toObject(data.id).userid;
					usernames = data.text;
				}

				Dialog.open({
					url : 'jsp-message-email-writeemail',
					params : {
						shouuser : {
							id : userids,
							text : usernames
						}
					}
				});

			}
		} ]
	}

	var grouprender = false;
	var tabconfig = {
		activate : function(ui) {
			var newPanel = ui.newPanel;
			var id = newPanel.attr('id');
			if (id == 'grooupDiv' && grouprender == false) {
				grouprender = true;
				$('#span_groupTree').render();
			}
		}
	}

	var groupTreeConfig = {
		id : 'groupTree',
		url : 'usersystem-Group-queryListAndUserGroup',
		render : false,
		rightItems : [ {
			text : '发送邮件',
			img : StaticVar.img_email,
			onClick : function(data) {
				var userids = '';
				var usernames = '';
				Request.request('usersystem-user-queryUserByGroup', {
					data : {
						groupId : data.id
					},
					async : false
				}, function(result) {
					userids = BaseUtil.objsToStr(result);
					usernames = BaseUtil.objsToStr(result, 'text');
				});

				Dialog.open({
					url : 'jsp-message-email-writeemail',
					params : {
						shouuser : {
							id : userids,
							text : usernames
						}
					}
				});

			}
		} ]
	}
	
	function set_height(height) {
		$('#tabs').render();
	}
</script>
</head>
<body>
	<div id="tabs" xtype="tab" configVar="tabconfig">
		<ul>
			<li><a href="#tabs-1">机构</a></li>
			<li><a href="#grooupDiv">组</a></li>
		</ul>
		<div id="tabs-1">
			<span xtype="tree" configVar="orgtreeconfig"></span>
		</div>
		<div id="grooupDiv">
			<span xtype="tree" configVar=" groupTreeConfig "></span>
		</div>
	</div>
</body>
</html>