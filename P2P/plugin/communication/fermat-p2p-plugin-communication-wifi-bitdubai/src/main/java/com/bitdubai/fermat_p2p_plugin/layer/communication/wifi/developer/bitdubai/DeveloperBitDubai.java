package com.bitdubai.fermat_p2p_plugin.layer.communication.wifi.developer.bitdubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
import com.bitdubai.fermat_p2p_plugin.layer.communication.wifi.developer.bitdubai.version_1.WifiCommunicationPluginRoot;

/**
 * Created by ciencias on 20.01.15.
 */
public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    
    Plugin plugin;


    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () {



        plugin = new WifiCommunicationPluginRoot();

    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
}
