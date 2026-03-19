package com.bank;

import java.sql.*;

public class DatabaseConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/bankdb",
                    "root",
                    "sql@projects1234"
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {

        try {
            Connection conn = getConnection();

            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");

            while (rs.next()) {
                int accountID = rs.getInt("accountID");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");

                System.out.println(accountID + " " + name + " " + balance);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}