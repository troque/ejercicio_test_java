/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantCreateNewIntraUserException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Matchers.any;

*/
/**
 * Created by natalia on 10/09/15.
 *//*


@RunWith(MockitoJUnitRunner.class)
public class SetLoggingLevelPerClassTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private ErrorManager errorManager;

    private WalletContactsMiddlewarePluginRoot pluginRoot;

    @Test
    public void setLoggingLevelPerClassTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantCreateNewIntraUserException {
        UUID testOwnerId = UUID.randomUUID();

        pluginRoot = new WalletContactsMiddlewarePluginRoot();


        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        pluginRoot.setId(testOwnerId);
        pluginRoot.setErrorManager(errorManager);

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
        newLoggingLevel.put(eccKeyPair.getPrivateKey(), LogLevel.AGGRESSIVE_LOGGING);

        pluginRoot.setLoggingLevelPerClass(newLoggingLevel);



    }
}

*/
