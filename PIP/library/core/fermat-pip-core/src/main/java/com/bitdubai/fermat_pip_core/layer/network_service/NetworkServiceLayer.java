package com.bitdubai.fermat_pip_core.layer.network_service;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_pip_core.layer.network_service.sub_app_resources.SubAppResourcesPluginSubsystem;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.NetworkServiceLayer</code>
 * contains all the necessary business logic to start the NetworkService Layer of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class NetworkServiceLayer extends AbstractLayer {

    public NetworkServiceLayer() {
        super(Layers.NETWORK_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new SubAppResourcesPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
