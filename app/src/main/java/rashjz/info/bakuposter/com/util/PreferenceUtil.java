package rashjz.info.bakuposter.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rashjz.info.app.bakuposter.com.model.User;


/**
 * Created by Rash
 */
public class PreferenceUtil {

    public static final String PREFS_NAME = "Bakuposter";
    public static final String Language = "language";
    public static final String imgUrl = "imgUrl";
    public static final String email = "email";
    public static final String fullname = "fullname";
    public static final String isGCMRegistered = "isGCMRegistered";


    public PreferenceUtil() {
        super();
    }

    public static String getIpAddress(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ipAddress = sharedPrefs.getString("prefIpAddress", "NULL");
        return ipAddress;
    }


    public static String getLanguage(Context context) {
        SharedPreferences settings;
        String status;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(Language)) {
            status = settings.getString(Language, null);
        } else
            return null;
        return status;
    }

    public static void setLanguage(Context context, String lang) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(Language, lang);
        editor.commit();
    }

    public static User getUserDetails(Context context) {
        SharedPreferences settings;
        String simgUrl, memail, mfullname;
        User user = new User();
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(imgUrl)) {
            simgUrl = settings.getString(imgUrl, null);
            user.setImgUrl(simgUrl);
        }
        if (settings.contains(email)) {
            memail = settings.getString(email, null);
            user.setEmail(memail);
        }
        if (settings.contains(fullname)) {
            mfullname = settings.getString(fullname, null);
            user.setFullname(mfullname);
        }

        return user;
    }

    public static void setUserDetails(Context context, User user) {
        System.out.println("---------- " + user.toString());
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(fullname, user.getFullname());
        editor.putString(email, user.getEmail());
        editor.putString(imgUrl, user.getImgUrl());
        editor.commit();
    }


    public static void setGCMNotify(Context context, String regid) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(isGCMRegistered, regid);
        editor.commit();
    }
    public static String getGCMNotify(Context context) {
        SharedPreferences settings;
        String  regid = "";
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(isGCMRegistered)) {
            regid = settings.getString(isGCMRegistered, null);
        }
        return regid;
    }
}
