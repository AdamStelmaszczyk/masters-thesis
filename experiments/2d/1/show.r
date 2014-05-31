#!/usr/bin/Rscript

set.seed(1)
ITER = 10000
SD = 0.1
NP = 20
F = 1
x = rep(NA, NP)
y = rep(NA, NP)
for (i in 1:20)
{
	x[i] = i + rnorm(1, 0, SD)
	y[i] = rnorm(1, 0, SD)
}
y[10] = 2.52
p = data.frame(x, y)
n = nrow(p)

png(paste(commandArgs(TRUE)[1], ".png", sep = ""))
plot(p, pch = 20, xlim = c(-20,40), ylim = c(-10,10))

abline(h = 5)
abline(h = 2.5)
abline(h = -2.5)
abline(h = -5)

for (i in 1:ITER)
{
	p1 = p[sample(n, 1), ]
	p2 = p[sample(n, 1), ]
	p3 = p[sample(n, 1), ]
	p4 = p[sample(n, 1), ]
	p5 = p[sample(n, 1), ]
	p6 = p[sample(n, 1), ]
	p7 = p[sample(n, 1), ]
	p8 = p[sample(n, 1), ]
	p9 = p[sample(n, 1), ]
	p10 = p[sample(n, 1), ]
	p11 = p[sample(n, 1), ]
	p12 = p[sample(n, 1), ]
	p13 = p[sample(n, 1), ]
	#points(p1 + F*(p2 - p3), col = "red")
	points(p1 + (F/sqrt(6))*(p2 - p3 + p4 - p5 + p6 - p7 + p8 - p9 + p10 - p11 + p12 - p13), col = "red")
}
