package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.adapters.AssetFactoryAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.interfaces.PopupMenu;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends FermatFragment implements
        FermatWorkerCallBack, SwipeRefreshLayout.OnRefreshListener, android.widget.PopupMenu.OnMenuItemClickListener {

    /**
     * asset to edit
     */
    private static AssetFactory selectedAsset;
    private final String TAG = "DapMain";
    private ArrayList<AssetFactory> dataSet;
    private AssetFactoryModuleManager manager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AssetFactoryAdapter adapter;
    // custom inflater
    private ViewInflater viewInflater;


    private boolean isRefreshing;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public static AssetFactory getAssetForEdit() {
        return selectedAsset;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            selectedAsset = null;
            manager = ((AssetFactorySession) subAppsSession).getManager();
            //viewInflater = new ViewInflater(getActivity(), subAppResourcesProviderManager);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_assets_draf_fragment, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        selectedAsset = null;
    }

    protected void initViews(View layout) {
        Log.i(TAG, "recycler view setup");
        if (layout == null)
            return;
        recyclerView = (RecyclerView) layout.findViewById(R.id.recycler_assets);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new AssetFactoryAdapter(getActivity());
            adapter.setMenuItemClick(new PopupMenu() {
                @Override
                public void onMenuItemClickListener(View menuView, AssetFactory project, int position) {
                    selectedAsset = project;
                    /*Showing up popup menu*/
                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), menuView);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.asset_factory_main, popupMenu.getMenu());
                    try {
                        if (!manager.isReadyToPublish(selectedAsset.getPublicKey())) {
                            popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                        }
                    } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
                        cantPublishAssetFactoy.printStackTrace();
                        popupMenu.getMenu().findItem(R.id.action_publish).setVisible(false);
                    }
                    popupMenu.setOnMenuItemClickListener(MainFragment.this);
                    popupMenu.show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_assets);
        if (swipeRefreshLayout != null) {
            isRefreshing = false;
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        // fab action button create
        ActionButton create = (ActionButton) layout.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* create new asset factory project */
                selectedAsset = null;
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), getAssetForEdit());
            }
        });
        create.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fab_jump_from_down));
        create.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                dataSet = (ArrayList<AssetFactory>) result[0];
                adapter.changeDataSet(dataSet);
                if (dataSet == null || dataSet.isEmpty()) {
                    /* create new asset */
                    //changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), getAssetForEdit());
                }
            } else if (result != null) {
                dataSet = new ArrayList<>();
                adapter.changeDataSet(dataSet);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public List<AssetFactory> getMoreDataAsync() throws CantGetAssetFactoryException, CantCreateFileException {
        return manager.getAssetFactoryAll();
    }

    @Override
    public void onRefresh() {
        if (!isRefreshing) {
            isRefreshing = true;
            FermatWorker worker = new FermatWorker(getActivity(), this) {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreDataAsync();
                }
            };
            worker.execute();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_edit) {
            if (getAssetForEdit() != null && getAssetForEdit().getState() == State.DRAFT)
                changeActivity(Activities.DAP_ASSET_EDITOR_ACTIVITY.getCode(), getAssetForEdit());
            else
                selectedAsset = null;
        } else if (menuItem.getItemId() == R.id.action_publish) {
            try {
                if (manager.isReadyToPublish(selectedAsset.getPublicKey())) {
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setTitle("Asset Factory");
                    dialog.setMessage("Publishing asset, please wait...");
                    dialog.setCancelable(false);
                    dialog.show();
                    FermatWorker worker = new FermatWorker() {
                        @Override
                        protected Object doInBackground() throws Exception {
                            // for test
                            for (InstalledWallet wallet : manager.getInstallWallets()) {
                                selectedAsset.setWalletPublicKey(wallet.getWalletPublicKey());
                                break;
                            }
                            manager.publishAsset(getAssetForEdit(), BlockchainNetworkType.TEST);
                            selectedAsset = null;
                            return true;
                        }
                    };
                    worker.setContext(getActivity());
                    worker.setCallBack(new FermatWorkerCallBack() {
                        @Override
                        public void onPostExecute(Object... result) {
                            dialog.dismiss();
                            selectedAsset = null;
                            if (getActivity() != null) {
                                onRefresh();
                            }
                            Toast.makeText(getActivity(), "The asset was successfully published.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onErrorOccurred(Exception ex) {
                            dialog.dismiss();
                            selectedAsset = null;
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "Ups, some error occurred publishing this asset", Toast.LENGTH_SHORT).show();
                                onRefresh();
                            }
                            ex.printStackTrace();
                        }
                    });
                    worker.execute();
                }
            } catch (CantPublishAssetFactoy cantPublishAssetFactoy) {
                cantPublishAssetFactoy.printStackTrace();
                Toast.makeText(getActivity(), "You cannot publish this asset", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
