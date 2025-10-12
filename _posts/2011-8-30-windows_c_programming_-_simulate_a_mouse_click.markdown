---
title: Windows C programming - Simulate a Mouse Click
category: programming
---

Simulate a Mouse Click in Windows using native C programming:

Includes
--------
<pre>
#include <windows.h>
#include <Winuser.h>
#include <Wtsapi32.h>
</pre>

Code
---

<pre>
	INPUT    Input={0};
        // left down
        Input.type      = INPUT_MOUSE;
        Input.mi.dwFlags  = MOUSEEVENTF_LEFTDOWN;
        ::SendInput(1,&Input,sizeof(INPUT));

        Sleep(10);
        // left up
        ::ZeroMemory(&Input,sizeof(INPUT));
        Input.type      = INPUT_MOUSE;
        Input.mi.dwFlags  = MOUSEEVENTF_LEFTUP;
        ::SendInput(1,&Input,sizeof(INPUT));
</pre>
