#include "StdAfx.h"
#include "debug.h"
#include "ConnectMR.h"
#include "../MAudioTalk.h"

#define SERVER_PORT	5566
#define RTP_PORT    9000
#define BUFFER_SIZE 4096
#define MAX_ADDR_LEN 128

CConnectMR::CConnectMR(CMAudioTalk* pcmt, CAudioTalkMR** ppatmr)
: _pcmt(pcmt), _ppatmr(ppatmr), _connecting(true), _type(0)
{
}

CConnectMR::~CConnectMR(void)
{
}

int CConnectMR::Init(char* ip, int type, char* name)
{
	memset(_ip, 0, sizeof(_ip));
	strcpy(_ip, ip);
	_type = type;
	memset(_name, 0, sizeof(_name));
	strcpy(_name, name);

#ifndef _WIN32_WCE
	_beginthreadex(NULL,0,Thread,(LPVOID)this,0,0);
#else
	CreateThread(NULL,0,Thread,this,0,0);
#endif // _WIN32_WCE
	return 0;
}

void CConnectMR::Uninit()
{
	_connecting = false;
}

int CConnectMR::Connect()
{
	long ret;
	int nPort = SERVER_PORT;
	char szBuffer[BUFFER_SIZE];

	int nAddrLen;
	int nRet;
	SOCKET s;
	SOCKADDR_IN sa;

	WSAData wsaData;
	WORD wVersion = MAKEWORD(2,2);
	nRet = WSAStartup(wVersion, &wsaData);
	if (SOCKET_ERROR == nRet)
	{
		TRACEMSG("Socket init error\n");
		ret = STATE_CONNECT_SOCKET;
		PutState(&ret);
		return ret;
	}

	s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if (INVALID_SOCKET == s)
	{
		TRACEMSG("Socket was created failse:%d\n", WSAGetLastError());
		ret = STATE_CONNECT_SOCKET;
		PutState(&ret);
		WSACleanup();
		return ret;
	}

	int timeout=6000;     //收发超时6秒
	if(setsockopt(s, SOL_SOCKET, SO_RCVTIMEO, (char*)&timeout, sizeof(timeout)))
	{
		TRACEMSG("socket setsockopt() failse:%d\n", WSAGetLastError());
		ret = STATE_CONNECT_SOCKET;
		PutState(&ret);
		closesocket(s);
		WSACleanup();
		return ret;
	}

	nAddrLen = sizeof(SOCKADDR_IN);
	memset(&sa, 0, nAddrLen);
	sa.sin_family = AF_INET;
	sa.sin_port	  = htons(nPort);
	//sa.sin_addr.s_addr = inet_addr("127.0.0.1");
	sa.sin_addr.s_addr = inet_addr(_ip);

	nRet = connect(s, (LPSOCKADDR)&sa, nAddrLen);
	if (SOCKET_ERROR == nRet)
	{
		TRACEMSG("socket connect() failse:%d\n", WSAGetLastError());
		ret = STATE_CONNECT_CONNECTSVR;
		PutState(&ret);
		closesocket(s);
		WSACleanup();
		return ret;
	}

	memset(szBuffer, 0, BUFFER_SIZE);
	szBuffer[0] = '1';
	szBuffer[1] = _type + '0'; //身份
	strcpy(&szBuffer[2], _name);

	nRet = send(s, szBuffer, 2+strlen(_name), 0);
	if (SOCKET_ERROR == nRet)
	{
		TRACEMSG("Socket send() failse:%d\n", WSAGetLastError());
		ret = STATE_CONNECT_SEND;
		PutState(&ret);
		closesocket(s);
		WSACleanup();
		return ret;
	}

	_connecting = true;
	while (_connecting)
	{
		//_connecting = false;

		memset(szBuffer, 0, BUFFER_SIZE);
		nRet = recv(s, szBuffer, BUFFER_SIZE, 0);
		if (nRet == -1 && WSAGetLastError() == WSAETIMEDOUT)
		{
			if (_connecting)
			{
				// 超时
				ret = STATE_CONNECT_TIMEOUT;
			}
			else
			{
				ret = STATE_CONNECT_CLOSE;
				closesocket(s);
				WSACleanup();
				return ret;
			}
		}
		else if (nRet <= 0) //断开连接
		{
			printf("Sokcet:%d, was disconnected.\n", s);
			ret = STATE_CONNECT_RECV;
			break;
		}
		else
		{
			printf("Socket:%d, %d, send data:%s\n", s, nRet, szBuffer);

			// 处理数据
			if (nRet > 0)
			{
				char flag = *(char*)szBuffer;
				TRACE("Server ECHO back:%c\n", flag);
				if (flag == '0')
				{
					if (_type == IDENTITY_EXPERT || _type == IDENTITY_LISTENER)
					{
						// 等待作业员来连接
						ret = STATE_CONNECT_WAITFOR;
						PutState(&ret);
						continue;
					}
					else
					{
						// 木有找到接线员，等等再说
						//MessageBox(NULL, _T("木有找到接线员，请稍候再连接！"), _T("Frss"), MB_OK);
						ret = STATE_CONNECT_NOLISTENER;
						break;
					}
				}
				else if (flag == '1')
				{
					if (nRet > 4)
					{
						char* tarIp = inet_ntoa(*(in_addr*)(szBuffer+1));
						TRACE("Server ECHO back: ip: %s\n",  tarIp);

						if (nRet > 5)
						{
							char name[256];
							strncpy(name, szBuffer+5, nRet-5);
							name[nRet-5] = '\0';
							PutName(CA2T(name));
						}

						if (_type == IDENTITY_EXPERT || _type == IDENTITY_LISTENER)
						{
							//if (MessageBox(NULL, _T("来自用户的通话将被接入！\n是否允许？"), _T("Frss"), MB_YESNO) == IDYES)
							{
								int ret1 = InitATMR(tarIp);
								if (ret1 != 0)
								{
									TRACE("udp error: %d\n", ret1);
									ret = STATE_CONNECT_UDPERROR;
								}
								else
								{
									ret = STATE_CONNECT_OK;
								}
								break;
							}
							//else
							//{
							//	ret = STATE_CONNECT_CLOSE;
							//	break;
							//}
						}
						else
						{
							int ret1 = InitATMR(tarIp);
							if (ret1 != 0)
							{
								TRACE("udp error: %d\n", ret1);
								ret = STATE_CONNECT_UDPERROR;
							}
							else
							{
								ret = STATE_CONNECT_OK;
							}
							break;
						}
					}
					else
					{
						ret = STATE_CONNECT_WRONGDATA;
						break;
					}
				}
				else
				{
					ret = STATE_CONNECT_WRONGDATA;
					break;
				}
			}
			else
			{
				ret = STATE_CONNECT_WRONGDATA;
				break;
			}
		}
	}

	PutState(&ret);
	closesocket(s);
	WSACleanup();
	return ret;
}

