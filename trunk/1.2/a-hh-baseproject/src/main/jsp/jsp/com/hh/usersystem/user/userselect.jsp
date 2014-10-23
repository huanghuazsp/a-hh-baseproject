<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>用户选择</title>
<%=SystemUtil.getBaseJs("layout", "ztree")%>
<script type="text/javascript"
	src="/hhcommon/opensource/jquery/layout/jquery.layout.resizePaneAccordions-latest.js"></script>
<script type="text/javascript">
	var params = BaseUtil.getIframeParams();

	var hiddenField = params.hiddenField;
	var textField = params.textField;
	var selectUserList = [];
	var pagelistobject = null;
	var pageconfig = {
		toolbarList : [
				'<span xtype="button" config=" icon :\'hh_img_query\' , onClick : query "></span>',
				'<span xtype="button" config="text:\'确定\' , icon :\'hh_img_green\' , onClick : ok "></span>' ],
		itemchange : itemchange,
		dataLoad : dataLoad,
		toolbarType : 'min',
		radio : params.config.radio,
		url : 'usersystem-user-queryPagingData',
		params : {
			nxb : 2
		},
		column : [ {
			name : 'text',
			text : '请选择',
			render : function(value, data) {
				return '<img src="/hhcommon/opensource/ext/shared/icons/fam/'
						+ (data.nxb == 1 ? 'user' : 'user_female')
						+ '.gif" />&nbsp;' + value;
			}
		} ]
	};

	var ids = hiddenField.val();
	var texts = textField.val();
	var idsarr = ids.split(",");
	var textsarr = texts.split(",");
	for (var i = 0; i < idsarr.length; i++) {
		if (idsarr[i] != null && idsarr[i] != '') {
			selectUserList.push({
				id : idsarr[i],
				text : textsarr[i]
			});
		}
	}

	function dataLoad() {
		pagelistobject.selectRow('id', selectUserList);
	}
	function itemchange(data) {
		var checked = data.checked;
		var rowdata = data.rowdata;
		if (data.clickDom == 'checkbox') {
			if (checked) {
				addSelectUserList(rowdata);
			} else {
				removeSelectUserList(rowdata.id);
			}
		} else {
			if (checked) {
				selectUserList = [ rowdata ];
				renderSpanUserHtml();
			} else {
				removeSelectUserList(rowdata.id);
			}
		}
	}

	function init() {
		pagelistobject = $('#pagelist').getWidgetObject();
		renderSpanUserHtml();
	}

	function ok() {
		if (selectUserList.length > 0) {
			var id = "";
			var text = "";
			for (var i = 0; i < selectUserList.length; i++) {
				var selectUser = selectUserList[i];
				id += selectUser.id + ",";
				text += selectUser.text + ",";
			}
			if (id != "") {
				id = id.substr(0, id.length - 1);
				text = text.substr(0, text.length - 1);
			}
			hiddenField.val(id);
			textField.val(text);
			Dialog.close();
			textField.focus();
		} else {
			Dialog.infomsg("请选中一条数据！！");
		}
	}

	function renderSpanUserHtml() {
		var html = "";
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			html += '&nbsp;<span >' + selectUser.text
			html += '<img src="/hhcommon/images/extjsico/delete2.gif" onclick="unSelect(\''
					+ selectUser.id + '\')" /></span>&nbsp;|';
		}
		$('#userspan').html(html);
	}
	function removeSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser.id != id) {
				userList.push(selectUser);
			}
		}
		selectUserList = userList;
		renderSpanUserHtml();
	}

	function isInSelectUserList(id) {
		var userList = [];
		for (var i = 0; i < selectUserList.length; i++) {
			var selectUser = selectUserList[i];
			if (selectUser.id == id) {
				return true;
			}
		}
		return false;
	}
	function unSelect(id) {
		pagelistobject.unSelectRow('id', id);
		removeSelectUserList(id);
	}
	function addSelectUserList(object) {
		if (pageconfig.radio) {
			selectUserList = [ {
				id : object.id,
				text : object.text
			} ];
			renderSpanUserHtml();
		} else {

			var isin = isInSelectUserList(object.id);
			if (isin == false) {
				selectUserList.push({
					id : object.id,
					text : object.text
				});
				renderSpanUserHtml();
			}
		}
	}

	function loadUserList(result) {
		var ids = BaseUtil.objsToStr(result);
		if (ids == '') {
			ids = '0';
		}
		$('#pagelist').loadData({
			params : {
				ids : ids,
				nxb : 2
			}
		});
	}

	function orgTreeClick(treeNode) {
		$('#pagelist').loadData({
			params : {
				orgs : treeNode.code_,
				nxb : 2
			}
		});
		//Request.request('usersystem-user-queryUserByOrcCode', {
		//	data : {
		//		code : treeNode.code_
		//	}
		//}, function(result) {
		//	loadUserList(result);
		//});
	}

	function roleitemclick(data) {
		$('#pagelist').loadData({
			params : {
				roles : data.rowdata.id,
				nxb : 2
			}
		});
		//Request.request('usersystem-user-queryUserByRole', {
		//	data : {
		//		roleId : data.rowdata.id
		//	}
		//}, function(result) {
		//	loadUserList(result);
		//});
	}

	function groupitemclick(data) {
		$('#pagelist').loadData({
			params : {
				groups : data.id,
				nxb : 2
			}
		});
		//Request.request('usersystem-user-queryUserByGroup', {
		//	data : {
		//		groupId : data.rowdata.id
		//	}
		//}, function(result) {
		//	loadUserList(result);
		//});
	}

	function query() {
		$('#pagelist').loadData({
			params : {
				text : $('#span_text').getValue(),
				nxb : 2
			}
		});
	}

	var rolerender = false;
	var grouprender = false;

	function activate(ui) {
		var newPanel = ui.newPanel;
		var id = newPanel.attr('id');
		if (id == 'rloediv' && rolerender == false) {
			rolerender = true;
			$('#role_pagelist').render();
		} else if (id == 'groupdiv' && grouprender == false) {
			grouprender = true;
			$('#group_pagelist').render();
		}
	}
</script>
</head>
<body xtype="border_layout">
	<div config="render : 'west' ,width : 260 ">
		<div xtype="accordion" config="activate : activate">
			<h3>机构</h3>
			<div>
				<span xtype="tree"
					config=" id:'orgTree', url:'usersystem-Org-queryTreeList' , onClick : orgTreeClick  "></span>
			</div>
			<h3>角色</h3>
			<div id="rloediv">
				<div id="role_pagelist" xtype="pagelist"
					config=" params : {nzt:1} , url: 'usersystem-role-queryPagingData' ,toolbarType : 'min', radio:true , itemclick : roleitemclick , column : [
					{
						name : 'text' ,
						text : '名称'
					}
				], title : false ,render:false">
				</div>
			</div>
			<h3>用户组</h3>
			<div id="groupdiv">
				<span id="group_pagelist" xtype="tree"
					config=" id:'groupTree', url:'usersystem-Group-queryTreeList'  , onClick : groupitemclick ,render:false "></span>
			</div>
		</div>
	</div>
	<div>
		<span id="userspan"></span>
		<hr />
		&nbsp;&nbsp;<span xtype="text" config=" name : 'text' "></span>
		<hr />
		<div id="pagelist" xtype="pagelist" configVar="pageconfig"></div>
	</div>
</body>
</html>