#!/usr/bin/Rscript

filename = commandArgs(TRUE)

data1 = scan(filename[1], sep=" ")
data2 = scan(filename[2], sep=" ")
if (!is.na(filename[3])) 
	data3 = scan(filename[3], sep=" ")
if (!is.na(filename[4])) 
	data4 = scan(filename[4], sep=" ")
if (!is.na(filename[5])) 
	data5 = scan(filename[5], sep=" ")
if (!is.na(filename[6])) 
	data6 = scan(filename[6], sep=" ")

start = regexpr("f", filename[1], fixed=T)[1] + 1
end = regexpr(".", filename[1], fixed=T)[1] - 1
f_num = substr(filename[1], start, end)

name1 = substr(filename[1], 0, regexpr("/", filename[1], fixed=T)[1] - 1)
name2 = substr(filename[2], 0, regexpr("/", filename[2], fixed=T)[1] - 1)
if (!is.na(filename[3])) 
	name3 = substr(filename[3], 0, regexpr("/", filename[3], fixed=T)[1] - 1)
if (!is.na(filename[4])) 
	name4 = substr(filename[4], 0, regexpr("/", filename[4], fixed=T)[1] - 1)
if (!is.na(filename[5])) 
	name5 = substr(filename[5], 0, regexpr("/", filename[5], fixed=T)[1] - 1)
if (!is.na(filename[6])) 
	name6 = substr(filename[6], 0, regexpr("/", filename[6], fixed=T)[1] - 1)
	
names = c(name1, name2)
if (!is.na(filename[3])) 
	names = c(names, name3)
if (!is.na(filename[4])) 
	names = c(names, name4)
if (!is.na(filename[5])) 
	names = c(names, name5)
if (!is.na(filename[6])) 
	names = c(names, name6)

png(paste("pngs/", f_num, ".png", sep = ""))
colors = c("black", "darkblue", "cyan", "darkred", "red", "magenta")
xmax = max(data1, data2, data3, data4, data5, data6)
plot(ecdf(data1), verticals = TRUE, do.points = FALSE, col = colors[1], main = f_num, xlab="best - target", ylab="Prawdopodobieństwo", xlim=c(0, xmax), ylim=c(0,1))
lines(ecdf(data2), verticals = TRUE, do.points = FALSE, col = colors[2])
if (!is.na(filename[3])) 
	lines(ecdf(data3), verticals = TRUE, do.points = FALSE, col = colors[3])
if (!is.na(filename[4])) 
	lines(ecdf(data4), verticals = TRUE, do.points = FALSE, col = colors[4])
if (!is.na(filename[5])) 
	lines(ecdf(data5), verticals = TRUE, do.points = FALSE, col = colors[5])
if (!is.na(filename[6])) 
	lines(ecdf(data6), verticals = TRUE, do.points = FALSE, col = colors[6])
	
legend("bottomright", names, col = colors, lty = 1);
