<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	ArrayList<String> al = (ArrayList<String>) request.getSession().getAttribute("loginUser");
	int userlevel = -1;
	if (al != null) {
		userlevel = Integer.parseInt(al.get(3));
	}
%>

<%
	if (userlevel == 3 || userlevel == 7)
	{
%>
<style type="text/css">
#dlg_fault_form_underlay {display:none;}
</style>
<div id="dlg_fault_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"故障上报", onFocus:function(){setTop(this);}' >
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
		<div id="tab_fault" class="tab_page" data-dojo-type="dijit.layout.ContentPane" title="故障上报单">
			<span class="TableTitle">
				<h1>故障上报单</h1>
			</span>
			<script type="text/javascript">
			function submitFormFault() {
				dijit.byId("submit_fault").set("disabled",true);
				var dlg = dijit.byId("dlg_fault_form");
				showProgress("提交故障上报单");
				dojo.io.iframe.send({
					url: "./UploadFaultServlet" ,
					form: "form_fault" ,
					content: {
						keyid: dlg.keyid ? dlg.keyid : 0
					},
					contentType:"multipart/form-data",
					handleAs: "json" ,
					load: function(data) {
						dijit.byId("submit_fault").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("故障上报成功！");
							// add to grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_fault").reset();
							dijit.byId("dlg_fault_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_fault").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_fault" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST", encType:"multipart/form-data",
				onSubmit:function(){
					return submitFormFault();
				}'>
				<script type="dojo/connect" data-dojo-event="startup">
					dijit.byId('zbgzsj').constraints.max = new Date();
					var sb = dijit.byId("submit_fault");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table style="table-layout:fixed">
					<tr>
						<th><label for="zbtype">装备型号</label></th>
						<td><input id="zbtype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbtype", type:"text", required:true, maxLength:"32", placeholder:"装备型号"' /></td>
						<th><label for="zbname">装备名称</label></th>
						<td><input id="zbname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbname", type:"text", required:true, maxLength:"32", placeholder:"装备名称"' /></td>
					</tr>
					<tr>
						<th><label for="zbserial">装备编号</label></th>
						<td><input id="zbserial" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbserial", type:"text", required:true, maxLength:"32", placeholder:"装备编号"' /></td>
						<th><label for="zbnum">装备数量</label></th>
						<td><input id="zbnum" class="editbox" data-dojo-type="dijit.form.NumberTextBox"
							data-dojo-props='name:"zbnum", type:"number", required:true, maxLength:"5", value:"1"' /></td>
					</tr>
					<tr style="height:auto;">
						<th><label for="zbgzxx">故障现象</label></th>
						<td colspan="3">
							<textarea id="zbgzxx" style="padding:0px;width:100%" data-dojo-type="dijit.form.SimpleTextarea"
								data-dojo-props='name:"zbgzxx", type:"text", rows:"5", required:true, maxLength:"80"' ></textarea>
						</td>
					</tr>
					<tr>
						<th><label for="zbgzsj">故障发生时间</label></th>
						<td colspan="3">
							<input id="zbgzsj" class="editbox" data-dojo-type="dijit.form.DateTextBox"
								data-dojo-props='name:"zbgzsj", type:"text", required:true' />
						</td>
					</tr>
					<tr>
						<th><label for="zbxqcl">先期处理情况</label></th>
						<td colspan="3">
							<input id="zbxqcl" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"zbxqcl", type:"text", required:true, maxLength:"80", placeholder:"装备先期处理情况"' />
						</td>
					</tr>
					<tr>
						<th><label for="zbgzpc">故障发生频次</label></th>
						<td><input id="zbgzpc" class="editbox" data-dojo-type="dijit.form.NumberTextBox"
							data-dojo-props='name:"zbgzpc", type:"number", required:true, maxLength:"5", placeholder:"故障发生频次"' /></td>
						<th><label for="zbgzbw">故障产生部位</label></th>
						<td><input id="zbgzbw" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbgzbw", type:"text", required:true, maxLength:"32", placeholder:"故障产生部位"' /></td>
					</tr>
					<tr>
						<th><label for="zbsydw">装备使用单位</label></th>
						<td colspan="3">
							<input id="zbsydw" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"zbsydw", type:"text", required:true, maxLength:"32", placeholder:"装备使用单位"' />
						</td>
					</tr>
					<tr>
						<th><label for="zbuser">使用人</label></th>
						<td><input id="zbuser" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbuser", type:"text", required:true, maxLength:"10", placeholder:"使用人"' /></td>
						<th><label for="fault_commiter">记录人</label></th>
						<td><input id="fault_commiter" class="editbox" data-dojo-type="dijit.form.TextBox"
							data-dojo-props='name:"commiter", type:"text", readonly:true' /></td>
					</tr>
					<tr>
						<th><label for="fault_contact">连络方式</label></th>
						<td><input id="fault_contact" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contact", type:"text", required:true, maxLength:"32", placeholder:"连络方式"' /></td>
						<th><label for="fault_committime">报修时间</label></th>
						<td><input id="fault_committime" class="editbox" data-dojo-type="dijit.form.TextBox"
							data-dojo-props='name:"committime", type:"text", readonly:true' /></td>
					</tr>
					<tr>
						<th><label for="zbqzzp">取证照片</label></th>
						<td colspan="3">
							<script type="text/javascript">
							function changeimg(path) {
								ShowLocalImage(dojo.byId("zbqzzpShow"), dojo.byId("preview_size_fake"), path, {
									width: 400, height: 300
								});
							}
							</script>
							<input id="zbqzzp" class="editbox" data-dojo-type="dijit.form.TextBox"
								data-dojo-props='name:"zbqzzp", type:"file", accept:"image/gif, image/jpeg, image/png", placeholder:"取证照片", onchange:"changeimg(this.value)"' />
							<div id="zbqzzpShow" style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);"></div>
							<div id="preview_size_fake" style="visibility:hidden;position:fixed;left:9999px; width:1px;height:1px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);"></div>
						</td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_fault" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_fault").reset();
							$("zbqzzp").select();
							document.execCommand("Delete");
							$("zbqzzpShow").style.display = "none";
							dijit.byId("dlg_fault_form").hide();
							dijit.byId("submit_fault").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
		<div id="tab_backup" class="tab_page" data-dojo-type="dijit.layout.ContentPane" title="备件申请单">
			<span class="TableTitle">
				<h1>备件申请单</h1>
			</span>
			<script type="text/javascript">
			function submitFormBackup() {
				dijit.byId("submit_backup").set("disabled",true);
				showProgress("提交备件申请单");
				dojo.xhrPost({
					url: "./UploadBackupServlet" ,
					form: "form_backup" ,
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_backup").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("备件申请上报成功！");
							// add to grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_backup").reset();
							dijit.byId("dlg_fault_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_backup").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_backup" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormBackup(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					var sb = dijit.byId("submit_backup");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
					<tr>
						<th><label for="bjtype">备件型号</label></th>
						<td><input id="bjtype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"backtype", type:"text", required:true, maxLength:"32", placeholder:"备件型号"' /></td>
						<th><label for="bjname">备件名称</label></th>
						<td><input id="bjname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"backname", type:"text", required:true, maxLength:"32", placeholder:"备件名称"' /></td>
					</tr>
					<tr>
						<th><label for="bjid">备件编号</label></th>
						<td><input id="bjid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"backid", type:"text", required:true, maxLength:"32", placeholder:"备件编号"' /></td>
						<th><label for="bjnum">备件数量</label></th>
						<td><input id="bjnum" class="editbox" data-dojo-type="dijit.form.NumberTextBox"
							data-dojo-props='name:"backnum", type:"number", required:true, value:"1"' /></td>
					</tr>
					<tr>
						<th><label for="bjzbname">备件使用装备名称</label></th>
						<td><input id="bjzbname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"equipname", type:"text", required:true, maxLength:"32", placeholder:"备件使用装备名称"' /></td>
						<th><label for="bjzbtype">备件使用装备型号</label></th>
						<td><input id="bjzbtype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"equiptype", type:"text", required:true, maxLength:"32", placeholder:"备件使用装备型号"' /></td>
					</tr>
					<tr>
						<th><label for="bjzbbw">备件使用装备部位</label></th>
						<td><input id="bjzbbw" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"equiplace", type:"text", required:true, maxLength:"32", placeholder:"备件使用装备部位"' /></td>
						<th><label for="wxsqdh">关联故障单号</label></th>
						<td><input id="wxsqdh" class="editbox" data-dojo-type="dijit.form.TextBox"
							data-dojo-props='name:"faultid", type:"text", maxLength:"18", minLength:"18", placeholder:"维修申请单号"' /></td>
					</tr>
					<tr>
						<th><label for="bjzbsydw">装备使用单位</label></th>
						<td colspan="3">
							<input id="bjzbsydw" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"department", type:"text", required:true, maxLength:"32", placeholder:"装备使用单位"' />
						</td>
					</tr>
					<tr>
						<th><label for="bjsyr">使用人</label></th>
						<td><input id="bjsyr" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"operator", type:"text", required:true, maxLength:"10", placeholder:"使用人"' /></td>
						<th><label for="bjllfs">连络方式</label></th>
						<td><input id="bjllfs" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contactway", type:"text", required:true, maxLength:"32", placeholder:"连络方式"' /></td>
<!--
						<th><label for="bjjlr">记录人</label></th>
						<td><input id="bjjlr" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"reporter", type:"text", readonly:true, placeholder:"记录人"' /></td>
-->
					</tr>
<!--
					<tr>
						<th><label for="bjllfs">连络方式</label></th>
						<td><input id="bjllfs" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contactway", type:"text", required:true, placeholder:"连络方式"' /></td>
						<th><label for="bjbxsj">报修时间</label></th>
						<td><input id="bjbxsj" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"reportime", type:"text", readonly:true, placeholder:"报修时间"' /></td>
					</tr>
-->
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_backup" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>确定</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_backup").reset();
							dijit.byId("dlg_fault_form").hide();
							dijit.byId("submit_backup").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
	</div>
</div>
<%
	}
%>

<%
	if (userlevel == 1 || userlevel == 2)
	{
%>
<div id="dlg_audit_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"装备故障维修信息审核表"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="tab_page" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"故障审核单",style:"overflow:hidden;"' >
			<span class="TableTitle" >
				<h1>故障审核单</h1>
			</span>
			<script type="text/javascript">
			function submitFormAudit() {
				dijit.byId("submit_audit").set("disabled",true);
				var val = $("audit_content").value;
				if (val.length == 0) {
					alert("请填写审核意见！");
					dijit.byId("submit_audit").set("disabled",false);
					return false;
				}
				var dlg = dijit.byId("dlg_audit_form");
				showProgress("提交故障审核单");
				dojo.xhrPost({
					url: "./UploadApprovalServlet" ,
					form: "form_audit" ,
					content: {
						reporttype: dlg.reporttype,
						keyid: dlg.keyid
					},
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_audit").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("审核成功！");
							// update grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_audit").reset();
							dijit.byId("dlg_audit_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_audit").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_audit" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormAudit(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					var sb = dijit.byId("submit_audit");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
<!--
					<tr>
						<th><label for="audit_user">审核人</label></th>
						<td><input id="audit_user" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"checker", readOnly:true' /></td>
					</tr>
					<tr>
						<th><label for="audit_time">审核时间</label></th>
						<td><input id="audit_time" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"time", readOnly:true' /></td>
					</tr>
-->
					<tr>
						<th><label for="audit_res">审核结果</label></th>
						<td>
							<input id="audit_r1" data-dojo-type="dijit.form.RadioButton" 
								data-dojo-props='name:"state", value:"1", checked:true'/> 同意
							<input id="audit_r2" data-dojo-type="dijit.form.RadioButton" 
								data-dojo-props='name:"state", value:"2"'/> 不同意
						</td>
					</tr>
					<tr id="audit_contr" style="height:auto;">
						<th><label for="audit_content">审核意见</label></th>
						<td>
							<textarea id="audit_content" data-dojo-type="dijit.form.SimpleTextarea"
								data-dojo-props='name:"opinion", type:"text", rows:"5", cols:"50", maxLength:"80", placeholder:"审核意见"' ></textarea>
						</td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_audit" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>确定</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_audit").reset();
							dijit.byId("dlg_audit_form").hide();
							dijit.byId("submit_audit").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
<!--
	</div>
-->
</div>
<%
	}
%>

<%
	if (userlevel == 4)
	{
%>
<style type="text/css">
#dlg_dispatch_form_underlay {display:none;}
</style>
<div id="dlg_dispatch_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"装备故障维修派遣信息表", onFocus:function(){setTop(this);}' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="tab_page" data-dojo-type="dijit.layout.ContentPane" title="装备故障维修派遣信息表" style="overflow:hidden;">
			<span class="TableTitle">
				<h1>装备故障维修派遣信息表</h1>
			</span>
			<script type="text/javascript">
			function submitFormDispatch() {
				dijit.byId("submit_dispatch").set("disabled",true);
				showProgress("提交故障派遣单");
				dojo.xhrPost({
					url: "./UploadDispatchServlet" ,
					form: "form_dispatch" ,
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_dispatch").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("故障维修派遣成功！");
							// add to grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_dispatch").reset();
							dijit.byId("dlg_dispatch_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_dispatch").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_dispatch" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormDispatch(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					//dijit.byId('dispatch_time').constraints.min = new Date();
					var sb = dijit.byId("submit_dispatch");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
					<tr>
						<th><label for="dispatch_zbinfoid">故障单号</label></th>
						<td><input id="dispatch_zbinfoid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"orderid", type:"text", readonly:true, placeholder:"装备故障维修信息单号"' /></td>
<!--
						<th><label for="dispatch_dispatchid">派遣单号</label></th>
						<td><input id="dispatch_dispatchid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"dpid", type:"text", readonly:true, placeholder:"装备故障维修派遣信息单号"' /></td>
-->
					</tr>
					<tr>
						<th><label for="dispatch_unit">派遣单位</label></th>
						<td colspan="3">
							<input id="dispatch_unit" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"dpunit", type:"text", required:true, maxLength:"32", placeholder:"派遣单位"' />
						</td>
					</tr>
<!--
					<tr>
						<th><label for="dispatch_time">派遣时间</label></th>
						<td colspan="3">
							<input id="dispatch_time" class="editbox" data-dojo-type="dijit.form.DateTextBox"
								data-dojo-props='name:"dptime", type:"text", required:true, placeholder:"派遣时间"' />
						</td>
					</tr>
-->
					<tr>
						<th><label for="dispatch_safeunit">保障部队</label></th>
						<td colspan="3">
							<input id="dispatch_safeunit" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"dpsafeunit", type:"text", required:true, maxLength:"32", placeholder:"保障部队"' />
						</td>
					</tr>
					<tr>
						<th><label for="dispatch_zbtype">装备型号</label></th>
						<td colspan="3">
							<input id="dispatch_zbtype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"zbtype", type:"text", readonly:true, placeholder:"装备型号"' />
						</td>
					</tr>
					<tr>
						<th><label for="dispatch_zbname">装备名称</label></th>
						<td colspan="3">
							<input id="dispatch_zbname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"zbname", type:"text", readonly:true, placeholder:"装备名称"' />
						</td>
					</tr>
					<tr>
						<th><label for="dispatch_address">部队地点</label></th>
						<td colspan="3">
							<input id="dispatch_address" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"dpaddr", type:"text", required:true, placeholder:"部队地点"' />
						</td>
					</tr>
					<tr>
						<th><label for="dispatch_contacter">部队联系人</label></th>
						<td colspan="3">
							<input id="dispatch_contacter" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"dpcontact", type:"text", required:true, maxLength:"10", placeholder:"部队联系人"' />
						</td>
					</tr>
					<tr style="height:auto;">
						<th><label for="dispatch_describe">故障现象描述</label></th>
						<td colspan="3">
							<textarea id="dispatch_describe" style="padding:0px;width:100%" data-dojo-type="dijit.form.SimpleTextarea"
								data-dojo-props='name:"zbgzxx", type:"text", rows:"5", readonly:true' ></textarea>
						</td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_dispatch" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_dispatch").reset();
							dijit.byId("dlg_dispatch_form").hide();
							dijit.byId("submit_dispatch").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
<!--
	</div>
-->
</div>
<%
	}
%>

<%
	if (userlevel == 6)
	{
%>
<div id="dlg_feedback_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"装备故障维修信息反馈表"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="tab_page" data-dojo-type="dijit.layout.ContentPane" title="装备故障维修信息反馈表" >
			<span class="TableTitle">
				<h1>装备故障维修信息反馈表</h1>
			</span>
			<script type="text/javascript">
			function submitFormFeedback() {
				dijit.byId("submit_feedback").set("disabled",true);
				showProgress("提交故障反馈单");
				dojo.xhrPost({
					url: "./UploadFeedbackServlet" ,
					form: "form_feedback" ,
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_feedback").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("故障维修反馈成功！");
							// add to grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_feedback").reset();
							dijit.byId("dlg_feedback_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_feedback").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_feedback" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormFeedback(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					dijit.byId('feedback_repair_time').constraints.max = new Date();
					var sb = dijit.byId("submit_feedback");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
					<tr>
						<th><label for="feedback_repairid">故障单号</label></th>
						<td colspan="3"><input id="feedback_repairid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"orderid", type:"text", readonly:true, placeholder:"装备故障维修信息单号"' /></td>
<!--
						<th><label for="feedback_feedbackid">反馈单号</label></th>
						<td><input id="fbid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"feedback_feedbackid", type:"text", readonly:true, placeholder:"装备故障维修信息反馈单号"' /></td>
-->
					</tr>
					<tr>
						<th><label for="feedback_zbinfoid">装备型号</label></th>
						<td><input id="feedback_zbinfoid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbtype", type:"text", readonly:true, placeholder:"装备型号"' /></td>
						<th><label for="feedback_zbname">装备名称</label></th>
						<td><input id="feedback_zbname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbname", type:"text", readonly:true, placeholder:"装备名称"' /></td>
					</tr>
					<tr>
						<th><label for="feedback_gztime">故障发生时间</label></th>
						<td><input id="feedback_gztime" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"zbgzsj", type:"text", readonly:true, placeholder:"故障发生时间"' /></td>
						<th><label for="feedback_dispatch">故障派修</label></th>
						<td><input id="feedback_dispatch" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"fbdispatch", type:"text", required:true, maxLength:"32", placeholder:"故障派修"' /></td>
					</tr>
					<tr>
						<th><label for="feedback_content">维修内容</label></th>
						<td colspan="3">
							<input id="feedback_content" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"fbrpcontent", type:"text", required:true, maxLength:"80", placeholder:"维修内容"' />
						</td>
					</tr>
					<tr>
						<th><label for="feedback_reason">故障原因</label></th>
						<td colspan="3">
							<input id="feedback_reason" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"fbgzreason", type:"text", required:true, maxLength:"80", placeholder:"故障原因"' />
						</td>
					</tr>
					<tr>
						<th><label for="feedback_repair_means">维修手段</label></th>
						<td colspan="3">
							<input id="feedback_repair_means1" data-dojo-type="dijit.form.CheckBox"
								data-dojo-props='name:"fbrpway1" '/>
								<label for="frm1">现场维修</label>
							<input id="feedback_repair_means2" data-dojo-type="dijit.form.CheckBox"
								data-dojo-props='name:"fbrpway2", onClick:function() {
									if (dijit.byId("feedback_repair_means2").get("value")) {
										dijit.byId("remotesup_channel")._setRequiredAttr(true);
										dijit.byId("remotesup_type")._setRequiredAttr(true);
										dijit.byId("remotesup_dep")._setRequiredAttr(true);
										dijit.byId("remotesup_exp")._setRequiredAttr(true);
										dijit.byId("remotesup_conn")._setRequiredAttr(true);
										dijit.byId("remotesup_connway")._setRequiredAttr(true);
										dojo.byId("feedback_remotesup_table").style.display="block";
									}
									else {
										dijit.byId("remotesup_channel")._setRequiredAttr(false);
										dijit.byId("remotesup_type")._setRequiredAttr(false);
										dijit.byId("remotesup_dep")._setRequiredAttr(false);
										dijit.byId("remotesup_exp")._setRequiredAttr(false);
										dijit.byId("remotesup_conn")._setRequiredAttr(false);
										dijit.byId("remotesup_connway")._setRequiredAttr(false);
										dojo.byId("feedback_remotesup_table").style.display="none";
									}
								}'/>
								<label for="frm1">专家远程支持</label>
						</td>
					</tr>
					<tr>
						<th><label for="feedback_repair_result">维修结果</label></th>
						<td colspan="3">
							<input id="feedback_repair_result" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"fbrpresult", type:"text", required:true, maxLength:"32", placeholder:"维修结果"' />
						</td>
					</tr>
					<tr>
						<th><label for="feedback_repair_quality">维修质量</label></th>
						<td><span id="feedback_repair_quality" data-dojo-type="dojox.form.Rating" 
							data-dojo-props='name:"fbrpquality", required:true, numStars:5, value:5'></span></td>
						<th><label for="feedback_repair_time">维修时间</label></th>
						<td><input id="feedback_repair_time" class="editbox" data-dojo-type="dijit.form.DateTextBox" 
							data-dojo-props='name:"fbrptime", type:"text", required:true' /></td>
					</tr>
					<tr>
						<th><label for="feedback_bjname">备件名称</label></th>
						<td><input id="feedback_bjname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"fbrpbjname", type:"text", maxLength:"32", placeholder:"维修使用备件名称"' /></td>
						<th><label for="feedback_bjtype">备件型号</label></th>
						<td><input id="feedback_bjtype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"fbrpbjtype", type:"text", maxLength:"32", placeholder:"维修使用备件型号"' /></td>
					</tr>
					<tr>
						<th><label for="feedback_bjfrom">备件来源情况</label></th>
						<td colspan="3">
							<input id="feedback_bjfrom" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"fbrpbjfrom", type:"text", maxLength:"80", placeholder:"备件来源情况"' />
						</td>
					</tr>
				</table>
				<table id="feedback_remotesup_table" style="display:none;">
<!--
					<tr>
						<th><label for="remotesup_zbinfoid">故障单号</label></th>
						<td><input id="remotesup_zbinfoid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"orderid", type:"text", readonly:true, placeholder:"装备故障维修信息单号"' /></td>
						<th><label for="remotesup_feedbackid">反馈单号</label></th>
						<td><input id="remotesup_feedbackid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"fbid", type:"text", readonly:true, placeholder:"装备故障维修信息反馈单号"' /></td>
					</tr>
					<tr>
						<th><label for="remotesup_resupid">远程支援单号</label></th>
						<td colspan="3">
							<input id="remotesup_resupid" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rsid", type:"text", readonly:true, placeholder:"装备维修远程支援信息单号"' />
						</td>
					</tr>
-->
					<tr>
						<th><label for="remotesup_channel">远程支援通道</label></th>
						<td style="width:80%">
							<input id="remotesup_channel" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rschannel", type:"text", required:false, maxLength:"32", placeholder:"远程支援通道"' />
						</td>
					</tr>
					<tr>
						<th><label for="remotesup_type">远程支援方式</label></th>
						<td colspan="3">
							<input id="remotesup_type" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rstype", type:"text", required:false, maxLength:"32", placeholder:"远程支援方式"' />
						</td>
					</tr>
					<tr>
						<th><label for="remotesup_dep">远程支援单位</label></th>
						<td colspan="3">
							<input id="remotesup_dep" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rsunit", type:"text", required:false, maxLength:"32", placeholder:"远程支援单位"' />
						</td>
					</tr>
					<tr>
						<th><label for="remotesup_exp">远程支援专家</label></th>
						<td colspan="3">
							<input id="remotesup_exp" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rsexpert", type:"text", required:false, maxLength:"10", placeholder:"远程支援专家"' />
						</td>
					</tr>
					<tr>
						<th><label for="remotesup_conn">联络人</label></th>
						<td colspan="3">
							<input id="remotesup_conn" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rscontacter", type:"text", required:false, maxLength:"10", placeholder:"联络人"' />
						</td>
					</tr>
					<tr>
						<th><label for="remotesup_connway">联络方式</label></th>
						<td colspan="3">
							<input id="remotesup_connway" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rscontactway", type:"text", required:false, maxLength:"32", placeholder:"联络方式"' />
						</td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_feedback" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_feedback").reset();
							dijit.byId("dlg_feedback_form").hide();
							dijit.byId("submit_feedback").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
<!--
	</div>
-->
</div>
<%
	}
%>

<%
	if (userlevel == 7)
	{
%>
<div id="dlg_phoneback_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"客户回访单"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="tab_page" data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"客户回访单",style:"overflow:hidden;"' >
			<span class="TableTitle" >
				<h1>客户回访单</h1>
			</span>
			<script type="text/javascript">
			function submitFormPhoneback() {
				dijit.byId("submit_phoneback").set("disabled",true);
				var dlg = dijit.byId("dlg_phoneback_form");
				showProgress("提交客户回访单");
				dojo.xhrPost({
					url: "./PhoneBackConfirmServlet" ,
					form: "form_phoneback" ,
					content: {
						orderid: dlg.keyid
					},
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_phoneback").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("回访成功！");
							// update grid
							var grid = dijit.byId("grid");
							grid.query.timestamp = new Date().getTime();
							grid.setStore(grid.store, grid.query);
							// close dialog
							dijit.byId("form_phoneback").reset();
							dijit.byId("dlg_phoneback_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_phoneback").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_phoneback" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormPhoneback(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					var sb = dijit.byId("submit_phoneback");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
					<tr>
						<th><label for="pb_name">被回访人</label></th>
						<td><input id="pb_name" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"client", type:"text", required:true, maxLength:"10", placeholder:"被回访人"' /></td>
					</tr>
					<tr>
						<th><label for="pb_contact">联系方式</label></th>
						<td><input id="pb_contact" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contact", type:"text", required:true, maxLength:"32", placeholder:"工厂代号"' /></td>
					</tr>
					<tr>
						<th><label for="pb_reviewway">回访手段</label></th>
						<td>
							<input id="pb_r1" data-dojo-type="dijit.form.RadioButton" 
								data-dojo-props='name:"reviewway", value:"1", checked:true'/>电话回访
							<input id="pb_r2" data-dojo-type="dijit.form.RadioButton" 
								data-dojo-props='name:"reviewway", value:"2"'/> 传真回访
							<input id="pb_r3" data-dojo-type="dijit.form.RadioButton" 
								data-dojo-props='name:"reviewway", value:"3"'/> 邮件回访
						</td>
					</tr>
					<tr>
						<th><label for="pb_quality">维修质量</label></th>
						<td><span id="pb_quality" data-dojo-type="dojox.form.Rating" 
							data-dojo-props='name:"quality", required:true, numStars:5, value:5'></span></td>
					</tr>
					<tr>
						<th><label for="pb_disc">维修描述</label></th>
						<td><span id="pb_disc" data-dojo-type="dijit.form.SimpleTextarea" 
							data-dojo-props='name:"discription", type:"text", rows:"5", required:true, maxLength:"80"'></span></td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_phoneback" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit"'>确定</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_phoneback").reset();
							dijit.byId("dlg_phoneback_form").hide();
							dijit.byId("submit_phoneback").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
<!--
	</div>
-->
</div>
<%
	}
%>


<%
	if (userlevel == 4 || userlevel == 7)
	{
%>
<div id="dlg_industry_form" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"工业部门信息表"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="tab_page" data-dojo-type="dijit.layout.ContentPane" title="工业部门信息表" style="overflow-y:auto;">
			<span class="TableTitle">
				<h1>工业部门信息表</h1>
			</span>
			<script type="text/javascript">
			function submitFormIndustry() {
				dijit.byId("submit_industry").set("disabled",true);
				showProgress("提交工业部门信息表");
				dojo.xhrPost({
					url: "./UploadFactoryServlet" ,
					form: "form_industry" ,
					handleAs: "json" ,
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_industry").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							alert("增加工业部门成功！");
							// close dialog
							dijit.byId("form_industry").reset();
							dijit.byId("dlg_industry_form").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_industry").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_industry" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST",
				onSubmit:function(){ return submitFormIndustry(); }'>
				<script type="dojo/connect" data-dojo-event="startup">
					var sb = dijit.byId("submit_industry");
					//  set initial state
					sb.set("disabled", !this.isValid());
					this.connect(this, "onValidStateChange", function(state){
							sb.set("disabled", !state);
					});
				</script>
				<div class="editTable w540">
				<table>
					<tr>
						<th><label for="industry_name">工厂名称</label></th>
						<td><input id="industry_name" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"name", type:"text", required:true, maxLength:"32", placeholder:"工厂名称"' /></td>
						<th><label for="industry_address">工厂地址</label></th>
						<td><input id="industry_address" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"address", type:"text", required:true, maxLength:"80", placeholder:"工厂地址"' /></td>
					</tr>
					<tr>
						<th><label for="industry_code">工厂代号</label></th>
						<td><input id="industry_code" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"code", type:"text", required:true, maxLength:"32", placeholder:"工厂代号"' /></td>
						<th><label for="industry_prorange">生产范围</label></th>
						<td><input id="industry_prorange" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"range", type:"text", required:true, maxLength:"80", placeholder:"生产范围"' /></td>
					</tr>
					<tr>
						<th><label for="industry_protype">已出厂装备型号</label></th>
						<td colspan="3">
							<input id="industry_protype" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"pdtype", type:"text", required:true, maxLength:"80", placeholder:"已出厂装备型号"' />
						</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="3">
							注：已出厂装备有多个时，请用空格符将型号分开。
						</td>
					</tr>
					<tr>
						<th><label for="industry_proname">已出厂装备名称</label></th>
						<td colspan="3">
							<input id="industry_proname" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"pdname", type:"text", required:true, maxLength:"80", placeholder:"已出厂装备名称"' />
						</td>
					</tr>
					<tr>
						<th></th>
						<td colspan="3">
							注：已出厂装备有多个时，请用空格符将名称分开。
						</td>
					</tr>
					<tr>
						<th><label for="industry_matchunit">编号及编配部队</label></th>
						<td colspan="3">
							<input id="industry_matchunit" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"guarantee", type:"text", required:true, maxLength:"32", placeholder:"编号及编配部队"' />
						</td>
					</tr>
					<tr>
						<th><label for="industry_serviceman">维修人员</label></th>
						<td><input id="industry_serviceman" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"rpinfo", type:"text", required:true, maxLength:"10", placeholder:"维修人员"' /></td>
						<th><label for="industry_servicelevel">维修能力</label></th>
						<td><span id="industry_servicelevel" data-dojo-type="dojox.form.Rating" 
							data-dojo-props='name:"ability", required:true, numStars:4, value:4'></span></td>
					</tr>
					<tr>
						<th><label for="industry_deviceinfo">维修设备情况</label></th>
						<td colspan="3">
							<input id="industry_deviceinfo" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
								data-dojo-props='name:"rpdevice", type:"text", required:true, maxLength:"32", placeholder:"维修设备情况"' />
						</td>
					</tr>
					<tr>
						<th><label for="industry_contactman">联系人</label></th>
						<td><input id="industry_contactman" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contact", type:"text", required:true, maxLength:"10", placeholder:"联系人"' /></td>
						<th><label for="industry_contactway">联系方式</label></th>
						<td><input id="industry_contactway" class="editbox" data-dojo-type="dijit.form.ValidationTextBox"
							data-dojo-props='name:"contactWay", type:"text", required:true, maxLength:"32", placeholder:"联系方式"' /></td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_industry" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_industry").reset();
							dijit.byId("dlg_industry_form").hide();
							dijit.byId("submit_industry").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
<!--
	</div>
-->
</div>
<%
	}
%>


<div id="dlg_fault_info" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"故障详情"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div data-dojo-type="dijit.layout.ContentPane" title="故障上报单">
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"故障维修进度"' >
				<span class="TableTitle">
					<h1>故障维修进度</h1>
				</span>
				<div id="process_fault" class="process">
				</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"故障上报单"' >
			<div id="faultInfoTable" >
				<button class="print" onClick="JPrint.print('faultInfoTable');"></button>
				<span class="TableTitle">
					<h1>故障上报单</h1>
				</span>
				<div id="fault_info" class="infoTable">
				</div>
			</div>
				<div id="fault_info_confirm" class="toolbar" style="display:none;">
					<script type="text/javascript">
					function submitFormFormat(ispass) {
						var dlg = dijit.byId("dlg_fault_info");
						showProgress("提交格式审核");
						dojo.xhrPost({
							url: "./UploadFormatServlet",
							handleAs: "json",
							preventCache: true,
							content: {
								orderid: dlg.data.orderid,
								ispass: ispass
							},
							load: function(data) {
								if (!data) {
									alert("未知错误！请重新提交！");
								}
								else if (data.ret != 0) {
									alert(data.msg);
								}
								else {
									alert("格式审核成功！");
									// update grid
									var grid = dijit.byId("grid");
									grid.query.timestamp = new Date().getTime();
									grid.setStore(grid.store, grid.query);
									// close dialog
									dlg.hide();
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
						return false;
					}
					</script>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							submitFormFormat(1);
						} '>格式正确</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							submitFormFormat(0);
						} '>格式错误</button>
				</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"故障一级审核单"' >
			<div id="l1auditInfoTable" >
				<button class="print" onClick="JPrint.print('l1auditInfoTable');"></button>
				<span class="TableTitle">
					<h1>故障一级审核单</h1>
				</span>
				<div id="fault_info_l1audit" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"故障二级审核单"' >
			<div id="l2auditInfoTable" >
				<button class="print" onClick="JPrint.print('l2auditInfoTable');"></button>
				<span class="TableTitle">
					<h1>故障二级审核单</h1>
				</span>
				<div id="fault_info_l2audit" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"装备故障维修派遣信息表"' >
			<div id="dispatchInfoTable" >
				<button class="print" onClick="JPrint.print('dispatchInfoTable');"></button>
				<span class="TableTitle">
					<h1>装备故障维修派遣信息表</h1>
				</span>
				<div id="fault_info_dispatch" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"装备故障维修信息反馈表"' >
			<div id="feedbackInfoTable" >
				<button class="print" onClick="JPrint.print('feedbackInfoTable');"></button>
				<span class="TableTitle">
					<h1>装备故障维修信息反馈表</h1>
				</span>
				<div id="fault_info_feedback" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"装备维修远程支援信息表"' >
			<div id="remotesupInfoTable" >
				<button class="print" onClick="JPrint.print('remotesupInfoTable');"></button>
				<span class="TableTitle">
					<h1>装备维修远程支援信息表</h1>
				</span>
				<div id="fault_info_remotesup" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"客户回访表"' >
			<div id="phonebackInfoTable" >
				<button class="print" onClick="JPrint.print('phonebackInfoTable');"></button>
				<span class="TableTitle">
					<h1>客户回访表</h1>
				</span>
				<div id="fault_info_phoneback" class="infoTable">
				</div>
			</div>
			</div>
		</div>
<!--
	</div>
-->
</div>

<div id="dlg_backup_info" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"故障详情"' >
<!--
	<div class="dlgTabContainer" data-dojo-type="dijit.layout.TabContainer">
-->
		<div class="infoTable" data-dojo-type="dijit.layout.ContentPane" title="备件申请单">
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"备件申请进度"' >
				<span class="TableTitle">
						<h1>备件申请进度</h1>
					</span>
				<div id="process_backup" class="process">
				</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"备件申请单"' >
			<div id="backupInfoTable" >
				<button class="print" onClick="JPrint.print('backupInfoTable');"></button>
				<span class="TableTitle">
					<h1>备件申请单</h1>
				</span>
				<div id="bj_info" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"备件一级审核单"' >
			<div id="bjl1auditInfoTable" >
				<button class="print" onClick="JPrint.print('bjl1auditInfoTable');"></button>
				<span class="TableTitle">
					<h1>备件一级审核单</h1>
				</span>
				<div id="bj_info_l1audit" class="infoTable">
				</div>
			</div>
			</div>
			<div style="width:100%" data-dojo-type="dijit.TitlePane" data-dojo-props='open:true, title:"备件二级审核单"' >
			<div id="bjl2auditInfoTable" >
				<button class="print" onClick="JPrint.print('bjl2auditInfoTable');"></button>
				<span class="TableTitle">
					<h1>备件二级审核单</h1>
				</span>
				<div id="bj_info_l2audit" class="infoTable">
				</div>
			</div>
			</div>
		</div>
<!--
	</div>
-->
</div>
