#pragma once
#include "WaveFile.h"

class CWaveWriteFile
{
public:
	CWaveWriteFile(void);
	~CWaveWriteFile(void);

public:
	bool Init(WAVEFORMATEX* cmwf, LPTSTR path, LPTSTR file);
	bool Write(BYTE* buf, int len);
	void EndWrite();
	BSTR GetPath();

public:
	TCHAR m_filePath[512];
	CWaveFile* m_pWaveFile;
};
