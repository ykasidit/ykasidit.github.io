---
permalink: /about
title: "About"
author_profile: true
---

About ClearEvo.com
=====

ClearEvo.com was started back in early 2005, hosting most of personal interest/developed products (the Nokia Symbian, J2ME days...) and official pages for some of our open-source projects and blog.


About Kasidit Yusuf
-------------------

I'm a 'Wireless Protocol Software Engineer' from Thailand. I've been developing software related to wireless protocols since 2004, and commercial mobile network test and measurement software since 2006. Currently my work focuses on development of low-level software and Android/Linux kernel modifications for radio-chip/modem access and decoding of LTE, WCDMA, GSM radio parameters and Layer-3 signalling messages, signalling state detection etc - on Android for the [AZENQOS - AZQ Android Network Testing Solution](http://www2.azenqos.com) product/Tools under [Meritech Solutions](https://meritechsolutions.com).

Open-source projects on github
------------------------------

<https://github.com/ykasidit>


Experience:
-----------

- Developed the totally new AZENQOS GNU\Linux based server back end (Python gevent based log import scheduler, Parquet in S3 objectstore + Python Pandas radio/signalling calculation modules for LTE/WCDMA/GSM, geographical radio params plotter, report generator, KML/CSV/QGIS-SPATIALITE-DB exporters, uplink wav playback + downlink wav recording on Android + Numpy-based wav silence/spacing detector/splitter + integration with Opticom's licensed POLQA caculation modules, etc...)
- Developed 'azm_db_merge': <https://github.com/freewillfx-azenqos/azm_db_merge>
- Developed the core parts of log processing, database merging, report generation of the AZENQOS second generation GNU/Linux based server, implemented to work together through docker-compose.
- Developed the main software engine for access and decoding of low-level radio parameters/measurements and Layer-3 messages used in the [AZQ Android Radio Network Testing Solution](http://www.azenqos.com/):
- Developed advanced radio-interface locking features: LTE PCI Cell Locking, LTE EARFCN Locking, LTE Band Locking, WCDMA Dedicated-Mode PSC Locking (disabling Measurement Reports), WCDMA UARFCN Locking, R99 Force, GPRS Force (disabling EDGE), etc - without needing to restart/reboot (power-cycle) the phone for most models.
- Developed software decoders for GSM Layer-3 Messages: RR, CC, MM, GMM, SM protocols (3GPP 44.018 24.007, 24.008, etc).
- Developed software decoders for WCDMA Layer-3 Messages: WCDMA RRC (3GPP 25.331) - a big "THANK YOU" to [asn1c](http://lionet.info/asn1c/blog/).
- Coded the "assembling" of the SysInfo "Segments" as they come over the air - through Qualcomm L3 packets.
- Developed device specific (radio chipset) methods on Android to digitally play wav sound to telephony voice uplink and also record on downlink of the receiving phone - totally in Android - no need for a notebook PCs to record via its soundcards. 
- Most of the above are developed in pure C programming language - for Android NDK, GNU/Linux testing engine and also Windows DLLs for the AZQ Replay software.
- Some old code for GSM RR/CC/MM decoding was in C++ (with [wxWidgets](http://www.wxwidgets.org/) core and also legacy Symbian C++) - before I learned that pure C was much more suitable for my tasks and preference and switched to C fully.
- Experience with Qualcomm protocols/formats, Diagnostic packets, modem command/reponse protocols and related Android Linux kernel drivers.
- Experience with Qualcomm tools and technologies.
- Experience with LTE, VoLTE, LTE Carrier Aggregation, WCDMA and GSM L3 signalling protocols, parameters and the calculation thereof.
- Developed Android software to digitally record Telephony Downlink audio, digitally playing wav audio to Telephony Uplink on Qualcomm MSM, MDM and Exynos+MDM (S6 HK) Android phones - for audio quality sample recording (on GSM/WCDMA and VoLTE) and processing.
- Developed server software to use POLQA MOS score calculation tools.
- VoLTE SIP full decrypted (behind IPSec) message access and RTP, RTCP packet statistics - on some Android VoLTE phone models - requires kernel and system modificaitons.
- Android enterprise software/kernel modifications that can directly read/write modem settings, and directly read/write modem command pipes without going through Android RIL.
- Enterprise Android 'root-like' app firmware development - also disbling SELinux and other security features that can block certain engineering access. The kernel modifications that allow 'root' access to only certain enterprise apps. Customer simply flashes the 'custom firmware' and installs the enterprise app that requires root. Phone is not 'rooted' - root checkers would show so. (Nexus 5X, Samsung S6 HK, Samsung S5, Note 4/Edge, Google/LG Nexus 5, etc).
- Some Android Linux Kernel Driver pathes for ARM (Qualcomm MSM) targets - worked on a driver improvement to better handle high speed packet logging when running on multi-cores like Google Nexus 4 (2013).
- Happy to be one of the world's 29 [winners of Symbian's global "Because of the Code" competition in 2007](symbian_because_of_the_code_2007_winners.pdf).
- That was a world-wide competition - send 100 lines of Symbian C++ code and let the expert judges compare and choose a few...
- Graduated in Computer Science (BSc) from Kasetsart University, Thailand.
- Developed a C++ implementation of Bluetooth protocols L2CAP, SDP, RFCOMM and OBEX (Acedemic "Computer Science" Project during BSc studies at Kasetsard University) to send files via Bluetooth:
- The developed Bluetooth protocol stack successfully sended files and vCards via "OBEX Object Push" to Nokia Symbian S60 Phones and also PC Computers running WIDCOMM Bluetooth stacks, etc.
- Developed a C++ (used MFC at that time) implementation of required parts of L2CAP, SDP, RFCOMM and OBEX on top of a driver and HCI implementation by [FreeBT's](http://www.freebt.net/) (6th December, 2004 - A3 Release).
- The old [source code (quite low-quality code - please forgive me as I was just learning to develop software) - click here to download](kasidit_cs_bsc_BTServerLIB_l2cap_sdp_rfcomm_obex_implementation.zip).
- A Winner of Thailand Java Contest 2003. In category "Personal Applications" - the J2ME project "Bangkok Bus Pathfinder" developed with my colleague Anon Sutichairatana. Developed a file indexing/storage system that stored the whole database of all Bangkok bus routes (bus number "109" for example) and the "places"/"main stations" that it pased into a searchable database for the tiny RAM (about 50K if I remember correctly) available in the Siemens SL45 - it can also search (even connecting routes) for what bus take to get from "start" to "destination" by querying a Java-Servlet via GPRS.

Contact: **`<`ykasidit[AT]gmail.com`>`**
