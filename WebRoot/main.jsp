<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	ArrayList<String> al = (ArrayList<String>) request.getSession().getAttribute("loginUser");
	int userlevel = -1;
	if (al != null) {
		userlevel = Integer.parseInt(al.get(3));
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>故障受理服务系统</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">

<style type="text/css">
<% String contextPath = "js/dojo-release-1.7.2"; %>
@import "<%=contextPath%>/dojo/resources/dojo.css";
@import "<%=contextPath%>/dijit/themes/claro/claro.css";
@import "<%=contextPath%>/dijit/themes/claro/document.css";
@import "<%=contextPath%>/dijit/themes/claro/Dialog.css";
@import "<%=contextPath%>/dijit/themes/claro/Tree.css";
@import "<%=contextPath%>/dijit/themes/ProgressBar.css";
@import "<%=contextPath%>/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
@import "<%=contextPath%>/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
@import "<%=contextPath%>/dojox/layout/resources/FloatingPane.css";
@import "<%=contextPath%>/dojox/layout/resources/ResizeHandle.css";
@import "<%=contextPath%>/dojox/form/resources/Rating.css";
.claro .dijitContentPane {padding:0;}

/* pre-loader specific stuff to prevent unsightly flash of unstyled content */
#loader {
	padding:0;
	margin:0;
	position:absolute;
	top:0; left:0;
	width:100%; height:100%;
	background:#ededed;
	z-index:999;
	vertical-align:middle;
}
#loaderInner {
	margin:auto;
	padding:5px;
	position:relative;
	left:0;
	top:45%;
	width:175px;
	background:#3c3;
	color:#fff;
}
</style>
<link type="text/css" rel="stylesheet" href="css/main.css?v=201205210001" />
<script type="text/javascript">
	var userinfo = {};
<%	if (al == null) {%>
		userinfo['id'] = 0;
<%	}
	else {%>
		userinfo['id'] = <%=al.get(0)%>;
		userinfo['name'] = "<%=al.get(1)%>";
		userinfo['unit'] = "<%=al.get(2)%>";
		userinfo['level'] = <%=al.get(3)%>;
<%	}%>
</script>
<script type="text/javascript" src="<%=contextPath%>/dojo/dojo.js"
	data-dojo-config="isDebug: false, parseOnLoad: true">
</script>
<script language="javascript" src="js/main.js?v=201205210001">
</script>
<script language="javascript" src="js/image.js?v=201205210001">
</script>
<script type="text/javascript">
	dojo.ready(function(){
		setTimeout(function hideLoader(){
			dojo.fadeOut({
				node: 'loader',
				duration:500,
				onEnd: function(n){
					n.style.display = "none";
				}
			}).play();
		}, 250);
	});
