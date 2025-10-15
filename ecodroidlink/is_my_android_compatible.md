---
title: Android Bluetooth Internet capbability check
description: Android Bluetooth Internet capbability check
---


Is my Android device Compatible with EcoDroidLink? How to test?
==============================================

Here's a simple way to test/simulate a Bluetooth Access Point from another Android phone:

Preliminary check:

On your Android, go to Settings > More... > Tethering & portable hotspot - Now look for a "Bluetooth Tethering" item - if it is available, there's a good chance that your device is compatible (but sometimes not - see the "better check" below) - but if you can't find it, your device is likely NOT compatible.

Better check:

1. Take another Android 4.0 or newer phone, turn on its Bluetooth. Then, share its mobile internet via bluetooth to your phone: Go to phone's Settings > More ... > Tethering & portable hotspot > Enable/check "Bluetooth Tethering". Then, go to Settings > Bluetooth, make it discoverable - you can normally do this by just clicking on the phone's self name to make it "visible to all nearby devices... for (1:xx) minutes...".

2. Take your device and try pair with the phone in step "1." above. Then, after you got them "paired", you'd see the name of the phone in step "1." in the list of paried devices in your phone's Bluetooth setting page, now click on the settings icon (on the right) of that phone's name. If this inner settings page has the "Internet Access" item and checkbox shown - this means that your phone/tablet has the "Bluetooth Internet Acess" usage capability and would likely work with EcoDroidLink. If not, this means that your device does not support this feature and would NOT work with EcoDroidLink.

**We've successfully tested EcoDroidLink with the following devices below:**

  - Samsung Galaxy S6 (Hong Kong)
  - Samsung Galaxy S5
  - Samsung Galaxy S3
  - Samsung Galaxy Tab 2 (7.0)
  - Samsung Galaxy Note
  - Sony Xperia V
  - Google (LG) Nexus 5
  - Google (LG) Nexus 4
  - HTC One X (1X)

