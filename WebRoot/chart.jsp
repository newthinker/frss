<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	ArrayList<String> al = (ArrayList<String>) request.getSession().getAttribute("loginUser");
	int userlevel = -1;
	if (al != null) {
		userlevel = Integer.parseInt(al.get(3));
	}
%>

<script type="text/javascript">
var chartStore;
var chart;

dojo.addOnLoad(initSelector);

function valTrans(value, store, item){
	return store.getValue(item, value);
}

function changeStore(data) {
	if (!chartStore) {
		chartStore = new dojo.data.ItemFileReadStore({data:data,clearOnClose:true});
	}
	else {
		chartStore.close();
		chartStore.data = data;
		chartStore._forceLoad();
	}
	if (chart) {
		if (chart.chars) {
			dojo.forEach(chart.chars, function(name) {
				chart.removeSeries(name);
			});
		}
		chart.chars = data.chars;
		chart.addAxis("x", {
			includeZero: true,
			natural: true,
			labels: dojo.map(chartStore._getItemsArray(), function(item, index){
				return {
					value: index + 1,
					text:  item.datePeriod
				}
			})
		});
		dojo.forEach(chart.chars, function(name) {
			chart.addSeries(name, new dojox.charting.DataSeries(chartStore,
				{query:{}}, dojo.hitch(null, valTrans, name)));
		});
		chart.render();
	}
}
function changeChart(type){
	if (!chartStore) {
		return;
	}

	if (!chart) {
		chart = new dojox.charting.Chart("chart");
		chart.setTheme(dojox.charting.themes.ThreeD);
		chart.chars = chartStore._jsonData.chars;
		var legend = new dojox.charting.widget.Legend({chart: chart}, "chart_legend");
		dojo.connect(chart, "render", legend, "refresh");
		if (!type) type = "lines";
	}
	
	if (type == "lines") {
		chart.addAxis("x", {
			includeZero: true,
			natural: true,
			labels: dojo.map(chartStore._getItemsArray(), function(item, index){
				return {
					value: index + 1,
					text:  item.datePeriod
				}
			})
		});
		chart.addAxis("y", {
			title: "数量（个）", 
			vertical: true,
			natural: true,
			includeZero: true
		});
		chart.addPlot("default", {type: dojox.charting.plot2d.Markers});
		dojo.forEach(chart.chars, function(name) {
			chart.addSeries(name, new dojox.charting.DataSeries(chartStore,
				{query:{}}, dojo.hitch(null, valTrans, name)));
		});
		new dojox.charting.action2d.Magnify(chart,"default");
		new dojox.charting.action2d.Tooltip(chart,"default");
		chart.render();
	}
	else if (type == "cols") {
		chart.addAxis("x", {
			includeZero: true,
			natural: true,
			labels: dojo.map(chartStore._getItemsArray(), function(item, index){
				return {
					value: index + 1,
					text:  item.datePeriod
				}
			})
		});
		chart.addAxis("y", {
			title: "数量（个）", 
			vertical: true,
			natural: true,
			includeZero: true
		});
		chart.addPlot("default", {type: dojox.charting.plot2d.ClusteredColumns, gap: 10});
		dojo.forEach(chart.chars, function(name) {
			chart.addSeries(name, new dojox.charting.DataSeries(chartStore,
				{query:{}}, dojo.hitch(null, valTrans, name)));
		});
		new dojox.charting.action2d.Shake(chart, "default", {shiftY: 0});
		new dojox.charting.action2d.Tooltip(chart,"default");
		chart.render();
	}
	else if (type == "pie") {
		chart.removeAxis("x");
		chart.removeAxis("y");
		chart.addPlot("default", {type: dojox.charting.plot2d.Pie, radius: 125});
		dojo.forEach(chart.chars, function(name) {
			chart.addSeries(name, new dojox.charting.DataSeries(chartStore,
				{query:{}}, dojo.hitch(null, valTrans, name)));
		});
		new dojox.charting.action2d.MoveSlice(chart);
		new dojox.charting.action2d.Tooltip(chart,"default");
		chart.render();
	}
	else {
		return;
	}
};

