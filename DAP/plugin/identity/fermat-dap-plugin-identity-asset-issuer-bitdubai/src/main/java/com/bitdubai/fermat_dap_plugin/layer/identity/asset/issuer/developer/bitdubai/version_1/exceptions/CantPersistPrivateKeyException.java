package com.bitdubai.fermat_dap_plugin.layer.identity.asset.issuer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantPersistPrivateKeyException extends FermatException{

    public CantPersistPrivateKeyException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}
