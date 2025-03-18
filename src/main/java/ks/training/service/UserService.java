package ks.training.service;

import ks.training.dao.UserDao;
import ks.training.dto.UserDto;
import ks.training.entity.User;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    public UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public boolean checkEmailUser(String email){
        return userDao.checkEmailUser(email);
    }
    public void register(String email, String password, String fullName, String phone, String address) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                userDao.register(email,password,fullName,phone,address);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User selectByEmailAndPassWord(User user){
        return userDao.selectByEmailAndPassWord(user);
    }
    public User validateUser(String email, String password){
        return userDao.validateUser(email,password);
    }
    public boolean updateUser(int id, String fullName, String password, String phone, String address){
       boolean result = false;
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                userDao.updateUser(conn, id, fullName, password, phone, address);
                conn.commit();
                result = true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User userDetail(int userId){
        return userDao.userDetail(userId);
    }
    public List<UserDto> listUser(int page, int recordsPerPage){
        return userDao.listUserByPage(page,recordsPerPage);
    }
    public boolean updateUserRole(int userId, int newRole){
        return userDao.updateUserRole(userId,newRole);
    }

    public int countUsers() {
        return userDao.countTotalUsers();
    }
}
