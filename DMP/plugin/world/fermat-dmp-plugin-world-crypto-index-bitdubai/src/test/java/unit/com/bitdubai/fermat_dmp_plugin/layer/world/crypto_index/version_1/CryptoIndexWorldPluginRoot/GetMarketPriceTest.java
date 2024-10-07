package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.CryptoIndexWorldPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.CryptoIndexWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by francisco on 15/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMarketPriceTest {

    /**
     * DealsWithEvents Interface member variables.
     */
    @Mock
    private EventManager mockEventManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private LogManager mocklogManager;

    /**
     * PluginDatabaseSystem Interface member variables.
     */

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem= Mockito.mock(PluginDatabaseSystem.class);

    @Mock
    private CryptoIndexDatabaseFactory mockCryptoIndexDatabaseFactory= Mockito.mock(CryptoIndexDatabaseFactory.class);;

    @Mock
    DatabaseFactory mockDatabaseFactory = Mockito.mock(DatabaseFactory.class);;
    @Mock
    private DatabaseTableFactory mockDatabaseTableFactory = Mockito.mock(DatabaseTableFactory.class);
    @Mock
    private CryptoIndexDao cryptoIndexDao;

    private CryptoCurrency cryptoCurrency;

    private FiatCurrency fiatCurrency;

    private long time;

    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);


    private CryptoIndexWorldPluginRoot cryptoIndexWorldPluginRoot;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();
        cryptoIndexWorldPluginRoot = new CryptoIndexWorldPluginRoot();
        cryptoIndexWorldPluginRoot.setErrorManager(mockErrorManager);
        cryptoIndexWorldPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        cryptoIndexWorldPluginRoot.setId(pluginId);
        setUpMockitoRules();
        cryptoIndexDao.initializeDatabase();
        cryptoIndexWorldPluginRoot.start();
    }

    public void setUpMockitoRules() throws Exception {
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME)).thenReturn(mockDatabase);
        when(mockCryptoIndexDatabaseFactory.createDatabase(pluginId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME)).thenReturn(mockDatabase);
        cryptoIndexDao = new CryptoIndexDao(mockPluginDatabaseSystem, pluginId);
    }
    @Test
    public void TestGetMarketPrice()throws Exception{
        cryptoCurrency= CryptoCurrency.getByCode("BTC");
        fiatCurrency= FiatCurrency.getByCode("USD");
        double price;
        price=cryptoIndexWorldPluginRoot.getMarketPrice(fiatCurrency,cryptoCurrency,0);
        Assertions.assertThat(price).isNotNull();
    }
}
