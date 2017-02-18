<%@page import="com.hh.system.util.Convert"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.pk.PrimaryKey"%>
<%=SystemUtil.getBaseDoctype()+SystemUtil.getUser()%>

<html>
<head>
<title>数据列表</title>
<style type="text/css">
.ztree li span.button.task_ico_docu{margin-right:2px; background: url(/hhcommon/images/icons/task/task.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.task_ico_open{margin-right:2px; background: url(/hhcommon/images/icons/task/task.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.task_ico_close{margin-right:2px; background: url(/hhcommon/images/icons/task/task.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

.ztree li span.button.folder_ico_docu{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.folder_ico_open{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.folder_ico_close{margin-right:2px; background: url(/hhcommon/images/icons/folder/folder.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}

</style>
<%=SystemUtil.getBaseJs("layout","ztree", "ztree_edit")%>
<%String iframeId = PrimaryKey.getUUID();%>

<script type="text/javascript">
	var width = 950;
	var height = 650;
	var iframeId = '<%=iframeId%>';
	var projectId = '<%=Convert.toString(request.getParameter("projectId"))%>';
	var oper = '<%=Convert.toString(request.getParameter("oper"))%>';
	function doAdd() {
		$('#centerdiv').undisabled();
		var selectNode = $.hh.tree.getSelectNode('tree');
		var iframe = window.frames[iframeId];
		iframe.callback = function() {
			$.hh.tree.refresh('tree');
			$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
		}
		if (selectNode) {
			iframe.newData({
				node : selectNode.id
			});
		} else {
			iframe.newData({});
		}
		return;
		Dialog.open({
			url : 'jsp-project-projectmodular-ProjectModularEdit',
			params : {
				selectNode : selectNode,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doEdit(treeNode) {
		Dialog.open({
			url : 'jsp-project-projectmodular-ProjectModularEdit',
			params : {
				object : treeNode,
				callback : function() {
					$.hh.tree.refresh('tree');
				}
			}
		});
	}
	function doDelete(treeNode) {
		$.hh.tree.deleteData({
			pageid : 'tree',
			action : 'project-ProjectModular-deleteTreeByIds',
			id : treeNode.id,
			callback : function(result) {
				if (result.success!=false) {
					$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
				}
			}
		});
	}
	function treeClick(treeNode) {
		$('#centerdiv').undisabled();
		var iframe = window.frames[iframeId];
		iframe.callback = function(object) {
			treeNode.name = object.text;
			$.hh.tree.updateNode('tree', treeNode);
			$.hh.tree.getTree('tree').refresh();
		}
		var oper_ = true;
		if(loginUser.id!=treeNode.createUser && oper!='all'){
			oper_ = false;
		}
		if(oper=='false'){
			oper_ = false;
		}
		iframe.findData(treeNode.id,oper_);
	}
	
	function showRemoveBtn(treeId, treeNode) {
		if(oper=='false'){
			return false;
		}
		var oper_ = true;
		if(loginUser.id!=treeNode.createUser && oper!='all'){
			oper_ = false;
		}
		return oper_;
	}
	function init(){
		if(oper=='false'){
			$('#leftDiv').hide();
		}
		
		$('#centerdiv').disabled('请选择要编辑的树节点或添加新的数据！');
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west'">
			<div xtype="toolbar" config="type:'head'" id="leftDiv">
				<span xtype="button" config="onClick: doAdd ,text:'添加'"></span> <span
					xtype="button"
					config="onClick: $.hh.tree.doUp , params:{treeid:'tree',action:'project-ProjectModular-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: $.hh.tree.doDown , params:{treeid:'tree',action:'project-ProjectModular-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'tree'  "></span>
			</div>
			<span xtype="tree"
				config=" id:'tree', url:'project-ProjectModular-queryTreeList' ,remove: doDelete , onClick : treeClick , params :{projectId:projectId},showRemoveBtn:showRemoveBtn ,nheight:42"></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="<%=iframeId%>" name="<%=iframeId%>" width=100%
				height=100% frameborder=0 src="jsp-project-projectmodular-ProjectModularEdit?projectId=<%=Convert.toString(request.getParameter("projectId"))%>"></iframe>
		</div>
	</div>
</body>
</html>