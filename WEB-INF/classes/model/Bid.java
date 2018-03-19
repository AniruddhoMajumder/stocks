package model;

public class Bid{
   private String traderId;
   private double amount;
   private int demand;

   public Bid(String TraderId, double BidAmount, int BidDemand){
      this.traderId = TraderId;
      this.amount = BidAmount;
      this.demand = BidDemand;
   }
}
