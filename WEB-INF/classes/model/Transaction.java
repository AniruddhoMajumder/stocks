package model;

import java.util.*;

public class Transaction{
	String id;
	String buyerId;
	String sellerId;
	String securityId;

	int number;
	double amount;
	Calendar tDatetime;

	public Transaction(List<Object> values){
		this.id = (String) values.get(0);
		this.buyerId = (String) values.get(1);
		this.sellerId = (String) values.get(2);
		this.securityId = (String) values.get(3);
		this.number = (int) values.get(4);
		this.amount = (double) values.get(5);
		this.tDatetime = (Calendar) values.get(6);
	}

	public String getId(){
		return this.id;
	}

	public String getBuyerId(){
		return this.buyerId;
	}

	public String getSellerId(){
		return this.sellerId;
	}

	public String getSecurityId(){
		return this.securityId;
	}

	public int getNumber(){
		return this.number;
	}

	public double getAmount(){
		return this.amount;
	}

	public Calendar getDateTime(){
		return this.tDatetime;
	}

}