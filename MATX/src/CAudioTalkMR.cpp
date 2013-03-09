#include "stdafx.h"
#include <Shlobj.h>
#include "HCNetSDK.h"
#include "debug.h"
#include "CAudioTalkMR.h"
#include "ConnectMR.h"
#include "../MAudioTalk.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif


void CreateFolder(LPTSTR szPath)
{
	if(SUCCEEDED(SHGetFolderPath(NULL, 
		CSIDL_PERSONAL|CSIDL_FLAG_CREATE, 
		NULL, 
		0, 
		szPath))) 
	{
		PathAppend(szPath, _T("\\MATX"));
		CreateDirectory(szPath, NULL);
		PathAppend(szPath, _T("\\wav"));
		CreateDirectory(szPath, NULL);
	}
}

CAudioTalkMR::CAudioTalkMR(CMAudioTalk* pcmt) : _pcmt(pcmt)
{
	NET_DVR_Init();
	m_bWaveDeal = FALSE;
	m_bOnLine = TRUE;
	
	m_hExitEvent = NULL;
	m_hHaveRDataRV = NULL;
	
	m_pDecoder = NULL;
	m_pEncoder = NULL;

	m_bOpenWavOut = FALSE;
	m_bOpenWavIn = FALSE;
	m_bOpenWavOutFile = FALSE;
	m_bOpenWavInFile = FALSE;

	m_userlevel = 0;
	CreateFolder(m_wavPath);

	m_dwBufSize = AUDENCSIZE;
	m_dwBufNum = 6;
	//init WAVEFORMATEX
	m_struWaveFormat.cbSize =			sizeof(m_struWaveFormat);
	m_struWaveFormat.nBlockAlign =		CHANNEL * BITS_PER_SAMPLE / 8;
	m_struWaveFormat.nChannels =		CHANNEL;
	m_struWaveFormat.nSamplesPerSec =	SAMPLES_PER_SECOND;
	m_struWaveFormat.wBitsPerSample =	BITS_PER_SAMPLE;
	m_struWaveFormat.nAvgBytesPerSec =	SAMPLES_PER_SECOND * m_struWaveFormat.nBlockAlign;
	m_struWaveFormat.wFormatTag =		WAVE_FORMAT_PCM;

	m_pRenderBuf = NULL;
	m_nBufNo = 0;
	m_rIndexRV = 0;
	m_ReceiveIndexRV = 0;
	
	m_bOpenPlayThread = FALSE;
}

CAudioTalkMR::~CAudioTalkMR()
{
	Destory();
	NET_DVR_Cleanup();
}

int CAudioTalkMR::Initialize(	char * pRemoteIpaddr,
								unsigned short sRemotePort,
								unsigned short sLocalPort,
								int level)
{
	m_userlevel = level;

	int ret = CreateTalk();
	if (ret != 0)
	{
		return ret;
	}

	m_cRtpBufferRecevie.Init(this);
	ret = m_cRtpBufferRecevie.CreateSession(pRemoteIpaddr,sRemotePort,1,NULL,sLocalPort);
	if (ret != 0)
	{
		return ret;
	}

	return ret;
}

void CAudioTalkMR::UnInitialize()
{
	Destory();
}

int CAudioTalkMR::InitOffline()
{
	if (m_bWaveDeal)
	{
		return 1000; //created already
	}

	DWORD dwRet=-1;
	//open wavin
	m_SoundIn.SetSoundInDataCB(DataFromSoundIn, (DWORD)this);
	if (m_SoundIn.Start(&m_struWaveFormat, m_dwBufNum, m_dwBufSize))
	{
		m_bOpenWavIn = TRUE;
	}
	else
	{
		dwRet = 1003;
	}

	//open file
	if (m_SoundInFile.Init(&m_struWaveFormat, m_wavPath, _T("Offline")))
	{
		m_bOpenWavInFile = TRUE;
	}
	else
	{
		dwRet = 1004;
	}

	m_bOnLine = FALSE;
	m_bWaveDeal = TRUE;

	return dwRet;
}

