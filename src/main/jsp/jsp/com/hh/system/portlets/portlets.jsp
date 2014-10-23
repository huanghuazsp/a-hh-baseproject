<%@page import="com.hh.usersystem.bean.usersystem.HhXtCd"%>
<%@page import="com.hh.usersystem.bean.usersystem.HhXtYh"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.hh.system.util.SystemUtil"%>
<%@page import="com.google.gson.Gson"%>
<%=SystemUtil.getBaseDoctype()%>
<html>
<head>
<title>系统门户</title>
<%=SystemUtil.getBaseJs()%>
<%
	Gson gson = new Gson();
	HhXtYh hhXtYh = (HhXtYh) session.getAttribute("loginuser");
%>
<style>
body {
	min-width: 520px;
	padding: 10px;
}

.column {
	width: 33%;
	float: left;
}

.portlet {
	margin: 0 1em 1em 0;
}

.portlet-header {
	margin: 0.3em;
	padding-bottom: 4px;
	padding-left: 0.2em;
}

.portlet-header .ui-icon {
	float: right;
}

.portlet-content {
	padding: 0.4em;
}

.ui-sortable-placeholder {
	border: 1px dotted black;
	visibility: visible !important;
	height: 50px !important;
}

.ui-sortable-placeholder * {
	visibility: hidden;
}
</style>
<script>
	$(function() {
		$(".column").sortable({
			connectWith : ".column"
		});

		$(".portlet").addClass(
				"ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
				.find(".portlet-header").addClass(
						"ui-widget-header ui-corner-all").prepend(
						"<span class='ui-icon ui-icon-minusthick'></span>")
				.end().find(".portlet-content");

		$(".portlet-header .ui-icon").click(
				function() {
					$(this).toggleClass("ui-icon-minusthick").toggleClass(
							"ui-icon-plusthick");
					$(this).parents(".portlet:first").find(".portlet-content")
							.toggle();
				});

		$(".column").disableSelection();
	});
</script>
</head>
<body>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column" style="width: 66%;">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing eliLorem ipsum dolor sit amet,
				consectetuer adipiscing eliLorem ipsum dolor sit amet,
				consectetuer adipiscing eliLorem ipsum dolor sit amet,
				consectetuer adipiscing eliLorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header">Shopping</div>
			<div class="portlet-content">Lorem ipsum dolor sit amet,
				consectetuer adipiscing elit</div>
		</div>
	</div>
</body>
</html>