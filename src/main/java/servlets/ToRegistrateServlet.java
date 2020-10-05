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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/toregistrate")
public class ToRegistrateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        String full_name = request.getParameter("full_name");
        String birthdate = request.getParameter("birthdate");
        String picture_url = request.getParameter("picture_url");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(birthdate, format);
        Date date1 = Date.valueOf(date);

        String redirect = "/register?passworderror";


        if (picture_url.equals("")) {
            picture_url = "https://karateinthewoodlands.com/wp-content/uploads/2017/09/default-user-image.png";
        }


        if (password.equals(re_password)) {

            User testUser = DBManager.getUserByEmail(email);

            redirect = "/register?emailerror";

            if (testUser == null){
                redirect = "/register?success";
                User user = new User(null, email, password, full_name, date1, picture_url);
                DBManager.addUser(user);
            }
        }

        response.sendRedirect(redirect);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
