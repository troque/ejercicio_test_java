package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingCryptoReversedEvent extends AbstractFermatEvent {

    public IncomingCryptoReversedEvent(EventType eventType){
        super(eventType);
    }



}
