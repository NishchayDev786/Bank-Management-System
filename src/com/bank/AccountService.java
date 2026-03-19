package com.bank;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;

public class AccountService {

    public void createAccount(int accountID, String name, double balance) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "INSERT INTO accounts VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, accountID);
            ps.setString(2, name);
            ps.setDouble(3, balance);

            ps.executeUpdate();

            System.out.println("Account created successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void depositMoney(int accountID, double amount) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "UPDATE accounts SET balance = balance + ? WHERE accountID = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setDouble(1, amount);
            ps.setInt(2, accountID);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Money deposited successfully");
            else
                System.out.println("Account not found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdrawMoney(int accountID, double amount) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String checkQuery = "SELECT balance FROM accounts WHERE accountID = ? ";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, accountID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");

                if (currentBalance >= amount) {
                    String query = "UPDATE accounts SET balance = balance - ? WHERE accountID = ?";
                    PreparedStatement ps = conn.prepareStatement(query);

                    ps.setDouble(1, amount);
                    ps.setInt(2, accountID);
                    ps.executeUpdate();
                    System.out.println("Money withdrawn successfully");
                } else {
                    System.out.println("Insufficient balance");
                }

            } else {
                System.out.println("Account not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBalance(int accountID) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT balance, name FROM accounts WHERE accountID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, accountID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                String name = rs.getString("name");
                System.out.println("Account Holder: " + name);
                System.out.println("Current Balance: " + balance);
            } else {
                System.out.println("Account not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllAccounts() {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM accounts";
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int accountID = rs.getInt("accountID");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");

                System.out.println(accountID + " | " + name + " | " + balance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void transferMoney(int sourceID, int destID, double amount) {

        try {
            Connection conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String checkSource = "SELECT balance FROM accounts WHERE accountID = ?";
            PreparedStatement ps1 = conn.prepareStatement(checkSource);
            ps1.setInt(1, sourceID);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                System.out.println("Source account not found");
                return;
            }

            double balance = rs1.getDouble("balance");

            if (balance < amount) {
                System.out.println("Insufficient balance");
                return;
            }

            String checkDest = "SELECT * FROM accounts WHERE accountID = ?";
            PreparedStatement ps2 = conn.prepareStatement(checkDest);
            ps2.setInt(1, destID);
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                System.out.println("Destination account not found");
                return;
            }

            String withdraw = "UPDATE accounts SET balance = balance - ? WHERE accountID = ?";
            PreparedStatement ps3 = conn.prepareStatement(withdraw);
            ps3.setDouble(1, amount);
            ps3.setInt(2, sourceID);
            ps3.executeUpdate();

            String deposit = "UPDATE accounts SET balance = balance + ? WHERE accountID = ?";
            PreparedStatement ps4 = conn.prepareStatement(deposit);
            ps4.setDouble(1, amount);
            ps4.setInt(2, destID);
            ps4.executeUpdate();

            conn.commit();
            System.out.println("Money transferred successfully");

        } catch (Exception e) {
            try {
                System.out.println("Transaction failed. Rolling back...");
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}