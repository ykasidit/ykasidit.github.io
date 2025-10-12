---
title: Decoding WCDMA RRC packets (3GPP 25.331)
category: programming
---

The 3GPP 25.331 provides the asn1 syntax for the specs of WCDMA RRC packets sent over 3G networks between phone and base station, however, you need a asn1 compiler to convert it to C code to do the decoding in a software program. 

Thanks to [Lev Walkin's asn1c open source asn1 compiler](http://lionet.info/asn1c/blog/), you can convert asn1 sytax source into C code ready for decoding the raw binary packets! This is a huge contribution by Lev Walkin. Thanks so much, Lev!
 