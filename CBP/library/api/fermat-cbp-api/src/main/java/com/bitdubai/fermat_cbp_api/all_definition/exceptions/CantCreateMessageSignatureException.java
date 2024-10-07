package com.bitdubai.fermat_cbp_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorge on 28-09-2015.
 */
public class CantCreateMessageSignatureException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE MESSAGE SIGNATURE";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantCreateMessageSignatureException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
