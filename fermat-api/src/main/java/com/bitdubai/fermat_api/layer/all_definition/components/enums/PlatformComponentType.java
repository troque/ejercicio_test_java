/*
 * @#PlatformComponentType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.components.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType</code> define
 * all types that a platform component can be
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum PlatformComponentType implements FermatEnum {

    // Definition types

    ACTOR_ASSET_USER            ("ACT_ASU"),
    ACTOR_ASSET_ISSUER          ("ACT_ASI"),
    ACTOR_ASSET_REDEEM_POINT    ("ACT_ASR"),
    ACTOR_INTRA_USER            ("ACT_IU"),
    ACTOR_NETWORK_SERVICE       ("ANS"),
    COMMUNICATION_CLOUD_CLIENT  ("COM_CLD_CLI"),
    COMMUNICATION_CLOUD_SERVER  ("COM_CLD_SER"),
    NETWORK_SERVICE             ("NS"),;

    /**
     * Represent the code
     */
    private String code;

    /**
     * Constructor whit parameter
     *
     * @param code
     */
    PlatformComponentType(String code){
        this.code = code;
    }

    /**
     * Return the code representation
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Return the PlatformComponentType represented by the code pass as parameter
     *
     * @param code
     * @return PlatformComponentType
     */
    public static PlatformComponentType getByCode(final String code){

        switch (code){

            case "ACT_IU"      : return ACTOR_INTRA_USER;
            case "ACT_ASU"     : return ACTOR_ASSET_USER;
            case "ACT_ASI"     : return ACTOR_ASSET_ISSUER;
            case "ACT_ASR"     : return ACTOR_ASSET_REDEEM_POINT;
            case "ANS"         : return ACTOR_NETWORK_SERVICE;
            case "COM_CLD_CLI" : return COMMUNICATION_CLOUD_CLIENT;
            case "COM_CLD_SER" : return COMMUNICATION_CLOUD_SERVER;
            case "NS"          : return NETWORK_SERVICE;

            default: throw new IllegalArgumentException();
        }
    }

}
