#include <winsock2.h>
#include <stdio.h>
#include <stdlib.h>
#include <map>
using namespace std;

#define VERSION "1.0.1.4"

#define DEFAULT_PORT	5566
#define BUFFER_SIZE		4096
#define DEFAULT_ADDR_LEN 128
#define MAX_CLIENT (FD_SETSIZE -1)


#define IDENTITY_DISPATCHER   '4'  //分发员 - 仅连接砖家
#define IDENTITY_EXPERT       '5'  //砖家 - 仅供分发员连接
#define IDENTITY_LISTENER     '7'  //接线员 - 供其它角色连接

typedef struct MATXUSER
{
	SOCKET fd;
	ULONG  ip;
	char   name[256];
} MU;

// 全局变量
map<SOCKET, ULONG> gAllConn; //所有连接
map<SOCKET, MU> gListeners; //接线员
map<SOCKET, MU> gExperts; //砖家们

// 函数
BOOL insertSocket(SOCKET *pClient, SOCKET s);
void eraseConn(SOCKET s);

// 入口
int main(int argc, char *argv[])
{
	WSAData wsaData;
	WORD wVersion = MAKEWORD(2,2);
	SOCKET s, sClient;
	SOCKADDR_IN sa, saRemote;
	int nRet, nLen, i, nRecvLen;
	char szBuffer[BUFFER_SIZE];
	SOCKET arrClientSocket[MAX_CLIENT] = {INVALID_SOCKET};

	//checkArgv(argc, argv);
	nRet = WSAStartup(wVersion, &wsaData);

	if (SOCKET_ERROR == nRet)
	{
		printf("\n套接字初始化错误：%d\n", WSAGetLastError());
		return 1;
	}

	s = socket(AF_INET, SOCK_STREAM, 0);
	if (INVALID_SOCKET == s)
	{
		printf("\n套接字创建错误：%d\n", WSAGetLastError());
		return 1;
	}

	nLen = sizeof(SOCKADDR_IN);
	memset(&sa, 0, nLen);
	sa.sin_family = AF_INET;
	sa.sin_port	  = htons(DEFAULT_PORT);
	sa.sin_addr.s_addr = htonl(INADDR_ANY);

	nRet = bind(s, (LPSOCKADDR)&sa, nLen);
	if (SOCKET_ERROR == nRet)
	{
		printf("Socket绑定错误：%d\n", WSAGetLastError());
		return 1;
	}

	nRet = listen(s, 8);
	if (SOCKET_ERROR == nRet)
	{
		printf("Socket listen error:%d\n", WSAGetLastError());
		return 1;
	}

	printf("初始化完成，开始监听：\n");

	fd_set fdRead;
	for(i = 0;i < MAX_CLIENT; i++)
	{
		arrClientSocket[i] = INVALID_SOCKET;
	}

	for(;;)
	{
		//初始化队列，将所有套接字加入。
		FD_ZERO(&fdRead);
		FD_SET(s, &fdRead);
		for(i = 0; i < MAX_CLIENT; i++)
		{
			if (INVALID_SOCKET != arrClientSocket[i])
			{
				FD_SET(arrClientSocket[i], &fdRead);
			}
		}

		nRet = select(0, &fdRead, NULL, NULL, NULL);
		if (SOCKET_ERROR == nRet)
		{
			printf("\n检索活动连接错误：%d\n", WSAGetLastError());
			break;
		}

		if (nRet > 0)
		{
			//说明有新SOCKET连接到服务器
			if(FD_ISSET(s, &fdRead))
			{
				sClient = accept(s, (LPSOCKADDR)&saRemote, &nLen);
				insertSocket(arrClientSocket, sClient);
				gAllConn[sClient] = saRemote.sin_addr.s_addr;
				printf("接入连接：%s[%d]\n", inet_ntoa(saRemote.sin_addr), sClient);
				continue;
			}
			for(i = 0; i < MAX_CLIENT; i++)
			{
				if (FD_ISSET(arrClientSocket[i], &fdRead))
				{
					unsigned int iip = gAllConn[arrClientSocket[i]];
					memset(szBuffer, 0, BUFFER_SIZE);
					nRet = recv(arrClientSocket[i], szBuffer, BUFFER_SIZE, 0);
					if (nRet <= 0) //断开连接
					{
						printf("连接%s[%d]已断开。\n", inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
						eraseConn(arrClientSocket[i]);
						closesocket(arrClientSocket[i]);
						arrClientSocket[i] = INVALID_SOCKET;
						continue;
					}

					nRecvLen = nRet;
					printf("连接%s[%d]发来数据：[%d]%s\n", inet_ntoa(*(in_addr*)&iip), arrClientSocket[i], nRecvLen, szBuffer);

					// 处理数据
					// 第1个字节：类型，1申明身份
					// 第2个字节：身份标识 4分发员 5砖家 7接线员
					// 第3到后：名字
					if (nRecvLen >= 2)
					{
						if (szBuffer[0] == '1') //申明身份
						{
							if (szBuffer[1] == IDENTITY_LISTENER) //是接线员
							{
								printf("接线员[%d]已就位。\n", arrClientSocket[i]);
								MU mu;
								mu.fd = arrClientSocket[i];
								mu.ip = gAllConn[mu.fd];
								strncpy(mu.name, &szBuffer[2], nRecvLen-2);
								mu.name[nRecvLen-2] = '\0';
								gListeners[mu.fd] = mu;

								// 等待别人来连接
								nRet = send(arrClientSocket[i], "0", 1, 0);
								if (SOCKET_ERROR == nRet)
								{
									printf("给接线员[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
									eraseConn(arrClientSocket[i]);
								}
								else
								{
								}
							}
							else if (szBuffer[1] == IDENTITY_EXPERT) //是砖家
							{
								printf("专家[%d]已就位。\n", arrClientSocket[i]);
								MU mu;
								mu.fd = arrClientSocket[i];
								mu.ip = gAllConn[mu.fd];
								strncpy(mu.name, &szBuffer[2], nRecvLen-2);
								mu.name[nRecvLen-2] = '\0';
								gExperts[mu.fd] = mu;

								// 等待别人来连接
								nRet = send(arrClientSocket[i], "0", 1, 0);
								if (SOCKET_ERROR == nRet)
								{
									printf("给专家[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
									eraseConn(arrClientSocket[i]);
								}
								else
								{
								}
							}
							else if (szBuffer[1] == IDENTITY_DISPATCHER) //分发员
							{
								char name[256];
								strncpy(name, &szBuffer[2], nRecvLen-2);
								name[nRecvLen-2] = '\0';
								if (gExperts.size() == 0)
								{
									// 现在木有闲着的砖家了
									nRet = send(arrClientSocket[i], "0", 1, 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给分发员[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}
								}
								else if (gExperts.find(arrClientSocket[i]) == gExperts.end()) //不是接线员
								{
									// 找一个不忙的接线员给他
									int idx = rand() % gExperts.size();
									map<SOCKET, MU>::iterator it = gExperts.begin();
									for (int j=0; j<idx; ++j)
									{
										++it;
									}
									printf("分发员[%s][%s][%d]", 
										name, inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
									printf("找到一位专家[%s][%s][%d]。\n", 
										it->second.name, inet_ntoa(*(in_addr*)&it->second.ip), it->first);

									char buf[BUFFER_SIZE];
									buf[0] = '1';
									memcpy(buf + 1, (char*)&it->second.ip, sizeof(it->second.ip));
									strcpy(buf + 1+sizeof(it->second.ip), it->second.name);
									nRet = send(arrClientSocket[i], buf, 1+sizeof(it->second.ip)+strlen(it->second.name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给分发员[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}

									memcpy(buf + 1, (char*)&iip, sizeof(iip));
									strcpy(buf + 1+sizeof(iip), name);
									nRet = send(it->first, buf, 1+sizeof(iip)+strlen(name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给专家[%d]发送消息失败：%d\n", it->first, WSAGetLastError());
										eraseConn(it->first);
									}
									else
									{
									}

									// 把这货先从有效接线员的集合里T出去
									//gListeners.erase(it);
									//gAllConn.erase(arrClientSocket[i]);
								}
								else //无效
								{
								}
							}
							else //不是接线员
							{
								char name[256];
								strncpy(name, &szBuffer[2], nRecvLen-2);
								name[nRecvLen-2] = '\0';
								if (gListeners.size() == 0)
								{
									// 现在木有闲着的接线员了
									nRet = send(arrClientSocket[i], "0", 1, 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给作业员[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}
								}
								else if (gListeners.find(arrClientSocket[i]) == gListeners.end()) //不是接线员
								{
									// 找一个不忙的接线员给他
									int idx = rand() % gListeners.size();
									map<SOCKET, MU>::iterator it = gListeners.begin();
									for (int j=0; j<idx; ++j)
									{
										++it;
									}
									printf("作业员[%s][%s][%d]", 
										name, inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
									printf("找到一位接线员[%s][%s][%d]。\n", 
										it->second.name, inet_ntoa(*(in_addr*)&it->second.ip),  it->first);

									char buf[BUFFER_SIZE];
									buf[0] = '1';
									memcpy(buf + 1, (char*)&it->second.ip, sizeof(it->second.ip));
									strcpy(buf + 1+sizeof(it->second.ip), it->second.name);
									nRet = send(arrClientSocket[i], buf, 1+sizeof(it->second.ip)+strlen(it->second.name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给作业员[%d]发送消息失败：%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}

									memcpy(buf+1, (char*)&iip, sizeof(iip));
									strcpy(buf + 1+sizeof(iip), name);
									nRet = send(it->first, buf, 1+sizeof(iip)+strlen(name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("给接线员[%d]发送消息失败：%d\n", it->first, WSAGetLastError());
										eraseConn(it->first);
									}
									else
									{
									}

									// 把这货先从有效接线员的集合里T出去
									//gListeners.erase(it);
									//gAllConn.erase(arrClientSocket[i]);
								}
								else //无效
								{
								}
							}
						}
						else //无效
						{
						}
					}
				}
			}
		}
	}
	closesocket(s);
	WSACleanup();
	return 0;
}

BOOL insertSocket(SOCKET *pClient, SOCKET s)
{
	int i;
	BOOL bResult = FALSE;
	for(i = 0; i < MAX_CLIENT; i++)
	{
		if (INVALID_SOCKET == pClient[i])
		{
			pClient[i] = s;
			bResult = TRUE;
			break;
		}
	}
	return bResult;
}

void eraseConn(SOCKET s)
{
	map<SOCKET, ULONG>::iterator it1 = gAllConn.find(s);
	if (it1 != gAllConn.end())
	{
		gAllConn.erase(it1);
	}
	map<SOCKET, MU>::iterator it = gListeners.find(s);
	if (it != gListeners.end())
	{
		gListeners.erase(it);
	}
	it = gExperts.find(s);
	if (it != gExperts.end())
	{
		gExperts.erase(it);
	}
}
