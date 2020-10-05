<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <%@include file="vendor/head.jsp"%>
</head>
<body>
    <%@include file="vendor/navbar.jsp"%>
    <div class="container">
        <div class="row mt-5">
            <div class="col-sm-6 offset-3">
                <h4 class="mb-4 text-center" >
                    REGISTER TO SYSTEM
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
                    User with this email is already exists!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>
                <%
                    String success = request.getParameter("success");
                    if(success != null){
                %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    User added successfully!
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>
                <form action="/toregistrate" method="post">
                    <div class="form-group">
                        <label>Email address</label>
                        <input type="email" required class="form-control" name="email">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" required class="form-control" name="password">
                    </div>
                    <div class="form-group">
                        <label>Confirm password</label>
                        <input type="password" required class="form-control" name="re_password">
                    </div>
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" required class="form-control" name="full_name">
                    </div>
                    <div class="form-group">
                        <label>Birthdate</label>
                        <input type="date" required class="form-control" name="birthdate">
                    </div>
                    <div class="form-group">
                        <label>Picture URL</label>
                        <input type="text" class="form-control" name="picture_url">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-success">Sign Up</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
