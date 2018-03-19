package model;

import java.util.*;
import model.*;
import controller.dao.*;

public class Auction implements Runnable{
   public static int count;

   private List<Bid> bids;
   private int auctionId ;
   private double askingPrice;
   private int sellingShares;
   private String companyId;
   private String securityId;
   private int round;

   static{
      count = 0;
   }

   public Auction(String CompanyId, int NoOfShares){
      try{
         count++;

         SecurityDAO sdao = new SecurityDAO();

         this.companyId = CompanyId;
         this.securityId = sdao.getEquityShareID(this.companyId);
         this.sellingShares = NoOfShares;
         this.askingPrice = sdao.getEquitySharePrice(this.companyId);
         this.auctionId = count;
         this.bids = new ArrayList<Bid>();
         this.round = 0;

      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public void run(){
      try{
         if(this.bids.size()<=0){
            degenerate();
         }
      }catch(Exception e){
         e.printStackTrace();
      }
   }

   public int getAuctionId(){
      return this.auctionId;
   }

   private void degenerate(){
      this.askingPrice -= this.askingPrice * .01;
   }

   synchronized public boolean placeBid(int AuctionId, Bid NewBid){
      try{
         return false;
      }catch(Exception e){
         e.printStackTrace();
         return false;
      }
   }
}
