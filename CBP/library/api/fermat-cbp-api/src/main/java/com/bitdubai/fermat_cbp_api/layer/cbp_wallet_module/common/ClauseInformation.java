package com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;

/**
 * Created by jorge on 28-10-2015.
 */
public interface ClauseInformation {
    ClauseType getType();
    String getValue();
    ClauseStatus getStatus();
}
