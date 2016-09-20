package rashjz.info.bakuposter.com.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.SearchModel;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.util.Config;
import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.adapter.TheatreListAdapter;
import rashjz.info.bakuposter.com.util.DateUtil;
import rashjz.info.bakuposter.com.util.JsonUtil;
import rashjz.info.bakuposter.com.util.Message;

public class SearchResultsActivity extends AppCompatActivity {
    private JsonArrayRequest request;
    private TextView txtQuery;
    private ListView listView;
    private TheatreListAdapter adapter;
    private SearchModel model = new SearchModel();
    private List<ListItem> searchList = new ArrayList<ListItem>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
//        // get the action bar
        ActionBar actionBar = getSupportActionBar();
//        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        //corey
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtQuery = (TextView) findViewById(R.id.txtQuery);
//        handleIntent(getIntent());
        adapter = new TheatreListAdapter(this, searchList);


        progressDialog = new ProgressDialog(SearchResultsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Yüklənir...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
        downloadContentList();
        //
        //CREATING LIST ADAPTER
        listView = (ListView) findViewById(R.id.list);
//
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                Intent newActivity = new Intent(getBaseContext(), SelectedItemActivity.class);
                ListItem movie = (ListItem) view.getTag();
                newActivity.putExtra("listitem", movie);
                startActivity(newActivity);

            }
        });
        adapter.setmItems(searchList);
        adapter.notifyDataSetChanged();
    }

    public void downloadContentList() {
        model = new SearchModel();
        model.setFrom(0);
        model.setTo(100);
        model.setTitle(getIntent().getStringExtra("title"));
        if (getIntent().getStringExtra("searchType") != null && getIntent().getStringExtra("searchType").toString().equals("advanced")) {
            if (getIntent().getStringExtra("catId") != null && !getIntent().getStringExtra("catId").equals("")) {
                model.setType_id(new BigDecimal(getIntent().getStringExtra("catId").toString()));
            }

            if (getIntent().getStringExtra("dateFrom") != null && !getIntent().getStringExtra("dateFrom").equals("")) {
                Date date = DateUtil.strToDate(getIntent().getStringExtra("dateFrom").toString());
                model.setFrom_date(date);
            }
            if (getIntent().getStringExtra("dateTo") != null && !getIntent().getStringExtra("dateTo").equals("")) {
                Date date = DateUtil.strToDate(getIntent().getStringExtra("dateTo").toString());
                model.setTo_date(date);
            }
        } else {
            model.setType_id(BigDecimal.ZERO);
        }
  
        Gson gson = new Gson();
        String json = gson.toJson(model);
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        request = new JsonArrayRequest(Request.Method.POST, Config.wsURL, json,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        searchList = JsonUtil.downloadContentList(response);
                        adapter.setmItems(searchList);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("--------------------- " + error.toString());
                        progressDialog.dismiss();
                        Message.message(getApplicationContext(),"Sorğu nəticə vermədi!");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "xxxxxxxxxxxxxx==");
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            txtQuery.setText("açar sözlük: " + query);
        }
    }


}
