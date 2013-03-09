#include "stdafx.h"
#include "CWinJrtpThread.h"
#include <time.h>
#include <jthread/jmutexautolock.h>

#ifndef _WIN32_WCE
	#include <iostream>
#endif // _WIN32_WCE

CWinJrtpThread::CWinJrtpThread()
{
	m_iRunning=40;
	m_hEvent=NULL;
	if (!m_runningmutex.IsInitialized())
	{
		if (m_runningmutex.Init() < 0)
			return ;
	}
}

CWinJrtpThread::~CWinJrtpThread()
{
	Stop();
	if(NULL!=m_hEvent)
	{
		CloseHandle(m_hEvent);
		m_hEvent=NULL;
	}
}

int CWinJrtpThread::SetParams(void * pParentThis,
							  ThreadCallBack * pThreadCallBack,
							  bool bThreadMessage)
{
	m_pParentThis=pParentThis;
	m_pThreadCallBack=pThreadCallBack;
	m_bThreadMessage=bThreadMessage;
	return 1;
}

bool CWinJrtpThread::IsRunning()
{
	JMutexAutoLock lock(m_runningmutex);
	if (40!=m_iRunning)
		return true;
	else
		return false;
}

int CWinJrtpThread::Kill()
{
	{
		JMutexAutoLock lock(m_runningmutex);
		if (40==m_iRunning)
			return -1;
	}

	TerminateThread(m_threadhandle,0);
	CloseHandle(m_threadhandle);
	m_iRunning = 40;
	return 1;
}

int CWinJrtpThread::Start()
{
	{
		JMutexAutoLock lock(m_runningmutex);
		if (1==m_iRunning)
			return -1;
		else if (2==m_iRunning)
		{
			m_iRunning=1;
			return -3;
		}
		m_iRunning=1;
	}

	m_hEvent=CreateEvent(NULL,TRUE,FALSE,NULL);
	m_threadhandle = (HANDLE)_beginthreadex(NULL,0,TheThread,this,0,&m_iThreadId);
	if (m_threadhandle == NULL)
	{
		return -10;
	}

	WaitForSingleObject(m_hEvent,INFINITE);

	return m_iThreadId;
}

int CWinJrtpThread::Stop()
{
	{
		JMutexAutoLock lock(m_runningmutex);
		if (40==m_iRunning)
			return -1;
	}
	
	if(m_bThreadMessage)
	{
		PostThreadMessage(m_iThreadId,_WIIN_THREAD_QUIT,NULL,NULL);
	}

	{
		JMutexAutoLock lock(m_runningmutex);
		m_iRunning=0;
		m_pThreadCallBack=NULL;
		m_pParentThis=NULL;
	}
	
	//SYSTEMTIME thetime = {0};
	//GetLocalTime(&thetime);//获得当前本地时间
	DWORD thetime =GetTickCount();

	bool done = false;
	while ( IsRunning() && !done)
	{
		// wait max 5 sec
		//SYSTEMTIME curtime = {0};
		//GetLocalTime(&curtime);//获得当前本地时间
		DWORD curtime = GetTickCount();

		//if ((curtime.wSecond-thetime.wSecond) > 5.0)
		//	done = true;
		if ((curtime-thetime) > 5000)
			done = true;
		Sleep(100);
	}

	if ( IsRunning() )
	{
		Kill();
	}

	return 1;
}

int CWinJrtpThread::Pause()
{
	JMutexAutoLock lock(m_runningmutex);
	if (40==m_iRunning)
		return -1;
	m_iRunning=2;

	return 1;
}

UINT __stdcall CWinJrtpThread::TheThread(void *param)
{
	CWinJrtpThread * pCWinJrtpThread = (CWinJrtpThread *)param;
	if(pCWinJrtpThread->m_bThreadMessage)
	{
		MSG msg;
		PeekMessage(&msg, NULL, WM_USER, WM_USER, PM_NOREMOVE);
		::SetEvent(pCWinJrtpThread->m_hEvent);

		if(NULL!=pCWinJrtpThread->m_pParentThis)
		(*pCWinJrtpThread->m_pThreadCallBack)(pCWinJrtpThread->m_pParentThis);
	}
	else
	{
		::SetEvent(pCWinJrtpThread->m_hEvent);
		while (true)
		{
			bool bRunning=false;
			{
				JMutexAutoLock lock(pCWinJrtpThread->m_runningmutex);
				if(pCWinJrtpThread->m_iRunning==0)
				{
					pCWinJrtpThread->m_iRunning=40;
					break;
				}
				else if(pCWinJrtpThread->m_iRunning==2)
				{
					Sleep(1);
				}
				else
				{
					bRunning=true;
				}	
			}

			if(bRunning)
			{
				if( NULL!=pCWinJrtpThread->m_pParentThis &&
						NULL!=pCWinJrtpThread->m_pThreadCallBack )
					{
						(*pCWinJrtpThread->m_pThreadCallBack)(pCWinJrtpThread->m_pParentThis);
					}
			}
			else
			{
				Sleep(1);
			}
		}
	}

	{
		JMutexAutoLock lock(pCWinJrtpThread->m_runningmutex);
		pCWinJrtpThread->m_iRunning=40;
	}
	
	return 0;
}