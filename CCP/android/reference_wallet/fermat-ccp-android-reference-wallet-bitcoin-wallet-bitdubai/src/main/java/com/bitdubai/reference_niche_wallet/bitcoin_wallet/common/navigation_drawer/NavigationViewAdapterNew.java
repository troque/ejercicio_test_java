package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;

import java.util.ArrayList;

/**
 * Created by hp1 on 28-12-2014.
 */
public class NavigationViewAdapterNew extends RecyclerView.Adapter<NavigationViewAdapterNew.ViewHolder>{

    private static final int TYPE_ITEM = 1;


    private Context mContext;

    //TODO: hay que hacer un generico bueno de esto
    //items
    private ArrayList<MenuItem> lstItems;


    //identity
    private IntraUserLoginIdentity intraUserLoginIdentity;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them
 
    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;      
         
        TextView textView; 
        ImageView imageView;
        ImageView profile;
        TextView txt_name;
        
 
        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
             
            
            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
             
            if(ViewType == TYPE_ITEM) {
                textView = (FermatTextView) itemView.findViewById(R.id.textView_label); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.imageView_icon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{
 
 
                txt_name = (FermatTextView) itemView.findViewById(R.id.txt_name);         // Creating Text View object from header.xml for name
                profile = (ImageView) itemView.findViewById(R.id.image_view_profile);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }
 
         
    }
 
 
 
    public NavigationViewAdapterNew(Context context) {
        this.mContext = context;
        this.lstItems = new ArrayList<>();
    }
 
 
 
    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder
 
    @Override
    public NavigationViewAdapterNew.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_row,parent,false); //Inflating the layout
 
            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view
 
            return vhItem; // Returning the created object

 
    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(NavigationViewAdapterNew.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
                                                              // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(lstItems.get(position - 1).getLabel()); // Setting the Text with the array of our Titles

            switch (position) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.btn_drawer_home_normal);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.btn_drawer_profile_normal);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.btn_drawer_request_normal);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.btn_drawer_settings_normal);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.btn_drawer_logout_normal);
                    break;
                default:
                    break;
            }
        }
        else{
            if(intraUserLoginIdentity!=null) {
                Bitmap bitmapDrawable = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length);
                bitmapDrawable = Bitmap.createScaledBitmap(bitmapDrawable, holder.profile.getWidth(), holder.profile.getHeight(), true);
                holder.profile.setImageBitmap(bitmapDrawable);
                holder.txt_name.setText(intraUserLoginIdentity.getAlias());
            }
        }
    }
 
    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return lstItems.size()+1; // the number of items in the list will be +1 the titles including the header view.
    }
 
     
    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

 
}