package classes;

import java.sql.Time;
import java.sql.Timestamp;

public class Chats {
    private Long id;
    private User user;
    private User opponent_user;
    private Timestamp created_date;
    private String latest_message_text;
    private Timestamp latest_message_time;
    private User latest_message_user;
    private boolean read_by_receiver;

    public Chats() {}

    public Chats(Long id, User user, User opponent_user, Timestamp created_date, String latest_message_text, Timestamp latest_message_time, User latest_message_user, boolean read_by_receiver) {
        this.id = id;
        this.user = user;
        this.opponent_user = opponent_user;
        this.created_date = created_date;
        this.latest_message_text = latest_message_text;
        this.latest_message_time = latest_message_time;
        this.latest_message_user = latest_message_user;
        this.read_by_receiver = read_by_receiver;
    }

    public boolean isRead_by_receiver() {
        return read_by_receiver;
    }

    public void setRead_by_receiver(boolean read_by_receiver) {
        this.read_by_receiver = read_by_receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponent_user() {
        return opponent_user;
    }

    public void setOpponent_user(User opponent_user) {
        this.opponent_user = opponent_user;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public String getLatest_message_text() {
        return latest_message_text;
    }

    public void setLatest_message_text(String latest_message_text) {
        this.latest_message_text = latest_message_text;
    }

    public Timestamp getLatest_message_time() {
        return latest_message_time;
    }

    public void setLatest_message_time(Timestamp latest_message_time) {
        this.latest_message_time = latest_message_time;
    }

    public User getLatest_message_user() {
        return latest_message_user;
    }

    public void setLatest_message_user(User latest_message_user) {
        this.latest_message_user = latest_message_user;
    }
}