// 选项
function initSelector() {
	var datenow = new Date();
	var select_year = $("select_year");
	select_year.innerHTML = "";
	for (var i=2012; i<= datenow.getFullYear(); ++i) {
		var y=document.createElement('option');
		y.value=i;
		y.text=i+'年';
		select_year.add(y);
	}
	select_year.selectedIndex = select_year.length-1;
	
	var select_dateFlag = $("select_dateFlag");
	select_dateFlag.innerHTML = "";
	{
		var y=document.createElement('option');
		y.value = 1;
		y.text = '年度';
		select_dateFlag.add(y);
	}
	{
		var y=document.createElement('option');
		y.value = 2;
		y.text = '半年度';
		select_dateFlag.add(y);
	}
	{
		var y=document.createElement('option');
		y.value = 3;
		y.text = '季度';
		select_dateFlag.add(y);
	}
	{
		var y=document.createElement('option');
		y.value = 4;
		y.text = '月度';
		select_dateFlag.add(y);
	}
	select_dateFlag.selectedIndex = 3;

	var select_equipTag = $("select_equipTag");
	select_equipTag.innerHTML = "";
	{
		var y=document.createElement('option');
		y.value = 1;
		y.text = "装备型号";
		select_equipTag.add(y);
	}
	{
		var y=document.createElement('option');
		y.value = 2;
		y.text = "装备名称";
		select_equipTag.add(y);
	}
	{
		var y=document.createElement('option');
		y.value = 3;
		y.text = "装备编号";
		select_equipTag.add(y);
	}

<%	if (userlevel != 3)
	{%>
	require([
		"dojo/dom", 
		"my_ext/CheckboxTree", // "dijit/Tree", 
		"dojo/data/ItemFileWriteStore", 
		"dijit/tree/ForestStoreModel",
		"dojo/domReady!"
		], function(dom, CheckboxTree, ItemFileWriteStore, ForestStoreModel) {
			var url = "./QueryDepartmentServlet";
			var treeStore = new ItemFileWriteStore({
				url: url,
				urlPreventCache: true
			});
			var treeModel = new ForestStoreModel({
					store: treeStore,
					query: {"root": true},
					childrenAttrs: ["children"]
				});
			var tree = new CheckboxTree({
					id : "select_depart",
					model : treeModel,
					showRoot : false,
					openOnClick : false,
					setCheckboxOnClick : true
				}, 'select_depart');
			tree.getIconClass = function() {
					return "";
				}
			tree.startup();
	});
<%	}%>
}

<%	if (userlevel != 3)
	{%>
function set_chart_adv() {
	if ($('chart_adv').style.display == 'none') {
		dojo.fx.wipeIn({
				node: 'chart_adv',
				duration: 500
			}).play();
		var select_depart = dijit.byId('select_depart');
		if (select_depart)
			select_depart.resize();
	}
	else {
		dojo.fx.wipeOut({
				node: 'chart_adv',
				duration: 500
			}).play();
	}
}
<%	}%>

