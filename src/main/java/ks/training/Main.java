package ks.training;

import ks.training.utils.DatabaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    public static void main(String[] args) {
        File uploadDir = new File("C:/Users/Admin/Desktop/projectEx4Final/uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println("Thư mục uploads đã được tạo!");
        }
    }
}