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

    public static void updateProfile(Long idx, String new_email, String new_name, Date new_birthdate){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET email = ?, full_name = ?, birth_date = ? WHERE id = ?");
            ps.setString(1, new_email);
            ps.setString(2, new_name);
            ps.setDate(3, new_birthdate);
            ps.setLong(4, idx);

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
            PreparedStatement ps = connection.prepareStatement("SELECT * from posts");
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
            PreparedStatement ps = connection.prepareStatement("SELECT * from posts WHERE author_id = ?");

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
}
