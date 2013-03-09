// file debug.h
#ifndef __DEBUG_H__
#define __DEBUG_H__

#ifdef _DEBUG

void _trace(char *fmt, ...);
void _tracemsg(char *fmt, ...);

#define ASSERT(x) {if(!(x)) _asm{int 0x03}}
#define VERIFY(x) {if(!(x)) _asm{int 0x03}}  // ��ע��Ϊ���԰汾ʱ�����ж���Ч

#else
#define ASSERT(x)
#define VERIFY(x) x                  // ��ע��Ϊ���а汾ʱ�������ж�
#endif

#ifdef _DEBUG
#define TRACE _trace
#define TRACEMSG _tracemsg

#else
inline void _trace(char *fmt, ...) { }
#define TRACE  1 ? (void)0 : _trace
inline void _tracemsg(char *fmt, ...) { }
#define TRACEMSG  1 ? (void)0 : _tracemsg
#endif

#endif // __DEBUG_H__

