package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
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
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.interfaces.RedeemPointRedemptionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletManager;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.developer_utils.AssetRedeemPointRedemptionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionMonitorAgent;
import com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 18/10/15.
 */
public class RedeemPointRedemptionDigitalAssetTransactionPluginRoot extends AbstractPlugin implements 
        RedeemPointRedemptionManager,
        LogManagerForDevelopers,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem        ;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.REDEEM_POINT)
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.WALLET, plugin = Plugins.REDEEM_POINT)
    private AssetRedeemPointWalletManager assetRedeemPointWalletManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.ASSET_TRANSMISSION)
    private AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.ACTOR, plugin = Plugins.ASSET_USER)
    private ActorAssetUserManager actorAssetUserManager;

    RedeemPointRedemptionMonitorAgent monitorAgent;
    RedeemPointRedemptionRecorderService recorderService;
    //VARIABLE DECLARATION
    private static Map<String, LogLevel> newLoggingLevel;

    static {
        newLoggingLevel = new HashMap<>();
    }

    //CONSTRUCTORS

    public RedeemPointRedemptionDigitalAssetTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    //PUBLIC METHODS
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("VAMM: PLUGIN REDEEMPOINT REDEMPTION INICIADO!!");

        String context = "pluginId : " + pluginId + "\n" +
                "ErrorManager : " + errorManager + "\n" +
                "pluginDatabaseSystem : " + pluginDatabaseSystem + "\n" +
                "pluginFileSystem : " + pluginFileSystem + "\n" +
                "actorAssetRedeemPointManager : " + actorAssetRedeemPointManager + "\n" +
                "assetRedeemPointWalletManager : " + assetRedeemPointWalletManager + "\n" +
                "assetTransmissionNetworkServiceManager : " + assetTransmissionNetworkServiceManager + "\n" +
                "logManager : " + logManager + "\n" +
                "eventManager : " + eventManager + "\n" +
                "monitorAgent : " + monitorAgent + "\n" +
                "recorderService : " + recorderService + "\n";
        try {
            AssetRedeemPointRedemptionDatabaseFactory databaseFactory = new AssetRedeemPointRedemptionDatabaseFactory(pluginDatabaseSystem);
            if (!databaseFactory.isDatabaseCreated(pluginId)) {
                databaseFactory.createDatabase(pluginId);
            }
            monitorAgent = createNewMonitorAgent();
            monitorAgent.start();
            recorderService = createNewRecorderService();
            recorderService.start();
//            test();
        } catch (CantSetObjectException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "There was a null reference, check the context.");
        } catch (CantCreateDatabaseException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "Can't create the database, probably you don't have permissions to do so.");
        } catch (CantStartAgentException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "Couldn't start the monitor agent, probably the agent couldn't load the redeem point wallet.");
        } catch (CantStartServiceException e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, context, "");
        } catch (Exception e) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), context, "");
        } finally {
            this.serviceStatus= ServiceStatus.STOPPED;
        }
        this.serviceStatus= ServiceStatus.STARTED;
    }
    
    @Override
    public void stop() {
        monitorAgent.stop();
        recorderService.stop();
        this.serviceStatus= ServiceStatus.STOPPED;
    }

    //PRIVATE METHODS

    private RedeemPointRedemptionMonitorAgent createNewMonitorAgent() throws CantSetObjectException {
        RedeemPointRedemptionMonitorAgent monitorAgent = new RedeemPointRedemptionMonitorAgent(errorManager,
                logManager,
                assetTransmissionNetworkServiceManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                pluginId,
                actorAssetRedeemPointManager,
                assetRedeemPointWalletManager,
                actorAssetUserManager);
        return monitorAgent;
    }

    private RedeemPointRedemptionRecorderService createNewRecorderService() throws CantSetObjectException {
        RedeemPointRedemptionRecorderService recorderService = new RedeemPointRedemptionRecorderService(eventManager, pluginDatabaseSystem, pluginId);
        return recorderService;
    }

    private void test() {
        //RAISE EVENT
        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION);
        eventToRaise.setSource(EventSource.NETWORK_SERVICE_ASSET_TRANSMISSION);
        eventManager.raiseEvent(eventToRaise);
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.developer_utils.AssetRedeemPointRedemptionDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetRedemptionEventListException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantLoadAssetredemptionMetadataListException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.CantPersistTransactionMetadataException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions.RecordsNotFoundException");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDAO");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database.AssetRedeemPointRedemptionDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionEventHandler");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionMonitorAgent");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.events.RedeemPointRedemptionRecorderService");
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.RedeemPointRedemptionDigitalAssetTransactionPluginRoot");
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (RedeemPointRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                RedeemPointRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                RedeemPointRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                RedeemPointRedemptionDigitalAssetTransactionPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetRedeemPointRedemptionDeveloperDatabaseFactory factory = new AssetRedeemPointRedemptionDeveloperDatabaseFactory(this.pluginId, this.pluginDatabaseSystem);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return AssetRedeemPointRedemptionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);
            return AssetRedeemPointRedemptionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("Cannot open the database", cantOpenDatabaseException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception", exception, "DeveloperDatabase: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_REDEEM_POINT_REDEMPTION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }
    //INNER CLASSES
}
