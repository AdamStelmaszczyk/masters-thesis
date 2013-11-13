#!/bin/sh

if [ $# -lt 2 ]; then
	echo "Usage: $0 results1 [results2] [results3] [results4] [results5] [results6]" 
	return 65
fi

if [ ! -e pngs ]; then 
	mkdir pngs
fi 

for file in $1/*.info
do
	if [ $# -eq 2 ]; then
		./png.r $file $2/${file#*/}
	else
		if [ $# -eq 3 ]; then
			./png.r $file $2/${file#*/} $3/${file#*/}
		else
			if [ $# -eq 4 ]; then
				./png.r $file $2/${file#*/} $3/${file#*/} $4/${file#*/}
			else
				if [ $# -eq 5 ]; then
					./png.r $file $2/${file#*/} $3/${file#*/} $4/${file#*/} $5/${file#*/}
				else
					./png.r $file $2/${file#*/} $3/${file#*/} $4/${file#*/} $5/${file#*/} $6/${file#*/} 
				fi
			fi
		fi
	fi
done

