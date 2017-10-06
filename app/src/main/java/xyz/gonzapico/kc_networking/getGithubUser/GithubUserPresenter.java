package xyz.gonzapico.kc_networking.getGithubUser;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import xyz.gonzapico.kc_networking.VolleyHandler;

/**
 * Created by gonzapico on 06/10/2017.
 */

public class GithubUserPresenter {

  private final static String TAG = GithubUserPresenter.class.getSimpleName();

  private GithubUserView mGithubUserView;

  public GithubUserPresenter(){

  }

  public void attachView(GithubUserView githubUserView){
    mGithubUserView = githubUserView;
  }

  public void detachView(){
    mGithubUserView = null;
  }

  /***
   * Use case
   */
  public void getGithubUser(){
    JsonObjectRequest githubUserRequest = new JsonObjectRequest(Request.Method.GET,
        "https://api.github.com/users/gonzapico", null,
        new Response.Listener<JSONObject>() {

          @Override
          public void onResponse(JSONObject response) {
            Log.d(TAG, response.toString());
            
          }
        }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
      }
    });

    VolleyHandler.getInstance((Context)mGithubUserView).addToRequestQueue(githubUserRequest);
  }
}
