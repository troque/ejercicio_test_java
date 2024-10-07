package com.bitdubai.fermat_cbp_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_core.layer.identity.IdentityLayer;
import com.bitdubai.fermat_cbp_core.layer.sub_app_module.SubAppModuleLayer;
import com.bitdubai.fermat_cbp_core.layer.wallet_module.WalletModuleLayer;

/**
 * The class <code>com.bitdubai.fermat_cbp_core.CBPPlatform</code>
 * haves all the necessary business logic to start the CBP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 */
public class CBPPlatform extends AbstractPlatform {

    public CBPPlatform() {
        super(new PlatformReference(Platforms.CRYPTO_BROKER_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new IdentityLayer() );
            registerLayer(new SubAppModuleLayer() );
            registerLayer(new WalletModuleLayer() );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
