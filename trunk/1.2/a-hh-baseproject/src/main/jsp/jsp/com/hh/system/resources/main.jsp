<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.BaseSystemUtil"%>
<%@page import="com.hh.system.util.PrimaryKey"%>
<%=BaseSystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据列表</title>
<%=BaseSystemUtil.getBaseJs("layout","ztree", "ztree_edit")%>
<%String iframeId = PrimaryKey.getPrimaryKeyUUID();%>

<script type="text/javascript">
	var iframeId = '<%=iframeId%>';
	function treeClick(treeNode) {
		window.frames[iframeId].iframeClick(treeNode);
	}
	function init(){
	}
	function doSetState(state) {
		var node = $('#span_tree').getWidget().getSelectNode();
		if(node){
			var data = {};
			data.ids = node.id;
			data.state = state || 0;
			Request.request('system-ResourcesType-doSetState', {
				data : data
			}, function(result) {
				if (result.success != false) {
					$.hh.tree.refresh('tree');
				}
			});
		}else{
			Dialog.infomsg('请选中一条数据！');
		}
		
		
	}
	
	
	function doGX(){
		doSetState(1);
	}
	function doQXGX(){
		doSetState(0);
	}
</script>
</head>
<body>
	<div xtype="border_layout">
		<div config="render : 'west',width:220">
			<div xtype="toolbar" config="type:'head'">
				<span xtype="button"
					config="onClick : $.hh.tree.refresh,text : '刷新' ,params: 'tree'  "></span>
					<span
			xtype="button" config="onClick: doGX ,text:'共享' "></span>
			<span
			xtype="button" config="onClick: doQXGX ,text:'取消共享' "></span>
			</div>
			<span xtype="tree"
				config=" id:'tree', url:'system-ResourcesType-queryTreeList' ,onClick : treeClick  "></span>
		</div>
		<div style="overflow: visible;" id=centerdiv>
			<iframe id="<%=iframeId%>" name="<%=iframeId%>" width=100%
				height=100% frameborder=0 src="jsp-system-resources-ResourcesList?type=<%=request.getParameter("type")%>"></iframe>
		</div>
	</div>
</body>
</html>