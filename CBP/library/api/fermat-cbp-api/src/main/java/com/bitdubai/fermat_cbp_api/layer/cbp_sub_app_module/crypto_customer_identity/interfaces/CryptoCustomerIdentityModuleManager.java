package com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CantGetCryptoCustomerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CouldNotPublishCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.exceptions.CouldNotUnPublishCryptoCustomerException;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager</code>
 * provides the methods for the Crypto Customer Identity sub app.
 */

public interface CryptoCustomerIdentityModuleManager extends ModuleManager {



    /**
     * The method <code>createCryptoCustomerIdentity</code> is used to create a new crypto Customer identity
     *
     * @param cryptoBrokerName the name of the crypto Customer to create
     * @param profileImage  the profile image of the crypto Customer to create
     * @return the crypto Customer identity generated.
     * @throws CouldNotCreateCryptoCustomerException
     */
    public CryptoCustomerIdentityInformation createCryptoCustomerIdentity(String cryptoBrokerName, byte[] profileImage) throws CouldNotCreateCryptoCustomerException;

    /**
     * The method <code>publishCryptoBrokerIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoCustomerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CouldNotPublishCryptoBrokerException
     */
    public void publishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotPublishCryptoCustomerException;

    /**
     * The method <code>publishCryptoBrokerIdentity</code> is used to publish a Broker identity
     *
     * @param cryptoCustomerPublicKey the public key of the crypto Broker to publish
     *
     * @throws CouldNotPublishCryptoBrokerException
     */
    public void unPublishCryptoCustomerIdentity(String cryptoCustomerPublicKey) throws CouldNotUnPublishCryptoCustomerException;

    /**
     * The method <code>getAllCryptoCustomerIdentities</code> returns the list of all crypto Customer
     *
     * @return the list of crypto Customer
     * @throws CantGetCryptoCustomerListException
     */
    public List<CryptoCustomerIdentityInformation> getAllCryptoCustomersIdentities(int max, int offset) throws CantGetCryptoCustomerListException;



}