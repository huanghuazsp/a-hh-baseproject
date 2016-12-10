<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform")%>
<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var width = 720;
	var height = 400;

	var objectid = params.object ? params.object.id : '';

	var lxconfig = {
		name : 'lx_',
		value : 2,
		onChange:function(value){
			$('#node_span').setConfig({params:{lx_ : value}});
		},
		data : [ /*{
			id : 0,
			text : '集团'
		},*/ {
			id : 1,
			text : '机构'
		}, {
			id : 2,
			text : '部门'
		}/* , {
			id : 3,
			text : '岗位'
		}  */]
	}
	
	function callback(){
		
	}

	function save() {
		$.hh.validation.check('form', function(formData) {
			Request.request('usersystem-Org-save', {
				data : formData,
				callback : function(result) {
					if (result.success!=false) {
						callback(formData);
					}
				}
			});
		});
	}

	function findData(objid) {
		if (objid) {
			Request.request('usersystem-Org-findObjectById', {
				data : {
					id : objid
				},
				callback : function(result) {
					renderLxSpan(result.lx_);
					$('#form').setValue(result);
				}
			});
		}
	}

	
	function renderLxSpan(lx_){
		$('#node_span').setConfig({params:{lx_ : lx_}});
		return;
		if(lx_==0){
			lxconfig.data=[ /*{
				id : 0,
				text : '集团'
			}, */{
				id : 1,
				text : '机构'
			}, {
				id : 2,
				text : '部门'
			}/* , {
				id : 3,
				text : '岗位'
			}  */];
		}else if(lx_==1){
			lxconfig.data=[ {
				id : 1,
				text : '机构'
			}, {
				id : 2,
				text : '部门'
			}/* , {
				id : 3,
				text : '岗位'
			}  */];
		}else if(lx_==2){
			lxconfig.data=[ {
				id : 2,
				text : '部门'
			}/* , {
				id : 3,
				text : '岗位'
			} */ ];
		}else if(lx_==3){
			lxconfig.data=[ {
				id : 3,
				text : '岗位'
			} ];
		}
		lxconfig.value=lx_;
		$('#lxspan').render();
	}
	
	function newData(params) {
		if(params.lx_==null){
			params.lx_=0;
		}
		renderLxSpan(params.lx_);
		$.extend(params, {
			state : 0,
			expanded : 0
		});
		$('#form').setValue(params);
	}
	function node_span_change(data,change){
		/*if(change){
			if(data){
				$('#sjbmspan').setValue(data.code_);
			}else{
				$('#sjbmspan').setValue('');
			}
		}*/
	}
	function init() {
		//$('#spancode_').setValue($.hh.getUUID(3));
	}
	/* function init() {
		if (params.selectNode) {
			if (params.selectNode.isParent) {
				$("#node_span").setValue(params.selectNode);
			}
		}
		findData();
	} */
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
				<tr>
					<td xtype="label">名称：</td>
					<td><span xtype="text" config=" name : 'text',required :true"></span></td>
					<td xtype="label">父节点：</td>
					<td><span id="node_span" xtype="selectTree"
						config="name: 'node' , tableName : 'US_ORGANIZATION' , url : 'usersystem-Org-queryTreeListByLx' ,params :{lx_:2}  , onChange : node_span_change"></span>
					</td>
				</tr>
				<tr>
					<!-- <td xtype="label">上级编码：</td>
					<td><span id="sjbmspan"  xtype="text"
						config=" name : 'sjbm_', readonly : true "></span></td> -->
					<td xtype="label">编码：</td>
					<td colspan="3"><span id="spancode_" xtype="text"
						config=" name : 'code_',readonly :true ,watermark : '自动生成'"></span></td>
				</tr>
				
				<tr>
					<td xtype="label">状态：</td>
					<td><span xtype="radio"
						config="name: 'state' ,value : 0,  data :[{id:0,text:'正常'},{id:1,text:'禁用'}]"></span></td>
					<td xtype="label">是否展开：</td>
					<td><span xtype="radio"
						config="name: 'expanded' ,value : 0,  data :[{id:1,text:'是'},{id:0,text:'否'}]"></span></td>
				</tr>
				<tr>
					<td xtype="label">类型：</td>
					<td colspan="3"><span id="lxspan" xtype="radio" configVar="lxconfig"></span></td>
				</tr>
				<tr>
					<td xtype="label">角色：</td>
					<td colspan="3"><span xtype="selectPageList"
						config="name: 'roleIds'  , url:'usersystem-role-queryPagingData' ,tableName:'US_ROLE' "></span></td>
				</tr>
				<tr>
					<td xtype="label">自定义编码：</td>
					<td><span xtype="text" config=" name : 'zdybm_' "></span></td>
					<td xtype="label">简称：</td>
					<td><span xtype="text" config=" name : 'jc_' "></span></td>
				</tr>
				<tr>
					<td xtype="label">备注：</td>
					<td colspan="3"><span xtype="textarea" config=" name : 'ms_'"></span></td>
				</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'保存' , onClick : save "></span> 
	</div>
</body>
</html>