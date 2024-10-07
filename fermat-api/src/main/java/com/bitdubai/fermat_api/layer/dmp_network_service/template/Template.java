/*
 * @#IntraUser.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_network_service.template;

import java.util.UUID;


/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_network_service.template.Template</code>
 * expose the basic method of a template
 * <p/>
 *
 * Created by ciencias on 2/13/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface Template {

    /**
     * Return the address
     *
     * @return String
     */
    public String getAddress();

    /**
     * Return the userId
     *
     * @return UUID
     */
    public UUID getUserId();

    /**
     * Return the userName
     *
     * @return String
     */
    public String getUserName();

    /**
     * Return the lastLocation
     *
     * @return String
     */
    public String getLastLocation();

    /**
     * Return the status
     *
     * @return TemplateStatus
     */
    public TemplateStatus getStatus();

    /**
     * Return the SmallProfilePicture
     *
     * @return String
     */
    public String getSmallProfilePicture();

    /**
     * Return the MediumProfilePicture
     *
     * @return String
     */
    public String getMediumProfilePicture();

    /**
     * Return the BigProfilePicture
     *
     * @return String
     */
    public String getBigProfilePicture();

    /**
     * Return the createdTime
     *
     * @return String
     */
    public Long getCreatedTime();

    /**
     * Return the updateTime
     *
     * @return String
     */
    public Long getUpdateTime();

}
