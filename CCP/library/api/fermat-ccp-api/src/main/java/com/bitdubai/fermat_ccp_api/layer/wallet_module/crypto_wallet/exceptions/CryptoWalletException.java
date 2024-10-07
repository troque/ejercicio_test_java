package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exception.CryptoWalletException</code>
 * is thrown when there's an exception in WalletModuleCryptoWallet Plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/15.
 * @version 1.0
 */
public class CryptoWalletException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE WALLET MODULE CRYPTO WALLET REQUESTED HAS TRIGGERED AN EXCEPTION";

    public CryptoWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoWalletException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CryptoWalletException(final String message) {
        this(message, null);
    }

    public CryptoWalletException() {
        this(DEFAULT_MESSAGE);
    }
}
