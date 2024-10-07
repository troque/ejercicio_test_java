package com.bitdubai.sub_app.developer.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.exception.CantGetDataBaseToolException;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.DatabaseTool;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.developer.R;
import com.bitdubai.sub_app.developer.common.Databases;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.common.StringUtils;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.DatabaseToolsDatabaseListFragment</code>
 * haves all methods for the database tools activity of a developer
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 */
public class DatabaseToolsDatabaseTableRecordListFragment extends FermatFragment {

    View rootView;
    private ErrorManager errorManager;
    private DatabaseTool databaseTools;

    private DeveloperDatabase developerDatabase;
    private DeveloperDatabaseTable developerDatabaseTable;

    List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList;


    private Resource resource;
    LinearLayout base;

    /**
     * SubApp session
     */
    DeveloperSubAppSession developerSubAppSession;

    public static DatabaseToolsDatabaseTableRecordListFragment newInstance() {
        return new DatabaseToolsDatabaseTableRecordListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(super.subAppsSession!=null){
            developerSubAppSession = (DeveloperSubAppSession) super.subAppsSession;

            resource = (Resource)developerSubAppSession.getData("resource");
            developerDatabaseTable = (DeveloperDatabaseTable)developerSubAppSession.getData("databaseTable");
            developerDatabase = (DeveloperDatabase)developerSubAppSession.getData("developerDataBase");
        }

        errorManager = developerSubAppSession.getErrorManager();
        try {
            ToolManager toolManager = developerSubAppSession.getToolManager();
            databaseTools = toolManager.getDatabaseTool();
        } catch (CantGetDataBaseToolException e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.database_table_record, container, false);
        List<String> columnNames = null;
        List<List<String>> values=null;

        try {
            if (resource.type== Databases.TYPE_ADDON) {
                AddonVersionReference addon = AddonVersionReference.getByKey(resource.code);
                this.developerDatabaseTableRecordList = databaseTools.getAddonTableContent(addon, developerDatabase, developerDatabaseTable);
            } else if (resource.type== Databases.TYPE_PLUGIN) {
                PluginVersionReference plugin = PluginVersionReference.getByKey(resource.code);
                this.developerDatabaseTableRecordList = databaseTools.getPluginTableContent(plugin, developerDatabase, developerDatabaseTable);
            }

            columnNames = developerDatabaseTable.getFieldNames();
            int i=0;


            if (developerDatabaseTableRecordList.size() > 0) {
                i=0;
                values =new ArrayList<List<String>>();
                for(DeveloperDatabaseTableRecord developerDatabaseTableRecord:developerDatabaseTableRecordList){
                    values.add(developerDatabaseTableRecord.getValues());
                    i++;
                }
            } else {
                //listString = new String[0];

            }

        base=(LinearLayout)rootView.findViewById(R.id.base);


        ScrollView scrollView = new ScrollView(getActivity());
        if(developerDatabaseTableRecordList!=null){
            TableLayout tableLayout = createTable(values, columnNames, developerDatabaseTableRecordList.size(), columnNames.size());
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());

            horizontalScrollView.addView(tableLayout);
            scrollView.addView(horizontalScrollView);
            base.addView(scrollView);
        }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
        return rootView;
    }

    private TableLayout createTable(List<List<String>> lstRows, List<String> lstColumns,int rowCount, int columnCount){
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        TableLayout tableLayout= new TableLayout(getActivity());
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        tableLayout.setBackgroundColor(Color.parseColor("#b46a54"));
        tableLayout.setPadding(3,3,3,3);
        try {
            TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
            tableRowParams.setMargins(3, 3, 3, 3);
            tableRowParams.weight =0.14f;

            TableRow tableRow = new TableRow(getActivity());
            if(lstColumns!=null) {

                for (int i = 0; i < lstColumns.size(); i++) {
                    TextView textView = new TextView(getActivity());
                    textView.setBackgroundColor(Color.WHITE);
                    String tableHeader=StringUtils.splitCamelCase(lstColumns.get(i));
                    tableHeader=StringUtils.replaceStringByUnderScore(tableHeader);
                    textView.setText(tableHeader);
                    textView.setTextColor(Color.BLACK);
                    textView.setPadding(10, 10, 10, 10);
                    textView.setTypeface(tf);
                    // 2) create tableRow params

                    tableRow.addView(textView, tableRowParams);
                }
            }
            tableLayout.addView(tableRow);
            if(lstRows!=null) {
                //tableRow = new TableRow(getActivity());
                for (List<String> lstValues:lstRows){
                    tableRow = new TableRow(getActivity());
                    for (String values:lstValues){
                        TextView textView = new TextView(getActivity());
                        textView.setBackgroundColor(Color.WHITE);
                        textView.setText(values);
                        textView.setTextColor(Color.BLACK);
                        textView.setPadding(10, 10, 10, 10);
                        textView.setTypeface(tf);
                        tableRow.addView(textView, tableRowParams);
                    }
                    tableLayout.addView(tableRow,tableLayoutParams);
                }

            }

        }catch (Exception e){
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
        return tableLayout;
    }




    public void setDeveloperDatabase(DeveloperDatabase developerDatabase) {
        this.developerDatabase = developerDatabase;
    }

    public void setDeveloperDatabaseTable(DeveloperDatabaseTable developerDatabaseTable) {
        this.developerDatabaseTable = developerDatabaseTable;
    }
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setDeveloperSubAppSession(DeveloperSubAppSession developerSubAppSession) {
        this.developerSubAppSession = developerSubAppSession;
    }
}