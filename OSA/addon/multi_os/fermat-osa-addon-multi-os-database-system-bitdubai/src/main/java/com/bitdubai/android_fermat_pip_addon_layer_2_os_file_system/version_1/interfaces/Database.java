package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.DatabaseTransactionFailedException;

/**
 * Created by ciencias on 22.01.15.
 */
public interface Database {

    public void executeQuery();

    public DatabaseTable getTable(String tableName);

    public DatabaseTransaction newTransaction();

    public void executeTransaction(DatabaseTransaction transaction) throws DatabaseTransactionFailedException;
}
