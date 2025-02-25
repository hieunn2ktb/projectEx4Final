package ks.training.service;

import ks.training.dao.UserDao;
import ks.training.entity.Property;
import ks.training.entity.User;
import ks.training.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

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

}
