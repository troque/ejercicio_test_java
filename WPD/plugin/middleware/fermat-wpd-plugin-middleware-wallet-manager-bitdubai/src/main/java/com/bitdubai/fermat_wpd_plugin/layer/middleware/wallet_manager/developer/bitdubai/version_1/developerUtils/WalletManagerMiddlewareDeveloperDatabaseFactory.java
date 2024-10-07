package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.developerUtils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 24/07/15.
 */
public class WalletManagerMiddlewareDeveloperDatabaseFactory {

    String pluginId;
    List<String> walletsIds;

    public WalletManagerMiddlewareDeveloperDatabaseFactory (String pluginId){
        this.pluginId = pluginId;

    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE, pluginId));
        return databases;
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /*
         * First we add the Wallets table
         * We start with the columns
         */

        List<String> WalletManagerMiddlewareTableColumns = new ArrayList<>();
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PUBLIC_KEY_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PRIVATE_KEY_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_CATEGORY_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAME_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_PLATFORM_IDENTIFIER_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_ICON_NAME_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_VERSION_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_TYPE_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_SIZE_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_SCREEN_DENSITY_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_WALLET_NAVIGATION_VERSION_COLUMN_NAME);
        WalletManagerMiddlewareTableColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_DEVELOPER_NAME_COLUMN_NAME);

        /**
         * Wallets table
         */
        DeveloperDatabaseTable  WalletTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME, WalletManagerMiddlewareTableColumns);
        tables.add(WalletTable);


        /**
         * LanguageColumns table
         */
        List<String> WalletManagerMiddlewareTableLanguageColumns = new ArrayList<>();
        WalletManagerMiddlewareTableLanguageColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_ID_COLUMN_NAME);
        WalletManagerMiddlewareTableLanguageColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_LABEL_COLUMN_NAME);
        WalletManagerMiddlewareTableLanguageColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_NAME_COLUMN_NAME);
        WalletManagerMiddlewareTableLanguageColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_WALLET_CATALOG_ID_COLUMN_NAME);
        WalletManagerMiddlewareTableLanguageColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_LANGUAGE_VERSION_COLUMN_NAME);

        /**
         * language table
         */
        DeveloperDatabaseTable  languageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_LANGUAGES_TABLE_TABLE_NAME, WalletManagerMiddlewareTableLanguageColumns);
        tables.add(languageTable);

        /**
         * SkinColumns table
         */

        List<String> WalletManagerMiddlewareTableSkinColumns = new ArrayList<>();
        WalletManagerMiddlewareTableSkinColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_ID_COLUMN_NAME);
        WalletManagerMiddlewareTableSkinColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_WALLET_CATALOG_ID_COLUMN_NAME);
        WalletManagerMiddlewareTableSkinColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_NAME_COLUMN_NAME);
        WalletManagerMiddlewareTableSkinColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_PREVIEW_IMAGE_COLUMN_NAME);
        WalletManagerMiddlewareTableSkinColumns.add(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_SKIN_VERSION_COLUMN_NAME);

        /**
         * Skin table
         */

        DeveloperDatabaseTable  cryptoTransactionsTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_SKINS_TABLE_TABLE_NAME, WalletManagerMiddlewareTableSkinColumns);
        tables.add(cryptoTransactionsTable);


        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory,  Database database, DeveloperDatabaseTable developerDatabaseTable) {
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
}

