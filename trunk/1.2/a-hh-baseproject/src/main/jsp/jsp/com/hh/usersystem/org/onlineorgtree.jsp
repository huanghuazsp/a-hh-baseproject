<%@page import="com.hh.system.util.Json"%>
<%@page import="com.hh.usersystem.bean.usersystem.SysMenu"%>
<%@page import="com.hh.usersystem.bean.usersystem.UsUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>即时通讯</title>
<%=SystemUtil.getBaseJs("layout", "ztree", "ckeditor")+SystemUtil.getUser()%>
<script type="text/javascript" src="/hhcommon/opensource/dwr/engine.js"></script>
<script type="text/javascript" src="/hhcommon/opensource/dwr/message.js"></script>
<script type="text/javascript" src="/hhcommon/opensource/dwr/util.js"></script>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var orgtreeconfig = {
		//render : false,
		render : false,
		id : 'orgTree',
		nheight : 42,
		url : 'usersystem-Org-queryOrgAndUsersList',
		onClick : function(node) {
			if(node.id=='default'){
				//Dialog.infomsg('请输入内容！');
				return;
			}
			clickMenu(node);
		},
		rightMenu : [ {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('orgTree');
			}
		} ],
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
					id : 'e9fa8689-c362-4c66-bd75-d1b132bd5211',
					text : '个人邮件',
					vsj : 'jsp-message-email-emailmain?type=write',
					params : {
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
		}, {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('orgTree');
			}
		} ]
	}
	
	var messConfig = {
			render:false,
			onClick : function(data){
				data.li.find('.hh_red').remove();
				clickMenu(data);
			}
	}
	
	function msg(msg){
		$('#himessageDiv').html('<h1>'+msg+'</h1>');
		$('#backbtn').disabled();
	}
	
	function clickMenu(data){
		data.sendObjectType = data.sendObjectType ==null ? data.lx_ : data.sendObjectType ;
		
		$('#backbtn').undisabled();
		if(data.sendObjectType!=0 && data.sendObjectType!=6 && data.sendObjectType!=7){
			if(data.id!=loginUser.orgId && data.id!=loginUser.deptId){
				msg('您不在本机构/本部门');
				$('#backbtn').disabled();
				return;
			}
		}
		
		
		var objectMap = $.hh.listToObject($('#messDivspan').data('data'));
		if(objectMap[data.id]==null){
			data.addCylxr = 1;
		}else{
			data.addCylxr = 0;
		}
		$('#userdiv').html(
				'<div style="margin-top:5px;">' + data.text + '</div>');
		$('#userdiv').data('data', data);
		
		var limit = 30;
		var page = 1;
		var start =  limit * page - limit;
		
		Request.request('message-SysMessage-queryMyPagingDataBySendObjectId', {
			data : {
				limit:limit,
				page:page,
				start:start,
				toObjectId : data.id,
				sendObjectType :  data.sendObjectType
			},
			defaultMsg : false
		}, function(dataList) {
			 loadData(dataList);
		});
		
		
	}
	
	function loadData(dataList){
		$('#himessageDiv').empty();
		for(var i=dataList.length-1;i>-1;i--){
			var data =dataList[i];
			data.date = $.hh.dateTimeToString(data.dcreate);
			data.message = data.content;
			data.type = data.sendUserId == loginUser.id ? 'my' : 'you';
			data.sendHeadpic = data.sendHeadpic;
			appendMessage(data,1);
		}
		$('#himessagediv').animate({scrollTop:5000},100);
	}

	var grouprender = false;
	var orgrender = false;
	var messrender = false;
	var tabconfig = {
		activate : function(ui) {
			var newPanel = ui.newPanel;
			var id = newPanel.attr('id');
			if (id == 'grooupDiv' && grouprender == false) {
				grouprender = true;
				$('#span_groupTree').render();
			}else if (id == 'orgDiv' && orgrender == false) {
				orgrender = true;
				$('#span_orgTree').render();
			}else if (id == 'orgDiv' && orgrender == false) {
				messrender = true;
				$('#messDivspan').render();
			}
		}
	}

	var groupTreeConfig = {
		id : 'groupTree',
		nheight : 42,
		url : 'usersystem-Group-queryListAndUserGroup',
		render : false,
		rightMenu : [ {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('groupTree');
			}
		} ],
		itemRightMenu : [ {
			text : '发送邮件',
			img : $.hh.property.img_email,
			onClick : function(data) {
				var userids = '';
				var usernames = '';
				if (data.type != 'user') {
					Request.request('usersystem-user-queryUserByGroup', {
						data : {
							groupId : data.id
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
					id : 'e9fa8689-c362-4c66-bd75-d1b132bd5211',
					text : '个人邮件',
					vsj : 'jsp-message-email-emailmain?type=write',
					params : {
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
		}, {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('groupTree');
			}
		} ],
		onClick : function(node) {
			if(node.type=='zdy'){
				msg('请选择组或人员');
				return;
			}
			if(node.type=='usgroup'){
				node.sendObjectType=6;
			}
			if(node.type=='sysgroup'){
				node.sendObjectType=7;
			}
			if(node.type=='user'){
				node.sendObjectType=0;
				if(loginUser.id==node.id){
					msg('不能选择本人');
					return;
				}
			}
			if(node.meGroup==0 && node.sendObjectType!=0){
				msg('您不在本组');
				return;
			}
			clickMenu(node);
		}
	}

	function onPageLoad() {
		var userId = loginUser.id;
		var userName = loginUser.text;
		var deptId = loginUser.deptId;
		var orgId = loginUser.orgId;
		message.onPageLoad($.hh.toString({
			'userId' : userId,
			'userName' : userName,
			'orgId' : orgId,
			'deptId' : deptId
		}));
	}
	function showMessage(autoMessage) {
		var result = $.hh.toObject(autoMessage);
		var message = result.message;
		if (message && message.message) {
			renderMsg(message);
		}
		
		var allMessage = result.allMessage;
		if (allMessage) {
			renderAllMessage(allMessage);
		}
	}
	
	function renderAllMessage(allMessage){
		for(var i =0;i<allMessage.length;i++){
			if(allMessage[i].count){
				allMessage[i].rightText = '<font class="hh_red">'+allMessage[i].count+'</font>';
			}
		}
		$('#messDivspan').render({data: allMessage});
	}
	
	
	function renderMsg(message){
		var selectData = $('#userdiv').data('data');
		if(selectData && (message.objectId == selectData.id )){
			appendMessage(message);
		}else{
			//console.log(message);
		}
	}

	function openEmail(params) {
		$.hh.getRootFrame().addTab(params);
	}

	function getMyMsg(config) {
		var message = config.message || '';
		var date = config.date || '';
		var type = config.type;
		
		
		var text = config.sendUserName;
		var headpic = config.sendHeadpic;
		
		
		
		if(headpic &&  headpic.indexOf('hhcomm')==-1){
	    	headpic =	"system-File-download?params={id:'"+headpic+"'}";
	    }
		
		if(headpic){
	    	headpic=" <img id='headpicimg' onClick='updateUser()' style=\"cursor: pointer;\" width=\"50\"		height=\"50\"		src=\""+headpic+"\" />";
	    }else{
	    	headpic="<img id='headpicimg' onClick='updateUser()' style=\"cursor: pointer;\" width=\"50\"		height=\"50\"		src=\"/hhcommon/images/icons/user/100/no_on_line_user.jpg\" />";
	    }
		
		var tdStr = '';

		var align = 'right';
		var backColor = '#d4eede';
		if (type == 'my') {
			align = 'left';
			backColor = '#bdeea3';
		}

		var td1 = '<td style="width: 40px; text-align: center;">'+headpic+' <br />'
				+ text+'</td>';
		var td2 = '<td>'
				+ '<div style="-moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px; background-color: '+backColor+'; width: 340px; height: auto; display: block; padding: 5px; float: '+align+'; color: #333333;table-layout:fixed;word-wrap: break-word; word-break: break-all;">'
				+ '<font style="font-size: 14px;"><b>' + message
				+ '</b></font>' + '<br />'
				+ '<div style="padding-top: 6px; color: #a9b4a2;">' + date
				+ '</div>' + '</div>' + '</td>';
		if (type == 'my') {
			tdStr = td1 + td2;
		} else {
			tdStr = td2 + td1;
		}
		return '<table style="width: 100%;">' + '<tr>' + tdStr + '</tr>'
				+ '</table>';
	}

	function init() {
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true, true);
		
		dwr.engine.setErrorHandler(function() {
		});
		onPageLoad();
		
		 if ($.hh.browser.type.indexOf('IE') > -1) {
			 mysetInterval = setInterval(keepSession, 1000 * 60*4);
		} 
	}
	
	function keepSession(){
		message.sendMessageAuto("");
	}

	function setHeight(height) {
		$('#himessagediv').height(height - 218);
	}
	
	function doSendMessage() {
		var data = $('#userdiv').data('data');
		if(data){
			var messageData = {};
			messageData.message = $('#messageSpan').getValue();
			if(!messageData.message){
				Dialog.infomsg('请输入内容！');
				return;
			}
			
			messageData.sendUserId = loginUser.id;
			messageData.sendUserName = loginUser.text;
			messageData.sendHeadpic =  loginUser.headpic;
			
			messageData.toObjectHeadpic =data.headpic;
			messageData.toObjectId =data.id;
			messageData.toObjectName =data.text;
			
			messageData.sendObjectType =data.sendObjectType;
			
			messageData.date = $.hh.dateTimeToString($.hh.getDate());
			messageData.type='my';
			
			messageData.addCylxr=data.addCylxr;
			
			
			appendMessage(messageData);
			
			
			message.sendMessageAuto($.hh.toString(messageData));
			$('#messageSpan').setValue('');
		}else{
			Dialog.infomsg('请选择发送对象！');
		}
	}
	
	function appendMessage (data,isTop){
		$('#himessageDiv').append(getMyMsg(data));
		if(!isTop){
			$('#himessagediv').animate({scrollTop:5000},100);
		}
	}

</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west' ,width:260 ">
			<div id="tabs" xtype="tab" configVar="tabconfig">
				<ul>
					<li><a href="#messDiv">消息</a></li>
					<li><a href="#orgDiv">机构</a></li>
					<li><a href="#grooupDiv">组</a></li>
				</ul>
				<div id="messDiv">
					<span xtype=menu id="messDivspan" configVar=" messConfig "></span>
				</div>
				<div id="orgDiv">
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
					<div id="userdiv" xtype="toolbar" config="type:'head'"
						style="text-align: center; height: 28px; vertical-align: middle;">
					</div>
					<div id="himessagediv"
						style="padding: 10px; overflow-y: auto; overflow-x: hidden;">

						<div id="himessageDiv"></div>

					</div>
				</div>
				<div config="render : 'south' ,width:160,spacing_open:0 ">
					<span id="messageSpan"
						config=" height:81,bottom:'hidden', name:'message' ,toolbar : ['Format',	'Font', 'FontSize', 'Styles'] "
						xtype="ckeditor"></span>
					<div xtype="toolbar" config="type:'head'"
						style="text-align: right;">
						<span id="backbtn" xtype="button"
							config="onClick: doSendMessage ,text:'发送'   "></span>
					</div>
				</div>
			</div>
		</div>
		
		
		<div config="render : 'east' ,width:332 , overflow : 'hidden' ">
			<iframe id='eastiframe'  frameborder=0  width=100% height=100% src='jsp-usersystem-menu-zmtb' /> 
		</div>
	</div>

</body>
</html>