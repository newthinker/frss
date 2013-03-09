// dllmain.h : 模块类的声明。

class CMATXModule : public CAtlDllModuleT< CMATXModule >
{
public :
	DECLARE_LIBID(LIBID_MATXLib)
	DECLARE_REGISTRY_APPID_RESOURCEID(IDR_MATX, "{F6C40B73-F4A3-42F6-BCF5-C57D837B482D}")
};

extern class CMATXModule _AtlModule;
