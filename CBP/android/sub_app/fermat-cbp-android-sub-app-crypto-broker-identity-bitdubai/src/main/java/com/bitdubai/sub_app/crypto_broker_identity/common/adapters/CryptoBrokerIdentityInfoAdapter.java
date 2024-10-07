package com.bitdubai.sub_app.crypto_broker_identity.common.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.TextUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.common.holders.CryptoBrokerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_broker_identity.util.CryptoBrokerIdentityListFilter;

import java.util.ArrayList;

/**
 * Adapter para el RecyclerView del CryptoBrokerIdentityListFragment que muestra la lista de identidades de un broker
 *
 * @author Nelson Ramirez
 */
public class CryptoBrokerIdentityInfoAdapter
        extends FermatAdapter<CryptoBrokerIdentityInformation, CryptoBrokerIdentityInfoViewHolder>
        implements Filterable {

    CryptoBrokerIdentityListFilter filter;

    public CryptoBrokerIdentityInfoAdapter(Context context, ArrayList<CryptoBrokerIdentityInformation> dataSet) {
        super(context, dataSet);
    }

    public CryptoBrokerIdentityInfoAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindHolder(final CryptoBrokerIdentityInfoViewHolder holder, final CryptoBrokerIdentityInformation data, final int position) {
        filter = (CryptoBrokerIdentityListFilter) getFilter();

        SpannableString spannedText = TextUtils.getSpannedText(
                context.getResources(),
                R.color.spanned_text,
                data.getAlias(),
                filter.getConstraint());

        holder.setText(spannedText);
        holder.setImage(data.getProfileImage());
    }

    @Override
    protected CryptoBrokerIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new CryptoBrokerIdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.crypto_broker_identity_list_item;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new CryptoBrokerIdentityListFilter(dataSet, this);

        return filter;
    }
}
