#!/usr/bin/Rscript

seed = 1
set.seed(seed)
ITER = 10000
F = 1
x = c(-0.5, 0, 0.5, 3)
NP = length(x)
xlim = c(-6, 8)
ylim = c(0, ITER/2)

png(paste(seed, ".png", sep = ""))
plot(x, rep(0, NP), pch = 20, xlim = xlim, ylim = ylim, xlab = "x", ylab = "częstość")

mutants = c()
for (i in 1:ITER)
{
	x1 = x[sample(NP, 1)]
	x2 = x[sample(NP, 1)]
	x3 = x[sample(NP, 1)]
	x4 = x[sample(NP, 1)]
	x5 = x[sample(NP, 1)]
	w = F*(x2 - x3 + x4 - x5)
	mutants = c(mutants, x1 + w, x1 - w)      # DE/rand
	#mutants = c(mutants, w, -w) 	   # DE/best
}
hist(mutants, xlim = xlim, ylim = ylim, breaks = 100, xlab = "x", ylab = "częstość", main = "")
points(mutants, rep(0, length(mutants)), col = "red")
points(x, xlim = xlim, ylim = ylim, rep(0, NP), pch = 20, col = "black",  xlim = xlim, ylim = ylim, xlab = "x", ylab = "częstość")
