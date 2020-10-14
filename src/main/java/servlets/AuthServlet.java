package servlets;

import classes.Chats;
import classes.DBManager;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(value = "/auth")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String redirect = "/login?emailerror";

        User user = DBManager.getUserByEmail(email);

        if (user != null) {
            redirect = "/login?passworderror";
            if (user.getPassword().equals(password)){
                request.getSession().setAttribute("CURRENT_USER", user);
                User user1 = (User) request.getSession().getAttribute("CURRENT_USER");

                ArrayList<Chats> chats = DBManager.getAllChatsByUserId(user1.getId());
                ArrayList<Chats> chats1 = DBManager.getAllChatsByOpponentId(user1.getId());
                if (chats == null && chats1 == null){
                    request.setAttribute("chats", chats);
                }
                else if (chats1 == null){
                    request.setAttribute("chats", chats);
                }
                else if (chats == null){
                    request.setAttribute("chats", chats1);
                }
                else {
                    chats.addAll(chats1);
                    request.setAttribute("chats", chats);
                }

                List<Chats> chatsList = chats.stream()
                        .filter(x -> !x.getLatest_message_user().getId().equals(user1.getId()))
                        .filter(y -> !y.isRead_by_receiver())
                        .collect(Collectors.toList());

                request.getSession().setAttribute("NOT_READ", chatsList.size());

                redirect = "/";
            }
        }

        response.sendRedirect(redirect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
