---
title: GNU C Generic Makefile Example
category: programming
---

Here's an example of a GNU Make "Makefile" to compile your C programs - it searches for all .c files and includes them in the compile list:

<pre>
#####generic conf
SRC_FILTER = .c
LIBS  = 
LIBS += 
CFLAGS = -I.
LDFLAGS =
CONF = .
SOURCES :=  $(shell find ./ -type f -name '*${SRC_FILTER}')
################

################target_specific conf

##### single target without a name - so we just call "make" without CONF=... for this target
ifeq ($(CONF),.)
#mkres := $(shell mkdir $(CONF))
CFLAGS += 
TARGET = your_compiled_binary_output_name
CC = gcc
LD = gcc
endif
####

################


OBJS := $(patsubst ./%${SRC_FILTER}, ${CONF}/%.o, $(SOURCES))

#all: post-build
#post-build: $(TARGET)
#	@echo post_build_step if you want to run somthing here like copy the binary to somewhere, etc.
#

all: $(TARGET)

$(TARGET): ${OBJS}
	$(LD) ${OBJS} ${LIBS} ${LDFLAGS} -o ${CONF}/"$(TARGET)"

${CONF}/%.o: %${SRC_FILTER}
	$(CC) $(CFLAGS) -o $@ -c $<

clean:
	rm -f ${CONF}/$(TARGET)
	rm -f $(OBJS)
</pre>