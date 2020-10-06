package servlets;

import classes.DBManager;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(value = "/editpost")
public class EditPostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            String title = request.getParameter("title");
            String short_content = request.getParameter("short_content");
            String content = request.getParameter("content");


            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);

            DBManager.updatePost(id, title, short_content, content, sqlTimestamp);
            response.sendRedirect("/myposts");
        }





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
