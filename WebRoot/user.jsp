<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	ArrayList<String> al = (ArrayList<String>) request.getSession().getAttribute("loginUser");
	int userlevel = -1;
	if (al != null) {
		userlevel = Integer.parseInt(al.get(3));
	}
%>

<div id="dlg_password" data-dojo-type="dijit.Dialog" data-dojo-props='title:"用户管理"' >
		<div id="tab_password" data-dojo-type="dijit.layout.ContentPane" 
			data-dojo-props='title:"修改密码", style:"overflow:hidden;"'>
			<span class="TableTitle"><h1>修改密码</h1></span>
			<script type="text/javascript">
			function submitFormPassword() {
				dijit.byId("submit_password").set("disabled",true);
				showProgress("修改密码");
				dojo.xhrPost({
					url: "./ModifyUserPwdServlet",
					form: "form_password",
					handleAs: "json",
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_password").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							// close dialog
							dijit.byId("form_password").reset();
							dijit.byId("dlg_password").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_password").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_password" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST", 
				onSubmit:function(){ return submitFormPassword(); }'>
				<div data-dojo-type="dojox.form.PasswordValidator" 
					data-dojo-props='name:"newpwd", oldName:"oldpwd"'>
				<script type="dojo/method" event="pwCheck" args="password">
					return true;
				</script>
				<table style="table-layout:fixed">
					<tr style="height:30px">
						<th style="width:100px"><label for="pass_old">旧密码</label></th>
						<td style="width:160px"><input type="password" pwType="old" /></td>
					</tr>
					<tr style="height:30px">
						<th><label for="pass_new">新密码</label></th>
						<td><input type="password" pwType="new" /></td>
					</tr>
					<tr style="height:30px">
						<th><label for="pass_confirm">确认密码</label></th>
						<td><input type="password" pwType="verify" /></td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_password" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_password").reset();
							dijit.byId("dlg_password").hide();
							dijit.byId("submit_password").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
</div>

<%
	if (userlevel == 0 || userlevel == 1 || userlevel == 2 || userlevel == 4 || userlevel == 7)
	{
%>
<div id="dlg_useradd" data-dojo-type="dijit.Dialog" data-dojo-props='title:"用户管理"' >
		<div id="tab_useradd" data-dojo-type="dijit.layout.ContentPane" 
			data-dojo-props='title:"增加用户", style:"overflow:hidden;"'>
			<span class="TableTitle"><h1>增加用户</h1></span>
			<script type="text/javascript">
			function submitFormUseradd() {
				dijit.byId("submit_useradd").set("disabled",true);
				showProgress("新增用户");
				dojo.xhrPost({
					url: "./AddNewUserServlet",
					form: "form_useradd",
					handleAs: "json",
					preventCache: true,
					load: function(data) {
						dijit.byId("submit_useradd").set("disabled",false);
						if (!data) {
							alert("未知错误！请重新提交！");
						}
						else if (data.ret != 0) {
							alert(data.msg);
						}
						else {
							// close dialog
							dijit.byId("form_useradd").reset();
							dijit.byId("dlg_useradd").hide();
						}
						hideProgress();
					},
					error: function() {
						//
						alert("出了点问题，提交没有成功呢！");
						dijit.byId("submit_useradd").set("disabled",false);
						hideProgress();
					},
					sync: false
				});
				return false;
			}
			</script>
			<form id="form_useradd" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST", 
				onSubmit:function(){ return submitFormUseradd(); }'>
				<div>
				<table>
					<tr style="height:30px">
						<th style="width:100px"><label for="useradd_name">用户名</label></th>
						<td style="width:160px"><input id="useradd_name" data-dojo-type="dijit.form.ValidationTextBox" 
							data-dojo-props='name:"name", type:"text", required:true' /></td>
					</tr>
					<tr style="height:30px">
						<th><label for="useradd_type">类型</label></th>
						<td>
							<select id="useradd_type" data-dojo-type="dijit.form.Select" 
								data-dojo-props='name:"level", maxHeight:100 '>
							</select>
						</td>
					</tr>
					<tr style="height:30px">
						<th><label for="useradd_fullname">全名</label></th>
						<td><input id="useradd_fullname" data-dojo-type="dijit.form.ValidationTextBox" 
							data-dojo-props='name:"fullname", type:"text", required:true' /></td>
					</tr>
					<tr style="height:30px">
						<th><label for="useradd_depart">单位</label></th>
						<td><input id="useradd_depart" data-dojo-type="dijit.form.ValidationTextBox" 
							data-dojo-props='name:"depart", type:"text", required:true' /></td>
					</tr>
					<tr style="height:60px">
						<th><label for="useradd_descript">说明</label></th>
						<td>
							<textarea id="useradd_descript" data-dojo-type="dijit.form.SimpleTextarea"
								data-dojo-props='name:"descript", type:"text", rows:"2"' ></textarea>
						</td>
					</tr>
				</table>
				</div>
				<div class="toolbar">
					<button id="submit_useradd" data-dojo-type="dijit.form.Button" 
						data-dojo-props='type:"submit", busyLabel:"正在提交"'>提交</button>
					<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
						onClick:function(){
							dijit.byId("form_useradd").reset();
							dijit.byId("dlg_useradd").hide();
							dijit.byId("submit_useradd").set("disabled",false);
						} '>取消</button>
				</div>
			</form>
		</div>
	</div>
</div>
<%
	}
%>