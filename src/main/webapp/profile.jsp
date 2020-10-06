<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aralasu.kz</title>
    <%@include file="vendor/head.jsp"%>
</head>
<body>
<%@include file="vendor/navbar.jsp"%>
<div class="container">
    <div class="row mt-5">
        <%
            if (!ONLINE){
        %>
        <div class="col-sm-8 offset-3">
            <img src="https://img.icons8.com/wired/64/000000/a.png"/>
            <h1 class="display-5">
                We are Aralasu.kz
            </h1>

            <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.

            </p>
        </div>
        <%
        }
        else {
        %>
        <div class="col" style="margin: 0;">

            <img id="foo" src="<%=currentUser.getPicture_url()%>" style="max-width: 285px; max-height: 350px; border-radius: 2%;" onerror="standby()">
            <script>
                function standby() {
                    document.getElementById('foo').src = 'https://karateinthewoodlands.com/wp-content/uploads/2017/09/default-user-image.png'
                }
            </script>
            <div class="card mt-2">
                <div class="card-header">
                    <%
                        String bdate = currentUser.getBirthDate().toString();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse(bdate);
                        Instant instant = date.toInstant();
                        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
                        LocalDate givenDate = zone.toLocalDate();
                        Period period = Period.between(givenDate, LocalDate.now());
                    %>
                    <p style="font-weight: bold; margin: 0;"> <%=currentUser.getFullName()%>, <%=period.getYears()%> years </p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a href="/" style="font-weight: bold;">My Profile</a></li>
                    <li class="list-group-item"><a href="/profile" style="font-weight: bold;">Settings</a></li>
                    <li class="list-group-item"><a href="/logout" style="font-weight: bold; color: darkred;">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="col-6">
            <h4 class="mb-4" >
                Edit Profile
            </h4>
            <%
                String emailError = request.getParameter("emailerror");
                if(emailError != null){
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                User with this email already exists!
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
                Edited successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <form action="/editprofile" method="post">
                <div class="form-group">
                    <label>Email address</label>
                    <input type="email" required class="form-control" name="email" value="<%=currentUser.getEmail()%>">
                    <small class="form-text text-muted">Change your email</small>
                </div>
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" required class="form-control" name="full_name" value="<%=currentUser.getFullName()%>">
                    <small class="form-text text-muted">Change your full name</small>
                </div>
                <div class="form-group">
                    <label>Birthdate</label>
                    <input type="date" required class="form-control" name="birthdate" value="<%=currentUser.getBirthDate()%>">
                    <small class="form-text text-muted">Change your birthdate</small>
                </div>
                <div class="form-group">
                    <button class="btn btn-success" style="background-color: #17339B; border: none;">Update Profile</button>
                </div>
            </form>

            <hr class="my-4">

            <h4 class="mb-4" >
                Edit Picture
            </h4>
            <%
                String urlerror = request.getParameter("urlerror");
                if(urlerror != null){
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                Enter correct url!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String urlsuccess = request.getParameter("urlsuccess");
                if(urlsuccess != null){
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Edited successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <form action="/editpicture" method="post">
                <div class="form-group">
                    <label>Picture URL</label>
                    <input type="text" required class="form-control" name="picture_url" value="<%=currentUser.getPicture_url()%>">
                </div>
                <div class="form-group">
                    <button class="btn btn-success" style="background-color: #17339B; border: none;">Update Picture</button>
                </div>
            </form>

            <hr class="my-4">

            <h4 class="mb-4" >
                Update Password
            </h4>
            <%
                String oldpasserror = request.getParameter("oldpasserror");
                if(oldpasserror != null){
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                Incorrect old password!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String diffpasserror = request.getParameter("diffpasserror");
                if(diffpasserror != null){
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                New passwords are not equal!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String successpassword = request.getParameter("successpassword");
                if(successpassword != null){
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Edited successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <form action="/editpassword" method="post">
                <div class="form-group">
                    <label>Old Password</label>
                    <input type="password" required class="form-control" name="old_password">
                </div>
                <div class="form-group">
                    <label>New Password</label>
                    <input type="password" required class="form-control" name="new_password">
                </div>
                <div class="form-group">
                    <label>Confirm Password</label>
                    <input type="password" required class="form-control" name="re_new_password">
                </div>
                <div class="form-group">
                    <button class="btn btn-success" style="background-color: #17339B; border: none;">Update Password</button>
                </div>
            </form>
        </div>
        <div class="col">
            <div class="card mb-5">
                <div class="card-header text-white bg-primary" style="font-weight: bold;">
                    Latest birthdays
                </div>
                <div class="card-body" style="font-size: 12px;">
                    <p>Almas Almasov, tomorrow</p>
                    <p>Zhandos Altynbek, 10 October</p>
                    <p>Shyngys Gani, 13 October</p>
                    <p>Diyas Talgatov, 01 November</p>
                </div>
            </div>
            <div class="card">
                <div class="card-header text-white bg-primary" style="font-weight: bold;">
                    My Games
                </div>
                <div class="card-body" style="font-size: 14px; font-weight: bold; color: #17339B;">
                <a href="#" style="color: #17339B;">FOOTBALL ONLINE</a> <br><br>
                <a href="#" style="color: #17339B;">PING-PONG ONLINE</a> <br><br>
                <a href="#" style="color: #17339B;">CHESS MASTERS</a>  <br><br>
                <a href="#" style="color: #17339B;">RACES ONLINE</a>
            </div>
        </div>
    </div>
    <%
        }
    %>
</div>
</div>
</body>
</html>
