package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import static org.fest.assertions.api.Assertions.*;
import static org.robolectric.Shadows.shadowOf;
import static com.googlecode.catchexception.CatchException.*;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import java.io.File;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.06.27..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class OpenDatabaseTest {

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
    public void OpenDatabase_DatabaseInPath_InvokedSuccesfully() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        System.out.println(mockContext);
        testDatabase.createDatabase(testDatabaseName);
        catchException(testDatabase).openDatabase();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void OpenDatabase_DatabaseInAlreadyOpen_InvokedSuccesfully() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
        testDatabase.openDatabase();
        catchException(testDatabase).openDatabase();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void OpenDatabase_NoDatabaseInPath_ThrowException() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        catchException(testDatabase).openDatabase();
        assertThat(caughtException()).isInstanceOf(DatabaseNotFoundException.class);
        caughtException().printStackTrace();
    }

    @Test
    public void OpenDatabase_DatabaseInPath_InvokedSuccesfully1() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
        catchException(testDatabase).openDatabase();
        assertThat(caughtException()).isNull();
        catchException(testDatabase).closeDatabase();
        assertThat(caughtException()).isNull();
        catchException(testDatabase).openDatabase();
        assertThat(caughtException()).isNull();
    }

}