void CAudioTalkMR::UnInitOffline()
{
	if (m_bOpenWavIn)
	{
		m_SoundIn.Stop();
		m_bOpenWavIn = FALSE;
	}

	if (m_bOpenWavInFile)
	{
		m_SoundInFile.EndWrite();
		m_bOpenWavInFile = FALSE;
	}

	PutString(m_wavPath);
}

void CAudioTalkMR::PutState(long* pstate)
{
	_pcmt->Fire_GetState(pstate);
}

void CAudioTalkMR::PutString(BSTR str)
{
	_pcmt->Fire_GetString(str);
}

BOOL CAudioTalkMR::Destory()
{
	m_cRtpBufferRecevie.DestorySession();
	DestoryTalk();

	return TRUE;
}

int CAudioTalkMR::CreateTalk()
{
	if (m_bWaveDeal)
	{
		return 1000; //created already
	}

	DWORD dwRet=-1;
	if( NULL==m_hExitEvent )
		m_hExitEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
	if( NULL==m_hHaveRDataRV )
		m_hHaveRDataRV = CreateEvent(NULL, TRUE, FALSE, NULL);
	
	if( NULL==m_pRenderBuf )
	{
		//audio buffer
		m_pRenderBuf = (PBYTE)::VirtualAlloc(NULL, 160*40L, MEM_RESERVE|MEM_COMMIT, PAGE_READWRITE);
	}
	if( NULL==m_pDecoder )
	{
		//decoder
		m_pDecoder = NET_DVR_InitG722Decoder(BIT_RATE_16000);
		dwRet=NET_DVR_GetLastError();
	}
	if( NULL==m_pEncoder )
	{
		//encoder
		m_pEncoder = NET_DVR_InitG722Encoder();
		dwRet=NET_DVR_GetLastError();
	}

	//open wavout
	if (m_SoundOut.OpenSound(m_struWaveFormat, m_dwBufNum, m_dwBufSize, CALLBACK_FUNCTION, 0))
	{	
		if (m_SoundOut.PlaySound())
		{
			m_bOpenWavOut = TRUE;
			//m_SoundOut.SetVolume(0x7fff7fff);
		}
		else
		{
			dwRet = 1001;
		}
	}
	else
	{
		dwRet = 1002;
	}
	
	//open wavin
	m_SoundIn.SetSoundInDataCB(DataFromSoundIn, (DWORD)this);
	if (m_SoundIn.Start(&m_struWaveFormat, m_dwBufNum, m_dwBufSize))
	{
		m_bOpenWavIn = TRUE;
	}
	else
	{
		dwRet = 1003;
	}

	if (m_userlevel == IDENTITY_LISTENER)
	{
		//open file
		if (m_SoundInFile.Init(&m_struWaveFormat, m_wavPath, _T("listener")))
		{
			m_bOpenWavInFile = TRUE;
		}
		else
		{
			dwRet = 1004;
		}
		//open file
		if (m_SoundOutFile.Init(&m_struWaveFormat, m_wavPath, _T("tuan")))
		{
			m_bOpenWavOutFile = TRUE;
		}
		else
		{
			dwRet = 1004;
		}
	}
	else if (m_userlevel == IDENTITY_DISPATCHER)
	{
		//open file
		if (m_SoundInFile.Init(&m_struWaveFormat, m_wavPath, _T("dispatcher")))
		{
			m_bOpenWavInFile = TRUE;
		}
		else
		{
			dwRet = 1004;
		}
		//open file
		if (m_SoundOutFile.Init(&m_struWaveFormat, m_wavPath, _T("expert")))
		{
			m_bOpenWavOutFile = TRUE;
		}
		else
		{
			dwRet = 1004;
		}
	}

	m_bOnLine = TRUE;
	m_bWaveDeal = TRUE;

	return dwRet;
}

