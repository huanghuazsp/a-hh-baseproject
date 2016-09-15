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
<%=SystemUtil.getBaseJs("layout", "ztree", "ckeditor", "workflow")
					+ SystemUtil.getUser()%>
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
			if (node.id == 'default') {
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

				$.hh.addTab(data)

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
		render : false,
		onClick : function(data) {
			data.li.find('.hh_red').remove();
			clickMenu(data);
		},
		rightMenu : [ {
			text : '删除',
			img : $.hh.property.img_delete,
			onClick : function(resultObject) {
				Request.request('usersystem-user-deleteCylxr', {
					data : {
						'paramsMap.cylxrId' : resultObject.id
					}
				},function(){
					resultObject.content.remove();
				});
			}
		} ,{
			text : '上移',
			imgClass : 'hh_up',
			onClick : function(resultObject) {
				var content = resultObject.content;
				var pretr = content.prev('li');
				if (pretr && pretr.length>0) {
					Request.request('usersystem-user-orderCylxr', {
						data : {
							id1 : resultObject.id,
							id2 : pretr.data('data').id
						}
					},function(){
						content.insertBefore(pretr);
					});
				}else{
					Dialog.infomsg('已经是第一条了');
				}
			}
		} ,{
			text : '下移',
			imgClass : 'hh_down',
			onClick : function(resultObject) {
				var content = resultObject.content;
				var pretr = content.next('li');
				if (pretr && pretr.length>0) {
					Request.request('usersystem-user-orderCylxr', {
						data : {
							id1 : resultObject.id,
							id2 : pretr.data('data').id
						}
					},function(){
						content.insertAfter(pretr);
					});
				}else{
					Dialog.infomsg('已经是最后一条了');
				}
			}
		} ]
	}

	function msg(msg) {
		$('#himessageDiv').html('<h1>' + msg + '</h1>');
		$('#sendbtn').disabled();
	}
	
	var indexpage = 1;

	function clickMenu(data) {
		data.sendObjectType = data.sendObjectType == null ? data.lx_
				: data.sendObjectType;
		$('#sendbtn').undisabled();
		if (data.sendObjectType != 0 && data.sendObjectType != 6
				&& data.sendObjectType != 7 && data.sendObjectType != 11 && data.sendObjectType != 12) {
			if (data.id != loginUser.orgId && data.id != loginUser.deptId) {
				msg('您不在本机构/本部门');
				$('#sendbtn').disabled();
				return;
			}
		}
		if(data.sendObjectType>10){
			$('#sendbtn').disabled();
		}
		var objectMap = $.hh.listToObject($('#messDivspan').data('data'));
		if (objectMap[data.id] == null) {
			data.addCylxr = 1;
		} else {
			data.addCylxr = 0;
		}
		$('#userdiv').html(
				'<div style="margin-top:5px;"><a href="javascript:viewUser('+data.sendObjectType+',\''+data.id+'\');">' + data.text + '</a><span style="float:right;"><a href="javascript:openHi(\''+data.id+'\',\''+data.sendObjectType+'\',\''+data.text+'\')">查看历史记录</a></span></div>');
		$('#userdiv').data('data', data);
		indexpage=1;
		requestDataLoad(data);
	}
	
	function openHi(id,sendObjectType,text){
		var data = {
				id : 'message_'+id,
				text : text+'-聊天',
				vsj : 'jsp-message-message-messagelist?toObjectId='+id+'&sendObjectType='+sendObjectType
		};
		$.hh.addTab(data);
	}
	
	function requestDataLoad(data){
		var limit = 30;
		var start = limit * indexpage - limit;
		Request.request('message-SysMessage-queryMyPagingDataBySendObjectId', {
			data : {
				limit : limit,
				page : indexpage,
				start : start,
				toObjectId : data.id,
				sendObjectType : data.sendObjectType
			},
			defaultMsg : false
		}, function(dataList) {
			loadData(dataList);
		});
	}
	
	function more(){
		indexpage++;
		requestDataLoad($('#userdiv').data('data'));
	}

	function loadData(dataList) {
		if(indexpage==1){
			$('#himessageDiv').empty();
			$('#himessageDiv').append('<div style="text-align:center;"><a href="javascript:more();">查看更多...</a></div>');
		}
		if(indexpage==1){
			for (var i = dataList.length - 1; i > -1; i--) {
				var data = dataList[i];
				addData(data);
			}
		}else{
			for (var i =0; i <dataList.length; i++) {
				var data = dataList[i];
				addData(data);
			}
		}
		
		if(indexpage>1){
			$('#himessageDiv').prepend('<div style="text-align:center;"><a href="javascript:more();">查看更多...</a></div>');
		}else{
			scroll1();
		}
		
	}
	
	function addData(data){
		data.date = $.hh.dateTimeToString(data.dcreate);
		data.message = data.content;
		data.type = data.sendUserId == loginUser.id ? 'my' : 'you';
		data.sendHeadpic = data.sendHeadpic;
		data.indexpage = indexpage;
		appendMessage(data, 1);
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
			} else if (id == 'orgDiv' && orgrender == false) {
				orgrender = true;
				$('#span_orgTree').render();
			} else if (id == 'orgDiv' && orgrender == false) {
				messrender = true;
				renderMessDivspan();
			}
		}
	}
	
	function renderMessDivspan(data){
		$('#messDivspan').removeAttr('rightload');
		if(data){
			$('#messDivspan').render(data);
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

				$.hh.addTab(data)

			}
		}, {
			text : '刷新',
			img : $.hh.property.img_refresh,
			onClick : function() {
				$.hh.tree.refresh('groupTree');
			}
		} ],
		onClick : function(node) {
			if (node.type == 'zdy') {
				msg('请选择组或人员');
				return;
			}
			if (node.type == 'usgroup') {
				node.sendObjectType = 6;
			}
			if (node.type == 'sysgroup') {
				node.sendObjectType = 7;
			}
			if (node.type == 'user') {
				node.sendObjectType = 0;
				if (loginUser.id == node.id) {
					msg('不能选择本人');
					return;
				}
			}
			if (node.meGroup == 0 && node.sendObjectType != 0) {
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
		var usGroupIds = loginUser.usGroupIds;
		var sysGroupIds = loginUser.sysGroupIds;
		message.onPageLoad($.hh.toString({
			'userId' : userId,
			'userName' : userName,
			'orgId' : orgId,
			'deptId' : deptId,
			'usGroupIds' : usGroupIds,
			'sysGroupIds' : sysGroupIds
		}));
	}

	function showMessage2(result) {
		var message = result.message;
		if (message && message.message) {
			renderMsg(message);
		}

		var allMessage = result.allMessage;
		if (allMessage) {
			renderAllMessage(allMessage);
		}
	}

	function showMessage(autoMessage) {
		if (autoMessage) {
			var result = $.hh.toObject(autoMessage);
			showMessage2(result);
		} else {
			if ($.hh.browser.type.indexOf('IE') > -1) {
				$('#himessageDiv').append(new Date() + '<br>');
				scroll1();
			}
		}
	}

	function renderAllMessage(allMessage) {
		for (var i = 0; i < allMessage.length; i++) {
			if (allMessage[i].count) {
				allMessage[i].rightText = '<font class="hh_red">'
						+ allMessage[i].count + '</font>';
			}
			convertMenuImg(allMessage[i]);
		}
		renderMessDivspan({
			data : allMessage
		});
	}
	
	function convertMenuImg(data){
		if(data.sendObjectType==0){
			var headpic = data.headpic || '/hhcommon/images/icons/user/100/no_on_line_user.jpg';
			if (data.headpic && data.headpic.indexOf("/hhcomm")==-1) {
				headpic = "system-File-download?params={id:'" + headpic + "'}";
			}
			data.img=headpic
		}else if(data.sendObjectType==6 || data.sendObjectType==7){
			data.img='/hhcommon/images/icons/group/group.png';
		}else if(data.sendObjectType==2){
			data.img='/hhcommon/images/icons/user/group.png';
		}else if(data.sendObjectType==1){
			data.img='/hhcommon/images/myimage/org/org.png';
		}else if(data.sendObjectType==11){
			data.img='/hhcommon/images/icons/email/email.png';
		}else if(data.sendObjectType==12){
			data.img='/hhcommon/images/extjsico/txt.gif';
		}
		
	}

	function renderMsg(message) {
		var selectData = $('#userdiv').data('data');
		if (selectData && (message.objectId == selectData.id)) {
			appendMessage(message);
		} else {
			var li = $('#messDivspan').find('[liid=' + message.objectId + ']');
			if (li.length > 0) {
				var count = $.hh.toInt(li.find('[litype=rightText]').text());
				var rightText = '<font class="hh_red">' + (count + 1)
						+ '</font>';
				li.find('[litype=rightText]').html(rightText);
			} else {
				var ul = $('#messDivspan').find('ul');

				var objectId = '';
				var objectName = '';
				var objectHeadpic = '';

				if (message.sendObjectType == 0) {
					objectId = message.sendUserId;
					objectName = message.sendUserName;
					objectHeadpic = message.sendHeadpic;
				} else {
					objectId = message.toObjectId;
					objectName = message.toObjectName;
					objectHeadpic = message.toObjectHeadpic;
				}

				var data = {
					id : objectId,
					text : objectName,
					headpic : objectHeadpic,
					sendObjectType : message.sendObjectType,
					rightText : '<font class="hh_red">1</font>'
				};
				
				convertMenuImg(data);

				var dataList = $('#messDivspan').data('data');
				dataList.unshift(data);
				renderMessDivspan({
					data : dataList
				});

				Request.request('usersystem-user-addCylxrObject', {
					data : {
						'paramsMap.yhId' : loginUser.id,
						'paramsMap.cylxrId' : data.id,
						'paramsMap.cylxrName' : data.text,
						'paramsMap.headpic' : data.headpic,
						'paramsMap.type' : data.sendObjectType
					},
					defaultMsg : false
				});

			}
		}
	}

	function openEmail(params) {
		$.hh.addTab(params);
	}
	
	function openTask(params) {
		WF.manager({
			id : params,
			actionType : 'manager'
		});
	}

	function openEmail(params){
		$.hh.addTab({
			id : 'e9fa8689-c362-4c66-bd75-d1b132bd5211',
			text :  '个人邮件',
			src : 'jsp-message-email-emailmain?type=shouemail&id=' +params
		});
	}
	
	function getMyMsg(config) {
		var message = config.message || '';
		var date = config.date || '';
		var type = config.type;

		var text = config.sendUserName;
		var headpic = config.sendHeadpic;
		var userId = config.sendUserId;
		
		
		if(config.sendObjectType==11 && config.params){
			message = '<a href="javascript:openEmail(\''+config.params+'\')">'+message+'</a>';
		}else if(config.sendObjectType==12 && config.params){
			message = '<a href="javascript:openTask(\''+config.params+'\')">'+message+'</a>';
		}

		if (headpic && headpic.indexOf('hhcomm') == -1) {
			headpic = "system-File-download?params={id:'" + headpic + "'}";
		}

		if (headpic) {
			headpic = " <img id='headpicimg' onClick='viewUser(0,\""+userId+"\");' style=\"cursor: pointer;\" width=\"50\"		height=\"50\"		src=\""
					+ headpic + "\" />";
		} else {
			headpic = "<img id='headpicimg' onClick='viewUser(0,\""+userId+"\");' style=\"cursor: pointer;\" width=\"50\"		height=\"50\"		src=\"/hhcommon/images/icons/user/100/no_on_line_user.jpg\" />";
		}

		var tdStr = '';

		var align = 'right';
		var backColor = '#d4eede';
		if (type != 'my') {
			align = 'left';
			backColor = '#bdeea3';
		}

		var td1 = '<td style="width: 40px; text-align: center;">' + headpic
				+ ' <br />' + text + '</td>';
		var td2 = '<td>'
				+ '<div style="-moz-border-radius: 5px; -webkit-border-radius: 5px; border-radius: 5px; background-color: '+backColor+'; width: 340px; height: auto; display: block; padding: 5px; float: '+align+'; color: #333333;table-layout:fixed;word-wrap: break-word; word-break: break-all;">'
				+ '<font style="font-size: 14px;"><b>' + message
				+ '</b></font>' + '<br />'
				+ '<div style="padding-top: 6px; color: #a9b4a2;">' + date
				+ '</div>' + '</div>' + '</td>';
		if (type != 'my') {
			tdStr = td1 + td2;
		} else {
			tdStr = td2 + td1;
		}
		return '<table style="width: 100%;">' + '<tr>' + tdStr + '</tr>'
				+ '</table>';
	}

	function init() {
		//dwrInit();

		messageInit();

	}

	function errorLoad() {
		Dialog.alert('推送失败需要重新加载页面！', function() {
			$.hh.getRootFrame().location.reload();
		});
	}

	var timeoutTime = 3;
	var errorlength = 0;
	function messageInit() {
		$.ajax({
			type : "POST",
			url : "message-SysMessage-poll",
			dataType : "json",
			timeout : 70000, //超时时间,设置为60s.
			data : {
				timeout : timeoutTime
			},
			success : function(result) {
				if (timeoutTime == 3) {
					timeoutTime = 60;
					renderAllMessage(result.allMessage)
				}
				for (var i = 0; i < result.length; i++) {
					showMessage2(result[i]);
				}
				messageInit();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (errorlength > 6) {
					errorLoad();
				} else {
					setTimeout(messageInit, 2000)
				}
				errorlength++;
			}
		});
	}

	function sendMessage(message) {
		$.ajax({
			type : "POST",
			url : "message-SysMessage-sendMessage",
			dataType : "json",
			data : message,
			success : function() {
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}

	function dwrInit() {
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true, true);

		dwr.engine.setErrorHandler(function() {
			alert('消息推送异常！');
		});
		onPageLoad();
		if ($.hh.browser.type.indexOf('IE') > -1) {
			//mysetInterval = setInterval(keepSession, 1000 * 60 * 4);
		}
	}

	function keepSession() {
		message.sendMessageAuto("");
		Request.request('usersystem-System-loadDataTime', {
			doing : false,
			callback : function(result) {
				$('#himessageDiv').append('dddd' + (new Date()) + '<br>');
			}
		});
	}

	function setHeight(height) {
		$('#himessagediv').height(height - 218);
	}

	function doSendMessage() {
		var data = $('#userdiv').data('data');
		if (data) {
			var messageData = {};
			messageData.message = $('#messageSpan').getValue();
			if (!messageData.message) {
				Dialog.infomsg('请输入内容！');
				return;
			}

			messageData.sendUserId = loginUser.id;
			messageData.sendUserName = loginUser.text;
			messageData.sendHeadpic = loginUser.headpic;

			messageData.toObjectHeadpic = data.headpic;
			messageData.toObjectId = data.id;
			messageData.toObjectName = data.text;

			messageData.sendObjectType = data.sendObjectType;

			messageData.date = $.hh.dateTimeToString($.hh.getDate());
			messageData.type = 'my';
			messageData.addCylxr = data.addCylxr;
			appendMessage(messageData);

			sendMessage(messageData);

			//message.sendMessageAuto($.hh.toString(messageData));

			$('#messageSpan').setValue('');
		} else {
			Dialog.infomsg('请选择发送对象！');
		}
	}

	function scroll1(){
		$('#himessagediv').animate({
			scrollTop : $('#himessagediv')[0].scrollHeight+ 500
		}, 100);
	}
	
	function appendMessage(data, isTop) {
		if(data.indexpage>1){
			$('#himessageDiv').prepend(getMyMsg(data));
		}else{
			$('#himessageDiv').append(getMyMsg(data));
		}
		if (!isTop) {
			scroll1();
		}
	}
	
	function viewUser(type,id){
		if(type==0){
			Dialog.open({
				url : 'jsp-usersystem-user-useredit',
				urlParams : {
					id : id,
					view : 1
				}
			});
		}
	}
	
	function renderAllQuickMenu(timeData){
		if(window.frames['eastiframe'] && window.frames['eastiframe'].renderAllQuickMenu){
			window.frames['eastiframe'].renderAllQuickMenu(timeData);
		}
	}
</script>
</head>
<body xtype="border_layout">
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
			<div config="render : 'south' ,width:160,spacing_open:0, overflow : 'hidden'  ">
				<span id="messageSpan"
					config=" height:81,bottom:'hidden', name:'message' ,toolbar : ['Format',	'Font', 'FontSize', 'Styles'] "
					xtype="ckeditor"></span>
				<div xtype="toolbar" config="type:'head'" style="text-align: right;">
					<span id="sendbtn" xtype="button"
						config="onClick: doSendMessage ,text:'发送' , disabled :true  "></span>
				</div>
			</div>
		</div>
	</div>


	<div config="render : 'east' ,width:332 , overflow : 'hidden' ">
		<iframe id='eastiframe' name='eastiframe'  frameborder=0 width=100% height=100%
			src='jsp-usersystem-menu-zmtb' />
	</div>

</body>
</html>