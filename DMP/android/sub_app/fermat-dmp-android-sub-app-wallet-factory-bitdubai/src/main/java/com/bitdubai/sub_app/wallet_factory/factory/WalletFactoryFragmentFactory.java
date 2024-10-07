package com.bitdubai.sub_app.wallet_factory.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_factory.settings.WalletFactoryPreferenceSettings;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.AvailableProjectsFragment;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.AvailableProjectsFragmentOld;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.MainFragment;

/**
 * WalletFactoryFragmentFactory
 *
 * @author Matias Furszyfer on 2015.19.22..
 * @author Francisco Vasquez
 */

public class WalletFactoryFragmentFactory extends FermatSubAppFragmentFactory<WalletFactorySubAppSession, WalletFactoryPreferenceSettings, WalletFactoryFragmentsEnumType> {


    public WalletFactoryFragmentFactory() {
    }

    @Override
    public FermatFragment getFermatFragment(WalletFactoryFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;
        switch (fragments) {
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_FACTORY_DEVELOPER_PROJECTS:
                currentFragment = MainFragment.newInstance();
                break;
            case CWP_WALLET_FACTORY_AVAILABLE_PROJECTS:
                currentFragment = AvailableProjectsFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletFactoryFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletFactoryFragmentsEnumType.getValue(key);
    }
}
