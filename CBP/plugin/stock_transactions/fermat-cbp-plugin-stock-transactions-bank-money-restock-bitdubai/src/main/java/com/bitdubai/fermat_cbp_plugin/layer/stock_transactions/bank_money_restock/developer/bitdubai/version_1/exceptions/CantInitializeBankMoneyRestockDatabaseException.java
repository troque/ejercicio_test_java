package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 16/11/15.
 */
public class CantInitializeBankMoneyRestockDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BANK MONEY RESTOCK  DATABASE EXCEPTION";

    public CantInitializeBankMoneyRestockDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeBankMoneyRestockDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeBankMoneyRestockDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeBankMoneyRestockDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
