package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
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
public class GetPendingDigitalAssetPublicKeysTest {
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

    String transactionId = "transactionId";
    List<DatabaseTableRecord> records;
    List<DatabaseTableRecord> recordsForException;
    String publicKeyExpected = "publicKeyExpected";
    List<String> pendingDigitalAssetPublicKeysExpected;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(database);
        assetIssuingTransactionDao = new AssetIssuingTransactionDao(pluginDatabaseSystem, pluginId);

        records = new LinkedList<>();
        records.add(databaseTableRecord);

        recordsForException = new LinkedList<>();
        recordsForException.add(databaseTableRecord);
        recordsForException.add(databaseTableRecord);

        pendingDigitalAssetPublicKeysExpected = new LinkedList<>();
        pendingDigitalAssetPublicKeysExpected.add(publicKeyExpected);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        when(databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME)).thenReturn(publicKeyExpected);
        when(databaseTableRecord.getIntegerValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME)).thenReturn(1);
    }

    @Test
    public void test_OK() throws Exception {
        List<String> pendingDigitalAssetPublicKeys = assetIssuingTransactionDao.getPendingDigitalAssetPublicKeys();
        assertThat(pendingDigitalAssetPublicKeys).isEqualTo(pendingDigitalAssetPublicKeysExpected);
    }

    @Test
    public void test_Throws_CantCheckAssetIssuingProgressException() throws Exception {
        doThrow(new CantOpenDatabaseException("error")).when(pluginDatabaseSystem).openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
        catchException(assetIssuingTransactionDao).getPendingDigitalAssetPublicKeys();
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCheckAssetIssuingProgressException.class);
    }
}
