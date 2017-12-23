package rashjz.info.bakuposter.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.bakuposter.com.AppController;
import rashjz.info.bakuposter.com.R;


/**
 * * @author Rashad Javadov*
 */
public class ConsertAdapter extends ArrayAdapter<Movie> {


    private FragmentActivity activity;
    private Context context;
    private int layoutResourceId;
    private List<ListItem> data = new ArrayList<>();
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public ConsertAdapter(Context context, int layoutResourceId,  FragmentActivity activity, List<ListItem> movieList) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.activity = activity;
        this.data = movieList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = ((Activity) context).getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
//            convertView = inflater.inflate(layoutResourceId, parent,false);
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);


        // getting movie data for the row
        ListItem m = data.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImg_url(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));

        // genre
        String genreStr = ""+m.getGenre();
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);

        convertView.setTag(m);
        return convertView;

    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }


    public List<ListItem> getData() {
        return data;
    }

    public void setData(List<ListItem> data) {
        this.data = data;
    }
}
