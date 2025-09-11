package org.example.domain;

import org.example.messages.ErrorMessage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int bankAccountNumber;
    private String alias;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getAlias() {
        return alias;
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void increaseBalance(int depositAmount) {
        lock.lock();
        try {
            this.balance += depositAmount;
        } finally {
            lock.unlock(); // 반드시 해제해야 함
        }
    }

    public boolean decreaseBalance(int withdrawalAmount) {
        lock.lock();
        try {
            if (withdrawalAmount > balance) {
                throw new IllegalArgumentException(ErrorMessage.LIMIT_EXCEEDED.getMessage());
            }
            this.balance -= withdrawalAmount;
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(withdrawalAmount+"원 출금/이체/결제 실패: " + e.getMessage());
            return false;
        } finally {
            lock.unlock(); // 반드시 해제해야 함
        }
    }

    public void pay(int price) {
        System.out.println("결제 진행 중");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (decreaseBalance(price)) {
            System.out.println(price + "원 결제 완료");
        }
    }
}
