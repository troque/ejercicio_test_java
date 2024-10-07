package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CryptoWalletException;

/**
 * The Class <code>CantCreateOrRegisterActorException</code>
 * is thrown when i cant create or register an actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantEnrichTransactionException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE OR REGISTER ACTOR EXCEPTION";

    public CantEnrichTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
