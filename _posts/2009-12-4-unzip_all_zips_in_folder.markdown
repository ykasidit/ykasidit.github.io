---
title: Unzip all zips in folder
category: howto
---

I wanted to unzip lots of zips in a folder (as I downloaded lots of 3gpp specs from  ftp.3gpp.org - they were all zipped)
Found a great solution from <http://ubuntuforums.org/showthread.php?t=564711> - simply run in your terminal:

`for f in *.zip; do unzip "$f"; done`

This code can also be adapted to do many other repititive things. I want to make all those docs to pdf too (since they are all .doc and some big files take a long time to import in openoffice everytime to read them - so it's better to use oowriter to convert them to pdf once). I'll try to write about that soon. Ubuntuforums is a great place to learn many things!
