package rashjz.info.bakuposter.com.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Work on 4/7/2015.
 */
public class Message {
    public static void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
