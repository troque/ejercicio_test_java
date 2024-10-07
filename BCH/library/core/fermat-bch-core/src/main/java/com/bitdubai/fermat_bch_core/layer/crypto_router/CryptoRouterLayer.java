package com.bitdubai.fermat_bch_core.layer.crypto_router;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_bch_core.layer.crypto_router.incoming_crypto.IncomingCryptoPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoRouterLayer extends AbstractLayer {

    public CryptoRouterLayer() {
        super(Layers.CRYPTO_ROUTER);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new IncomingCryptoPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
