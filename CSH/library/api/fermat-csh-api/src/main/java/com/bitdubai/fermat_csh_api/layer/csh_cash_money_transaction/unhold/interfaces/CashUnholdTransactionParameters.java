package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.unhold.interfaces;

import com.bitdubai.fermat_api.layer.world.enums.FiatCurrency;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public interface CashUnholdTransactionParameters {

    /**
     * Returns the transaction's UUID
     * @return      The transaction's unique identifier
     */
    UUID getTransactionId();

    /**
     * Returns the public key of the CASH wallet linked to this transaction
     * @return      Public key of the CSH wallet
     */
    String getPublicKeyWallet();

    /**
     * Returns the public key of the Actor which made the transaction
     * @return      Public key of the Actor which made the transaction
     */
    String getPublicKeyActor();

    /**
     * Returns the public key of the Plugin which made the transaction
     * @return      Public key of the Plugin which made the transaction
     */
    String getPublicKeyPlugin();

    /**
     * Returns the amount of the transaction
     * @return      Amount of the transaction
     */
    float getAmount();

    /**
     * Returns the currency of the transaction (e.g. USD, EUR)
     * @return      Currency of the transaction
     */
    FiatCurrency getCurrency();

    /**
     * Returns the memo of the transaction
     * @return      Memo of the transaction
     */
    String getMemo();

}
