package controller.dao;

import java.sql.*;
import java.util.*;
import model.*;

public class SecurityDAO{

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

   public boolean insert(Security newSecurity) throws SQLException{
      String insertQuery = "INSERT INTO Securities VALUES(?, ?, ?, ?, ?, ?, ?)";

      connect();
      PreparedStatement insertSTAT = dbConnection.prepareStatement(insertQuery);

      insertSTAT.setString(1, newSecurity.getId());
      insertSTAT.setString(2, newSecurity.getCompanyId());
      insertSTAT.setString(3, newSecurity.getTypeId());
      insertSTAT.setString(4, Double.toString(newSecurity.getPrice()));
      insertSTAT.setString(5, Integer.toString(newSecurity.getRegistered()));
      insertSTAT.setString(6, Integer.toString(newSecurity.getIssued()));
      insertSTAT.setString(7, Integer.toString(newSecurity.getAvailable()));

      boolean inserted = insertSTAT.executeUpdate()==0?false:true;

      disconnect();

      return inserted;
   }

   public String getNextId() throws SQLException{
      String newId = "sec";

      String getQuery = "SELECT COUNT(*) FROM securities";

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

   public String getEquityShareID(String CompanyId) throws SQLException{
      String queryString = "SELECT id FROM securities WHERE company_id = '" + CompanyId + "' and type_id = 'eshr'";

      connect();

      Statement getPriceSTAT = dbConnection.createStatement();
      ResultSet getPriceRSET = getPriceSTAT.executeQuery(queryString);

      if(getPriceRSET.next()){
         String ret = getPriceRSET.getString(1);
         disconnect();
         return ret;
      }else{
         disconnect();
         return null;
      }
   }

   public double getEquitySharePrice(String CompanyId) throws SQLException{
      String queryString = "SELECT price FROM securities WHERE company_id = '" + CompanyId + "' and type_id = 'eshr'";

      connect();

      Statement getPriceSTAT = dbConnection.createStatement();
      ResultSet getPriceRSET = getPriceSTAT.executeQuery(queryString);

      if(getPriceRSET.next()){
         double ret = getPriceRSET.getDouble(1);
         disconnect();
         return ret;
      }else{
         disconnect();
         return 0;
      }
   }

   public void setEquitySharePrice(String CompanyId, double NewPrice) throws SQLException{
      String updateString = "UPDATE securities SET price = '" + NewPrice + "' WHERE company_id = '" + CompanyId + "' and type_id = 'eshr'";

      connect();

      Statement setPriceSTAT = dbConnection.createStatement();

      setPriceSTAT.executeUpdate(updateString);
   }

   public int getEquitySharesIssued(String CompanyId) throws SQLException{
      String queryString = "SELECT issued FROM securities WHERE company_id = '" + CompanyId + "' and type_id = 'eshr'";

      connect();

      Statement getIssuedSTAT = dbConnection.createStatement();
      ResultSet getIssuedRSET = getIssuedSTAT.executeQuery(queryString);

      if(getIssuedRSET.next()){
         int ret = getIssuedRSET.getInt(1);
         disconnect();
         return ret;
      }else{
         disconnect();
         return 0;
      }
   }

   public int getEquitySharesSold(String CompanyId) throws SQLException{
      String queryString = "SELECT number FROM transaction WHERE seller_id = (SELECT id FROM transaction_party WHERE company_id='" + CompanyId + "') and security_id = '" + getEquityShareID(CompanyId) + "'";

      connect();

      int sold = 0;

      Statement getSoldSTAT = dbConnection.createStatement();
      ResultSet getSoldRSET = getSoldSTAT.executeQuery(queryString);

      while(getSoldRSET.next()){
         sold += getSoldRSET.getInt(1);
      }

      disconnect();
      return sold;
   }

   public int getEquitySharesAvailable(String CompanyId) throws SQLException{
      String queryString = "SELECT available FROM securities where company_id = '" + CompanyId + "' and type_id = 'eshr'";

      connect();

      Statement getIssuedSTAT = dbConnection.createStatement();
      ResultSet getIssuedRSET = getIssuedSTAT.executeQuery(queryString);

      if(getIssuedRSET.next()){
         int ret = getIssuedRSET.getInt(1);
         disconnect();
         return ret;
      }else{
         disconnect();
         return 0;
      }
   }

}
