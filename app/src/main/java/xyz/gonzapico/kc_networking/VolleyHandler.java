package xyz.gonzapico.kc_networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by gonzapico on 05/10/2017.
 */

public class VolleyHandler {

  public static final String TAG = VolleyHandler.class
      .getSimpleName();

  private static VolleyHandler mVolleyHandlerInstance;
  private RequestQueue mRequestQueue;
  private ImageLoader mImageLoader;
  private static Context mContext;

  private VolleyHandler(Context context) {
    mContext = context;
    mRequestQueue = getRequestQueue();

    createImageLoader();
  }

  private void createImageLoader() {
    mImageLoader = new ImageLoader(mRequestQueue,
        new ImageLoader.ImageCache() {
          private final LruCache<String, Bitmap>
              cache = new LruCache<String, Bitmap>(20);

          @Override
          public Bitmap getBitmap(String url) {
            return cache.get(url);
          }

          @Override
          public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
          }
        });
  }

  public static synchronized VolleyHandler getInstance(Context context) {
    if (mVolleyHandlerInstance == null) {
      mVolleyHandlerInstance = new VolleyHandler(context);
    }
    return mVolleyHandlerInstance;
  }

  public RequestQueue getRequestQueue() {
    if (mRequestQueue == null) {
      // getApplicationContext() keeps you from leaking the Activity
      mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
    }
    return mRequestQueue;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setTag(TAG);
    getRequestQueue().add(req);
  }

  public <T> void addToRequestQueue(Request<T> req,String tag) {
    req.setTag(tag);
    getRequestQueue().add(req);
  }

  public ImageLoader getImageLoader() {
    return mImageLoader;
  }

  public void cancelPendingRequests(Object tag) {
    if (mRequestQueue != null) {
      mRequestQueue.cancelAll(tag);
    }
  }
}
