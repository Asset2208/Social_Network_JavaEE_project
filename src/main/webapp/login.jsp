<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <%@include file="vendor/head.jsp"%>
    <link href="fontawesome-free-5.15.1-web/css/all.css" rel="stylesheet">
</head>
<body>
    <%@include file="vendor/navbar.jsp"%>
    <div class="container">
        <div class="row mt-5">
            <div class="col-sm-6 offset-3">
                <h4 class="mb-4 text-center" >
                    LOGIN TO SYSTEM
                </h4>
                <%
                    String passError = request.getParameter("passworderror");
                    if(passError != null){
                %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Incorrect password!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>
                <%
                    String emailError = request.getParameter("emailerror");
                    if(emailError != null){
                %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    User with this email is doesn't exists!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>
                <form action="/auth" method="post">
                    <div class="form-group">
                        <label>Email address</label>
                        <input type="email" required class="form-control" name="email">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" required class="form-control" name="password">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success">Sign In</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</body>
</html>
