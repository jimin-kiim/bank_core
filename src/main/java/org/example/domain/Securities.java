package org.example.domain;

public class Securities extends BankAccount {
    private double riskAssetRation;

    public Securities(int bankAccountNumber) {
        super(bankAccountNumber);
    }

    public void setRiskAssetRation(double riskAssetRation) {
        this.riskAssetRation = riskAssetRation;
    }
}
