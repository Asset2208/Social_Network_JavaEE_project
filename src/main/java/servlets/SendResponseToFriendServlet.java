package servlets;

import classes.DBManager;
import classes.Friends;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(value = "/sendresponsetofriend")
public class SendResponseToFriendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null){
            Timestamp added_time = new Timestamp(System.currentTimeMillis());
            String response_name = request.getParameter("response");
            Long friend_id = Long.parseLong(request.getParameter("friend_id"));
            if (response_name.equals("Yes")){
                Friends friends = new Friends(null, user.getId(), friend_id, added_time);
                Friends friends2 = new Friends(null, friend_id, user.getId(), added_time);
                DBManager.addFriend(friends);
                DBManager.addFriend(friends2);
                DBManager.deleteFriendsRequests(user.getId(), friend_id);
                response.sendRedirect("/friends");
            }
            else {
                DBManager.deleteFriendsRequests(user.getId(), friend_id);
                response.sendRedirect("/friends");
            }
        }
        else {
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
