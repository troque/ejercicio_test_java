/*
 * @#DiscoveryQueryParameters.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.components.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters</code> this if use to pass
 * the parameters whit the communication cloud server search on all register PlatformComponentProfile to match whit this params
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 16/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DiscoveryQueryParameters {

    /**
     * Return the public key that represent the identity of the component
     *
     * @return String
     */
    public String getIdentityPublicKey();

    /**
     * Get the alias
     *
     * @return String
     */
    public String getAlias();

    /**
     * Get the name
     *
     * @return String
     */
    public String getName();

    /**
     * Get the location for geo localization
     *
     * @return Double
     */
    public Location getLocation();

    /**
     * Get the distance from geo localization
     *
     * @return Double
     */
    public Double getDistance();

    /**
     * Return the platform component type
     *
     * @return PlatformComponentType
     */
    public PlatformComponentType getPlatformComponentType();

    /**
     * Get the network service type of the packet
     *
     * @return NetworkServiceType
     */
    public NetworkServiceType getNetworkServiceType();

    /**
     * Return the extra data
     *
     * @return String
     */
    public String getExtraData();

    /**
     * Get the first record to make pagination
     *
     * @return int
     */
    public Integer getOffset();

    /**
     * Get the number of max the record to return
     *
     * @return int
     */
    public Integer getMax();

    /**
     * Return the platform component type from other component type,
     * this parameter indicate the filters are referent from other
     * platform component type, and need to find the component that match
     * with the PlatformComponentType specified in the other parameter
     *
     * @return PlatformComponentType
     */
    public PlatformComponentType getFromOtherPlatformComponentType();

    /**
     * Get the network service type of the packet from other component type,
     * this parameter indicate the filters are referent from other
     * platform component type and need to find the component that match
     * with the NetworkServiceType specified in the other parameter
     *
     * @return NetworkServiceType
     */
    public NetworkServiceType getFromOtherNetworkServiceType();

    /**
     * Convert this object to json string
     *
     * @return String json
     */
    public String toJson();

    /**
     * Convert to DiscoveryQueryParameters from json
     *
     * @param json string object
     * @return DiscoveryQueryParameters
     */
    public DiscoveryQueryParameters fromJson(String json);

}
