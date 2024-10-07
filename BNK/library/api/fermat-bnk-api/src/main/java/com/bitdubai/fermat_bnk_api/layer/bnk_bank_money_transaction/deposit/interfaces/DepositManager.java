package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.exceptions.CantMakeDepositTransactionException;

/**
 * Created by memo on 17/11/15.
 */
public interface DepositManager {
    BankTransaction makeDeposit(BankTransactionParameters parameters) throws CantMakeDepositTransactionException;
}
