---
title: WIFI-Alternative for Android phones and tablets - EcoDroidLink Bluetooth Internet Access Point
description: Share internet from LAN/Ethernet to Android via Bluetooth using EcoDroidLink Bluetooth Aceess Point. Saves more battery than WIFI in our tests!
---

> **Note:** This project is no longer maintained or supported.  
> The following information is provided solely for historical and legacy reference.

WIFI Alternative for Android phones and tablets - EcoDroidLink Bluetooth Internet Access Point
===========================================

The Lower-power alternative to WIFI - using Bluetooth Internet made our Android phone battery charge last up to 26% longer than WIFI in our test! [see our battery test results](bluetooth_vs_wifi_on_android_battery_consumption)

If you ever used 'Bluetooth Internet Tethering' - this is essentially the same but instead of mobile internet, EcoDroidLink uses your home/office Internet router as the internet source (via a LAN cable), providing internet to Android phones and tablets (and even most laptop PCs, notebooks or Raspberry Pis) via Bluetooth.

**NEW - August 2017** *Version 3 featuring new hardware and upgraded software and now only 49 USD!*

<img class="img-responsive" src="/images/ecodroidlink.jpg">

<img class="img-responsive" src="ecodroidlink_main.png">

Use EcoDroidLink to provide Bluetooth Internet as an alternative to WIFI in your home or office (to <a href="bluetooth_vs_wifi_on_android_battery_consumption">very likely save more battery power</a> on your Android phone or tablet!). Connect it using an ethernet cable (provided) to existing home or office internet modems/routers (it's ok if the modem/router has WIFI).

*WIFI, 4G, 3G drains too much battery? **Try Bluetooth Internet** - much lower-power yet fast-enough.*

<a id="demo"></a>

See how it works
-----------------

Watch the demo video using EcoDroidLink on a Nexus 5 phone:

<div class="embed-responsive embed-responsive-4by3">
<iframe width="560" height="315" src="https://www.youtube.com/embed/rk6v9L3IzhM" frameborder="0" allowfullscreen></iframe>
</div>

**[See more screenshots of using Bluetooth Internet on Android...](gallery)**

Yes, Bluetooth is technically slower than WIFI - max downlink speed we tested is apx 1.7 Mbps but practically 'responsive' and fast-enough for most day-to-day use cases - as shown in above video. Personally we don't notice much difference in web-browsing, Messaging or even Youtube (unless you go beyond 720p video resolution).

<a id="buy"></a>

Get it now!
-----------

<big>Product discontinued. If you have similar requirements, please kindly contact **`<`support[AT]clearevo.com`>`**.</big>

<a id="setup"></a>

Easy Setup
-----------

Watch the EcoDroidLink Internet setup and usage on Samsung S6 video:

<div class="embed-responsive embed-responsive-4by3">
<iframe width="560" height="315" src="https://www.youtube.com/embed/BVyl63axH-8" frameborder="0" allowfullscreen></iframe>
</div>

Should likely work with most newer Android phones.

Below video shows the EcoDroidLink (current model) power up sequence - you should wait around 30 seconds until the Bluetooth dongle blinks:

<div class="embed-responsive embed-responsive-4by3">
<iframe width="560" height="315" src="https://www.youtube.com/embed/BlPZ62LR2sI" frameborder="0" allowfullscreen></iframe>
</div>

Tested with Samsung Galaxy S6, S5, S3, Tab 2 (7.0), many Google Nexus phones, many Sony Xperia phones and more! Nothing to install, No need to root - EcoDroidLink uses a standard bluetooth profile/feature already there in many Android devices!*


---

[Step-by-step EcoDroidLink Box Setup Instructions](ecodroidlink_box_setup)

---

[Step-by-step Android Bluetooth Internet Setup Instructions](android_bluetooth_internet_setup)

---

[Step-by-step PC Computer Bluetooth interent Setup Instructions](pc_computer_bluetooth_internet_setup)

---

[Is my Android device Compatible with EcoDroidLink? How to test?](is_my_android_compatible)

---

Features
--------

  - About 7 meters tested real use range with Android phones.

  - Fast-Enough Connection - Although the *tested max-internet-speed* through Bluetooth is much slower (about 1.7 Mbps on Speedtest.net) than WIFI, it's more than fast enough for web-Browsing, Facebook, Instant Messaging. We've even watched Youtube HQ on two devices at the same time with no lag! The max number of devices connected in theory is 7. So far we only tested up to 3 or 4 Android connected to it at the same time and Internet worked fine for all.

  - Saves phone/tablet battery - **Using Bluetooth Internet made phone's battery last 26% longer than using WIFI in our android Youtube test.** We used a Sony Xperia V, fully charged and opened a 9-hour youtube video until battery finishes and phone powers off. [Click here to see the Bluetooth vs WIFI bettery consumption comparison](bluetooth_vs_wifi_on_android_battery_consumption). Many researches conclude that Bluetooth consumes less low-power than WIFI, especially for low-data-rate use-cases. We think that most common use-cases like web-browsing, messaging, email require quite low data-rates. However, results may vary depending on your usage styles and application - as some researches show that for high data-rate use-cases, WIFI might be more efficient. Therefore, we invite you to try and decide for yourself. EcoDroidLink would likely be helpful for most generic internet usage/applications and also business/enterprise uses like web-based POS, inventory or restaurant ordering systems already used on your Android tablets but you have to frequently charge the device - *WIFI and 3G, 4G drains too much battery? 2G is too slow? **Bluetooth internet access** is the middle-path solution!*.

  - An alternative VIP Internet Route - Sometimes in offices, the WIFI gets saturated as it's used by so many users - but when you plug the EcoDroidLink Access Point to your router's ethernet port, it opens up a new uncongested path/route from your compatible Android and PCs to access your internet ADSL or Cable router!

  - Similar practical range to generic WIFI access points - The EcoDroidLink unit is in our guestroom, we can use the bluetooth internet from the garden infront of the house, or the room next to it, but signal it gets too weak to connect from 2nd floor.

  - Standard Bluetooth Network Access Point profile - EcoDroidLink provides the standard Bluetooth Network Access Point profile, this means it would likely work with many other devices or industrial/enterprise equipment that require Bluetooth Internet Access Points. We tested the Broadcom/Widcom Bluetooth software for windows: it discovered, paired and the "Network Access" option worked successfully with EcoDroidLink. It should also work well to provide internet access to other Raspberry Pi computers/boxes with compatible [USB Bluetooth dongles](https://www.clearevo.com/bluetooth_usb_dongle) too - just use [this method](#setup_raspberry_pi).

  - Some say the Internet is even more 'responsive' than using their WIFI access point - although we didn't do any real/logged tests yet, at least two people who've tried EcoDroidLink in thier homes say that the internet feels more responsive (faster response time) when searching google or chat. We hope to do some real browsing tests and post results in the future.

  - Fully Open-Source - This means you can *freely use, study, modify/customize and also share the EcoDroidLink software*. EcoDroidLink's hardware is based on Raspberry Pi, we simply assemble the certified (CE) parts in a standard housing/case (from official distributor "RS Components") and provide our software in the SD card. It runs Archlinux ARM and BlueZ for Bluetooth. The EcoDroidLink software is actually very simple - it auto-starts, configures, manages/maintains the Bluetooth Network Access Point service and its requirements. The EcoDroidLink access point management software is licensed under GNU GPL, provided in the SD-Card at "/home/ecodroidlink". Written in Python programming language - simple and easy to modify, no compiled binaries.

**Looking for legacy enterprise Bluetooth internet?** Apart from the default modern 'network access profile' which EcoDroidLink provides by default, we can also provide Bluetooth DUN (Dial-up Network) Internet implementation for legacy/older enterprise hardware/devices that need Bluetooth internet from older Nokia cell-phones. We need to test/customize per device - please contact us at support@clearevo.com for Bluetooth DUN internet trails.

Household Internet Router/Modem Requirements
-------------------------------

EcoDroidLink is just a wireless "Access Point", it needs to be connected to your home/office's "Router" or "Modem" via an ethernet cable. Normal ADSL/WIFI routers which are connected to your Internet Service Provider would most probably work (it just requires DHCP - the common/default auto IP assignment used with WIFI).

---

Questions & Comments
------------------

We're just a humble team of two brothers trying to develop and support a great product. It would be nice to recieve your suggestions, questions and comments, please kindly email to **`<`support[AT]clearevo.com`>`**.

