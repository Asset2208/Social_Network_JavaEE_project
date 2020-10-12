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
import java.sql.Timestamp;

@WebServlet(value = "/sendmessagein")
public class SendMessageFromInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null) {
            Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
            String message = request.getParameter("message");
            Long chat_id = Long.parseLong(request.getParameter("chat_id"));
            Long user_id = Long.parseLong(request.getParameter("user_id"));
            User opponent_user = DBManager.getUserById(user_id);
            Chats chats = DBManager.getChatByChatId(chat_id);
            chats.setRead_by_receiver(false);
            chats.setLatest_message_user(user);
            chats.setLatest_message_text(message);
            chats.setLatest_message_time(sqlTimestamp);
            DBManager.updateChat(chats);
            Messages messages = new Messages(null, chats, opponent_user, user, message, false, sqlTimestamp);
            DBManager.addMessage(messages);
            response.sendRedirect("/privatemessage?id="+chat_id);
        }
        else {
            response.sendRedirect("/");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
