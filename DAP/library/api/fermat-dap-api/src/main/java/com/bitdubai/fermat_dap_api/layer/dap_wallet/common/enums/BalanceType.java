package com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 24/09/15.
 */
public enum BalanceType {
    AVAILABLE("AVAILABLE"),
    BOOK("BOOK");

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static BalanceType getByCode(String code) {

        switch (code) {
            case "BOOK":      return BOOK;
            case "AVAILABLE": return AVAILABLE;
            default:          return AVAILABLE;
            //TODO: throw and handle this exception
                //throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BalanceType enum.");
        }
    }
}
