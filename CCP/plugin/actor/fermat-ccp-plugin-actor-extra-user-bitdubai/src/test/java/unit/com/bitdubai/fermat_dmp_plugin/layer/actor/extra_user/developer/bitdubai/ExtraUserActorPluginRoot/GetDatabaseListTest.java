package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserActorPluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import junit.framework.TestCase;

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
 * Created by natalia on 03/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest extends TestCase {

    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    DeveloperDatabase developerDatabase;

    @Mock
    DeveloperDatabaseTable developerDatabaseTable;

    private ExtraUserActorPluginRoot pluginRoot;


    @Test
    public void getDatabaseList_GetListOk() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        pluginRoot = new ExtraUserActorPluginRoot();
        pluginRoot.setErrorManager(mockErrorManager);

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setId(UUID.randomUUID());

        assertThat(pluginRoot.getDatabaseList(developerObjectFactory)).isInstanceOf(List.class);


    }

}