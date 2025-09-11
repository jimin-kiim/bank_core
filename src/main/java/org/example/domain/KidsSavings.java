package org.example.domain;

public class KidsSavings extends Savings {
    private double bonusRate;

    public KidsSavings(int bankAccountNumber) {
        super(bankAccountNumber);
    }

    public void setBonusRate(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    public double getBonusRate() {
        return bonusRate;
    }
}
