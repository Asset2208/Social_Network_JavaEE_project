package servlets;

import classes.Chats;
import classes.DBManager;
import classes.Messages;
import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(value = "/privatemessage")
public class PrivateMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            ArrayList<Messages> messages = DBManager.getAllMessagesByChatId(id);
            Chats chats = DBManager.getChatByChatId(id);
            if (!chats.getLatest_message_user().getId().equals(user.getId()) && !chats.isRead_by_receiver()){
                chats.setRead_by_receiver(true);
                DBManager.updateChatRead(chats);
            }
            request.setAttribute("chats", chats);
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("/private_message.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
