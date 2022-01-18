to run the executable jar go to dist folder and run below command 

java -jar TestUpload.jar D:\Users\mallik\Desktop\DACOGEN_09DEC2016.xls

note: don't forget to replace the excel file path with your own.

to build the new jar again : 

go to TestUpload.java file and comment 

	// private static final String excelFilePath =
	// "D:\\Users\\mallik\\Desktop\\DACOGEN_09DEC2016.xls";
	
	and replace the  excelFilePath with args[0] and right click on build.xml file in ecplise and run as ant build will create the new jar file. 
	
	