function changeInfo() {
	var str = "";
	var dfidx = $("select_dateFlag").selectedIndex;
	if (dfidx == 2 || dfidx == 3) {
		str += $("select_year").options[$("select_year").selectedIndex].text;
	}
	str += $("select_dateFlag").options[dfidx].text;
	str += " ";

	var eqval = $("edit_equipInfo").value;
	if (eqval && eqval.length > 0) {
		str += $("select_equipTag").options[$("select_equipTag").selectedIndex].text + "为【" + eqval + "】的";
	}

<%	if (userlevel != 3)
	{%>
	var fdflag1 = dijit.byId("fault_done_flag1");
	var fdflag2 = dijit.byId("fault_done_flag2");
	if (fdflag1 && fdflag2) {
		var v1 = fdflag1.get('value');
		var v2 = fdflag2.get('value');
		if (!v1 && !v2) {
			str += "全部";
		}
		else if (v1 && !v2) {
			str += "未处理";
		}
		else if (!v1 && v2) {
			str += "已处理";
		}
		else {
			var tree = dijit.byId("select_depart");
			if (tree) {
				tree.model.store.fetch({
						query: {'checked': true},
						queryOptions: {deep:true},
						onItem: function(item, i) {
							switch (tree.model.store.getValue(item, 'level')) {
							case 1:
								str += "团级用户【" + tree.model.store.getValue(item, 'name') + "】所处理的";
								break;
							case 2:
								str += "集团军级用户【" + tree.model.store.getValue(item, 'name') + "】所处理的";
								break;
							case 3:
								str += "军级用户【" + tree.model.store.getValue(item, 'name') + "】所处理的";
								break;
							case 4:
								str += "维修中心用户【" + tree.model.store.getValue(item, 'name') + "】所处理的";
								break;
							default:
								break;
							}
						}
					});
			}
		}
	}
<%	}%>

	str += "故障单统计情况：";
	$("chart_info").innerHTML = str;
}

function submitFormChart() {
	showProgress("统计数据");

	var departs = "",
		state = 0;

<%	if (userlevel != 3)
	{%>
	var departsNum = 0;
	var departLevel = 0;
	var tree = dijit.byId("select_depart");
	if (tree) {
		tree.model.store.fetch({
				query: {'checked': true},
				queryOptions: {deep:true},
				onItem: function(item, i) {
					departLevel = tree.model.store.getValue(item, 'level');
					departs += ";" + departLevel + "-" + tree.model.store.getValue(item, 'name');
					departsNum++;
				}
			});
	}
	var fdflag1 = dijit.byId("fault_done_flag1");
	var fdflag2 = dijit.byId("fault_done_flag2");
	if (fdflag1 && fdflag1.get('value')) {
		if (departLevel == 1) {
			alert("团级部门不可选择未处理表单复选框！");
			hideProgress();
			return false;
		}
		state += 1;
	}
	if (fdflag2 && fdflag2.get('value'))
		state += 10;
	if (departsNum > 1 && state == 11) {
		alert("在选择了多个部门后，您不能同时选择已处理表单和未处理表单复选框！");
		hideProgress();
		return false;
	}
<%	}%>
	
	dojo.xhrPost({
		url: "./StatGraphServlet",
		form: "form_chart",
		content: {
			state: state,
			departName: departs
		},
		handleAs: "json",
		preventCache: true,
		load: function(data) {
			if (!data) {
				alert("未知错误！请重新统计！");
			}
			else if (data.ret != 0) {
				alert(data.msg);
			}
			else {
				// 判断是否横坐标数为0
				if (data.items.length < 1) {
					alert("统计结果异常！横坐标出错！");
					return;
				}
				// 判断是否全部为0
				var allzero = true;
				for(var j=0; j<data.chars.length; ++j) {
					for(var i=0; i<data.items.length; ++i) {
						if (data.items[i][data.chars[j]] > 0) {
							allzero = false;
							break;
						}
					}
				}
				if (allzero) {
					alert("统计结果为0！");
				}
				
				changeInfo();
				changeStore(data);
				changeChart();
			}
			hideProgress();
		},
		error: function() {
			//
			alert("出了点问题，统计没有成功呢！");
			hideProgress();
		},
		sync: false
	});
	return false;
}
</script>

