package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;


/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetsOverBitcoinCryptoVaultDatabaseConstants {

    /**
     * key_accounts database table definition.
     */
    static final String KEY_ACCOUNTS_TABLE_NAME = "key_accounts";

    static final String KEY_ACCOUNTS_ID_COLUMN_NAME = "id";
    static final String KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME = "description";

    static final String KEY_ACCOUNTS_FIRST_KEY_COLUMN = "id";

    /**
     * key_Maintenance database table definition.
     */
    static final String KEY_MAINTENANCE_TABLE_NAME = "key_maintenance";

    static final String KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME = "account_id";
    static final String KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME = "generated_keys";
    static final String KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME = "used_keys";

    static final String KEY_MAINTENANCE_FIRST_KEY_COLUMN = "account_id";

    /**
     * key_Maintenance_Monitor database table definition.
     */
    static final String KEY_MAINTENANCE_MONITOR_TABLE_NAME = "key_maintenance_monitor";

    static final String KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME = "execution_number";
    static final String KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME = "account_id";
    static final String KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME = "execution_date";
    static final String KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME = "generated_keys";
    static final String KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME = "used_keys";
    static final String KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME = "threshold";

    static final String KEY_MAINTENANCE_MONITOR_FIRST_KEY_COLUMN = "execution_number";

    /**
     * active_Networks database table definition.
     */
    static final String ACTIVE_NETWORKS_TABLE_NAME = "active_networks";

    static final String ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME = "networktype";
    static final String ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME = "activation_date";

    static final String ACTIVE_NETWORKS_FIRST_KEY_COLUMN = "networktype";

}