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

@WebServlet(value = "/editpicture")
public class EditPictureServlet extends HttpServlet {
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
        String picture_url = request.getParameter("picture_url");

        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user!= null && !picture_url.equals("")){
            String redirect = "/profile?urlerror";
            if (isValid(picture_url)){
                BufferedImage image = null;
                try
                {
                    URL url = new URL(picture_url);
                    image = ImageIO.read(url);

                    redirect = "/profile?urlsuccess";
                    DBManager.updatePicture(user.getId(), picture_url);
                    User new_user = DBManager.getUserByEmail(user.getEmail());
                    request.getSession().setAttribute("CURRENT_USER", new_user);
                } catch (IOException e) {
                    System.out.println("Unable to retrieve Image!!!");
                    e.printStackTrace();
                }

            }

            response.sendRedirect(redirect);
        }
        else if (user != null){
            String redirect = "/profile?urlerror";
            picture_url = "https://karateinthewoodlands.com/wp-content/uploads/2017/09/default-user-image.png";
            DBManager.updatePicture(user.getId(), picture_url);
            response.sendRedirect(redirect);
        }
        else {
            response.sendRedirect("/");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
