package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantGetListPurchaseNegotiationsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LIST PURCHASE NEGOTIATIONS EXCEPTION";

    public CantGetListPurchaseNegotiationsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}