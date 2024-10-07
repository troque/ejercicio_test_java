package com.bitdubai.sub_app.wallet_publisher.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubApp;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletPublisherSubAppSession extends AbstractFermatSession<InstalledSubApp,WalletPublisherModuleManager,SubAppResourcesProviderManager> implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession{



    /**
     * SubApps type
     */
    SubApps subApps;

    /**
     * Active objects in wallet session
     */
    Map<String,Object> data;

    /**
     * Error manager
     */
    private ErrorManager errorManager;

    private WalletPublisherModuleManager walletPublisherManager;
    /**
     *  Projects opened
     */


    /**
     * Event manager.
     */
    // Ver si esto va acá
    //private EventManager eventManager;



    public WalletPublisherSubAppSession(InstalledSubApp subApp, ErrorManager errorManager,WalletPublisherModuleManager walletPublisherManager){
        super(subApp.getAppPublicKey(),subApp,errorManager,walletPublisherManager,null);
        this.subApps=subApps;
        data= new HashMap<String,Object>();
        this.errorManager=errorManager;
        this.walletPublisherManager=walletPublisherManager;
    }

    @Override
    public InstalledSubApp getSubAppSessionType() {
        return getFermatApp();
    }

    @Override
    public void setData(String key, Object object) {
        data.put(key,object);
    }

    @Override
    public Object getData(String key) {
        return data.get(key);
    }
    @Override
    public ErrorManager getErrorManager() {
        return errorManager;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletPublisherSubAppSession that = (WalletPublisherSubAppSession) o;

        return subApps == that.subApps;

    }

    @Override
    public int hashCode() {
        return subApps.hashCode();
    }

    public WalletPublisherModuleManager getWalletPublisherManager() {
        return walletPublisherManager;
    }
}
