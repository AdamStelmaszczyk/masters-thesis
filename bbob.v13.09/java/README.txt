This is the Java interface of COCO experimental framework
    --- http://coco.gforge.inria.fr/ ---

It was tested using Java 1.6.0_16, it uses a JNI layer (Java Native Interface) to use the C version of COCO.

As usual, this software comes with absolutely no warranty (see Warranty at end).

It is here assumed that you know what COCO/BBOB is about http://coco.gforge.inria.fr/doku.php?id=bbob-2010 and that you have read the corresponding documentation http://coco.gforge.inria.fr/

Installation
============
If you are reading this, you already succeeded in unpacking the BBOB tar file :-)

After unpacking the archive, the bbob.vXX.XX/java directory should contain the following files:

benchmarkinfos.txt
libcjavabbob.dylib: generated using gcc-4.2.1 on Mac OS X 10.6
libcjavabbob.so: generated using gcc-4.4.3 on 32-bit Linux 2.6.32-30-generic
cjavabbob.dll: generated using gcc-3.4.5 (mingw-vista special r3) on 32-bit Windows Vista
exampleexperiment.out
exampletiming.out
javabbob/ExampleExperiment.java
javabbob/ExampleTiming.java
javabbob/JNIfgeneric.java
doc : folder with Java documentation
generatelib : folder containing files for generating the library in case the library files provided do not work.
README.txt

The javabbob.ExampleExperiment and javabbob.ExampleTiming classes are both executables and respectively launch a very quick but complete experiment using the random MY_OPTIMIZER optimizer and the timing experiment (warning, this program takes at least half a minute before the first output to console, be patient :-) ).

Before you can run the examples, you will need to:
* compile the java source files javabbob/ExampleExperiment.java and javabbob/ExampleTiming.java javabbob/JNIfgeneric.java for instance by using javac in the bbob.vXX.XX/java directory : javac javabbob/*.java
* set your library path to look for the java directory, otherwise you will get the following error
  Exception in thread "main" java.lang.UnsatisfiedLinkError: no cjavabbob in java.library.path
  To do so you might want to correctly set the appropriate environment variable (LD_LIBRARY_PATH for linux, PATH for Windows, DYLD_LIBRARY_PATH for Mac OS)

To execute the code, supposing you are in the bbob.vXX.XX/java directory do:
    java javabbob.ExampleExperiment

The output should match the file exampleexperiment.out, set input argument randomSeed to MY_OPTIMIZER correctly to have the exact same figures though.

Some dynamic link library files are provided in the bbob.vXX.XX/java directory. Those were compiled on Linux and Windows (see above for more details).
If you do not get the UnsatistiedLinkError exception mentioned above but cannot run the java code, this could mean the library is not appropriate for your system. Therefore you might have to compile from the source code. To do so refer to the file generatelib/README.txt

If compilation fails, or some run-time error appears when running either of those programs, get in touch immediately with bbob@lri.fr indicating all information about your system and compiler, as well as the complete error message(s).

Customization
=============
you might at some point want to try your own optimizer: this is fairly easy, assuming you know how to write java code

1 - Edit javabbob/ExampleExperiment.java (or javabbob/ExampleTiming.java) so that it calls your optimizer or include the code of your optimizer in the stead of MY_OPTIMIZER.

2 - Edit javabbob/ExampleExperiment.java and replace the upper cases strings "PUT_..." with the correct names.

3 - Compile the java code (again)

In case of problem, send a mail to bbob@lri.fr but please make sure first that your optimizer runs smoothly as a standalone application.
To discuss more general issues about BBOB: bbob-discuss@lists.lri.fr

Acknowledgment
==============
The BBOBies would like to acknowledge Mike Preuss for this contribution:
the implementation of the JNI for using the C-code in Java. We would also like to acknowledge Petr Posik for his help and feedback in the beta-tests.

Warranty
========
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL ANY CONTRIBUTOR TO THIS SOFTWARE BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

