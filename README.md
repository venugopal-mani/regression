# regression

To make file, just "make centroid" or "make regression" commands will work


For running centroid - java centroid input-file rlabel-file train-file test-file class-file clabel-file featurerepresentation-option outputfile


IMPORTANT: For running ridge - model selection is not performed by the program - all it does is for one value of lambda (the value of lambda  =1 was found to produce best results)


The command to run ridge is as follows - java regression input-file rlabel-file train-file test-file class-file clabel-file feature-representation output-file ridge-train-file validation-file

NOTE: Input files must be in the format <i,j,v> where i is the row (object), j is the column (feature), v is the value of that feature for that object

A sample data set can be found at the UCI machine learning repository or by using the data set provided in http://github.com/venugopal-mani/sphericalkmeans
