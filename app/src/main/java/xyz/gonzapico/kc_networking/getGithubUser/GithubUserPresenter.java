package xyz.gonzapico.kc_networking.getGithubUser;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import org.json.JSONObject;
import xyz.gonzapico.kc_networking.HomeActivity;
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
    VolleyHandler.getInstance((HomeActivity)mGithubUserView).cancelPendingRequests(TAG);
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
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<GithubUserModel> jsonAdapter = moshi.adapter(GithubUserModel.class);

            GithubUserModel githubUserModel = null;
            try {
              githubUserModel = jsonAdapter.fromJson(response.toString());
              mGithubUserView.renderUsername(githubUserModel.getName());
              mGithubUserView.renderBio(githubUserModel.getBio());
            } catch (IOException error) {
              Log.e(TAG, error.getMessage());
            }
          }
        }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
      }
    });

    VolleyHandler.getInstance((Context)mGithubUserView).addToRequestQueue(githubUserRequest, TAG);
  }
}