int CConnectMR::InitATMR(char* listenerIp)
{
	if ((*_ppatmr) == NULL)
	{
		(*_ppatmr) = new CAudioTalkMR(_pcmt);
	}
	if ((*_ppatmr) == NULL)
	{
		TRACEMSG("new memory fault!\n");
		return 1;
	}

	int ret = (*_ppatmr)->Initialize(listenerIp, RTP_PORT, RTP_PORT, _type);
	//if (ret == 0)
	//{
	//	MessageBox(NULL, _T("begin"), NULL, MB_OK);
	//}

	return ret;
}

void CConnectMR::UninitATMR()
{
	if (*_ppatmr != NULL)
	{
		CAudioTalkMR* patmr = *_ppatmr;
		(*_ppatmr)->UnInitialize();
		delete (*_ppatmr);
		*_ppatmr = NULL;
		//MessageBox(NULL, _T("end"), NULL, MB_OK);
	}

	long ret = STATE_CONNECT_CLOSE;
	PutState(&ret);
}

int CConnectMR::Offline()
{
	if ((*_ppatmr) == NULL)
	{
		(*_ppatmr) = new CAudioTalkMR(_pcmt);
	}
	if ((*_ppatmr) == NULL)
	{
		TRACEMSG("new memory fault!\n");
		return 1;
	}

	int ret = (*_ppatmr)->InitOffline();

	return ret;
}

void CConnectMR::Offover()
{
	if (*_ppatmr != NULL)
	{
		(*_ppatmr)->UnInitOffline();
		delete (*_ppatmr);
		*_ppatmr = NULL;
	}

	long ret = STATE_CONNECT_CLOSE;
	PutState(&ret);
}

void CConnectMR::PutState(long* pstate)
{
	_pcmt->Fire_GetState(pstate);
}

void CConnectMR::PutName(BSTR str)
{
	_pcmt->Fire_GetName(str);
}

#ifndef _WIN32_WCE
UINT __stdcall CConnectMR::Thread(void *pParam)
#else
DWORD WINAPI CConnectMR::Thread(void *pParam)
#endif // _WIN32_WCE
{
	ASSERT(pParam);

	HRESULT hRes = CoInitializeEx(NULL, COINIT_MULTITHREADED);
	if (hRes != S_OK)
		return -1;

	CConnectMR * pClient = (CConnectMR *)pParam;
	pClient->Connect();
	//CMAudioTalk* cmt = (CMAudioTalk*)pParam;
	//cmt->m_cmr->Connect();

	::CoUninitialize ();

	return 0;
}
