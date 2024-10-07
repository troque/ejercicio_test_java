package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common;

import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;

/**
 * Created by jorge on 28-10-2015.
 */
public interface CustomerBrokerNegotiationInformation extends NegotiationInformation {
    ActorIdentity getCustomer();

    ActorIdentity getBroker();
}
