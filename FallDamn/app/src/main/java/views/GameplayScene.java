package views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.transition.*;
import android.transition.*;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.gasalabert.falldown.*;
import com.example.gasalabert.falldown.R;

import java.util.Random;
import java.util.Scanner;

import model.Constants;
import model.Platform;
import model.PlatformManager;
import model.RectanglePlayer;

import static model.PlatformManager.score;

/**
 * Created by Gabin Salabert on 08/03/2018.
 */

public class GameplayScene implements Scene{

    private RectanglePlayer rectanglePlayer;
    private Point playerPoint;
    private PlatformManager platformManager;
    private float sensitivity = 1.3f;

    public static MediaPlayer mediaPlayer;
    private MediaPlayer gOver;
    private MediaPlayer getObj;

    public static int randObject;

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private boolean cheater = false;
    private long gameOvertime;

    private int bPx;
    private int bPy;
    private int bTx;
    private int bTy;

    private int wCanvas;
    private int hCanvas;

    Activity act = (Activity) Constants.CURRENT_CONTEXT;

    private Rect r = new Rect();

    public GameplayScene(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);

        rectanglePlayer = new RectanglePlayer(new Rect(100, 100, 200, 200), Color.YELLOW);
        rectanglePlayer.update(playerPoint);

        platformManager = new PlatformManager(200, 450, 75, Color.MAGENTA);

        if(Constants.SONIC) {
            getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.srs);
            mediaPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.ghz);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }

        if(Constants.MARIO) {
            mediaPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.smb);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }
    }

    public void reset(){
        gOver.stop();
        if(Constants.SONIC) {
            mediaPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.ghz);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }

        if(Constants.MARIO) {
            getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.mcs);
            mediaPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.smb);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }
        cheater = false;
        platformManager = new PlatformManager(200, 450, 75, Color.MAGENTA);
        movingPlayer = false;
        score = 0;
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        rectanglePlayer.update(playerPoint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void catchTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!gameOver){
                    movingPlayer = true;

                    bPx = playerPoint.x;
                    bPy = playerPoint.y;
                    bTx = (int)event.getX();
                    bTy = (int)event.getY();
                }


                if(gameOver && System.currentTimeMillis() >= 2000){
                    gameOver = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)((bPx-(bTx-(int)event.getX())*sensitivity)), (int)((bPy-(bTy-(int)event.getY())*sensitivity)));
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    public void lelDraw(Canvas canvas, GameView gv) {
        Drawable drawable2 = null;
        Drawable drawable3 = null;
        Drawable drawable4 = null;
        Drawable drawable5 = null;
        Constants.CURRENT_GPS = this;



        if(Constants.MARIO) {
            drawable2 = gv.getResources().getDrawable(R.drawable.blockmario);
            drawable3 = gv.getResources().getDrawable(R.drawable.blocks);
            drawable4 = gv.getResources().getDrawable(R.drawable.blocks);
            drawable5 = gv.getResources().getDrawable(R.drawable.submarine);
        }

        if(Constants.SONIC) {
            drawable2 = gv.getResources().getDrawable(R.drawable.ring);
            drawable3 = gv.getResources().getDrawable(R.drawable.sonicplat);
            drawable4 = gv.getResources().getDrawable(R.drawable.sonicplat);
            drawable5 = gv.getResources().getDrawable(R.drawable.cem);
        }


        canvas.drawColor(Color.BLACK);

        rectanglePlayer.draw(canvas);

        platformManager.draw(canvas, drawable2, drawable3, drawable4, drawable5);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            drawGoText(canvas, paint, "Game Over !");
        }

        if (!gameOver && cheater){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            drawGoText(canvas, paint, "You Little Cheater !");
        }
    }

    @Override
    public void update() {

        Random r = new Random();
        randObject = r.nextInt((7 - 1) + 1) + 1;

        if(!gameOver) {
            rectanglePlayer.update(playerPoint);
            platformManager.update();

            for(Platform pl : platformManager.getPlatforms())
                if(rectanglePlayer.getRectangle().centerY() < pl.getRectangle2().centerY() && !pl.hasBeenTouch2)
                    cheater = true;

            if(platformManager.playerCollide(rectanglePlayer)){
                mediaPlayer.stop();
                if(Constants.MARIO) {
                    gOver = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.mgo);
                    gOver.start();
                }

                if(Constants.SONIC) {
                    gOver = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.sgo);
                    gOver.start();
                }

                Vibrator v = (Vibrator) Constants.VIB;
                // Vibrate for 500 milliseconds
                v.vibrate(200);
                gameOver = true;
                gameOvertime = System.currentTimeMillis();
                new GameOver().show(act.getFragmentManager(), "Game Over");
            }

            if (platformManager.playerCollide2(rectanglePlayer)){
                if(Constants.SONIC) {
                    if(score == 20){
                        getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.st);
                        getObj.start();
                    }
                    if(score%10 == 0 && score != 20) {
                        getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.srs);
                        getObj.start();
                    }
                }

                if(Constants.MARIO) {
                    if(score == 20){
                        getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.st);
                        getObj.start();
                    }
                    if(score%10 == 0 && score != 20){
                        getObj = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.mcs);
                        getObj.start();
                    }
                }

                gameOver = false;
            }
        }
    }

    private void drawGoText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
