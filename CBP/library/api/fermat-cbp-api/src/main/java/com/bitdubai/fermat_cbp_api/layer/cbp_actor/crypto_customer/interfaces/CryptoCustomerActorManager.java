package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.actor.Actor;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantGetCryptoCustomerActorException;

/**
 * Created by jorge on 26-10-2015.
 */
public interface CryptoCustomerActorManager {
    CryptoCustomerActor createNewCryptoCustomerActor(ActorIdentity identity) throws CantCreateCryptoCustomerActorException;
    CryptoCustomerActor getCryptoCustomer(ActorIdentity identity) throws CantGetCryptoCustomerActorException;
}
