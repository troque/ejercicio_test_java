package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.developer_utils;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_reception.exceptions.CantInitializeAssetReceptionTransactionDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 12/10/15.
 */
public class AssetReceptionDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public AssetReceptionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetReceptionTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetReceptionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetReceptionTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetReceptionDatabaseFactory assetReceptionDatabaseFactory = new AssetReceptionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetReceptionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetReceptionTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DATABASE, this.pluginId.toString()));
        return databases;
    }


    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Asset Reception columns.
         */
        List<String> assetReceptionColumns = new ArrayList<String>();

        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_GENESIS_TRANSACTION_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_HASH_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_ACTOR_ASSET_ISSUER_ID_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_STATUS_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_CRYPTO_STATUS_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_PROTOCOL_STATUS_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_ACTOR_ASSET_ISSUER_BITCOIN_ADDRESS_COLUMN_NAME);
        assetReceptionColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEPTION_ID_COLUMN_NAME);
        /**
         * Table Asset Reception addition.
         */
        DeveloperDatabaseTable assetReceptionTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_TABLE_NAME, assetReceptionColumns);
        tables.add(assetReceptionTable);

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        /**
         * Asset Reception table
         */
        List<String> assetReceivingColumns = new ArrayList<String>();

        assetReceivingColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_MESSAGE_ID_COLUMN_NAME);
        assetReceivingColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_GENESIS_TRANSACTION_COLUMN_NAME);
        assetReceivingColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_MESSAGE_TYPE_COLUMN_NAME);
        assetReceivingColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_TIMESTAMP_COLUMN_NAME);
        assetReceivingColumns.add(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_EVENT_ID_COLUMN_NAME);

        DeveloperDatabaseTable assetDeliveringTable = developerObjectFactory.getNewDeveloperDatabaseTable(AssetReceptionDatabaseConstants.ASSET_RECEPTION_RECEIVING_TABLE_NAME, assetReceivingColumns);
        tables.add(assetDeliveringTable);

        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
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
