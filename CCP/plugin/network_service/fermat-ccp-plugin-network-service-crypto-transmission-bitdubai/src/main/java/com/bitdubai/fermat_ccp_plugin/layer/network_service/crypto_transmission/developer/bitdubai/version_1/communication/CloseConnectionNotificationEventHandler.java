package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.communication;

import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.template.event_handlers.AbstractCommunicationBaseEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentConnectionRequestNotificationEvent;

/**
 * Created by Matias Furszyfer on 2015.10.23..
 */
public class CloseConnectionNotificationEventHandler extends AbstractCommunicationBaseEventHandler<CompleteComponentConnectionRequestNotificationEvent> {

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CloseConnectionNotificationEventHandler(NetworkService networkService) {
        super(networkService);
    }

    /**
     * (non-Javadoc)
     * */

    @Override
    public void processEvent(CompleteComponentConnectionRequestNotificationEvent event) {
        this.networkService.handleCompleteComponentConnectionRequestNotificationEvent(event.getApplicantComponent(), event.getRemoteComponent());

    }
}
