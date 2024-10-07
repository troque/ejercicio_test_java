package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerWalletException;

/**
 * Created by angel on 17/9/15.
 */
public interface CryptoCustomerWalletModuleManager extends ModuleManager {

    /**
     * @param walletPublicKey the public key of the wallet
     * @return an interface the contain the methods to manipulate the selected wallet
     */
    CryptoCustomerWallet getCryptoCustomerWallet(String walletPublicKey) throws CantGetCryptoCustomerWalletException;
}
