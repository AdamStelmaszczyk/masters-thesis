This is the instruction file to generate the dynamic library used by javabbob.JNIfgeneric:

You looked into the generatelib folder either because you were curious or could not run the java code. Before, please make sure you set the path to the library correctly: to do so you might want to set the appropriate environment variable (LD_LIBRARY_PATH for Linux, PATH for Windows, DYLD_LIBRARY_PATH for Mac OS).
To compile the library for your system, you will have to:

 * Get the BBOB C-source code available at http://coco.gforge.inria.fr/doku.php?id=downloads
 * Unarchive it so that you have the following folder structure:
  bbob.vX.XX --> c
             |
             --> java --> generatelib
             ...
 * Comment or uncomment the lines corresponding to your system in the file Makefile
 * Compile the library by using the 'make' command
 * This should generate a library file in the current folder
 * Set the library path to look for this new library file.

Then try executing the java code again, supposing you are in the bbob.vX.XX/java directory do:
    java javabbob.ExampleExperiment

