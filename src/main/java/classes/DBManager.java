package classes;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/socialnetwork_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (email, password, full_name, birth_date, picture_url) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setDate(4, user.getBirthDate());
            ps.setString(5, user.getPicture_url());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addPost(Post post) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO posts (author_id, title, short_content, content, post_date)"
            + " VALUES (?, ?, ?, ?, ?)");
            ps.setLong(1, post.getUser().getId());
            ps.setString(2, post.getTitle());
            ps.setString(3, post.getShort_content());
            ps.setString(4, post.getContent());
            ps.setTimestamp(5, post.getPost_date());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static User getUserByEmail(String email) {
        User user = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user = new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),
                        rs.getString("picture_url")
                );
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static ArrayList<User> getAllUsersByFullName(String full_name) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE full_name LIKE ?");
            String search_name = "%"+full_name+"%";
            ps.setString(1, search_name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),
                        rs.getString("picture_url"))
                );
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return users;
    }

    public static User getUserById(Long idx) {
        User user = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE id = ?");
            ps.setLong(1, idx);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user = new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getDate("birth_date"),
                        rs.getString("picture_url")
                );
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void updateProfile(User user){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET full_name = ?, birth_date = ? WHERE id = ?");
            ps.setString(1, user.getFullName());
            ps.setDate(2, user.getBirthDate());
            ps.setLong(3, user.getId());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updatePassword(Long id, String new_password) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
            ps.setString(1, new_password);
            ps.setLong(2, id);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePicture(Long id, String picture_url) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET picture_url = ? WHERE id = ?");
            ps.setString(1, picture_url);
            ps.setLong(2, id);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Post> getAllPosts() {
        ArrayList<Post> list = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from posts ORDER BY post_date DESC");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long author_id = rs.getLong("author_id");
                String title = rs.getString("title");
                String short_content = rs.getString("short_content");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("post_date");

                User user = getUserById(author_id);

                list.add(new Post(id, user, title, short_content, content, date));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static Post getPost(Long idx) {
        Post post = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from posts WHERE id = ?");

            ps.setLong(1, idx);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long author_id = rs.getLong("author_id");
                String title = rs.getString("title");
                String short_content = rs.getString("short_content");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("post_date");

                User user = getUserById(author_id);

                post = new Post(id, user, title, short_content, content, date);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static ArrayList<Post> getPostsByAuthorId(Long author_idx) {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from posts WHERE author_id = ? ORDER BY post_date DESC ");

            ps.setLong(1, author_idx);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long author_id = rs.getLong("author_id");
                String title = rs.getString("title");
                String short_content = rs.getString("short_content");
                String content = rs.getString("content");
                Timestamp date = rs.getTimestamp("post_date");

                User user = getUserById(author_id);

                posts.add(new Post(id, user, title, short_content, content, date));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static void deletePost(Long idx) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
            ps.setLong(1, idx);


            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePost(Long id, String title, String short_content, String content, Timestamp sqlTimestamp) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET title=?, short_content=?, content=?, post_date=? WHERE id = ?");
            ps.setString(1, title);
            ps.setString(2, short_content);
            ps.setString(3, content);
            ps.setTimestamp(4, sqlTimestamp);
            ps.setLong(5, id);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getAllUserFriends(Long idx) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT f.id, f.user_id, f.friend_id, f.added_time, fr.email, fr.password, fr.full_name, fr.birth_date, fr.picture_url FROM friends f " +
                    " INNER JOIN users fr ON f.friend_id = fr.id" +
                    " WHERE f.user_id = ? ORDER BY added_time DESC");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                users.add(
                        new User(
                                rs.getLong("friend_id"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("full_name"),
                                rs.getDate("birth_date"),
                                rs.getString("picture_url")
                        )
                );
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Friends> getAllFriends(Long idx) {
        ArrayList<Friends> friends = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friends WHERE user_id = ?");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                friends.add(
                        new Friends(
                                rs.getLong("id"),
                                rs.getLong("user_id"),
                                rs.getLong("friend_id"),
                                rs.getTimestamp("added_time")
                        )
                );
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static ArrayList<Friends_requests> getAllFriendsRequests(Long idx) {
        ArrayList<Friends_requests> friends_requests = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friends_requests WHERE request_sender_id = ?");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                friends_requests.add(
                        new Friends_requests(
                                rs.getLong("id"),
                                rs.getLong("user_id"),
                                rs.getLong("request_sender_id"),
                                rs.getTimestamp("sent_time")
                        )
                );
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends_requests;
    }

    public static ArrayList<Friends_requests> getAllFriendsReceivedRequests(Long idx) {
        ArrayList<Friends_requests> friends_requests = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM friends_requests WHERE user_id = ?");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                friends_requests.add(
                        new Friends_requests(
                                rs.getLong("id"),
                                rs.getLong("user_id"),
                                rs.getLong("request_sender_id"),
                                rs.getTimestamp("sent_time")
                        )
                );
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends_requests;
    }

    public static ArrayList<User> getAllReceivedUsersRequests(Long idx) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT f.id, f.user_id, f.request_sender_id, f.sent_time, fr.email, fr.password, fr.full_name, fr.birth_date, fr.picture_url FROM friends_requests f " +
                    " INNER JOIN users fr ON f.request_sender_id = fr.id" +
                    " WHERE f.user_id = ? ORDER BY sent_time DESC");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                users.add(
                        new User(
                                rs.getLong("request_sender_id"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("full_name"),
                                rs.getDate("birth_date"),
                                rs.getString("picture_url")
                        )
                );
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void sendRequestFriend(Friends_requests friends_requests) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO friends_requests (user_id, request_sender_id, sent_time)"
                    + " VALUES (?, ?, ?)");
            ps.setLong(1, friends_requests.getUser_id());
            ps.setLong(2, friends_requests.getRequest_sender_id());
            ps.setTimestamp(3, friends_requests.getSent_time());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addFriend(Friends friends) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO friends (user_id, friend_id, added_time)"
                    + " VALUES (?, ?, ?)");

            ps.setLong(1, friends.getUser_id());
            ps.setLong(2,friends.getFriend_id());
            ps.setTimestamp(3, friends.getAdded_time());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addChat(Chats chats) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO chats (user_id, opponent_user_id, created_date, latest_message_text, latest_message_time, latest_message_user_id)"
                    + " VALUES (?, ?, ?, ?, ?, ?)");

            ps.setLong(1, chats.getUser().getId());
            ps.setLong(2, chats.getOpponent_user().getId());
            ps.setTimestamp(3, chats.getCreated_date());
            ps.setString(4, chats.getLatest_message_text());
            ps.setTimestamp(5, chats.getLatest_message_time());
            ps.setLong(6, chats.getLatest_message_user().getId());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage(Messages message) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO messages (chat_id, user_id, sender_id, message_text, read_by_receiver, sent_date)"
                    + " VALUES (?, ?, ?, ?, ?, ?)");

            ps.setLong(1, message.getChats().getId());
            ps.setLong(2, message.getUser().getId());
            ps.setLong(3, message.getSender().getId());
            ps.setString(4, message.getMessage_text());
            ps.setBoolean(5, message.isRead_by_receiver());
            ps.setTimestamp(6, message.getSent_date());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFriendsRequests(Long id, Long friend_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM friends_requests WHERE user_id = ? AND request_sender_id = ?");

            ps.setLong(1, id);
            ps.setLong(2, friend_id);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFriend(Long idx1, Long idx2) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM friends WHERE user_id = ? AND friend_id = ?");

            ps.setLong(1, idx1);
            ps.setLong(2, idx2);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Chats getChatById(Long user_id, Long opponent_id) {
        Chats chats = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM chats c " +
                    " WHERE c.user_id = ? AND c.opponent_user_id = ?");

            ps.setLong(1, user_id);
            ps.setLong(2, opponent_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long us_id = rs.getLong("user_id");
                Long op_id = rs.getLong("opponent_user_id");
                Timestamp cr_date = rs.getTimestamp("created_date");
                String latest_msg = rs.getString("latest_message_text");
                Timestamp latest_msg_time = rs.getTimestamp("latest_message_time");
                Long latest_msg_user_id = rs.getLong("latest_message_user_id");
                boolean read_by_receiver = rs.getBoolean("read_by_receiver");

                User user = getUserById(us_id);
                User op_user = getUserById(op_id);
                User lts_user = getUserById(latest_msg_user_id);
                chats = new Chats(id, user, op_user, cr_date, latest_msg, latest_msg_time, lts_user, read_by_receiver);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chats;
    }


    public static void updateChat(Chats chats) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE chats SET latest_message_text=?, latest_message_time=?, latest_message_user_id=?, read_by_receiver = ? WHERE id = ?");
            ps.setString(1, chats.getLatest_message_text());
            ps.setTimestamp(2, chats.getLatest_message_time());
            ps.setLong(3, chats.getLatest_message_user().getId());
            ps.setBoolean(4, chats.isRead_by_receiver());
            ps.setLong(5, chats.getId());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Chats> getAllChatsByUserId(Long idx) {
        ArrayList<Chats> chats = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM chats c" +
                    " WHERE c.user_id = ? ORDER BY latest_message_time DESC");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long us_id = rs.getLong("user_id");
                Long op_id = rs.getLong("opponent_user_id");
                Timestamp cr_date = rs.getTimestamp("created_date");
                String latest_msg = rs.getString("latest_message_text");
                Timestamp latest_msg_time = rs.getTimestamp("latest_message_time");
                Long latest_msg_user_id = rs.getLong("latest_message_user_id");
                boolean read_by_receiver = rs.getBoolean("read_by_receiver");

                User user = getUserById(us_id);
                User op_user = getUserById(op_id);
                User lts_user = getUserById(latest_msg_user_id);
                chats.add(new Chats(id, user, op_user, cr_date, latest_msg, latest_msg_time, lts_user, read_by_receiver));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chats;
    }

    public static ArrayList<Chats> getAllChatsByOpponentId(Long idx) {
        ArrayList<Chats> chats = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM chats c" +
                    " WHERE c.opponent_user_id = ? ORDER BY latest_message_time DESC");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long us_id = rs.getLong("user_id");
                Long op_id = rs.getLong("opponent_user_id");
                Timestamp cr_date = rs.getTimestamp("created_date");
                String latest_msg = rs.getString("latest_message_text");
                Timestamp latest_msg_time = rs.getTimestamp("latest_message_time");
                Long latest_msg_user_id = rs.getLong("latest_message_user_id");
                boolean read_by_receiver = rs.getBoolean("read_by_receiver");

                User user = getUserById(us_id);
                User op_user = getUserById(op_id);
                User lts_user = getUserById(latest_msg_user_id);
                chats.add(new Chats(id, user, op_user, cr_date, latest_msg, latest_msg_time, lts_user, read_by_receiver));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chats;
    }

    public static ArrayList<Messages> getAllMessagesByChatId(Long idx) {
        ArrayList<Messages> messages = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM messages" +
                    " WHERE chat_id = ?");

            ps.setLong(1, idx);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long chat_id = rs.getLong("chat_id");
                Long us_id = rs.getLong("user_id");
                Long sender_id = rs.getLong("sender_id");
                String msg = rs.getString("message_text");
                boolean read_by_receiver = rs.getBoolean("read_by_receiver");
                Timestamp sent_date = rs.getTimestamp("sent_date");

                Chats chats = getChatByChatId(chat_id);
                User user = getUserById(us_id);
                User op_user = getUserById(sender_id);
                messages.add(new Messages(id, chats, user, op_user, msg, read_by_receiver, sent_date));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static Chats getChatByChatId(Long chat_id) {
        Chats chats = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM chats" +
                    " WHERE id = ?");

            ps.setLong(1, chat_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Long id = rs.getLong("id");
                Long us_id = rs.getLong("user_id");
                Long op_id = rs.getLong("opponent_user_id");
                Timestamp cr_date = rs.getTimestamp("created_date");
                String latest_msg = rs.getString("latest_message_text");
                Timestamp latest_msg_time = rs.getTimestamp("latest_message_time");
                Long latest_msg_user_id = rs.getLong("latest_message_user_id");
                boolean read_by_receiver = rs.getBoolean("read_by_receiver");

                User user = getUserById(us_id);
                User op_user = getUserById(op_id);
                User lts_user = getUserById(latest_msg_user_id);
                chats = new Chats(id, user, op_user, cr_date, latest_msg, latest_msg_time, lts_user, read_by_receiver);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chats;
    }

    public static void updateChatRead(Chats chats) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE chats SET read_by_receiver = ? WHERE id = ?");
            ps.setBoolean(1, chats.isRead_by_receiver());
            ps.setLong(2, chats.getId());

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
