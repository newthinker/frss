
dojo.require("dojo.parser");
dojo.require("dojo.io.iframe");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.data.ItemFileWriteStore");

dojo.require("dijit.dijit");
dojo.require("dijit.Tree");
dojo.require("dijit.Menu");
dojo.require("dijit.Dialog");
dojo.require("dijit.TitlePane");
dojo.require("dijit.ProgressBar");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.tree.ForestStoreModel");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.form.SimpleTextarea");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojox.form.Rating");
dojo.require("dojox.form.PasswordValidator");

dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Pagination");
dojo.require("dojox.grid.enhanced.plugins.Menu");
dojo.require("dojox.data.QueryReadStore");

dojo.require("dojox.charting.Chart");
dojo.require("dojox.charting.DataSeries");
dojo.require("dojox.charting.themes.ThreeD");
dojo.require("dojox.charting.widget.Legend");
dojo.require("dojox.charting.axis2d.Default");
dojo.require("dojox.charting.plot2d.Markers");
dojo.require("dojox.charting.plot2d.Columns");
dojo.require("dojox.charting.plot2d.ClusteredColumns");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Magnify");
dojo.require("dojox.charting.action2d.Shake");


function $(id) {
	return "string" == typeof id ? document.getElementById(id) : id;
}
function html_encode(str)
{
	var s = "";
	if (str.length == 0) return "";
	s = str.replace(/&/g, "&gt;");
	s = s.replace(/</g, "&lt;");
	s = s.replace(/>/g, "&gt;");
	s = s.replace(/ /g, "&nbsp;");
	s = s.replace(/\'/g, "&#39;");
	s = s.replace(/\"/g, "&quot;");
	s = s.replace(/\n/g, "<br>");
	return s;
}

function html_decode(str)
{
	var s = "";
	if (str.length == 0) return "";
	s = str.replace(/&gt;/g, "&");
	s = s.replace(/&lt;/g, "<");
	s = s.replace(/&gt;/g, ">");
	s = s.replace(/&nbsp;/g, " ");
	s = s.replace(/&#39;/g, "\'");
	s = s.replace(/&quot;/g, "\"");
	s = s.replace(/<br>/g, "\n");
	return s;
}
function checkRet(data) {
	try {
		if (data.ret != 0) {
			alert(data.msg);
			window.location = "./index.jsp";
		}
		return data;
	}catch(e){
		alert("请刷新页面！");
		window.location = "./index.jsp";
	}
}

function createTree(type) {
	var tree = dijit.byId(type);
	if (!tree) {
		var url = "./TreeServlet?t="+type;
		var treeStore = new dojo.data.ItemFileReadStore({
			url: url,
			urlPreventCache: true
		});

		var treeModel = new dijit.tree.ForestStoreModel({
			store: treeStore,
			query: {
				"root": true
			}
		});

		tree = new dijit.Tree({
			id : type,
			model : treeModel,
			labelAttr : "name",
			urlAttr : "url",
			showRoot : false,
			openOnClick : true,
			openOnDblClick : true,
			onClick : clickTreeItem
		}, type);

		tree.getIconClass = function() {
			return "";
		}
	}
}

function showProgress(text) {
	if (!text) {
		dojo.byId("text_progress").innerHTML = "请稍等...";
	}
	else {
		dojo.byId("text_progress").innerHTML = "正在" + text + "，请稍等...";
	}
	dijit.byId("dlg_progress").show();
}
function hideProgress() {
	dijit.byId("dlg_progress").hide();
}

dojo.addOnLoad(function(){
	createTree("formTree_noProcessed");
	createTree("formTree_processed");
	//createTree("faultCommit_manage");
	//createTree("faultSearch");
	createTree("user_manage");
});

dijit.Dialog._DialogLevelManager.setTop = function(dialog) {
	var ds = dijit.Dialog._dialogStack;
	var idx = dojo.indexOf(dojo.map(ds, function(elem){return elem.dialog}), dialog);
	if(idx == -1 || idx == ds.length-1) {
		return;
	}

	var tmp = ds[ds.length-1].dialog;
	if (tmp == dialog) {
		return;
	}

	ds[idx].dialog = tmp;
	ds[idx].underlayAttrs = tmp.underlayAttrs;
	ds[ds.length-1].dialog = dialog;
	ds[ds.length-1].underlayAttrs = dialog.underlayAttrs;

	dijit._underlay.set(dialog.underlayAttrs);

	//var zIndex = ds[ds.length-1].zIndex;
	dojo.setStyle(ds[ds.length-1].dialog.domNode, 'zIndex', ds[ds.length-1].zIndex);
	dojo.setStyle(ds[idx].dialog.domNode, 'zIndex', ds[idx].zIndex);
}

function setTop(dlg) {
	if (dlg) {
		//dijit.Dialog._DialogLevelManager.show(dlg, dlg.underlayAttrs);
		dijit.Dialog._DialogLevelManager.setTop(dlg);
	}
}

// search xhr arg
var searchXhr;
// grid data from search or not
var besearch = false;

var strStates = new Array(7);
	strStates[0] = "<br>等待一级审核";
	strStates[1] = "<br>等待二级审核";
	strStates[2] = "<br>等待格式审核";
	strStates[3] = "<br>等待任务分发";
	strStates[4] = "<br>等待处理反馈";
	strStates[5] = "<br>等待电话确认";
	strStates[6] = "<br>";
var strActions = new Array(7);
	strActions[0] = "提交订单";
	strActions[1] = "一级审核";
	strActions[2] = "二级审核";
	strActions[3] = "格式审核";
	strActions[4] = "任务分发";
	strActions[5] = "维修处理";
	strActions[6] = "完成";

function GetProcByState(step, state, node) {
	step = Number(step);
	var strInner = "";
	if (step < 0 || step > 6) {
		dojo.byId(node).innerHTML = strInner;
	}
	for (var i=0; i<step; i++) {
		strInner += "<div class='node ready'><ul><li class='tx1'>";
		strInner += "</li><li class='tx2'>";
		strInner += strActions[i];
		strInner += "</li></ul></div>\r\n";
		strInner += "<div class='proce ready'><ul><li class='tx1'>&nbsp;</li></ul></div>";
	}
	{
		strInner += "<div class='node ";
		if (state == 2)
			strInner += "singular";
		else
			strInner += "ready";
		strInner += "'><ul><li class='tx1'>";
		if (state == 2)
			strInner += "打回";
		else
			strInner += strStates[step];
		strInner += "</li><li class='tx2'>";
		strInner += strActions[step];
		strInner += "</li></ul></div>\r\n";
	}
	for (var i=step+1; i<7; i++) {
		strInner += "<div class='proce wait'><ul><li class='tx1'>&nbsp;</li></ul></div>";
		strInner += "<div class='node wait'><ul><li class='tx1'>";
		strInner += "</li><li class='tx2'>";
		strInner += strActions[i];
		strInner += "</li></ul></div>\r\n";
	}

	dojo.byId(node).innerHTML = strInner;
}

function show_fault_info(data) {
	var ordertype = GetTypeFromOrderid(data.orderid);
	if (ordertype == 1) {
		GetProcByState(data.step, data.state, "process_fault");

		var strInner = "";
		// 故障信息
		{
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>故障单号</th><td>" + html_encode(data.orderid) + "</td>";
			strInner += "<th>故障发生时间</th><td>" + html_encode(data.zbgzsj) + "</td></tr>\r\n";
			strInner += "<tr><th>装备型号</th><td>" + html_encode(data.zbtype) + "</td>";
			strInner += "<th>装备名称</th><td>" + html_encode(data.zbname) + "</td></tr>\r\n";
			strInner += "<tr><th>装备编号</th><td>" + html_encode(data.zbserial) + "</td>";
			strInner += "<th>装备数量</th><td>" + html_encode(data.zbnum) + "</td></tr>\r\n";
			strInner += "<tr><th>故障现象</th><td colspan=\"3\">" + html_encode(data.zbgzxx) + "</td></tr>\r\n";
			strInner += "<tr><th>装备先期处理情况</th><td colspan=\"3\">" + html_encode(data.zbxqcl) + "</td></tr>\r\n";
			strInner += "<tr><th>故障发生频次</th><td>" + html_encode(data.zbgzpc) + "</td>";
			strInner += "<th>故障产生部位</th><td>" + html_encode(data.zbgzbw) + "</td></tr>\r\n";
			strInner += "<tr><th>装备使用单位</th><td colspan=\"3\">" + html_encode(data.zbsydw) + "</td></tr>\r\n";
			strInner += "<tr><th>使用人</th><td>" + html_encode(data.zbuser) + "</td>";
			strInner += "<th>记录人</th><td>" + html_encode(data.commiter) + "</td></tr>\r\n";
			strInner += "<tr><th>连络方式</th><td>" + html_encode(data.contact) + "</td>";
			strInner += "<th>报修时间</th><td>" + html_encode(data.committime) + "</td></tr>\r\n";
			strInner += "<tr><th>取证照片</th><td colspan=\"3\">";
			if (data.zbqzzp && data.zbqzzp.length > 0) {
				strInner += "<img style='max-width:400px;max-height:300px' src='" + html_encode(data.zbqzzp) + "' />";
			}
			else {
				strInner += "无";
			}
			strInner += "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info").innerHTML = strInner;
		}
		// 审核
		if (data.step > 0) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>一级审核人</th><td>" + html_encode(data.l1checker) + "</td>";
			strInner += "<th>审核时间</th><td>" + html_encode(data.l1time) + "</td></tr>\r\n";
			strInner += "<tr><th>审核结果</th><td colspan=\"3\">";
			if (data.step > 1 || data.state < 2) {
				strInner += "通过";
			}
			else {
				strInner += "打回";
			}
			strInner += "</td></tr>\r\n";
			strInner += "<tr><th>审核意见</th><td colspan=\"3\">" + html_encode(data.l1opinion) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_l1audit").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_l1audit").innerHTML = "";
		}
		if (data.step > 1) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>二级审核人</th><td>" + html_encode(data.l2checker) + "</td>";
			strInner += "<th>审核时间</th><td>" + html_encode(data.l2time) + "</td></tr>\r\n";
			strInner += "<tr><th>审核结果</th><td colspan=\"3\">";
			if (data.step > 2 || data.state < 2) {
				strInner += "通过";
			}
			else {
				strInner += "打回";
			}
			strInner += "</td></tr>\r\n";
			strInner += "<tr><th>审核意见</th><td colspan=\"3\">" + html_encode(data.l2opinion) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_l2audit").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_l2audit").innerHTML = "";
		}
		if (data.step == 2 && data.state == 0 && userinfo['level'] == 7) {
			dojo.byId("fault_info_confirm").style.display = "block";
		}
		else {
			dojo.byId("fault_info_confirm").style.display = "none";
		}
		// 分发派遣
		if (data.step > 3) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>故障单号</th><td colspan=\"3\">" + html_encode(data.orderid) + "</td></tr>\r\n";
			strInner += "<tr><th>派遣单位</th><td colspan=\"3\">" + html_encode(data.dpunit) + "</td></tr>\r\n";
			strInner += "<tr><th>派遣时间</th><td colspan=\"3\">" + html_encode(data.dptime) + "</td></tr>\r\n";
			strInner += "<tr><th>保障部队</th><td colspan=\"3\">" + html_encode(data.dpsafeunit) + "</td></tr>\r\n";
			strInner += "<tr><th>装备型号</th><td colspan=\"3\">" + html_encode(data.zbtype) + "</td></tr>\r\n";
			strInner += "<tr><th>装备名称</th><td colspan=\"3\">" + html_encode(data.zbname) + "</td></tr>\r\n";
			strInner += "<tr><th>部队地点</th><td colspan=\"3\">" + html_encode(data.dpaddr) + "</td></tr>\r\n";
			strInner += "<tr><th>部队联系人</th><td colspan=\"3\">" + html_encode(data.dpcontact) + "</td></tr>\r\n";
			strInner += "<tr><th>故障现象描述</th><td colspan=\"3\">" + html_encode(data.zbgzxx) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_dispatch").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_dispatch").innerHTML = "";
		}
		// 反馈
		if (data.step > 4) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>故障单号</th><td colspan=\"3\">" + html_encode(data.orderid) + "</td></tr>\r\n";
			strInner += "<tr><th>反馈时间</th><td colspan=\"3\">" + html_encode(data.fbtime) + "</td></tr>\r\n";
			strInner += "<tr><th>装备型号</th><td>" + html_encode(data.zbtype) + "</td>";
			strInner += "<th>装备名称</th><td>" + html_encode(data.zbname) + "</td></tr>\r\n";
			strInner += "<tr><th>故障发生时间</th><td>" + html_encode(data.zbgzsj) + "</td>";
			strInner += "<th>故障派修</th><td>" + html_encode(data.fbdispatch) + "</td></tr>\r\n";
			strInner += "<tr><th>维修内容</th><td colspan=\"3\">" + html_encode(data.fbrpcontent) + "</td></tr>\r\n";
			strInner += "<tr><th>故障原因</th><td colspan=\"3\">" + html_encode(data.fbgzreason) + "</td></tr>\r\n";
			strInner += "<tr><th>维修手段</th><td colspan=\"3\">";
			switch (Number(data.fbrpway)) {
			case 1:
				strInner += "现场维修";
				break;
			case 2:
				strInner += "专家远程支持";
				break;
			case 3:
				strInner += "现场维修和远程支持";
				break;
			default:
				strInner += "无";
				break;
			}
			strInner += "</td></tr>\r\n";
			strInner += "<tr><th>维修结果</th><td colspan=\"3\">" + html_encode(data.fbrpresult) + "</td></tr>\r\n";
			strInner += "<tr><th>维修质量</th><td>";
			switch (Number(data.fbrpquality)) {
			case 1:
				strInner += "非常不满意";
				break;
			case 2:
				strInner += "不满意";
				break;
			case 3:
				strInner += "一般";
				break;
			case 4:
				strInner += "满意";
				break;
			case 5:
				strInner += "非常满意";
				break;
			default:
				strInner += "";
				break;
			}
			strInner += "</td>";
			strInner += "<th>维修时间</th><td>" + html_encode(data.fbrptime) + "</td></tr>\r\n";
			strInner += "<tr><th>维修使用备件名称</th><td>" + html_encode(data.fbrpbjname) + "</td>";
			strInner += "<th>维修使用备件型号</th><td>" + html_encode(data.fbrpbjtype) + "</td></tr>\r\n";
			strInner += "<tr><th>备件来源情况</th><td colspan=\"3\">" + html_encode(data.fbrpbjfrom) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_feedback").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_feedback").innerHTML = "";
		}
		// 远程支援
		if (data.fbrpway == 2 || data.fbrpway == 3) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>故障单号</th><td colspan=\"3\">" + html_encode(data.orderid) + "</td></tr>\r\n";
			strInner += "<tr><th>远程支援通道</th><td colspan=\"3\">" + html_encode(data.rschannel) + "</td></tr>\r\n";
			strInner += "<tr><th>远程支援方式</th><td colspan=\"3\">" + html_encode(data.rstype) + "</td></tr>\r\n";
			strInner += "<tr><th>远程支援单位</th><td colspan=\"3\">" + html_encode(data.rsunit) + "</td></tr>\r\n";
			strInner += "<tr><th>远程支援专家</th><td colspan=\"3\">" + html_encode(data.rsexpert) + "</td></tr>\r\n";
			strInner += "<tr><th>联络人</th><td colspan=\"3\">" + html_encode(data.rscontacter) + "</td></tr>\r\n";
			strInner += "<tr><th>联络方式</th><td colspan=\"3\">" + html_encode(data.rscontactway) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_remotesup").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_remotesup").innerHTML = "";
		}
		// 回访
		if (data.step > 5) {
			strInner = "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>故障单号</th><td colspan=\"3\">" + html_encode(data.orderid) + "</td></tr>\r\n";
			strInner += "<tr><th>回访员</th><td>" + html_encode(data.re_reporter) + "</td>";
			strInner += "<th>回访时间</th><td>" + html_encode(data.re_reporttime) + "</td></tr>\r\n";
			strInner += "<tr><th>被访人</th><td>" + html_encode(data.re_client) + "</td>";
			strInner += "<th>联系方式</th><td>" + html_encode(data.re_contact) + "</td></tr>\r\n";
			strInner += "<tr><th>回访手段</th><td>";
			switch (Number(data.re_reviewway)) {
			case 1:
				strInner += "电话回访";
				break;
			case 2:
				strInner += "传真回访";
				break;
			case 3:
				strInner += "邮件回访";
				break;
			default:
				strInner += "";
				break;
			}
			strInner += "</td>";
			strInner += "<th>维修质量</th><td>";
			switch (Number(data.re_quality)) {
			case 1:
				strInner += "非常不满意";
				break;
			case 2:
				strInner += "不满意";
				break;
			case 3:
				strInner += "一般";
				break;
			case 4:
				strInner += "满意";
				break;
			case 5:
				strInner += "非常满意";
				break;
			default:
				strInner += "";
				break;
			}
			strInner += "</td></tr>\r\n";
			strInner += "<tr><th>维修描述</th><td colspan=\"3\">" + html_encode(data.re_disc) + "</td></tr>\r\n";
			strInner += "</table>\r\n";
			dojo.byId("fault_info_phoneback").innerHTML = strInner;
		}
		else {
			dojo.byId("fault_info_phoneback").innerHTML = "";
		}

		var dlg = dijit.byId("dlg_fault_info");
		dlg.data = data;
		dlg.show();
	}
	else {
		GetProcByState(data.step, data.state, "process_backup");

		var strInner = "";
		// 备件信息
		{
			strInner += "<table style='table-layout:fixed'>\r\n";
			strInner += "<tr><th>备件型号</th><td>" + html_encode(data.bjtype) + "</td>";
			strInner += "<th>备件名称</th><td>" + html_encode(data.bjname) + "</td></tr>\r\n";
			strInner += "<tr><th>备件编号</th><td>" + html_encode(data.bjserial) + "</td>";
			strInner += "<th>备件数量</th><td>" + html_encode(data.bjnum) + "</td></tr>\r\n";
			strInner += "<tr><th>备件使用装备名称</th><td>" + html_encode(data.zbtype) + "</td>";
			strInner += "<th>备件使用装备型号</th><td>" + html_encode(data.zbname) + "</td></tr>\r\n";
			strInner += "<tr><th>备件使用装备部位</th><td>" + html_encode(data.bjpart) + "</td>";
			strInner += "<th>维修申请单号</th><td>" + html_encode(data.bjfaultid) + "</td></tr>\r\n";
			strInner += "<tr><th>装备使用单位</th><td colspan=\"3\">" + html_encode(data.zbsydw) + "</td></tr>\r\n";
			strInner += "<tr><th>使用人</th><td>" + html_encode(data.zbuser) + "</td>";
			strInner += "<th>记录人</th><td>" + html_encode(data.commiter) + "</td></tr>\r\n";
			strInner += "<tr><th>连络方式</th><td>" + html_encode(data.contact) + "</td>";
			strInner += "<th>报修时间</th><td>" + html_encode(data.committime) + "</td></tr>\r\n";
			dojo.byId("fault_info").innerHTML = strInner;
		}
		// 审核
		dijit.byId("dlg_backup_info").show();
	}
}

