package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 17/11/15.
 */
public class MissingBankMoneyRestockDataException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to insert data in table. The referenced Bank Money Transaction transactionId does not exists.";

    public MissingBankMoneyRestockDataException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}