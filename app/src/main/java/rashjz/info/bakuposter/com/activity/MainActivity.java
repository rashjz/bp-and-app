package rashjz.info.bakuposter.com.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.util.Config;
import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.adapter.CustomSpinnerAdapter;
import rashjz.info.bakuposter.com.adapter.DateDisplayPicker;
import rashjz.info.bakuposter.com.adapter.SmartSlidingPane;
import rashjz.info.bakuposter.com.adapter.TabsPagerAdapter;
import rashjz.info.bakuposter.com.fragment.AboutFragment;
import rashjz.info.bakuposter.com.fragment.LanguageFragment;
import rashjz.info.bakuposter.com.model.SpinnerModel;
import rashjz.info.bakuposter.com.util.PreferenceUtil;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener, LanguageFragment.ChangeLanguage {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SmartSlidingPane mSlidingPanel;
    private Spinner spinnerDropDown;
    private ViewPager viewPager;
    private TabsPagerAdapter tabAdapter;
    private CustomSpinnerAdapter spinnerAdapter;
    private ActionBar actionBar;
    private SearchView searchView;
    private EditText titleEdt;
    private DateDisplayPicker dateFrom, dateTo;
    private Button searchBtn;
    private GoogleCloudMessaging gcm;
    private String regId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlidingPanel = (SmartSlidingPane) findViewById(R.id.slidingPanel);
//        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(200);

        // getting  pager for ActionBar .
        viewPager = (ViewPager) findViewById(R.id.pager);
        titleEdt = (EditText) findViewById(R.id.titleEdt);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        dateFrom = (DateDisplayPicker) findViewById(R.id.dateFrom);
        dateTo = (DateDisplayPicker) findViewById(R.id.dateTo);
        // Get reference of SpinnerView from layout/main_activity.xml
        spinnerDropDown = (Spinner) findViewById(R.id.spinnerDropDown);

        List<SpinnerModel> modelList = new ArrayList<>();
        modelList.add(new SpinnerModel("Select one", "", "0", R.drawable.pointer));
        modelList.add(new SpinnerModel("Movie", "", "1", R.drawable.movie));
        modelList.add(new SpinnerModel("Concerts", "", "2", R.drawable.music));
        modelList.add(new SpinnerModel("Theatre", "", "3", R.drawable.theatre));
        spinnerAdapter = new CustomSpinnerAdapter(this, R.layout.spinner_rows, modelList, getResources());
        spinnerDropDown.setAdapter(spinnerAdapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerModel viewTag = (SpinnerModel) spinnerDropDown.getSelectedView().getTag();
//                Message.message(getApplicationContext(), titleEdt.getText().toString() + " " + dateFrom.getText().toString() + " " + dateTo.getText().toString() + " " + viewTag.getId());
                Intent newActivity = new Intent(getBaseContext(), SearchResultsActivity.class);
                Bundle searchExt = new Bundle();
                searchExt.putString("title", titleEdt.getText().toString());
                searchExt.putString("dateFrom", dateFrom.getText().toString());
                searchExt.putString("dateTo", dateTo.getText().toString());
                searchExt.putString("catId", "" + viewTag.getId());
                searchExt.putString("searchType", "advanced");
                newActivity.putExtras(searchExt);
                startActivity(newActivity);
            }
        });

        settingActionBarDetails();
//        //create bar   fragment   list inside
        tabAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        getRegId();

    }


    public void settingActionBarDetails() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.app_name);
        actionBar.setHomeButtonEnabled(true);//back button
//        getSupportActionBar().setIcon(R.drawable.applogo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        String[] tabs = getResources().getStringArray(R.array.tabArrays);
//        String fontPath = "fonts/Times Old Attic.ttf";
//        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        for (String tab_name : tabs) {
            ActionBar.Tab tab = actionBar.newTab();
            TextView customTabView = (TextView) getLayoutInflater().inflate(R.layout.tabstyle, null);
            customTabView.setText(tab_name);
//            Typeface typface2 = Typeface.createFromAsset(getAssets(), "fonts/Times Old Attic.TTF");
//            customTabView.setTypeface(typface2);
            tab.setTabListener(this);
            tab.setCustomView(customTabView);
            actionBar.addTab(tab);
        }
//actionBar

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            float mInitialMotionX, mInitialMotionY = 0;

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }


            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
//                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("--------------invoked--------TextSubmitSearch----------");
//                Intent textSubmit = new Intent(getBaseContext(), SearchResultsActivity.class);
//                Bundle extras = new Bundle();
//                extras.putString("title", query.toString());
//                extras.putString("catId", "0");
//                extras.putString("searchType", "ontextQuery");
//                textSubmit.putExtras(extras);
////                newActivity.putExtra("year", movie.getRelease_year());
//                startActivity(textSubmit);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void startActivity(Intent intent) {
        // check if search intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra("title", searchView.getQuery().toString());
        }

        super.startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                System.out.println("action_search");
                return true;
            case R.id.pref_about:
                AboutFragment aboutFragment = new AboutFragment();
                aboutFragment.show(getFragmentManager(), "aboutdialog");
                return true;
            case R.id.pref_lang:
                LanguageFragment dialogFragment = LanguageFragment.newInstance(getBaseContext(), tabAdapter, getIntent());
                dialogFragment.show(getFragmentManager(), "dialog");
                return false;
            case R.id.action_settings:
                Intent newActivity = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(newActivity);
                return true;
            case R.id.pref_exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


    @Override
    public void updateLanguage() {
        String lang = PreferenceUtil.getLanguage(getBaseContext());
        if (lang != null) {
            if (lang.equals("az")) {
                AppController.setLocaleAZ(getBaseContext());
            } else if (lang.equals("en_US")) {
                AppController.setLocaleEN(getBaseContext());
            } else if (lang.equals("ru")) {
                AppController.setLocaleRU(getBaseContext());
            }
            this.finish();
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
        }
    }


    public void getRegId() {
        Log.i("", "^^^^^^^^^^^^^ " + PreferenceUtil.getGCMNotify(getApplicationContext()));
        System.out.println("^^^^^^^^^^^^^ " + PreferenceUtil.getGCMNotify(getApplicationContext()));
        if (PreferenceUtil.getGCMNotify(getApplicationContext()) == null || PreferenceUtil.getGCMNotify(getApplicationContext()).equals("")) {

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String msg = "";
                    try {
                        if (gcm == null) {
                            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                        }
                        Log.i(TAG,":::::::::: "+Config.SENDER_ID);
                        regId = gcm.register(Config.SENDER_ID);
                        msg = "Device registered, registration ID=" + regId;
                        Log.i("GCM", msg);
                    } catch (IOException ex) {
                        msg = "Error :" + ex.getMessage();
                    }
                    return msg;
                }

                @Override
                protected void onPostExecute(String msg) {
//                Message.message(getApplicationContext(), "msg " + msg);
                }
            }.execute(null, null, null);
        }
    }
}
