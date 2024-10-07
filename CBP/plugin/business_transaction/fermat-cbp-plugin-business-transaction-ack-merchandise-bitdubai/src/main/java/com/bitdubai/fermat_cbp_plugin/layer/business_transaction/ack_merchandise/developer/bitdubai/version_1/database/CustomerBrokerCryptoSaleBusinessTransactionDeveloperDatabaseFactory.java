package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.database.CustomerBrokerCryptoSaleBusinessTransactionDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CustomerBrokerCryptoSaleBusinessTransactionDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CustomerBrokerCryptoSaleBusinessTransactionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CustomerBrokerCryptoSaleBusinessTransactionDatabaseFactory customerBrokerCryptoSaleBusinessTransactionDatabaseFactory = new CustomerBrokerCryptoSaleBusinessTransactionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = customerBrokerCryptoSaleBusinessTransactionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Customer Broker Crypto Sale", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Customer Broker Crypto Sale columns.
         */
        List<String> customerBrokerCryptoSaleColumns = new ArrayList<String>();

        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TRANSACTION_ID_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_STATUS_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_BROKER_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PUBLIC_KEY_CUSTOMER_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CONTRACT_ID_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_TRANSACTION_ID_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_PAYMENT_CURRENCY_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_CURRENCY_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_MERCHANDISE_AMOUNT_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_EXECUTION_TRANSACTION_ID_COLUMN_NAME);
        customerBrokerCryptoSaleColumns.add(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_CRYPTO_CURRENCY_TYPE_COLUMN_NAME);
        /**
         * Table Customer Broker Crypto Sale addition.
         */
        DeveloperDatabaseTable customerBrokerCryptoSaleTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerCryptoSaleBusinessTransactionDatabaseConstants.CUSTOMER_BROKER_CRYPTO_SALE_TABLE_NAME, customerBrokerCryptoSaleColumns);
        tables.add(customerBrokerCryptoSaleTable);



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