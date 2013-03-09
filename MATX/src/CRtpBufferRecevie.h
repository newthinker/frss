#pragma once
#include "crtptransport.h"
#include "CWinJrtpThread.h"

#pragma pack(1)

class CAudioTalkMR;
class CRtpBufferRecevie : public CRtpTransport
{
	struct SPacket
	{
		char PacketType;
		long lDateStamp;
		unsigned int iSeqNumber;
		unsigned int iSum;
		unsigned int iBufferLength;
		unsigned char * pBuffer;
	};

	struct SSendPacket
	{
		char PacketType;  //video:I P B Audio:A  Nat:N  Retransport:R
		long lDateStamp;
		unsigned int iSeqNumber;
		unsigned int iSum;
		unsigned int iBufferLength;
	};

	public:
		CRtpBufferRecevie(void);
		~CRtpBufferRecevie(void);
	public:
		void CallBackBuffer(unsigned int pSeqNumber,unsigned char * pBuffer,unsigned long lBufferLength,char * pRemoteIp,unsigned short RemotePort);
		int SendBuffer(unsigned char * pBuffer,unsigned long lBufferLength);

		void Init(CAudioTalkMR * pCMpeg4Decode);
		 
	#ifdef _WIN32_WCE
		static DWORD WINAPI TheThread(void *param);
	#else
		static UINT __stdcall TheThread(void *param);
	#endif // _WIN32_WCE
	private:
		JMutex receiveMutex;
		CWinJrtpThread m_CWinThread;
		UINT iThreadId;

		SPacket m_eSPacket[10];
		unsigned int m_iPacketLen;

		unsigned char * m_pEncBuffer;
		unsigned int m_pEncBufferLen;
		unsigned char * m_pTempBuffer;

		CAudioTalkMR * m_cAudioTalkMR;

		unsigned int m_iSendSequence;
	private:
		int GetBufferForDequeue();
};
#pragma pack()