package com.bitdubai.fermat_android_api.ui.expandableRecicler;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.android_api.R;


/**
 * Custom child ViewHolder. Any views should be found and set to public variables here to be
 * referenced in your custom ExpandableAdapter later.
 *
 * Must extend ChildViewHolder.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class VerticalChildViewHolder extends ChildViewHolder {

    public TextView mDataTextView;

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public VerticalChildViewHolder(View itemView) {
        super(itemView);

        //mDataTextView = (TextView) itemView.findViewById(R.id.list_item_vertical_child_textView);
    }

    public void bind(String childText) {
        mDataTextView.setText(childText);
    }
}