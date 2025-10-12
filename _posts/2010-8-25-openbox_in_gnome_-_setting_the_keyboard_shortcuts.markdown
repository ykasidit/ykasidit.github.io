---
title: Openbox in GNOME - setting the keyboard shortcuts
category: howto
---

As the "window manager" Openbox being much more responsive than the default one called Metacity - it proved to be a great replacement expecially on my slower notebook. Much faster/responsive when doing all kinds of gui tasks - opening/closing a window, switching workspaces, etc.

**Install on Ubuntu 10.04**

    sudo apt-get install openbox obconf

**Starting Openbox**

Press Alt-F2 and enter:

    openbox --replace

However, as using the keyboard is at times much faster than using the mouse, some normal GNOME/metacity shortcuts are missing - I did as below to add the main keyboard shortcuts I use:

**Add the section below to your openbox config file:**

    gedit ~/.config/openbox/rc.xml

**Find the `</keyboard>` line and add the following in a new line just above it:**
    
    <!-- my custom Keybindings -->
    <keybind key="A-F1">
      <action name="execute">
        <execute>gnome-panel-control --main-menu</execute>
      </action>
    </keybind>
    <keybind key="A-F2">
      <action name="execute">
        <execute>gnome-panel-control --run-dialog</execute>
      </action>
    </keybind>
    <keybind key="A-F10">
      <action name="ToggleMaximizeFull"/>
    </keybind>
    <keybind key="A-F9">
      <action name="Iconify"/>
    </keybind>
    <keybind key="A-F7">
      <action name="Move"/>
    </keybind>
    <keybind key="A-F8">
      <action name="Resize"/>
    </keybind>
    <keybind key="C-A-d">
      <action name="ToggleShowDesktop"/>
    </keybind>
    <keybind key="C-A-t">
      <action name="execute">
        <execute>gnome-terminal</execute>
      </action>
    </keybind>

**Let openbox reload your settings:**
    
    openbox --reconfigure

Done.

**Adjusting the window manager theme**

Go to System > Preferences > Openbox Configuration Mnanager


**Auto start openbox**

Go to System > Preferences > Startup Applications, press Add, enter some name, in the command, enter:

    openbox --replace



**Thanks to:**

<http://ubuntuforums.org/showthread.php?t=75471>

<http://openbox.org/wiki/Help:Actions>

<http://openbox.org/wiki/Help:GNOME/Openbox>
