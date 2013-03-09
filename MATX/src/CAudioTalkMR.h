#if !defined(AFX_DLGTALKMR_H__7A5D50DD_8FF3_485D_AA16_E864261314DC__INCLUDED_)
#define AFX_DLGTALKMR_H__7A5D50DD_8FF3_485D_AA16_E864261314DC__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "crtpbufferrecevie.h"
#include "soundin.h"
#include "wavesound.h"
#include "WaveWriteFile.h"

#define AUDENCSIZE			1280
#define AUDDECSIZE			80

#define BITS_PER_SAMPLE		16
#define CHANNEL				1
#define SAMPLES_PER_SECOND	16000

#define BIT_RATE_16000		16000
#define AUDIOBUF			(80*40L)

class CMAudioTalk;
class CAudioTalkMR
{
	public:
		CAudioTalkMR(CMAudioTalk* pcmt);
		~CAudioTalkMR();

		// for operator
		int Initialize(	char * pRemoteIpaddr,
							unsigned short sRemotePort,
							unsigned short sLocalPort,
							int level	);

		void UnInitialize();

		// 离线
		int InitOffline();
		void UnInitOffline();

		// 回调状态
		void PutState(long* pstate);
		void PutString(BSTR str);

		BOOL Destory();

		BOOL PutIntoBuf(char *buf, int dwSize);
		
		void ReceiveCallback(char *buf, int dwSize);

	private:
	#ifndef _WIN32_WCE
		static UINT __stdcall PlayAudioThread(void *pParam);
	#else
		static DWORD WINAPI PlayAudioThread(void *pParam);
	#endif // _WIN32_WCE

		static void CALLBACK DataFromSoundIn(char* buffer, DWORD dwSize, DWORD dwOwner);

		int CreateTalk();
		void DestoryTalk();
		
		BOOL CopyAudioData(PBYTE lpTemp, DWORD dwReadLength);
		void InputAudioData(void);
	protected:
		BOOL SendDataToDevice(char *buf, DWORD dwSize);	
	protected:
		BOOL m_bWaveDeal; //是否已初始化

		BOOL m_bOnLine; //是否在线

		WAVEFORMATEX m_struWaveFormat;	
		CWaveSound	m_SoundOut;
		CSoundIn	m_SoundIn;
		CWaveWriteFile m_SoundOutFile;
		CWaveWriteFile m_SoundInFile;
		BOOL m_bOpenWavOut;
		BOOL m_bOpenWavIn;
		BOOL m_bOpenWavOutFile;
		BOOL m_bOpenWavInFile;

		int m_userlevel; //用户权限等级
		TCHAR m_wavPath[MAX_PATH]; //save file to this path
		
		LPBYTE  m_pRenderBuf;		//buffer
		int     m_rIndexRV;			//data that has already been read
		int     m_ReceiveIndexRV;		//received data for m_pRenderBuf
		int     m_nBufNo;			
		BOOL	m_bOpenPlayThread;
		
		void * m_pDecoder;//g722 decoder
		void * m_pEncoder;//g722 encoder
		
		DWORD m_dwBufSize;
		DWORD m_dwBufNum;

		BYTE m_byDecBuf[AUDENCSIZE];
		BYTE m_byEncBuf[AUDDECSIZE];
		
		HANDLE m_hExitEvent;
		HANDLE m_hHaveRDataRV;

	private:
		CRtpBufferRecevie m_cRtpBufferRecevie;

		CMAudioTalk* _pcmt;
};
#endif