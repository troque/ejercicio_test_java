// @author Bhavya Mehta
package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.views_contacts_fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.util.MemoryUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views.FermatListViewFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.HeaderTypes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// Customized adaptor to populate data in PinnedHeaderListView
public class PinnedHeaderAdapter extends BaseAdapter implements OnScrollListener, IPinnedHeader, Filterable {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SECTION = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SECTION + 1;
    private static final int TOTAL_CONTACTS_SECTION_POSITION = 0;
    private final FermatListViewFragment contactsFragment;

    private String constrainStr = null;

    LayoutInflater mLayoutInflater;
    int mCurrentSectionPosition = 0, mNextSectionPosition = 0;

    // array list to store section positions
    ArrayList<Integer> mListSectionPos;

    // array list to store list view data
    ArrayList<Object> mListItems;

    // context object
    Context mContext;
    //Type face font
    Typeface tf;


    // posiscionamiento de los contactos
    private Map<Integer,CryptoWalletWalletContact> contactPositionItem;

    /**
     * @param context          Context
     * @param listItems        List of Items
     * @param listSectionPos   List of section positions
     * @param constrainStr     The string that is being searching
     * @param contactsFragment ContactsFragment who is calling
     */
    public PinnedHeaderAdapter(Context context, ArrayList<Object> listItems, ArrayList<Integer> listSectionPos,
                               String constrainStr, FermatListViewFragment contactsFragment) {
        this.mContext = context;
        this.mListItems = listItems;
        this.mListSectionPos = listSectionPos;
        this.contactsFragment = contactsFragment;

        if (constrainStr != null) {
            if (!constrainStr.isEmpty()) {
                this.constrainStr = constrainStr;
            }
        }

        tf = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        contactPositionItem = new HashMap<>();


        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }


    @Override
    public boolean isEnabled(int position) {
        return !mListSectionPos.contains(position);
    }


    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }


    @Override
    public Object getItem(int position) {
        return mListItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return mListItems.get(position).hashCode();
    }


    @Override
    public int getItemViewType(int position) {
        return mListSectionPos.contains(position) ? TYPE_SECTION : TYPE_ITEM;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CryptoWalletWalletContact walletContact = null;
        String text = "";

        mListSectionPos = mListSectionPos;

        int type = getItemViewType(position);

        try {
            if (convertView == null) {
                holder = new ViewHolder();



                switch (type) {
                    case TYPE_ITEM:
                        convertView = mLayoutInflater.inflate(R.layout.row_view, null);
                        holder.imageView =(ImageView) convertView.findViewById(R.id.imageView_contact);
                        walletContact = (CryptoWalletWalletContact) mListItems.get(position);
                        //guardo el contacto
                        contactPositionItem.put(position, walletContact);
                        try {
                            if (walletContact.getProfilePicture() != null) {
                                if (walletContact.getProfilePicture().length > 0) {
                                    holder.imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(mContext.getResources(), walletContact.getProfilePicture()));
                                } else {
                                    holder.imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(mContext.getResources(), R.drawable.profile_image));
                                }
                            } else
                                holder.imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(mContext.getResources(), R.drawable.profile_image));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        text = walletContact.getActorName();
                        //contact image
//                        ImageView contact_image = (ImageView)convertView.findViewById(R.id.imageView_contact);
//                        contact_image.setImageResource(R.drawable.caroline_profile_picture);
                        break;
                    case TYPE_SECTION:
                        convertView = mLayoutInflater.inflate(R.layout.section_row_view, null);
                        text = (String) mListItems.get(position);
                        break;
                }

                holder.textView = (TextView) convertView.findViewById(R.id.row_title);
                holder.textView.setTypeface(tf);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (text.equals("")) {
                Object o = mListItems.get(0);
                if (o instanceof CryptoWalletWalletContact) {
                    text = ((CryptoWalletWalletContact) o).getActorName();
                } else {
                    o = mListItems.get(1);
                    if (o instanceof CryptoWalletWalletContact) {
                        text = ((CryptoWalletWalletContact) o).getActorName();
                    }

                }
            }



//        final String text = mListItems.get(position);
            final SpannableString textToShow = getSpannedText(text, constrainStr, type);
            holder.textView.setText(text);


        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }


    @Override
    public int getPinnedHeaderState(int position) {
        // hide pinned header when items count is zero OR position is less than zero OR there is already a header in list view
//        if (getCount() == 0 || position < 0 || mListSectionPos.indexOf(position) != -1) {
//            return PINNED_HEADER_GONE;
//        }

        // the header should get pushed up if the top item shown is the last item in a section for a particular letter.
        mCurrentSectionPosition = getCurrentSectionPosition(position);
        mNextSectionPosition = getNextSectionPosition(mCurrentSectionPosition);
        if (mNextSectionPosition != -1 && position == mNextSectionPosition - 1) {
            return PINNED_HEADER_PUSHED_UP;
        }

        return PINNED_HEADER_VISIBLE;
    }


    //TODO: ver si lo que cambie aca del toString está bien, estoy casao
    @Override
    public void configurePinnedHeader(View v, int position) {
        try
        {
            // set text in pinned header
            LinearLayout linearLayout =(LinearLayout)v;
            TextView header = (TextView) linearLayout.getChildAt(0);
            mCurrentSectionPosition = getCurrentSectionPosition(position);
            header.setText(mListItems.get(mCurrentSectionPosition).toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
    }

    @Override
    public Filter getFilter() {
        return contactsFragment.instanceOfListFilter();
    }

    /**
     * Return a spanned string based on a substring (the substring is spanned)
     *
     * @param text      the string with all the text
     * @param substring te substring that is going to be spanned
     * @return the spanned string
     */
    private SpannableString getSpannedText(String text, String substring, int type) {
        final SpannableString textToShow = new SpannableString(text);

        if (substring != null && type == TYPE_ITEM) {
            final String substringLowerCase = substring.toLowerCase(Locale.getDefault());
            final String textLowerCase = text.toLowerCase(Locale.getDefault());

            final int start = textLowerCase.indexOf(substringLowerCase);
            final int end = start + substringLowerCase.length();

            final int color = mContext.getResources().getColor(R.color.green);
            final ForegroundColorSpan span = new ForegroundColorSpan(color);

            textToShow.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return textToShow;
    }


    public int getCurrentSectionPosition(int position) {
        final boolean searchMode = constrainStr != null;


            if (searchMode) {
                return TOTAL_CONTACTS_SECTION_POSITION;
            }

            String listChar ="";
            if (mListItems.get(position).getClass().equals(String.class))
                listChar = ((String)mListItems.get(position)).substring(0, 1).toUpperCase(Locale.getDefault());

            if (listChar.matches(HeaderTypes.LETTER.getRegex())) {
                return mListItems.indexOf(listChar);
            }

            if (listChar.matches(HeaderTypes.NUMBER.getRegex())) {
                return mListItems.indexOf(HeaderTypes.NUMBER.getCode());
            }

        return mListItems.indexOf(HeaderTypes.SYMBOL.getCode());

    }


    public int getNextSectionPosition(int currentSectionPosition) {
        int index = mListSectionPos.indexOf(currentSectionPosition);
        if ((index + 1) < mListSectionPos.size()) {
            return mListSectionPos.get(index + 1);
        }
        return mListSectionPos.get(index);
    }


    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
