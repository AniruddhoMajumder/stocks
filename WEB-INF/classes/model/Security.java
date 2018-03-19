package model;

import java.util.*;

public class Security{

   private String id;
   private String companyId;
   private String typeId;

   private double price;

   private int registered;
   private int issued;
   private int available;

   public Security(List<Object> values){
      this.id = (String) values.get(0);
      this.companyId = (String) values.get(1);
      this.typeId = (String) values.get(2);

      this.price = (double) values.get(3);

      this.registered = (int) values.get(4);
      this.issued = (int) values.get(5);
      this.issued = (int) values.get(6);
   }

   public String getId(){
      return this.id;
   }

   public String getCompanyId(){
      return this.companyId;
   }

   public String getTypeId(){
      return this.typeId;
   }

   public double getPrice(){
      return this.price;
   }

   public int getRegistered(){
      return this.registered;
   }

   public int getIssued(){
      return this.issued;
   }

   public int getAvailable(){
      return this.available;
   }

}