void CAudioTalkMR::DestoryTalk() 
{
	if (m_hExitEvent)
	{
		SetEvent(m_hExitEvent);
	} 

	if (m_bOpenWavOut)
	{
		m_SoundOut.CloseSound();
		m_bOpenWavOut = FALSE;
	}
	
	if (m_bOpenWavIn)
	{
		m_SoundIn.Stop();
		m_bOpenWavIn = FALSE;
	}

	if (m_bOpenWavInFile)
	{
		m_SoundInFile.EndWrite();
		m_bOpenWavInFile = FALSE;
	}

	if (m_bOpenWavOutFile)
	{
		m_SoundOutFile.EndWrite();
		m_bOpenWavOutFile = FALSE;
	}

	if (m_pDecoder)
	{
		NET_DVR_ReleaseG722Decoder(m_pDecoder);
		m_pDecoder = NULL;
	}
	
	if (m_pEncoder)
	{
		NET_DVR_ReleaseG722Encoder(m_pEncoder);
		m_pEncoder = NULL;
	}

	if (m_hExitEvent)
	{
		CloseHandle(m_hExitEvent);	
		m_hExitEvent = NULL;
	}

	if (m_hHaveRDataRV)
	{
		CloseHandle(m_hHaveRDataRV);	
		m_hHaveRDataRV = NULL;
	}
	
	if (m_pRenderBuf)
	{
		::VirtualFree(m_pRenderBuf, 0, MEM_RELEASE);
		m_pRenderBuf = NULL;
	}

	PutString(m_wavPath);
}

void CALLBACK CAudioTalkMR::DataFromSoundIn(char* buffer, DWORD dwSize, DWORD dwOwner)
{
	ASSERT(dwOwner);
	CAudioTalkMR * p = (CAudioTalkMR *) dwOwner;
	p->SendDataToDevice(buffer,dwSize);
}

void CAudioTalkMR::ReceiveCallback(char *buf, int dwSize)
{
	TRACE("recv buf...\n");
	PutIntoBuf(buf,dwSize);
}

BOOL CAudioTalkMR::SendDataToDevice(char *buf, DWORD dwSize)
{
	if (!m_bWaveDeal)
	{
		return FALSE;
	}

	if (m_bOpenWavInFile)
	{
		m_SoundInFile.Write((BYTE*)buf, dwSize);

		//long state = 99;
		//PutState(&state);
	}

	int i = 0, j=0;
	char *pBuf = buf;
	for (i=0; i<(int)dwSize/2; i++)
	{
		*((short *)pBuf) = (short)(*((short *)pBuf) / 2);
		pBuf += sizeof(short);
	}

	if (m_bOnLine)
	{
		//encode 
		if (!NET_DVR_EncodeG722Frame(m_pEncoder, (BYTE*)buf, m_byEncBuf))
		{
			TRACEMSG("NET_DVR_EncodeG722Frame failed\n");
			return FALSE;
		}

		/////send audio encode buffer
		m_cRtpBufferRecevie.SendBuffer(m_byEncBuf,80);
	}
	
	return TRUE;
}

