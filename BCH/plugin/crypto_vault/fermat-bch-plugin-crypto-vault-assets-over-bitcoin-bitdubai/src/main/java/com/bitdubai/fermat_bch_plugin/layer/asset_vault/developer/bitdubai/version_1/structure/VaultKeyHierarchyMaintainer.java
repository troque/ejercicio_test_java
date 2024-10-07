package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.VaultKeyMaintenanceParameters;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantLoadHierarchyAccountsException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.KeyMaintainerStatisticException;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.structure.VaultKeyHierarchyMaintainer</code>
 * Maintains the list of keys generated for each account. The list of keys are used to generate crypto address.
 * This agent takes care to validate we always have enough keys to pass to the crypto network.
 * Its goal is to pass the list of ECKeys to the bitcoin Network so we are monitoring all the ever created keys plus a bag of un used keys
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
class VaultKeyHierarchyMaintainer implements Agent {
    /**
     * controller of the agent execution thread
     */
    private boolean isSupposedToRun;

    /**
     * This will hold all the keys that I need to pass to bitcoin network for monitoring.
     */
    List<ECKey> allAccountsKeyList;

    /**
     * The inner class that is the agent itself.
     */
    VaultKeyHierarchyMaintainerAgent vaultKeyHierarchyMaintainerAgent;
    /**
     * the DAO object to access the database
     */
    AssetsOverBitcoinCryptoVaultDao dao;

    /**
     * The vault complete key hierarchy
     */
    private VaultKeyHierarchy vaultKeyHierarchy;


    /**
     * platform services variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    BitcoinNetworkManager bitcoinNetworkManager;
    UUID pluginId;

    /**
     * Constructor
     * @param vaultKeyHierarchy
     * @param pluginDatabaseSystem
     */
    public VaultKeyHierarchyMaintainer(VaultKeyHierarchy vaultKeyHierarchy, PluginDatabaseSystem pluginDatabaseSystem, BitcoinNetworkManager bitcoinNetworkManager, UUID pluginId) {
        this.vaultKeyHierarchy = vaultKeyHierarchy;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.pluginId = pluginId;
    }

    @Override
    public void start() throws CantStartAgentException {
        isSupposedToRun = true;
        vaultKeyHierarchyMaintainerAgent = new VaultKeyHierarchyMaintainerAgent();
        Thread agentThread = new Thread(vaultKeyHierarchyMaintainerAgent);
        agentThread.start();
    }

    @Override
    public void stop() {
        isSupposedToRun = false;
    }

    private class VaultKeyHierarchyMaintainerAgent implements Runnable {
        /**
         * current key usage from the database
         */
        int currentGeneratedKeys, currentUsedKeys, currentThreshold;

        /**
         * This will hold all the keys that I need to pass to bitcoin network for monitoring.
         */
        List<ECKey> allAccountsKeyList;

        /**
         * Sleep time of the agent between iterations
         */
        final long AGENT_SLEEP_TIME = 600000; //default time is 10 minutes


