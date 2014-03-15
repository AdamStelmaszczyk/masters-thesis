#!/usr/bin/Rscript

algorithm = "derand1"
outfile = paste("wilcox_test/", algorithm, sep = "")
cat(file = outfile)
for (dim in c(10, 20, 40, 80))
{
	basepath = paste("ready/", dim, "/", sep = "")
	infopath = paste(basepath, algorithm, "_", dim, "D", sep = "")
	for (file in dir(basepath))
	{
		filepath = paste(basepath, file, sep = "")
		if (file.info(filepath)$isdir)
		{
			name = sub("de", "DE/", file)
			name = sub("_.*", "", name)
			name = sub("(rand|mid|best)", "\\1/", name)
			name = sub("inf", "$\\\\infty$", name)
			cat(name, file = outfile, append = TRUE)
			cat("\t", file = outfile, append = TRUE)
			for (infofile in dir(infopath, pattern = "info"))
			{
				data1 = scan(paste(infopath, "/", infofile, sep = ""), quiet = TRUE)
				data2 = scan(paste(filepath, "/", infofile, sep = ""), quiet = TRUE)
				test = wilcox.test(data1, data2, exact = FALSE)
				cat(" & ", file = outfile, append = TRUE)
				if (test$p.value < 0.05)
				{
					if (median(data1) < median(data2))
						cat("+", file = outfile, append = TRUE)
					else
						cat("--", file = outfile, append = TRUE)
				} else 
					cat("$\\cdot$", file = outfile, append = TRUE)				
			}
			cat(" \\\\\n", file = outfile, append = TRUE)
		}
	}
	cat("\n", file = outfile, append = TRUE)
}
