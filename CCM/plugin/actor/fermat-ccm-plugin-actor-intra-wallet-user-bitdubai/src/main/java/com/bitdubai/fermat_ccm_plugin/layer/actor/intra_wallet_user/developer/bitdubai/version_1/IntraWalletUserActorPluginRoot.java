package com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantCancelIntraWalletUserException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUser;
import com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDao;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.event_handlers.IntraWalletUserNewNotificationsEventHandlers;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantAddPendingIntraWalletUserException;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUsersListException;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserActorDatabaseException;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantProcessNotificationsExceptions;
import com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantUpdateIntraWalletUserConnectionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserNotification;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This plugin manages connections between users of the platform..
 * Provides contact information of Intra User
 * <p/>
 * Created by loui on 22/02/15.
 * modified by Natalia on 11/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */


public class IntraWalletUserActorPluginRoot extends AbstractPlugin implements
        IntraWalletUserManager,
        DatabaseManagerForDevelopers,
        DealsWithErrors,
        DealsWithEvents,
       // DealsWithIntraUsersNetworkService,
        LogManagerForDevelopers,
        DealsWithPluginDatabaseSystem,
        DealsWithPluginFileSystem {

    public IntraWalletUserActorPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private IntraWalletUserActorDao intraWalletUserActorDao;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithDeviceUsers Interface member variables.
     */
    private DeviceUserManager deviceUserManager;


    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithLogger interface member variable
     */

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithIntraWalletUsersNetworkService interface member variable
     */
    IntraUserManager intraUserNetworkServiceManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * ActorIntraWalletUserManager interface implementation.
     */


    /**
     * That method registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_HIS_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantCreateIntraWalletUserException
     */

    @Override
    public void askIntraWalletUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.createNewIntraWalletUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ConnectionState.PENDING_REMOTELY_ACCEPTANCE);
        } catch (CantAddPendingIntraWalletUserException e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }

    }


    /**
     * That method takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptIntraWalletUserException
     */

    @Override
    public void acceptIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateIntraWalletUserConnectionState(intraUserLoggedInPublicKey, intraUserToAddPublicKey, ConnectionState.CONNECTED);
        } catch (CantUpdateIntraWalletUserConnectionException e) {
            throw new CantAcceptIntraWalletUserException("CAN'T ACCEPT INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantAcceptIntraWalletUserException("CAN'T ACCEPT INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    @Override
    public void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException {

        try {
            this.intraWalletUserActorDao.updateIntraWalletUserConnectionState(intraUserLoggedInPublicKey, intraUserToRejectPublicKey, ConnectionState.DENIED_LOCALLY);
        } catch (CantUpdateIntraWalletUserConnectionException e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDenyConnectionException("CAN'T DENY INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method disconnect an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey     The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraWalletUserException
     */
    @Override
    public void disconnectIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateIntraWalletUserConnectionState(intraUserLoggedInPublicKey, intraUserToDisconnectPublicKey, ConnectionState.DISCONNECTED_REMOTELY);
        } catch (CantUpdateIntraWalletUserConnectionException e) {
            throw new CantDisconnectIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantDisconnectIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method cancels an intra user from the connections registry
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraWalletUserException
     */
    @Override
    public void cancelIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.updateIntraWalletUserConnectionState(intraUserLoggedInPublicKey, intraUserToCancelPublicKey, ConnectionState.CANCELLED);
        } catch (CantUpdateIntraWalletUserConnectionException e) {
            throw new CantCancelIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCancelIntraWalletUserException("CAN'T CANCEL INTRA USER CONNECTION", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * That method get the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */
    @Override
    public List<IntraWalletUser> getAllIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllIntraWalletUsers(intraUserLoggedInPublicKey, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }



    /**
     * That method get the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */

    @Override
    public List<IntraWalletUser> getWaitingYourAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllIntraWalletUsers(intraUserLoggedInPublicKey, ConnectionState.PENDING_LOCALLY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER ACCEPTED CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * That method get  the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */

    @Override
    public List<IntraWalletUser> getWaitingTheirAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException {
        try {
            return this.intraWalletUserActorDao.getAllIntraWalletUsers(intraUserLoggedInPublicKey, ConnectionState.PENDING_REMOTELY_ACCEPTANCE, max, offset);
        } catch (CantGetIntraWalletUsersListException e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", e, "", "");
        } catch (Exception e) {
            throw new CantGetIntraWalletUsersException("CAN'T LIST INTRA USER PENDING_HIS_ACCEPTANCE CONNECTIONS", FermatException.wrapException(e), "", "");
        }
    }

    @Override
    public void receivingIntraWalletUserRequestConnection(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraWalletUserException {
        try {
            this.intraWalletUserActorDao.createNewIntraWalletUser(intraUserLoggedInPublicKey, intraUserToAddName, intraUserToAddPublicKey, profileImage, ConnectionState.PENDING_LOCALLY_ACCEPTANCE);
        } catch (CantAddPendingIntraWalletUserException e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", e, "", "");
        } catch (Exception e) {
            throw new CantCreateIntraWalletUserException("CAN'T ADD NEW INTRA USER REQUEST CONNECTION", FermatException.wrapException(e), "", "");
        }

    }

    /**
     * DealsWithEvents Interface implementation.
     */


    @Override
    public void setEventManager(EventManager DealsWithEvents) {
        this.eventManager = DealsWithEvents;
    }

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;

    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;

    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of IntraWalletUserActorDao
             * and initialize Database
             */
            this.intraWalletUserActorDao = new IntraWalletUserActorDao(pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            this.intraWalletUserActorDao.initializeDatabase();

            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;




            /**
             * Listener NetWorkService New Notifications event
             */
            fermatEventListener = eventManager.getNewListener(EventType.ACTOR_NETWORK_SERVICE_NEW_NOTIFICATIONS);
            fermatEventHandler = new IntraWalletUserNewNotificationsEventHandlers();
            ((IntraWalletUserNewNotificationsEventHandlers) fermatEventHandler).setIntraWalletUserManager(this);
            ((IntraWalletUserNewNotificationsEventHandlers) fermatEventHandler).setIntraUserManager(this.intraUserNetworkServiceManager);
            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            /**
             * I ask the list of pending requests to the Network Service to execute
             */

            this.processNotifications();

            // set plugin status Started
            this.serviceStatus = ServiceStatus.STARTED;


        } catch (CantProcessNotificationsExceptions e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR);

        } catch (CantInitializeIntraWalletUserActorDatabaseException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR);
        }

    }

    /**
     * DatabaseManagerForDevelopers Interface implementation.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);


    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {

        try {
            IntraWalletUserActorDeveloperDatabaseFactory dbFactory = new IntraWalletUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeIntraWalletUserActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("IntraWalletUserActorPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.ActorIntraWalletUser");

        /**
         * I return the values.
         */
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
                if (IntraWalletUserActorPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    IntraWalletUserActorPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    IntraWalletUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    IntraWalletUserActorPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + IntraWalletUserActorPluginRoot.newLoggingLevel, "Check the cause");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CCM_INTRA_WALLET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

    }


/**
 * Private methods
 */

    /**
     * Procces the list o f notifications from Intra User Network Services
     * And update intra user actor contact state
     *
     * @throws CantProcessNotificationsExceptions
     */
    public void processNotifications() throws CantProcessNotificationsExceptions {

        try {

            List<IntraUserNotification> intraUserNotificationes = intraUserNetworkServiceManager.getPendingNotifications();


            for (IntraUserNotification notification : intraUserNotificationes) {

                String intraUserSendingPublicKey = notification.getActorSenderPublicKey();

                String intraUserToConnectPublicKey = notification.getActorDestinationPublicKey();

                switch (notification.getNotificationDescriptor()) {
                    case ASKFORACCEPTANCE:

                        this.askIntraWalletUserForAcceptance(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());

                    case CANCEL:
                        this.cancelIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);

                    case ACCEPTED:
                        this.acceptIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);
                        /**
                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
                         */
                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
                        break;
                    case DISCONNECTED:
                        this.disconnectIntraWalletUser("", intraUserSendingPublicKey);
                        break;
                    case RECEIVED:
                        this.receivingIntraWalletUserRequestConnection(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());
                        /**
                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                         */
                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
                        break;
                    case DENIED:
                        this.denyConnection(intraUserSendingPublicKey, intraUserToConnectPublicKey);
                        break;
                    default:
                        break;

                }

                /**
                 * I confirm the application in the Network Service
                 */
                intraUserNetworkServiceManager.confirmNotification(notification.getId());
            }


        } catch (CantAcceptIntraWalletUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");

        } catch (CantDisconnectIntraWalletUserException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");

        } catch (CantDenyConnectionException e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");

        } catch (Exception e) {
            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");

        }
    }

}
