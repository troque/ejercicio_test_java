package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Nerio on 17/09/15.
 */
public class AssetUserActorDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    public AssetUserActorDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;
        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException exception) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset User database table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetUserActorDatabaseConstants.ASSET_USER_TABLE_NAME);

            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_AGE_COLUMN_NAME, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_GENDER_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(AssetUserActorDatabaseConstants.ASSET_USER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Asset User REGISTERED ACTOR USER database Associate table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_TABLE_NAME);

            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_AGE_COLUMN_NAME, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_GENDER_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(AssetUserActorDatabaseConstants.ASSET_USER_REGISTERED_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Asset User relation Asset Issuer Associate table.
             */
//            table = databaseFactory.newTableFactory(ownerId, AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ASSET_ISSUER_TABLE_NAME);
//
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_IDENTITY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 200, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_RESOURCES_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_ASSET_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_REDEEMPTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//
//            table.addIndex(AssetUserActorDatabaseConstants.ASSET_USER_RELATION_ISSUER_FIRST_KEY_COLUMN);
//
//            try {
//                //Create the table
//                databaseFactory.createTable(ownerId, table);
//            } catch (CantCreateTableException cantCreateTableException) {
//                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
//            }

        } catch (InvalidOwnerIdException exception) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "", "There is a problem with the ownerId of the database.");
        }

        return database;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
