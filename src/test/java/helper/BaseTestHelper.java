package helper;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import core.OutputLog;

public class BaseTestHelper {

	public static void createFolder(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdir();
		}else {
			OutputLog.info(">>> Folder already created");
		}
	}
	
	public static String currentDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMM YYYY");
		String formattedDate = currentDate.format(formatter);
		OutputLog.info(">>> Current Date : "+formattedDate);
		
		return formattedDate;
	}
	
	public static String timeStamp() {
		LocalDateTime currentTimeStamp = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMM yyyy HH:mm:ss");
		String formattedTimeStamp = currentTimeStamp.format(formatter).replace(":", "_").replace(" ", "_");
		OutputLog.info(">>> Current TimeStamp : "+formattedTimeStamp);
		
		return formattedTimeStamp;
	}
}
