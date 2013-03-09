// MAudioTalk.h : CMAudioTalk ������

#pragma once
#include "resource.h"       // ������
#include <Windows.h>
#include "MATX_i.h"
#include "_IMAudioTalkEvents_CP.h"


#if defined(_WIN32_WCE) && !defined(_CE_DCOM) && !defined(_CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA)
#error "Windows CE ƽ̨(�粻�ṩ��ȫ DCOM ֧�ֵ� Windows Mobile ƽ̨)���޷���ȷ֧�ֵ��߳� COM ���󡣶��� _CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA ��ǿ�� ATL ֧�ִ������߳� COM ����ʵ�ֲ�����ʹ���䵥�߳� COM ����ʵ�֡�rgs �ļ��е��߳�ģ���ѱ�����Ϊ��Free����ԭ���Ǹ�ģ���Ƿ� DCOM Windows CE ƽ̨֧�ֵ�Ψһ�߳�ģ�͡�"
#endif



// CMAudioTalk
class CConnectMR;
class CAudioTalkMR;
class ATL_NO_VTABLE CMAudioTalk :
	public IObjectSafetyImpl<CMAudioTalk, INTERFACESAFE_FOR_UNTRUSTED_CALLER| INTERFACESAFE_FOR_UNTRUSTED_DATA>,
	public IProvideClassInfo2Impl<&CLSID_MAudioTalk, &IID_IMAudioTalk, &LIBID_MATXLib,1,0>,
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CMAudioTalk, &CLSID_MAudioTalk>,
	public IConnectionPointContainerImpl<CMAudioTalk>,
	public CProxy_IMAudioTalkEvents<CMAudioTalk>,
	public IObjectWithSiteImpl<CMAudioTalk>,
	public IDispatchImpl<IMAudioTalk, &IID_IMAudioTalk, &LIBID_MATXLib, /*wMajor =*/ 1, /*wMinor =*/ 0>
{
public:
	CMAudioTalk() : m_pcmr(NULL), m_patmr(NULL)
	{
	}

DECLARE_REGISTRY_RESOURCEID(IDR_MAUDIOTALK)


BEGIN_COM_MAP(CMAudioTalk)
	COM_INTERFACE_ENTRY(IMAudioTalk)
	COM_INTERFACE_ENTRY(IDispatch)
	COM_INTERFACE_ENTRY(IConnectionPointContainer)
	COM_INTERFACE_ENTRY(IObjectWithSite)
	COM_INTERFACE_ENTRY(IObjectSafety)
	COM_INTERFACE_ENTRY(IProvideClassInfo2)
	COM_INTERFACE_ENTRY(IProvideClassInfo)
END_COM_MAP()

BEGIN_CONNECTION_POINT_MAP(CMAudioTalk)
	CONNECTION_POINT_ENTRY(__uuidof(_IMAudioTalkEvents))
END_CONNECTION_POINT_MAP()


	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		return S_OK;
	}

	void FinalRelease()
	{
	}

public:

	STDMETHOD(MAudioTalk_Init)(BSTR ip, LONG type, BSTR name, LONG* ret);
	STDMETHOD(MAudioTalk_Uninit)(LONG* ret);
	STDMETHOD(MAudioTalk_Offline)(LONG* ret);
	STDMETHOD(MAudioTalk_Offover)(LONG* ret);

public:
	CConnectMR* m_pcmr; //
	CAudioTalkMR* m_patmr; //
};

OBJECT_ENTRY_AUTO(__uuidof(MAudioTalk), CMAudioTalk)
