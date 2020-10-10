package classes;

import java.sql.Timestamp;

public class Friends_requests {
    private Long id;
    private Long user_id;
    private Long request_sender_id;
    private Timestamp sent_time;

    public Friends_requests() {}

    public Friends_requests(Long id, Long user_id, Long request_sender_id, Timestamp sent_time) {
        this.id = id;
        this.user_id = user_id;
        this.request_sender_id = request_sender_id;
        this.sent_time = sent_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRequest_sender_id() {
        return request_sender_id;
    }

    public void setRequest_sender_id(Long request_sender_id) {
        this.request_sender_id = request_sender_id;
    }

    public Timestamp getSent_time() {
        return sent_time;
    }

    public void setSent_time(Timestamp sent_time) {
        this.sent_time = sent_time;
    }
}
