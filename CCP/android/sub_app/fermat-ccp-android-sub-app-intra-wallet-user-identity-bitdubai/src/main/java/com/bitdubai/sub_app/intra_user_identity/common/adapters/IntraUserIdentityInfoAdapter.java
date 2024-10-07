package com.bitdubai.sub_app.intra_user_identity.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.common.holders.IntraUserIdentityInfoViewHolder;
import com.bitdubai.sub_app.intra_user_identity.util.UtilsFuncs;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class IntraUserIdentityInfoAdapter extends FermatAdapter<IntraWalletUserIdentity, IntraUserIdentityInfoViewHolder> {


    public IntraUserIdentityInfoAdapter(Context context, ArrayList<IntraWalletUserIdentity> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IntraUserIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new IntraUserIdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_identity_list_item;
    }

    @Override
    protected void bindHolder(final IntraUserIdentityInfoViewHolder holder, final IntraWalletUserIdentity data, final int position) {
        holder.getIdentityName().setText(data.getAlias());

        byte[] profileImage = data.getProfileImage();
        Bitmap imageBitmap = profileImage == null ?
                BitmapFactory.decodeResource(context.getResources(), R.drawable.profile_image) :
                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
        holder.getIdentityImage().setImageBitmap(roundedBitmap);
    }
}
