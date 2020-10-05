package classes;

import java.sql.*;

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
}
