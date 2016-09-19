//package app.info.rashjz.amuse.util;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONArray;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.util.ArrayList;
//
//import app.info.rashjz.amuse.model.MainItem;
//
///**
// * Created by Work on 4/24/2015.
// */
//public class DownloadHelper {
//
//    private static Gson gson;
//    private static JsonElement mJson;
//
//    public static ArrayList<MainItem> downloadTables(String url){
//        ArrayList<MainItem> result = new ArrayList<>();
//        MainItem item;
//        try {
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet();
//        request.setURI(new URI(url));
//        HttpResponse response = client.execute(request);
//        BufferedReader in = new BufferedReader(new InputStreamReader(response
//                .getEntity().getContent()));
//        String line = "";
//        while ((line = in.readLine()) != null) {
//            JSONArray arr = new JSONArray(line);
//
//            for (int i = 0; i < arr.length(); i++) {
//                arr.getJSONObject(i);
//                gson = new Gson();
//                mJson =  new JsonParser().parse(arr.getJSONObject(i).toString());
//                result.add(gson.fromJson(mJson, MainItem.class));
//            }
//        }
//
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
//        return result;
//
//    }
//
////    public static CDepartment_ScreenMenu downloadScreenMenuItem(String responce){
////        CDepartment_ScreenMenu cDepartment_screenMenu = new CDepartment_ScreenMenu();
////        try {
////                gson = new Gson();
////                mJson =  new JsonParser().parse(responce);
////                cDepartment_screenMenu = gson.fromJson(mJson, CDepartment_ScreenMenu.class);
////        } catch (Throwable t) {
////            t.printStackTrace();
////        }
////
////        return cDepartment_screenMenu;
////    }
//
//
//
//}
