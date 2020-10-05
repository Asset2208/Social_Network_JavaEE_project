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

@WebServlet("/post")
public class PostViewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null){
            Long id = Long.parseLong(request.getParameter("id"));
            Post post = DBManager.getPost(id);
            request.setAttribute("post", post);
            request.getRequestDispatcher("/post_view.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/login");
        }

    }
}
