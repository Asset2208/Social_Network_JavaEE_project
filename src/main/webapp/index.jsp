<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.*" %>
<%@ page import="classes.Post" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aralasu.kz</title>
    <%@include file="vendor/head.jsp"%>

    <link href="fontawesome-free-5.15.1-web/css/all.css" rel="stylesheet">

    <script src="tinymce_latest_custom/tinymce/tinymce.min.js"></script>
    <script>tinymce.init({ selector:'textarea' });</script>
    <style>
        .asdasd p { /* Запрещаем перенос строк */
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;

            /* Добавляем многоточие */
        }
    </style>
</head>
<body>
    <%@include file="vendor/navbar.jsp"%>
    <div class="container">
        <div class="row mt-5">
            <%
                if (!ONLINE){
            %>
            <div class="col-sm-10 offset-1">
                <center><i class="fab fa-asymmetrik fa-5x"></i>
<%--                <img src="https://img.icons8.com/wired/64/000000/a.png"/>--%>
                <h1 class="display-5">
                    We are Aralasu.kz
                </h1>

                <p>
                    Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.

                </p>
                </center>
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
                            <li class="list-group-item"><a href="/" style="font-weight: bold;"><i class="fas fa-address-card"></i> My Profile</a></li>
                            <li class="list-group-item"><a href="/profile" style="font-weight: bold;"><i class="fas fa-cogs"></i> Settings</a></li>
                            <li class="list-group-item"><a href="/logout" style="font-weight: bold; color: darkred;"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-6">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary mb-5" data-toggle="modal" data-target="#exampleModal">
                        <i class="fas fa-plus-square"></i> ADD NEW
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="/addpost" method="post">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Add New Post</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label>TITLE: </label>
                                            <input type="text" name="title" class="form-control">
                                            <input type="hidden" name="id" value="<%=currentUser.getId()%>">
                                        </div>
                                        <div class="form-group">
                                            <label>SHORT CONTENT: </label>
                                            <textarea name="short_content"  class="form-control"></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>CONTENT: </label>
                                            <textarea name="content" class="form-control"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button class="btn btn-primary">Add</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                    <%
                        ArrayList<Post> posts = (ArrayList<Post>)request.getAttribute("posts");
//                        Collections.reverse(posts);
                        if (posts != null){
                            for (Post post : posts){
                    %>
                        <div class="card mb-2">
                            <div class="card-body">
                                <h5 class="card-title"><%=post.getTitle()%></h5>
                                <div class="asdasd">
                                    <p class="card-text"><%=post.getShort_content()%></p>
                                </div>
                                <a href="post?id=<%=post.getId()%>" class="btn btn-outline-primary">More <i class="fas fa-arrow-circle-right"></i></a>
                            </div>
                            <div class="card-footer text-muted">
                                <p class="text-muted">Posted on <%=post.getPost_date()%> by <span style="color:#17339B; font-weight: bold;"><%=post.getUser().getFullName()%></span></p>
                            </div>
                        </div>
                    <%
                            }
                        }
                    %>
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
