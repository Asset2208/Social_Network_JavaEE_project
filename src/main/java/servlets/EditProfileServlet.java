package servlets;

import classes.DBManager;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/editprofile")
public class EditProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String full_name = request.getParameter("full_name");
        String birthdate = request.getParameter("birthdate");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(birthdate, format);
        Date date1 = Date.valueOf(date);

        User user = (User)request.getSession().getAttribute("CURRENT_USER");


        if (user != null){
            user.setFullName(full_name);
            user.setBirthDate(date1);
            DBManager.updateProfile(user);
            request.getSession().setAttribute("CURRENT_USER", user);
            response.sendRedirect("/profile?success");
        }
        else {
            response.sendRedirect("/");
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
