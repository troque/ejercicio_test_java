package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetPluginIdException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartPluginIdsManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginIdsManager</code> haves all the main functionality
 * to manage fermat plugins ids.
 *
 * Created by Leon Acosta (laion.cj01@gmail.com) on 20/10/2015.
 */
public final class FermatPluginIdsManager {

    private final String PLUGIN_IDS_DIRECTORY_NAME = DeviceDirectory.SYSTEM.getName();

    private final String PLUGIN_IDS_FILE_NAME = "plugin_ids";


    private final String PAIR_SEPARATOR   = ";";
    private final String PLUGIN_SEPARATOR = "|";

    private final PlatformFileSystem platformFileSystem;

    private final Map<PluginVersionReference, UUID> pluginIdsMap;

    protected FermatPluginIdsManager(final PlatformFileSystem platformFileSystem) throws CantStartPluginIdsManagerException {

        this.platformFileSystem = platformFileSystem;
        this.pluginIdsMap       = new HashMap<>();

        chargeOrCreateSavedIds();
    }

    private void chargeOrCreateSavedIds() throws CantStartPluginIdsManagerException {

        try {
            try {
                final String[] stringPluginIdPairs = getPluginIdsFile().getContent().split(Pattern.quote(PLUGIN_SEPARATOR));
                boolean changed = false;

                for (final String stringPluginIdPair : stringPluginIdPairs) {

                    if (!stringPluginIdPair.equals("")) {

                        final String[] pluginIdPair = stringPluginIdPair.split(PAIR_SEPARATOR);

                        try {

                            pluginIdsMap.put(
                                    PluginVersionReference.getByKey(pluginIdPair[0]),
                                    UUID.fromString(pluginIdPair[1])
                            );
                        } catch (InvalidParameterException e) {

                            changed = true;
                        }
                    }
                }
                if (changed)
                    savePluginIdsFile(getPluginIdsFile());

            } catch(final CantCreateFileException  |
                          CantPersistFileException e) {

                throw new CantStartPluginIdsManagerException(e, "", "Problem with plugins id file.");
            }
        } catch (final FileNotFoundException fileNotFoundException) {

            try {
                final PlatformTextFile platformTextFile = platformFileSystem.createFile(PLUGIN_IDS_DIRECTORY_NAME, PLUGIN_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                savePluginIdsFile(platformTextFile);

            } catch (final CantCreateFileException  |
                           CantPersistFileException e) {

                throw new CantStartPluginIdsManagerException(e, "", "Problem with plugins id file.");
            }
        }

    }

    /**
     * returns the id of the desired plugin.
     * if it doesn't exists, creates a new one and saves it in the plugins id file.
     *
     * @param plugin reference of the plugin of which you want to have the id.
     *
     * @return the id of the plugin
     *
     * @throws CantGetPluginIdException if something goes wrong.
     */
    public final UUID getPluginId(final PluginVersionReference plugin) throws CantGetPluginIdException {

        final UUID pluginId = pluginIdsMap.get(plugin);

        if (pluginId != null)
            return pluginId;
        else
            return registerNewPlugin(plugin);
    }

    private UUID registerNewPlugin(final PluginVersionReference plugin) throws CantGetPluginIdException {

        try {

            final UUID newId = UUID.randomUUID();

            pluginIdsMap.put(plugin, newId);

            savePluginIdsFile(getPluginIdsFile());

            return newId;

        } catch(final CantPersistFileException |
                      CantCreateFileException  |
                      FileNotFoundException    e) {

            throw new CantGetPluginIdException(
                    plugin.toString(),
                    "There is a problem with the plugins id file."
            );
        }
    }

    private PlatformTextFile getPluginIdsFile() throws CantCreateFileException,
                                                       FileNotFoundException  {

        return platformFileSystem.getFile(
                PLUGIN_IDS_DIRECTORY_NAME,
                PLUGIN_IDS_FILE_NAME     ,
                FilePrivacy.PRIVATE      ,
                FileLifeSpan.PERMANENT
        );
    }

    private void savePluginIdsFile(final PlatformTextFile platformTextFile) throws CantCreateFileException  ,
                                                                                   CantPersistFileException {

        String fileContent = "";
        for (final Map.Entry<PluginVersionReference, UUID> plugin : pluginIdsMap.entrySet())
            fileContent = fileContent + plugin.getKey().toKey() + PAIR_SEPARATOR + plugin.getValue() + PLUGIN_SEPARATOR;

        platformTextFile.setContent(fileContent);
        platformTextFile.persistToMedia();
    }

}