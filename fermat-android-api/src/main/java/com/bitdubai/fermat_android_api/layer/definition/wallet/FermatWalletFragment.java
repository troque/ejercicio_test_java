package com.bitdubai.fermat_android_api.layer.definition.wallet;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_android_api.engine.PaintActivtyFeactures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.26.21..
 */
public class FermatWalletFragment<M extends ModuleManager> extends Fragment implements FermatFragments {

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /**
     * Platform
     */
    protected WalletSession walletSession;
    protected WalletSettings walletSettings;
    protected WalletResourcesProviderManager walletResourcesProviderManager;


    /**
     * Inflater
     */
    protected ViewInflater viewInflater;

    /**
     * REFERENCES
     */
    protected WizardConfiguration context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = (WizardConfiguration) getActivity();
            viewInflater = new ViewInflater(getActivity(),walletResourcesProviderManager);
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to FermatActivity");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void setWalletSession(WalletSession walletSession) {
        this.walletSession = walletSession;
    }

    public void setWalletSettings(WalletSettings walletSettings) {
        this.walletSettings = walletSettings;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }

    /**
     * Change activity
     */
    protected final void changeActivity(Activities activity,String appPublicKey) {
        getFermatScreenSwapper().changeActivity(activity.getCode(),appPublicKey);
    }

    /**
     * Change activity
     */
    protected final void changeFragment(String fragment,int idContainer) {
        getFermatScreenSwapper().changeScreen(fragment, idContainer, null);
    }

    /**
     *
     */


    protected final RelativeLayout getToolbarHeader() {
        return getPaintActivtyFeactures().getToolbarHeader();
    }

    protected PaintActivtyFeactures getPaintActivtyFeactures(){
        return ((PaintActivtyFeactures)getActivity());
    }

    protected void setNavigationDrawer(FermatAdapter adapter){
        getPaintActivtyFeactures().changeNavigationDrawerAdapter(adapter);
    }

    protected void addNavigationHeader(View view){
        getPaintActivtyFeactures().addNavigationViewHeader(view);
    }

    protected Toolbar getToolbar(){
        return getPaintActivtyFeactures().getToolbar();
    }

    protected void changeApp(Engine emgine,Object[] objects){
        getFermatScreenSwapper().connectWithOtherApp(emgine,objects);
    }
    protected FermatScreenSwapper getFermatScreenSwapper(){
        return (FermatScreenSwapper) getActivity();
    }


}

