package rashjz.info.bakuposter.com.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import rashjz.info.app.bakuposter.com.model.Comment;
import rashjz.info.bakuposter.com.R;


public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> messagesItems;

    public MessagesListAdapter(Context context, List<Comment> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        Comment m = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // message belongs to other person, load the left aligned layout
        convertView = mInflater.inflate(R.layout.list_item_message_left, null);

        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
//setters
        txtMsg.setText(" `" + m.getMessage() + "` ");
        lblFrom.setText("Email : " + m.getFromName());

        return convertView;
    }
}