function audit_fault(item) {
	var dlg = dijit.byId("dlg_audit_form");
	if (dlg) {
		var ordertype = GetTypeFromOrderid(item.orderid);
		if (ordertype == 1) {
			if (item.step == 0) {
				dlg.reporttype = 1;
			}
			else if (item.step == 1) {
				dlg.reporttype = 2;
			}
			else {
				//error
				alert("已经审核过了");
				return;
			}
		}
		else  {
			if (item.step == 0) {
				dlg.reporttype = 3;
			}
			else if (item.step == 1) {
				dlg.reporttype = 4;
			}
			else {
				//error
				alert("已经审核过了");
				return;
			}
		}
		dlg.keyid = item.orderid;
		dlg.data = item;
		//dojo.byId("audit_user").value = userinfo["name"];
		//dojo.byId("audit_time").value = dojo.date.locale.format(new Date(),
		//	{timePattern:'yyyy-MM-dd-HH-mm-ss',selector:'time'});
		dlg.show();
	}
}

function dispatch_fault(item) {
	showProgress("获取故障单详细信息");
	var dlg = dijit.byId("dlg_dispatch_form");
	if (dlg) {
		dojo.xhrGet({
			url: "./QueryDetailServlet?orderid=" + item.orderid,
			handleAs: "json",
			preventCache: true,
			load: function(data) {
				data = checkRet(data);
				dlg.data = data;
				dlg.keyid = data.orderid;
				dojo.byId("dispatch_zbinfoid").value = data.orderid;
				dojo.byId("dispatch_zbtype").value = data.zbtype;
				dojo.byId("dispatch_zbname").value = data.zbname;
				dojo.byId("dispatch_describe").value = data.zbgzxx;
				hideProgress();
				dlg.show();
			},
			error: function() {
				//
				if (ordertype == 1) {
					alert("获取故障维修详细信息失败！");
					return;
				}
				else {
					alert("获取备件申请详细信息失败！");
					return;
				}
				hideProgress();
			},
			sync: false
		});
	}
}

