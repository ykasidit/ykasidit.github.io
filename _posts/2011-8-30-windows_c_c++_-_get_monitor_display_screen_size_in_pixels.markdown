---
title: Windows C C++ - Get monitor display screen size in pixels
category: programming
---

This code can get the screen size/resolution - works with multi-screen/monitors too - use the RefreshMonitorsMetrics() function.

<pre>
long lMonitorsX, lMonitorsY;

BOOL CALLBACK MonitorEnumProc(
HMONITOR hMonitor, // handle to display monitor
HDC hdcMonitor, // handle to monitor DC
LPRECT lprcMonitor, // monitor intersection rectangle
LPARAM dwData // data
)
{
RECT Rect;
MONITORINFO mi;

mi.cbSize = sizeof( mi );
Rect = *lprcMonitor;
GetMonitorInfo( hMonitor, &mi );

//chad edit - make += instead of max because we want whole multiscreen width
lMonitorsX += mi.rcMonitor.right;
lMonitorsX--;//0 based

lMonitorsY = max( lMonitorsY, mi.rcMonitor.bottom );
lMonitorsY--;//0 based

return TRUE;
}


void RefreshMonitorsMetrics( )
{

// work out how big we have to be to cover all the screens.
lMonitorsX = 0;
lMonitorsY = 0;
EnumDisplayMonitors(
NULL, // handle to display DC 
NULL, // clipping rectangle 
MonitorEnumProc, // callback function
0 // data for callback function 
);



}
</pre>