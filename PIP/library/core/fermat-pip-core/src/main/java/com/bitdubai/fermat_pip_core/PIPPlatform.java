package com.bitdubai.fermat_pip_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_pip_core.layer.engine.EngineLayer;
import com.bitdubai.fermat_pip_core.layer.network_service.NetworkServiceLayer;
import com.bitdubai.fermat_pip_core.layer.platform_service.PlatformServiceLayer;
import com.bitdubai.fermat_pip_core.layer.sub_app_module.SubAppModuleLayer;
import com.bitdubai.fermat_pip_core.layer.user.UserLayer;

/**
 * The class <code>com.bitdubai.fermat_pip_core.PIPPlatform</code>
 * contains all the necessary business logic to start the PIP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public final class PIPPlatform extends AbstractPlatform {

    public PIPPlatform() {
        super(new PlatformReference(Platforms.PLUG_INS_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new EngineLayer());
            registerLayer(new NetworkServiceLayer());
            registerLayer(new PlatformServiceLayer());
            registerLayer(new SubAppModuleLayer());
            registerLayer(new UserLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
