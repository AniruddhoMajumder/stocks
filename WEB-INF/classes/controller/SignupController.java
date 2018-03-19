package controller;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;
import controller.*;
import controller.dao.*;


public class SignupController extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		if(null == session.getAttribute("USER_TYPE")){
			response.sendRedirect("index.jsp");
		}else{
			String type = (String) session.getAttribute("USER_TYPE");
			if(type.equals("company")){

				List<String> companyValues = new ArrayList<String>();
				List<Object> securityValues = new ArrayList<Object>();

				CompanyDAO cdao = new CompanyDAO();
				SecurityDAO sdao = new SecurityDAO();
				TransactionPartyDAO tpdao = new TransactionPartyDAO();

				String name = request.getParameter("NAME");
				String licence = request.getParameter("LICENCE");
				String phone = request.getParameter("PHONE");
				String email = request.getParameter("EMAIL");
				String address = request.getParameter("ADDRESS");
				int registered = Integer.parseInt(request.getParameter("REGISTERED"));
				int issued = Integer.parseInt(request.getParameter("ISSUED"));
				int available = issued;
				double price = Double.parseDouble(request.getParameter("PRICE"));
				String securityType = request.getParameter("TYPE");
				String user = request.getParameter("USERNAME");
				String pass = request.getParameter("PASSWORD");
				String companyId = "";
				String securityId = "";
				String transactionPartyId = "";

				try {
					companyId = cdao.getNextId();
					securityId = sdao.getNextId();
					transactionPartyId = tpdao.getNextId();
				}catch (Exception e) {
					e.printStackTrace();
				}

				companyValues.add(companyId);
				companyValues.add(name);
				companyValues.add(licence);
				companyValues.add(phone);
				companyValues.add(email);
				companyValues.add(address);

				securityValues.add(securityId);
				securityValues.add(companyId);
				securityValues.add(securityType);
				securityValues.add(price);
				securityValues.add(registered);
				securityValues.add(issued);
				securityValues.add(available);

				Company newCompany = new Company(companyValues);
				Security newSecurity = new Security(securityValues);
				TransactionParty newTransactionParty = new TransactionParty(transactionPartyId, companyId, TransactionParty.COMPANY, 0);

				try {
					boolean completion = false;

					completion = cdao.insert(newCompany, user, pass);
					completion = sdao.insert(newSecurity);
					completion = tpdao.insert(newTransactionParty);

					if(completion){
						response.sendRedirect("welcome.html");
					}else{
						response.sendRedirect("index.jsp");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}

			}else{

				List<Object> traderValues = new ArrayList<Object>();

				TraderDAO tdao = new TraderDAO();
				TransactionPartyDAO tpdao = new TransactionPartyDAO();

				String title = request.getParameter("TITLE");
				String fname = request.getParameter("FNAME");
				String mname = request.getParameter("MNAME")!=null?request.getParameter("MNAME"):"";
				String lname = request.getParameter("LNAME");
				String idproof = request.getParameter("IDPROOF");
				char gender = (request.getParameter("GENDER")).charAt(0);

				Calendar dob = Calendar.getInstance();
				int y = Integer.parseInt(request.getParameter("DOB").substring(0, 4));
				int m = Integer.parseInt(request.getParameter("DOB").substring(5, 7));
				int d = Integer.parseInt(request.getParameter("DOB").substring(8));
				dob.set(y, (m-1), d);

				String phone = request.getParameter("PHONE");
				String email = request.getParameter("EMAIL");
				String address = request.getParameter("ADDRESS");
				String user = request.getParameter("USERNAME");
				String pass = request.getParameter("PASSWORD");
				double balance = Double.parseDouble(request.getParameter("BALANCE"));
				String traderId = "";
				String transactionPartyId = "";

				try {
					traderId = tdao.getNextId();
					transactionPartyId = tpdao.getNextId();
				}catch (Exception e) {
					e.printStackTrace();
				}

				traderValues.add(traderId);
				traderValues.add(title);
				traderValues.add(fname);
				traderValues.add(mname);
				traderValues.add(lname);
				traderValues.add(gender);
				traderValues.add(idproof);
				traderValues.add(dob);
				traderValues.add(phone);
				traderValues.add(email);
				traderValues.add(address);

				Trader newTrader = new Trader(traderValues);
				TransactionParty newTransactionParty = new TransactionParty(transactionPartyId, traderId, TransactionParty.TRADER, balance);

				try {
					boolean completion = false;

					completion = tdao.insert(newTrader, user, pass);
					completion = tpdao.insert(newTransactionParty);

					if(completion){
						response.sendRedirect("welcome.html");
					}else{
						response.sendRedirect("index.jsp");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

}