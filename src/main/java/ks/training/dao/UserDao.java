package ks.training.dao;

import ks.training.entity.User;
import ks.training.utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDao {

    public boolean checkEmailUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ? ";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void register(String email, String password, String fullName, String phone, String address) {
        String insertUserSQL = "INSERT INTO users(email, password, full_name, phone, address) VALUES (?, ?, ?, ?, ?)";
        String insertUserRoleSQL = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        String getRoleIdSQL = "SELECT id FROM roles WHERE name = 'Customer'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtUser = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
             Statement stmtRole = conn.createStatement();
             ResultSet rs = stmtRole.executeQuery(getRoleIdSQL)) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

            stmtUser.setString(1, email);
            stmtUser.setString(2, hashedPassword);
            stmtUser.setString(3, fullName);
            stmtUser.setString(4, phone);
            stmtUser.setString(5, address);
            int affectedRows = stmtUser.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Lỗi: Không thể đăng ký user.");
            }

            try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Lỗi: Không lấy được user_id.");
                }
                int userId = generatedKeys.getInt(1);

                if (!rs.next()) {
                    throw new SQLException("Lỗi: Vai trò 'Customer' không tồn tại.");
                }
                int roleId = rs.getInt("id");

                try (PreparedStatement stmtUserRole = conn.prepareStatement(insertUserRoleSQL)) {
                    stmtUserRole.setInt(1, userId);
                    stmtUserRole.setInt(2, roleId);
                    stmtUserRole.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public User selectByEmailAndPassWord(User user) {
        User result = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, user.getEmail());
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String hashedPassword = rs.getString("password"); // Mật khẩu đã mã hóa
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String createdAt = rs.getString("created_at");

                if (BCrypt.checkpw(user.getPassword(), hashedPassword)) {
                    result = new User(id, email, hashedPassword, fullName, phone, address, createdAt);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
