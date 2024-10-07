package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalia on 26/03/2015.
 */

/**
 * This class define methods to manage insert and update transactions on the database.
 *
 * *
 */

public class AndroidDatabaseTransaction implements DatabaseTransaction {

    /**
     * AndroidDatabaseTransaction Interface member variables.
     */

    private List<DatabaseTable> updateTables;
    private List<DatabaseTableRecord>  updateRecords;

    private List<DatabaseTable> insertTables;
    private List<DatabaseTableRecord>  insertRecords;

    private List<DatabaseTable> selectTables;
    private List<DatabaseTableRecord>  selectRecords;

    /**
     * DatabaseTransaction interface implementation.
     */

    /**
     *<p>This method add a table and a record to the update transaction
     *
     * @param table DatabaseTable object to modified
     * @param record DatabaseTableRecord object fields and values to modified
     */
    @Override
    public void addRecordToUpdate(DatabaseTable table, DatabaseTableRecord record){
        if(updateTables == null)
            updateTables = new ArrayList<DatabaseTable>();

        if(updateRecords == null)
            updateRecords = new ArrayList<DatabaseTableRecord>();

        updateTables.add(table);
        updateRecords.add(record);
    }

    /**
     * <p>This method add a table and a record to the update transaction
     *
     * @param table DatabaseTable object to inserted
     * @param record DatabaseTableRecord object fields and values to inserted
     */
    @Override
    public void addRecordToInsert(DatabaseTable table, DatabaseTableRecord record){

        if(insertTables == null)
            insertTables = new ArrayList<DatabaseTable>();

        if(insertRecords == null)
            insertRecords = new ArrayList<DatabaseTableRecord>();

        insertTables.add(table);
        insertRecords.add(record);
    }

    @Override
    public void addRecordToSelect(DatabaseTable selectTable, DatabaseTableRecord selectRecord){

        if(selectTables == null)
            selectTables = new ArrayList<DatabaseTable>();

        if(selectRecords == null)
            selectRecords = new ArrayList<DatabaseTableRecord>();

        selectTables.add(selectTable);
        selectRecords.add(selectRecord);

    }
    /**
     * <p>This method gets list of DatabaseTableRecord object that will be modified
     *
     * @return List of DatabaseTableRecord object to update
     */
    @Override
    public List<DatabaseTableRecord> getRecordsToUpdate(){
       return updateRecords;
    }

    /**
     *<p>This method gets list of DatabaseTableRecord object that will be inserted
     *
     * @return List of DatabaseTableRecord object to insert
     */
    @Override
    public List<DatabaseTableRecord> getRecordsToInsert(){

        return insertRecords;
    }

    @Override
    public List<DatabaseTableRecord> getRecordsToSelect(){
        return  this.selectRecords;
    }
    /**
     *<p>This method gets list of DatabaseTable object that will be updated
     *
     * @return List of DatabaseTable to update
     */
    @Override
    public List<DatabaseTable> getTablesToUpdate(){
        return this.updateTables;
    }

    /**
     *<p>This method gets list of DatabaseTable object that will be inserted
     *
     * @return List of DatabaseTable to insert
     */
    @Override
    public List<DatabaseTable> getTablesToInsert(){

        return insertTables;
    }

    @Override
    public List<DatabaseTable> getTablesToSelect(){
        return this.selectTables;
    }

    @Override
    public String toString(){

        //los select van a ser asignados a una variable y
        // esa variable va a ser usada en un insert o update para asignarla a un campo
        StringBuffer buffer = new StringBuffer();

        buffer.append("SELECT TABLES:");
        if (selectTables == null)
            selectTables = new ArrayList<>();

        for(DatabaseTable table : selectTables)
            buffer.append(" " + table.toString());
        buffer.append("\n");

        buffer.append("SELECT RECORDS:");

        if (selectRecords == null)
            selectRecords = new ArrayList<>();
        for(DatabaseTableRecord record : selectRecords)
            buffer.append(" " + record.toString());
        buffer.append("\n");

        buffer.append("INSERT TABLES:");

        if (insertTables == null)
            insertTables = new ArrayList<>();
        for(DatabaseTable table : insertTables)
            buffer.append(" " + table.toString());
        buffer.append("\n");
        buffer.append("INSERT RECORDS:");

        if (insertRecords == null)
            insertRecords = new ArrayList<>();
        for(DatabaseTableRecord record : insertRecords)
            buffer.append(" " + record.toString());
        buffer.append("\n");
        buffer.append("UPDATE TABLES:");

        if (updateTables == null)
            updateTables = new ArrayList<>();
        for(DatabaseTable table : updateTables)
            buffer.append(" " + table.toString());
        buffer.append("\n");
        buffer.append("UPDATE RECORDS:");

        if (updateRecords == null)
            updateRecords = new ArrayList<>();
        for(DatabaseTableRecord record : updateRecords)
            buffer.append(" " + record.toString());
        return buffer.toString();
    }

}
