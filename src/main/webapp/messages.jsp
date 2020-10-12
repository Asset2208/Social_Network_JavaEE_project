<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.function.Function" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="classes.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aralasu.kz</title>
    <%@include file="vendor/head.jsp" %>

    <link href="fontawesome-free-5.15.1-web/fontawesome-free-5.15.1-web/css/all.css" rel="stylesheet">
    <style>
        .asdasd p { /* Запрещаем перенос строк */
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;

            /* Добавляем многоточие */
        }
    </style>
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
                    <form class="form-inline mb-0" method="get" action="/messages">
                        <input type="text" class="form-control mr-sm-2 w-75" name="message"
                               placeholder="Search Messages">

                        <button type="submit" class="btn btn-outline-primary ml-auto"><i class="fas fa-search"></i>
                            Search
                        </button>
                    </form>
                </div>
            </div>

            <%
                ArrayList<Chats> chats = (ArrayList<Chats>) request.getAttribute("chats");
                if (chats != null) {
                    Collections.sort(chats, Comparator.comparing(Chats::getLatest_message_time).reversed());
                    for (Chats chat : chats) {
            %>
            <div class="card mb-2">
                <div class="card-body d-flex">
                    <%
                        if (chat.getUser().getId().equals(currentUser.getId())) {
                    %>
                    <div style="min-width: 75px;"><img src="<%=chat.getOpponent_user().getPicture_url()%>"
                                                       style="border-radius: 100%; max-height: 75px; max-width: 75px;">
                    </div>

                    <div class="ml-5">
                        <%
                            if (chat.getLatest_message_user().getId().equals(chat.getOpponent_user().getId()) && !chat.isRead_by_receiver()) {
                        %>
                        <a href="/privatemessage?id=<%=chat.getId()%>"><h5 class="card-title"
                                                                           style="font-weight: bold;">
                            ***<%=chat.getOpponent_user().getFullName()%>***
                        </h5></a>
                        <%
                        } else{
                        %>
                        <a href="/privatemessage?id=<%=chat.getId()%>"><h5
                                class="card-title"><%=chat.getOpponent_user().getFullName()%>
                        </h5></a>
                        <%
                            }
                        %>

                        <%
                            if (chat.getLatest_message_user().getId().equals(currentUser.getId())) {
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px;"><span
                                    style="font-weight: bold; color: #3A3A3A;">You:</span> <%=chat.getLatest_message_text()%>
                            </p>
                        </div>
                        <%
                        } else {
                            if (chat.isRead_by_receiver() == false) {
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px; font-weight: bold;"><%=chat.getLatest_message_text()%>
                            </p>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px;"><%=chat.getLatest_message_text()%>
                            </p>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>

                    <%
                        if (chat.getLatest_message_user().getId().equals(currentUser.getId())) {
                    %>
                    <p class="ml-auto"><%=chat.getLatest_message_time()%>
                    </p>
                    <%
                    } else {
                        if (chat.isRead_by_receiver() == false) {
                    %>
                    <p class="ml-auto" style="font-weight: bold;"><%=chat.getLatest_message_time()%>
                    </p>
                    <%
                    } else {
                    %>
                    <p class="ml-auto"><%=chat.getLatest_message_time()%>
                    </p>

                    <%
                            }
                        }
                    %>

                    <%
                        }
                            else{
                    %>
                    <div style="min-width: 75px;">
                        <img src="<%=chat.getUser().getPicture_url()%>"
                             style="border-radius: 100%; max-height: 75px; max-width: 75px;">
                    </div>

                    <div class="ml-5">
                        <%
                            if (chat.getLatest_message_user().getId().equals(chat.getUser().getId()) && !chat.isRead_by_receiver()) {
                        %>
                        <a href="/privatemessage?id=<%=chat.getId()%>"><h5 class="card-title"
                                                                           style="font-weight: bold;">
                            ***<%=chat.getUser().getFullName()%>***
                        </h5></a>
                        <%
                        } else{
                        %>
                        <a href="/privatemessage?id=<%=chat.getId()%>"><h5
                                class="card-title"><%=chat.getUser().getFullName()%>
                        </h5></a>
                        <%
                            }
                        %>
<%--                        <a href="/privatemessage?id=<%=chat.getId()%>"><h5--%>
<%--                                class="card-title"><%=chat.getUser().getFullName()%>--%>
<%--                        </h5></a>--%>
                        <%
                            if (chat.getLatest_message_user().getId().equals(currentUser.getId())) {
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px;"><span
                                    style="font-weight: bold; color: #3A3A3A;">You:</span> <%=chat.getLatest_message_text()%>
                            </p>
                        </div>
                        <%
                        } else {
                            if (chat.isRead_by_receiver() == false){
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px; font-weight: bold;"><%=chat.getLatest_message_text()%>
                            </p>
                        </div>

                        <%
                                }
                            else {
                        %>
                        <div class="asdasd">
                            <p style="max-width: 222px;"><%=chat.getLatest_message_text()%>
                            </p>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>

                    <%
                        if (chat.getLatest_message_user().getId().equals(currentUser.getId())) {
                    %>
                    <p class="ml-auto"><%=chat.getLatest_message_time()%>
                    </p>
                    <%
                    } else {
                        if (chat.isRead_by_receiver() == false) {
                    %>
                    <p class="ml-auto" style="font-weight: bold;"><%=chat.getLatest_message_time()%>
                    </p>
                    <%
                    } else {
                    %>
                    <p class="ml-auto"><%=chat.getLatest_message_time()%>
                    </p>

                    <%
                            }
                        }
                    %>
                    <%
                        }
                    %>


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
