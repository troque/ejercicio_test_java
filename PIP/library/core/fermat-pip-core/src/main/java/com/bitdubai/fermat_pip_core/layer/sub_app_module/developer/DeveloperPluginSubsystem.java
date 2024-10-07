package com.bitdubai.fermat_pip_core.layer.sub_app_module.developer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.developer.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperPluginSubsystem extends AbstractPluginSubsystem {

    public DeveloperPluginSubsystem() {
        super(new PluginReference(Plugins.DEVELOPER));
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
