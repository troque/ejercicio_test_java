package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_monitor_agent;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events.AssetDistributionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.fail;

/**
 * Created by Luis Campo (campusprize@gmail.com)on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetAssetTransmissionManagerTest {

    private String userPublicKey;
    @Mock
    private EventManager eventManager;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    @Mock
    private AssetVaultManager assetVaultManager;
    @Mock
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;

    private AssetDistributionMonitorAgent assetDistributionMonitorAgent;

    @Before
    public void init() throws CantSetObjectException {
        userPublicKey = new ECCKeyPair().getPublicKey();
        pluginId = UUID.randomUUID();
        assetDistributionMonitorAgent = new AssetDistributionMonitorAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userPublicKey, assetVaultManager);
    }

    @Test
    public void setAssetTransmissionManagerTest() throws CantSetObjectException {
        assetDistributionMonitorAgent.setAssetTransmissionManager(assetTransmissionManager);
    }

    @Test
    public void setAssetTransmissionManagerThrowsCantSetObjectExceptionTest() throws CantSetObjectException {
        try {
            assetDistributionMonitorAgent.setAssetTransmissionManager(null);
            fail("The method didn't throw when I expected it to");
        }catch (Exception ex) {
            Assert.assertTrue(ex instanceof CantSetObjectException);
        }
    }

}
