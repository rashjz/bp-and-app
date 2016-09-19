package rashjz.info.bakuposter.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rashjz.info.bakuposter.com.R;


public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private List<rashjz.info.bakuposter.com.model.SpinnerModel> data;
    public Resources res;
    rashjz.info.bakuposter.com.model.SpinnerModel tempValues = null;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(Activity activity, int textViewResourceId, List objects, Resources resLocal) {
        super(activity, textViewResourceId, objects);
        data = objects;
        res = resLocal;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);
        tempValues = null;
        tempValues = (rashjz.info.bakuposter.com.model.SpinnerModel) data.get(position);
        TextView label = (TextView) row.findViewById(R.id.label);
        ImageView imageView = (ImageView) row.findViewById(R.id.image);

        label.setText(tempValues.getItemName());
        imageView.setImageResource(tempValues.getDrawerImg());
        row.setTag(tempValues);
        return row;
    }
}