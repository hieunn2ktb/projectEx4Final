package ks.training.dao;

import ks.training.dto.UserDto;
import ks.training.entity.User;
import ks.training.utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public User validateUser(String email, String password) {
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = "SELECT u.id, u.full_name, u.email, u.password,u.phone,u.address, r.name AS role FROM users u " +
                    "JOIN user_roles ur ON u.id = ur.user_id " +
                    "JOIN roles r ON ur.role_id = r.id " +
                    "WHERE u.email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRole(rs.getString("role"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateUser(Connection connection, int id, String fullName, String password, String phone, String address) {
        String sql = "UPDATE users SET full_name = ?, phone = ?, address = ? , password = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

            stmt.setString(1, fullName);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setString(4, hashedPassword);
            stmt.setInt(5, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public User userDetail(int userId) {
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<UserDto> listUserByPage(int page, int recordsPerPage) {
        String sql = "SELECT u.id, u.full_name, u.email, u.phone, u.address, r.name " +
                "FROM users u " +
                "JOIN user_roles ur ON u.id = ur.user_id " +
                "JOIN roles r ON r.id = ur.role_id " +
                "LIMIT ? OFFSET ?";

        List<UserDto> userDtos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int start = (page - 1) * recordsPerPage;

            pstmt.setInt(1, recordsPerPage);
            pstmt.setInt(2, start);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserDto userDto = new UserDto();
                userDto.setId(rs.getInt("id"));
                userDto.setUsername(rs.getString("full_name"));
                userDto.setEmail(rs.getString("email"));
                userDto.setPhone(rs.getString("phone"));
                userDto.setAddress(rs.getString("address"));
                userDto.setRole(rs.getString("name"));
                userDtos.add(userDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userDtos;
    }

    public boolean updateUserRole(int userId, int newRole) {
        String sql = "Update user_roles SET role_id = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newRole);
            stmt.setInt(2, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countTotalUsers() {
        String query = "SELECT count(*) FROM users u\n" +
                "                JOIN user_roles ur ON u.id = ur.user_id \n" +
                "                JOIN roles r ON r.id = ur.role_id ;";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
