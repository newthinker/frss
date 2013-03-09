#include "stdafx.h"
#include ".\crtptransport.h"
#ifndef WIN32
	#include <netinet/in.h>
	#include <arpa/inet.h>
#else
	#include <winsock2.h>
#endif // WIN32
#include <stdio.h>
#include <iostream>
#include <jrtplib3/rtppacket.h>
#include <jrtplib3/rtpsourcedata.h>

CRtpTransport::CRtpTransport()
{
	iSessid=0;
}

CRtpTransport::~CRtpTransport(void)
{
}

int CRtpTransport::Initialization()
{
#ifdef WIN32
	WSADATA dat;
	WSAStartup(MAKEWORD(2,2),&dat);
#endif // WIN32
	return 1;
}

int CRtpTransport::Destory()
{
#ifdef WIN32
	WSACleanup();
#endif // WIN32
	return 1;
}

int CRtpTransport::CreateSession(char * pRemoteIp,unsigned short RemotePort,int iPayloadType,char * pLoacalIp,unsigned short loaclPort)
{
	if( NULL==pRemoteIp || 0==RemotePort || 0==loaclPort || 0!=iSessid )
		return -1;

	int status;
	uint32_t remoteIp = inet_addr(pRemoteIp);
	if (remoteIp == INADDR_NONE)
	{
		return -2;
	}
	remoteIp = ntohl(remoteIp);

	uint32_t localIp = 0;
	if( NULL!=pLoacalIp )
	{
		localIp = inet_addr(pLoacalIp);
		if (localIp == INADDR_NONE)
		{
			return -3;
		}
		localIp = ntohl(localIp);
	}

	sessparams.SetOwnTimestampUnit(1.0/8000.0);		
	
	sessparams.SetAcceptOwnPackets(true);
	transparams.SetPortbase(loaclPort);
	if( localIp!=0)
		transparams.SetBindIP(localIp);
	status = sess.Create(sessparams,&transparams);
	if (status < 0)
	{
		return status;
	}

	RTPIPv4Address addr(remoteIp,RemotePort);

	status = sess.AddDestination(addr);	
	if (status < 0)
	{
		return status;
	}
	
	//RTPIPv4Address addr2(remoteIp,RemotePort+2);
	//status = sess.AddDestination(addr2);	
	
	status = sess.SetDefaultPayloadType(iPayloadType);
	if (status < 0)
	{
		return status;
	}

	status = sess.SetDefaultMark(false);
	if (status < 0)
	{
		return status;
	}

	status = sess.SetDefaultTimestampIncrement(1);
	if (status < 0)
	{
		return status;
	}

	status = sess.SetTimestampUnit(1);
	if (status < 0)
	{
		return status;
	}

	iSessid=1;
	status = Start();
	if (status < 0)
	{
		return status;
	}

	return status;
}

int CRtpTransport::DestorySession()
{
	if(0!=iSessid)
	{
		Stop();
		sess.BYEDestroy(RTPTime(10.0),0,0);
		iSessid=0;
	}
	return 1;
}

int CRtpTransport::SendRTPBuffer(unsigned char * pBuffer,unsigned long lBufferLength)
{
	int iRet=0;
	if(0!=iSessid)
	iRet=sess.SendPacket((void *)pBuffer,lBufferLength);
	return iRet;
}

int CRtpTransport::SetPayloadType(int iPayloadType)
{
	int iRet=0;
	if(0!=iSessid)
	iRet=sess.SetDefaultPayloadType(iPayloadType);
	return iRet;
}

int CRtpTransport::AddRemoteList(char * pRemoteIp,unsigned short RemotePort)
{
	if( NULL==pRemoteIp || 
		0==RemotePort || 
		0==iSessid)
		return -1;

	int status;
	uint32_t remoteIp = inet_addr(pRemoteIp);
	if (remoteIp == INADDR_NONE)
	{
		return -2;
	}

	RTPIPv4Address addr(remoteIp,RemotePort);
	
	status = sess.AddDestination(addr);
	return status;
}

