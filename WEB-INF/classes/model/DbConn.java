package model;

import java.sql.*;

public class DbConn{
	private static Connection dbConn;

	static{
		dbConn = null;
	}

	public static Connection getConnection(){
		try{
			if(dbConn == null){
				Class.forName("com.mysql.jdbc.Driver");
				String url = DbProps.getDbUrl();
				String user = DbProps.getDbUser();
				String pass = DbProps.getDbPass();
				dbConn = DriverManager.getConnection(url, user, pass);

				return dbConn;
			}else {
				return dbConn;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void killConnection(){
		try{
			dbConn.close();
			dbConn = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}