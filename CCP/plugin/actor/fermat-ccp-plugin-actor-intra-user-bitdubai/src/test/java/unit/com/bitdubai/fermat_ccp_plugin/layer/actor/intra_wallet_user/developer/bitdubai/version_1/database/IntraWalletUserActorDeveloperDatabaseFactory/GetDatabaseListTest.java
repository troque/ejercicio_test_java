package unit.com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 24/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraWalletUserActorDeveloperDatabaseFactory intraUserIdentityDeveloperDatabaseFactory;

    @Test
    public void getDatabaseListTest_GetListOk() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        intraUserIdentityDeveloperDatabaseFactory = new IntraWalletUserActorDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraUserIdentityDeveloperDatabaseFactory.initializeDatabase();

        assertThat(intraUserIdentityDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory)).isInstanceOf(List.class);



    }

}