package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by nelson on 21/10/15.
 */
public class ContractExpandableListViewHolder extends ChildViewHolder {
    public ImageView customerImage;
    public FermatTextView customerName;
    public FermatTextView contractAction;
    public FermatTextView typeOfPayment;
    public FermatTextView lastUpdateDate;
    public FermatTextView status;
    private Resources res;
    private View itemView;


    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public ContractExpandableListViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();

        customerImage = (ImageView) itemView.findViewById(R.id.cbw_customer_image);
        customerName = (FermatTextView) itemView.findViewById(R.id.cbw_customer_name);
        contractAction = (FermatTextView) itemView.findViewById(R.id.cbw_receiving_or_sending);
        typeOfPayment = (FermatTextView) itemView.findViewById(R.id.cbw_type_of_payment);
        lastUpdateDate = (FermatTextView) itemView.findViewById(R.id.cbw_update_date);
        status = (FermatTextView) itemView.findViewById(R.id.cbw_contract_status);
    }

    public void bind(ContractBasicInformation itemInfo) {

        ContractStatus contractStatus = itemInfo.getStatus();
        itemView.setBackgroundColor(getStatusBackgroundColor(contractStatus));
        status.setText(getStatusStringRes(contractStatus));
        contractAction.setText(getContractActionDescription(itemInfo, contractStatus));
        customerName.setText(itemInfo.getCryptoCustomerAlias());
        typeOfPayment.setText(itemInfo.getTypeOfPayment());
        customerImage.setImageDrawable(getImgDrawable(itemInfo.getCryptoCustomerImage()));

        CharSequence date = DateFormat.format("dd MMM yyyy", itemInfo.getLastUpdate());
        lastUpdateDate.setText(date);
    }

    @NonNull
    private String getContractActionDescription(ContractBasicInformation itemInfo, ContractStatus contractStatus) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();
        return getReceivingOrSendingText(contractStatus) + " " + decimalFormat.format(itemInfo.getAmount()) + " " + itemInfo.getMerchandise();
    }

    private int getStatusBackgroundColor(ContractStatus status) {
        if (status == ContractStatus.PENDING_PAYMENT)
            return res.getColor(R.color.waiting_for_customer_list_item_background);

        if (status == ContractStatus.PAUSED)
            return res.getColor(R.color.waiting_for_broker_list_item_background);

        if (status == ContractStatus.COMPLETED)
            return res.getColor(R.color.contract_completed_list_item_background);

        return res.getColor(R.color.contract_cancelled_list_item_background);
    }

    private String getReceivingOrSendingText(ContractStatus status) {
        if (status == ContractStatus.PENDING_PAYMENT)
            return res.getString(R.string.receiving);
        return res.getString(R.string.sending);
    }

    private int getStatusStringRes(ContractStatus status) {
        if (status == ContractStatus.CANCELLED)
            return R.string.contract_cancelled;

        if (status == ContractStatus.PENDING_PAYMENT)
            return R.string.waiting_for_the_customer;

        return R.string.waiting_for_you;
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.person);
    }
}