</script>
<script type="text/javascript">
	function clickTreeItem(item) {
		if (item.urltype == "none") {
			return;
		}
		if (item.urltype == "griddata") {
			var grid = dijit.byId("grid");
			if (grid) {
				var params = {};
				params.start = item.params[0].start;
				params.count = item.params[0].count;
				params.starttime = item.params[0].starttime;
				params.endtime = item.params[0].endtime;
				params.state = item.params[0].state;
				params.timestamp = new Date().getTime();
				besearch = false;
				grid._clearData();
				grid.setStore(
						new dojox.data.QueryReadStore({
								url: item.url[0],
								requestMethod: "get",
								clearOnClose : true,
								_filterResponse : checkRet
						}),
						params
				);
				grid.set('structure', gridLayout);
				grid.onRowDblClick = onGridRowClick
				var tab = dijit.byId("mainTabcon");
				tab.selectChild(dijit.byId("main_grid"));
			}
		}
		else if (item.urltype == "gridaudio") {
			var grid = dijit.byId("grid");
			if (grid) {
				var params = {};
				params.status = item.params[0].status;
				params.timestamp = new Date().getTime();
				besearch = false;
				grid._clearData();
				grid.setStore(
						new dojox.data.QueryReadStore({
								url: item.url[0],
								requestMethod: "get",
								clearOnClose : true,
								_filterResponse : checkRet
						}),
						params
				);
				grid.set('structure', gridLayoutAudio);
				grid.onRowDblClick = function(){};
				var tab = dijit.byId("mainTabcon");
				tab.selectChild(dijit.byId("main_grid"));
			}
		}
		else if (item.urltype == "dialog") {
			if (item.url == "./ChangePass") {
				dijit.byId("dlg_password").show();
				dijit.byId("dlg_password").resize();
			}
			else if (item.url == "./AddUser") {
<%	if (userlevel == 0 || userlevel == 1 || userlevel == 2 || userlevel == 4 || userlevel == 7)
	{%>
				var sellevel = dijit.byId("useradd_type");
				sellevel.removeOption(sellevel.getOptions());
				switch (userinfo['level'])
				{
				case 2:
					sellevel.addOption({value: 3, label: "团级"});
					break;
				case 1:
					sellevel.addOption({value: 2, label: "集团军"});
					break;
				case 7:
				case 4:
					sellevel.addOption({value: 1, label: "军区"});
					sellevel.addOption({value: 5, label: "专家"});
					sellevel.addOption({value: 6, label: "工业部门"});
					break;
				case 0:
					sellevel.addOption({value: 7, label: "维修中心-格式检查员"});
					sellevel.addOption({value: 4, label: "维修中心-分发员"});
					break;
				default:
					break;
				}
				dijit.byId("dlg_useradd").show();
				dijit.byId("dlg_useradd").resize();
<%	}%>
			}
			else if (item.url == "./PushFault") {
				push_fault();
			}
			else if (item.url == "./PushAudio") {
				push_audio();
			}
			//else if (item.url == "./LogServlet") {
			//	show_log();
			//}
		}
		else {
			return;
		}
	};

	dojo.addOnLoad(function(){
		$("usermessage").innerHTML = "登录用户："+userinfo['name']+" | 用户级别："+
<%		switch (userlevel)
		{
		case 0:%>
			"超级管理员";
<%			break;
		case 1:%>
			"军区";
<%			break;
		case 2:%>
			"集团军";
<%			break;
		case 3:%>
			"团级";
<%			break;
		case 4:%>
			"维修中心-分发员";
<%			break;
		case 5:%>
			"专家";
<%			break;
		case 6:%>
			"工业部门";
<%			break;
		case 7:%>
			"维修中心-格式检查员";
<%			break;
		default:%>
<%			break;
		}%>
		$("usermessage").innerHTML += " | <a href='./LogoutManagerServlet'>退出</a>";
	});
</script>
</head>
<jsp:include flush="true" page="/SessLoginServlet"></jsp:include>
<body class="claro" style="overflow:none;">
<!-- basic preloader: -->
<div id="loader"><div id="loaderInner" style="direction:ltr;">载入中，请稍后... </div></div>

<div id="main" data-dojo-type="dijit.layout.BorderContainer" data-dojo-props='design:"headline", gutters:false, liveSplitters:false' >
	<div id="header" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='region:"top", splitter:false' >
		<table width="100%" height="127px" cellspacing="0" cellpadding="0" border="0">
			<tr height="94px">
				<td><h1>故障受理服务系统</h1></td>
			</tr>
			<tr id="menubar" height="33px">
				<td><div id="usermessage"></div></td>
			</tr>
		</table>
	</div>
	<div  id="leftContainer" class="w250" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='region:"leading", splitter:true, minSize:"250"' >
		<div id="accon" data-dojo-type="dijit.layout.AccordionContainer">
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"未处理表单"' >
				<div id="formTree_noProcessed" ></div>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"已处理表单"' >
				<div id="formTree_processed" ></div>
			</div>
<!--
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"故障上报管理"' >
				<div id="faultCommit_manage" ></div>
			</div>
