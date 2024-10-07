package com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantGetUserDeveloperIdentitiesException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.ReedemPointIdentityPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantGetAssetRedeemPointIdentityProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantGetAssetRedeemPointIdentityPrivateKeyException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeAssetRedeemPointIdentityDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantListAssetRedeemPointIdentitiesException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantPersistPrivateKeyException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.exceptions.CantPersistProfileImageException;
import com.bitdubai.fermat_dap_plugin.layer.identity.redeem.point.developer.bitdubai.version_1.structure.IdentityAssetRedeemPointImpl;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 02/11/15.
 */
public class AssetRedeemPointIdentityDao implements DealsWithPluginDatabaseSystem {
    /**
     * Represent the Plugin Database.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    /**
     * Constructor with parameters
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */

    public AssetRedeemPointIdentityDao(PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) throws CantInitializeAssetRedeemPointIdentityDatabaseException
    {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;

        try {
            initializeDatabase();
        } catch (CantInitializeAssetRedeemPointIdentityDatabaseException e) {
            throw new CantInitializeAssetRedeemPointIdentityDatabaseException(e.getMessage());
        }
    }

    /**
     * Represent de Database where i will be working with
     */
    Database database;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetRedeemPointIdentityDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetRedeemPointIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */

            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DB_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetRedeemPointIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetRedeemPointIdentityDatabaseFactory assetRedeemPointIdentityDatabaseFactory = new AssetRedeemPointIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetRedeemPointIdentityDatabaseFactory.createDatabase(pluginId);
                database.closeDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetRedeemPointIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        } catch (Exception e) {

            throw new CantInitializeAssetRedeemPointIdentityDatabaseException(e.getMessage());

        }
    }

    /**
     * first i persist private key on a file
     * second i insert the record in database
     * third i save the profile image file
     *
     * @param alias
     * @param publicKey
     * @param privateKey
     * @param deviceUser
     * @param profileImage
     * @throws CantCreateNewDeveloperException
     */
    public void createNewUser (String alias, String publicKey,String privateKey, DeviceUser deviceUser,byte[] profileImage) throws CantCreateNewDeveloperException {

        try {
            if (aliasExists (alias)) {
                throw new CantCreateNewDeveloperException ("Cant create new Asset Issuer, alias exists.", "Asset Issuer Identity", "Cant create new Asset Issuer, alias exists.");
            }

            persistNewUserPrivateKeysFile(publicKey, privateKey);

            DatabaseTable table = this.database.getTable(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);
            DatabaseTableRecord record = table.getEmptyRecord();

            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, publicKey);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias);
            record.setStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey());

            table.insertRecord(record);

            if(profileImage!=null)
                persistNewUserProfileImage(publicKey, profileImage);

        } catch (CantInsertRecordException e){
            // Cant insert record.
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Asset IssuerIdentity", "Cant create new Asset Issuer, insert database problems.");

        } catch (CantPersistPrivateKeyException e){
            // Cant insert record.
            throw new CantCreateNewDeveloperException (e.getMessage(), e, "Asset Issuer Identity", "Cant create new Asset Issuer,persist private key error.");

        } catch (Exception e) {
            // Failure unknown.

            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Asset Issuer Identity", "Cant create new Asset Issuer, unknown failure.");
        }
    }

    public List<RedeemPointIdentity> getIdentityAssetRedeemPointsFromCurrentDeviceUser (DeviceUser deviceUser) throws CantListAssetRedeemPointIdentitiesException {


        // Setup method.
        List<RedeemPointIdentity> list = new ArrayList<>(); // Intra User list.
        DatabaseTable table; // Intra User table.

        // Get Redeem Point identities list.
        try {

            /**
             * 1) Get the table.
             */
            table = this.database.getTable (AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            if (table == null) {
                /**
                 * Table not found.
                 */
                throw new CantGetUserDeveloperIdentitiesException ("Cant get Asset Issuer identity list, table not found.", "Asset IssuerIdentity", "Cant get Intra User identity list, table not found.");
            }


            // 2) Find the Redeem Point.
            table.setStringFilter(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, deviceUser.getPublicKey(), DatabaseFilterType.EQUAL);
            table.loadToMemory();


            // 3) Get Redeem Point.
            for (DatabaseTableRecord record : table.getRecords ()) {

                // Add records to list.
                list.add(new IdentityAssetRedeemPointImpl(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME),
                        record.getStringValue (AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME),
                        getAssetIssuerIdentityPrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_PUBLIC_KEY_COLUMN_NAME)),
                        getAssetIssuerProfileImagePrivateKey(record.getStringValue(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME)),
                        pluginFileSystem,
                        pluginId));
            }
        } catch (CantLoadTableToMemoryException e) {
            throw new CantListAssetRedeemPointIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Cant load " + AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME + " table in memory.");
        } catch (CantGetAssetRedeemPointIdentityPrivateKeyException e) {
            // Failure unknown.
            throw new CantListAssetRedeemPointIdentitiesException(e.getMessage(), e, "Asset Redeem Point Identity", "Can't get private key.");

        } catch (Exception e) {
            throw new CantListAssetRedeemPointIdentitiesException(e.getMessage(), FermatException.wrapException(e), "Asset Redeem Point Identity", "Cant get Asset Issuer identity list, unknown failure.");
        }

        // Return the list values.
        return list;
    }

    public byte[] getAssetIssuerProfileImagePrivateKey(String publicKey) throws CantGetAssetRedeemPointIdentityProfileImageException {
        byte[] profileImage;
        try {
            PluginBinaryFile file = this.pluginFileSystem.getBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            profileImage = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetRedeemPointIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            //Not image found return byte null
            profileImage = new byte[0];
            // throw new CantGetIntraWalletUserIdentityProfileImageException("CAN'T GET IMAGE PROFILE ", e, "Error getting developer identity private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetAssetRedeemPointIdentityProfileImageException("CAN'T GET IMAGE PROFILE ",FermatException.wrapException(e),"", "");
        }

        return profileImage;
    }

    /**
     * Private Methods
     */

    private void  persistNewUserPrivateKeysFile(String publicKey,String privateKey) throws CantPersistPrivateKeyException {
        try {
            PluginTextFile file = this.pluginFileSystem.createTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PRIVATE_KEYS_FILE_NAME  + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(privateKey);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ", e, "Error creating file.", null);
        }
        catch (Exception e) {
            throw  new CantPersistPrivateKeyException("CAN'T PERSIST PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }
    }

    private void  persistNewUserProfileImage(String publicKey,byte[] profileImage) throws CantPersistProfileImageException {
        try {
            PluginBinaryFile file = this.pluginFileSystem.createBinaryFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(profileImage);

            file.persistToMedia();
        } catch (CantPersistFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error persist file.", null);

        } catch (CantCreateFileException e) {
            throw new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ", e, "Error creating file.", null);
        }
        catch (Exception e) {
            throw  new CantPersistProfileImageException("CAN'T PERSIST PROFILE IMAGE ",FermatException.wrapException(e),"", "");
        }
    }

    /**
     * <p>Method that check if alias exists.
     * @param alias
     * @return boolean exists
     * @throws CantCreateNewDeveloperException
     */
    private boolean aliasExists (String alias) throws CantCreateNewDeveloperException {


        DatabaseTable table;
        /**
         * Get developers identities list.
         * I select records on table
         */

        try {
            table = this.database.getTable (AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME);

            if (table == null) {
                throw new CantGetUserDeveloperIdentitiesException("Cant check if alias exists", "Asset Issuer Identity", "");
            }

            table.setStringFilter(AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_ALIAS_COLUMN_NAME, alias, DatabaseFilterType.EQUAL);
            table.loadToMemory();

            return table.getRecords ().size () > 0;


        } catch (CantLoadTableToMemoryException em) {
            throw new CantCreateNewDeveloperException (em.getMessage(), em, "Asset Issuer  Identity", "Cant load " + AssetRedeemPointIdentityDatabaseConstants.ASSET_REDEEM_POINT_IDENTITY_TABLE_NAME + " table in memory.");

        } catch (Exception e) {
            throw new CantCreateNewDeveloperException (e.getMessage(), FermatException.wrapException(e), "Asset Issuer  Identity", "unknown failure.");
        }
    }

    public String getAssetIssuerIdentityPrivateKey(String publicKey) throws CantGetAssetRedeemPointIdentityPrivateKeyException {
        String privateKey = "";
        try {
            PluginTextFile file = this.pluginFileSystem.getTextFile(pluginId,
                    DeviceDirectory.LOCAL_USERS.getName(),
                    ReedemPointIdentityPluginRoot.ASSET_REDEEM_POINT_PRIVATE_KEYS_FILE_NAME  + "_" + publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );


            file.loadFromMedia();

            privateKey = file.getContent();

        } catch (CantLoadFileException e) {
            throw new CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error loaded file.", null);

        }
        catch (FileNotFoundException |CantCreateFileException e) {
            throw new CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ", e, "Error getting developer identity private keys file.", null);
        }
        catch (Exception e) {
            throw  new CantGetAssetRedeemPointIdentityPrivateKeyException("CAN'T GET PRIVATE KEY ",FermatException.wrapException(e),"", "");
        }

        return privateKey;
    }
}
