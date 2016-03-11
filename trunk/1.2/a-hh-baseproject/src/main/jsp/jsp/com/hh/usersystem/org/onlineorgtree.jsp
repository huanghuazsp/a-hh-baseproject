<%@page import="com.hh.usersystem.bean.usersystem.SysMenu"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>组织机构树</title>
<%=SystemUtil.getBaseJs("layout","ztree","ckeditor")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var orgtreeconfig = {
		id : 'orgTree',nheight:42,
		url : 'usersystem-Org-queryOrgAndUsersList',
		rightMenu :[{
			text:'刷新',
			img : $.hh.property.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('orgTree');
			}
		}],
		itemRightMenu : [ {
			text : '发送邮件',
			img : $.hh.property.img_email,
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
						userids = $.hh.objsToStr(result);
						usernames = $.hh.objsToStr(result, 'text');
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

				if ($.hh.getRootFrame().addTab) {
					$.hh.addTab(data)
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
			img : $.hh.property.img_refresh,
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
		id : 'groupTree',nheight:42,
		url : 'usersystem-Group-queryListAndUserGroup',
		render : false,
		rightMenu : [{
			text:'刷新',
			img : $.hh.property.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('groupTree');
			}
		}],
		itemRightMenu : [ {
			text : '发送邮件',
			img : $.hh.property.img_email,
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
						userids = $.hh.objsToStr(result);
						usernames = $.hh.objsToStr(result, 'text');
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

				if ($.hh.getRootFrame().addTab) {
					$.hh.addTab(data)
				} else {
					Request.href(data.vsj);
				}

			}
		},{
			text:'刷新',
			img : $.hh.property.img_refresh,
			onClick:function(){
					$.hh.tree.refresh('groupTree');
			}
		} ]
	}

	function doSendMessage(){
		
	}
	
	function setHeight(height) {
		$('#himessagediv').height(height-218);
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
		<div>
		
		<div xtype="border_layout">
			<div>
				<div id="userdiv" xtype="toolbar" config="type:'head'" style="text-align:center;height:28px;vertical-align:middle;">
				</div>
				<div id="himessagediv" style="padding:10px;overflow-y:auto;overflow-x:hidden;">
				
					<div >
						<table style="width:100%;">
							<tr>
								<td style="width:40px;text-align:center;">
									<img width="32" height="32"  src="/hhcommon/images/icons/user/100/no_on_line_user.jpg"/>
									<br/>
									黄华
								</td>
								<td >
									<div style="-moz-border-radius:5px;	-webkit-border-radius:5px;	border-radius:5px;	background-color:#bdeea3;	width:340px;	height:auto;	display:block;	padding: 5px;	float:left;	color:#333333;">
										<font style="font-size: 14px; "><b>我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？我的问题是：1+1=？</b></font>
										<br/>
										<div style="padding-top:6px;color:#a9b4a2;">2014-09-15 15:06</div>
									</div>
								</td>
							</tr>
						</table>
						<table style="width:100%;">
							<tr>
								<td >
									<div style="-moz-border-radius:5px;	-webkit-border-radius:5px;	border-radius:5px;	background-color:#d4eede;	width:340px;	height:auto;	display:block;	padding: 5px;	float:right;	color:#333333;">
										<font style="font-size: 14px; "><b>我的问题是：1+1=？</b></font>
										<br/>
										<div style="padding-top:6px;color:#a9b4a2;">2014-09-15 15:06</div>
									</div>
								</td>
								<td style="width:40px;text-align:center;">
									<img width="32" height="32"  src="/hhcommon/images/icons/user/100/no_on_line_user.jpg"/>
									<br/>
									黄华
								</td>
							</tr>
						</table>
					</div>
				
				</div>
			</div>
			<div config="render : 'south' ,width:160,spacing_open:0 ">
				<span config=" height:80,bottom:'hidden', name:'message' ,toolbar : ['Format',	'Font', 'FontSize', 'Styles'] "  xtype="ckeditor" ></span>
				<div xtype="toolbar" config="type:'head'" style="text-align:right;">
					<span id="backbtn" xtype="button"
						config="onClick: doSendMessage ,text:'发送'   "></span>
				</div>
			</div>
		</div>
		</div>
	</div>

</body>
</html>