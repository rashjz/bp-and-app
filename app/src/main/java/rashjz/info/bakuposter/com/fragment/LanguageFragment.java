package rashjz.info.bakuposter.com.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import rashjz.info.bakuposter.com.R;
import rashjz.info.bakuposter.com.adapter.TabsPagerAdapter;
import rashjz.info.bakuposter.com.util.PreferenceUtil;


public class LanguageFragment extends DialogFragment implements View.OnClickListener {
    private static Intent intent;
    private static Context appContext;
    private Button btnEN, btnAZ, btnRU;
    final String LOG_TAG = "myLogs";
    private static TabsPagerAdapter tabsAdapter;
    ChangeLanguage changeLanguage;

    public static LanguageFragment newInstance(Context context, TabsPagerAdapter tabsAdaptr,Intent inten) {
        LanguageFragment f = new LanguageFragment();
        appContext = context;
        tabsAdapter = tabsAdaptr;
        intent=inten;
        // Supply num input as an argument.
        Bundle args = new Bundle();
//        args.putInt("num", num);
//        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, null);
//        setContentView(R.layout.dish_tag);
//        super.onCreate(savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS); //before
//        getDialog().setTitle("Dil Seçimi");
        btnEN = (Button) view.findViewById(R.id.btnEN);
        btnEN.setOnClickListener(this);
        btnAZ = (Button) view.findViewById(R.id.btnAZ);
        btnAZ.setOnClickListener(this);
        btnRU = (Button) view.findViewById(R.id.btnRU);
        btnRU.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEN:
                PreferenceUtil.setLanguage(appContext, "en_US");
//                AppController.setLocaleEN(appContext);
//                Toast.makeText(getActivity(), "english", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAZ:
                PreferenceUtil.setLanguage(appContext, "az");
//                AppController.setLocaleAZ(appContext);
//                Toast.makeText(getActivity(), "azeri", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRU:
                PreferenceUtil.setLanguage(appContext, "ru");
//                AppController.setLocaleRU(appContext);
//                Toast.makeText(getActivity(), "russian", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        changeLanguage.updateLanguage();
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
//        tabsAdapter.onConfigurationChanged(newConfig);
    }


    public   interface ChangeLanguage {
        public void updateLanguage();
    }

    @Override
    public void onAttach(Activity activity) {
        System.out.println("onAttach");
        changeLanguage = (ChangeLanguage) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        System.out.println("onDetach");
        changeLanguage = null;
        super.onDetach();
    }
}
