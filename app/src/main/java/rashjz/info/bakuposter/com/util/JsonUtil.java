package rashjz.info.bakuposter.com.util;

import android.graphics.Movie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import rashjz.info.app.bakuposter.com.model.ListItem;
import rashjz.info.app.bakuposter.com.model.SearchModel;


public class JsonUtil {

    private static Gson gson;
    private static JsonElement mJson;

    public static ArrayList<Movie> downloadMovieList(String response) {
        ArrayList<Movie> result = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(response);

            for (int i = 0; i < arr.length(); i++) {
                arr.getJSONObject(i);
                gson = new Gson();
                mJson = new JsonParser().parse(arr.getJSONObject(i).toString());
                result.add(gson.fromJson(mJson, Movie.class));
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;

    }


    public static ArrayList<ListItem> downloadContentList(JSONArray response) {
        ArrayList<ListItem> result = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject object=  response.getJSONObject(i);
                gson = new Gson();
                gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                mJson = new JsonParser().parse(object.toString());
                result.add(gson.fromJson(mJson, ListItem.class));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public static ListItem downloadContentItemData(JSONObject  response) {
        ListItem result = new ListItem();
        try {
            gson = new Gson();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            mJson = new JsonParser().parse(response.toString());
            result= gson.fromJson(mJson, ListItem.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
         Gson gson=new Gson();
        SearchModel model=new SearchModel();
        model.setTo(100);
        model.setFrom(0);
        model.setType_id(BigDecimal.ONE);
        System.out.println(gson.toJson(model).toString());
    }
}
