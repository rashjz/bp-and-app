package rashjz.info.bakuposter.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.SearchModel;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.util.Config;
import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.activity.MainActivity;
import rashjz.info.bakuposter.com.activity.SelectedItemActivity;
import rashjz.info.bakuposter.com.adapter.ConsertAdapter;


public class ConsertFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private GridView gridView;
    private ConsertAdapter adapter;
    private JsonArrayRequest request;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ListItem> movieList = new ArrayList<ListItem>();
    private ActionBar actionBar;
    private SearchModel model = new SearchModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_consert, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        downloadContentList();
        //CREATING grid ADAPTER
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        adapter = new ConsertAdapter(getContext(), R.layout.list_item_consert, this.getActivity(), movieList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                Intent newActivity = new Intent(getContext(), SelectedItemActivity.class);
                ListItem movie = (ListItem) view.getTag();
                newActivity.putExtra("listitem", movie);
                startActivity(newActivity);
                getActivity().overridePendingTransition(R.anim.activity_appear, R.anim.activity_start);

            }
        });
        startSwiperRefresh();
        onMoveUpDisableswipe();
        return rootView;
    }

    public void downloadContentList() {
        model = new SearchModel();
        model.setFrom(0);
        model.setTo(100);
        model.setType_id(new BigDecimal(2));
        Gson gson = new Gson();
        String json = gson.toJson(model);
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        request = new JsonArrayRequest(Request.Method.POST, Config.wsURL, json,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        movieList = rashjz.info.bakuposter.com.util.JsonUtil.downloadContentList(response);
                        adapter.setData(movieList);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("--------------------- " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.headerParamName, Config.headerParam);
                return params;
            }
        };
        swipeRefreshLayout.setRefreshing(false);
        queue.add(request);
    }

    public void onMoveUpDisableswipe() {
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (gridView != null && gridView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = gridView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = gridView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

    }

    public void startSwiperRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        System.out.println("start refresh on create............................");
                                        downloadContentList();
                                        System.out.println("end refresh oncreate...............................");
                                    }
                                }
        );

    }

    //
    @Override
    public void onRefresh() {
        System.out.println("refresh on swipe down.....");
        swipeRefreshLayout.setRefreshing(false);
    }
}
