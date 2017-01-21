<%@page import="com.hh.system.util.Convert"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>日程安排</title>
<%=SystemUtil.getBaseJs()%>
<meta charset='utf-8' />
<link
	href='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.css'
	rel='stylesheet' />
<link
	href='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.print.css'
	rel='stylesheet' media='print' />
<script
	src='/hhcommon/opensource/jquery/fullcalendar/2.0.1/lib/moment.min.js'></script>
<script
	src='/hhcommon/opensource/jquery/fullcalendar/2.0.1/fullcalendar.min.js'></script>
<script>
	var meetingId = '<%=Convert.toString(request.getParameter("meetingId"))%>';
	var meetingIdText = '<%=Convert.toString(request.getParameter("meetingIdText"))%>';
	function getBackground(isOk) {
		return isOk == 0 ? 'red' : 'green';
	}

	function toEvents(dataList) {
		var resultList = [];
		for (var i = 0; i < dataList.length; i++) {
			resultList.push(toEvent(dataList[i]));
		}
		return resultList;
	}
	function toEvent(data) {
		if (data) {
			var data = $.extend(data, {
				title : data.text,
				start : $.hh.formatDate(data.start,'yyyy-MM-dd HH:mm:ss'),
				end : $.hh.formatDate(data.end,'yyyy-MM-dd HH:mm:ss'),
				color : getBackground(data.isOk),
				textColor : null,
				borderColor : null
			});
		}
		return data;
	}

	function getEventObject(event) {
		var object = {};
		object.start = event.start.format('YYYY-MM-DD HH:mm:ss');
		if(event.end){
			object.end = event.end.format('YYYY-MM-DD HH:mm:ss');
		}
		object.id = event.id;
		object.text = event.text;
		object.participants = event.participants;
		object.level = event.level;
		object.isOk = event.isOk;
		object.userId = event.userId;
		return object;
	}

	$(document)
			.ready(
					function() {
						renderData();
					});
	
	function renderData(){
		$('#calendar')
		.fullCalendar(
				{
					//lazyFetching : false,
					defaultView:'agendaWeek',
					header : {
						left : 'title',
						center : 'prevYear,prev,next,nextYear today',
						right : 'month,agendaWeek,agendaDay'
					},
					titleFormat : {
						month : 'YYYY年MM月',
						day : 'YYYY年MM月DD日'
					},
					monthNames : [ "一月", "二月", "三月",
							"四月", "五月", "六月", "七月",
							"八月", "九月", "十月", "十一月",
							"十二月" ],
					monthNamesShort : [ "一月", "二月",
							"三月", "四月", "五月", "六月",
							"七月", "八月", "九月", "十月",
							"十一月", "十二月" ],
					dayNames : [ "周日", "周一", "周二",
							"周三", "周四", "周五", "周六" ],
					dayNamesShort : [ "周日", "周一", "周二",
							"周三", "周四", "周五", "周六" ],
					today : [ "今天" ],
					buttonText : {
						today : '当前',
						month : '月',
						week : '周',
						day : '日',
						prev : '‹', // ‹
						next : '›', // ›
						prevYear : '«', // «
						nextYear : '»' // »
					},
					selectable : true,
					selectHelper : true,
					eventClick : function(calEvent,
							jsEvent, view) {
						var object = getEventObject(calEvent);
						object.meetingId=meetingId;
						object.meetingIdText=meetingIdText;
						Dialog
								.open({
									url : 'jsp-oa-meetingapply-calendaredit',
									params : {
										object : object,
										callback : function(
												resultData) {
											if (resultData == 'delete') {
												$(
														'#calendar')
														.fullCalendar(
																'removeEvents',
																[ object.id ]);
											} else {
												$
														.extend(
																calEvent,
																toEvent(resultData));
												$(
														'#calendar')
														.fullCalendar(
																'updateEvent',
																calEvent,
																true);
												renderEvent();
											}

										}
									}
								});
					},
					eventDrop :  function(event,dayDelta,minuteDelta,revertFunc) {
						var object = getEventObject(event);
						object.meetingId=meetingId;
						Request
								.request(
										'oa-MeetingApply-updateDate',
										{
											data : object,
											callback : function(result) {
												if (result.success == false) {
													$('#calendar').fullCalendar('refetchEvents');
												}
											}
										});
					},
					eventResize : function(event,dayDelta,minuteDelta,revertFunc) {
						var object = getEventObject(event);
						object.meetingId=meetingId;
						Request
								.request(
										'oa-MeetingApply-updateDate',
										{
											data : object,
											callback : function(result) {
												if (result.success == false) {
													$('#calendar').fullCalendar('refetchEvents');
												}
											}
										});
					},
					select : function(start, end) {
						var object = {
							start : start
									.format('YYYY-MM-DD HH:mm:ss'),
							end : end
									.format('YYYY-MM-DD HH:mm:ss')
						};
						object.meetingId=meetingId;
						object.meetingIdText=meetingIdText;
						Dialog
								.open({
									url : 'jsp-oa-meetingapply-calendaredit',
									params : {
										object : object,
										callback : function(
												resultData) {
											$(
													'#calendar')
													.fullCalendar(
															'renderEvent',
															toEvent(resultData));
											$(
													'#calendar')
													.fullCalendar(
															'unselect');
											renderEvent();
										}
									}
								});
					},
					editable : true,
					events : function(start, end, ddd,
							callback) {
						if(meetingId){
							Request
							.request(
									'oa-MeetingApply-queryListByDate',
									{
										data : {
											start : start
													.format('YYYY-MM-DD HH:mm:ss'),
											end : end
													.format('YYYY-MM-DD HH:mm:ss'),
											'meetingId':meetingId
										}
									},
									function(result) {
										callback(toEvents(result));
										renderEvent();
									});
							$("#calendar").undisabled();
						}else{
							$("#calendar").disabled('请选择会议室！');
						}
					}
				});

	}
	
	
	function renderEvent(){
		$('.fc-event-inner').each(function(){
			$(this).attr('title',$(this).text());
		});
	}
</script>
<style>
body {
	padding: 0;
	font-size: 14px;
}


#calendar {
	width: 900;
	margin: 10px;
}
</style>
</head>
<body>
	<div id='calendar'></div>
</body>
</html>