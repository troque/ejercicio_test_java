package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1;

import android.content.Context;
import android.util.Base64;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * This addon handles a layer of database representation.
 * Encapsulates all the necessary functions to manage the database , its tables and records.
 * For interfaces PluginDatabase the modules need to authenticate with their plugin ids
 *
 * Created by nattyco
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */

public final class PluginDatabaseSystemAndroidAddonRoot extends AbstractAddon implements PluginDatabaseSystem {

    private Context context;


    public PluginDatabaseSystemAndroidAddonRoot() {
        super(
                new AddonVersionReference(new Version()),
                true // true because uses system context (the core will assign the context if needed)
        );
    }

    @Override
    public final void start() throws CantStartPluginException {

        if (this.getOsContext() != null && this.getOsContext() instanceof Context) {
            context = (Context) this.getOsContext();
            this.serviceStatus = ServiceStatus.STARTED;
        } else {
            throw new CantStartPluginException(
                    "osContext: "+this.getOsContext(),
                    "Context is not instance of Android Context or is null."
            );
        }
    }
    /**
     * PluginDatabaseSystem Interface implementation.
     */

    @Override
    public final Database openDatabase(final UUID   ownerId     ,
                                       final String databaseName) throws CantOpenDatabaseException ,
                                                                   DatabaseNotFoundException {

        try {

            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.openDatabase();
            return database;

        } catch (final NoSuchAlgorithmException e) {

            throw new CantOpenDatabaseException(e, "Database Name : " + databaseName, "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final DatabaseNotFoundException exception){

            throw exception;
        } catch (final Exception e){

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception");
        }

    }

    @Override
    public final void deleteDatabase(final UUID   ownerId     ,
                                     final String databaseName) throws CantOpenDatabaseException ,
                                                                 DatabaseNotFoundException {

        try {

            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.deleteDatabase();

        } catch (final NoSuchAlgorithmException e){

            throw new CantOpenDatabaseException(e, "Database Name : " + databaseName, "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final DatabaseNotFoundException exception){

            throw exception;
        } catch (final Exception e){

            throw new CantOpenDatabaseException(e, null, "Unhandled Exception");
        }

    }

    @Override
    public final Database createDatabase(final UUID   ownerId     ,
                                         final String databaseName) throws CantCreateDatabaseException {

        try {
            String hasDBName = hashDataBaseName(databaseName);
            AndroidDatabase database = new AndroidDatabase(context.getFilesDir().getPath(), ownerId, hasDBName);
            database.createDatabase(hasDBName);
            return database;

        } catch (final NoSuchAlgorithmException e){

            throw new CantCreateDatabaseException(e, "Database Name : " + databaseName, "This is a hash failure, we have to check the hashing algorithm used for the generation of the Hashed Database Name");
        } catch (final Exception e){

            throw new CantCreateDatabaseException(e, null, "Unhandled Exception");
        }

    }

    private String hashDataBaseName(final String databaseName) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(databaseName.getBytes(Charset.forName("UTF-8")));
        byte[] digest = md.digest();
        byte[] encoded = Base64.encode(digest, 1);

        try {

            String encryptedString = new String(encoded, "UTF-8");

            encryptedString = encryptedString.replace("/","");

            return encryptedString.replace("\n","");

        } catch (final Exception e) {

            throw new NoSuchAlgorithmException (e);
        }
    }
}
