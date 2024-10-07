package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.PlatformDatabaseSystemAndroidAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.event_manager.developer.bitdubai.DeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Location Manager Platform Service Addon.
 *
 * Here we can choose between the different versions of the Platform Database System Addon.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformDatabaseSystemDeveloperBitDubai extends AbstractAddonDeveloper {

    public PlatformDatabaseSystemDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new PlatformDatabaseSystemAndroidAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
