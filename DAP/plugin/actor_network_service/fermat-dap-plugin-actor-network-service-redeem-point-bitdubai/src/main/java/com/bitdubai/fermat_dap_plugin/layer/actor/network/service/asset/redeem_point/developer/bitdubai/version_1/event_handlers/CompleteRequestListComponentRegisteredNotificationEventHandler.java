package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteRequestListComponentRegisteredNotificationEvent;

/**
 * Created by franklin on 18/10/15.
 */
public class CompleteRequestListComponentRegisteredNotificationEventHandler implements FermatEventHandler {

    /*
    * Represent the networkService
    */
    private NetworkService networkService;

    /**
     * Constructor with parameter
     *
     * @param networkService
     */
    public CompleteRequestListComponentRegisteredNotificationEventHandler(NetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * (non-Javadoc)
     *
     * @see FermatEventHandler#handleEvent(FermatEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("CompleteRequestListComponentRegisteredNotificationEventHandler - handleEvent platformEvent ="+platformEvent );


        if (((Service) this.networkService).getStatus() == ServiceStatus.STARTED) {

            CompleteRequestListComponentRegisteredNotificationEvent completeRequestListComponentRegisteredNotificationEvent = (CompleteRequestListComponentRegisteredNotificationEvent) platformEvent;


            if (completeRequestListComponentRegisteredNotificationEvent.getNetworkServiceTypeApplicant() == networkService.getNetworkServiceType()){

                 /*
                 *  networkService make the job
                 */
                this.networkService.handleCompleteRequestListComponentRegisteredNotificationEvent(completeRequestListComponentRegisteredNotificationEvent.getRegisteredComponentList());

            }
        }
    }

}
