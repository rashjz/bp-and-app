package rashjz.info.bakuposter.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rashjz.info.app.bakuposter.com.model.Comment;
import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.util.Config;
import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.adapter.MessagesListAdapter;
import rashjz.info.bakuposter.com.util.DeviceUtil;


/**
 * Created by rasha_000 on 10/4/2015.
 */
public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getSimpleName();
    private Button btnSend;
    private EditText inputMsg;
    private JsonObjectRequest request;
    private List<Comment> listMessages = new ArrayList<>();
    private rashjz.info.bakuposter.com.adapter.MessagesListAdapter adapter;
    private ListView listViewMessages;
    private Comment comment=new Comment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_view); //messaging_screen);
        btnSend = (Button) findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        listViewMessages = (ListView) findViewById(R.id.list_view_messages);
        //show list fromdb
        final ListItem item = (ListItem) getIntent().getSerializableExtra("item");
        for (Comment s : item.getComments()) {
            item.getComments().add(new Comment(s.getMessage(), s.getFromName()));
            listMessages.add(new Comment(s.getMessage(), s.getFromName()));
        }
        adapter = new MessagesListAdapter(this, listMessages);
        listViewMessages.setAdapter(adapter);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getApplicationContext(), inputMsg.getText().toString());
                // Clearing the input filed once message was sent
                listMessages.add(new Comment(inputMsg.getText().toString(),  getIntent().getStringExtra("useremail")));
                adapter = new MessagesListAdapter(getApplicationContext(), listMessages);
                listViewMessages.setAdapter(adapter);
                //insert comment web service
                comment.setMac_id(DeviceUtil.getDeviceID(getApplicationContext()));
                comment.setContent_id(item.getRec_id());
                comment.setFromName(getIntent().getStringExtra("useremail"));
                comment.setMessage(inputMsg.getText().toString());
                inputMsg.setText("");
                insertCommentWS(comment);
            }
        });
    }


    public void insertCommentWS(Comment comment) {
//        System.out.println("cccccc"+comment.toString());
        Gson gson = new Gson();
        String json = gson.toJson(comment);
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        System.out.println("::::"+json);
        request = new JsonObjectRequest(Request.Method.POST, Config.insComment, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        comment = JsonUtil.downloadContentItemData(response);
//                        adapter.setData(movieList);
//                        adapter.notifyDataSetChanged();
//                        System.out.println(" ------------ " + item.getComments().size());
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
                params.put("Authorization", "cmFzaGp6OnBhcmtldDQ3MA==");
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back, R.anim.activity_backend);
    }
}
