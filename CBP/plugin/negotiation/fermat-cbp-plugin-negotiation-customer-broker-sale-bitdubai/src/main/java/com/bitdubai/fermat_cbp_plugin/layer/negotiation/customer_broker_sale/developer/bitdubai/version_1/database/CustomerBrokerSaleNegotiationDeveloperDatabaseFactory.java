package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleNegotiationDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.database.CustomerBrokerSaleNegotiationDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CustomerBrokerSaleNegotiationDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CustomerBrokerSaleNegotiationDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCustomerBrokerSaleNegotiationDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCustomerBrokerSaleNegotiationDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CustomerBrokerSaleNegotiationDatabaseFactory customerBrokerSaleNegotiationDatabaseFactory = new CustomerBrokerSaleNegotiationDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = customerBrokerSaleNegotiationDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCustomerBrokerSaleNegotiationDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Customer Broker Sale", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Negotiations columns.
         */
        List<String> negotiationsColumns = new ArrayList<String>();

        negotiationsColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_NEGOTIATION_ID_COLUMN_NAME);
        negotiationsColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_CUSTOMER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        negotiationsColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_START_DATETIME_COLUMN_NAME);
        negotiationsColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_STATUS_COLUMN_NAME);
        /**
         * Table Negotiations addition.
         */
        DeveloperDatabaseTable negotiationsTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.NEGOTIATIONS_TABLE_NAME, negotiationsColumns);
        tables.add(negotiationsTable);

        /**
         * Table Clauses columns.
         */
        List<String> clausesColumns = new ArrayList<String>();

        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_CLAUSE_ID_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_NEGOTIATION_ID_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TYPE_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_VALUE_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_STATUS_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_PROPOSED_BY_COLUMN_NAME);
        clausesColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_INDEX_ORDER_COLUMN_NAME);
        /**
         * Table Clauses addition.
         */
        DeveloperDatabaseTable clausesTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSES_TABLE_NAME, clausesColumns);
        tables.add(clausesTable);

        /**
         * Table Clause Status Log columns.
         */
        List<String> clauseStatusLogColumns = new ArrayList<String>();

        clauseStatusLogColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_CHANGE_ID_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_CLAUSE_ID_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_STATUS_COLUMN_NAME);
        clauseStatusLogColumns.add(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_CHANGE_DATETIME_COLUMN_NAME);
        /**
         * Table Clause Status Log addition.
         */
        DeveloperDatabaseTable clauseStatusLogTable = developerObjectFactory.getNewDeveloperDatabaseTable(CustomerBrokerSaleNegotiationDatabaseConstants.CLAUSE_STATUS_LOG_TABLE_NAME, clauseStatusLogColumns);
        tables.add(clauseStatusLogTable);



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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        for (DatabaseTableRecord row : records) {
            List<String> developerRow = new ArrayList<String>();
            /**
             * for each row in the table list
             */
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }


        /**
         * return the list of DeveloperRecords for the passed table.
         */
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
