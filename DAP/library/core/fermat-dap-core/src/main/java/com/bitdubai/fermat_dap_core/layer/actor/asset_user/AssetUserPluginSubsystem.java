package com.bitdubai.fermat_dap_core.layer.actor.asset_user;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by PatricioGesualdi - (pmgesualdi@hotmail.com) on 10/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetUserPluginSubsystem extends AbstractPluginSubsystem {

    public AssetUserPluginSubsystem() {
        super(new PluginReference(Plugins.ASSET_USER));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
