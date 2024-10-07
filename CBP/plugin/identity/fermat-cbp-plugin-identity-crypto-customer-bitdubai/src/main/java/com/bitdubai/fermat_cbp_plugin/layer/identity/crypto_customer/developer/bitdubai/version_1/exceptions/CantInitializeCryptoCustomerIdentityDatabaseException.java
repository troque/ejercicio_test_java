package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerIdentityDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoCustomerIdentityDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CRYPTO CUSTOMER IDENTITY DATABASE EXCEPTION";

    public CantInitializeCryptoCustomerIdentityDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoCustomerIdentityDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCryptoCustomerIdentityDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCryptoCustomerIdentityDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}