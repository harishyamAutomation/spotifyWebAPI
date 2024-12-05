package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyReader {

	//Method to read the property from file
	public static String getProperty(String key) {
		String propertyValue = null;
		
		//System.out.println("Current Working Directory [PropertyReader] : "+new File(".").getAbsolutePath());
		
		try {
			InputStream propertyFile = new FileInputStream("config.properties");
			
			Properties properties = new Properties();
			
			properties.load(propertyFile);
			
			propertyValue = properties.getProperty(key);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		
		return propertyValue;
	}
	
	//Method to set the property to file
	public static void setProperty(String key, String value) {
		
		//System.out.println("Current Working Directory [PropertyReader] : "+new File(".").getAbsolutePath());
		
		try {
			InputStream propertyFile = new FileInputStream("config.properties");
			
			Properties properties = new Properties();
			
			properties.load(propertyFile);
			
			properties.setProperty(key, value);
			
			OutputStream saveChanges = new FileOutputStream("config.properties");
			properties.store(saveChanges, "Access Token Value updated to : "+value);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
}
