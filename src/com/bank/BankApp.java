package com.bank;

import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();

        while(true) {

            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View All Accounts");
            System.out.println("6. Transfer Money");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch(choice) {

                case 1:
                    System.out.println("Enter Account ID:");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Name:");
                    String name = sc.nextLine();

                    System.out.println("Enter Balance:");
                    double balance = sc.nextDouble();

                    service.createAccount(id, name, balance);
                    break;

                case 2:
                    System.out.println("Enter Account ID:");
                    int depositId = sc.nextInt();

                    System.out.println("Enter amount to deposit:");
                    double amount = sc.nextDouble();

                    service.depositMoney(depositId, amount);
                    break;

                case 3:
                    System.out.println("Enter Account ID for withdrawal :");
                    int withdrawID = sc.nextInt();

                    System.out.println("Enter amount to withdraw");
                    double withdrawAmount = sc.nextDouble();

                    service.withdrawMoney(withdrawID, withdrawAmount);
                    break;

                case 4:
                    System.out.println("Enter account ID to check balance:");
                    int checkID = sc.nextInt();
                    service.checkBalance(checkID);

                case 5:
                    System.out.println("The accounts are:");
                    service.viewAllAccounts();
                    break;

                case 6:
                    System.out.println("Enter Source Account ID:");
                    int srcID = sc.nextInt();

                    System.out.println("Enter Destination Account ID:");
                    int destID = sc.nextInt();

                    System.out.println("Enter amount to transfer:");
                    double tranferAmount = sc.nextDouble();

                    service.transferMoney(srcID, destID, tranferAmount);
                    break;

                case 7:
                    System.out.println("Thank you for using the bank system.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}