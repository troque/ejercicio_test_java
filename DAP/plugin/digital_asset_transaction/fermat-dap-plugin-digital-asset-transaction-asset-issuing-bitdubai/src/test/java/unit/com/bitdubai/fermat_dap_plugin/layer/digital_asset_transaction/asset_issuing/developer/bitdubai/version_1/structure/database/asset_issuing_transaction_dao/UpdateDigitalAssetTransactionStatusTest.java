package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 31/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateDigitalAssetTransactionStatusTest {
    AssetIssuingTransactionDao assetIssuingTransactionDao;
    UUID pluginId;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    String transactionID = "transactionID";
    List<DatabaseTableRecord> records;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(database);
        assetIssuingTransactionDao = new AssetIssuingTransactionDao(pluginDatabaseSystem, pluginId);

        records = new LinkedList<DatabaseTableRecord>();
        records.add(databaseTableRecord);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingTransactionDao.updateDigitalAssetTransactionStatus(transactionID, TransactionStatus.FORMING_GENESIS);
    }

    @Test
    public void test_Throws_CantExecuteQueryException() throws Exception {
        doThrow(new CantUpdateRecordException("error")).when(databaseTable).updateRecord(databaseTableRecord);
        catchException(assetIssuingTransactionDao).updateDigitalAssetTransactionStatus(transactionID, TransactionStatus.FORMING_GENESIS);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteQueryException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantUpdateRecordException.class);
    }
}
