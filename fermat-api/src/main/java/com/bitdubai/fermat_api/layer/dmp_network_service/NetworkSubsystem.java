package com.bitdubai.fermat_api.layer.dmp_network_service;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface NetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
