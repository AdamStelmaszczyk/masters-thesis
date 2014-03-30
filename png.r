#!/usr/bin/Rscript

filename = commandArgs(TRUE)
args = length(filename)

data = as.list(rep(NA, args))
xmax = 0
xmin = +Inf
for (i in 1:args)
{
	data[[i]] = scan(filename[i])
	xmax = max(xmax, max(data[[i]]))
	xmin = min(xmin, min(data[[i]]))
}

start = regexpr("f", filename[1], fixed=T)[1] + 1
end = regexpr(".", filename[1], fixed=T)[1] - 1
fnum = substr(filename[1], start, end)

names = c(
"DE/rand/1", "DE/rand/2", "DE/rand/6", "DE/rand/∞", 
"DE/best/1", "DE/best/2", "DE/best/6", "DE/best/∞", 
"DE/mid/1", "DE/mid/2", "DE/mid/6", "DE/mid/∞")
	
png(paste("pngs/", fnum, ".png", sep = ""))

colors = c("black", "red", "green", "blue")

plot(ecdf(data[[1]]), verticals = TRUE, do.points = FALSE, col = colors[1], main = fnum, xlab = "najlepszy - minimum", ylab = "Prawdopodobieństwo", xlim = c(xmin, xmax), ylim = c(0, 1), lwd = 2)
for (i in 2:4)
	lines(ecdf(data[[i]]), verticals = TRUE, do.points = FALSE, col = colors[i], lwd = 2)
for (i in 5:8)
	lines(ecdf(data[[i]]), verticals = TRUE, do.points = FALSE, col = colors[i - 4], lty = 2, lwd = 2)
for (i in 9:12)
	lines(ecdf(data[[i]]), verticals = TRUE, do.points = FALSE, col = colors[i - 8], lty = 3, lwd = 2)
	
legend("bottomright", names, col = colors, bty="n", lty = c(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3), lwd = 2);
