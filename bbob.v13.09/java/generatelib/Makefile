
#This is a Makefile that will generate the dynamic library to use with javabbob

#Define your compiler/linker if needed.
#CC=gcc

SRC=../../c

UNAME := $(shell uname)

#The JNIINCLUDE are two directories that hold the necessary include files for JNI, to be found under the JDK main dir
#DLLNAME is the name given to the dynamic link library, usually ending in .dll for windows, .so for linux/unix

ifeq ($(UNAME), Linux) # LINUX
#Input the path to your java here.
JAVA=/usr/lib/jvm/java-6-openjdk
JNIINCLUDE2=-I "$(JAVA)/include/linux"
DLLNAME=libcjavabbob.so
LDFLAGS=-shared
CFLAGS=$(JNIINCLUDE1) $(JNIINCLUDE2) -Wextra -Wall -std=c99 -pedantic -I$(SRC) -o $@ -fPIC
RM=rm
else ifeq ($(UNAME), Darwin) # MAC OS X 10.6
#Input the path to your java here.
JAVA=/Library/Java/Home
DLLNAME=libcjavabbob.dylib
LDFLAGS=-shared
CFLAGS=$(JNIINCLUDE1) -Wextra -Wall -std=c99 -pedantic -I$(SRC) -o $@
RM=rm
else # WINDOWS MINGW
#Input the path to your java here.
JAVA=c:/Program Files/Java/jdk1.6.0_17
JNIINCLUDE2=-I "$(JAVA)/include/win32"
DLLNAME=cjavabbob.dll
LDFLAGS=-Wl,--kill-at -mno-cygwin -shared
CFLAGS=-m32 $(JNIINCLUDE1) $(JNIINCLUDE2) -Wextra -Wall -std=c99 -pedantic -mno-cygwin -I$(SRC)
RM=del
# MP comment: for cygwin, the -mno-cygwin option is needed otherwise we load the wrong include files
endif

# LINUX CROSS COMPILATION for Windows (under test)
#Mingw32 cross-compilation
#LDFLAGS=-Wl,--kill-at -mno-cygwin -mwindows -shared -L/usr/i586-mingw32msvc/lib -I/usr/i586-mingw32msvc/include -m32 -I$(SRC)
#CFLAGS=-m32 $(JNIINCLUDE1) $(JNIINCLUDE2) -Wextra -Wall -std=c99 -pedantic -mno-cygwin

# GENERIC contains lines which should correspond to all systems. 
JNIINCLUDE1=-I "$(JAVA)/include"

########################################################################################

DIROK=dirOK.o

#Linux/Unix users can try to comment the above and uncomment the following line instead.
# DIROK=dirOK-linux.o

all: $(DLLNAME)

# Compile library
$(DLLNAME): $(SRC)/benchmarksdeclare.o $(SRC)/$(DIROK) $(SRC)/benchmarkshelper.o $(SRC)/benchmarks.o $(SRC)/benchmarksnoisy.o $(SRC)/fgeneric.o javabbob_JNIfgeneric.o
	$(CC) $(LDFLAGS) -o $@ $^ -lm

$(SRC)/benchmarkshelper.o $(SRC)/fgeneric.o $(SRC)/benchmarks.o $(SRC)/benchmarksnoisy.o $(SRC)/benchmarksdeclare.o : $(SRC)/bbobStructures.h

javabbob_JNIfgeneric.o: javabbob_JNIfgeneric.h $(SRC)/bbobStructures.h

#Benchmarks object files are optimized so they are exceptions to the implicit *.o generation rule.
$(SRC)/benchmarks.o : $(SRC)/benchmarks.c
	$(CC) -c $(CFLAGS) -O2 -o $@ $<

$(SRC)/benchmarksnoisy.o : $(SRC)/benchmarksnoisy.c
	$(CC) -c $(CFLAGS) -O2 -o $@ $<

#Clean
.PHONY: clean

clean:
	$(RM) -f *.o $(DLLNAME)
