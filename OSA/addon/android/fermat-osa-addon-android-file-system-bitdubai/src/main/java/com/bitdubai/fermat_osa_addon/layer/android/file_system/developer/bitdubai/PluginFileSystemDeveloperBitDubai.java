package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.PluginFileSystemAndroidAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.PluginFileSystemDeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Plugin File System Android Addon.
 *
 * Here we can choose between the different versions of the Plugin File System Addon.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class PluginFileSystemDeveloperBitDubai extends AbstractAddonDeveloper {

    public PluginFileSystemDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new PluginFileSystemAndroidAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
