package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.Objects;

/**
 * Created by mati on 2015.11.17..
 */
public class ConnectionWithCommunityDialog extends FermatDialog<ReferenceWalletSession,WalletResourcesProviderManager> implements View.OnClickListener {


    private final Activity activity;
    private FermatButton search_contact_btn;
    private FermatButton cancel_btn;

    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public ConnectionWithCommunityDialog(Activity activity, ReferenceWalletSession fermatSession, WalletResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        search_contact_btn =(FermatButton) findViewById(R.id.search_contact_btn);
        cancel_btn = (FermatButton) findViewById(R.id.cancel_btn);

        search_contact_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.connection_with_community_dialog;
    }

    @Override
    protected int setWindowFeacture() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.search_contact_btn){
            Object[] objects =new Object[2];
            objects[0] = getSession().getLastContactSelected();
            ((FermatScreenSwapper) activity).connectWithOtherApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY,objects);
        }
        else if(id == R.id.cancel_btn){
            dismiss();
        }
    }
}