function accept_mission(item) {
	showProgress("获取故障单详细信息");
	var dlg = dijit.byId("dlg_feedback_form");
	if (dlg) {
		dojo.xhrGet({
			url: "./QueryDetailServlet?orderid=" + item.orderid,
			handleAs: "json",
			preventCache: true,
			load: function(data) {
				data = checkRet(data);
				dlg.data = data;
				dojo.byId("feedback_repairid").value = data.orderid;
				dojo.byId("feedback_zbinfoid").value = data.zbtype;
				dojo.byId("feedback_zbname").value = data.zbname;
				dojo.byId("feedback_gztime").value = data.zbgzsj;
				//dojo.byId("remotesup_repairid").value = data.orderid;
				hideProgress();
				dlg.show();
			},
			error: function() {
				//
				if (ordertype == 1) {
					alert("获取故障维修详细信息失败！");
					return;
				}
				else {
					alert("获取备件申请详细信息失败！");
					return;
				}
				hideProgress();
			},
			sync: false
		});
	}
}

function confirm_done(item) {
	var dlg = dijit.byId("dlg_phoneback_form");
	if (dlg) {
		dlg.keyid = item.orderid;
		dlg.show();
	}
}

function GetTypeFromOrderid(orderid) {
	if (orderid.length < 1)
		return 0;
	if (orderid.charAt(0) == '1')
		return 1; //故障
	else if (orderid.charAt(0) == '2')
		return 2; //备件
	else
		return 0;
}

