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
        String email = request.getParameter("email");
        String full_name = request.getParameter("full_name");
        String birthdate = request.getParameter("birthdate");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(birthdate, format);
        Date date1 = Date.valueOf(date);

        User user = (User)request.getSession().getAttribute("CURRENT_USER");


        if (user.getEmail().equals(email)){
            DBManager.updateProfile(user.getId(), email, full_name, date1);
            User new_user = DBManager.getUserByEmail(email);
            request.getSession().setAttribute("CURRENT_USER", new_user);
            response.sendRedirect("/profile?success");
        }
        else {
            String redirect = "/profile?emailerror";
            User testUser = DBManager.getUserByEmail(email);
            if (testUser == null) {
                DBManager.updateProfile(user.getId(), email, full_name, date1);
                User new_user = DBManager.getUserByEmail(email);
                request.getSession().setAttribute("CURRENT_USER", new_user);
                redirect = "/?success";
            }
            response.sendRedirect(redirect);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
