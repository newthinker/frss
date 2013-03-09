// file debug.h
#ifndef __DEBUG_H__
#define __DEBUG_H__

#ifdef _DEBUG

void _trace(char *fmt, ...);
void _tracemsg(char *fmt, ...);

#define ASSERT(x) {if(!(x)) _asm{int 0x03}}
#define VERIFY(x) {if(!(x)) _asm{int 0x03}}  // 译注：为调试版本时产生中断有效

#else
#define ASSERT(x)
#define VERIFY(x) x                  // 译注：为发行版本时不产生中断
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

