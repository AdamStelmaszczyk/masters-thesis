#!/usr/bin/Rscript

filename = commandArgs(TRUE)
args = length(filename)

data = as.list(rep(NA, args))
xmax = 0
xmin = +Inf
for (i in 1:args)
{
	data[[i]] = scan(filename[i], sep=" ")
	xmax = max(xmax, max(data[[i]]))
	xmin = min(xmin, min(data[[i]]))
}

start = regexpr("f", filename[1], fixed=T)[1] + 1
end = regexpr(".", filename[1], fixed=T)[1] - 1
fnum = substr(filename[1], start, end)

names = rep(NA, args)
for (i in 1:args)
	names[i] = substr(filename[i], 0, regexpr("/", filename[i], fixed=T)[1] - 1)
	
png(paste("pngs/", fnum, ".png", sep = ""))

colors = c("springgreen", "yellowgreen", "palegreen4", "black", "cadetblue1", "blue", "blueviolet", "darkblue", "orange", "salmon", "magenta", "red" )

plot(ecdf(data[[1]]), verticals = TRUE, do.points = FALSE, col = colors[1], main = fnum, xlab = "najlepszy - minimum", ylab = "Prawdopodobieństwo", xlim = c(xmin, xmax), ylim = c(0, 1))
for (i in 2:args)
	lines(ecdf(data[[i]]), verticals = TRUE, do.points = FALSE, col = colors[i])
	
legend("bottomright", names, col = colors, lty = 1);