function onGridRowClick(cell) {
	var item = cell.grid.getItem(cell.rowIndex);
	var orderid = this.store.getValue(item, "orderid");
	var ordertype = GetTypeFromOrderid(orderid);
	showProgress("获取故障单详细信息");
	dojo.xhrGet({
		url: "./QueryDetailServlet?orderid=" + orderid,
		handleAs: "json",
		preventCache: true,
		load: function(data) {
			data = checkRet(data);
			hideProgress();
			show_fault_info(data);
		},
		error: function() {
			//
			if (ordertype == 1) {
				alert("获取故障维修详细信息失败！");
				return;
			}
			else {
				alert("获取备件申请详细信息失败！");
				return;
			}
			hideProgress();
		},
		sync: false
	});
}

function createAuditButton(item) {
	//jq用户、jtj用户审核
	if ((userinfo['level'] == 1 && item.step == 1) || (userinfo['level'] == 2 && item.step == 0)) {
		var w = new dijit.form.Button({
			label: "审核",
			onClick: function(){
				//console.log(item);
				audit_fault(item);
			}
		});
		w._destroyOnRemove = true;
		return w;
	}
	else
		return "";
}

function createDispatchButton(item) {
	if (userinfo['level'] != 4) //维修中心才能分发
		return "";

	var menu = new dijit.Menu({ });
	menu.domNode.style.display="none";
	dojo.xhrGet({
		url: "./QueryFactoryServlet?faultid=" + item.orderid,
		handleAs: "json",
		preventCache: true,
		load: function(data) {
			if (!data) {
				alert("未知错误！请重新提交！");
			}
			else if (data.ret != 0) {
				alert(data.msg);
			}
			else {
				for (var i=0; i<data.items.length; i++) {
					menu.addChild(new dijit.MenuItem({
						label: data.items[i].name,
						item: data.items[i],
						onClick: function(){
							showProgress("提交分发请求");
							dojo.xhrPost({
								url: "./UploadDispatchServlet",
								content: {
									orderid: item.orderid,
									factoryid: this.item.id
								},
								handleAs: "json",
								preventCache: true,
								load: function(data) {
									if (!data) {
										alert("未知错误！请重新提交！");
									}
									else if (data.ret != 0) {
										alert(data.msg);
									}
									else {
										var grid = dijit.byId("grid");
										grid.query.timestamp = new Date().getTime();
										grid.setStore(grid.store, grid.query);
										alert('分发成功！\n请等候处理...');
									}
									hideProgress();
								},
								error: function() {
									//
									alert("出了点问题，提交没有成功呢！");
									hideProgress();
								},
								sync: false
							});
						}
					}));
				}
			}
			menu.addChild(new dijit.MenuItem({
				label: "手动分发...",
				onClick: function(){
					dispatch_fault(item);
				}
			}));
		},
		error: function() {
			//
			//alert("获取工业部门列表失败！");
			return;
		},
		sync: false
	});

	var w = new dijit.form.DropDownButton({
		label: "分发",
		dropDown: menu
	});
	w._destroyOnRemove = true;

	return w;
}

