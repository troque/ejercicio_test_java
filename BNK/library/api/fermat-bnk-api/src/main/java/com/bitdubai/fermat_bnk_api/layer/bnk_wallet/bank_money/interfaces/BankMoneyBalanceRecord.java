package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankAccountType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BankTransactionStatus;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface BankMoneyBalanceRecord {

    UUID getBankTransactionId();

    BankTransactionStatus getStatus();

    BalanceType getBalanceType();

    TransactionType getTransactionType();

    String getPublicKeyActorFrom();

    String getPublicKeyActorTo();

    float getAmount();

    BankCurrencyType getBankCurrencyType();

    BankOperationType getBankOperationType();

    String getBankDocumentReference();

    String getBankName();

    String getBankAccountNumber();

    BankAccountType getBankAccountType();

    long getTimestamp();

    String getMemo();

}