        @Override
        public void run() {
            while (isSupposedToRun) {
                try {
                    doTheMainTask();
                    Thread.sleep(AGENT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (CantLoadHierarchyAccountsException e) {
                    e.printStackTrace();
                } catch (KeyMaintainerStatisticException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * main executor of the agent
         */
        private void doTheMainTask() throws CantLoadHierarchyAccountsException, KeyMaintainerStatisticException {
            allAccountsKeyList = new ArrayList<>();
            /**
             * I get all the accounts that are available from the database
             */
            for (HierarchyAccount hierarchyAccount : getHierarchyAccounts()) {
                /**
                 * for each account, I will get the currentGeneratedKeys value
                 */
                currentGeneratedKeys = getCurrentGeneratedKeys(hierarchyAccount);
                /**
                 * now I will get the currentUsedKeys from the database
                 */
                currentUsedKeys = getCurrentUsedKeys(hierarchyAccount);
                /**
                 * I will calculate the current threshold to see if we need to create new keys
                 */
                currentThreshold = (int) Math.round(100 - ((currentUsedKeys * 100) / currentGeneratedKeys));

                List<ECKey> keys;
                if (currentThreshold <= VaultKeyMaintenanceParameters.KEY_PERCENTAGE_GENERATION_THRESHOLD) {
                    /**
                     * The current threshold is lower than the limit imposed, we need to generate new keys
                     * I start by updating the database and defining the new GeneratedKeys values
                     */
                    int newGeneratedKeys = currentGeneratedKeys + VaultKeyMaintenanceParameters.KEY_GENERATION_BLOCK;
                    setGeneratedKeysValue(hierarchyAccount.getId(), newGeneratedKeys);

                    /**
                     * I will generate the list of keys from zero to the new value
                     */
                    keys = deriveChildKeys(hierarchyAccount, newGeneratedKeys);
                } else {
                    /**
                     * There is no need to generate new keys, so I will re generate the ones I previously generated
                     * to control them.
                     */
                    keys = deriveChildKeys(hierarchyAccount, currentGeneratedKeys);
                }

                /**
                 * I will update the stats of the Hierarchy Maintainer in the database
                 */
                updateMaintainerStats(hierarchyAccount.getId(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        currentGeneratedKeys,
                        currentUsedKeys,
                        currentThreshold);

                /**
                 * At this point I have the list of keys for each account, so I will put them all together at allAccountsKeyList
                 * so I can pass it later to the bitcoin network.
                 */
                allAccountsKeyList.addAll(keys);
            }

            /**
             * Now that I have all the keys I need to monitor in the bitcoin network. First I need to know which networks are active.
             * A network becomes active when it generated address for an specified network (MainNet, RegTest or TestNet)
             */
            try {
                bitcoinNetworkManager.monitorNetworkFromKeyList(CryptoVaults.ASSETS_OVER_BITCOIN, getActiveNetworks(), allAccountsKeyList);
            } catch (CantMonitorBitcoinNetworkException e) {
                e.printStackTrace();
            }

        }

        /**
         * Gets the list of active networks on this platform.
         *
         * @return
         */
        private List<BlockchainNetworkType> getActiveNetworks() {
            List<BlockchainNetworkType> blockchainNetworkTypes = new ArrayList<>();
            /**
             * I get this information from the active_Networks table on the networkType column
             */
            try {
                blockchainNetworkTypes = getDao().getActiveNetworkTypes();
            } catch (CantExecuteDatabaseOperationException e) {
                /**
                 * the default network is always active, so I will add this.
                 */
                blockchainNetworkTypes.add(BlockchainNetworkType.DEFAULT);
            }

            return blockchainNetworkTypes;
        }

        /**
         * Will derive the amount specified of public keys for the passed account.
         *
         * @param hierarchyAccount
         * @param amount
         * @return
         */
        private List<ECKey> deriveChildKeys(HierarchyAccount hierarchyAccount, int amount) {
            DeterministicHierarchy keyHierarchy = vaultKeyHierarchy.getKeyHierarchyFromAccount(hierarchyAccount);
            List<ECKey> childKeys = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                // I derive the key at position i
                DeterministicKey derivedKey = keyHierarchy.deriveChild(keyHierarchy.getRootKey().getPath(), true, true, new ChildNumber(i, false));
                // I add this key to the ECKey list
                childKeys.add(ECKey.fromPrivate(derivedKey.getPrivKey()));
            }

            return childKeys;
        }


        /**
         * updates the generateKey values
         * @param accountId
         * @param value
         * @throws KeyMaintainerStatisticException
         */
        private void setGeneratedKeysValue(int accountId, int value) throws KeyMaintainerStatisticException {
            try {
                getDao().setGeneratedKeysValue(accountId, value);
            } catch (CantExecuteDatabaseOperationException  e) {
                throw new KeyMaintainerStatisticException(KeyMaintainerStatisticException.DEFAULT_MESSAGE, e, "there was an error updateing the Generated Key value", "database error.");
            }
        }

        /**
         * Will update the statistics of the maintainer execution
         *
         * @param hierarchyAccountId
         * @param date
         * @param currentGeneratedKeys
         * @param currentUsedKeys
         * @param currentThreshold
         */
        private void updateMaintainerStats(int hierarchyAccountId, String date, int currentGeneratedKeys, int currentUsedKeys, int currentThreshold) throws KeyMaintainerStatisticException {
            try {
                getDao().updateMaintainerStatistics(hierarchyAccountId,date, currentGeneratedKeys, currentUsedKeys, currentThreshold);
            } catch (CantExecuteDatabaseOperationException e) {
                throw new KeyMaintainerStatisticException(KeyMaintainerStatisticException.DEFAULT_MESSAGE, e, "I was unable to update the execution statistics into the database.", "database issue");
            }
        }

        /**
         * Gets and instance of the AssetsOverBitcoinCryptoVaultDao class used to access database objects.
         * @return
         * @throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException
         */
        private AssetsOverBitcoinCryptoVaultDao getDao() {
            if (dao == null){
                try {
                    dao = new AssetsOverBitcoinCryptoVaultDao(pluginDatabaseSystem, pluginId);
                } catch (CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException e) {
                    e.printStackTrace();
                }
            }
            return dao;
        }

        /**
         * Gets the actual amount of used keys for the specified account.
         * Keys used are the ones that where used to generate addresses
         *
         * @param hierarchyAccount
         * @return
         */
        private int getCurrentUsedKeys(HierarchyAccount hierarchyAccount) {
            try {
                int currentUsedKeys = getDao().getCurrentUsedKeys(hierarchyAccount.getId());

                return currentUsedKeys;
            } catch (CantExecuteDatabaseOperationException e) {
                return 1;
            }
        }

        /**
         * get the amount of how many keys have been generated for the specified amount
         *
         * @param hierarchyAccount
         * @return
         */
        private int getCurrentGeneratedKeys(HierarchyAccount hierarchyAccount) {
            /**
             * I can never return 0 or it will fail by dividing by 0
             */
            try {
                int currentGeneratedKeys = getDao().getCurrentGeneratedKeys(hierarchyAccount.getId());
                if (currentGeneratedKeys == 0)
                    currentGeneratedKeys = 1;

                return currentGeneratedKeys;
            } catch (CantExecuteDatabaseOperationException e) {
                return 1;
            }
        }

        /**
         * Gets the available Accounts from the database
         *
         * @return
         */
        private List<HierarchyAccount> getHierarchyAccounts() throws CantLoadHierarchyAccountsException {
            List<HierarchyAccount> hierarchyAccounts = new ArrayList<>();

            /**
             * The DAO object used to access the database.
             */


            try {
                hierarchyAccounts = getDao().getHierarchyAccounts();
            } catch (CantExecuteDatabaseOperationException e) {
                /**
                 * If there was an error creating or loading the database, or getting the list of accounts, I can't go on.
                 */
                throw new CantLoadHierarchyAccountsException(
                        CantLoadHierarchyAccountsException.DEFAULT_MESSAGE,
                        e,
                        "Error trying to load the Hierarchy Accounts from the database.", "database issue");
            }


            /**
             * If there are no accounts in the database, these means is the first time the plugin runs, so I will create
             * the account 0 that will be used by the asset vault.
             */
            if (hierarchyAccounts.size() == 0) {
                HierarchyAccount accountZero = new HierarchyAccount(0, "Asset Vault account");
                hierarchyAccounts.add(accountZero);

                /**
                 * And I will also try to add this to the database so I can load it the next time.
                 */
                try {
                    getDao().addNewHierarchyAccount(accountZero);
                } catch (CantExecuteDatabaseOperationException e) {
                    // I don't need to handle this error.
                }
            }
            return hierarchyAccounts;
        }

        /**
         * Gets all the keys that are generated for the accounts.
         * @return
         */
        public List<ECKey> getAllAccountsKeyList() {
            return allAccountsKeyList;
        }
    }

    /**
     * Gets the key list that the network is listening to.
     * @return
     */
    public List<ECKey> getAllAccountsKeyList() {
        this.allAccountsKeyList = vaultKeyHierarchyMaintainerAgent.getAllAccountsKeyList();
        return allAccountsKeyList;
    }
}
