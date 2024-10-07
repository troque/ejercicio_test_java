package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerSaleNegotiationDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER SALE NEGOTIATION DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerSaleNegotiationDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerSaleNegotiationDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerSaleNegotiationDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerSaleNegotiationDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}