package xyz.gonzapico.kc_networking.getGithubUser;

/**
 * Created by gonzapico on 06/10/2017.
 */

public class GithubUserPresenter {

  private GithubUserView mGithubUserView;

  public GithubUserPresenter(){

  }

  public void attachView(GithubUserView githubUserView){
    mGithubUserView = githubUserView;
  }

  public void detachView(){
    mGithubUserView = null;
  }
}
