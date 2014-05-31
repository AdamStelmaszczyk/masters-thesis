#!/bin/bash

dir=cov_test
function run {
	echo $1 $2
	./run.sh -t -a $1 $2 > $dir/$1$2
}

run 'derand' 1
run 'derand' 2
run 'derand' 6
run 'derandinf' 1

run 'debest' 1
run 'debest' 2
run 'debest' 6
run 'debestinf' 1

run 'demid' 1
run 'demid' 2
run 'demid' 6
run 'demidinf' 1
