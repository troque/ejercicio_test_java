package com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.interfaces;


import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.exceptions.CantCreateCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_customer.exceptions.CantGetCryptoCustomerIdentityException;

import java.util.List;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface CryptoCustomerIdentityManager {

    List<CryptoCustomerIdentity> getAllCryptoCustomerFromCurrentDeviceUser() throws CantGetCryptoCustomerIdentityException;

    CryptoCustomerIdentity createCryptoCustomerIdentity(final String alias, final byte[] profileImage) throws CantCreateCryptoCustomerIdentityException;

}