-->
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"故障搜索"' >
				<span class="TableTitle" >
					<h1>搜索</h1>
				</span>
				<div data-dojo-type="dijit.layout.TabContainer" data-dojo-props='style:"height:80%;"'>
					<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"单号",style:"padding:5px;"'>
						<div class="editTable wauto">
							<script type="text/javascript">
							function submitFormSearchId() {
								dijit.byId("submit_search_id").set("disabled",true);
								showProgress("查询");
								searchXhr = dojo.xhrGet({
									url: "./SearchFaultServlet" ,
									form: "form_search_id" ,
									content: {
									},
									handleAs: "json" ,
									preventCache: true,
									load: function(data) {
										dijit.byId("submit_search_id").set("disabled",false);
										var grid = dijit.byId("grid");
										if (grid) {
											besearch = true;
											grid.setStore(new dojo.data.ItemFileWriteStore({data: data}), null);
											var tab = dijit.byId("mainTabcon");
											tab.selectChild(dijit.byId("main_grid"));
										}
										hideProgress();
									},
									error: function() {
										//
										alert("出了点问题，搜索没有成功呢！");
										dijit.byId("submit_search_id").set("disabled",false);
										hideProgress();
									},
									sync: false
								}).ioArgs.url;
								return false;
							}
							</script>
							<form id="form_search_id" data-dojo-type="dijit.form.Form" data-dojo-props='method:"GET",
								onSubmit:function(){ return submitFormSearchId(); }'>
								<script type="dojo/connect" data-dojo-event="startup">
									var sb = dijit.byId("submit_search_id");
									sb.set("disabled", !this.isValid());
									this.connect(this, "onValidStateChange", function(state){
											sb.set("disabled", !state);
									});
								</script>
								<table>
									<tr>
										<th style="width:40%">故障单号</th>
										<td style="width:60%">
											<input class="editbox" data-dojo-type="dijit.form.ValidationTextBox" data-dojo-props='name:"faultId", type:"text", required:true, regExp:"\\d{18}"' />
										</td>
									</tr>
									<tr>
										<th colspan="2">请输入一串18位数字单号</th>
									</tr>
								</table>
								<div class="toolbar">
									<button id="submit_search_id" data-dojo-type="dijit.form.Button" 
										data-dojo-props='type:"submit"'>搜索</button>
								</div>
							</form>
						</div>
					</div>
					<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"关键字",style:"padding:5px;"'>
						<div class="editTable wauto">
							<script type="text/javascript">
							function submitFormSearchKey() {
								dijit.byId("submit_search_key").set("disabled",true);
								showProgress("查询");
								searchXhr = dojo.xhrGet({
									url: "./SearchFaultServlet" ,
									form: "form_search_key" ,
									content: {
									},
									handleAs: "json" ,
									preventCache: true,
									load: function(data) {
										dijit.byId("submit_search_key").set("disabled",false);
										var grid = dijit.byId("grid");
										if (grid) {
											besearch = true;
											grid.setStore(new dojo.data.ItemFileWriteStore({data: data}), null);
											var tab = dijit.byId("mainTabcon");
											tab.selectChild(dijit.byId("main_grid"));
										}
										hideProgress();
									},
									error: function() {
										//
										alert("出了点问题，搜索没有成功呢！");
										dijit.byId("submit_search_key").set("disabled",false);
										hideProgress();
									},
									sync: false
								}).ioArgs.url;
								return false;
							}
							</script>
							<form id="form_search_key" data-dojo-type="dijit.form.Form" data-dojo-props='method:"GET",
								onSubmit:function(){ return submitFormSearchKey(); }'>
								<script type="dojo/connect" data-dojo-event="startup">
									var sb = dijit.byId("submit_search_key");
									sb.set("disabled", !this.isValid());
									this.connect(this, "onValidStateChange", function(state){
											sb.set("disabled", !state);
									});
								</script>
								<table>
									<tr>
										<th style="width:40%">故障现象关键字</th>
										<td style="width:60%">
											<input class="editbox" data-dojo-type="dijit.form.ValidationTextBox" data-dojo-props='name:"keyword", type:"text", required:true' />
										</td>
									</tr>
									<tr>
										<th colspan="2">请输入故障现象描述关键字</th>
									</tr>
								</table>
								<div class="toolbar">
									<button id="submit_search_key" data-dojo-type="dijit.form.Button" 
										data-dojo-props='type:"submit"'>搜索</button>
								</div>
							</form>
						</div>
					</div>
					<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"高级",style:"padding:5px;"'>
						<div class="editTable wauto">
							<script type="text/javascript">
							function submitFormSearchAdv() {
								dijit.byId("submit_search_adv").set("disabled",true);
								showProgress("查询");
								searchXhr = dojo.xhrGet({
									url: "./SearchFaultServlet" ,
									form: "form_search_adv" ,
									content: {
									},
									handleAs: "json" ,
									preventCache: true,
									load: function(data) {
										dijit.byId("submit_search_adv").set("disabled",false);
										var grid = dijit.byId("grid");
										if (grid) {
											besearch = true;
											grid.setStore(new dojo.data.ItemFileWriteStore({data: data}), null);
											var tab = dijit.byId("mainTabcon");
											tab.selectChild(dijit.byId("main_grid"));
										}
										hideProgress();
									},
									error: function() {
										//
										alert("出了点问题，搜索没有成功呢！");
										dijit.byId("submit_search_adv").set("disabled",false);
										hideProgress();
									},
									sync: false
								}).ioArgs.url;
								return false;
							}
							</script>
							<form id="form_search_adv" data-dojo-type="dijit.form.Form" data-dojo-props='method:"GET",
								onSubmit:function(){ return submitFormSearchAdv(); }'>
								<script type="dojo/connect" data-dojo-event="startup">
									dijit.byId('search_de').constraints.max = 
									dijit.byId('search_db').constraints.max = new Date();
								</script>
								<table>
									<tr>
										<th style="width:40%">时间段：</th>
										<td style="width:60%">
											<label for="">开始时间</label>
											<input id="search_db" class="editbox" data-dojo-type="dijit.form.DateTextBox" data-dojo-props='name:"datebegin"' onChange="dijit.byId('search_de').constraints.min = this.getValue();" />
										</td>
									</tr>
									<tr>
										<th></th>
										<td>
											<label for="">结束时间</label>
											<input id="search_de" class="editbox" data-dojo-type="dijit.form.DateTextBox" data-dojo-props='name:"dateend"' onChange="dijit.byId('search_db').constraints.max = this.getValue();" />
										</td>
									</tr>
									<tr>
										<th>装备：</th>
										<td>
											<input id="search_equip_type" data-dojo-type="dijit.form.RadioButton" data-dojo-props='name:"equip", value:"type" '/>型号
											<input class="editbox" data-dojo-type="dijit.form.TextBox" data-dojo-props='name:"equip_type", type:"text", onFocus:function(){dijit.byId("search_equip_type")._setCheckedAttr(true);}' />
										</td>
									</tr>
									<tr>
										<th></th>
										<td>
											<input id="search_equip_name" data-dojo-type="dijit.form.RadioButton" data-dojo-props='name:"equip", value:"name" '/>名称
											<input class="editbox" data-dojo-type="dijit.form.TextBox" data-dojo-props='name:"equip_name", type:"text", onFocus:function(){dijit.byId("search_equip_name")._setCheckedAttr(true);}' />
										</td>
									</tr>
									<tr>
										<th></th>
										<td>
											<input id="search_equip_id" data-dojo-type="dijit.form.RadioButton" data-dojo-props='name:"equip", value:"id" '/>编号
											<input class="editbox" data-dojo-type="dijit.form.TextBox" data-dojo-props='name:"equip_id", type:"text", onFocus:function(){dijit.byId("search_equip_id")._setCheckedAttr(true);}' />
										</td>
									</tr>
									<tr>
										<th>上报人：</th>
										<td>
											<input class="editbox" data-dojo-type="dijit.form.TextBox" data-dojo-props='name:"reporter", type:"text" ' />
										</td>
									</tr>
									<tr>
										<th>使用单位：</th>
										<td>
											<input class="editbox" data-dojo-type="dijit.form.TextBox" data-dojo-props='name:"department", type:"text" ' />
										</td>
									</tr>
								</table>
								<div class="toolbar">
									<button id="submit_search_adv" data-dojo-type="dijit.form.Button" 
										data-dojo-props='type:"submit", busyLabel:"正在搜索"'>搜索</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"用户管理"' >
				<div id="user_manage" ></div>
			</div>
		</div>
	</div>
	<div id="centerContainer" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='region:"center"' >
		<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='style:"height:100%"' >
