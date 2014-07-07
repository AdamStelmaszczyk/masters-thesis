for file in derand*
do
	cat $file | awk '{ if ($11 != "") print $1" "$11}' | sed 's/%//g' | sed -n 16,30p
done
