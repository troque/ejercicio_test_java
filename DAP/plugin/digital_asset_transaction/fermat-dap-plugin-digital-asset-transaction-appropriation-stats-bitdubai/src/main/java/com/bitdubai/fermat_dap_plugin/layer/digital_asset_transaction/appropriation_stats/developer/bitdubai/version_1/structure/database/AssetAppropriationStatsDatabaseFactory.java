package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.appropriation_stats.developer.bitdubai.version_1.structure.database;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationStatsDatabaseFactory implements DealsWithPluginDatabaseSystem {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;

    //CONSTRUCTORS
    public AssetAppropriationStatsDatabaseFactory() {
    }

    public AssetAppropriationStatsDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
    }


    //PUBLIC METHODS
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_DATABASE);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Events recorded database table.
             */
            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException e) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "UUID : " + ownerId, "Invalid Owner. Cannot create the table...");
        }
        return database;
    }

    public boolean isDatabaseCreated(UUID ownerId) {
        try {
            this.pluginDatabaseSystem.openDatabase(ownerId, AssetAppropriationStatsDatabaseConstants.APPROPRIATION_STATS_DATABASE);
        } catch (CantOpenDatabaseException e) {
            return false;
        } catch (DatabaseNotFoundException e) {
            return false;
        }
        return true;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    //INNER CLASSES
}
