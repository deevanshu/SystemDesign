package com.systemdesign.Atm;

public abstract class CashWithdrawProcessor {

    CashWithdrawProcessor nextCashWithdrawalProcessor;

    public CashWithdrawProcessor(CashWithdrawProcessor cashWithdrawalProcessor) {

        this.nextCashWithdrawalProcessor = cashWithdrawalProcessor;

    }

    public void withdraw(ATM atm, int remainingAmount) {

        if (nextCashWithdrawalProcessor != null) {
            nextCashWithdrawalProcessor.withdraw(atm, remainingAmount);
        }
    }
}