BOOL CAudioTalkMR::PutIntoBuf(char *lpTemp, int Bytes)
{
	int nTemp = 0;
	int nPacketStart = 0;
	if ((m_ReceiveIndexRV + Bytes) <= AUDIOBUF)
	{
		if (!m_bOpenPlayThread)
		{
			memcpy(m_pRenderBuf+m_ReceiveIndexRV, lpTemp, Bytes);
			m_ReceiveIndexRV += Bytes;
			m_ReceiveIndexRV = m_ReceiveIndexRV % AUDIOBUF;
		}
		else
		{
			if ( ((m_ReceiveIndexRV + Bytes) >= m_rIndexRV) && 
				 (m_ReceiveIndexRV < m_rIndexRV) )
			{   //buffer1 overflow
				TRACE("buffer1 overflow.\n");
				::SetEvent(m_hHaveRDataRV);
				nPacketStart = (m_rIndexRV - AUDDECSIZE + m_ReceiveIndexRV % AUDDECSIZE);
				if ((nPacketStart + Bytes) <= (DWORD)AUDIOBUF)
				{
					memcpy(m_pRenderBuf + nPacketStart, lpTemp, Bytes);
					m_ReceiveIndexRV = nPacketStart + Bytes;
				}
				else
				{
					nTemp = AUDIOBUF - nPacketStart;
					memcpy(m_pRenderBuf + nPacketStart, lpTemp, nTemp);
					memcpy(m_pRenderBuf, lpTemp + nTemp, Bytes - nTemp);
					m_ReceiveIndexRV = Bytes - nTemp;
				}
			}
			else	
			{
				memcpy(m_pRenderBuf + m_ReceiveIndexRV, lpTemp, Bytes);
				m_ReceiveIndexRV += Bytes;
				m_ReceiveIndexRV = m_ReceiveIndexRV % AUDIOBUF;
			}
		}
	}
	else
	{
		if (m_bOpenPlayThread)
		{
			if ((Bytes >= (m_rIndexRV + AUDIOBUF - m_ReceiveIndexRV))
				|| (m_rIndexRV >= m_ReceiveIndexRV))
			{	//buffer2 overflow
				TRACE("buffer2 overflow.\n");
				::SetEvent(m_hHaveRDataRV);
				if (m_rIndexRV != 0)
				{
					nPacketStart = (m_rIndexRV - AUDDECSIZE + m_ReceiveIndexRV % AUDDECSIZE);
				}
				else
				{
					nPacketStart = (m_rIndexRV + AUDIOBUF - AUDDECSIZE + m_ReceiveIndexRV % AUDDECSIZE);
				}
				if ((nPacketStart + Bytes) <= (DWORD)AUDIOBUF)
				{
					memcpy(m_pRenderBuf + nPacketStart, lpTemp, Bytes);
					m_ReceiveIndexRV = nPacketStart + Bytes;
				}
				else
				{
					nTemp = AUDIOBUF - nPacketStart;
					memcpy(m_pRenderBuf + nPacketStart, lpTemp, nTemp);
					memcpy(m_pRenderBuf, lpTemp + nTemp, Bytes - nTemp);
					m_ReceiveIndexRV = Bytes - nTemp;
				}
			}
			else
			{
				memcpy(m_pRenderBuf + m_ReceiveIndexRV, lpTemp, nTemp = AUDIOBUF - m_ReceiveIndexRV);
				memcpy(m_pRenderBuf, lpTemp + nTemp, Bytes - nTemp);
				m_ReceiveIndexRV = Bytes - nTemp;
			}
		}
	}

	if ((((m_ReceiveIndexRV + AUDIOBUF - m_rIndexRV) % (AUDIOBUF)) >= (m_nBufNo * AUDDECSIZE)))
	{
		::SetEvent(m_hHaveRDataRV);
		m_nBufNo = 1;
		if (!m_bOpenPlayThread)
		{
#ifndef _WIN32_WCE
			_beginthreadex(NULL,0,PlayAudioThread,(LPVOID)this,0,0);
#else
			CreateThread(NULL,0,PlayAudioThread,this,0,0);
#endif // _WIN32_WCE
			m_bOpenPlayThread = TRUE;
		}
	}

	return TRUE;
}

