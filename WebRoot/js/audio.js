/*
 * 
 */

function WebAudio() {}

WebAudio.audioMode = true; //true-offline  false-online
WebAudio.ipSvr; //目标IP,域名均可,数据类型为:string
WebAudio.levelSelf; //自己的身份 1接线员
WebAudio.name; //自己的名字
WebAudio.targetStr;
WebAudio.cn = 0;
WebAudio.se;
WebAudio.m=0;
WebAudio.h=0;
WebAudio.s=0;

if (!MAudioTalk) {
	document.getElementById("btnBegin").disabled = true;
	document.getElementById("state").innerHTML="载入语音控件失败！";
	alart("载入语音控件失败！请刷新页面！");
}

WebAudio.changemode = function() {
	this.audioMode = !this.audioMode
	if (this.audioMode) {
		document.getElementById("btnChange").value = "切换至离线提交模式";
		document.getElementById("btnBegin").value = "语音连接";
		document.getElementById("btnEnd").value = "挂断";
	}
	else {
		document.getElementById("btnChange").value = "切换至在线求助模式";
		document.getElementById("btnBegin").value = "离线录音";
		document.getElementById("btnEnd").value = "停止";
	}
}

WebAudio.Init = function(ip, port, name) {
	this.ipSvr = ip;
	this.levelSelf = port;
	this.name = name;

	switch (this.levelSelf)
	{
	case 4:
		this.targetStr = "专家";
		break;
	case 5:
		this.targetStr = "分发员";
		break;
	case 7:
		this.targetStr = "作业员";
		break;
	case 3:
	default:
		this.targetStr = "接线员";
		break;
	}
}

WebAudio.Begin = function() {
	if (MAudioTalk) {
		document.getElementById("btnBegin").disabled = true;
		document.getElementById("btnEnd").disabled = false;
		document.getElementById("audioinfo").innerHTML="";

		MAudioTalk.attachEvent("GetState", OnGetState);
		MAudioTalk.attachEvent("GetString", OnGetString);

		if (this.audioMode) {
			MAudioTalk.attachEvent("GetName", OnGetName);
			document.getElementById("state").innerHTML="请稍等...";
			MAudioTalk.MAudioTalk_Init(this.ipSvr, this.levelSelf, this.name);
		}
		else {
			document.getElementById("state").innerHTML="开始录音...";
			MAudioTalk.MAudioTalk_Offline();
			this.h = this.m = this.s = 0;
			this.se= setInterval(second, 1000);
		}
	}
	else {
		alart("载入语音控件失败！请刷新页面！");
	}
}

WebAudio.End = function() {
	if (MAudioTalk) {
		document.getElementById("btnBegin").disabled = false;
		document.getElementById("btnEnd").disabled = true;
		document.getElementById("state").innerHTML="请稍等...";
		document.getElementById("audioinfo").innerHTML="";

		clearInterval(this.se);
		this.h = this.m = this.s = 0;

		if (this.audioMode) {
			MAudioTalk.MAudioTalk_Uninit();
			MAudioTalk.detachEvent("GetName", OnGetName);
		}
		else {
			MAudioTalk.MAudioTalk_Offover();
		}
		
		MAudioTalk.detachEvent("GetState", OnGetState);
		MAudioTalk.detachEvent("GetString", OnGetString);
	}
}

second = function() {
	WebAudio.s += 1;
	if(WebAudio.s>0 && (WebAudio.s%60)==0) {
		WebAudio.m+=1;
		WebAudio.s=0;
	}
	if(WebAudio.m>0 && (WebAudio.m%60)==0) {
		WebAudio.h+=1;
		WebAudio.m=0;
	}
	var t = WebAudio.h<10?"0"+WebAudio.h:WebAudio.h;
	t += ":"
	t += WebAudio.m<10?"0"+WebAudio.m:WebAudio.m;
	t += ":"
	t += WebAudio.s<10?"0"+WebAudio.s:WebAudio.s;
	document.getElementById("showtime").innerHTML = t;
}

window.onbeforeunload = function() {
	if (MAudioTalk)
		MAudioTalk.MAudioTalk_Uninit();
}

function OnGetState(result) {
	setTimeout(function() {
		setState(result);
	} ,200);
}

function OnGetString(str) {
	document.getElementById("audioinfo").innerHTML="录音文件已保存于文件夹："+str;
}

function OnGetName(str) {
	document.getElementById("audioinfo").innerHTML="正在与【"+str+"】通话...";
}

function setState(result) {
	if (result == 99) {
		WebAudio.cn++;
		document.getElementById("state2").innerHTML="cn="+WebAudio.cn;
		return;
	}
	
	if (result == 0 || result == 10) {
		document.getElementById("btnBegin").disabled = true;
		document.getElementById("btnEnd").disabled = false;
	}
	else {
		document.getElementById("btnBegin").disabled = false;
		document.getElementById("btnEnd").disabled = true;
		MAudioTalk.detachEvent("GetState", OnGetState);
	}
	
	switch (result) {
	case 0:
		document.getElementById("state").innerHTML="连接" + WebAudio.targetStr + "成功！";
		WebAudio.h = WebAudio.m = WebAudio.s = 0;
		WebAudio.se= setInterval(second, 1000);
		break;
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
	case 6:
		document.getElementById("state").innerHTML="初始化连接失败(" + result + ")...请联系管理员！";
		break;
	case 7:
		document.getElementById("state").innerHTML="暂无空闲在线" + WebAudio.targetStr + "...请稍后重试！";
		alert("暂无空闲在线" + WebAudio.targetStr + "...请稍后重试！");
		break;
	case 8:
		document.getElementById("state").innerHTML="已完成！";
		break;
	case 9:
		document.getElementById("state").innerHTML="等待超时...请稍后重试！";
		break;
	case 10:
		document.getElementById("state").innerHTML="等待" + WebAudio.targetStr + "连接...";
		break;
	case 11:
		document.getElementById("state").innerHTML="与对方建立语音连接失败！";
		break;
	default:
		break;
	}
}
