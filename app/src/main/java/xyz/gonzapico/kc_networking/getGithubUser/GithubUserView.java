package xyz.gonzapico.kc_networking.getGithubUser;

import android.graphics.Bitmap;

/**
 * Created by gonzapico on 06/10/2017.
 */

public interface GithubUserView {

  void renderUsername(String username);

  void renderBio(String bio);

  void renderImage(Bitmap bitmap);
}
