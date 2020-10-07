<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.*" %>
<%@ page import="classes.Post" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aralasu.kz</title>
    <%@include file="vendor/head.jsp"%>

    <link href="fontawesome-free-5.15.1-web/css/all.css" rel="stylesheet">

    <script src="tinymce_latest_custom/tinymce/tinymce.min.js"></script>
    <script>tinymce.init({ selector:'textarea' });</script>
</head>
<body>
<%@include file="vendor/navbar.jsp"%>
<div class="container">
    <div class="row mt-5">
        <%
            Post post = (Post) request.getAttribute("post");
        %>
        <div class="col-sm-12 mb-5">
            <h4><%=post.getTitle()%></h4>
            <p class="text-muted">At <%=post.getPost_date()%> by <span style="color:#17339B; font-weight: bold;"><%=post.getUser().getFullName()%></span></p>
            <hr class="my-4">
            <hr class="my-4">
            <p><strong><%=post.getShort_content()%></strong></p>
            <p><%=post.getContent()%></p>
        </div>

</div>
</div>
</body>
</html>