int CRtpTransport::RemoveRemoteList(char * pRemoteIp,unsigned short RemotePort)
{
	if( NULL==pRemoteIp || 
		0==RemotePort || 
		0==iSessid )
		return -1;

	int status;
	uint32_t remoteIp = inet_addr(pRemoteIp);
	if (remoteIp == INADDR_NONE)
	{
		return -2;
	}

	RTPIPv4Address addr(remoteIp,RemotePort);
	status=sess.DeleteDestination(addr);
	return status;
}

int CRtpTransport::Start()
{
	if (JThread::IsRunning())
		return ERR_RTP_POLLTHREAD_ALREADYRUNNING;
	
	if (!stopmutex.IsInitialized())
	{
		if (stopmutex.Init() < 0)
			return ERR_RTP_POLLTHREAD_CANTINITMUTEX;
	}
	stop = false;
	if (JThread::Start() < 0)
		return ERR_RTP_POLLTHREAD_CANTSTARTTHREAD;
	return 0;
}

void CRtpTransport::Stop()
{	
	if (!IsRunning())
		return;
	
	stopmutex.Lock();
	stop = true;
	stopmutex.Unlock();
	
	RTPTime thetime = RTPTime::CurrentTime();
	bool done = false;

	while (JThread::IsRunning() && !done)
	{
		// wait max 5 sec
		RTPTime curtime = RTPTime::CurrentTime();
		if ((curtime.GetDouble()-thetime.GetDouble()) > 5.0)
			done = true;
		RTPTime::Wait(RTPTime(0,10000));
	}

	if (JThread::IsRunning())
	{
#ifndef _WIN32_WCE
		std::cerr << "RTPPollThread: Warning! Having to kill thread!" << std::endl;
#endif // _WIN32_WCE
		JThread::Kill();
	}
	stop = false;
}

void *CRtpTransport::Thread()
{
	JThread::ThreadStarted();
	
	bool stopthread;

	stopmutex.Lock();
	stopthread = stop;
	stopmutex.Unlock();

	while(!stopthread)
	{
		stopmutex.Lock();
		if( stop )
		{
			stopmutex.Unlock();
			return 0;
		}
		stopmutex.Unlock();
		sess.BeginDataAccess();

		// check incoming packets
		if (sess.GotoFirstSourceWithData())
		{
			do
			{
				RTPPacket *pack;
				
				while ((pack = sess.GetNextPacket()) != NULL)
				{
					const RTPIPv4Address * pRTPIPv4Address=(RTPIPv4Address *)(((RTPSourceData *)sess.GetCurrentSourceInfo())->GetRTPDataAddress());
					// You can examine the data here
					char pRemoteIp[32]={0};
					uint16_t remotePort=0;
					if( NULL!=pRTPIPv4Address)
					{
						printf("Got packet !\n");
						uint32_t remoteIp=pRTPIPv4Address->GetIP();
						remoteIp=htonl(remoteIp);
						struct in_addr addr1;
						memcpy(&addr1, &remoteIp, 4);
						strcpy(pRemoteIp,inet_ntoa(addr1));

						remotePort=pRTPIPv4Address->GetPort();
					}
					
					this->CallBackBuffer((unsigned int)pack->GetSequenceNumber(),
										pack->GetPayloadData(),
										(unsigned long)pack->GetPayloadLength(),
										pRemoteIp,
										remotePort);
					
					// we don't longer need the packet, so
					// we'll delete it
					sess.DeletePacket(pack);
				}
			} while (sess.GotoNextSourceWithData());
		}
		
		sess.EndDataAccess();

#ifndef RTP_SUPPORT_THREAD
		status = sess.Poll();
		checkerror(status);
#endif // RTP_SUPPORT_THREAD
		
		RTPTime::Wait(RTPTime(0,1000));
	}

	return 0;
}