package servlets;

import classes.DBManager;
import classes.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/toregistrate")
public class ToRegistrateServlet extends HttpServlet {
    public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
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


        if (picture_url.equals("") || !isValid(picture_url)) {
            picture_url = "https://karateinthewoodlands.com/wp-content/uploads/2017/09/default-user-image.png";
        }
        else {
            BufferedImage image = null;
            try
            {
                URL url = new URL(picture_url);
                image = ImageIO.read(url);
                //If successful, process the message
            } catch (IOException e) {
                picture_url = "https://karateinthewoodlands.com/wp-content/uploads/2017/09/default-user-image.png";
                e.printStackTrace();
            }
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