<style>
.chart_tj {width:600px;background-color:#F0F0F0;margin:0 auto;padding:10px;}
.chart_choose {width:600px;height:30px;background-color:#F0F0F0;margin:0 auto;padding:10px 10px 0;}
.chart_choose ul {position:relative;margin:0;}
.chart_choose ul li {position:relative;float:left;margin:0 2px;height:29px;font-size:14px;cursor:pointer;}
.chart_choose ul li span {z-index:0;color:#307D74;padding:7px 11px;font-family:黑体;}
.chart_choose ul li.current {z-index:2;font-weight:700;cursor:default;}
.chart_choose ul li.current span {background-color:#FFF;border:1px solid #F0F0F0;}
#chart_adv table {width: 100%;}
#chart_adv table td {border: 1px dotted green;padding:3px;}

.dojoxLegendNode {border:1px solid green;margin:5px 10px 5px 10px;padding:3px;}
.dojoxLegendText {vertical-align:text-top;padding-right:10px}
#chart_legend {float:right;margin:5px 20px;}
#chart_info {margin:5px;text-align:center;font-size:14px;}
#chart_area {border:1px solid green;width:600px;height:380px;margin:3px auto;padding:5px;}
#chart {clear:both;width:600px;height:300px;margin:0 auto;}
</style>

<div id="main_tb" class="tab_page" data-dojo-type="dijit.layout.ContentPane"
	data-dojo-props='title:"统计图表", onShow:function(){submitFormChart();if(chart)chart.resize();}'>

<span class="TableTitle">
	<h1>故障统计</h1>
</span>

<div class="chart_tj">
	<form id="form_chart" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
		onSubmit:function(){
			return submitFormChart();
		}'>
		<div>
			<table><tr><td style="width:500px;">
				<select id="select_year" name="year" style="min-width:75px">
				</select>
				<select id="select_dateFlag" name="dateFlag" style="min-width:75px">
				</select>
				<select id="select_equipTag" name="equipTag" style="min-width:75px">
				</select>
				<input id="edit_equipInfo" name="equipInfo" type="text" />
				<button type="submit">统计</button>
			</td><td style="width:50px;">
<%	if (userlevel != 3)
	{%>
				<a href="javascript:void(0);" onclick="set_chart_adv();">高级</a>
<%	}%>
			</td><td style="width:50px;">
				<a href="javascript:void(0);" onclick="JPrint.print('chart_area');">打印</a>
			</td></tr></table>
		</div>
<%	if (userlevel != 3)
	{%>
		<div id="chart_adv" style="display:none">
			<table><tr><td style="width:50%;">
				<div id="select_depart"></div>
			</td><td style="width:50%;line-height:22px;">
				<input id="fault_done_flag1" data-dojo-type="dijit.form.CheckBox"
					data-dojo-props='name:"fbrpway1" '/>
					<label for="fault_done_flag1">未处理表单</label>
				<br />
				<input id="fault_done_flag2" data-dojo-type="dijit.form.CheckBox"
					data-dojo-props='name:"fbrpway2" '/>
					<label for="fault_done_flag2">已处理表单</label>
				<p>注：如果您在左边的树中选择了多个部门，则此处复选框最多只能选择一项！</p>
			</td></tr></table>
		</div>
<%	}%>
	</form>
</div>
<div class="chart_choose">
	<script type="text/javascript">
		function setLiCurrent(node) {
			if (node == 'li_lines') {
				$('li_lines').className = 'current';
				$('li_cols').className = '';
				$('li_pie').className = '';
			}
			else if (node == 'li_cols') {
				$('li_lines').className = '';
				$('li_cols').className = 'current';
				$('li_pie').className = '';
			}
			else if (node == 'li_pie') {
				$('li_lines').className = '';
				$('li_cols').className = '';
				$('li_pie').className = 'current';
			}
		}
	</script>
	<ul>
		<li id="li_lines" class="current" onclick="changeChart('lines');setLiCurrent(this.id);"><span>折线图</span></li>
		<li>|</li>
		<li id="li_cols" onclick="changeChart('cols');setLiCurrent(this.id);"><span>柱状图</span></li>
		<li>|</li>
		<li id="li_pie" onclick="changeChart('pie');setLiCurrent(this.id);"><span>饼状图</span></li>
	</ul>
</div>
<div id="chart_area">
	<div id="chart_info"></div>
	<div id="chart_legend"></div>
	<div id="chart"></div>
</div>
</div>
