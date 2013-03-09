#ifndef CWINTHREAD_HEADER
#define CWINTHREAD_HEADER

#include <jthread/jmutex.h>
using namespace jthread;

#ifdef _WIN32_WCE
	typedef	 DWORD (WINAPI ThreadCallBack)(void *param);
#else
	typedef  UINT (__stdcall ThreadCallBack)(void *param);
#endif // _WIN32_WCE

#define _WIIN_THREAD_QUIT 1002

class CWinJrtpThread
{
	public:
		CWinJrtpThread();
		~CWinJrtpThread();
		int Start();
		int Stop();
		int Pause();
		int SetParams(void * pParentThis,
					  ThreadCallBack * pThreadCallBack,
					  bool bThreadMessage=false);
	private:
		static UINT __stdcall TheThread(void *param);
		
		HANDLE m_threadhandle;
		unsigned int m_iThreadId;
		int m_iRunning;
		JMutex m_runningmutex;
		HANDLE m_hEvent;

		ThreadCallBack * m_pThreadCallBack;
		void * m_pParentThis;
		bool m_bThreadMessage;
	private:
		bool IsRunning();
		int Kill();
};
#endif // CWINTHREAD_HEADER
