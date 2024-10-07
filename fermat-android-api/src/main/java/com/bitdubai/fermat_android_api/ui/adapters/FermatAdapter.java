package com.bitdubai.fermat_android_api.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;

import java.util.ArrayList;
import java.util.List;

/**
 * Fermat Adapter
 * Use with RecyclerView Widgets
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public abstract class FermatAdapter<M, H extends FermatViewHolder> extends RecyclerView.Adapter<H> {

    protected List<M> dataSet;
    protected Context context;
    protected FermatListItemListeners<M> eventListeners;

    protected FermatAdapter(Context context) {
        this.context = context;
    }

    protected FermatAdapter(Context context, List<M> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public H onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(), viewGroup, false), type);
    }

    @Override
    public void onBindViewHolder(H holder, final int position) {
        try
        {
            // setting up custom listeners
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventListeners != null) {
                        eventListeners.onItemClickListener(getItem(position), position);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (eventListeners != null) {
                        eventListeners.onLongItemClickListener(getItem(position), position);
                        return true;
                    }
                    return false;
                }
            });
            bindHolder(holder, getItem(position), position);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    /**
     * Get item
     *
     * @param position int position to get
     * @return Model object
     */
    public M getItem(final int position) {
        return dataSet != null ? (!dataSet.isEmpty() && position < dataSet.size()) ? dataSet.get(position) : null : null;
    }

    /**
     * Change whole dataSet and notify the adapter
     *
     * @param dataSet new ArrayList of model to change
     */
    public void changeDataSet(List<M> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    /**
     * Add an item to current dataSet
     *
     * @param item Item to insert into the dataSet
     */
    public void addItem(M item) {
        if (dataSet == null)
            return;
        int position = dataSet.size();
        dataSet.add(item);
        notifyItemInserted(position);
    }

    public void setFermatListEventListener(FermatListItemListeners<M> onEventListeners) {
        this.eventListeners = onEventListeners;
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    protected abstract H createHolder(View itemView, int type);

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    protected abstract int getCardViewResource();

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    protected abstract void bindHolder(H holder, M data, int position);


}
