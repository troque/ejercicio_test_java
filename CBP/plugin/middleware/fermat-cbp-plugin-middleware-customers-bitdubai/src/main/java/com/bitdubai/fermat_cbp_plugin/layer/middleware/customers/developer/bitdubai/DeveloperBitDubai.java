package com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.customersPluginRoot;

/**
 * Created by Angel on 16.09.15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () { plugin = new customersPluginRoot();
    }

    @Override
    public int getAmountToPay() {
        return 0;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return null;
    }
}
