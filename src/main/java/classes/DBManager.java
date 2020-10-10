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

    private static User getUserById(Long idx) {
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
}
