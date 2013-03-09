#if !defined(__CONNECTMR_H__GDOWNMSCSBXZCXZJSODWPSNXZOAAWE__INCLUDED_)
#define __CONNECTMR_H__GDOWNMSCSBXZCXZJSODWPSNXZOAAWE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "CAudioTalkMR.h"

#define STATE_CONNECT_OK          0 //�������ӳɹ�
#define STATE_CONNECT_SOCKET      1 //socket��ʼ��ʧ��
#define STATE_CONNECT_CONNECTSVR  2 //����serverʧ��
#define STATE_CONNECT_SEND        3 //������Ϣʧ��
#define STATE_CONNECT_SELECT      4 //selectʧ��
#define STATE_CONNECT_RECV        5 //������Ϣʧ��
#define STATE_CONNECT_WRONGDATA   6 //���յ������ݴ���
#define STATE_CONNECT_NOLISTENER  7 //�޽���Ա
#define STATE_CONNECT_CLOSE       8 //�ر�����
#define STATE_CONNECT_TIMEOUT     9 //��ʱ
#define STATE_CONNECT_WAITFOR    10 //�ȴ���ҵԱ
#define STATE_CONNECT_UDPERROR   11 //����������������ʧ��


#define IDENTITY_DISPATCHER   4  //�ַ�Ա - ������ש��
#define IDENTITY_EXPERT       5  //ש�� - �����ַ�Ա����
#define IDENTITY_LISTENER     7  //����Ա - ��������ɫ����

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
