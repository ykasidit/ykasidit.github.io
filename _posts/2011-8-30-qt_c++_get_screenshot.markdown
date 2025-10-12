---
title: Qt C++ Get Screenshot
category: programming
---

How to get the screenshot of the computer using Qt c++.

<pre>
    ss = QPixmap::grabWindow(QApplication::desktop()->winId());    
</pre>