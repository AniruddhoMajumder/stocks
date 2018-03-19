package controller.dao;

import java.sql.*;
import java.util.*;
import model.*;

public class CompanyDAO{

	private Connection dbConnection;

	private void connect() throws SQLException{
		if(dbConnection == null || dbConnection.isClosed()){
			dbConnection = DbConn.getConnection();
		}
	}

	private void disconnect() throws SQLException{
		if(dbConnection != null || !dbConnection.isClosed()){
			dbConnection.close();
			DbConn.killConnection();
		}
	}

	public boolean insert(Company newCompany, String user, String pass) throws SQLException{
		String insertQuery = "INSERT INTO Company VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		connect();
		PreparedStatement insertSTAT = dbConnection.prepareStatement(insertQuery);

		insertSTAT.setString(1, newCompany.getId());
		insertSTAT.setString(2, newCompany.getName());
		insertSTAT.setString(3, newCompany.getLicence());
		insertSTAT.setString(4, newCompany.getPhone());
		insertSTAT.setString(5, newCompany.getEmail());
		insertSTAT.setString(6, newCompany.getAddress());
		insertSTAT.setString(7, user);
		insertSTAT.setString(8, pass);

		boolean inserted = insertSTAT.executeUpdate()==0?false:true;

		disconnect();

		return inserted;
	}

	public String getNextId() throws SQLException{
		String newId = "cmp";

		String getQuery = "SELECT COUNT(*) FROM company";

		connect();
		Statement getIdSTAT = dbConnection.createStatement();
		ResultSet getIdRSET = getIdSTAT.executeQuery(getQuery);

		getIdRSET.next();
		String id = getIdRSET.getString(1);
		id = (Integer.parseInt(id) + 1) + "";

		for (int i=7; i>id.length(); i--) {
			newId += "0";
		}
		newId += id;

		disconnect();

		return newId;
	}

	public String getCompanyName(String CompanyId) throws SQLException{
		String queryString = "SELECT name FROM company WHERE id = '" + CompanyId + "'";

		connect();
		Statement getNameSTAT = dbConnection.createStatement();
		ResultSet getNameRSET = getNameSTAT.executeQuery(queryString);

		if(getNameRSET.next()){
			String ret = getNameRSET.getString(1);
			disconnect();
			return ret;
		}else{
			disconnect();
			return null;
		}
	}

	public Company getCompany(String CompanyId) throws SQLException{
		String queryString = "SELECT * FROM company WHERE id='" + CompanyId + "'";

		connect();
		Statement getCompanySTAT = dbConnection.createStatement();
		ResultSet getCompanyRSET = getCompanySTAT.executeQuery(queryString);

		if(getCompanyRSET.next()){
			List<String> values = new ArrayList<String>();

			values.add(getCompanyRSET.getString(1));
			values.add(getCompanyRSET.getString(2));
			values.add(getCompanyRSET.getString(3));
			values.add(getCompanyRSET.getString(4));
			values.add(getCompanyRSET.getString(5));
			values.add(getCompanyRSET.getString(6));

			Company ret = new Company(values);

			disconnect();
			return ret;
		}else{
			disconnect();
			return null;
		}
	}

   public List<String> getAvailableCompanies() throws SQLException{
      TransactionPartyDAO tpdao = new TransactionPartyDAO();
      List<String> ret = new ArrayList<String>();

      String queryString = "select c.id from company c, securities s where c.id = s.company_id and available > 0";

      connect();

      Statement getIdsSTAT = dbConnection.createStatement();
      ResultSet getIdsRSET = getIdsSTAT.executeQuery(queryString);

      while(getIdsRSET.next()){
         ret.add(getIdsRSET.getString(1));
      }

      disconnect();
      return ret;
   }

}
