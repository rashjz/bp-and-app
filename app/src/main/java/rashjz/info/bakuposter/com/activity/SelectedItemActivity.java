package rashjz.info.bakuposter.com.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.User;
import rashjz.info.app.bakuposter.com.model.Vote;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.util.Config;
import rashjz.info.bakuposter.com.util.DeviceUtil;
import rashjz.info.bakuposter.com.util.JsonUtil;
import rashjz.info.bakuposter.com.util.Message;
import rashjz.info.bakuposter.com.util.PreferenceUtil;

public class SelectedItemActivity extends AppCompatActivity {
    private TextView title, rating, genre, year, desc;
    NetworkImageView thumbNail;

    private GoogleMap googleMap;
    private ImageButton mapBtn, playBtn, commentBtn, likeBtn, shareBtn,calBtn;
    private RatingBar ratingBar;
    private ScrollView itemScroll;
    private JsonObjectRequest request;
    private ListItem item = new ListItem();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Vote vote = new Vote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_item);

        //corey
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //getfrom list
        item = (ListItem) getIntent().getSerializableExtra("listitem");
        //download
        item = downloadItemdata();
        System.out.println(item.getLike_status());
        //
        itemScroll = (ScrollView) findViewById(R.id.itemScroll);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.title);
        rating = (TextView) findViewById(R.id.rating);
        genre = (TextView) findViewById(R.id.genre);
        desc = (TextView) findViewById(R.id.desc);
        year = (TextView) findViewById(R.id.releaseYear);
        thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        thumbNail.setImageUrl(item.getImg_url(), imageLoader);

//data



        title.setText("" +item.getTitle());
        rating.setText("Reytinq : " + item.getRating());
        genre.setText("Janr : " + item.getGenre());
        if (item.getDescription() != null) {
            desc.setText("Şərh : " + item.getDescription());
        }
        year.setText("İl : " +item.getRelease_year());

        mapBtn = (ImageButton) findViewById(R.id.mapBtn);

        playBtn = (ImageButton) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getVideo_url() != null && !item.getVideo_url().equals("")) {
                    Intent newActivity = new Intent(getApplicationContext(), VideoActivity.class);
                    newActivity.putExtra("video_url", item.getVideo_url());
                    startActivity(newActivity);
                } else {
                    Message.message(getBaseContext(), "Uyğun video fayl tapilmadı!");
                }
            }
        });
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusOnView();
            }
        });

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //coordinates
            LatLng TutorialsPoint = new LatLng(item.getLocation().getLatitude().floatValue(), item.getLocation().getLongitude().floatValue());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            Marker TP = googleMap.addMarker(new MarkerOptions().position(TutorialsPoint).title(item.getLocation().getTitle()));
            TP.showInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float rtn = ratingBar.getRating() * 2;
//                Toast.makeText(getBaseContext(), String.valueOf(rtn), Toast.LENGTH_LONG).show();
            }
        });


        commentBtn = (ImageButton) findViewById(R.id.commentBtn);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(getApplicationContext(), CommentActivity.class);
                String userEmail = DeviceUtil.getUsername(getApplicationContext());
                User user = PreferenceUtil.getUserDetails(getApplicationContext());
                if (user != null && user.getEmail() != null && !user.getEmail().equals("")) {
                    getToComment(item, user.getEmail());
                } else if (userEmail != null && !userEmail.equals("")) {
                    getToComment(item, userEmail);
                } else {
                    Message.message(getApplicationContext(), "Qeydiyyatdan keçin!");
                }
            }
        });

        System.out.println("==========" + item.getLike_status());
        likeBtn = (ImageButton) findViewById(R.id.likeBtn);


        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote.setVote_type(2);
                if (likeBtn.getTag() != null && likeBtn.getTag().toString().equals("emp_like")) {
                    likeBtn.setImageResource(R.drawable.like);
                    likeBtn.setTag("like");
                    vote.setRate(1);
                } else {
                    likeBtn.setImageResource(R.drawable.emp_like);
                    likeBtn.setTag("emp_like");
                    vote.setRate(0);
                }
                vote.setDeviceID(DeviceUtil.getDeviceID(getApplicationContext()));
                voteContentws(vote);
            }
        });

        shareBtn = (ImageButton) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.setType("*/*");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, item.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, item.getDescription());
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File( item.getImg_url())));  //optional//use this when you want to send an image
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "PAYLAŞIM ET"));
            }
        });

        calBtn = (ImageButton) findViewById(R.id.calBtn);
        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
               if(item.getEnd_date()!=null){
                   cal.setTime(item.getEnd_date());
               }
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title",item.getTitle().toString());
                startActivity(intent);
            }
        });
    }

    public void getToComment(ListItem item, String email) {
        Intent newActivity = new Intent(getApplicationContext(), CommentActivity.class);
        newActivity.putExtra("useremail", email);
        newActivity.putExtra("item", item);
        startActivity(newActivity);
        overridePendingTransition(R.anim.activity_appear, R.anim.activity_start);
    }

    public ListItem downloadItemdata() {
        //nota vurram device if rating ucun
        item.setBnote(DeviceUtil.getDeviceID(getApplicationContext()));
//        String recid = getIntent().getStringExtra("recID");
//        item.setRec_id(new BigDecimal(recid));
        Gson gson = new Gson();
        String json = gson.toJson(item);
        System.out.println("------------ " + json.toString());
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        System.out.println("getItemdata:::::::::::::: " + json);
        request = new JsonObjectRequest(Request.Method.POST, Config.getItemdata, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        item = JsonUtil.downloadContentItemData(response);
                        System.out.println(" ------------ like" + item.getLike_status());
                        if (item.getLike_status() == 1) {
                            likeBtn.setTag("like");
                            likeBtn.setImageResource(R.drawable.like);
                        } else {
                            likeBtn.setImageResource(R.drawable.emp_like);
                            likeBtn.setTag("emp_like");
                        }
//                        adapter.setData(movieList);
//                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-------------VOLLEYERROR--------- " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "xxxxxxxxxxxx==");
                return params;
            }
        };
        queue.add(request);
        System.out.println(" ------------ like" + item.getLike_status());
        return item;
    }


    public void voteContentws(Vote vote) {
//        String recid = getIntent().getStringExtra("recID");
        vote.setContent_id(item.getRec_id());
        Gson gson = new Gson();
        String json = gson.toJson(vote);
        System.out.println("------------ " + json.toString());
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        request = new JsonObjectRequest(Request.Method.POST, Config.voteContent, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("**--**--*--**---- " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("-------------VOLLEYERROR--------- " + error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
                params.put("Authorization", "xxxxxxxxxxxxxxxxxxx==");
                return params;
            }
        };
        queue.add(request); 
    }


    private final void focusOnView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                itemScroll.scrollTo(0, 443);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back, R.anim.activity_backend);
    }
}
