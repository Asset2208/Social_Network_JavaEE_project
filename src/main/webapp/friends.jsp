<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.*" %>
<%@ page import="classes.Post" %>
<%@ page import="java.util.*" %>
<%@ page import="classes.Friends" %>
<%@ page import="java.util.function.Function" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="classes.Friends_requests" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aralasu.kz</title>
    <%@include file="vendor/head.jsp" %>

    <link href="fontawesome-free-5.15.1-web/fontawesome-free-5.15.1-web/css/all.css" rel="stylesheet">

</head>
<body>
<%@include file="vendor/navbar.jsp" %>
<div class="container">
    <div class="row mt-5">
        <%
            if (!ONLINE) {
        %>
        <div class="col-sm-8 offset-3">
            <img src="https://img.icons8.com/wired/64/000000/a.png"/>
            <h1 class="display-5">
                We are Aralasu.kz
            </h1>

            <p>
                Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the
                industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and
                scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap
                into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the
                release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing
                software like Aldus PageMaker including versions of Lorem Ipsum.

            </p>
        </div>
        <%
        } else {
        %>
        <div class="col" style="margin: 0;">
            <img id="foo" src="<%=currentUser.getPicture_url()%>"
                 style="max-width: 285px; max-height: 350px; border-radius: 2%;" onerror="standby()">
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
                    <p style="font-weight: bold; margin: 0;"><%=currentUser.getFullName()%>, <%=period.getYears()%>
                        years </p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><a href="/" style="font-weight: bold;"><i
                            class="fas fa-address-card"></i> My Profile</a></li>
                    <li class="list-group-item"><a href="/profile" style="font-weight: bold;"><i
                            class="fas fa-cogs"></i> Settings</a></li>
                    <li class="list-group-item"><a href="/logout" style="font-weight: bold; color: darkred;"><i
                            class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="col-6">
            <div class="card mb-5">
                <div class="card-body">
                    <form class="form-inline mb-0" method="get" action="/friends">
                        <input type="text" class="form-control mr-sm-2 w-75" name="full_name"
                               placeholder="Search Friends">

                        <button type="submit" class="btn btn-outline-primary ml-auto"><i class="fas fa-search"></i>
                            Search
                        </button>
                    </form>
                </div>
            </div>


            <%
                ArrayList<User> friends_of_user = (ArrayList<User>) request.getAttribute("friends_of_user");
                String search_content = (String) request.getAttribute("search_content");
                ArrayList<Friends_requests> received_requests = (ArrayList<Friends_requests>)request.getAttribute("received_requests");
                ArrayList<User> received_users_requests = (ArrayList<User>)request.getAttribute("received_users_requests");

                if (received_users_requests != null && received_users_requests.size() != 0 && search_content == null){
            %>
            <div class="card mb-2">
                <div class="card-header">
                    <h5> You have <%=received_users_requests.size()%> new requests</h5>
                </div>

            <%
                    for (User user : received_users_requests) {
            %>
            <div class="card-body d-flex">
                <img src="<%=user.getPicture_url()%>" style="border-radius: 100%; max-height: 150px; max-width: 150px;">

                <div class="ml-5">
                    <a href="/user?id=<%=user.getId()%>&status=received_req"><h5 class="card-title"><%=user.getFullName()%>
                    </h5></a>
                    <%
                        String b_date = user.getBirthDate().toString();
                        SimpleDateFormat fr_formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date fr_date = fr_formatter.parse(b_date);
                        Instant fr_instant = fr_date.toInstant();
                        ZonedDateTime fr_zone = fr_instant.atZone(ZoneId.systemDefault());
                        LocalDate fr_givenDate = fr_zone.toLocalDate();
                        Period fr_period = Period.between(fr_givenDate, LocalDate.now());
                    %>
                    <p class="card-text mb-5" style="text-decoration: none;"><%=fr_period.getYears()%> years</p>

                    <div class="row" style="max-height: 40px;">
                        <form action="/sendresponsetofriend" method="post">
                            <input type="hidden" name="friend_id" value="<%=user.getId()%>">
                            <input type="hidden" name="response" value="Yes">
                            <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                    class="fas fa-plus-square"></i> Confirm
                            </button>
                        </form>
                        <form action="/sendresponsetofriend" method="post">
                            <input type="hidden" name="friend_id" value="<%=user.getId()%>">
                            <input type="hidden" name="response" value="NO">
                            <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                    class="fas fa-trash-alt"></i> Reject
                            </button>
                        </form>

                    </div>
                </div>


            </div>


            <%
                }
            %>
            </div>
            <hr class="my-4">
            <%
                }

                if (search_content != null) {
            %>
            <div class="card mb-2">
                <div class="card-body">
                    <h5>Search results for: "<%=search_content%>"</h5>
                </div>
            </div>
            <%
                ArrayList<User> search_users = (ArrayList<User>) request.getAttribute("search_users");
                ArrayList<Friends> friends = (ArrayList<Friends>) request.getAttribute("friends");
                ArrayList<Friends_requests> friends_requests = (ArrayList<Friends_requests>) request.getAttribute("friends_requests");
                if (search_users != null) {
                    for (User user : search_users) {
                        if (user.getId() != currentUser.getId()) {
                            Predicate<Friends> p1 = friends1 -> friends1.getFriend_id().equals(user.getId());
                            boolean is_friend = friends.stream().anyMatch(p1);
                            Predicate<Friends_requests> p2 = friends_requests1 -> friends_requests1.getUser_id().equals(user.getId());
                            boolean is_send_request = friends_requests.stream().anyMatch(p2);
                            Predicate<Friends_requests> p3 = friends_requests2 -> friends_requests2.getRequest_sender_id().equals(user.getId());
                            boolean is_received_request = received_requests.stream().anyMatch(p3);
                            String urlToUser = (is_friend ? "/friend?id=" + user.getId() : (!is_send_request ? (!is_received_request ? "/user?id="+user.getId()+"&status=addtofriend" : "/user?id="+user.getId()+"&status=received_req") : "/user?id="+user.getId()+"&status=req_sent"));
            %>
            <div class="card mb-2">
                <div class="card-body d-flex">
                    <a href="<%=urlToUser%>"><img src="<%=user.getPicture_url()%>"
                                                  style="border-radius: 100%; max-height: 150px; max-width: 150px;"></a>
                    <div class="ml-5">
                        <a href="<%=urlToUser%>"><h5 class="card-title"><%=user.getFullName()%>
                        </h5></a>
                        <%
                            String b_date = user.getBirthDate().toString();
                            SimpleDateFormat fr_formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date fr_date = fr_formatter.parse(b_date);
                            Instant fr_instant = fr_date.toInstant();
                            ZonedDateTime fr_zone = fr_instant.atZone(ZoneId.systemDefault());
                            LocalDate fr_givenDate = fr_zone.toLocalDate();
                            Period fr_period = Period.between(fr_givenDate, LocalDate.now());
                        %>
                        <p class="card-text mb-5" style="text-decoration: none;"><%=fr_period.getYears()%> years</p>

                        <%
                            if (is_friend) {
                        %>
                        <div class="row" style="max-height: 40px;">

                            <a href="#" class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                    class="fas fa-paper-plane"></i> Send Message </a>

                        </div>
                        <%
                        } else {
                            if (!is_send_request) {
                                if (!is_received_request) {
                        %>
                        <div class="row" style="max-height: 40px;">
                            <form action="/sendrequestfriend" method="post">
                                <input type="hidden" name="friend_id" value="<%=user.getId()%>">
                                <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                        class="fas fa-plus-square"></i> Add To Friends
                                </button>

                            </form>

                        </div>
                        <%
                        } else {
                        %>
                        <div class="row" style="max-height: 40px;">
                            <form action="/sendresponsetofriend" method="post">
                                <input type="hidden" name="friend_id" value="<%=user.getId()%>">
                                <input type="hidden" name="response" value="Yes">
                                <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                        class="fas fa-plus-square"></i> Confirm
                                </button>
                            </form>
                            <form action="/sendresponsetofriend" method="post">
                                <input type="hidden" name="friend_id" value="<%=user.getId()%>">
                                <input type="hidden" name="response" value="NO">
                                <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                        class="fas fa-trash-alt"></i> Reject
                                </button>
                            </form>

                        </div>
                        <%
                            }
                        } else {
                        %>
                        <div class="row" style="max-height: 40px;">
                            <%--                            <form action="#" method="post">--%>
                            <%--                                <input type="hidden" name="friend_id" value="<%=user.getId()%>">--%>
                            <button class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                    class="fas fa-check-circle"></i> Request Sent
                            </button>

                            <%--                            </form>--%>

                        </div>
                        <%
                                }
                            }
                        %>

                    </div>

                </div>
            </div>


            <%
                        }
                    }
                }
            } else if (friends_of_user != null) {
                for (User user : friends_of_user) {
            %>
            <div class="card mb-2">
                <div class="card-body d-flex">
                    <a href="/friend?id=<%=user.getId()%>"><img src="<%=user.getPicture_url()%>" style="border-radius: 100%; max-height: 150px; max-width: 150px;"></a>

                    <div class="ml-5">
                        <a href="/friend?id=<%=user.getId()%>"><h5 class="card-title"><%=user.getFullName()%></h5></a>

                        <%
                            String b_date = user.getBirthDate().toString();
                            SimpleDateFormat fr_formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date fr_date = fr_formatter.parse(b_date);
                            Instant fr_instant = fr_date.toInstant();
                            ZonedDateTime fr_zone = fr_instant.atZone(ZoneId.systemDefault());
                            LocalDate fr_givenDate = fr_zone.toLocalDate();
                            Period fr_period = Period.between(fr_givenDate, LocalDate.now());
                        %>
                        <p class="card-text"><%=fr_period.getYears()%> years</p>

                        <div class="row" style="max-height: 40px;">

                            <a href="#" class="btn btn-outline-primary ml-3 mr-2" style="max-height: 40px;"><i
                                    class="fas fa-paper-plane"></i> Send Message </a>

                        </div>

                    </div>

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
                    <a href="#" style="color: #17339B;">CHESS MASTERS</a> <br><br>
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