/*********************************************************
  Function:	CopyAudioData
  Desc:		deal the audio data from device/从接收缓冲区中拷贝数据出来解码
  Input:	lpTemp, data buffer; dwReadLength, data length;
  Output:	none
  Return:	TRUE/FALSE
**********************************************************/
BOOL CAudioTalkMR::CopyAudioData(PBYTE lpTemp, DWORD dwReadLength)
{
	DWORD dwTemp;
	int  dwPacketNumber = 0;
	int itemp = 0;
	__try
	{
		if (m_rIndexRV == m_ReceiveIndexRV)
		{
			dwPacketNumber = 0;
			m_nBufNo = 6;
			::ResetEvent(m_hHaveRDataRV);
			itemp = 0;
			return FALSE;
		}
		else if (m_rIndexRV < m_ReceiveIndexRV)    //read < write
		{
			if (dwReadLength > (DWORD)(m_ReceiveIndexRV-m_rIndexRV))
			{
				dwPacketNumber = 0;
				m_nBufNo = 6;
				::ResetEvent(m_hHaveRDataRV);
				itemp = 0;
				return FALSE;
			}
			else
			{
				CopyMemory(lpTemp, m_pRenderBuf + m_rIndexRV, dwReadLength);
				m_rIndexRV += dwReadLength;
				dwPacketNumber = (m_ReceiveIndexRV - m_rIndexRV) / dwReadLength;
				itemp = 1;
			}
		}
		else	//read > write
		{
			if (dwReadLength > (DWORD)(AUDIOBUF - m_rIndexRV))
			{
				dwTemp = AUDIOBUF - m_rIndexRV;
				if ((dwReadLength-dwTemp) < (DWORD)(m_ReceiveIndexRV + 1))
				{
					CopyMemory(lpTemp, m_pRenderBuf + m_rIndexRV, dwTemp);
					CopyMemory(lpTemp + dwTemp, m_pRenderBuf, dwReadLength - dwTemp);
					m_rIndexRV = dwReadLength - dwTemp;
					dwPacketNumber = (m_ReceiveIndexRV-m_rIndexRV) / dwReadLength;
					itemp = 2;
				}
				else
				{
					dwPacketNumber = 0;
					::ResetEvent(m_hHaveRDataRV);
					m_nBufNo = 6;
					itemp = 3;
					return FALSE;
				}
			}
			else
			{
				itemp = 4;
				CopyMemory(lpTemp, m_pRenderBuf + m_rIndexRV, dwReadLength);
				m_rIndexRV += dwReadLength;
				dwPacketNumber = (AUDIOBUF + m_ReceiveIndexRV - m_rIndexRV) / dwReadLength;
			}
		}
	}
	__except(EXCEPTION_EXECUTE_HANDLER)
	{
		return FALSE;
	}
	return TRUE;
}

/*********************************************************
  Function:	InputAudioData
  Desc:		input the device audio data to decoder and decode/取出音频数据并解码
  Input:	none
  Output:	none
  Return:	none
**********************************************************/
void CAudioTalkMR::InputAudioData()
{
	HANDLE hWaitEvents[2];
	hWaitEvents[0] = m_hExitEvent;
	hWaitEvents[1] = m_hHaveRDataRV;

	DWORD dwReadLength = AUDDECSIZE;
	BYTE  lpTemp[AUDDECSIZE * 2] = {0};
	DWORD dwWaitResult = 0;
	int i = 0;

	try
	{
		while (1)
		{
			dwWaitResult = WaitForMultipleObjects(2, hWaitEvents, FALSE, INFINITE);
			if (WAIT_OBJECT_0 == dwWaitResult) // m_hExitEvent event
			{
				m_bOpenPlayThread = FALSE;
				ResetEvent(m_hExitEvent);
				return ;
			}

			// m_hHaveRDataRV event
			if (CopyAudioData(lpTemp, dwReadLength))
			{
				NET_DVR_DecodeG722Frame(m_pDecoder, (BYTE*)lpTemp, m_byDecBuf);

				if (m_bOnLine && m_bOpenWavOutFile)
				{
					m_SoundOutFile.Write(m_byDecBuf, AUDENCSIZE);

					//long state = 99;
					//PutState(&state);
				}

				for (i=0; i<35; i++)
				{
					if (m_SoundOut.InputData((BYTE*)m_byDecBuf, 0))
					{
						break;
					}
					Sleep(1);
				}
			}
		}
	
	}
	catch (...)
	{
		TRACEMSG("InputAudioData exception\n");
		m_bOpenPlayThread = FALSE;
		return ;
	}

	m_bOpenPlayThread = FALSE;
	return ;
}

/*********************************************************
  Function:	PlayAudioThread
  Desc:		play the audio data from device
  Input:	pParam, point to current dialog
  Output:	none
  Return:	0
  **********************************************************/
#ifndef _WIN32_WCE
UINT __stdcall CAudioTalkMR::PlayAudioThread(void *pParam)
#else
DWORD WINAPI CAudioTalkMR::PlayAudioThread(void *pParam)
#endif // _WIN32_WCE
{
	ASSERT(pParam);
	CAudioTalkMR * pClient = (CAudioTalkMR *)pParam;
	try
	{
		pClient->InputAudioData();
	}
	catch (...)
	{
		TRACEMSG("Input data exception\n");
		return 1;
	}
	
	return 0;
}

