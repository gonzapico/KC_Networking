package xyz.gonzapico.kc_networking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import xyz.gonzapico.kc_networking.getGithubUser.GithubUserView;

public class HomeActivity extends AppCompatActivity implements GithubUserView {

  TextView tvGithubUsername, tvGithubBio;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    bindViews();
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
  }
}
