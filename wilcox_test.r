#!/usr/bin/Rscript

dim = commandArgs(TRUE)[1]
basepath = paste("ready/", dim, "/", sep = "")
infopath = paste(basepath, "demid1_", dim, "D", sep = "")

for (file in dir(basepath))
{
	filepath = paste(basepath, file, sep = "")
	if (file.info(filepath)$isdir)
	{
		for (infofile in dir(infopath, pattern = "info"))
		{
			data1 = scan(paste(infopath, "/", infofile, sep = ""), quiet = TRUE)
			data2 = scan(paste(filepath, "/", infofile, sep = ""), quiet = TRUE)
			test = wilcox.test(data1, data2, exact = FALSE)
			if (test$p.value < 0.05)
			{
				if (mean(data1) < mean(data2))
					cat("+")
				else
					cat("-")
			} else 
				cat(".")
		}
		cat(" ")
		cat(file)
		cat("\n")
	}
}