function createFeedbackButton(item) {
	if (userinfo['level'] != 6) //工业部门
		return "";

	var w = new dijit.form.Button({
		label: "确认",
		onClick: function(){
			//console.log(item);
			accept_mission(item);
		}
	});
	w._destroyOnRemove = true;
	return w;
}

function createConfirmButton(item) {
	if (userinfo['level'] != 7) //工业部门
		return "";

	var w = new dijit.form.Button({
		label: "电话确认",
		onClick: function(){
			//console.log(item);
			confirm_done(item);
		}
	});
	w._destroyOnRemove = true;
	return w;
}

function createHandleButton(item) {
	if (item.state > 0) {
		return "";
	}

	// 0提交、1一级审核
	if (item.step < 2) {
		//1jq用户、2jtj用户 审核
		return createAuditButton(item);
	}
	// 2二级审核
	else if (item.step < 3) {
		//7维修中心 格式审查
		//此按钮位于详情处
	}
	// 3格式审查
	else if (item.step < 4) {
		//4维修中心 分发
		return createDispatchButton(item);
	}
	// 4分发
	else if (item.step < 5) {
		//6工业部门 确认-反馈表-远程表
		return createFeedbackButton(item);
	}
	// 5反馈，完成
	else if (item.step < 6) {
		//已经完成了
		return createConfirmButton(item);
	}
	// 6error
	else {
	}
	return "";
}

