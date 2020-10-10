package classes;

import java.sql.Timestamp;

public class Friends {
    private Long id;
    private Long user_id;
    private Long friend_id;
    private Timestamp added_time;

    public Friends() {}

    public Friends(Long id, Long user_id, Long friend_id, Timestamp added_time) {
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.added_time = added_time;
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

    public Long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(Long friend_id) {
        this.friend_id = friend_id;
    }

    public Timestamp getAdded_time() {
        return added_time;
    }

    public void setAdded_time(Timestamp added_time) {
        this.added_time = added_time;
    }
}
