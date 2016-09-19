package   rashjz.info.bakuposter.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.R;

public class TheatreListAdapter extends BaseAdapter {

    private List<ListItem> mItems = new ArrayList<>();
    private Context mContext;
    private Activity activity;
    private FragmentManager mFragmentManager;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public TheatreListAdapter(Activity activity, List<ListItem> movieItems) {
        this.activity = activity;
        this.mItems = movieItems;

    }

    @Override
    public int getCount() {
        if (mItems != null) {
            return mItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public ListItem getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_theatre, null);

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        ListItem m = mItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImg_url(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre

        genre.setText("Rating: " + m.getGenre());

        // desc
        String descr = m.getDescription();
        if (descr != null && descr.length() < 62) {
            desc.setText("Description: " + descr + "...");
        } else {
            desc.setText("Description: " + descr.substring(0, 60) + "...");
        }
        // release year
        year.setText(String.valueOf(m.getRelease_year()));


        convertView.setTag(m);
        return convertView;
    }

    public List<ListItem> getmItems() {
        return mItems;
    }

    public void setmItems(List<ListItem> mItems) {
        this.mItems = mItems;
    }
}
