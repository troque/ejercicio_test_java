package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.contracts_history;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ContractHistoryAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer.NavigationViewAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra el Historial del Contratos. Muestra una lista de contratos completados, cancelados, o en reclamo
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 18/11/2015
 */
public class ContractsHistoryActivityFragment extends FermatWalletListFragment<ContractBasicInformation>
        implements FermatListItemListeners<ContractBasicInformation> {

    // Constants
    private static final String WALLET_PUBLIC_KEY = "crypto_broker_wallet";
    private static final String TAG = "ContractsHistoryActivityFragment";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private ArrayList<ContractBasicInformation> contractHistoryList;
    private ContractStatus filterContractStatus = null;

    //UI
    private View noContractsView;


    public static ContractsHistoryActivityFragment newInstance() {
        return new ContractsHistoryActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            errorManager = walletSession.getErrorManager();

            contractHistoryList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();
        configureNavigationDrawer();

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        noContractsView = layout.findViewById(R.id.cbw_no_contracts);

        showOrHideNoContractsView(contractHistoryList.isEmpty());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.contract_history_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_no_filter) {
            filterContractStatus = null;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.action_filter_succeed) {
            filterContractStatus = ContractStatus.COMPLETED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.action_filter_cancel) {
            filterContractStatus = ContractStatus.CANCELLED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ContractHistoryAdapter(getActivity(), contractHistoryList);
            adapter.setFermatListEventListener(this);
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_contracts_history_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.contracts_history_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void configureNavigationDrawer() {
        Activity activity = getActivity();
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        try {
            addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), null));
            NavigationViewAdapter adapter = new NavigationViewAdapter(activity, null);
            setNavigationDrawer(adapter);
        } catch (CantGetActiveLoginIdentityException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
        }
    }

    private void showOrHideNoContractsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noContractsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noContractsView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClickListener(ContractBasicInformation data, int position) {
        walletSession.setData("contract_data", data);
        //changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS);
    }

    @Override
    public void onLongItemClickListener(ContractBasicInformation data, int position) {
    }

    @Override
    public List<ContractBasicInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<ContractBasicInformation> data = new ArrayList<>();

        if (moduleManager != null) {
            try {
                CryptoBrokerWallet wallet = moduleManager.getCryptoBrokerWallet(WALLET_PUBLIC_KEY);
                data.addAll(wallet.getContractsHistory(filterContractStatus, 0, 20));

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in ContractHistoryActivityFragment (Module == null)",
                    Toast.LENGTH_SHORT).
                    show();
        }

        return data;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                contractHistoryList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(contractHistoryList);

                showOrHideNoContractsView(contractHistoryList.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }
}
