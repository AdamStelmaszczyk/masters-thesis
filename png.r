#!/usr/bin/Rscript

filename1 = commandArgs(TRUE)[1]
filename2 = commandArgs(TRUE)[2]
filename3 = commandArgs(TRUE)[3]
filename4 = commandArgs(TRUE)[4]

data1 = scan(filename1, sep=" ")
data2 = scan(filename2, sep=" ")
if (!is.na(commandArgs(TRUE)[3])) 
	data3 = scan(filename3, sep=" ")
if (!is.na(commandArgs(TRUE)[4])) 
	data4 = scan(filename4, sep=" ")

start = regexpr("f", filename1, fixed=T)[1] + 1
end = regexpr(".", filename1, fixed=T)[1] - 1
f_num = substr(filename1, start, end)

name1 = substr(filename1, 0, regexpr("/", filename1, fixed=T)[1] - 1)
name2 = substr(filename2, 0, regexpr("/", filename2, fixed=T)[1] - 1)
if (!is.na(commandArgs(TRUE)[3])) 
	name3 = substr(filename3, 0, regexpr("/", filename3, fixed=T)[1] - 1)
if (!is.na(commandArgs(TRUE)[4])) 
	name4 = substr(filename4, 0, regexpr("/", filename4, fixed=T)[1] - 1)
	
names = c(name1, name2)
if (!is.na(commandArgs(TRUE)[3])) 
	names = c(names, name3)
if (!is.na(commandArgs(TRUE)[4])) 
	names = c(names, name4)

png(paste("pngs/", f_num, ".png", sep = ""))
colors = c("black", "blue", "red", "magenta")
plot(ecdf(data1), verticals = TRUE, do.points = FALSE, col = colors[1], main = f_num, xlab="Odległość od minimum", ylab="Prawdopodobieństwo")
lines(ecdf(data2), verticals = TRUE, do.points = FALSE, col = colors[2])
if (!is.na(commandArgs(TRUE)[3])) 
	lines(ecdf(data3), verticals = TRUE, do.points = FALSE, col = colors[3])
if (!is.na(commandArgs(TRUE)[4])) 
	lines(ecdf(data4), verticals = TRUE, do.points = FALSE, col = colors[4])
	
legend("bottomright", names, col = colors, lty = 1);
