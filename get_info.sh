for dir in data/*
do
	echo $dir
	rm $dir/*.info
	for i in 15 16 19 20 21 22 24
	do	
		dat_file="$dir/data_f$i/bbobexp_f"$i"_DIM40.dat"
		out_file="$dir/bbobexp_f$i.info"
		cat $dat_file | awk '{ if ($3 == "evaluation" && p != 0) printf("%f ", p); p = $3}' > $out_file
	done
done
