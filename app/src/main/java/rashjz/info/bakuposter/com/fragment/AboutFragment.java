package rashjz.info.bakuposter.com.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import rashjz.info.bakuposter.com.R;


public class AboutFragment extends DialogFragment implements View.OnClickListener {
    private Button btnOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about, null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        btnOK = (Button) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
//                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        dismiss();
    }
}
