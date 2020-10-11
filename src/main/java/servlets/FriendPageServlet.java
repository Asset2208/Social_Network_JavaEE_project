package servlets;

import classes.DBManager;
import classes.Post;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/friend")
public class FriendPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            User friend = DBManager.getUserById(id);
            ArrayList<Post> friend_posts = DBManager.getPostsByAuthorId(id);
            request.setAttribute("friend", friend);
            request.setAttribute("friend_posts", friend_posts);
            request.getRequestDispatcher("/friend_page.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
