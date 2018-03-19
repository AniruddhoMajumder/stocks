package controller;

import model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutController extends HttpServlet{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		session.invalidate();

		response.sendRedirect("index.jsp");

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();

		session.invalidate();

		response.sendRedirect("index.jsp");

	}
}