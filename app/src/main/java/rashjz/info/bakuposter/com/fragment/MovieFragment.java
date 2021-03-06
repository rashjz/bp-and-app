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
import android.widget.ListView;

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
import rashjz.info.bakuposter.com.adapter.MovieAdapter;
import rashjz.info.bakuposter.com.util.JsonUtil;


public class MovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private MovieAdapter adapter;
    private JsonArrayRequest request;
    private ActionBar actionBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ListItem> movieList = new ArrayList<>();
    private SearchModel model = new SearchModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        downloadContentList();


        //CREATING LIST ADAPTER
        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new MovieAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        startSwiperRefresh();
        onMoveUpDisableswipe();

        return rootView;
    }


    public void downloadContentList() {
        model = new SearchModel();
        model.setFrom(0);
        model.setTo(100);
//        model.setTitle("Javad");
        model.setType_id(BigDecimal.ONE);
        Gson gson = new Gson();
        String json = gson.toJson(model);
        System.out.println("***** "+json);
        RequestQueue queue = AppController.getInstance().getRequestQueue();

        request = new JsonArrayRequest(Request.Method.POST, Config.wsURL, json,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString()+"******+++++");
                        movieList = JsonUtil.downloadContentList(response);
                        adapter.setmItems(movieList);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.println("-----------downloadContentList---------- " + error.toString());
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json");
                params.put(Config.headerParamName, Config.headerParam);
                return params;
            }
        };
        swipeRefreshLayout.setRefreshing(false);
        queue.add(request);
    }


    public void onMoveUpDisableswipe() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

    }

    public void startSwiperRefresh() {
//        swipeRefreshLayout.setColorSchemeColors(
//                android.R.color.holo_blue_bright,
//                android.R.color.holo_blue_dark,
//                android.R.color.holo_purple);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        downloadContentList();
                                    }
                                }
        );

    }

    @Override
    public void onRefresh() {
        System.out.println("refresh on swipe down.....");
        swipeRefreshLayout.setRefreshing(false);
    }

}