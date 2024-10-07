package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.agent;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRequestListActorAssetRedeemPointRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.RedeemPointActorPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantAddPendingRedeemPointException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure.RedeemPointActorDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {


    private Thread agentThread;
    private MonitorAgent monitorAgent;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    RedeemPointActorDao redeemPointActorDao;
    UUID pluginId;
    String userPublicKey;
    AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager;
    RedeemPointActorPluginRoot redeemPointPluginRoot;

    public ActorAssetRedeemPointMonitorAgent(EventManager eventManager,
                                             PluginDatabaseSystem pluginDatabaseSystem,
                                             ErrorManager errorManager,
                                             UUID pluginId,
                                             AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager,
                                             RedeemPointActorDao redeemPointActorDao,
                                             RedeemPointActorPluginRoot redeemPointPluginRoot) {

        this.pluginId = pluginId;
        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.assetRedeemPointActorNetworkServiceManager = assetRedeemPointActorNetworkServiceManager;
        this.redeemPointActorDao = redeemPointActorDao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.redeemPointPluginRoot = redeemPointPluginRoot;
    }

    @Override
    public void start() throws CantStartAgentException {
        monitorAgent = new MonitorAgent(this.errorManager, this.pluginDatabaseSystem);
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }


    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    private class MonitorAgent implements Runnable {

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 60000;/*  / 1000 = TIME in SECONDS = 60 seconds */
        boolean threadWorking;

        public MonitorAgent(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem) {
            this.errorManager = errorManager;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {
            threadWorking = true;

            while (threadWorking) {

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    doTheMainTask();
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantCreateActorRedeemPointException {
            try {
//                test_RegisterActorNetworkService();

                listByActorAssetRedeemPointNetworkService();

            } catch (CantCreateActorRedeemPointException e) {
                throw new CantCreateActorRedeemPointException("CAN'T ADD NEW ACTOR ASSET ISSUER IN ACTOR NETWORK SERVICE", e, "", "");
            }
        }

        private void listByActorAssetRedeemPointNetworkService() throws CantCreateActorRedeemPointException {
            try {
                if (assetRedeemPointActorNetworkServiceManager != null) {
                    List<ActorAssetRedeemPoint> list = assetRedeemPointActorNetworkServiceManager.getListActorAssetRedeemPointRegistered();
                    if (list.isEmpty()) {
                        System.out.println("Actor Asset Redeem Point - Lista de Actor Asset Network Service: RECIBIDA VACIA - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
                        //TODO List Empty State = DISCONNECTED_REMOTELY
                        System.out.println("Actor Asset Redeem Point - Se procede actualizar Lista en TABLA (si) Existiera algun Registro");
                        redeemPointActorDao.createNewAssetRedeemPointRegisterInNetworkServiceByList(list);
                    } else {
                        System.out.println("Actor Asset Redeem Point - Se Recibio Lista de: " + list.size() + " Actors desde Actor Network Service - SE PROCEDE A SU REGISTRO");
                        //TODO new Actors State = PENDING_LOCALLY_ACCEPTANCE
                        int recordInsert = redeemPointActorDao.createNewAssetRedeemPointRegisterInNetworkServiceByList(list);
                        System.out.println("Actor Asset Redeem Point - Se Registro en tabla REGISTER Lista de: " + recordInsert + " Actors desde Actor Network Service");
                    }
                } else {
                    System.out.println("Actor Asset assetRedeemPointActorNetworkServiceManager: " + assetRedeemPointActorNetworkServiceManager);
                }
            } catch (CantRequestListActorAssetRedeemPointRegisteredException e) {
                throw new CantCreateActorRedeemPointException("CAN'T ADD NEW ASSET USER ACTOR NETWORK SERVICE", e, "", "");
            } catch (CantAddPendingRedeemPointException e) {
                e.printStackTrace();
            }
        }
    }
}
