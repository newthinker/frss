

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 7.00.0500 */
/* at Thu May 03 00:54:34 2012
 */
/* Compiler settings for .\MATX.idl:
    Oicf, W1, Zp8, env=Win32 (32b run)
    protocol : dce , ms_ext, c_ext, robust
    error checks: stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
//@@MIDL_FILE_HEADING(  )

#pragma warning( disable: 4049 )  /* more than 64k source lines */


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__

#ifndef COM_NO_WINDOWS_H
#include "windows.h"
#include "ole2.h"
#endif /*COM_NO_WINDOWS_H*/

#ifndef __MATX_i_h__
#define __MATX_i_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef __IMAudioTalk_FWD_DEFINED__
#define __IMAudioTalk_FWD_DEFINED__
typedef interface IMAudioTalk IMAudioTalk;
#endif 	/* __IMAudioTalk_FWD_DEFINED__ */


#ifndef ___IMAudioTalkEvents_FWD_DEFINED__
#define ___IMAudioTalkEvents_FWD_DEFINED__
typedef interface _IMAudioTalkEvents _IMAudioTalkEvents;
#endif 	/* ___IMAudioTalkEvents_FWD_DEFINED__ */


#ifndef __MAudioTalk_FWD_DEFINED__
#define __MAudioTalk_FWD_DEFINED__

#ifdef __cplusplus
typedef class MAudioTalk MAudioTalk;
#else
typedef struct MAudioTalk MAudioTalk;
#endif /* __cplusplus */

#endif 	/* __MAudioTalk_FWD_DEFINED__ */


/* header files for imported files */
#include "oaidl.h"
#include "ocidl.h"

#ifdef __cplusplus
extern "C"{
#endif 


#ifndef __IMAudioTalk_INTERFACE_DEFINED__
#define __IMAudioTalk_INTERFACE_DEFINED__

/* interface IMAudioTalk */
/* [unique][helpstring][nonextensible][dual][uuid][object] */ 


EXTERN_C const IID IID_IMAudioTalk;

#if defined(__cplusplus) && !defined(CINTERFACE)
    
    MIDL_INTERFACE("0F89CA69-1430-49CA-83F7-7B4A26B6103E")
    IMAudioTalk : public IDispatch
    {
    public:
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE MAudioTalk_Init( 
            /* [in] */ BSTR ip,
            /* [in] */ LONG type,
            /* [in] */ BSTR name,
            /* [retval][out] */ LONG *ret) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE MAudioTalk_Uninit( 
            /* [retval][out] */ LONG *ret) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE MAudioTalk_Offline( 
            /* [retval][out] */ LONG *ret) = 0;
        
        virtual /* [helpstring][id] */ HRESULT STDMETHODCALLTYPE MAudioTalk_Offover( 
            /* [retval][out] */ LONG *out) = 0;
        
    };
    
#else 	/* C style interface */

    typedef struct IMAudioTalkVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            IMAudioTalk * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            IMAudioTalk * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            IMAudioTalk * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            IMAudioTalk * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            IMAudioTalk * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            IMAudioTalk * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            IMAudioTalk * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *MAudioTalk_Init )( 
            IMAudioTalk * This,
            /* [in] */ BSTR ip,
            /* [in] */ LONG type,
            /* [in] */ BSTR name,
            /* [retval][out] */ LONG *ret);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *MAudioTalk_Uninit )( 
            IMAudioTalk * This,
            /* [retval][out] */ LONG *ret);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *MAudioTalk_Offline )( 
            IMAudioTalk * This,
            /* [retval][out] */ LONG *ret);
        
        /* [helpstring][id] */ HRESULT ( STDMETHODCALLTYPE *MAudioTalk_Offover )( 
            IMAudioTalk * This,
            /* [retval][out] */ LONG *out);
        
        END_INTERFACE
    } IMAudioTalkVtbl;

    interface IMAudioTalk
    {
        CONST_VTBL struct IMAudioTalkVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define IMAudioTalk_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define IMAudioTalk_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define IMAudioTalk_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define IMAudioTalk_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define IMAudioTalk_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define IMAudioTalk_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define IMAudioTalk_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 


#define IMAudioTalk_MAudioTalk_Init(This,ip,type,name,ret)	\
    ( (This)->lpVtbl -> MAudioTalk_Init(This,ip,type,name,ret) ) 

#define IMAudioTalk_MAudioTalk_Uninit(This,ret)	\
    ( (This)->lpVtbl -> MAudioTalk_Uninit(This,ret) ) 

#define IMAudioTalk_MAudioTalk_Offline(This,ret)	\
    ( (This)->lpVtbl -> MAudioTalk_Offline(This,ret) ) 

#define IMAudioTalk_MAudioTalk_Offover(This,out)	\
    ( (This)->lpVtbl -> MAudioTalk_Offover(This,out) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */




#endif 	/* __IMAudioTalk_INTERFACE_DEFINED__ */



#ifndef __MATXLib_LIBRARY_DEFINED__
#define __MATXLib_LIBRARY_DEFINED__

/* library MATXLib */
/* [helpstring][version][uuid] */ 


EXTERN_C const IID LIBID_MATXLib;

#ifndef ___IMAudioTalkEvents_DISPINTERFACE_DEFINED__
#define ___IMAudioTalkEvents_DISPINTERFACE_DEFINED__

/* dispinterface _IMAudioTalkEvents */
/* [helpstring][uuid] */ 


EXTERN_C const IID DIID__IMAudioTalkEvents;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("7EE43ED0-4E45-4F08-93B5-B51F6AC167F5")
    _IMAudioTalkEvents : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _IMAudioTalkEventsVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _IMAudioTalkEvents * This,
            /* [in] */ REFIID riid,
            /* [iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _IMAudioTalkEvents * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _IMAudioTalkEvents * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _IMAudioTalkEvents * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _IMAudioTalkEvents * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _IMAudioTalkEvents * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _IMAudioTalkEvents * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        END_INTERFACE
    } _IMAudioTalkEventsVtbl;

    interface _IMAudioTalkEvents
    {
        CONST_VTBL struct _IMAudioTalkEventsVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _IMAudioTalkEvents_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define _IMAudioTalkEvents_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define _IMAudioTalkEvents_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define _IMAudioTalkEvents_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define _IMAudioTalkEvents_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define _IMAudioTalkEvents_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define _IMAudioTalkEvents_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___IMAudioTalkEvents_DISPINTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_MAudioTalk;

#ifdef __cplusplus

class DECLSPEC_UUID("8853B3B8-D35F-4131-9967-B41D3214B13D")
MAudioTalk;
#endif
#endif /* __MATXLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

unsigned long             __RPC_USER  BSTR_UserSize(     unsigned long *, unsigned long            , BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserMarshal(  unsigned long *, unsigned char *, BSTR * ); 
unsigned char * __RPC_USER  BSTR_UserUnmarshal(unsigned long *, unsigned char *, BSTR * ); 
void                      __RPC_USER  BSTR_UserFree(     unsigned long *, BSTR * ); 

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


