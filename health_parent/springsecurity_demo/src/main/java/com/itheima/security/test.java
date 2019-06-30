package com.itheima.security;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author ziJing
 * @version 1.0
 * @date 2019/6/30 10:33
 */
public class test {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://39.105.97.76:3306/health", "root", "root");
        System.out.println(connection);
    }
}
