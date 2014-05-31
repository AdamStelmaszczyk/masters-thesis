#!/usr/bin/Rscript

set.seed(1)
ITER = 100
SD = 0.1
NP = 10
F = 1
x = rep(NA, NP)
for (i in 1:NP)
{
	x[i] = i + rnorm(1, 0, SD)
}
x[1] = -10
x[10] = 20

png(paste(commandArgs(TRUE)[1], ".png", sep = ""))
plot(x, rep(0, NP), pch = 20, xlim=c(-60,60), ylim = c(0, ITER/3))

mutants = c()
for (i in 1:ITER)
{
	x1 = x[sample(NP, 1)]
	x2 = x[sample(NP, 1)]
	x3 = x[sample(NP, 1)]
	x4 = x[sample(NP, 1)]
	x5 = x[sample(NP, 1)]
	x6 = x[sample(NP, 1)]
	x7 = x[sample(NP, 1)]
	x8 = x[sample(NP, 1)]
	x9 = x[sample(NP, 1)]
	x10 = x[sample(NP, 1)]
	x11 = x[sample(NP, 1)]
	x12 = x[sample(NP, 1)]
	x13 = x[sample(NP, 1)]
	#mutants = c(mutants, x1 + F*(x2 - x3))
	mutants = c(mutants, x1 + (F/sqrt(6))*(x2 - x3 + x4 - x5 + x6 - x7 + x8 - x9 + x10 - x11 + x12 - x13))
}
hist(mutants, xlim=c(-60,60), ylim = c(0, ITER/3))
points(mutants, rep(0, ITER), col = "red")

