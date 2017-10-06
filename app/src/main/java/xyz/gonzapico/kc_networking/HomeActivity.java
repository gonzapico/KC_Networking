package xyz.gonzapico.kc_networking;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import xyz.gonzapico.kc_networking.getGithubUser.GithubUserPresenter;
import xyz.gonzapico.kc_networking.getGithubUser.GithubUserView;

public class HomeActivity extends AppCompatActivity implements GithubUserView {

  private TextView tvGithubUsername, tvGithubBio;
  private ImageView ivGithubImage;
  private GithubUserPresenter mGithubPresenter;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    bindViews();

    setUpPresenter();

  }

  private void setUpPresenter() {
    mGithubPresenter = new GithubUserPresenter();
  }

  @Override protected void onResume() {
    super.onResume();
    mGithubPresenter.attachView(this);
    mGithubPresenter.getGithubUser();
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    mGithubPresenter.detachView();
  }

  @Override public void renderBio(String bio) {
    tvGithubBio.setText(bio);
  }

  @Override public void renderUsername(String username) {
    tvGithubUsername.setText(username);
  }

  private void bindViews(){
    tvGithubUsername = (TextView) findViewById(R.id.tvGithubName);
    tvGithubBio = (TextView) findViewById(R.id.tvGithubBio);
    ivGithubImage = (ImageView) findViewById(R.id.ivGithubAvatar);
  }

  @Override public void renderImage(Bitmap bitmap) {
    ivGithubImage.setImageBitmap(bitmap);
  }
}
