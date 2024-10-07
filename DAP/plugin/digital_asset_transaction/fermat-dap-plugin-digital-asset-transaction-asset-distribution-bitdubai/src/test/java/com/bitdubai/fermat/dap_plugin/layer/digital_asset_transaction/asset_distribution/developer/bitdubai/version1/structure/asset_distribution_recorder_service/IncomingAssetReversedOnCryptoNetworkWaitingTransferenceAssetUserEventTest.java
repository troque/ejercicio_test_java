package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_recorder_service;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events.AssetDistributionRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 06/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomingAssetReversedOnCryptoNetworkWaitingTransferenceAssetUserEventTest {
    @Mock
    private EventManager eventManager;
    private AssetDistributionRecorderService assetDistributionRecorderService;

    private AssetDistributionDao assetDistributionDao = Mockito.mock(AssetDistributionDao.class);
    IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent event;
    @Before
    public void init() throws Exception {
        event = new IncomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent();
        EventSource eventSource = EventSource.getByCode(EventSource.ASSETS_OVER_BITCOIN_VAULT.getCode());
        event.setSource(eventSource);
        assetDistributionRecorderService = new AssetDistributionRecorderService(assetDistributionDao, eventManager);
    }

    @Test
    public void incomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEventTest () throws CantSaveEventException {
        assetDistributionRecorderService.incomingAssetReversedOnBlockchainWaitingTransferenceAssetUserEvent(event);
    }

}
