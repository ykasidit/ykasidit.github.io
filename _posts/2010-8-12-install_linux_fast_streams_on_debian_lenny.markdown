---
title: Installing OpenSS7's Linux Fast STREAMS on Debian Lenny
category: programming
---

Did the following to compile and install on a Debian Lenny server.

    wget http://www.openss7.org/repos/tarballs/streams-0.9.2.4.tar.gz

    tar -xzvf streams-0.9.2.4.tar.gz 

    mkdir build

    cd build

    sudo apt-get install linux-headers-$(uname -r)

    sudo apt-get install libsnmp-dev

    sudo apt-get install libperl-dev

    ../streams-0.9.2.4/configure --enable-autotest CC=gcc-4.1

    make

    make check

    sudo make install

    sudo make installcheck

