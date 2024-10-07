/*
 * @#AssetTransmissionPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantSendCryptoAddressException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.agents.AssetUserActorNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.CommunicationNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.database.communications.OutgoingMessageDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.CompleteComponentConnectionRequestNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.CompleteComponentRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers.CompleteRequestListComponentRegisteredNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeTemplateNetworkServiceDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantUpdateRecordDataBaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.util.JsonAssetUserANSAttNamesConstants;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes.AbstractNetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunication;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatMessageCommunicationFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessageContentType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.FermatMessagesStatus;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRegisterComponentException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetUserActorNetworkServicePluginRoot extends AbstractNetworkService implements
        AssetUserActorNetworkServiceManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    private WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**
     * Represent the EVENT_SOURCE
     */
    public final static EventSource EVENT_SOURCE = EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER;

    private List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the communicationNetworkServiceConnectionManager
     */
    private CommunicationNetworkServiceConnectionManager communicationNetworkServiceConnectionManager;

    /**
     * Represent the remoteNetworkServicesRegisteredList
     */
    private List<PlatformComponentProfile> remoteNetworkServicesRegisteredList;

    /**
     * Represent the actorAssetUserPendingToRegistration
     */
    private List<PlatformComponentProfile> actorAssetUserPendingToRegistration;

    /**
     * Represent the communicationNetworkServiceDeveloperDatabaseFactory
     */
    private CommunicationNetworkServiceDeveloperDatabaseFactory communicationNetworkServiceDeveloperDatabaseFactory;

    /**
     * Represent the communicationRegistrationProcessNetworkServiceAgent
     */
    private CommunicationRegistrationProcessNetworkServiceAgent communicationRegistrationProcessNetworkServiceAgent;

    /**
     * Represent the actorAssetUserRegisteredList
     */
    private List<ActorAssetUser> actorAssetUserRegisteredList;

    private AssetUserActorNetworkServiceAgent assetUserActorNetworkServiceAgent;

    private int createactorAgentnum = 0;

    /**
     * Constructor
     */
    public AssetUserActorNetworkServicePluginRoot() {
        super(
                new PluginVersionReference(new Version()),
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.ASSET_USER_ACTOR,
                "Actor Network Service Asset User",
                "ActorNetworkServiceAssetUser",
                null,
                EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER
        );
        this.listenersAdded = new ArrayList<>();

        this.actorAssetUserRegisteredList = new ArrayList<>();
        this.actorAssetUserPendingToRegistration = new ArrayList<>();
    }

    /**
     * Get the IdentityPublicKey
     *
     * @return String
     */
    public String getIdentityPublicKey() {
        return this.identity.getPublicKey();
    }

    /**
     * This method initialize the communicationNetworkServiceConnectionManager.
     * IMPORTANT: Call this method only in the CommunicationRegistrationProcessNetworkServiceAgent, when execute the registration process
     * because at this moment, is create the platformComponentProfile for this component
     */
    public void initializeCommunicationNetworkServiceConnectionManager() {
        this.communicationNetworkServiceConnectionManager = new CommunicationNetworkServiceConnectionManager(
                getPlatformComponentProfilePluginRoot(),
                identity,
                wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(),
                dataBase,
                errorManager,
                eventManager);
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.AssetUserActorNetworkServicePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationRegistrationProcessNetworkServiceAgent");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceLocal");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceConnectionManager");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.communications.CommunicationNetworkServiceRemoteAgent");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try {
            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (AssetUserActorNetworkServicePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetUserActorNetworkServicePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + AssetUserActorNetworkServicePluginRoot.newLoggingLevel, "Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {

        try {
            /*
             * Create a new key pair for this execution
             */
            loadKeyPair(pluginFileSystem);
        } catch (CantLoadKeyPairException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "", "Problem trying to load the key pair of the plugin.");
        }

        System.out.println("Start Plugin Asset User ActorNetworkService");
        logManager.log(AssetUserActorNetworkServicePluginRoot.getLogLevelByClass(this.getClass().getName()), "AssetUserActorNetworkService - Starting", "AssetUserActorNetworkServicePluginRoot - Starting", "AssetUserActorNetworkServicePluginRoot - Starting");

        /*
         * Validate required resources
         */
        validateInjectedResources();

        try {
           /*
            * Initialize the data base
            */
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            communicationNetworkServiceDeveloperDatabaseFactory = new CommunicationNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            communicationNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

            /*
             * Initialize listeners
             */
            initializeListener();

            /*
             * Verify if the communication cloud client is active
             */
            if (!wsCommunicationsCloudClientManager.isDisable()) {

                /*
                 * Initialize the agent and start
                 */
                communicationRegistrationProcessNetworkServiceAgent = new CommunicationRegistrationProcessNetworkServiceAgent(this, wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection());
                communicationRegistrationProcessNetworkServiceAgent.start();
            }

            /*
             * Its all ok, set the new status
            */
            this.serviceStatus = ServiceStatus.STARTED;

        } catch (CantInitializeTemplateNetworkServiceDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Asset User Actor Network Service Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public void pause() {
        /*
         * Pause
         */
        communicationNetworkServiceConnectionManager.pause();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        /*
         * resume the managers
         */
        communicationNetworkServiceConnectionManager.resume();

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        //Clear all references of the event listeners registered with the event manager.
        listenersAdded.clear();

        /*
         * Stop all connection on the managers
         */
        communicationNetworkServiceConnectionManager.closeAllConnection();

        //set to not register
        register = Boolean.FALSE;

        /*
         * Set the new status
         */
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public void registerActorAssetUser(ActorAssetUser actorAssetUserToRegister) throws CantRegisterActorAssetUserException {

        try {
            CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
            /*
             * If register
             */
            if (this.isRegister()) {

                System.out.println("*************************************");
                System.out.println("Actor Asset User - Registrar Datos " + actorAssetUserToRegister.getName());
                System.out.println("*************************************");

                /*
                 * Construct the profile
                 */
                PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(actorAssetUserToRegister.getPublicKey(),
                        actorAssetUserToRegister.getName().toLowerCase().trim(),
                        actorAssetUserToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_USER,
                        Arrays.toString(actorAssetUserToRegister.getProfileImage()));
                /*
                 * ask to the communication cloud client to register
                 */
                communicationsClientConnection.registerComponentForCommunication(getNetworkServiceType(), platformComponentProfileAssetUser);
            } else {
                /*
                 * Construct the profile
                 */
                PlatformComponentProfile platformComponentProfileAssetUser = communicationsClientConnection.constructPlatformComponentProfileFactory(actorAssetUserToRegister.getPublicKey(),
                        actorAssetUserToRegister.getName().toLowerCase().trim(),
                        actorAssetUserToRegister.getName(),
                        NetworkServiceType.UNDEFINED,
                        PlatformComponentType.ACTOR_ASSET_USER,
                        Arrays.toString(actorAssetUserToRegister.getProfileImage()));
                /*
                 * Add to the list of pending to register
                 */
                actorAssetUserPendingToRegistration.add(platformComponentProfileAssetUser);
            }
        } catch (Exception e) {
            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Plugin was not registered";

            CantRegisterActorAssetUserException pluginStartException = new CantRegisterActorAssetUserException(CantStartPluginException.DEFAULT_MESSAGE, e, context, possibleCause);
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public List<ActorAssetUser> getListActorAssetUserRegistered() throws CantRequestListActorAssetUserRegisteredException {

        try {
            if (this.isRegister()) {
                if (actorAssetUserRegisteredList != null && !actorAssetUserRegisteredList.isEmpty()) {
                    actorAssetUserRegisteredList.clear();
                }
                DiscoveryQueryParameters discoveryQueryParametersAssetUser = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().
                        constructDiscoveryQueryParamsFactory(PlatformComponentType.ACTOR_ASSET_USER, //applicant = who made the request
                                NetworkServiceType.UNDEFINED,
                                null,                     // alias
                                null,                     // identityPublicKey
                                null,                     // location
                                null,                     // distance
                                null,                     // name
                                null,                     // extraData
                                null,                     // offset
                                null,                     // max
                                null,                     // fromOtherPlatformComponentType, when use this filter apply the identityPublicKey
                                null);

                List<PlatformComponentProfile> platformComponentProfileRegisteredListRemote = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(discoveryQueryParametersAssetUser);

                if (platformComponentProfileRegisteredListRemote != null && !platformComponentProfileRegisteredListRemote.isEmpty()) {

                    for (PlatformComponentProfile p : platformComponentProfileRegisteredListRemote) {

                        ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(p.getIdentityPublicKey(), p.getName(), convertoByteArrayfromString(p.getExtraData()), p.getLocation());
                        actorAssetUserRegisteredList.add(actorAssetUserNew);
                    }

                } else {
                    return actorAssetUserRegisteredList;
                }

            } else {
                return actorAssetUserRegisteredList;
            }
        } catch (CantRequestListException e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request List Actor Asset User Registered";

            CantRequestListActorAssetUserRegisteredException pluginStartException = new CantRequestListActorAssetUserRegisteredException(CantRequestListActorAssetUserRegisteredException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
        return actorAssetUserRegisteredList;
    }


    @Override
    public void requestCryptoAddress(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserDestination) throws CantRequestCryptoAddressException {

        try {

            if (this.isRegister()) {

                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorAssetUserDestination.getPublicKey());

                Gson gson = new Gson();
                JsonObject packetContent = new JsonObject();
                packetContent.addProperty(JsonAssetUserANSAttNamesConstants.ISSUER, gson.toJson(actorAssetIssuerSender));
                packetContent.addProperty(JsonAssetUserANSAttNamesConstants.USER, gson.toJson(actorAssetUserDestination));


                String messageContentIntoJson = gson.toJson(packetContent);

                if (communicationNetworkServiceLocal != null) {

                    //Send the message
                    communicationNetworkServiceLocal.sendMessage(actorAssetIssuerSender.getPublicKey(), actorAssetUserDestination.getPublicKey(), messageContentIntoJson);

                } else {

                    /*
                     * Created the message
                     */
                    FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(actorAssetIssuerSender.getPublicKey(),//Sender
                            actorAssetUserDestination.getPublicKey(), //Receiver
                            messageContentIntoJson,                //Message Content
                            FermatMessageContentType.TEXT);//Type

                    /*
                     * Configure the correct status
                     */
                    ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

                    /*
                     * Save to the data base table
                     */
                    OutgoingMessageDao outgoingMessageDao = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
                    outgoingMessageDao.create(fermatMessage);

                    /*
                     * Create the sender basic profile
                     */
                    PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorAssetIssuerSender.getPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_ISSUER);

                    /*
                     * Create the receiver basic profile
                     */
                    PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorAssetUserDestination.getPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_USER);

                    /*
                     * Ask the client to connect
                     */
                    communicationNetworkServiceConnectionManager.connectTo(sender, getPlatformComponentProfilePluginRoot(), receiver);

                }


            } else {

                StringBuffer contextBuffer = new StringBuffer();
                contextBuffer.append("Plugin ID: " + pluginId);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("errorManager: " + errorManager);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("eventManager: " + eventManager);

                String context = contextBuffer.toString();
                String possibleCause = "Asset User Actor Network Service Not Registered";

                CantRequestCryptoAddressException pluginStartException = new CantRequestCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

                throw pluginStartException;

            }

        } catch (Exception e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Request Crypto Address";

            CantRequestCryptoAddressException pluginStartException = new CantRequestCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }

    }

    @Override
    public void sendCryptoAddress(ActorAssetUser actorAssetUserSender, ActorAssetIssuer actorAssetIssuerDestination, CryptoAddress cryptoAddress) throws CantSendCryptoAddressException {

        try {


            if (this.isRegister()) {
                CommunicationNetworkServiceLocal communicationNetworkServiceLocal = communicationNetworkServiceConnectionManager.getNetworkServiceLocalInstance(actorAssetIssuerDestination.getPublicKey());

                Gson gson = new Gson();
                JsonObject packetContent = new JsonObject();
                packetContent.addProperty(JsonAssetUserANSAttNamesConstants.ISSUER, gson.toJson(actorAssetIssuerDestination));
                packetContent.addProperty(JsonAssetUserANSAttNamesConstants.USER, gson.toJson(actorAssetUserSender));
                packetContent.addProperty(JsonAssetUserANSAttNamesConstants.CRYPTOADDRES, gson.toJson(cryptoAddress));

                String messageContentIntoJson = gson.toJson(packetContent);

                if (communicationNetworkServiceLocal != null) {

                    //Send the message
                    communicationNetworkServiceLocal.sendMessage(actorAssetUserSender.getPublicKey(), actorAssetIssuerDestination.getPublicKey(), messageContentIntoJson);

                } else {

                    /*
                     * Created the message
                     */
                    FermatMessage fermatMessage = FermatMessageCommunicationFactory.constructFermatMessage(actorAssetUserSender.getPublicKey(),//Sender
                            actorAssetIssuerDestination.getPublicKey(), //Receiver
                            messageContentIntoJson,                //Message Content
                            FermatMessageContentType.TEXT);//Type

                    /*
                     * Configure the correct status
                     */
                    ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.PENDING_TO_SEND);

                    /*
                     * Save to the data base table
                     */
                    OutgoingMessageDao outgoingMessageDao = communicationNetworkServiceConnectionManager.getOutgoingMessageDao();
                    outgoingMessageDao.create(fermatMessage);

                    /*
                     * Create the sender basic profile
                     */
                    PlatformComponentProfile sender = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorAssetUserSender.getPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_USER);

                    /*
                     * Create the receiver basic profile
                     */
                    PlatformComponentProfile receiver = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructBasicPlatformComponentProfileFactory(actorAssetIssuerDestination.getPublicKey(), NetworkServiceType.UNDEFINED, PlatformComponentType.ACTOR_ASSET_ISSUER);

                    /*
                     * Ask the client to connect
                     */
                    communicationNetworkServiceConnectionManager.connectTo(sender, getPlatformComponentProfilePluginRoot(), receiver);

                }

            } else {


                StringBuffer contextBuffer = new StringBuffer();
                contextBuffer.append("Plugin ID: " + pluginId);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("errorManager: " + errorManager);
                contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
                contextBuffer.append("eventManager: " + eventManager);

                String context = contextBuffer.toString();
                String possibleCause = "Cant Request Crypto Address";

                CantSendCryptoAddressException pluginStartException = new CantSendCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

                throw pluginStartException;

            }


        } catch (Exception e) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "Cant Send Message";

            CantSendCryptoAddressException pluginStartException = new CantSendCryptoAddressException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);

            throw pluginStartException;
        }
    }

    @Override
    public List<PlatformComponentProfile> getRemoteNetworkServicesRegisteredList() {
        return remoteNetworkServicesRegisteredList;
    }

    @Override
    public void requestRemoteNetworkServicesRegisteredList(DiscoveryQueryParameters discoveryQueryParameters) {

        System.out.println("Asset User Actor NetworkServicePluginRoot - requestRemoteNetworkServicesRegisteredList");

        /*
         * Request the list of component registers
         */
        try {
            wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().requestListComponentRegistered(getPlatformComponentProfilePluginRoot(), discoveryQueryParameters);
        } catch (CantRequestListException e) {
            e.printStackTrace();
        }

    }

    @Override
    public NetworkServiceConnectionManager getNetworkServiceConnectionManager() {
        return communicationNetworkServiceConnectionManager;
    }

    @Override
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection().constructDiscoveryQueryParamsFactory(platformComponentType,
                networkServiceType, alias, identityPublicKey, location, distance, name, extraData, firstRecord, numRegister, fromOtherPlatformComponentType, fromOtherNetworkServiceType);
    }

    @Override
    public void handleCompleteComponentRegistrationNotificationEvent(PlatformComponentProfile platformComponentProfileRegistered) {
        /*
         * If the component registered have my profile and my identity public key
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.ASSET_USER_ACTOR &&
                platformComponentProfileRegistered.getIdentityPublicKey().equals(identity.getPublicKey())) {

            /*
             * Mark as register
             */
            this.register = Boolean.TRUE;

            /*
             * If exist actor asset user pending to registration
             */
            if (actorAssetUserPendingToRegistration != null && !actorAssetUserPendingToRegistration.isEmpty()) {

                CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();

                for (PlatformComponentProfile platformComponentProfileAssetUser : actorAssetUserPendingToRegistration) {
                     /*
                     * ask to the communication cloud client to register
                     */
                    try {
                        communicationsClientConnection.registerComponentForCommunication(
                                this.getNetworkServiceType(),
                                platformComponentProfileAssetUser);
                    } catch (CantRegisterComponentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /*
         * If is a actor registered
         */
        if (platformComponentProfileRegistered.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_USER &&
                platformComponentProfileRegistered.getNetworkServiceType() == NetworkServiceType.UNDEFINED) {

            Location loca = null;

            ActorAssetUser actorAssetUserNewRegsitered = new AssetUserActorRecord(
                    platformComponentProfileRegistered.getIdentityPublicKey(),
                    platformComponentProfileRegistered.getName(),
                    convertoByteArrayfromString(platformComponentProfileRegistered.getExtraData()),
                    loca);

            if (createactorAgentnum == 0) {

                assetUserActorNetworkServiceAgent = new AssetUserActorNetworkServiceAgent(
                        this,
                        wsCommunicationsCloudClientManager,
                        communicationNetworkServiceConnectionManager,
                        getPlatformComponentProfilePluginRoot(),
                        errorManager,
                        identity,
                        dataBase);

                // start main threads
                assetUserActorNetworkServiceAgent.start();

                createactorAgentnum = 1;
            }

            System.out.println("Actor Asset User REGISTRADO en A.N.S - Enviando Evento de Notificacion");

            FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
            event.setSource(EventSource.ACTOR_ASSET_USER);

            ((ActorAssetUserCompleteRegistrationNotificationEvent) event).setActorAssetUser(actorAssetUserNewRegsitered);

            eventManager.raiseEvent(event);
        }
    }

    @Override
    public void handleFailureComponentRegistrationNotificationEvent(PlatformComponentProfile networkServiceApplicant, PlatformComponentProfile remoteParticipant) {

        System.out.println(" Failure ConnectoTo() with " + remoteParticipant.getIdentityPublicKey());

        assetUserActorNetworkServiceAgent.connectionFailure(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    public void handleCompleteRequestListComponentRegisteredNotificationEvent(List<PlatformComponentProfile> platformComponentProfileRegisteredList) {

        System.out.println(" CommunicationNetworkServiceConnectionManager - Starting method handleCompleteComponentRegistrationNotificationEvent");
        /*
         * if have result create a ActorAssetUser
         */
        if (platformComponentProfileRegisteredList != null && !platformComponentProfileRegisteredList.isEmpty()) {
            /*
             * Get a remote network service registered from the list requested
             */
            PlatformComponentProfile remoteNetworkServiceToConnect = platformComponentProfileRegisteredList.get(0);

            /*
             * tell to the manager to connect to this remote network service
             */
            if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.UNDEFINED && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.ACTOR_ASSET_USER) {
                for (PlatformComponentProfile p : platformComponentProfileRegisteredList) {
                    Location loca = null;
                    ActorAssetUser actorAssetUserNew = new AssetUserActorRecord(p.getIdentityPublicKey(), p.getName(), convertoByteArrayfromString(p.getExtraData()), loca);

                    actorAssetUserRegisteredList.add(actorAssetUserNew);
                }

                FermatEvent event = eventManager.getNewEvent(EventType.COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION);
                event.setSource(EventSource.ACTOR_ASSET_USER);

                //((AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent) event).setActorAssetUserList(actorAssetUserRegisteredList);
                eventManager.raiseEvent(event);
            } else if (remoteNetworkServiceToConnect.getNetworkServiceType() == NetworkServiceType.ASSET_USER_ACTOR && remoteNetworkServiceToConnect.getPlatformComponentType() == PlatformComponentType.NETWORK_SERVICE) {
                /*
                 * save into the cache
                 */
                remoteNetworkServicesRegisteredList = platformComponentProfileRegisteredList;
            }
        }
    }

    @Override
    public void handleCompleteComponentConnectionRequestNotificationEvent(PlatformComponentProfile applicantComponentProfile, PlatformComponentProfile remoteComponentProfile) {

        System.out.println(" AssetUserActorNetworkServiceRoot - Starting method handleCompleteComponentConnectionRequestNotificationEvent");

        /*
         * Tell the manager to handler the new connection stablished
         */
        communicationNetworkServiceConnectionManager.handleEstablishedRequestedNetworkServiceConnection(remoteComponentProfile);
    }

    /**
     * Static method to get the logging level from any class under root.
     *
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return AssetUserActorNetworkServicePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return communicationNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * This method initialize the database
     *
     * @throws CantInitializeTemplateNetworkServiceDatabaseException
     */
    private void initializeDb() throws CantInitializeTemplateNetworkServiceDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            CommunicationNetworkServiceDatabaseFactory communicationNetworkServiceDatabaseFactory = new CommunicationNetworkServiceDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = communicationNetworkServiceDatabaseFactory.createDatabase(pluginId, CommunicationNetworkServiceDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeTemplateNetworkServiceDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }
    }

    /*
     * get a string and convert it into array bytes
     */
    private static byte[] convertoByteArrayfromString(String arrengebytes) {

        if (arrengebytes != null) {
            try {
                String[] byteValues = arrengebytes.substring(1, arrengebytes.length() - 1).split(",");
                byte[] bytes = new byte[byteValues.length];
                if (bytes.length > 0) {
                    for (int i = 0, len = bytes.length; i < len; i++) {
                        bytes[i] = Byte.parseByte(byteValues[i].trim());
                    }
                    return bytes;
                } else {
                    return new byte[]{};
                }
            } catch (Exception e) {
                return new byte[]{};
            }
        } else {
            return new byte[]{};
        }
    }

    /**
     * Get the New Received Message List
     *
     * @return List<FermatMessage>
     */
    public List<FermatMessage> getNewReceivedMessageList() throws CantReadRecordDataBaseException {

        Map<String, Object> filters = new HashMap<>();
        filters.put(CommunicationNetworkServiceDatabaseConstants.INCOMING_MESSAGES_FIRST_KEY_COLUMN, MessagesStatus.NEW_RECEIVED.getCode());

        return communicationNetworkServiceConnectionManager.getIncomingMessageDao().findAll(filters);
    }

    /**
     * Mark the message as read
     *
     * @param fermatMessage
     */
    public void markAsRead(FermatMessage fermatMessage) throws CantUpdateRecordDataBaseException {

        ((FermatMessageCommunication) fermatMessage).setFermatMessagesStatus(FermatMessagesStatus.READ);
        communicationNetworkServiceConnectionManager.getIncomingMessageDao().update(fermatMessage);
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

         /*
         * If all resources are inject
         */
        if (wsCommunicationsCloudClientManager == null ||
                pluginDatabaseSystem == null ||
                errorManager == null ||
                eventManager == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("wsCommunicationsCloudClientManager: " + wsCommunicationsCloudClientManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("eventManager: " + eventManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }
    }

    /**
     * Initialize the event listener and configure
     */
    private void initializeListener() {

         /*
         * Listen and handle Complete Component Registration Notification Event
         */
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentRegistrationNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_REQUEST_LIST_COMPONENT_REGISTERED_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteRequestListComponentRegisteredNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        /*
         * Listen and handle Complete Request List Component Registered Notification Event
         */
        fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_CONNECTION_REQUEST_NOTIFICATION);
        fermatEventListener.setEventHandler(new CompleteComponentConnectionRequestNotificationEventHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

//         /*
//         * Listen and handle New Message Receive Notification Event
//         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.NEW_NETWORK_SERVICE_MESSAGE_RECEIVE_NOTIFICATION);
//        fermatEventListener.setEventHandler(new NewReceiveMessagesNotificationEventHandler(this, eventManager));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

    }
}