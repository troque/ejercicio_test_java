package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException e) {
            /**
             * If there is an error, I just will log it.
             */
            e.printStackTrace();
        }
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetsOverBitcoinCryptoVaultDatabaseFactory assetsOverBitcoinCryptoVaultDatabaseFactory = new AssetsOverBitcoinCryptoVaultDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetsOverBitcoinCryptoVaultDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("AssetsOverBitcoin", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table key_accounts columns.
         */
        List<String> key_accountsColumns = new ArrayList<String>();

        key_accountsColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME);
        key_accountsColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME);
        /**
         * Table key_accounts addition.
         */
        DeveloperDatabaseTable key_accountsTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME, key_accountsColumns);
        tables.add(key_accountsTable);

        /**
         * Table key_Maintenance columns.
         */
        List<String> key_MaintenanceColumns = new ArrayList<String>();

        key_MaintenanceColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME);
        key_MaintenanceColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME);
        key_MaintenanceColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME);
        /**
         * Table key_Maintenance addition.
         */
        DeveloperDatabaseTable key_MaintenanceTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME, key_MaintenanceColumns);
        tables.add(key_MaintenanceTable);

        /**
         * Table key_Maintenance_Monitor columns.
         */
        List<String> key_Maintenance_MonitorColumns = new ArrayList<String>();

        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME);
        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME);
        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME);
        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME);
        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME);
        key_Maintenance_MonitorColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME);
        /**
         * Table key_Maintenance_Monitor addition.
         */
        DeveloperDatabaseTable key_Maintenance_MonitorTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME, key_Maintenance_MonitorColumns);
        tables.add(key_Maintenance_MonitorTable);

        /**
         * Table active_Networks columns.
         */
        List<String> active_NetworksColumns = new ArrayList<String>();

        active_NetworksColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME);
        active_NetworksColumns.add(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME);
        /**
         * Table active_Networks addition.
         */
        DeveloperDatabaseTable active_NetworksTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_TABLE_NAME, active_NetworksColumns);
        tables.add(active_NetworksTable);



        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();

                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e){
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}