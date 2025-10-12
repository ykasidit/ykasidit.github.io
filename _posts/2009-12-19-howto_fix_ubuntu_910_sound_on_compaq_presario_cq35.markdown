---
title: Howto fix ubuntu 9.10 sound on compaq presario CQ35
category: howto
---

Thanks to my cousin Yahya Hamad (computer science student in Ladkrabang University), he wanted to share a little howto fix the Ubuntu 9.10  "no sound" problem on compaq presario CQ35, I edit it a bit, as follows. Thanks very much to the Indonesian howto writer "Slacking Ubuntero". 

I found this [indonisian language howto](http://slackingubuntero.wordpress.com/2009/11/02/troubleshooting-suara-di-compaq-cq35-109tu/)
So i translate by google translate  >> <http://translate.google.com/translate?hl=en&sl=id&tl=en&u=http%3A%2F%2Fslackingubuntero.wordpress.com%2F2009%2F11%2F02%2Ftroubleshooting-suara-di-compaq-cq35-109tu%2F>

It say to do 3 steps.
I did only the third step.

Edit /etc/modprobe.d/alsa-base.conf

	sudo gedit /etc/modprobe.d/alsa-base.conf

Then I added these 2 lines to the end of the file

	options snd-hda-intel model=hp-m4 enable=1 index=0
	options snd-hda-intel enable_msi=1

Then I restart and guess what???

Thank God it worked!!!!!!!!!!!

