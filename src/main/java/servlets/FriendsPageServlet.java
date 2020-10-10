package servlets;

import classes.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/friends")
public class FriendsPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("CURRENT_USER");
        String full_name = request.getParameter("full_name");

        if (user != null && full_name == null){
            ArrayList<User> friends_of_user = DBManager.getAllUserFriends(user.getId());
            request.setAttribute("friends_of_user", friends_of_user);
            request.getRequestDispatcher("/friends.jsp").forward(request, response);
        }
        else if (user != null){
            ArrayList<User> search_users = DBManager.getAllUsersByFullName(full_name);
            ArrayList<Friends> friends = DBManager.getAllFriends(user.getId());
            ArrayList<Friends_requests> friends_requests = DBManager.getAllFriendsRequests(user.getId());
            request.setAttribute("search_content", full_name);
            request.setAttribute("search_users", search_users);
            request.setAttribute("friends", friends);
            request.setAttribute("friends_requests", friends_requests);
            request.getRequestDispatcher("/friends.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
