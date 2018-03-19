package model;

import java.sql.*;

public class Login{
	private String user;
	private String pass;

	public Login(String username, String password){
		this.user = username;
		this.pass = password;
	}

	public String authenticate(){
		try{
			Connection dbConnection = DbConn.getConnection();

			boolean loggedIn = false;

			Statement traderSTAT = dbConnection.createStatement();
			Statement companySTAT = dbConnection.createStatement();
			ResultSet traderRSET = traderSTAT.executeQuery("SELECT id FROM trader WHERE username = '" + this.user + "' and password = '" + this.pass + "'");
			ResultSet companyRSET = companySTAT.executeQuery("SELECT id FROM company WHERE username = '" + this.user + "' and password = '" + this.pass + "'");

			companyRSET.last();

			loggedIn = companyRSET.getRow()!=0;

			if(loggedIn){
				String id = companyRSET.getString(1);
				traderRSET.close();
				companyRSET.close();
				dbConnection.close();
				DbConn.killConnection();
				return id;
			}else{
				companyRSET.close();
				traderRSET.last();
				loggedIn = traderRSET.getRow()!=0;
			}

			if(loggedIn){
				String id = traderRSET.getString(1);
				traderRSET.close();
				dbConnection.close();
				DbConn.killConnection();
				return id;
			}else{
				dbConnection.close();
				DbConn.killConnection();
				return "INAVLID";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "INAVLID";
		}
	}
}