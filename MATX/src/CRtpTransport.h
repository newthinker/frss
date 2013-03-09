#pragma once
#include <jrtplib3/rtpsession.h>
#include <jrtplib3/rtpudpv4transmitter.h>
#include <jrtplib3/rtpipv4address.h>
#include <jrtplib3/rtpsessionparams.h>
#include <jrtplib3/rtperrors.h>
#include <jthread/jthread.h>
#include <jthread/jmutex.h>
using namespace jthread;
using namespace jrtplib;

class CRtpTransport : private JThread
{
	public:
		CRtpTransport();
		~CRtpTransport(void);

	public:
		static int Initialization();
		static int Destory();
		int CreateSession(char * pRemoteIp,unsigned short RemotePort,int iPayloadType,char * pLoacalIp=0,unsigned short loaclPort=0);
		int DestorySession();
		virtual void CallBackBuffer(unsigned int pSeqNumber,unsigned char * pBuffer,unsigned long lBufferLength,char * pRemoteIp,unsigned short RemotePort)=0;
		int SendRTPBuffer(unsigned char * pBuffer,unsigned long lBufferLength);
		int SetPayloadType(int iPayloadType);
		int AddRemoteList(char * pRemoteIp,unsigned short RemotePort);
		int RemoveRemoteList(char * pRemoteIp,unsigned short RemotePort);
		
	private:
		RTPSession sess;
		RTPUDPv4TransmissionParams transparams;
		RTPSessionParams sessparams;
		int iSessid;
	
		bool stop;
		JMutex stopmutex;
	private:
		void *Thread();
		int Start();
		void Stop();
};