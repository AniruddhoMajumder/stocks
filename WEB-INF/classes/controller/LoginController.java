package controller;

import model.*;
import controller.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginController extends HttpServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		try{
			String user = request.getParameter("USERNAME");
			String pass = request.getParameter("PASSWORD");

			Login userLogin = new Login(user, pass);

			String loginResponse = userLogin.authenticate();

			if(loginResponse.startsWith("cmp")){
				CompanyDAO cdao = new CompanyDAO();
				Company thisCompany = cdao.getCompany(loginResponse);
				String userType = "company";
				HttpSession session = request.getSession();

				session.setAttribute("USER_TYPE", userType);
				session.setAttribute("COMPANY", thisCompany);

				response.sendRedirect("./dashboard");
			}else if(loginResponse.startsWith("trd")){
				TraderDAO tdao = new TraderDAO();
				Trader thisTrader = tdao.getTrader(loginResponse);
				String userType = "trader";
				HttpSession session = request.getSession();

				session.setAttribute("USER_TYPE", userType);
				session.setAttribute("TRADER", thisTrader);

				response.sendRedirect("./dashboard");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}