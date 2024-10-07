package com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.exceptions.CantInitializeBankMoneyRestockDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_restock.developer.bitdubai.version_1.database.BusinessTransactionBankMoneyRestockDeveloperFactory.java
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Franklin Marcano - (franklinmarcano970@gmail.com) on 16/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */public class BusinessTransactionBankMoneyRestockDeveloperFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {
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
    public BusinessTransactionBankMoneyRestockDeveloperFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    public void initializeDatabase() throws CantInitializeBankMoneyRestockDatabaseException
    {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);
            database.closeDatabase();

        }catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeBankMoneyRestockDatabaseException(cantOpenDatabaseException.getMessage());

        }catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            BusinessTransactionBankMoneyRestockDatabaseFactory businessTransactionBankMoneyRestockDatabaseFactory = new BusinessTransactionBankMoneyRestockDatabaseFactory(this.pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = businessTransactionBankMoneyRestockDatabaseFactory.createDatabase(pluginId, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME);
                database.closeDatabase();
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeBankMoneyRestockDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME, BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_DATABASE_NAME));

        return databases;
    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Project columns.
         */
        List<String> projectColumns = new ArrayList<String>();

        /**
         * Table Asset Factory addition.
         */
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_ID_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_PUBLIC_KEY_ACTOR_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_FIAT_CURRENCY_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CBP_WALLET_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BNK_WALLET_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_BANK_ACCOUNT_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_CONCEPT_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_MEMO_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_AMOUNT_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TIMESTAMP_COLUMN_NAME);
        projectColumns.add(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_RESTOCK_TRANSACTION_STATUS_COLUMN_NAME);

        DeveloperDatabaseTable bankMoneyRestockTable = developerObjectFactory.getNewDeveloperDatabaseTable(BussinessTransactionBankMoneyRestockDatabaseConstants.BANK_MONEY_STOCK_TABLE_NAME, projectColumns);
        tables.add(bankMoneyRestockTable);

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
}