/*set up layout*/
var gridLayout = [ {
	cells:[ {
		name : '状态',
		field : 'state',
		width : '40px',
		formatter: function(value) {
			switch (Number(value)) {
			case 0:
				return "<div class='undone'></div>";
				break;
			case 1:
				return "<div class='done'></div>";
				break;
			case 2:
				return "<div class='interrupt'></div>";
				break;
			}
		}
	}, {
		name : '序号',
		field : 'index',
		width : '40px'
	}, {
		name : '单号',
		field : 'orderid',
		width : '150px'
	}, {
		name : '装备型号',
		field : 'zbtype'
	}, {
		name : '装备名称',
		field : 'zbname'
	}, {
		name : '装备数量',
		field : 'zbnum'
	}, {
		name : '使用人',
		field : 'zbuser'
	}, {
		name : '提交人',
		field : 'commiter'
	}, {
		name : '提交时间',
		field : 'committime'
	}, {
		name : '处理',
		field : 'step',
		formatter: function(value, rowIndex) {
			if (besearch)
				return "";
			var item = this.grid.getItem(rowIndex);
			if (item.i)
				return createHandleButton(item.i);
			else
				return createHandleButton(item);
		}
	} ]
} ];

function createAudioHandleButton(item) {
	//维修中心处理离线语音
	if (userinfo['level'] == 7 && item.status == 0) {
		var w = new dijit.form.Button({
			label: "故障上报",
			onClick: function(){
				//console.log(item);
				dlgFault(item);
			}
		});
		w._destroyOnRemove = true;
		return w;
	}
	else
		return "";
}

