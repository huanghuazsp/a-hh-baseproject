<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>用户组管理</title>
<%=SystemUtil.getBaseJs("layout", "ztree", "ztree_edit")%>
<script type="text/javascript">
	function doAdd() {
		$('#centerdiv').undisabled();
		var selectNode = TreeUtil.getSelectNode('groupTree');
		var iframe = window.frames['orgeditiframe'];
		iframe.callback = function() {
			TreeUtil.refresh('groupTree');
			$('#centerdiv').disabled('请选择要编辑的用户组或添加新的数据！');
		}
		if (selectNode) {
			iframe.newData({
				node : selectNode.leaf == 0 ? selectNode.id : ''
			});
		} else {
			iframe.newData({});
		}
	}
	function doDelete(treeNode) {
		TreeUtil.deleteData({
			pageid : 'groupTree',
			action : 'usersystem-Group-deleteTreeByIds',
			id : treeNode.id,
			callback : function(result) {
				if (result.success) {
					$('#centerdiv').disabled('请选择要编辑的用户组或添加新的数据！');
				}
			}
		});
	}
	function orgTreeClick(treeNode) {
		$('#centerdiv').undisabled();
		var iframe = window.frames['orgeditiframe'];
		iframe.callback = function(object) {
			treeNode.name = object.text;
			treeNode.isParent = object.leaf == 0;
			TreeUtil.updateNode('groupTree', treeNode);
			TreeUtil.getTree('groupTree').refresh();
			$('#centerdiv').disabled('请选择要编辑的用户组或添加新的数据！');
		}
		iframe.findData(treeNode.id);
	}
	
	function init(){
		$('#centerdiv').disabled('请选择要编辑的用户组或添加新的数据！');
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west'">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button" config="onClick: doAdd ,text:'添加'"></span> <span
					xtype="button"
					config="onClick: TreeUtil.doUp , params:{treeid:'groupTree',action:'usersystem-Group-order'}  , textHidden : true,text:'上移' ,icon : 'hh_up' "></span>
				<span xtype="button"
					config="onClick: TreeUtil.doDown , params:{treeid:'groupTree',action:'usersystem-Group-order'} , textHidden : true,text:'下移' ,icon : 'hh_down' "></span>
				<span xtype="button"
					config="onClick : TreeUtil.refresh,text : '刷新' ,params: 'groupTree'  "></span>
			</div>
			<span xtype="tree"
				config=" id:'groupTree', url:'usersystem-Group-queryTreeList' ,remove: doDelete , onClick : orgTreeClick  "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="orgeditiframe" name="orgeditiframe" width=100%
				height=100% frameborder=0 src="jsp-usersystem-group-groupedit"></iframe>
		</div>
	</div>
</body>
</html>