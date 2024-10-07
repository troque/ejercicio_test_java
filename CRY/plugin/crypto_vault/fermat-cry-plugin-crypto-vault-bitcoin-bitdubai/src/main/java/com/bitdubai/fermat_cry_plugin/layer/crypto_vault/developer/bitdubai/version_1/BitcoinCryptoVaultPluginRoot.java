package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 08/06/15.
 */
public class BitcoinCryptoVaultPluginRoot extends AbstractPlugin implements
        CryptoVaultManager,
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers {


    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.USER            , addon = Addons.DEVICE_USER         )
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS        , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK)
    private BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;


    public BitcoinCryptoVaultPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    public static final EventSource EVENT_SOURCE = EventSource.CRYPTO_VAULT;

    /**
     * BitcoinCryptoVaultPluginRoot member variables
     */
    BitcoinCryptoVault vault;
    TransactionNotificationAgent transactionNotificationAgent;

    Database database;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    List<FermatEventListener> listenersAdded = new ArrayList<>();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator");
        /**
         * I return the values.
         */
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
            if (BitcoinCryptoVaultPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                BitcoinCryptoVaultPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                BitcoinCryptoVaultPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                BitcoinCryptoVaultPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        try {
            String userPublicKey = deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            DeveloperDatabaseFactory dbFactory = new DeveloperDatabaseFactory(userPublicKey, pluginId.toString());
            return dbFactory.getDatabaseList(developerObjectFactory);
        } catch (CantGetLoggedInDeviceUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }

    /**
     * returns the list of tables for the given database
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * returns the list of records for the passed table
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
    }

    @Override
    public boolean isValidAddress(CryptoAddress addressTo) {
        return vault.isValidAddress(addressTo);
    }

    //TODO Franklin, aqui falta la gestion de excepciones genericas
    @Override
    public void start() throws CantStartPluginException {

        /**
         * I get the userPublicKey from the deviceUserManager
         */
        String userPublicKey;
        try {
            userPublicKey = deviceUserManager.getLoggedInDeviceUser().getPublicKey();
        } catch (CantGetLoggedInDeviceUserException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "Cant get LoggedIn Device User", "");
        }

        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {

            database = pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);

        } catch (CantOpenDatabaseException e) {
            /**
             * The database could not be opened, let try to create it instead.
             */
            try {
                CryptoVaultDatabaseFactory cryptoVaultDatabaseFactory = new CryptoVaultDatabaseFactory();
                cryptoVaultDatabaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
                cryptoVaultDatabaseFactory.setErrorManager(errorManager);

                database = cryptoVaultDatabaseFactory.createDatabase(pluginId, userPublicKey);
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creating the db, I can't handle this.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            }

        } catch (DatabaseNotFoundException e) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                CryptoVaultDatabaseFactory cryptoVaultDatabaseFactory = new CryptoVaultDatabaseFactory();
                cryptoVaultDatabaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
                cryptoVaultDatabaseFactory.setErrorManager(errorManager);

                database = cryptoVaultDatabaseFactory.createDatabase(pluginId, userPublicKey);
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creating the db, I can't handle this.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            }
        }

        /**
         * I will start the loading creation of the wallet from the user Id
         */
        try {
            vault = new BitcoinCryptoVault(
                    userPublicKey,
                    eventManager,
                    errorManager,
                    logManager,
                    pluginId,
                    pluginFileSystem,
                    database
            );

            vault.loadOrCreateVault();


            /**
             * Once the vault is loaded or created, I will connect it to Bitcoin network to recieve pending transactions
             */

            try {
                vault.connectVault();
            } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantConnectToBitcoinNetwork);
                throw new CantStartPluginException("Error trying to start CryptoVault plugin.", cantConnectToBitcoinNetwork, null, "I couldn't connect to the Bitcoin network.");

            }


        } catch (CantCreateCryptoWalletException cantCreateCryptoWalletException ) {
            /**
             * If I couldnt create the Vault, I cant go on.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateCryptoWalletException );
            throw new CantStartPluginException("Error trying to start CryptoVault plugin.", cantCreateCryptoWalletException, null, "Probably not enought space available to save the vault.");
        }


        /**
         * now I will start the TransactionNotificationAgent to monitor
         */
        transactionNotificationAgent = new TransactionNotificationAgent(eventManager, errorManager, logManager, database);
        try {
            transactionNotificationAgent.start();
        } catch (CantStartAgentException cantStartAgentException ) {
            /**
             * If I couldn't start the agent, I still will continue with the vault
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantStartAgentException );
        }

        // test method
        //sendTestBitcoins();

        /**
         * the service is started.
         */
        this.serviceStatus = ServiceStatus.STARTED;
        logManager.log(BitcoinCryptoVaultPluginRoot.getLogLevelByClass(this.getClass().getName()), "CryptoVault started.", null, null);
    }

    /**
     * Service interface implementation
     */
    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();

        /**
         * I will also stop the Notification Agent
         */
        transactionNotificationAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    //TODO Franklin, aqui falta la gestion de excepciones genericas
    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void connectToBitcoin() throws VaultNotConnectedToNetworkException {
        try {

            vault.connectVault();
        } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
            throw new VaultNotConnectedToNetworkException();
        }
    }

    //TODO Franklin, aqui falta la gestion de excepciones genericas, usa el errorManager
    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void disconnectFromBitcoin()  {
        try {
            vault.disconnectVault();
        } catch (Exception exception){

        }
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public CryptoAddress getAddress() {
        return vault.getAddress();
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public List<CryptoAddress> getAddresses(int amount) {
        List<CryptoAddress> addresses = new ArrayList<CryptoAddress>();
        for (int i=0; i < amount; i++){
            addresses.add(getAddress());
        }
        return addresses;
    }

    // changed wallet id from UUID to Strubg representing a public key
    // Ezequiel Postan August 15th 2015
    @Override
    public String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return vault.sendBitcoins(FermatTrId, addressTo, satoshis, null);
    }

    @Override
    public String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis, String op_Return) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return vault.sendBitcoins(FermatTrId, addressTo, satoshis, op_Return);
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return vault;
    }


    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return BitcoinCryptoVaultPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public CryptoStatus getCryptoStatus(String txHash) throws CouldNotGetCryptoStatusException {
        try {
            return vault.getCryptoStatus(txHash);
        } catch (CantExecuteQueryException e) {
            throw new CouldNotGetCryptoStatusException("There was an error accesing the database to get the CryptoStatus.", e, "TransactionId: " + txHash, "An error in the database plugin.");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CouldNotGetCryptoStatusException("There was an error getting the CryptoStatus of the transaction.", e, "TransactionId: " + txHash, "Duplicated transaction Id in the database.");
        }
    }
}