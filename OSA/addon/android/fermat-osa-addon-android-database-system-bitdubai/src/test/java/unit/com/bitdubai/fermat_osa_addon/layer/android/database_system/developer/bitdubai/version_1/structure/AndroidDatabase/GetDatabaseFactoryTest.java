package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;
import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

/**
 * Created by jorgegonzalez on 2015.07.06..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class GetDatabaseFactoryTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";

    @Before
    public void setUpContext(){
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
    }

    @Test
    public void getDatabaseFactoryTest() throws Exception{


        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        assertThat(testDatabase.getDatabaseFactory())
                .isNotNull()
                .isInstanceOf(DatabaseFactory.class);
    }

    @Test
    public void newTableFactoryTest() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        assertThat(testDatabase.newTableFactory("table"))
                .isNotNull()
                .isInstanceOf(DatabaseTableFactory.class);
    }

    @Test
    public void newTableFactoryTwoParamsTest() throws Exception{
        UUID ownerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, ownerId, testDatabaseName);
        assertThat(testDatabase.newTableFactory(ownerId,"table"))
                .isNotNull()
                .isInstanceOf(DatabaseTableFactory.class);
    }
}
