package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum class <code>com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices</code>
 * enumerates all the NetworkServices of fermat.
 * <p/>
 * Created by ciencias on 2/22/15.
 * Modified by Manuel Perez on 03/08/2015
 * Updated by PatricioGesualdi - (pmgesualdi@hotmail.com) on 18/11/2015.
 */
public enum NetworkServices implements FermatEnum {
    /**
     * In order to make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    BANK_NOTES          ("BNOTES"),
    CRYPTO_ADDRESSES    ("CRYPTADD"),
    INTRA_USER          ("IUS"),
    MONEY               ("MONEY"),
    TEMPLATE            ("TEMPLATE"),
    UNDEFINED           ("UNDEF"),
    WALLET_COMMUNITY    ("WALLCOMM"),
    WALLET_RESOURCES    ("WALLRES"),
    WALLET_STORE        ("WALLSTO")

    ;

    private final String code;

    NetworkServices(String code) {
        this.code = code;
    }

    public static NetworkServices getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "BNOTES":   return NetworkServices.BANK_NOTES;
            case "CRYPTADD": return NetworkServices.CRYPTO_ADDRESSES;
            case "IUS":      return NetworkServices.INTRA_USER;
            case "MONEY":    return NetworkServices.MONEY;
            case "TEMPLATE": return NetworkServices.TEMPLATE;
            case "UNDEF":    return NetworkServices.UNDEFINED;
            case "WALLCOMM": return NetworkServices.WALLET_COMMUNITY;
            case "WALLRES":  return NetworkServices.WALLET_RESOURCES;
            case "WALLSTO":  return NetworkServices.WALLET_STORE;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "The recieved code is not valid for the NetworkServices enum"
                );
        }
    }

    @Override
    public String getCode() { return this.code; }
}