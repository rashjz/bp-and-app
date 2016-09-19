package rashjz.info.bakuposter.com;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    // give your server registration url here
    public static final String SERVER_URL = "http://78.46.139.155:8075/Bakuposter-ws/bakufun/";
    // Google project id
    public static final String SENDER_ID = "3096103126";
    public static final String TAG = "BAKUPOSTER";

    public static final String DISPLAY_MESSAGE_ACTION = "rashjz.info.bakuposter.com.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}