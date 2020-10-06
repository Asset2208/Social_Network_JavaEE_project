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

@WebServlet(value = "/myposts")
public class MyPostsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("CURRENT_USER");
        if (user != null){
            ArrayList<Post> posts = DBManager.getPostsByAuthorId(user.getId());
            request.setAttribute("posts", posts);
            request.getRequestDispatcher("/my_posts.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }

    }
}
