package views;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import model.Constants;
import views.MainThread;

import static views.GameplayScene.mediaPlayer;

/**
 * Created by gasalabert on 28/02/18.
 */
public class GameActivity extends AppCompatActivity{
    public static GameView mView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Boolean mario = getIntent().getExtras().getBoolean("mario");
        Boolean sonic = getIntent().getExtras().getBoolean("sonic");
        Constants.MARIO = mario;
        Constants.SONIC = sonic;
        Constants.VIB = getSystemService(Context.VIBRATOR_SERVICE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = new GameView(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(mView);
    }

    @Override
    protected void onPause() {
        super.onStop();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("La bonne save", "onSaveInstanceState");
        //SharedPreferences

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Le bon restart", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("La bonne destruction", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("La bonne reprise", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Le bon start", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Le bon stop", "onStop");
    }

    public static GameView getView(){
        return mView;
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.stop();
        super.onBackPressed();
        MainThread.setKeepUpdate(false);
        finish();
    }
}