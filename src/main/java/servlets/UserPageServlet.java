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

@WebServlet(value = "/user")
public class UserPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            String status = request.getParameter("status");
            if (!status.equals("")){
                if (status.equals("received_req")){
                    request.setAttribute("status", status);
                }
                else if (status.equals("addtofriend")){
                    request.setAttribute("status", status);
                }
                else if (status.equals("req_sent")){
                    request.setAttribute("status", status);
                }

            }
            User just_user = DBManager.getUserById(id);
            ArrayList<Post> user_posts = DBManager.getPostsByAuthorId(id);
            request.setAttribute("just_user", just_user);
            request.setAttribute("user_posts", user_posts);
            request.getRequestDispatcher("/user_page.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
