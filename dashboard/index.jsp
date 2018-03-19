<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="controller.*"%>
<%@ page import="controller.dao.*"%>
<%@ page import="model.*"%>
<%if(session.getAttribute("USER_TYPE") == null || session.getAttribute("USER_TYPE").equals("")){
	response.sendRedirect("../");
}else if (session.getAttribute("USER_TYPE").equals("company")){

SecurityDAO sdao = new SecurityDAO();

session.setAttribute("SHARE_PRICE", sdao.getEquitySharePrice(( (Company) session.getAttribute("COMPANY")).getId() ));
session.setAttribute("SHARES_ISSUED", sdao.getEquitySharesIssued(( (Company) session.getAttribute("COMPANY")).getId() ));
session.setAttribute("SHARES_SOLD", sdao.getEquitySharesSold(( (Company) session.getAttribute("COMPANY")).getId() ));

	%>
<!DOCTYPE html>
<html>
<head>
	<title>Dashboard</title>

	<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
</head>

<body>

<nav class="navbar sticky-top navbar-dark bg-info" style="padding-right: 0px !important;">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="./index.jsp">Meridia</a>
		</div>
		<div class="form-inline">
			<button id="LOGOUT" type="button" class="btn btn-primary" style="margin: 0px 15px 0px 0px;">Log Out</button>
		</div>
	</div>
</nav>

<div class="container">
	<div class="container-fluid" style="color: rgb(249,89,42); margin-top:15px; padding: 15px; border-radius: 5px; border: solid 1px silver;">
		<div class="row">
			<div class="col-3">
				<p style="font-size: 50px;"><% out.println(((Company)session.getAttribute("COMPANY")).getName()); %></p>
				<p style="font-size: 20px;"><% out.println(((Company)session.getAttribute("COMPANY")).getLicence()); %></p>
			</div>
			<div class="col-6">
				<button class="btn btn-warning" type="button" id="SELL" data-toggle="modal" data-target="#sell_modal"><h1>Sell</h1></button>
			</div>
			<div class="col-3" style="text-align: right;">
				<p>Price (per share): Rs. </p>
				<p style="font-size: 40px;"><% out.println(session.getAttribute("SHARE_PRICE")); %></p>
			</div>
		</div>

		<hr>

		<div class="row">
			<div class="col-3">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<h1>Shares</h1>
						</div>
						<div class="col-6" style="margin-top: 30px;">
							<h4>Issued:</h4>
						</div>
						<div class="col-6" style="margin-top: 30px;">
							<h4><% out.println(session.getAttribute("SHARES_ISSUED")); %></h4>
						</div>
						<div class="col-6" style="margin-top: 30px;">
							<h4>Sold:</h4>
						</div>
						<div class="col-6" style="margin-top: 30px;">
							<h4><% out.println(session.getAttribute("SHARES_SOLD")); %></h4>
						</div>
					</div>
				</div>
			</div>
			<div class="col-9">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12" style="text-align: right;">
							<h1>Trend</h1>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


</body>

</html>

<script src="../js/jquery-3.2.1.js"></script>
<script src="../js/popper.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script>
    $('#LOGOUT').click(function(){
    	location.href ='../LogoutController';
    });
</script>
<div class="modal" id="sell_modal" tabindex="-1" role="dialog" aria-labelledby="sell_modalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content ">
			<div class="modal-header">
				<h5 class="modal-title" id="sell_modalLabel">Sell Shares</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<div class="container" style="width: inherit;">
					<div class="row">
						<div class="col-sm-12">
							<form id="sale" name="sale" action="beginAuction.jsp" method="post">
								<div class="row">
									<%
										int av = sdao.getEquitySharesAvailable( ((Company) session.getAttribute("COMPANY")).getId());
									%>
									<label class="control-label col-4">No. of Shares:</label>
									<div class="col-8">
										<input class="form-control" type="number" id="SHARENO" name="SHARENO" placeholder="No of shares to sell"></input>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="submit" class="btn btn-outline-success btn-md" value="Sell"></button>
				<!--button type="button" class="btn btn-primary">Save changes</button-->
				</form>
			</div>
		</div>
	</div>
</div>
<%
	String printJQuery = "<script>$(\"#SHARENO\").blur(function(){if( ($(\"#SHARENO\").val())>";
	printJQuery += av;
	printJQuery += "){$(\"#SHARENO\").val(";
	printJQuery += av;
	printJQuery += ");}else if( ($(\"#SHARENO\").val())<1 ){$(\"#SHARENO\").val(1);}});$(\"#SHARENO\").keyup(function(){if( ($(\"#SHARENO\").val())>";
	printJQuery += av;
	printJQuery += "){$(\"#SHARENO\").val(";
	printJQuery += av;
	printJQuery += ");}else if( ($(\"#SHARENO\").val())<1 ){$(\"#SHARENO\").val(1);}});</script>";

	out.println(printJQuery);
%>






















<%
	}else if(session.getAttribute("USER_TYPE").equals("trader")){

		TraderDAO tdao = new TraderDAO();
		CompanyDAO cdao = new CompanyDAO();
		SecurityDAO sdao = new SecurityDAO();
		TransactionPartyDAO tpdao = new TransactionPartyDAO();
%>
<!DOCTYPE html>
<html>
<head>
	<title>Dashboard</title>

	<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
</head>

<body>
<nav class="navbar sticky-top navbar-dark bg-info" style="padding-right: 0px !important;">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="./index.jsp">Meridia</a>
		</div>
		<div class="form-inline">
			<button id="LOGOUT" type="button" class="btn btn-primary" style="margin: 0px 15px 0px 0px;">Log Out</button>
		</div>
	</div>
</nav>

<div class="container-fluid">
	<div class="row" style="margin: 20px;">
		<div class="col-3" style="overflow: auto; max-height: 85vh;">
			<%
				List<String> availableCompanies = cdao.getAvailableCompanies();
				int formIndex = 0;

				for(String id : availableCompanies){
			%>

					<div class="row" style="margin-bottom : 15px;">
						<div class="col-12">
							<div class="card w-100 p-2 text-left">
								<div class="card-block row">
									<div class="col-6">
										<h4 class="card-title" style="padding-left: 21px;"><% out.print(cdao.getCompanyName(id)); %></h4>
									</div>
									<div class="col-6">
										<h6 class="card-title" style="padding-right: 21px; text-align: center;"><% out.print(sdao.getEquitySharePrice(id)); %></h6>
									</div>
								</div>
								<div class="card-block row">
									<div class="col-6">
										<h6 class="card-title" style="padding-left: 21px;">Available</h6>
									</div>
									<div class="col-6">
										<h6 class="card-title" style="padding-right: 21px; text-align: center;"><% out.print(sdao.getEquitySharesAvailable(id)); %></h6>
									</div>
								</div>
								<div class="card-header">
									<%out.println("<form class=\"buyformclass\" name=\"buyform" + formIndex + "\" id=\"buyform\" action=\"../BuyController\" method=\"POST\">");%>
									
										<div class="row">
											<div class="col-8">
												<input type="number" name="NUMBER" id="NUMBER" class="form-control" placeholder="Enter No." required>
											</div>
											<div class="col-4">
												<input type="submit" class="form-control btn btn-success" name="Buy" value="Buy"></form>
											</div>
										</div>
										<%
											out.println("<input type=\"hidden\" name=\"BUYERID\" value=\""
											+ tpdao.getTraderTpid(((Trader) session.getAttribute("TRADER")).getId())
											+ "\">");

											out.println("<input type=\"hidden\" name=\"SELLERID\" id=\"SELLERID\" value=\""
											+ tpdao.getCompanyTpid(id)
											+ "\">");

											out.println("<input type=\"hidden\" name=\"SECURITYID\" id=\"SECURITYID\" value=\""
											+ sdao.getEquityShareID(id)
											+ "\">");

											out.println("<input type=\"hidden\" name=\"PRICE\" id=\"PRICE\" value=\""
											+ sdao.getEquitySharePrice(id)
											+ "\">");											
										%>
									</form>
								</div>
							</div>
						</div>
					</div>

			<%
				}
			%>
			</div>
		


	
		<div class="col-9">
			<div class="card h-100" style="background: rgba(255, 105, 51,0.8);">
				<div class="dropdown">
					<button type="button" class="btn btn-dark float-right dropdown-toggle" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Profile</button>

					<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
						<h5 class="dropdown-header">
							<%
								String name = "";
								name += ((Trader) session.getAttribute("TRADER")).getTitle();
								name += " ";
								name += ((Trader) session.getAttribute("TRADER")).getFname();
								if(((Trader) session.getAttribute("TRADER")).getMname().length()>0){
								name += ((Trader) session.getAttribute("TRADER")).getMname() + " ";
								}else{
								name += " ";
								}
								name += ((Trader) session.getAttribute("TRADER")).getLname();
								out.print(name);
							%>
						</h5>
						<h5 class="dropdown-header"><% out.print(((Trader) session.getAttribute("TRADER")).getIdproof()); %></h5>
						
						<div class="dropdown-divider"></div>
						
						<a class="dropdown-item" href="../">Home</a>
						
						<div class="dropdown-divider"></div>
						
						<a class="dropdown-item" href="../logout.jsp">Log Out</a>

					</div>
				</div>
				<hr class="my-4">

				<div class="container">
					<div class="row">

						<div class="col-8">
						
							<div class="card" style="padding: 10px; background: rgba(128, 0, 0,0.2);">
								<div class="row">
									<div class="col-6">
										<div class="card bg-light card-outline-dark" style="margin-bottom: 10px;">
											<h3 class="card-header">ABC</h3>
											<div class="card-block">
												<table class="table table-hover table-dark">
													<tbody>
													<tr>
														<td>Shares</td>
														<td>20</td>
													</tr>
													<tr>
														<td>Price</td>
														<td>Rs. 10</td>
													</tr>
													<tr>
														<td>Total price</td>
														<td>Rs. 200</td>
													</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="col-4">
							<div class="row">
								<div class="col-12">
									<div class="card text-center" style="margin-bottom: 10px; padding: 5px;" >
										<div class="card-block">
											<h4 class="card-title">Amount Invested</h4>
											<hr class="my-4">
											<p class="card-text">Rs. <% out.print(tdao.getTotalInvestment( ((Trader) session.getAttribute("TRADER")).getId() )); %></p>
										</div>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-12">
									<div class="card text-center" style="margin-bottom: 10px; padding: 5px;">
										<div class="card-block">
											<h4 class="card-title">Amount Remaining</h4>
											<hr class="my-4">
											<p class="card-text">Rs. <% out.print(tdao.getRemainingBalance(((Trader) session.getAttribute("TRADER")).getId())); %></p>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>

</html>

<script src="../js/jquery-3.2.1.js"></script>
<script src="../js/popper.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script type="text/javascript">
	document.getElementById("LOGOUT").onclick = function () {
		location.href = "../logout.jsp";
	};
</script>
<%}%>
