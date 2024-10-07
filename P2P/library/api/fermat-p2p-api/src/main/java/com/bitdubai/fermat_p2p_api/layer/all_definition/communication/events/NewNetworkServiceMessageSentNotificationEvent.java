/*
 * @#NewNetworkServiceMessageSentNotificationEvent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.NewNetworkServiceMessageSentNotificationEvent</code> represent the event
 * when a new Network Service Message is received
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NewNetworkServiceMessageSentNotificationEvent extends AbstractP2PFermatEvent {

    /**
     *  Represent the data
     */
    private Object data;

    /**
     * Constructor with parameter
     *
     * @param p2pEventType type of the event
     */
    public NewNetworkServiceMessageSentNotificationEvent(P2pEventType p2pEventType){
        super(p2pEventType);
    }


    /**
     * Return the data object that contains message received
     *
     * @return message received
     */
    public Object getData() {
        return data;
    }

    /**
     * Set data object that contains the message received
     */
    public void setData(Object data) {
        this.data = data;
    }
}