<%	if (userlevel == 3 || userlevel == 4 || userlevel == 5 || userlevel == 7)
	{%>
			<div id="centerTop" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"快速功能"' >
				<div>
<%	}%>
<%	String audiotitle="";
	switch (userlevel)
	{
	case 3:
		audiotitle="语音上报";
		break;
	case 7:
		audiotitle="语音监听";
		break;
	case 4:
		audiotitle="语音求助";
		break;
	case 5:
		audiotitle="语音协助";
		break;
	}%>
<%	if (userlevel == 3 || userlevel == 7 || userlevel == 4 || userlevel == 5)
	{%>
					<script type="text/javascript">
					function dlgAudio() {
						var dlg = dijit.byId("dlg_audio");
						if (dlg) {
							dlg.show();
							dlg.resize();
						}
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){ dlgAudio(); }'><%=audiotitle%></button>
<%	}%>
<%	if (userlevel == 3 || userlevel == 7)
	{%>
					<script type="text/javascript">
					function dlgFault(item) {
						var dlg = dijit.byId("dlg_fault_form");
						if (dlg) {
							if (item) {
								dlg.keyid = item.keyid;
								dijit.byId("fault_commiter").setDisplayedValue(item.reporter);
								dijit.byId("fault_committime").setDisplayedValue(item.reporttime);
							}
							else {
								dijit.byId("fault_commiter").setDisplayedValue(userinfo["name"]);
								dijit.byId("fault_committime").setDisplayedValue(dojo.date.locale.format(new Date(),
									{timePattern:'yyyy-MM-dd-HH-mm-ss',selector:'time'}));
							}
							dlg.show();
							dlg.resize();
						}
					}
					</script>
<%	}%>
<%	if (userlevel == 3)
	{%>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){ dlgFault(); }'>故障上报</button>
					<script type="text/javascript">
					function dlgFile() {
						var dlg = dijit.byId("dlg_file_form");
						if (dlg) {
							dlg.show();
							dlg.resize();
						}
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){ dlgFile(); }'>导入数据文件</button>
<%	}%>
<%	if (userlevel == 4 || userlevel == 7)
	{%>
					<script type="text/javascript">
					function dlgIndustry() {
						var dlg = dijit.byId("dlg_industry_form");
						if (dlg) {
							dlg.show();
							dlg.resize();
						}
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){ dlgIndustry(); }'>工业部门录入</button>
					<script type="text/javascript">
					function expertSearchResult() {
						if (besearch) {
							window.location = searchXhr.replace(/SearchFaultServlet/, "OutputResultsServlet");
						}
						else {
							alert("当前没有搜索呢！");
						}
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){ expertSearchResult(); }'>导出搜索结果</button>
					<script type="text/javascript">
					function gridPrePrint() {
						var grid = dijit.byId("grid");
						dojo.style(dojo.byId("grid"), "display", "none");
						dojo.style(dojo.byId("main_grid"), "height", "auto");
						var gridtmp = new dojox.grid.DataGrid({
							id: "gridtmp",
							store: grid.store,
							structure: gridLayout,
							autoHeight: true,
							autoWidth: true
						}, document.createElement('div'));
						dojo.byId("main_grid").appendChild(gridtmp.domNode);
						gridtmp.startup();
					}
					function gridAftPrint() {
						dojo.style(dojo.byId("grid"), "display", "");
						var gridtmp = dijit.byId("gridtmp");
						gridtmp.destroy();;
					}
					function printGrid() {
						if (!besearch) {
							alert("当前没有搜索呢！");
							return;
						}
						JPrint.prePrint = gridPrePrint;
						JPrint.aftPrint = gridAftPrint;
						JPrint.print("main_grid");
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
							onClick:function(){printGrid();}'>打印搜索结果</button>
<%	}%>
<%	if (userlevel == 3 || userlevel == 4 || userlevel == 5 || userlevel == 7)
	{%>
				</div>
			</div>
<%	}%>
			<div id="mainTabcon" class="mainTabContainer" data-dojo-type="dijit.layout.TabContainer">
				<div id="main_grid" class="tab_page" style="overflow:hidden;" data-dojo-type="dijit.layout.ContentPane" title="表单">
					<div id="grid" data-dojo-type="dojox.grid.EnhancedGrid" data-dojo-props='structure:gridLayout, plugins:gridPlugins, onRowDblClick:onGridRowClick'></div>
				</div>
<%	if (userlevel == 1 || userlevel == 2 || userlevel == 3 || userlevel == 4 || userlevel == 7)
	{%>
				<jsp:include flush="true" page="chart.jsp?v=201205210001"></jsp:include>
<%	}%>
			</div>

		</div>
	</div>
	<div id="footer" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='region:"bottom", splitter:false' >版权所有</div>
</div>
<jsp:include flush="true" page="faultinfo.jsp?v=201205210001"></jsp:include>
<jsp:include flush="true" page="audio.jsp?v=201205210001"></jsp:include>
<jsp:include flush="true" page="user.jsp?v=201205210001"></jsp:include>

<!--
<script type="text/javascript">
function show_log() {
	var dlg = dijit.byId("dlg_log");
	dlg.show();
	dlg.resize();
	dojo.xhrGet({
		url: "./LogServlet" ,
		handleAs: "json" ,
		preventCache: true,
		load: function() {
			// close dialog
			alert("载入日志数据成功！");
		},
		error: function() {
			//
			alert("出了点问题，获取日志数据没有成功呢！");
		},
		sync: false
	});
}
</script>
<div id="dlg_log" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"日志"' >
	<div id="userlog" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='style:"width:100%;height:100%"'>
	</div>
</div>
-->

<div id="dlg_file_form" data-dojo-type="dijit.Dialog" data-dojo-props='title:"导入文件"' >
	<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"导入数据文件"' >
		<span class="TableTitle" >
			<h1>导入数据文件</h1>
		</span>
		<script type="text/javascript">
		function importFile() {
			dijit.byId("submit_file").set("disabled",true);
			showProgress("上传数据文件");
			dojo.io.iframe.send({
				url: "./UploadLocalFileServlet",
				form: "form_file",
				contentType:"multipart/form-data",
				handleAs: "json",
				load: function(data) {
					dijit.byId("submit_file").set("disabled",false);
					if (!data) {
						alert("未知错误！请重新提交！");
					}
					else if (data.ret != 0) {
						alert(data.msg);
					}
					else {
						try {
							var dlg = dijit.byId("dlg_fault_form");
							if (dlg) {
								dijit.byId("zbtype").setDisplayedValue(data.items[0].equipType);
								dijit.byId("zbname").setDisplayedValue(data.items[0].equipName);
								dijit.byId("zbserial").setDisplayedValue(data.items[0].equipNumber);
								dijit.byId("zbnum").setDisplayedValue(data.items[0].amount);
								dijit.byId("zbgzxx").setDisplayedValue(data.items[0].faultDesp);
								dijit.byId("zbgzsj").setDisplayedValue(data.items[0].faultTime);
								dijit.byId("zbxqcl").setDisplayedValue(data.items[0].preProcess);
								dijit.byId("zbgzpc").setDisplayedValue(data.items[0].frequence);
								dijit.byId("zbgzbw").setDisplayedValue(data.items[0].faultPlace);
								dijit.byId("zbsydw").setDisplayedValue(data.items[0].department);
								dijit.byId("zbuser").setDisplayedValue(data.items[0].operater);
								dijit.byId("fault_commiter").setDisplayedValue(data.items[0].reporter);
								dijit.byId("fault_contact").setDisplayedValue(data.items[0].contact);
								dijit.byId("fault_committime").setDisplayedValue(data.items[0].reportTime);
								dijit.byId("zbqzzp").setDisplayedValue(data.items[0].photos);
								dlg.show();
							}
							dijit.byId("form_file").reset();
							dijit.byId("dlg_file_form").hide();
						}catch(e) {
							alert("xml解析出了点问题，是不是格式不对呢？");
						}
					}
					hideProgress();
				},
				error: function() {
					alert("出了点问题，上传没有成功呢！");
					dijit.byId("submit_file").set("disabled",false);
					hideProgress();
				},
				sync: false
			});
			return false;
		}
		</script>
		<form id="form_file" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST", encType:"multipart/form-data",
			onSubmit:function(){
				return importFile();
			}'>
			<div class="editTable w220">
				<input id="file_url" class="editbox" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"file_url", type:"file", placeholder:"数据文件"' />
			</div>
			<br />
			<div class="toolbar">
				<button id="submit_file" data-dojo-type="dijit.form.Button" 
					data-dojo-props='type:"submit", busyLabel:"正在提交"'>确定</button>
				<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
					onClick:function(){
						$("file_url").select();
						document.execCommand("Delete");
						dijit.byId("dlg_file_form").hide();
						dijit.byId("submit_file").set("disabled",false);
					} '>取消</button>
			</div>
		</form>
	</div>
</div>

<div id="dlg_progress" data-dojo-type="dijit.Dialog" data-dojo-props='title:"请稍等"' >
	<div id="text_progress" style="margin:0 auto;text-align:center;font-size:14px;"></div>
	<div data-dojo-type="dijit.ProgressBar" data-dojo-props="indeterminate:true, style:'width:200px;margin:10px;'"></div>
</div>

</body>
</html>
