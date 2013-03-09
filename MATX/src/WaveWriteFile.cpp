#include "StdAfx.h"
#include "WaveWriteFile.h"

CWaveWriteFile::CWaveWriteFile(void)
{
	m_pWaveFile = NULL;
}

bool CWaveWriteFile::Init(WAVEFORMATEX* cmwf, LPTSTR path, LPTSTR file)
{
	SAFE_DELETE( m_pWaveFile );
	m_pWaveFile = new CWaveFile;
	if( NULL == m_pWaveFile )
	{
		return false;
	}

	memset(m_filePath, 0, sizeof(m_filePath));
	if (file == NULL)
	{
		SYSTEMTIME sys;
		GetLocalTime( &sys );

		_stprintf(m_filePath, _T("%s\\%4d-%02d-%02d-%02d-%02d-%02d-%03d.wav"), path, 
			sys.wYear,sys.wMonth,sys.wDay,sys.wHour,sys.wMinute, sys.wSecond,sys.wMilliseconds);
	}
	else
	{
		SYSTEMTIME sys;
		GetLocalTime( &sys );

		_stprintf(m_filePath, _T("%s\\%4d-%02d-%02d-%02d-%02d-%02d-%03d-%s.wav"), path, 
			sys.wYear,sys.wMonth,sys.wDay,sys.wHour,sys.wMinute, sys.wSecond,sys.wMilliseconds, file);
	}

	// Load the wave file
	if( FAILED( m_pWaveFile->Open( m_filePath, cmwf, WAVEFILE_WRITE ) ) )
	{
		return false;
	}
	return true;
}
bool CWaveWriteFile::Write(BYTE* buf, int len)
{
	UINT dwDataWrote;
	if( FAILED( m_pWaveFile->Write( len, buf, &dwDataWrote ) ) )
	{
		return false;
	}
	return true;
}
void CWaveWriteFile::EndWrite()
{
	SAFE_DELETE( m_pWaveFile );
}
CWaveWriteFile::~CWaveWriteFile(void)
{
	if (m_pWaveFile)
	{
		EndWrite();
	}
}

BSTR CWaveWriteFile::GetPath()
{
	return BSTR(m_filePath);
}
