---
title: PC Computer Bluetooth Internet Setup
description: How to setup Bluetooth Internet access on Windows, Ubuntu GNU/Linux PC and Raspberry Pi Computers
---


PC Computer Bluetooth interent Setup
====================================

<big>[Instructions for Windows 7 Desktop/Notebook Bluetooth Internet Setup - Please Click here](../windows_7_bluetooth_internet_setup)</big>

<big>[Instructions for Windows XP Desktop/Notebook Bluetooth Internet Setup - Please Click here](../windows_xp_bluetooth_internet_setup)</big>

<big>[Instructions For Ubuntu Desktop/Notebook Bluetooth Internet Setup - Please Click here](../ubuntu_bluetooth_internet_setup)</big>
<br/>**Note: For Ubuntu, we recommend to use the method for Raspberry Pi (via terminal/command-line) as described [further below](https://www.clearevo.com/ecodroidlink/#setup_raspberry_pi).** The default graphical-interface method/screenshots in the above link had some random disconnect problems on a Ubuntu 12.04 computer/notebook we've tested with EcoDroidLink and also Bluetooth tethering from Android. After the annoying disconnect, the connection option in 'network manager' also disappeared list so it's hard to re-connect - a re-pair and re-enable of the "Use mobile phone as network device" was required, but you can end up duplicate items in the network manager list. Therefore, we recommend to use the command-line method as described for Raspberry Pi below.

<big><strong><a id="setup_raspberry_pi">Instructions for Raspberry Pi (Raspbian OS) Bluetooth Internet Setup</a></strong></big>

Since Bluetooth consumes less power than WIFI, Bluetooth Internet could be quite suitable for Raspberry Pi projects since it could help avoid USB power-supply limit issues (causing instabilities or strange behavior) when some USB WIFI or USB 3G Aircard dongles consume too much power/current/amperes from the Pi's USB port.

For Raspberry Pi with Raspbian (and also recommended for Ubuntu on computers/laptops) please use the method below:

  - First, connect to internt from your existing ethernet/LAN cable source - then install bluez-compat:
<pre>
sudo apt-get install bluez-compat
</pre>

  - Now, remove the ethernet/LAN cable. Make sure the bluetooth on your Raspberry Pi is on:
<pre>
sudo hciconfig -a hci0 up
</pre>

  - Now, let's scan for EcoDroidLink:
<pre>
sudo hcitool scan
</pre>

  - You'd get a result address from the scan, like:
<pre>
Scanning ...
	00:1A:7D:DA:71:0D	EcoDroidLink
</pre>

  - Copy that address, use it in the 'pand' command - just replace '00:1A:7D:DA:71:0D' with your own results:
<pre>
sudo pand -c 00:1A:7D:DA:71:0D -n
</pre>
You can also try add the '--persist' option as described [here](https://blog.kugelfish.com/2012/10/look-ma-no-wires-raspberry-pi-bluetooth.html).

If it works correctly, it'd show output like:
<pre>
pand[2252]: Bluetooth PAN daemon version 4.98
pand[2252]: Connecting to 00:1A:7D:DA:71:0D
pand[2252]: bnep0 connected
</pre>

  - Run dhclient on the newly created bnep0:
<pre>
sudo dhclient bnep0
</pre>

  - DONE! Try ping www.google.com

  - To automate this, you can easily make a script containing the 'pand' and 'dhclient' commands above (with the bluetooth-address of your EcoDroidLink box) to connect and make it run/autostart on boot of your Pi.

  - Example output doing all the steps above:
<pre>
pi@raspberrypi:/etc$ sudo apt-get install bluez-compat
Reading package lists... Done
Building dependency tree       
Reading state information... Done
The following NEW packages will be installed:
  bluez-compat
0 upgraded, 1 newly installed, 0 to remove and 4 not upgraded.
Need to get 182 kB of archives.
After this operation, 387 kB of additional disk space will be used.
Get:1 https://mirrordirector.raspbian.org/raspbian/ wheezy/main bluez-compat armhf 4.99-2 [182 kB]
Fetched 182 kB in 8s (22.2 kB/s)       
Selecting previously unselected package bluez-compat.
(Reading database ... 68955 files and directories currently installed.)
Unpacking bluez-compat (from .../bluez-compat_4.99-2_armhf.deb) ...
Processing triggers for man-db ...
Setting up bluez-compat (4.99-2) ...
pi@raspberrypi:/etc$ 

-- Note: i did a reboot here simulate real clients coming from a fresh state --

pi@raspberrypi:~$ hcitool scan
Scanning ...
        00:1A:7D:DA:71:0D       EcoDroidLink
        00:1A:7D:00:02:E3       kasidit-0
pi@raspberrypi:~$ sudo pand -c 00:1A:7D:DA:71:0D -n
pand[2127]: Bluetooth PAN daemon version 4.99
pand[2127]: Connecting to 00:1A:7D:DA:71:0D
pand[2127]: bnep0 connected
pi@raspberrypi:~$ sudo dhclient bnep0
pi@raspberrypi:~$ ifconfig
bnep0     Link encap:Ethernet  HWaddr 00:1a:7d:da:71:09  
          inet addr:192.168.1.43  Bcast:192.168.1.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:13 errors:0 dropped:0 overruns:0 frame:0
          TX packets:15 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:1647 (1.6 KiB)  TX bytes:1443 (1.4 KiB)

eth0      Link encap:Ethernet  HWaddr b8:27:eb:38:76:19  
          UP BROADCAST MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:65536  Metric:1
          RX packets:8 errors:0 dropped:0 overruns:0 frame:0
          TX packets:8 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:1104 (1.0 KiB)  TX bytes:1104 (1.0 KiB)

pi@raspberrypi:~$ ping www.google.com
PING www.google.com (173.194.127.49) 56(84) bytes of data.
64 bytes from hkg03s10-in-f17.1e100.net (173.194.127.49): icmp_req=1 ttl=55 time=104 ms
64 bytes from hkg03s10-in-f17.1e100.net (173.194.127.49): icmp_req=2 ttl=55 time=108 ms
64 bytes from hkg03s10-in-f17.1e100.net (173.194.127.49): icmp_req=3 ttl=55 time=125 ms
64 bytes from hkg03s10-in-f17.1e100.net (173.194.127.49): icmp_req=4 ttl=55 time=98.4 ms
^C
--- www.google.com ping statistics ---
4 packets transmitted, 4 received, 0% packet loss, time 3000ms
rtt min/avg/max/mdev = 98.457/109.116/125.454/10.062 ms
pi@raspberrypi:~$ 
</pre>

  - This same method can be used for making your Pi or Ubuntu computer use internet shared from Android phones when 'Bluetooth Tethering' is enabled in the Android 'Tethering and Hotspot' Settings section.

  - Below is a screenshot of using internet on a Raspberry Pi (there is no Ethernet/LAN connected to the Pi - I'm using a [Serial Cable to connect/login into the Pi](https://elinux.org/RPi_Serial_Connection) from my Ubuntu computer via 'GNU Screen'.)<br/>**Note:** The ping might be a bit slower than normal just because the test was done in another room with a bathroom inbetween the room that the EcoDroidLink Access Point is working - this shows that the operation range and Bluetooth wireless penetration is quite good!
<br/>![](../bluetooth_internet_on_raspberry_pi.png)

