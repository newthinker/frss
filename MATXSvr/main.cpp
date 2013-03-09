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


#define IDENTITY_DISPATCHER   '4'  //�ַ�Ա - ������ש��
#define IDENTITY_EXPERT       '5'  //ש�� - �����ַ�Ա����
#define IDENTITY_LISTENER     '7'  //����Ա - ��������ɫ����

typedef struct MATXUSER
{
	SOCKET fd;
	ULONG  ip;
	char   name[256];
} MU;

// ȫ�ֱ���
map<SOCKET, ULONG> gAllConn; //��������
map<SOCKET, MU> gListeners; //����Ա
map<SOCKET, MU> gExperts; //ש����

// ����
BOOL insertSocket(SOCKET *pClient, SOCKET s);
void eraseConn(SOCKET s);

// ���
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
		printf("\n�׽��ֳ�ʼ������%d\n", WSAGetLastError());
		return 1;
	}

	s = socket(AF_INET, SOCK_STREAM, 0);
	if (INVALID_SOCKET == s)
	{
		printf("\n�׽��ִ�������%d\n", WSAGetLastError());
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
		printf("Socket�󶨴���%d\n", WSAGetLastError());
		return 1;
	}

	nRet = listen(s, 8);
	if (SOCKET_ERROR == nRet)
	{
		printf("Socket listen error:%d\n", WSAGetLastError());
		return 1;
	}

	printf("��ʼ����ɣ���ʼ������\n");

	fd_set fdRead;
	for(i = 0;i < MAX_CLIENT; i++)
	{
		arrClientSocket[i] = INVALID_SOCKET;
	}

	for(;;)
	{
		//��ʼ�����У��������׽��ּ��롣
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
			printf("\n��������Ӵ���%d\n", WSAGetLastError());
			break;
		}

		if (nRet > 0)
		{
			//˵������SOCKET���ӵ�������
			if(FD_ISSET(s, &fdRead))
			{
				sClient = accept(s, (LPSOCKADDR)&saRemote, &nLen);
				insertSocket(arrClientSocket, sClient);
				gAllConn[sClient] = saRemote.sin_addr.s_addr;
				printf("�������ӣ�%s[%d]\n", inet_ntoa(saRemote.sin_addr), sClient);
				continue;
			}
			for(i = 0; i < MAX_CLIENT; i++)
			{
				if (FD_ISSET(arrClientSocket[i], &fdRead))
				{
					unsigned int iip = gAllConn[arrClientSocket[i]];
					memset(szBuffer, 0, BUFFER_SIZE);
					nRet = recv(arrClientSocket[i], szBuffer, BUFFER_SIZE, 0);
					if (nRet <= 0) //�Ͽ�����
					{
						printf("����%s[%d]�ѶϿ���\n", inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
						eraseConn(arrClientSocket[i]);
						closesocket(arrClientSocket[i]);
						arrClientSocket[i] = INVALID_SOCKET;
						continue;
					}

					nRecvLen = nRet;
					printf("����%s[%d]�������ݣ�[%d]%s\n", inet_ntoa(*(in_addr*)&iip), arrClientSocket[i], nRecvLen, szBuffer);

					// ��������
					// ��1���ֽڣ����ͣ�1�������
					// ��2���ֽڣ���ݱ�ʶ 4�ַ�Ա 5ש�� 7����Ա
					// ��3��������
					if (nRecvLen >= 2)
					{
						if (szBuffer[0] == '1') //�������
						{
							if (szBuffer[1] == IDENTITY_LISTENER) //�ǽ���Ա
							{
								printf("����Ա[%d]�Ѿ�λ��\n", arrClientSocket[i]);
								MU mu;
								mu.fd = arrClientSocket[i];
								mu.ip = gAllConn[mu.fd];
								strncpy(mu.name, &szBuffer[2], nRecvLen-2);
								mu.name[nRecvLen-2] = '\0';
								gListeners[mu.fd] = mu;

								// �ȴ�����������
								nRet = send(arrClientSocket[i], "0", 1, 0);
								if (SOCKET_ERROR == nRet)
								{
									printf("������Ա[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
									eraseConn(arrClientSocket[i]);
								}
								else
								{
								}
							}
							else if (szBuffer[1] == IDENTITY_EXPERT) //��ש��
							{
								printf("ר��[%d]�Ѿ�λ��\n", arrClientSocket[i]);
								MU mu;
								mu.fd = arrClientSocket[i];
								mu.ip = gAllConn[mu.fd];
								strncpy(mu.name, &szBuffer[2], nRecvLen-2);
								mu.name[nRecvLen-2] = '\0';
								gExperts[mu.fd] = mu;

								// �ȴ�����������
								nRet = send(arrClientSocket[i], "0", 1, 0);
								if (SOCKET_ERROR == nRet)
								{
									printf("��ר��[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
									eraseConn(arrClientSocket[i]);
								}
								else
								{
								}
							}
							else if (szBuffer[1] == IDENTITY_DISPATCHER) //�ַ�Ա
							{
								char name[256];
								strncpy(name, &szBuffer[2], nRecvLen-2);
								name[nRecvLen-2] = '\0';
								if (gExperts.size() == 0)
								{
									// ����ľ�����ŵ�ש����
									nRet = send(arrClientSocket[i], "0", 1, 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("���ַ�Ա[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}
								}
								else if (gExperts.find(arrClientSocket[i]) == gExperts.end()) //���ǽ���Ա
								{
									// ��һ����æ�Ľ���Ա����
									int idx = rand() % gExperts.size();
									map<SOCKET, MU>::iterator it = gExperts.begin();
									for (int j=0; j<idx; ++j)
									{
										++it;
									}
									printf("�ַ�Ա[%s][%s][%d]", 
										name, inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
									printf("�ҵ�һλר��[%s][%s][%d]��\n", 
										it->second.name, inet_ntoa(*(in_addr*)&it->second.ip), it->first);

									char buf[BUFFER_SIZE];
									buf[0] = '1';
									memcpy(buf + 1, (char*)&it->second.ip, sizeof(it->second.ip));
									strcpy(buf + 1+sizeof(it->second.ip), it->second.name);
									nRet = send(arrClientSocket[i], buf, 1+sizeof(it->second.ip)+strlen(it->second.name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("���ַ�Ա[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
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
										printf("��ר��[%d]������Ϣʧ�ܣ�%d\n", it->first, WSAGetLastError());
										eraseConn(it->first);
									}
									else
									{
									}

									// ������ȴ���Ч����Ա�ļ�����T��ȥ
									//gListeners.erase(it);
									//gAllConn.erase(arrClientSocket[i]);
								}
								else //��Ч
								{
								}
							}
							else //���ǽ���Ա
							{
								char name[256];
								strncpy(name, &szBuffer[2], nRecvLen-2);
								name[nRecvLen-2] = '\0';
								if (gListeners.size() == 0)
								{
									// ����ľ�����ŵĽ���Ա��
									nRet = send(arrClientSocket[i], "0", 1, 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("����ҵԱ[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
										eraseConn(arrClientSocket[i]);
									}
									else
									{
									}
								}
								else if (gListeners.find(arrClientSocket[i]) == gListeners.end()) //���ǽ���Ա
								{
									// ��һ����æ�Ľ���Ա����
									int idx = rand() % gListeners.size();
									map<SOCKET, MU>::iterator it = gListeners.begin();
									for (int j=0; j<idx; ++j)
									{
										++it;
									}
									printf("��ҵԱ[%s][%s][%d]", 
										name, inet_ntoa(*(in_addr*)&iip), arrClientSocket[i]);
									printf("�ҵ�һλ����Ա[%s][%s][%d]��\n", 
										it->second.name, inet_ntoa(*(in_addr*)&it->second.ip),  it->first);

									char buf[BUFFER_SIZE];
									buf[0] = '1';
									memcpy(buf + 1, (char*)&it->second.ip, sizeof(it->second.ip));
									strcpy(buf + 1+sizeof(it->second.ip), it->second.name);
									nRet = send(arrClientSocket[i], buf, 1+sizeof(it->second.ip)+strlen(it->second.name), 0);
									if (SOCKET_ERROR == nRet)
									{
										printf("����ҵԱ[%d]������Ϣʧ�ܣ�%d\n", arrClientSocket[i], WSAGetLastError());
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
										printf("������Ա[%d]������Ϣʧ�ܣ�%d\n", it->first, WSAGetLastError());
										eraseConn(it->first);
									}
									else
									{
									}

									// ������ȴ���Ч����Ա�ļ�����T��ȥ
									//gListeners.erase(it);
									//gAllConn.erase(arrClientSocket[i]);
								}
								else //��Ч
								{
								}
							}
						}
						else //��Ч
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
