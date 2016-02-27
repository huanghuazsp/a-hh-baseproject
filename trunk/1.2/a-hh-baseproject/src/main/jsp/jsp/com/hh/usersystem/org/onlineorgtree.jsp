<%@page import="com.hh.usersystem.bean.usersystem.SysMenu"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>组织机构树</title>
<%=SystemUtil.getBaseJs("layout","ztree")%>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();
	var orgtreeconfig = {
		id : 'orgTree',nheight:36,
		url : 'usersystem-Org-queryOrgAndUsersList',
		rightMenu :[{
			text:'刷新',
			img : StaticVar.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('orgTree');
			}
		}],
		itemRightMenu : [ {
			text : '发送邮件',
			img : StaticVar.img_email,
			onClick : function(data) {
				var userids = '';
				var usernames = '';
				if (data.code_) {
					Request.request('usersystem-user-queryUserByOrgId', {
						data : {
							code : data.id
						},
						async : false
					}, function(result) {
						userids = BaseUtil.objsToStr(result);
						usernames = BaseUtil.objsToStr(result, 'text');
					});
				} else {
					userids = data.id;
					usernames = data.text;
				}
				
				var data = {
						id:'e9fa8689-c362-4c66-bd75-d1b132bd5211',
						text:'个人邮件',
						vsj:'jsp-message-email-emailmain?type=write',
						params:{
							userids : userids,
							usernames : usernames
						}
				}

				if (BaseUtil.getRootFrame().addTab) {
					BaseUtil.addTab(data)
				} else {
					Request.href(data.vsj);
				}
				
				/*Dialog.open({
					url : 'jsp-message-email-writeemail',
					params : {
						shouuser : {
							id : userids,
							text : usernames
						}
					}
				});*/

			}
		},{
			text:'刷新',
			img : StaticVar.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('orgTree');
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
		id : 'groupTree',nheight:36,
		url : 'usersystem-Group-queryListAndUserGroup',
		render : false,
		rightMenu : [{
			text:'刷新',
			img : StaticVar.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('groupTree');
			}
		}],
		itemRightMenu : [ {
			text : '发送邮件',
			img : StaticVar.img_email,
			onClick : function(data) {
				var userids = '';
				var usernames = '';
				if(data.type!='user'){
					Request.request('usersystem-user-queryUserByGroup', {
						data : {
							groupId : data.id
						},
						async : false
					}, function(result) {
						userids = BaseUtil.objsToStr(result);
						usernames = BaseUtil.objsToStr(result, 'text');
					});
				}else{
					userids = data.id;
					usernames = data.text;
				}
				

				var data = {
						id:'e9fa8689-c362-4c66-bd75-d1b132bd5211',
						text:'个人邮件',
						vsj:'jsp-message-email-emailmain?type=write',
						params:{
							userids : userids,
							usernames : usernames
						}
				}

				if (BaseUtil.getRootFrame().addTab) {
					BaseUtil.addTab(data)
				} else {
					Request.href(data.vsj);
				}

			}
		},{
			text:'刷新',
			img : StaticVar.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('groupTree');
			}
		} ]
	}

	function setHeight(height) {
		//$('#tabs').render();
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:260 ">
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
		</div>
		<div></div>
	</div>

</body>
</html>