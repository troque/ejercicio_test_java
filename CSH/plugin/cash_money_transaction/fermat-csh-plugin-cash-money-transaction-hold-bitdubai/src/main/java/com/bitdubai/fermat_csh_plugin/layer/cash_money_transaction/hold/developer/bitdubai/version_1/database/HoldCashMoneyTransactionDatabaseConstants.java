package com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database;

/**
 * The Class <code>com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.version_1.database.HoldCashMoneyTransactionDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 18/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class HoldCashMoneyTransactionDatabaseConstants {

    /**
     * Hold database table definition.
     */
    static final String HOLD_TABLE_NAME = "hold";

    static final String HOLD_TRANSACTION_ID_COLUMN_NAME = "transaction_id";
    static final String HOLD_WALLET_PUBLIC_KEY_COLUMN_NAME = "wallet_public_key";
    static final String HOLD_ACTOR_PUBLIC_KEY_COLUMN_NAME = "actor_public_key";
    static final String HOLD_PLUGIN_PUBLIC_KEY_COLUMN_NAME = "plugin_public_key";
    static final String HOLD_AMOUNT_COLUMN_NAME = "amount";
    static final String HOLD_CURRENCY_COLUMN_NAME = "currency";
    static final String HOLD_MEMO_COLUMN_NAME = "memo";
    static final String HOLD_TIMESTAMP_ACKNOWLEDGE_COLUMN_NAME = "timestamp_acknowledge";
    static final String HOLD_TIMESTAMP_CONFIRM_REJECT_COLUMN_NAME = "timestamp_confirm_reject";
    static final String HOLD_STATUS_COLUMN_NAME = "status";

    static final String HOLD_FIRST_KEY_COLUMN = "transaction_id";

}