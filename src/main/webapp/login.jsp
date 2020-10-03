<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <%@include file="vendor/head.jsp"%>
</head>
<body>
    <%@include file="vendor/navbar.jsp"%>
    <div class="container">
        <div class="row mt-5">
            <div class="col-sm-6 offset-3">
                <h4 class="mb-4 text-center" >
                    LOGIN TO SYSTEM
                </h4>
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
