package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

/**
 * Created by rodrigo on 10/15/15.
 */
public class IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent extends AbstractFermatEvent {
    public IncomingAssetOnBlockchainWaitingTransferenceAssetUserEvent() {
        super(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER);
    }
}
