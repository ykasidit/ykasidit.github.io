---
title: VirtualBox USB fix for Ubuntu 10.04
category: howto
---

Howto use your USB device in a Windows XP guest on a Ubuntu 10.04 VirtualBox host:


*Part 1 - Generic USB devices*
------
0. Add your Ubuntu account to group "vboxusers" and "usb": Go to System>Administration>Users and Groups - then "Manage Groups", then:
   0.1 double-click "vboxusers" then check/enable your account in there, OK.
   0.2 Click Add > enter group name: usb, group id: 85, add your account in there too, OK.
   0.3 Restart your computer.

*Real thanks to <http://forums.virtualbox.org/viewtopic.php?f=7&t=30906&start=30> for the details filtered from the great UsersManual for this case's fix - essence of this post - the rest is just generic...*

1. Download and install vbox from <http://www.virtualbox.org/wiki/Linux_Downloads> (the OSE version in ubuntu software center dones't have USB settings...)

2. Add your new guest OS normally.

3. (While guest is off) Go to settings > USB > Check/enable the USB support, then plug-in your USB device you want to use in the guest OS, click the small button on the right: "add Filter from Device" - select your desired device, make sure it's checked/enabled. OK.

4. Start your guest, plug-in your USB device then the gust OS should start to recognize it normally - in some cases you should right-click the "usb" icon at the lower-right of the virtualbox window and select/check your device to enable/disable it from guest OS.   



*Part 2 - USB Devices with their own mass-storage "Virtual CD" driver-installer autorun drive*
------

For some devices - Part 1 is not enough - especially the air-cards or mobile devices/phones (which I had to test) with their virtual "CD" drive to autorun the dirver installer once you plugin usb - they wont work only merely the "Part 1" above - some message like *an error occured installing the device...* the hardware problem in the device manager would show as:

    "This device cannot start. (Code 10)"

Thanks to <http://ubuntuforums.org/showthread.php?t=393582#3> - we have the solution - For Ubuntu 10.04 the file to change is  /etc/udev/rules.d/10-vboxdrv.rules - so simply:

    sudo gedit /etc/udev/rules.d/10-vboxdrv.rules 

**Fine the (last) line that shows:**
  
    SUBSYSTEM=="usb", ENV{DEVTYPE}=="usb_device", GROUP="vboxusers", MODE="0664"

**Change "664" to "666" it so that it shows:**

    SUBSYSTEM=="usb", ENV{DEVTYPE}=="usb_device", GROUP="vboxusers", MODE="0666"

**Save the file.**

Then, start your virtualbox winxp again and things should probably work correctly now.






