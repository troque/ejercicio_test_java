package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 20/02/15.
 */
public class IncomingCryptoReceptionConfirmedEvent extends AbstractFermatEvent {


    public IncomingCryptoReceptionConfirmedEvent(EventType eventType){
        super(eventType);
    }


}
