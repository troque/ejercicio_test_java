package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alex on 11/2/2015.
 */
public class CantGetInputStreamException extends FermatException{

    public static final String DEFAULT_MESSAGE ="CANT GET INPUT STREAM";

    public CantGetInputStreamException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
