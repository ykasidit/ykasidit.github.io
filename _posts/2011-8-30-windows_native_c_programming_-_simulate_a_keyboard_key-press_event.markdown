---
title: Windows native C programming - Simulate a keyboard key-press event
category: programming
---

Something like this example code can be used to generate key-press events on Windows. 

<pre>
#include <windows.h>
#include <Winuser.h>
#include <Wtsapi32.h>

.
.
.

enum {
KModMaskCtrl,
KModMaskAlt,
KModMaskShift
};

.
.
.

void SimulateKey(int KeyCode, int mod)
{

		INPUT  Input={0};



		if(mod & KModMaskCtrl)
		{
			ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_CONTROL;
		  Input.ki.dwFlags = KEYEVENTF_UNICODE;
		  SendInput(1,&Input,sizeof(INPUT));		  
		  Sleep(20);
		}		

		if(mod & KModMaskAlt)
		{
		  ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_MENU;
		  Input.ki.dwFlags = KEYEVENTF_UNICODE;
		  SendInput(1,&Input,sizeof(INPUT));
		  Sleep(20);
		}

		if(mod & KModMaskShift)
		{
		  ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_SHIFT;
		  Input.ki.dwFlags = KEYEVENTF_UNICODE;
		  SendInput(1,&Input,sizeof(INPUT));
		  Sleep(20);
		}

	  ZeroMemory(&Input,sizeof(INPUT));
	  Input.type      = INPUT_KEYBOARD;	  
	  Input.ki.wVk = KeyCode;	  
	  Input.ki.dwFlags = KEYEVENTF_UNICODE;	  
	  SendInput(1,&Input,sizeof(INPUT));

	  Sleep(20);
	  ZeroMemory(&Input,sizeof(INPUT));
	  Input.type      = INPUT_KEYBOARD;	  
	  Input.ki.wVk = KeyCode;
	  Input.ki.dwFlags = KEYEVENTF_KEYUP;	  
	  SendInput(1,&Input,sizeof(INPUT)); 



		if(mod & KModMaskShift)
		{
			Sleep(20);
		  ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_SHIFT;
		  Input.ki.dwFlags = KEYEVENTF_KEYUP;
		  SendInput(1,&Input,sizeof(INPUT));

		}



		if(mod & KModMaskAlt)
		{
			Sleep(20);
		  ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_MENU;
		  Input.ki.dwFlags = KEYEVENTF_KEYUP;
		  SendInput(1,&Input,sizeof(INPUT));

		}

		if(mod & KModMaskCtrl)
		{
			Sleep(20);
			ZeroMemory(&Input,sizeof(INPUT));
		  Input.type      = INPUT_KEYBOARD;	  
		  Input.ki.wVk = VK_CONTROL;
		  Input.ki.dwFlags = KEYEVENTF_KEYUP;
		  SendInput(1,&Input,sizeof(INPUT));		  

		}	
}

</pre>

See winuser.h for a list of virtual_key codes you can use, here are some codes

<pre>
#define VK_SPACE          0x20
#define VK_PRIOR          0x21
#define VK_NEXT           0x22
#define VK_END            0x23
#define VK_HOME           0x24
#define VK_LEFT           0x25
#define VK_UP             0x26
#define VK_RIGHT          0x27
#define VK_DOWN           0x28
#define VK_SELECT         0x29
#define VK_PRINT          0x2A
#define VK_EXECUTE        0x2B
#define VK_SNAPSHOT       0x2C
#define VK_INSERT         0x2D
#define VK_DELETE         0x2E
#define VK_HELP           0x2F
.
.
.
<pre>