package servlets;

import classes.DBManager;
import classes.Friends_requests;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(value = "/sendrequestfriend")
public class AddToFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null){
            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            Long friend_id = Long.parseLong(request.getParameter("friend_id"));
            Friends_requests friends_requests = new Friends_requests(null, friend_id, user.getId(), sqlTimestamp);
            DBManager.sendRequestFriend(friends_requests);
            response.sendRedirect("/friends");
        }
        else {
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
