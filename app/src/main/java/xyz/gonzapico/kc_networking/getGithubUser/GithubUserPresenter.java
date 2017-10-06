package xyz.gonzapico.kc_networking.getGithubUser;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
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
  private VolleyHandler mVolleyHandlerInstance;

  public GithubUserPresenter(){

  }

  public void attachView(GithubUserView githubUserView){
    mVolleyHandlerInstance = VolleyHandler.getInstance((HomeActivity)githubUserView);
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
              getGithubImage(githubUserModel.getAvatarUrl());
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

  /***
   * Use case
   *
   * Version 1.- with Image Loader inside Singleton
   */
  public void getGithubImageWithCache(String urlImage){
    ImageLoader imageLoader = mVolleyHandlerInstance.getImageLoader();
    imageLoader.get(urlImage, new ImageLoader.ImageListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Image Load Error: " + error.getMessage());
      }

      @Override
      public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
        Bitmap bitmap = response.getBitmap();
        if (bitmap != null) {
          mGithubUserView.renderImage(bitmap);
        }
      }
    });
  }

  /***
   * Use case
   *
   * Version 2.- with Image Request
   */
  public void getGithubImage(String urlImage){
    ImageRequest request = new ImageRequest(urlImage,
        new Response.Listener<Bitmap>() {
          @Override
          public void onResponse(Bitmap bitmap) {
            if (bitmap != null) {
              mGithubUserView.renderImage(bitmap);
            }
          }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
        new Response.ErrorListener() {
          public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Image Load Error: " + error.getMessage());
          }
        });

    VolleyHandler.getInstance((HomeActivity)mGithubUserView).addToRequestQueue(request);
  }
}
