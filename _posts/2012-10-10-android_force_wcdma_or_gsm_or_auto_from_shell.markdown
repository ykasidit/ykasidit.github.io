---
title: Android force WCDMA or GSM or AUTO from shell
category: howto
---

This requires a rooted phone - tested on HTC OneS (Thailand) and OneX (AT&T - USA).

Simply open an *adb shell* and enter:

1. <pre>
./sqlite3 /data/data/com.android.providers.settings/databases/settings.db "update secure set value='2' where name='preferred_network_mode'"
</pre>

2. after "su", <pre>killall com.android.phone</pre>

Done.

The step 1 changes the settings database - but it is not reflected in the settings app or known/reloaded by com.android.phone (which I understand that it does the real forcing...) yet - we can reboot but that takes too long and unneccesary - I found that killing the process of com.android.phone would cause some watchdog to re-start it (step 2) - and re-load our new settings into effect (probably by calling *setpreferrednetworktype* with our new values - I think we can't call that internal java function directly due to signature/key restrictions).

I'd like to try to find some time to make an app to do/automate this - it helps rooted phones with special firmwares have that missing option to force directly - very beneficial for programmatical/automated forcing - this is an alternative to the manual/GUI method using the *#*#4636#*#* > phone info > Setpreferred network type method.
