---
title: Qt C++ make a Label show animated GIF - for something like an ajax-loading icon
category: programming
---

Just use a QMovie together with your label... something like this:

in class ctor
---------
<pre>
iAnimGif = new QMovie(":/images/ajax-loader.gif");
</pre>

show the animation when needed
-------------------
<pre>
ui->connectLoadingLabel->setMovie(iAnimGif);
iAnimGif->start();
</pre>