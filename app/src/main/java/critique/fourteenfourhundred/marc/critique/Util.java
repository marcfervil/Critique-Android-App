package critique.fourteenfourhundred.marc.critique;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Marc on 3/5/18.
 */

public class Util {


    public static void bindClickButtonEventsToFragment(QueFragment fragment, int[] ids){
        for(int id:ids){
            Button button = (Button) fragment.getActivity().findViewById(R.id.voteGood);
            button.setOnClickListener((View.OnClickListener) fragment);
        }
    }

    public static JSONObject makeJson(Object[]... params){
        try {
            JSONObject f = new JSONObject();
            for (int i =0; i < params.length;i++) {
                f.put((String)params[i][0], params[i][1]);
            }
            return f;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void postRequest(Activity activity,String url,JSONObject data,Response.Listener rl,Response.ErrorListener re){
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, data,rl,re);
        queue.add(getRequest);
    }


    public static void showDialog(Activity activity,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
