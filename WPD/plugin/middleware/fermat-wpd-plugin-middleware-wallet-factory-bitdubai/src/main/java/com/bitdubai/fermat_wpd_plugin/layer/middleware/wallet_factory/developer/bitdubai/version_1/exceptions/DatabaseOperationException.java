package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 8/18/15.
 */
public class DatabaseOperationException extends FermatException{
    public static final String DEFAULT_MESSAGE = "There was an error trying to execute a database operation.";

    public DatabaseOperationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
