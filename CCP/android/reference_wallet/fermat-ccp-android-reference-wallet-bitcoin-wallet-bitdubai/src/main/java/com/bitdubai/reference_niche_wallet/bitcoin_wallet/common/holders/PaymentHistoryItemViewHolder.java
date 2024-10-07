package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import org.w3c.dom.Text;


/**
 * Created by Matias Furszyfer 22/09/2015
 */


public class PaymentHistoryItemViewHolder extends FermatViewHolder {
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;

    private LinearLayout linear_layour_container_state;
    private TextView txt_state;

    private LinearLayout linear_layour_container_buttons;
    private Button btn_refuse_request;
    private Button btn_accept_request;



    public PaymentHistoryItemViewHolder(View itemView) {
        super(itemView);

        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);
        txt_state = (TextView) itemView.findViewById(R.id.txt_state);
        btn_refuse_request = (Button) itemView.findViewById(R.id.btn_refuse_request);
        btn_accept_request = (Button) itemView.findViewById(R.id.btn_accept_request);
        linear_layour_container_state = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_state);
        linear_layour_container_buttons = (LinearLayout) itemView.findViewById(R.id.linear_layour_container_buttons);

    }

    public ImageView getContactIcon() {
        return contactIcon;
    }

    public TextView getTxt_contactName() {
        return txt_contactName;
    }

    public TextView getTxt_amount() {
        return txt_amount;
    }

    public TextView getTxt_notes() {
        return txt_notes;
    }


    public TextView getTxt_time() {
        return txt_time;
    }

    public TextView getTxt_state() {
        return txt_state;
    }

    public Button getBtn_refuse_request() {
        return btn_refuse_request;
    }

    public Button getBtn_accept_request() {
        return btn_accept_request;
    }

    public LinearLayout getLinear_layour_container_state() {
        return linear_layour_container_state;
    }

    public LinearLayout getLinear_layour_container_buttons() {
        return linear_layour_container_buttons;
    }
}
