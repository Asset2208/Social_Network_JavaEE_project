package servlets;

import classes.DBManager;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/editpassword")
public class EditPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String old_password = request.getParameter("old_password");
        String new_password = request.getParameter("new_password");
        String re_new_password = request.getParameter("re_new_password");

        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        String redirect = "/profile?oldpasserror";
        if (user.getPassword().equals(old_password)){
            redirect = "/profile?diffpasserror";
            if (new_password.equals(re_new_password)){
                redirect = "/profile?successpassword";
                DBManager.updatePassword(user.getId(), new_password);
                User new_user = DBManager.getUserByEmail(user.getEmail());
                request.getSession().setAttribute("CURRENT_USER", new_user);
            }
        }

        response.sendRedirect(redirect);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
