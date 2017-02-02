<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.hh.system.util.Convert"%>
<%=SystemUtil.getBaseDoctype()%>

<html>
<head>
<title>数据编辑</title>
<%=SystemUtil.getBaseJs("checkform","ueditor")%>

<script type="text/javascript">
	var params = $.hh.getIframeParams();
	var objectid = '<%=Convert.toString(request.getParameter("id"))%>';

	function findData() {
		if (objectid) {
			Request.request('project-Bug-findObjectById', {
				data : {
					id : objectid
				},
				callback : function(result) {
					result.state = stateRender(result.state);
					result.describe = result.describe_;
					result.describe_='';
					$('#form').setValue(result);
				}
			});
		}
	}

	function init() {
		findData();
	}
	function stateRender(value){
		if(value==0){
			return '<font class=hh_red>新建</font>';
		}else if(value==1){
			return '<font class=hh_red>解决</font>';
		}else if(value==2){
			return '<font class=hh_red>重现</font>';
		}else if(value==9){
			return '<font class=hh_green>关闭</font>';
		}
	}
	
	function updateState(state){
		Request.request('project-Bug-updateState', {
			data : {
				state:state,
				id : objectid,
				describe_ : $('#span_describe_').getValue()
			},
			callback : function(result) {
				if (result.success!=false) {
					params.callback(result);
					Dialog.close();
				}
			}
		});
	}
	
	function renderData(value,data){
		var returnStr = data.createUserName+'更新了状态';
		 if(data.type==1){
			returnStr+= '[解决]';
		}else if(data.type==2){
			returnStr+= '[重现]';
		}else if(data.type==9){
			returnStr+= '[关闭]';
		}
		 returnStr+='<br/>';
		 returnStr+='描述：'+value;
		 return returnStr;
	}
</script>
</head>
<body>
	<div xtype="hh_content">
		<form id="form" xtype="form" class="form">
			<span xtype="text" config=" hidden:true,name : 'id'"></span>
			<table xtype="form">
					<tr>
						<td xtype="label">状态：</td>
						<td><span xtype="html" config=" name : 'state' "></span></td>
					</tr>
					<tr>
						<td xtype="label">所属项目：</td>
						<td colspan="3"><span xtype="html"
							config=" name:'projectIdText' " ></span></td>
					</tr>
					<tr >
						<td xtype="label">所属模块：</td>
						<td colspan="3"><span xtype="html"
							config=" name:'modularIdText' " ></span></td>
					</tr>
				
					<tr>
						<td xtype="label">名称：</td>
						<td><span xtype="html" config=" name : 'text' "></span></td>
					</tr>
					
					<tr>
						<td xtype="label">处理人：</td>
						<td><span xtype="html" config=" name : 'processingPeopleText' "></span></td>
					</tr>
				
					<tr>
						<td xtype="label">抄送人：</td>
						<td><span xtype="html" config=" name : 'handlingUsersText' "></span></td>
					</tr>
					<tr>
						<td xtype="label">bug描述：</td>
						<td><span xtype="html" config=" name : 'describe' "></span></td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="pagelist" xtype="pagelist"
							config=" params:{bugId:objectid},paging:false,title:false, url: 'project-BugLog-queryList' ,column : [
								{
									name : 'describe_' ,
									text : '描述',
									render : renderData,
									align:'left'
								}
								]  ">
						</div>
						</td>
					</tr>
					<tr>
						<td xtype="label">描述：</td>
						<td><span xtype="ckeditor" config=" name : 'describe_' "></span></td>
					</tr>
			</table>
		</form>
	</div>
	<div xtype="toolbar">
		<span xtype="button" config="text:'解决' , onClick : updateState ,params:1"></span>
		<span xtype="button" config="text:'重现' , onClick : updateState ,params:2"></span>
		<span xtype="button" config="text:'关闭' , onClick : updateState ,params:9"></span>
		<span xtype="button" config="text:'取消' , onClick : Dialog.close "></span>
	</div>
</body>
</html>