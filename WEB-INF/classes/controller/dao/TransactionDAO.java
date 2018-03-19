package controller.dao;

import java.sql.*;
import java.util.*;
import model.*;

public class TransactionDAO{

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

   public boolean buy(String BuyerId, String SellerId, String SecurityId, String Snumber, String Sprice) throws SQLException{
      String insertQuery = "INSERT INTO Transaction VALUES(?, ?, ?, ?, ?, ?, now())";

      double price = Double.parseDouble(Sprice);
      int number= Integer.parseInt(Snumber);
      double amount = number * price;

      connect();
      PreparedStatement insertSTAT = dbConnection.prepareStatement(insertQuery);

      insertSTAT.setString(1, getNextId());
      insertSTAT.setString(2, BuyerId);
      insertSTAT.setString(3, SellerId);
      insertSTAT.setString(4, SecurityId);
      insertSTAT.setString(5, Integer.toString(number));
      insertSTAT.setString(6, Double.toString(amount));

      boolean inserted = insertSTAT.executeUpdate()==0?false:true;

      disconnect();

      return inserted;
   }

   public String getNextId() throws SQLException{
      String newId = "txn";

      String getQuery = "SELECT COUNT(*) FROM transaction";

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

}
