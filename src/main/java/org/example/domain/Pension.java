package org.example.domain;

public class Pension extends BankAccount {

    private int monthlyContribution;
    public Pension(int bankAccountNumber) {
        super(bankAccountNumber);
    }

    public void setMonthlyContribution(int monthlyContribution) {
        this.monthlyContribution = monthlyContribution;
    }

    public int getMonthlyContribution() {
        return monthlyContribution;
    }
}
