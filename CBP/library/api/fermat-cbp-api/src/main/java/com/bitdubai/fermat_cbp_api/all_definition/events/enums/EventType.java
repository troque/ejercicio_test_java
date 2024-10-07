package com.bitdubai.fermat_cbp_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionNewsEvent;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.fermat_cbp_api.events.enums.EventType</code>
 * represent the different type of events found on cbp platform.<p/>
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    CRYPTO_BROKER_CONNECTION_NEWS("CBCNWS") {
        public final FermatEvent getNewEvent() { return new CryptoBrokerConnectionNewsEvent(this); }
    },

    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.CRYPTO_BROKER_PLATFORM;
    }

}
