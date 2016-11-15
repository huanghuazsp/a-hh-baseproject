<%@page import="com.hh.system.util.Json"%>
<%@page import="com.hh.oa.bean.OaMeeting"%>
<%@page import="com.hh.system.service.impl.BeanFactoryHelper"%>
<%@page import="com.hh.oa.service.impl.OaMeetingService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>会议申请</title>
<%=SystemUtil.getBaseJs("layout")%>
<% 
	OaMeetingService oaMeetingService = BeanFactoryHelper.getBean( OaMeetingService.class);

	List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();

	List<OaMeeting> oaMeetingList = oaMeetingService.queryAllList();
	for(OaMeeting oaMeeting : oaMeetingList){
		Map<String,Object> map = new HashMap<String,Object>();
		mapList.add(map);
		
		map.put("id", oaMeeting.getId());
		map.put("text", oaMeeting.getText()+"/"+oaMeeting.getPeopleNumber()+"人/"+oaMeeting.getDevice());
		map.put("basetext", oaMeeting.getText());
		map.put("peopleNumber", oaMeeting.getPeopleNumber());
		map.put("device", oaMeeting.getDevice());
	}
%>
<script>
	var dataList = <%=Json.toStr(mapList)%>;
	var menuConfig = {
			onClick : meetingClick
	};
	menuConfig.data = dataList;
	
	function meetingClick(data){
		$('[name=jsp-oa-meetingapply-mettingfullcalendar]').attr('src','jsp-oa-meetingapply-mettingfullcalendar?meetingId='+data.id+'&meetingIdText='+data.basetext);
	}
</script>
</head>
<body  xtype="border_layout">
	<div config="render : 'west' ,width:260  ">
		<span xtype=menu  configVar=" menuConfig "></span>
	</div>
	<div  style="overflow: visible;" >
		<iframe name="jsp-oa-meetingapply-mettingfullcalendar"  width=100%
				height=100% frameborder=0 src="jsp-oa-meetingapply-mettingfullcalendar"></iframe>
	</div>
</body>
</html>