#!/usr/bin/Rscript

for (dim in c(10, 20, 40, 80))
{
	basepath = paste("ready/", dim, "/", sep = "")
	infopath = paste(basepath, "demid1_", dim, "D", sep = "")
	outfile = paste("wilcox_test/", dim, sep = "")
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
						cat("+", file = outfile, append = TRUE)
					else
						cat("-", file = outfile, append = TRUE)
				} else 
					cat(".", file = outfile, append = TRUE)
			}
			cat(" ", file = outfile, append = TRUE)
			cat(file, file = outfile, append = TRUE)
			cat("\n", file = outfile, append = TRUE)
		}
	}
}
