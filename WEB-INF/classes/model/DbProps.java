package model;

import java.util.*;
import java.io.*;

public class DbProps{
	public static String getDbUrl(){
		try{
			Properties props = new Properties();
			FileInputStream propFile = new FileInputStream(new File("C:\\apache-tomcat-8.5.15\\webapps\\trial\\WEB-INF\\classes\\dbProp.properties"));
			props.load(propFile);
			propFile.close();

			return props.getProperty("dburl");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}

	public static String getDbUser(){
		try{
			Properties props = new Properties();
			FileInputStream propFile = new FileInputStream(new File("C:\\apache-tomcat-8.5.15\\webapps\\trial\\WEB-INF\\classes\\dbProp.properties"));
			props.load(propFile);
			propFile.close();

			return props.getProperty("dbuser");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}

	public static String getDbPass(){
		try{
			Properties props = new Properties();
			FileInputStream propFile = new FileInputStream(new File("C:\\apache-tomcat-8.5.15\\webapps\\trial\\WEB-INF\\classes\\dbProp.properties"));
			props.load(propFile);
			propFile.close();

			return props.getProperty("dbpass");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}
}