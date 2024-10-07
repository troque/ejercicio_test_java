package com.bitdubai.fermat_bch_core.layer.middleware;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_asset_vault.BitcoinAssetVaultPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_vault.BitcoinVaultPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_watch_only_vault.BitcoinWatchOnlyVaultPluginSubsystem;
import com.bitdubai.fermat_bch_core.layer.middleware.crypto_addresses.CryptoAddressesPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MiddlewareLayer extends AbstractLayer {

    public MiddlewareLayer() {
        super(Layers.MIDDLEWARE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoAddressesPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
