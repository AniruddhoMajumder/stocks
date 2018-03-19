package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;
import controller.*;
import controller.dao.*;


public class BuyController extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		TransactionDAO tdao = new TransactionDAO();

		try{
			String buyerid[] = request.getParameterValues("BUYERID"),
			 sellerid[] = request.getParameterValues("SELLERID"),
			 securityid[] = request.getParameterValues("securityID"),
			 number[] = request.getParameterValues("NUMBER"),
			 price[] = request.getParameterValues("PRICE");

			int i=0, j=0, k=0, l=0, m=0;
			for(; buyerid[i]!=null; i++);
			for(; sellerid[j]!=null; j++);
			for(; securityid[k]!=null; k++);
			for(; number[l]!=null; l++);
			for(; price[m]!=null; m++);

			System.out.println(buyerid[i]);
			System.out.println(sellerid[j]);
			System.out.println(securityid[k]);
			System.out.println(number[l]);
			System.out.println(price[m]);

			tdao.buy(buyerid[i], sellerid[j], securityid[k], number[l], price[m]);
		}catch(Exception e){
			e.printStackTrace();
		}

		response.sendRedirect("dashboard/");
	}
}