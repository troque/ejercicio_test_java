package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Index;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.IndexHelper;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Alex on 11/2/2015.
 */

/**

 */

public class FiatIndexPluginRoot implements FiatIndexManager, DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Service, Plugin {

    IndexHelper indexHelper = new IndexHelper();

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;

    /**
     * DealsWithLogManager interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cbp_plugin.layer.sub_app_module.crypto_broker_community.developer.bitdubai.version_1.FiatIndexPluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */
        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (FiatIndexPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    FiatIndexPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    FiatIndexPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    FiatIndexPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception exception) {
            // this.errorManager.reportUnexpectedPluginException(Plugins., UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
        }
    }


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * DealsWithError interface implementation
     *
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }



    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * Plugin interface implementation
     *
     * @param uuid
     */
    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }



    public static LogLevel getLogLevelByClass(String className) {
        try {
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return FiatIndexPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e) {
            System.err.println("CantGetLogLevelByClass: " + e.getMessage());
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }



    @Override
    public FiatCurrency getReferenceCurrency() {
        return FiatCurrency.US_DOLLAR;
    }

    @Override
    public Collection<FiatCurrency> getSupportedCurrencies() {
        Collection<FiatCurrency> c = new HashSet<>();
        for (FiatCurrency f : FiatCurrency.values())
            c.add(f);
        return c;
    }

    @Override
    public FiatIndex getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {
        return indexHelper.getCurrentIndex(currency);
    }

    @Override
    public FiatIndex getIndexInDate(FiatCurrency currency, long timestamp) throws CantGetIndexException {
        return null;
    }

    @Override
    public Collection<FiatIndex> getQueriedIndexHistory(FiatCurrency currency) {
        return null;
    }



}