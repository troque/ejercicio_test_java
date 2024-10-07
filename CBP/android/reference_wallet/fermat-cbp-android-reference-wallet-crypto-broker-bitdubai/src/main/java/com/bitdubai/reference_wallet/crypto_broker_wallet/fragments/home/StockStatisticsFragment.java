package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockStatisticsFragment extends FermatWalletFragment {


    public StockStatisticsFragment() {
        // Required empty public constructor
    }

    public static StockStatisticsFragment newInstance() {
        return new StockStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
