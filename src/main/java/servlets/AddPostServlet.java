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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet(value = "/addpost")
public class AddPostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        DateFormat df = new SimpleDateFormat("dd MMMM 'at' HH:mm");
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);



        String title = request.getParameter("title");
        String short_content = request.getParameter("short_content");
        String content = request.getParameter("content");
        Long id = Long.parseLong(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("CURRENT_USER");

        Post post = new Post(null, user, title, short_content, content, sqlTimestamp);

        DBManager.addPost(post);

        response.sendRedirect("/");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
