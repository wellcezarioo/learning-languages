package bank.aplication;

import java.util.Scanner;

class Account {
    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if(amount > this.withdrawLimit){
            throw(new IllegalArgumentException("The amount exceeds withdraw limit"));
        }
        else if (this.balance < amount) {
            throw(new IllegalArgumentException("Not enought balance"));
        }

        this.balance -= amount;
    }
}


public class Bank {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            int number;
            System.out.println("Enter account data");
            System.out.print("Number: ");
            number = sc.nextInt();
            sc.nextLine();

            String holder;
            System.out.print("Holder: ");
            holder = sc.nextLine();

            double balance;
            System.out.print("Initial balance: ");
            balance = sc.nextDouble();

            double withdrawLimit;
            System.out.print("Withdraw limit: ");
            withdrawLimit = sc.nextDouble();

            Account account = new Account(number, holder, balance, withdrawLimit);

            double withdraw;
            System.out.print("\nEnter amount for withdraw: ");
            withdraw = sc.nextDouble();

            account.withdraw(withdraw);
            System.out.println("New balance: " + account.getBalance());

        }
        catch (IllegalArgumentException e){
            System.out.println("Withdrae error: " + e.getMessage());
        }

        sc.close();

    }
}
