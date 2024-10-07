package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 17/11/15.
 */
public class CantMakeHoldTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed to make a hold funds on the account";
    public CantMakeHoldTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
