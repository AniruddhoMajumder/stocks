package controller.dao;

import java.sql.*;
import java.util.*;
import model.*;

public class TraderDAO{

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

   public boolean insert(Trader newTrader, String user, String pass) throws SQLException{
      String insertQuery = "INSERT INTO Trader VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      connect();
      PreparedStatement insertSTAT = dbConnection.prepareStatement(insertQuery);

      String dob = newTrader.getDob().get(Calendar.YEAR) + "-"
      + ((newTrader.getDob().get(Calendar.MONTH)+1)<10?("0" + (newTrader.getDob().get(Calendar.MONTH)+1)):(newTrader.getDob().get(Calendar.MONTH)+1)) + "-"
      + ((newTrader.getDob().get(Calendar.DATE))<10?("0" + (newTrader.getDob().get(Calendar.DATE))):(newTrader.getDob().get(Calendar.DATE)));

      insertSTAT.setString(1, newTrader.getId());
      insertSTAT.setString(2, newTrader.getTitle());
      insertSTAT.setString(3, newTrader.getFname());
      insertSTAT.setString(4, newTrader.getMname());
      insertSTAT.setString(5, newTrader.getLname());
      insertSTAT.setString(6, String.valueOf(newTrader.getGender()));
      insertSTAT.setString(7, newTrader.getIdproof());
      insertSTAT.setString(8, dob);
      insertSTAT.setString(9, newTrader.getPhone());
      insertSTAT.setString(10, newTrader.getEmail());
      insertSTAT.setString(11, newTrader.getAddress());
      insertSTAT.setString(12, user);
      insertSTAT.setString(13, pass);

      boolean inserted = insertSTAT.executeUpdate()==0?false:true;

      disconnect();

      return inserted;
   }

   public String getNextId() throws SQLException{
      String newId = "trd";

      String getQuery = "SELECT COUNT(*) FROM trader";

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

   public double getRemainingBalance(String TraderId) throws SQLException{
      String queryString = "SELECT balance FROM transaction_party WHERE trader_id = '" + TraderId + "'";

      TransactionPartyDAO tpdao = new TransactionPartyDAO();
      connect();

      Statement balanceSTAT = dbConnection.createStatement();
      ResultSet balanceRSET = balanceSTAT.executeQuery(queryString);

      if(balanceRSET.next()){
         double ret = balanceRSET.getDouble(1);
         disconnect();
         return ret;
      }else{
         disconnect();
         return 0;
      }
   }

   public List<String> getInvestments(String TraderId){
      try{
         TransactionPartyDAO tpdao = new TransactionPartyDAO();

         String queryString = "SELECT DISTINCT seller_id FROM transaction WHERE buyer_id = '" + tpdao.getTraderTpid(TraderId) + "'";

         List<String> companyIds = new ArrayList<String>();
         connect();

         Statement getSellersSTAT = dbConnection.createStatement();
         ResultSet getSellersRSET = getSellersSTAT.executeQuery(queryString);

         while(getSellersRSET.next()){
            if(tpdao.isCompany(getSellersRSET.getString(1))){
               companyIds.add(tpdao.getRealId(getSellersRSET.getString(1)));
            }
         }

         disconnect();
         return companyIds;
      }catch(Exception e){
         System.out.println("Error here");
         e.printStackTrace();
         return null;
      }
   }

   public double getInvestedNo(String TraderId, String CompanyId) throws SQLException{
      TransactionPartyDAO tpdao = new TransactionPartyDAO();

      String queryString = "SELECT number FROM transaction WHERE buyer_id = '" + tpdao.getTraderTpid(TraderId) + "' and seller_id = '" + tpdao.getCompanyTpid(CompanyId) + "'";

      int ret = 0;
      connect();
      Statement boughtSTAT = dbConnection.createStatement();
      ResultSet boughtRSET = boughtSTAT.executeQuery(queryString);

      while(boughtRSET.next()){
         ret += boughtRSET.getInt(1);
      }
      disconnect();

      queryString = "SELECT t.number FROM transaction t, securities s WHERE s.id = t.security_id and s.company_id = '" + CompanyId +"' and t.seller_id = '" + tpdao.getTraderTpid(TraderId) + "'";

      connect();
      Statement soldSTAT = dbConnection.createStatement();
      ResultSet soldRSET = soldSTAT.executeQuery(queryString);

      while(soldRSET.next()){
         ret -= soldRSET.getInt(1);
      }
      disconnect();
      return ret;
   }

   public double getTotalInvestment(String TraderId) throws SQLException{
      TransactionPartyDAO tpdao = new TransactionPartyDAO();

      String queryString = "SELECT amount FROM transaction WHERE buyer_id = '" + tpdao.getTraderTpid( TraderId ) + "'";

      double ret = 0;
      connect();
      Statement boughtSTAT = dbConnection.createStatement();
      ResultSet boughtRSET = boughtSTAT.executeQuery(queryString);

      while(boughtRSET.next()){
         ret += boughtRSET.getDouble(1);
      }
      disconnect();

      queryString = "SELECT amount FROM transaction WHERE seller_id = '" + tpdao.getTraderTpid( TraderId ) + "'";

      connect();
      Statement soldSTAT = dbConnection.createStatement();
      ResultSet soldRSET = soldSTAT.executeQuery(queryString);

      while(soldRSET.next()){
         ret -= soldRSET.getInt(1);
      }
      disconnect();
      return ret;
   }

   public Trader getTrader(String TraderId) throws SQLException{
      String queryString = "SELECT * FROM trader WHERE id='" + TraderId + "'";

      connect();
      Statement getTraderSTAT = dbConnection.createStatement();
      ResultSet getTraderRSET = getTraderSTAT.executeQuery(queryString);

      if(getTraderRSET.next()){
         List<Object> values = new ArrayList<Object>();

         values.add(getTraderRSET.getString(1));
         values.add(getTraderRSET.getString(2));
         values.add(getTraderRSET.getString(3));
         values.add(getTraderRSET.getString(4));
         values.add(getTraderRSET.getString(5));
         values.add(getTraderRSET.getString(6).charAt(0));
         values.add(getTraderRSET.getString(7));

         String date = getTraderRSET.getString(8);

         Calendar dob = Calendar.getInstance();

         dob.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
         dob.set(Calendar.MONTH, (Integer.parseInt(date.substring(5, 7)) - 1));
         dob.set(Calendar.YEAR, Integer.parseInt(date.substring(8)));

         values.add(dob);

         values.add(getTraderRSET.getString(9));
         values.add(getTraderRSET.getString(10));
         values.add(getTraderRSET.getString(11));

         Trader ret = new Trader(values);

         disconnect();
         return ret;
      }else{
         disconnect();
         return null;
      }
   }

}
