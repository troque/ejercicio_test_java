package com.bitdubai.sub_app.manager.fragment;

import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.sub_app.manager.R;
import com.bitdubai.sub_app.manager.fragment.provisory_classes.InstalledSubApp;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.app.Fragment;
/**
 * Created by Natalia on 12/01/2015.
 */
public class SubAppDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String CWP_SUB_APP_ALL_DEVELOPER = Activities.CWP_SUB_APP_ALL_DEVELOPER.getCode();
    private static final String CWP_WALLET_BASIC_ALL_MAIN = Activities.CWP_WALLET_BASIC_ALL_MAIN.getCode();
    private static final String CWP_WALLET_FACTORY_MAIN = Activities.CWP_WALLET_FACTORY_MAIN.getCode();
    private static final String CWP_WALLET_PUBLISHER_MAIN = Activities.CWP_WALLET_PUBLISHER_MAIN.getCode();
    private static final String CWP_WALLET_RUNTIME_STORE_MAIN = Activities.CWP_WALLET_RUNTIME_STORE_MAIN.getCode();

    private ArrayList<InstalledSubApp> mlist;
    private static int tabId;

    private int position;
    Typeface tf;

    public static SubAppDesktopFragment newInstance(int position) {
        SubAppDesktopFragment f = new SubAppDesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
    //    f.setArguments(b);
        return f;
    }

   // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  setHasOptionsMenu(true);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        String[] installed =
                {  "true",
                        "true",
                        "true",
                        "true",
                        "true",
                        "true"
                };
        String[] sub_app_names =
                { "Developer",
                   "Wallet Factory",
                   "Wallet Publisher",
                        "Wallet Store",
                        "Intra user",
                        "Intra User Community"
                };


        String[] sub_app_picture =
                {"developer_sub_app",
                        "wallet_factory",
                        "wallet_publisher",
                        "wallet_store",
                        "intra_user",
                        "intra_user_community"
                };

        mlist = new ArrayList<InstalledSubApp>();


        InstalledSubApp installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_FACTORY,null,null,"wallet_factory","Wallet factory","wallet_factory","wallet_factory",new Version(1,0,0));
        mlist.add(installedSubApp);
        installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_PUBLISHER,null,null,"wallet_publisher","Wallet publisher","wallet_publisher","wallet_publisher",new Version(1,0,0));
        mlist.add(installedSubApp);
        installedSubApp = new InstalledSubApp(SubApps.CWP_WALLET_STORE,null,null,"wallet_store","Wallet store","wallet_store","wallet_store",new Version(1,0,0));
        mlist.add(installedSubApp);
        installedSubApp = new InstalledSubApp(SubApps.CWP_DEVELOPER_APP,null,null,"developer_sub_app","Developer","developer_sub_app","developer_sub_app",new Version(1,0,0));
        mlist.add(installedSubApp);
//        installedSubApp = new InstalledSubApp(SubApps.CWP_INTRA_USER_IDENTITY,null,null,"intra_user_identity_sub_app","Intra user Identity","intra_user_identity_sub_app","intra_user_identity_sub_app",new Version(1,0,0));
//        mlist.add(installedSubApp);
//
//        installedSubApp = new InstalledSubApp(SubApps.CCP_INTRA_USER_COMMUNITY,null,null,"intra_user_community_sub_app","Intra user Community","intra_user_community_sub_app","intra_user_community_sub_app",new Version(1,0,0));
//        mlist.add(installedSubApp);


        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        //@SuppressWarnings("unchecked")
        //   ArrayList<App> list = (ArrayList<App>) getArguments().get("list");
        AppListAdapter _adpatrer = new AppListAdapter(getActivity(), R.layout.shell_sub_app_desktop_fragment_grid_item ,mlist);
        _adpatrer.notifyDataSetChanged();
        gridView.setAdapter(_adpatrer);



        return gridView;
    }


  //  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
     //   inflater.inflate(R.menu.shell_shop_desktop_fragment_menu, menu);
      //  super.onCreateOptionsMenu(menu,inflater);
    }


    public class App implements Serializable {

        private static final long serialVersionUID = -8730067026050196758L;

        public String title;

        public String description;

        public String picture;

        public String company;


        public String Address;



        public float rate;

        public int value;

        public float favorite;

        public float sale;

        public float timetoarraive;

        public boolean installed;

    }



    public class AppListAdapter extends ArrayAdapter<InstalledSubApp> {


        public AppListAdapter(Context context, int textViewResourceId, List<InstalledSubApp> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final InstalledSubApp installedSubApp = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
               convertView = inflater.inflate(R.layout.shell_sub_app_desktop_fragment_grid_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(installedSubApp.getSubAppName());
            holder.companyTextView.setTypeface(tf, Typeface.BOLD);

            LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.sub_apps);
            switch (installedSubApp.getSubAppIcon())
            {
                case "developer_sub_app":
                    holder.imageView.setImageResource(R.drawable.developer_image);
                  holder.imageView.setTag("DevelopersActivity|1");
                    linearLayout.setTag("DevelopersActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(installedSubApp);

                        }
                    });
                    break;
                case "wallet_factory":
                    holder.imageView.setImageResource(R.drawable.factory);
                    holder.imageView.setTag("FactoryActivity|1");
                    linearLayout.setTag("FactoryActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(installedSubApp);
                        }
                    });
                    break;

                case "wallet_publisher":
                    holder.imageView.setImageResource(R.drawable.publisher_image);
                    holder.imageView.setTag("PublisherActivity|1");
                    linearLayout.setTag("PublisherActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(installedSubApp);
                        }
                    });
                    break;

                case "wallet_store":
                    holder.imageView.setImageResource(R.drawable.store_image);
                    holder.imageView.setTag("StoreFrontActivity|1");
                    linearLayout.setTag("StoreFrontActivity|1");
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

          //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectSubApp(installedSubApp);
                        }
                    });
                    break;

            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {

            public ImageView imageView;
            public TextView companyTextView;

        }

    }

}

