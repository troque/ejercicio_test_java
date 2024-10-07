package com.bitdubai.android_core.app.common.version_1.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import java.util.List;

/**
     * Tabs adapter
     */
    public class TabsPagerAdapter extends FragmentStatePagerAdapter {


    private String onlyFragment;
    private String[] titles;


        private Context context;

        private Activity activity;

        private WalletFragmentFactory walletFragmentFactory;

        private TabStrip tabStrip;


        private WalletSession walletSession;

        private ErrorManager errorManager;

        private WalletSettings walletSettings;

        private WalletResourcesProviderManager walletResourcesProviderManager;

        private SubAppFragmentFactory subAppFragmentFactory;

        private SubAppSettings subAppSettings;

        private SubAppResourcesProviderManager subAppResourcesProviderManager;

        private SubAppsSession subAppsSession;

        private Resources resources;


    public TabsPagerAdapter(FragmentManager fm,Context context,Activity activity,SubAppsSession subAppSession,ErrorManager errorManager,SubAppFragmentFactory subAppFragmentFactory,SubAppSettings subAppSettings,SubAppResourcesProviderManager subAppResourcesProviderManager) {
            super(fm);
            this.context=context;


            this.subAppsSession = subAppSession;

            this.errorManager=errorManager;
            this.activity=activity;
            tabStrip=activity.getTabStrip();
            this.subAppFragmentFactory=subAppFragmentFactory;
            this.subAppSettings = subAppSettings;
            this.subAppResourcesProviderManager = subAppResourcesProviderManager;


            if(activity.getTabStrip() != null){
                List<Tab> titleTabs = activity.getTabStrip().getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }

    public TabsPagerAdapter(FragmentManager fragmentManager, Context applicationContext, SubAppFragmentFactory subAppFragmentFactory, String fragment, SubAppsSession subAppsSession, SubAppResourcesProviderManager subAppResourcesProviderManager, Resources resources) {
        super(fragmentManager);
        this.context=applicationContext;

        this.subAppFragmentFactory =subAppFragmentFactory;
        this.subAppsSession = subAppsSession;
        this.onlyFragment = fragment;

        this.subAppFragmentFactory=subAppFragmentFactory;
        this.subAppSettings = subAppSettings;
        this.subAppResourcesProviderManager = subAppResourcesProviderManager;


    }

        public TabsPagerAdapter(FragmentManager fm,Context context,WalletFragmentFactory walletFragmentFactory,TabStrip tabStrip,WalletSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager,Resources resources) {
            super(fm);
            this.context=context;

            this.walletSession=walletSession;
            this.walletFragmentFactory = walletFragmentFactory;
            this.tabStrip=tabStrip;
            this.walletResourcesProviderManager =walletResourcesProviderManager;
            this.resources = resources;

            if(tabStrip != null){
                List<Tab> titleTabs = tabStrip.getTabs();
                titles = new String[titleTabs.size()];
                for (int i = 0; i < titleTabs.size(); i++) {
                    Tab tab = titleTabs.get(i);
                    titles[i] = tab.getLabel();
                }
            }

        }

    public TabsPagerAdapter(FragmentManager fm,Context context,WalletFragmentFactory walletFragmentFactory,String fragment ,WalletSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager,Resources resources) {
        super(fm);
        this.context=context;

        this.walletSession=walletSession;
        this.errorManager=errorManager;
        this.walletFragmentFactory = walletFragmentFactory;
        this.tabStrip=null;
        this.onlyFragment = fragment;
        this.walletResourcesProviderManager =walletResourcesProviderManager;
        this.resources = resources;


    }



    public void destroyItem(android.view.ViewGroup container, int position, Object object) {

            FragmentManager manager = ((Fragment) object).getFragmentManager();
            if(manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.detach((Fragment) object);
                trans.remove((Fragment) object);
                trans.commit();


            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            if(titles.length>0) {
                title = titles[position];
            }
            return title;

        }




        @Override
        public int getCount() {

            if (titles != null)
                return titles.length;
            else if (onlyFragment!=null){
                return 1;
            } else
                return 0;
        }

        @Override
        public Fragment getItem(int position) {

            String fragmentCodeType = null;


            Fragment currentFragment = null;
            Fragments fragmentType = Fragments.CWP_SHELL_LOGIN;
            if(tabStrip!=null) {
                List<Tab> titleTabs = tabStrip.getTabs();
                for (int j = 0; j < titleTabs.size(); j++) {
                    if (j == position) {
                        Tab tab = titleTabs.get(j);
                        fragmentCodeType = tab.getFragment().getKey();
                        break;
                    }
                }



            }else{
                fragmentCodeType = onlyFragment;
            }






            try {
                if(walletFragmentFactory !=null){
                    currentFragment= walletFragmentFactory.getFragment(fragmentCodeType, walletSession,walletSettings,walletResourcesProviderManager);
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }


            try {
                if(subAppFragmentFactory !=null){
                    currentFragment= subAppFragmentFactory.getFragment(fragmentCodeType,subAppsSession,subAppSettings,subAppResourcesProviderManager);
                }
            } catch (FragmentNotFoundException e) {
                e.printStackTrace();
            }


            return currentFragment;
        }


    @Override
    public Parcelable saveState() {
        return null;
    }

}