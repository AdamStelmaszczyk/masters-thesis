#!/bin/sh

export LD_LIBRARY_PATH=bbob.v13.09/java/generatelib:$LD_LIBRARY_PATH

nice -n 19 java -cp lib/*:bin javabbob/Experiment "$@"
