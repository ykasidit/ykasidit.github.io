---
title: Qt C++ read a whole file into memory
category: programming
---

Sometimes you just want to get that whole file into a buffer for quick use:

<pre>
QFile file(abs_path);

    if(file.open(QIODevice::ReadOnly))
    {
        QByteArray bytes = file.readAll();
        file.close();
        main_template = bytes;
        qDebug(main_template.toAscii());
    }
    else
	qDebug("rfile failed...");
</pre>