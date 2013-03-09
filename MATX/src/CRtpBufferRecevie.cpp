#include "stdafx.h"
#include "crtpbufferrecevie.h"
#include <stdio.h>
#include <iostream>
#include "CAudioTalkMR.h"

#define _PACKET_BUFFER_SIZE 1300

CRtpBufferRecevie::CRtpBufferRecevie(void)
{
	this->Initialization();
	
	m_iSendSequence=1;
	if (!receiveMutex.IsInitialized())
	{
		if (receiveMutex.Init() < 0)
			return ;
	}
	m_iPacketLen=10;
	for(int i=0;i<m_iPacketLen;i++)
	{
		m_eSPacket[i].pBuffer=new unsigned char [160];
	}

	m_pEncBuffer=new unsigned char [160];
	m_pEncBufferLen=0;

	m_pTempBuffer=new unsigned char [320];

	m_CWinThread.SetParams((void *)this,(ThreadCallBack *)(this->TheThread),true);
	iThreadId=m_CWinThread.Start();
}

CRtpBufferRecevie::~CRtpBufferRecevie(void)
{
	for(int i=0;i<m_iPacketLen;i++)
	{
		if(NULL!=m_eSPacket[i].pBuffer)
		{
			delete [] m_eSPacket[i].pBuffer;
			m_eSPacket[i].pBuffer=NULL;
		}
	}
}

void CRtpBufferRecevie::Init(CAudioTalkMR * pCMpeg4Decode)
{
	m_cAudioTalkMR=pCMpeg4Decode;
}

void CRtpBufferRecevie::CallBackBuffer( unsigned int pSeqNumber,
										unsigned char * pBuffer,
									    unsigned long lBufferLength,
										char * pRemoteIp,
										unsigned short RemotePort )
{
	receiveMutex.Lock();
	unsigned int iSeqNumber;
	SSendPacket eSPacket;
	memcpy(&eSPacket,pBuffer,sizeof(SSendPacket));

	iSeqNumber=eSPacket.iSeqNumber;
	unsigned int iTempSeqNumber=0;
	unsigned int iInedx=0;
	for(int i=0;i<m_iPacketLen;i++)
	{
		if( 0==m_eSPacket[i].iSeqNumber )
		{
			iTempSeqNumber=m_eSPacket[i].iSeqNumber;
			iInedx=i;
			break ;
		}
		else
		{
			if( 0==iTempSeqNumber ||
				iTempSeqNumber>m_eSPacket[i].iSeqNumber )
			{
				iTempSeqNumber=m_eSPacket[i].iSeqNumber;
				iInedx=i;
			}
		}
	}

	m_eSPacket[iInedx].iSeqNumber=iSeqNumber;
	m_eSPacket[iInedx].iBufferLength=lBufferLength-sizeof(SSendPacket);
	memcpy(m_eSPacket[iInedx].pBuffer,pBuffer+sizeof(SSendPacket),m_eSPacket[iInedx].iBufferLength);

	::PostThreadMessage(iThreadId,1001,NULL,NULL);

	receiveMutex.Unlock();
}

int CRtpBufferRecevie::SendBuffer(unsigned char * pBuffer,unsigned long lBufferLength)
{
	int iRet=0;
	if( NULL==pBuffer ||
		0==lBufferLength )
		return -1;

	SSendPacket pSPacket;
	pSPacket.iBufferLength=lBufferLength;
	pSPacket.iSum=0;
	pSPacket.lDateStamp=0;
	pSPacket.iSeqNumber=m_iSendSequence;
	pSPacket.PacketType='A';
		
	memcpy(m_pTempBuffer,&pSPacket,sizeof(SSendPacket));
	memcpy(m_pTempBuffer+sizeof(SSendPacket),pBuffer,pSPacket.iBufferLength);
	iRet=this->SendRTPBuffer(m_pTempBuffer,lBufferLength+sizeof(SSendPacket));

	m_iSendSequence++;

	return iRet;
}

int CRtpBufferRecevie::GetBufferForDequeue()
{
	bool bHave=false;
	{
		receiveMutex.Lock();
		unsigned int iTempSeqNumber=0;
		int iInedx=-1;
		for(int i=0;i<m_iPacketLen;i++)
		{
			if( 0!=m_eSPacket[i].iSeqNumber )
			{
				if( 0==iTempSeqNumber ||
					iTempSeqNumber>m_eSPacket[i].iSeqNumber )
				{
					iTempSeqNumber=m_eSPacket[i].iSeqNumber;
					iInedx=i;
				}
			}
		}
		
		if( -1!=iInedx )
		{
			m_pEncBufferLen=m_eSPacket[iInedx].iBufferLength;
			memcpy(m_pEncBuffer,m_eSPacket[iInedx].pBuffer,m_pEncBufferLen);
			bHave=true;
		}

		receiveMutex.Unlock();
	}

	if(bHave)
	{
		///////
		m_cAudioTalkMR->ReceiveCallback((char *)m_pEncBuffer,m_pEncBufferLen);
	}

	return 1;
}

#ifndef _WIN32_WCE
UINT __stdcall CRtpBufferRecevie::TheThread(void *param)
#else
DWORD WINAPI CRtpBufferRecevie::TheThread(void *param)
#endif // _WIN32_WCE
{
	MSG msg;
	CRtpBufferRecevie * pCRtpBufferRecevie=(CRtpBufferRecevie *)param;
	 
    while(GetMessage(&msg,0,0,0)) //get msg from message queue
    {
        switch(msg.message)
        {
			case 1001:
			{
				pCRtpBufferRecevie->GetBufferForDequeue();
				break;
			}
			case 1002:
			{
				return 0;
			}
        }
    }

	return 0;
}