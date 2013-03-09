<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	ArrayList<String> al = (ArrayList<String>) request.getSession().getAttribute("loginUser");
	int userlevel = -1;
	String name = "";
	if (al != null) {
		userlevel = Integer.parseInt(al.get(3));
		name = al.get(1);
	}
%>
<%	String audiotitle="";
	switch (userlevel)
	{
	case 3:
		audiotitle="语音";
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

<script type="text/javascript">
function dlgAudioForm() {
<%	if (userlevel == 3)
	{%>
	if (!WebAudio.audioMode) {
		var dlg = dijit.byId("dlg_audio_form");
		if (dlg) {
			dlg.show();
			dlg.resize();
		}
	}
<%	}%>
}
</script>

<style type="text/css">
#dlg_audio_underlay {display:none;}
</style>
<div id="dlg_audio" class="dialog" data-dojo-type="dijit.Dialog" data-dojo-props='title:"<%=audiotitle%>", onFocus:function(){setTop(this);}'>
<!--
	<div data-dojo-type="dijit.layout.ContentPane">
	width:200px;height:150px;
-->
		<div style="border:1px solid green;margin:0px auto;">
			<OBJECT id="MAudioTalk" width="0" height="0" codeBase="MATX.CAB#version=1,0,1,6" 
					classid="CLSID:8853B3B8-D35F-4131-9967-B41D3214B13D"></OBJECT>
			<script type="text/javascript" src="js/audio.js">
			</script>
			<script type="text/javascript">
			dojo.addOnLoad(function() {
				WebAudio.Init("<%=request.getServerName()%>", <%=userlevel%>, "<%=name%>");
			});
			</script>
			<span class="TableTitle" >
				<h1>语音面板</h1>
			</span>
			<div style="width:250px;margin:5px auto;">
			<div id="showtime" style="border:1px solid green;color:#ff0000;width:120px;height:30px;font-size:20px;line-height:30px;text-align:center;margin:5px auto;" type="text">00:00:00</div>
			<span id="audioinfo"></span>
			<div style="margin:0px auto;padding:10px;width:150px;">
<%	if (userlevel == 3)
	{%>
				<input id="btnChange" style="padding:3px 10px;" type="button" value="切换至离线提交模式" onClick="WebAudio.changemode();" />
<%	}%>
				<input id="btnBegin" style="padding:3px 10px;" type="button" value="语音连接" onClick="WebAudio.Begin();" />
				<input id="btnEnd" style="padding:3px 10px;" type="button" value="挂断" disabled="disabled" onClick="WebAudio.End();dlgAudioForm();" />
			</div>
			状态：<span id="state"></span>
			<br />
			<span id="state2" style="visibility:hidden;">0</span>
			</div>
		</div>
<%	if (userlevel == 3)
	{%>
		<div style="width:auto;height:auto;overflow: auto;">
			<p>注意事项：</p>
			<p>语音内容需包括但不限于以下表格内容：</p>
			<table class="testinfoTable">
				<tr><th>装备型号</th><td></td><th>装备名称</th><td></td></tr>
				<tr><th>装备编号</th><td></td><th>装备数量</th><td></td></tr>
				<tr><th>故障现象</th><td colspan="3"></td></tr>
				<tr><th>故障发生时间</th><td colspan="3"></td></tr>
				<tr><th>装备使用单位</th><td colspan="3"></td></tr>
				<tr><th>连络方式</th><td></td><th>报修时间</th><td></td></tr>
			</table>
		</div>
<%	}%>
<!--
	</div>
-->
</div>

<%	if (userlevel == 3)
	{%>
<div id="dlg_audio_form" data-dojo-type="dijit.Dialog" data-dojo-props='title:"离线语音"' >
	<div data-dojo-type="dijit.layout.ContentPane" data-dojo-props='title:"提交离线语音"' >
		<span class="TableTitle" >
			<h1>提交离线语音</h1>
		</span>
		<script type="text/javascript">
		function uploadAudio() {
			dijit.byId("submit_audio").set("disabled",true);
			showProgress("提交离线语音");
			dojo.io.iframe.send({
				url: "./UploadAudioServlet",
				form: "form_audio",
				contentType:"multipart/form-data",
				handleAs: "json",
				load: function(data) {
					dijit.byId("submit_audio").set("disabled",false);
					if (!data) {
						alert("未知错误！请重新提交！");
					}
					else if (data.ret != 0) {
						alert(data.msg);
					}
					else {
						alert("离线语音提交成功！");
						dijit.byId("form_audio").reset();
						dijit.byId("dlg_audio_form").hide();
					}
					hideProgress();
					dijit.byId("dlg_audio").hide();
				},
				error: function() {
					alert("出了点问题，上传没有成功呢！");
					dijit.byId("submit_audio").set("disabled",false);
					hideProgress();
				},
				sync: false
			});
			return false;
		}
		</script>
		<form id="form_audio" data-dojo-type="dijit.form.Form" data-dojo-props='method:"POST", encType:"multipart/form-data",
			onSubmit:function(){
				return uploadAudio();
			}'>
			<div class="editTable w220">
				<input id="audio_url" class="editbox" data-dojo-type="dijit.form.TextBox"
					data-dojo-props='name:"audio_url", type:"file", placeholder:"数据文件"' />
			</div>
			<br />
			<div class="toolbar">
				<button id="submit_audio" data-dojo-type="dijit.form.Button" 
					data-dojo-props='type:"submit"'>确定</button>
				<button data-dojo-type="dijit.form.Button" data-dojo-props='type:"button",
					onClick:function(){
						$("audio_url").select();
						document.execCommand("Delete");
						dijit.byId("dlg_audio_form").hide();
						dijit.byId("submit_audio").set("disabled",false);
					} '>取消</button>
			</div>
		</form>
	</div>
</div>
<%	}%>
