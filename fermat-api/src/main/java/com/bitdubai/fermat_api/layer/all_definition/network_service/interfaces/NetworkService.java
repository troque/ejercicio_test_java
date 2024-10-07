package com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 30.12.14.
 * Update by Roberto Requena - (rart3001@gmail.com) on 28/09/15.
 */
public interface NetworkService {

    /**
     * Get the PlatformComponentProfile for this network service
     * @return PlatformComponentProfile
     */
    PlatformComponentProfile getPlatformComponentProfilePluginRoot();

    /**
     * Get the PlatformComponentType for this network service
     * @return PlatformComponentType
     */
    PlatformComponentType getPlatformComponentType();

    /**
     * Get the NetworkServiceType for this network service
     * @return NetworkServiceType
     */
    NetworkServiceType getNetworkServiceType();

    /*
     * Get the RemoteNetworkServicesRegisteredList, this method may be called after
     * the <code>requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters</code> and the
     * <code>CompleteRequestListComponentRegisteredNotificationEvent</code> is raised.
     *
     * @return List<PlatformComponentProfile>
     */
    List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList();

    /**
     * Make a request of the Remote Network Services Registered in the cloud server, that
     * match with the DiscoveryQueryParameters pass as parameters.
     *
     * @param discoveryQueryParameters
     */
    void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters);

    /**
     * Get the NetworkServiceConnectionManager
     * @return NetworkServiceConnectionManager
     */
    NetworkServiceConnectionManager getNetworkServiceConnectionManager();

    /**
     * Construct a DiscoveryQueryParameters instance, for use in the process
     * of the discovery query to search all component register in the communication
     * cloud server that match with the params
     *
     * @param platformComponentType
     * @param networkServiceType
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param distance
     * @param name
     * @param extraData
     * @param firstRecord
     * @param numRegister
     * @param fromOtherPlatformComponentType
     * @param fromOtherNetworkServiceType
     * @return DiscoveryQueryParameters
     */
    DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType);


    /**
     * Handles the events CompleteComponentRegistrationNotification
     * @param platformComponentProfileRegistered
     */
    void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered);

    /**
     * Handles the events FailureComponentConnectionRequestNotificationEvent
     * @param networkServiceApplicant
     * @param remoteNetworkService
     */
    void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteNetworkService);


    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param platformComponentProfileRegisteredList
     */
    void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList);

    /**
     * Handles the events CompleteRequestListComponentRegisteredNotificationEvent
     * @param applicantComponentProfile
     */
    void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile);


    boolean isRegister();
}