var gridLayoutAudio = [ {
	cells:[ {
		name : '状态',
		field : 'status',
		width : '40px',
		formatter: function(value) {
			switch (Number(value)) {
			case 0:
				return "<div class='undone'></div>";
				break;
			case 1:
				return "<div class='done'></div>";
				break;
			case 2:
				return "<div class='interrupt'></div>";
				break;
			}
		}
	}, {
		name : '序号',
		field : 'id'
	}, {
		name : '提交人',
		field : 'reporter'
	}, {
		name : '提交时间',
		field : 'reporttime'
	}, {
		name : '下载',
		field : 'filepath',
		formatter: function(value) {
			return "<a href=\"" + value + "\" target=\"_blank\">点击下载</a>";
		}
	}, {
		name : '处理',
		field : 'status',
		formatter: function(value, rowIndex) {
			if (value == 0) {
				var item = this.grid.getItem(rowIndex);
				if (item.i)
					return createAudioHandleButton(item.i);
				else
					return createAudioHandleButton(item);
			}
			else {
				return "";
			}
		}
	} ]
} ];

var gridPlugins = {
	pagination: {
		pageSizes: [10, 25, 30, 50, 100, Infinity],// Array, custom the items per page button
		description: true,	// boolean, custom whether or not the description will be displayed
		sizeSwitch: true,	// boolean, custom whether or not the page size switch will be displayed
		pageStepper: true,	// boolean, custom whether or not the page step will be displayed
		gotoButton: true,	// boolean, custom whether or not the goto page button will be displayed
		maxPageStep: 10,	// Integer, custom how many page step will be displayed
		position: "bottom",	// String, custom the position of the pagination bar. There're two options: top, bottom
		defaultPage: 1, 	// Integer, which page will be displayed by default
		defaultPageSize: 25 // Integer, what page size will be used by default
	}
};

