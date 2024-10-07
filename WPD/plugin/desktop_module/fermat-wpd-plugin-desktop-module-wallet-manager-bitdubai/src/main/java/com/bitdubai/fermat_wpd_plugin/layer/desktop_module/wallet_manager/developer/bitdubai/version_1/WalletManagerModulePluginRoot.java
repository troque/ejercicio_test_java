package com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantEnableWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantGetIfIntraWalletUsersExistsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.NewWalletCreationFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRemovalFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletRenameFailedException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces.WalletManagerModule;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_plugin.layer.desktop_module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerModuleInstalledWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */
/**
 * This plug serves as an interface between the SubApp and WalletManager Middleware.
 * It allows you to retrieve information from the wallets installed and create clones.
 *
 *
 * Created by ciencias on 21.01.15.
 * Modified by Natalia on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerModulePluginRoot extends AbstractPlugin implements
        LogManagerForDevelopers,
        WalletManagerModule,
        WalletManager {

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET   , plugin = Plugins.BITCOIN_WALLET)
    private BitcoinWalletManager bitcoinWalletManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM         , addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE         , plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletMiddlewareManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.IDENTITY         , plugin = Plugins.INTRA_WALLET_USER)
    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    /**
     * WalletManager Interface member variables.
     */
    String deviceUserPublicKey = "";
    String walletPublicKey = "reference_wallet";

    List<InstalledWallet> userWallets;

    private Map<String, String> walletIds = new HashMap<>();
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    List<FermatEventListener> listenersAdded = new ArrayList<>();


    public WalletManagerModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
        userWallets = new ArrayList<>();
    }


    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        /**
         * I will check if wallet if created, if not I execute Bitcoinmanager create
         *
         */
        //TODO: Verificar si este bloque de codigo es necesario que quede aca
        boolean existWallet = false;
        try {
            //load user's wallets ids
            this.loadUserWallets(deviceUserPublicKey);

            Iterator iterator = walletIds.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                if (mapEntry.getValue().toString().equals(walletPublicKey))
                    existWallet = true;
            }

            if (!existWallet) {
                //Create new Bitcoin Wallet

                try {

                    bitcoinWalletManager.createWallet(walletPublicKey);

                    //Save wallet id on file

                    try {
                        this.persistWallet(walletPublicKey);
                    } catch (CantPersistWalletException cantPersistWalletException) {
                        throw new CantStartPluginException(cantPersistWalletException, Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);

                    }

                } catch (CantCreateWalletException cantCreateWalletException) {
                    throw new CantStartPluginException(cantCreateWalletException, Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE);

                }
            }

        } catch (Exception cantLoadWalletsException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadWalletsException);
            throw new CantStartPluginException();
        }


        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

       /* fermatEventListener = eventManager.getNewListener(EventType.DEVICE_USER_CREATED);
        fermatEventHandler = new UserCreatedEventHandler();
        ((UserCreatedEventHandler) fermatEventHandler).setWalletManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.DEVICE_USER_LOGGED_IN);
        fermatEventHandler = new UserLoggedInEventHandler();
        ((UserLoggedInEventHandler) fermatEventHandler).setWalletManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
        fermatEventHandler = new WalletResourcesInstalledEventHandler();
        ((WalletResourcesInstalledEventHandler) fermatEventHandler).setWalletManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.NAVIGATION_STRUCTURE_UPDATED);
        fermatEventHandler = new NavigationStructureUpdatedEventHandler();
        ((NavigationStructureUpdatedEventHandler) fermatEventHandler).setWalletManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_CREATED);
        fermatEventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) fermatEventHandler).setWalletManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);*/


        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * WalletManager Interface implementation.
     */

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName the name to give to the wallet
     * @throws NewWalletCreationFailedException
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws NewWalletCreationFailedException
    {
        try
        {
            walletMiddlewareManager.createNewWallet(walletIdInTheDevice,newName);
        }
        catch(CantCreateNewWalletException e)
        {
            throw  new NewWalletCreationFailedException("CAN'T CREATE NEW WALLET",e,"","");
        }
        catch(Exception e)
        {
            throw  new NewWalletCreationFailedException("CAN'T CREATE NEW WALLET",FermatException.wrapException(e),"","");
        }
    }

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     * @throws WalletsListFailedToLoadException
     */
    public List<InstalledWallet> getInstalledWallets() throws WalletsListFailedToLoadException{
        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();

        try
        {

            List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> installetMiddlewareWallets = walletMiddlewareManager.getInstalledWallets();
            for (com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet wallet : installetMiddlewareWallets){

                InstalledWallet installedWallet= new WalletManagerModuleInstalledWallet(wallet.getWalletCategory(),wallet.getWalletType(),
                        wallet.getSkinsId(),
                        wallet.getLanguagesId(),
                        wallet.getWalletIcon(),
                        wallet.getWalletName(),
                        wallet.getWalletPublicKey(),
                        wallet.getWalletPlatformIdentifier(),
                        wallet.getWalletVersion()
                );

                lstInstalledWallet.add(installedWallet);
            }


        }
        catch (CantListWalletsException e) {
            throw  new WalletsListFailedToLoadException("CAN'T GET THE INSTALLED WALLETS",e,"","");
        }
        catch(Exception e)
        {
            throw  new WalletsListFailedToLoadException("CAN'T GET THE INSTALLED WALLETS", FermatException.wrapException(e),"","");
        }

        return lstInstalledWallet;
    }

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws WalletRemovalFailedException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws WalletRemovalFailedException{
        try
        {
            walletMiddlewareManager.removeWallet(walletIdInTheDevice);
        }
        catch(CantRemoveWalletException e)
        {
            throw  new WalletRemovalFailedException("CAN'T CREATE NEW WALLET",e,"","");
        }
        catch(Exception e)
        {
            throw  new WalletRemovalFailedException("CAN'T CREATE NEW WALLET",FermatException.wrapException(e),"","");
        }
    }

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws WalletRenameFailedException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws WalletRenameFailedException
    {
        try
        {
            walletMiddlewareManager.renameWallet(walletIdInTheDevice, newName);
        }
        catch(CantRenameWalletException e)
        {
            throw  new WalletRenameFailedException("CAN'T RENAME WALLET",e,"","");
        }
        catch(Exception e)
        {
            throw  new WalletRenameFailedException("CAN'T RENAME WALLET",FermatException.wrapException(e),"","");
        }
    }

    public void persistWallet(String walletId) throws CantPersistWalletException {
        /**
         * Now I will add this wallet to the list of wallets managed by the plugin.
         */
        walletIds.put(deviceUserPublicKey, walletId);

        PluginTextFile walletIdsFile = null;

        try {
            walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {

            /**
             * If I can not save this file, then this plugin shouldn't be running at all.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

            throw new CantPersistWalletException();
        }

        /**
         * I will generate the file content.
         */
        StringBuilder stringBuilder = new StringBuilder(walletIds.size() * 72);

        Iterator iterator = walletIds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            stringBuilder.append(pair.getKey().toString() + "," + pair.getValue().toString() + ";");
            iterator.remove();
        }


        /**
         * Now I set the content.
         */
        walletIdsFile.setContent(stringBuilder.toString());

        try {
            walletIdsFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            /**
             * If I can not save the id of the new wallet created, then this method fails.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_DESKTOP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
            throw new CantPersistWalletException();
        }

    }

    @Override
    public void createNewIntraWalletUser(String alias, byte[] profileImage) throws WalletCreateNewIntraUserIdentityException {
        try
        {
           intraWalletUserIdentityManager.createNewIntraWalletUser(alias,profileImage);

        }
        catch( CantCreateNewIntraWalletUserException e)
        {
            throw  new WalletCreateNewIntraUserIdentityException("CAN'T CREATE NEW INTRA USER IDENTITY",e,"","");

        }
        catch(Exception e)
        {
            throw  new WalletCreateNewIntraUserIdentityException("CAN'T CREATE NEW INTRA USER IDENTITY",FermatException.wrapException(e),"","");
        }
    }


    @Override
    public boolean hasIntraUserIdentity() throws CantGetIfIntraWalletUsersExistsException {
        try
        {
            return intraWalletUserIdentityManager.hasIntraUserIdentity();
        }
        catch( CantListIntraWalletUsersException e)
        {
            throw  new CantGetIfIntraWalletUsersExistsException("CAN'T GET IF INTRA USERs IDENTITY EXISTS",e,"","");
        }
        catch(Exception e)
        {
            throw  new CantGetIfIntraWalletUsersExistsException("CAN'T GET IF INTRA USERS IDENTITY EXISTS",FermatException.wrapException(e),"","");
        }
    }


    //TODO: revisar si esta interface Wallet Manager se va a usar (Natalia)
    /**
     * WalletManager Interface implementation.
     */



    //TODO: Analizar este metodo deberia reemplazarce por el metodo getInstalledWallets de la interface WalletManagerModule que ya esta implementado (Natalia)
        @Override
        public List<InstalledWallet> getUserWallets() {
        // Harcoded para testear el circuito más arriba
        InstalledWallet installedWallet= new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                WalletType.REFERENCE,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "reference_wallet_icon",
                "Bitcoin Wallet",
                "reference_wallet",
                "wallet_platform_identifier",
                new Version(1,0,0)
        );

        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();
        lstInstalledWallet.add(installedWallet);
        return lstInstalledWallet;
    }

    @Override
    public void createDefaultWallets(String deviceUserPublicKey) throws CantCreateDefaultWalletsException {

        /**
         * By now I will create only a new wallet, In the future there will be more than one default wallets.
         */

        //  Wallet wallet = new WalletManagerWallet();

        //  ((DealsWithPluginFileSystem) wallet).setPluginFileSystem(pluginFileSystem);
        // ((DealsWithEvents) wallet).setEventManager(eventManager);
        //((DealsWithPluginIdentity) wallet).setPluginId(pluginId);

        //try {
        //aquí al crear la wallet se le deben pasar todos los parametros de esta
        //wallet.createWallet("public_key_hardcoded");
        //} catch (CantCreateWalletException cantCreateWalletException) {
        /**
         * Well, if it is not possible to create a wallet, then we have a problem that I can not handle...
         */
        //    System.err.println("CantCreateWalletException: " + cantCreateWalletException.getMessage());
        //    cantCreateWalletException.printStackTrace();

        //    throw new CantCreateDefaultWalletsException();
        //}

    }

    @Override
    public void enableWallet() throws CantEnableWalletException{

    }

    @Override
    public InstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException {
        InstalledWallet installedWallet = null;

        //TODO: Hardcoded for testing purpose, hice esto que va a andar cuando la tengamos instalada.  mati
//        com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet wallet = walletMiddlewareManager.getInstalledWallet(walletPublicKey);
//
//            InstalledWallet installedWallet= new WalletManagerModuleInstalledWallet(wallet.getWalletCategory(),wallet.getWalletType(),
//                    wallet.getSkinsId(),
//                    wallet.getLanguagesId(),
//                    wallet.getIcon(),
//                    wallet.getName(),
//                    wallet.getWalletPublicKey(),
//                    wallet.getWalletPlatformIdentifier(),
//                    wallet.getWalletVersion());
        switch (walletPublicKey){
            case "reference_wallet":
                installedWallet = new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                        WalletType.REFERENCE,
                        new ArrayList<InstalledSkin>(),
                        new ArrayList<InstalledLanguage>(),
                        "reference_wallet_icon",
                        "Bitcoin Reference Wallet",
                        "reference_wallet",
                        "wallet_platform_identifier",
                        new Version(1,0,0));
                break;
            case "asset_issuer":
                installedWallet= new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                        WalletType.REFERENCE,
                        new ArrayList<InstalledSkin>(),
                        new ArrayList<InstalledLanguage>(),
                        "asset_issuer",
                        "asset issuer",
                        "asset_issuer",
                        "wallet_platform_identifier",
                        new Version(1,0,0));
                break;
            case "asset_user":
                installedWallet= new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                        WalletType.REFERENCE,
                        new ArrayList<InstalledSkin>(),
                        new ArrayList<InstalledLanguage>(),
                        "asset_user",
                        "asset user",
                        "asset_user",
                        "wallet_platform_identifier",
                        new Version(1,0,0));
                break;
            case "redeem_point":
                installedWallet= new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                        WalletType.REFERENCE,
                        new ArrayList<InstalledSkin>(),
                        new ArrayList<InstalledLanguage>(),
                        "redeem_point",
                        "redeem point",
                        "redeem_point",
                        "wallet_platform_identifier",
                        new Version(1,0,0));
                break;
            default:
                throw new CantCreateNewWalletException("No existe public key",null,null,null);
        }

        return installedWallet;
    }

    /**
     *
     * @param deviceUserPublicKey
     * @throws CantLoadWalletsException
     */
    @Override
    public void loadUserWallets(String deviceUserPublicKey) throws CantLoadWalletsException {

        this.deviceUserPublicKey = deviceUserPublicKey;
        /**
         *I check if the file containing all the wallets  ids managed by this plug-in already exists or not.
         * and load wallets ids for user
         *
         */
        PluginTextFile walletIdsFile;

        try {

            try {
                walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

                throw new CantLoadWalletsException();
            }
            try {
                walletIdsFile.loadFromMedia();

                /*
                 * TODO: This can't stay in a file. A user id will be assign to many walletIds
                 */
                /**
                 * Now I read the content of the file and place it in memory.
                 */
                String[] stringWalletIds = walletIdsFile.getContent().split(";", -1);

                for (String stringWalletId : stringWalletIds) {

                    if (!stringWalletId.equals("")) {
                        /**
                         * Each record in the file has to values: the first is the external id of the wallet, and the
                         * second is the internal id of the wallet.
                         * * *
                         */
                        String[] idPair = stringWalletId.split(",", -1);

                        //put wallets of this user
                        if (idPair[0].equals(deviceUserPublicKey))
                            walletIds.put(idPair[0], idPair[1]);

                        /**
                         * Great, now the wallet list is in memory.
                         */
                    }
                }
            } catch (CantLoadFileException cantLoadFileException) {

                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 *
                 * In the future there should be implemented a method to deal with this situation.
                 * * * *
                 */

                //errorManager.reportUnexpectedPluginException(Plugins.BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);

                throw new CantLoadWalletsException();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try {

                walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantLoadWalletsException();
            }
            try {
                walletIdsFile.persistToMedia();
            } catch (CantPersistFileException cantPersistFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantLoadWalletsException();
            }
        }
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.WalletManagerModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerWallet");


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
            if (WalletManagerModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletManagerModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletManagerModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletManagerModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }
}

