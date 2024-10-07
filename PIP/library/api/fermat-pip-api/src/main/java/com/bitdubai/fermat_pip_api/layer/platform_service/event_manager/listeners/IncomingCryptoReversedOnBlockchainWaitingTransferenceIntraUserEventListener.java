package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * Created by eze on 2015.09.02..
 */
public class IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener extends com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener {
    public IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventListener(FermatEventMonitor fermatEventMonitor) {
        super(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, fermatEventMonitor);
    }
}
