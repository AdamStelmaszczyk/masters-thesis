#!/bin/bash

for file in *.out
do
	out=($(cat $file | awk '{print $1}' | grep '%' | sed 's/%//g'))
	for i in $(seq 0 6)
	do
		sum=0
		for j in $(seq 0 14)
		do
			index=$(echo $i*15 + $j | bc)
			sum=$(echo $sum + ${out[index]} | bc);
		done	
		avg=$(echo $sum/15 | bc)
		echo -n "& $avg "
	done
	echo "         $file"
done

for alg in *
do
	if [ ! -d $alg ]
	then
		continue;
	fi
	echo $alg
	for info in $alg/*.info
	do
		sum=0
		for i in `cat $info`
		do 
			sum=$(echo $sum + $i | bc);
		done; 
		echo -n "$info: " 
		total=$(wc -w $info | awk '{print $1}')
		echo $sum/$total | bc -l;
	done;
done;



