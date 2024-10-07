package com.bitdubai.fermat_pip_addon.layer._4_hardware.local_device.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer._3_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._2_os.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._4_hardware.Hardware;
import com.bitdubai.fermat_api.layer._4_hardware.local_device.LocalDeviceHardwareManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loui on 05/03/15.
 */
public class LocalDeviceHardwareAddonRoot implements  Addon, DealsWithErrors,DealsWithEvents , DealsWithPlatformFileSystem, Hardware,LocalDeviceHardwareManager ,Service {

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    PlatformFileSystem platformFileSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<EventListener>();





    /**
     * DealsWithFileSystem Interface Implementation.
     */

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;

    }

    /**
     * Error manager Interface Implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {

    }


    /**
     * Event Manager Interface Implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }



    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }


}