dojo.addOnLoad(function(){
	var grid = dijit.byId("grid");
	if (grid) {
		grid.canSort = function(index) {
			if(index == 1 || index == 8)
				return false;
			else
				return true;
		}
		var params = {};
		params.timestamp = new Date().getTime();
		grid.setStore(new dojox.data.QueryReadStore({
			url: "./QueryFaultServlet",
			requestMethod: "get",
			//clearOnClose : true,
			_filterResponse : checkRet
		}), params);
	}
});

function resizeCenter() {
	var dg = dojo.byId("mainTabcon");
	if (dg == undefined) {
		return;
	}
	var hdcc = dojo.contentBox("centerContainer").h;
	var hdct = 0;
	if (dojo.byId("centerTop"))
		hdct = dojo.contentBox("centerTop").h;
	var h = hdcc - hdct - 12;
	if (h < 0)
		return;
	dojo.style(dg, "height", (hdcc - hdct - 12) + "px");
	var dit = dijit.byId("mainTabcon");
	if (dit) {
		dit.resize();
	}
	var cg = dijit.byId("grid");
	if (cg) {
		cg.resize();
	}
}

function resizeLayout() {
	if (dojo.byId("main") == undefined) {
		return;
	}
	dojo.style(dojo.byId("main"), "height", dijit.getViewport().h + "px");
	var ma = dijit.byId("main");
	if (ma) {
		ma.resize();
	}
	resizeCenter();
}
dojo.connect(window, "onresize", resizeLayout);
dojo.addOnLoad(resizeLayout);

/************************************打印功能代码******************************/
(function(){
window.JPrint = {
	print: function (id) {
		var doc = document,
			win = window,
			container = doc.getElementById(id),
			origDisplay = [],
			origParent = container.parentNode,
			body = doc.body,
			NONE = 'none',
			childNodes = body.childNodes;
		each = function (arr, fn) {
			var i = 0,
				len = arr.length;
			for (; i < len; i++) {
				if (fn.call(arr[i], arr[i], i, arr) === false) {
					return i;
				}
			}
		};
		each(childNodes, function (node, i) {
			if (node.nodeType === 1) {
				origDisplay[i] = node.style.display;
				node.style.display = NONE;
			}
		});
		body.appendChild(container);
		this.prePrint();
		setTimeout(function () {
			win.print();
			setTimeout(function () {
				JPrint.aftPrint();
				origParent.appendChild(container);
				each(childNodes, function (node, i) {
					if (node.nodeType === 1) {
						node.style.display = origDisplay[i];
					}
				});
			}, 1000);
		}, 1000);
	},
	prePrint: function() {
	},
	aftPrint: function() {
	}
};
}());