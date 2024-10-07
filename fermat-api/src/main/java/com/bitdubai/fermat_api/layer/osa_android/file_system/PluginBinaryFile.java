package com.bitdubai.fermat_api.layer.osa_android.file_system;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

/**
 *
 *  <p>The abstract class <code>PluginBinaryFile</code> is a interface
 *     that define the methods to get, set and save binary file content.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   11/02/15.
 * */
public interface PluginBinaryFile {
    
    public byte[] getContent ();

    public void setContent (byte[] content);

    public void persistToMedia() throws CantPersistFileException;

    public void loadFromMedia() throws CantLoadFileException;

    public void delete() throws FileNotFoundException;
}
