<%@ page import="classes.User" %><%
    User currentUser = (User) request.getSession().getAttribute("CURRENT_USER");
    boolean ONLINE = false;
    if (currentUser != null) {
        ONLINE = true;
    }
%>