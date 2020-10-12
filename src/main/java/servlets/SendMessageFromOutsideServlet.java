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

@WebServlet(value = "/sendmessageout")
public class SendMessageFromOutsideServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("CURRENT_USER");
        if (user != null){
            Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());
            Long opponent_id = Long.parseLong(request.getParameter("opponent_id"));
            User opponent_user = DBManager.getUserById(opponent_id);
            String message = request.getParameter("message");
            Chats chats = DBManager.getChatById(user.getId(), opponent_id);
            if (chats == null) {
                chats = DBManager.getChatById(opponent_id, user.getId());
                if (chats == null){
                    chats = new Chats(null, user, opponent_user, sqlTimestamp, message, sqlTimestamp, user, false);
                    DBManager.addChat(chats);
                    Chats chats1 = DBManager.getChatById(user.getId(), opponent_id);
                    Messages messages = new Messages(null, chats1, opponent_user, user, message, false, sqlTimestamp);
                    DBManager.addMessage(messages);
                    response.sendRedirect("/messages");
                }
                else {
                    chats.setLatest_message_text(message);
                    chats.setLatest_message_user(user);
                    chats.setLatest_message_time(sqlTimestamp);
                    chats.setRead_by_receiver(false);
                    DBManager.updateChat(chats);
                    Messages messages = new Messages(null, chats, opponent_user, user, message, false, sqlTimestamp);
                    DBManager.addMessage(messages);
                    response.sendRedirect("/messages");
                }
            }
            else {
                chats.setLatest_message_text(message);
                chats.setLatest_message_time(sqlTimestamp);
                chats.setLatest_message_user(user);
                chats.setRead_by_receiver(false);
                DBManager.updateChat(chats);
                Messages messages = new Messages(null, chats, opponent_user, user, message, false, sqlTimestamp);
                DBManager.addMessage(messages);
                response.sendRedirect("/messages");
            }
        }
        else {
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
