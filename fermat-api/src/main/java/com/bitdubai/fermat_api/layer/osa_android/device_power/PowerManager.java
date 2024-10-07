package com.bitdubai.fermat_api.layer.osa_android.device_power;


/**
 * Created by Natalia on 04/05/2015.
 */
public interface PowerManager {

    public int getLevel();

    public PowerStatus getStatus();

    public PlugType getPlugType();

    public void setContext (Object context);
}
