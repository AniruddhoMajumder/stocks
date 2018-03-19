<%
	String userType = "";

	if(null == request.getParameter("USER_TYPE")){
		response.sendRedirect("./index.jsp");
	}else{
		userType = request.getParameter("USER_TYPE");
		session.setAttribute("USER_TYPE", userType);
	}

%>

<!DOCTYPE html>
<html>
<head>
	<title>Stockmarket</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<style type="text/css">
		.modal-open {
			padding-right: 0px !important;
			overflow: auto !important;
		}
	</style>
</head>
<body>

	<nav class="navbar sticky-top navbar-dark bg-info" style="padding-right: 0px !important;">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="./index.jsp">Meridia</a>
			</div>
			<div class="form-inline">
				<button type="button" class="btn btn-success signup-close" style="margin: 0px 15px 0px 0px;" data-toggle="modal" data-target="#signup_modal">Sign Up</button>
				<button type="button" class="btn btn-primary login-close" style="margin: 0px 15px 0px 0px;" data-toggle="modal" data-target="#login_modal">Log In</button>
			</div>
		</div>
	</nav>

	<div class="container">

		<div class="row">
			<div class="col-12">
				<h2 style="text-align: center; margin: 15px; margin-top: 100px;">Congratualtions! Registration successful.</h2>
			</div>
		</div>

		<div class="row">
			<div class="col-3"></div>
			<div class="col-6" style="margin-top: 15px; border: solid 1px silver; border-radius: 5px; padding: 25px;">
				<form id="login" name="login" action="LoginController" method="post">
					<div class="row">
						<label class="control-label col-4">Username:</label>
						<div class="col-8">
							<input class="form-control" type="text" id="USERNAME" name="USERNAME" placeholder="Username"></input>
						</div>
					</div>
					<br>
					<div class="row">
						<label class="control-label col-sm-4">Password:</label>
						<div class="col-8">
							<input class="form-control" type="password" id="PASSWORD" name="PASSWORD" placeholder="Password"></input>
						</div>
					</div>
					<div class="row">
						<div class="col-8"></div>
						<div class="col-4" style="padding: 15px;">
							<input type="submit" class="form-control btn-outline-success btn-md login-close" value="Log In"></button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>