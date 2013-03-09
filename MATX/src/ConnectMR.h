#if !defined(__CONNECTMR_H__GDOWNMSCSBXZCXZJSODWPSNXZOAAWE__INCLUDED_)
#define __CONNECTMR_H__GDOWNMSCSBXZCXZJSODWPSNXZOAAWE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "CAudioTalkMR.h"

#define STATE_CONNECT_OK          0 //语音连接成功
#define STATE_CONNECT_SOCKET      1 //socket初始化失败
#define STATE_CONNECT_CONNECTSVR  2 //连接server失败
#define STATE_CONNECT_SEND        3 //发送信息失败
#define STATE_CONNECT_SELECT      4 //select失败
#define STATE_CONNECT_RECV        5 //接收信息失败
#define STATE_CONNECT_WRONGDATA   6 //接收到的数据错误
#define STATE_CONNECT_NOLISTENER  7 //无接线员
#define STATE_CONNECT_CLOSE       8 //关闭连接
#define STATE_CONNECT_TIMEOUT     9 //超时
#define STATE_CONNECT_WAITFOR    10 //等待作业员
#define STATE_CONNECT_UDPERROR   11 //与语音对象建立连接失败


#define IDENTITY_DISPATCHER   4  //分发员 - 仅连接砖家
#define IDENTITY_EXPERT       5  //砖家 - 仅供分发员连接
#define IDENTITY_LISTENER     7  //接线员 - 供其它角色连接

class CMAudioTalk;
class CConnectMR
{
public:
	CConnectMR(CMAudioTalk* pcmt, CAudioTalkMR** ppatmr);
	~CConnectMR(void);

	int Init(char* pip, int type, char* name);
	void Uninit();
	int Connect();
	int InitATMR(char* pIp);
	void UninitATMR();

	int Offline();
	void Offover();

	void PutState(long* pstate);
	void PutName(BSTR str);

private:
#ifndef _WIN32_WCE
	static UINT __stdcall Thread(void *pParam);
#else
	static DWORD WINAPI Thread(void *pParam);
#endif // _WIN32_WCE

private:
	CMAudioTalk* _pcmt;
	bool _connecting; //
	CAudioTalkMR** _ppatmr;
	char _ip[256];
	int _type;
	char _name[256];
};

#endif
