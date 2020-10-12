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

@WebServlet(value = "/messages")
public class MessagesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            ArrayList<Chats> chats = DBManager.getAllChatsByUserId(user.getId());
            ArrayList<Chats> chats1 = DBManager.getAllChatsByOpponentId(user.getId());
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
            request.getRequestDispatcher("/messages.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
