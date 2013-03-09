// MAudioTalk.cpp : CMAudioTalk µÄÊµÏÖ

#include "stdafx.h"
#include <stdio.h>
#include <stdlib.h>
#include <winsock2.h>
#include "MAudioTalk.h"
#include "src/debug.h"
#include "src/ConnectMR.h"


// CMAudioTalk

STDMETHODIMP CMAudioTalk::MAudioTalk_Init(BSTR ip, LONG type, BSTR name, LONG* ret)
{
	*ret = 0;
	if (m_pcmr == NULL)
	{
		m_pcmr = new CConnectMR(this, &m_patmr);
	}
	if (m_pcmr == NULL)
	{
		TRACEMSG("new memory fault!\n");
		return S_FALSE;
	}

	m_pcmr->Init(CT2A(ip), type, CT2A(name));

	return S_OK;
}

STDMETHODIMP CMAudioTalk::MAudioTalk_Uninit(LONG* ret)
{
	if (m_pcmr != NULL)
	{
		m_pcmr->UninitATMR();
		m_pcmr->Uninit();
	}

	return S_OK;
}

STDMETHODIMP CMAudioTalk::MAudioTalk_Offline(LONG* ret)
{
	if (m_pcmr == NULL)
	{
		m_pcmr = new CConnectMR(this, &m_patmr);
	}
	if (m_pcmr == NULL)
	{
		TRACEMSG("new memory fault!\n");
		return S_FALSE;
	}

	m_pcmr->Offline();

	return S_OK;
}

STDMETHODIMP CMAudioTalk::MAudioTalk_Offover(LONG* ret)
{
	if (m_pcmr != NULL)
	{
		m_pcmr->Offover();
	}

	return S_OK;
}
