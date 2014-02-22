#!/bin/bash

if [ $# -lt 2 ]; then
	echo "Usage: $0 results1 [results2] [results3] ..." 
	return 65
fi

if [ ! -e pngs ]; then 
	mkdir pngs
fi 

for file in $1/*.info
do
	filename=${file#*/}
	echo $filename
	args=""
	for i in "$@";
	do
		args=" $args $i/$filename"
	done
	./png.r $args
done

