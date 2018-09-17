package model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


/**
 * Created by Gabin Salabert on 01/03/2018.
 */

public class PlatformManager {
    //higher index = lower displayed = higher y value
    private ArrayList<Platform> platforms;
    private int playerGap;
    private int platformGap;
    private int platformHeight;
    private int color;
    private int know;
    private boolean emeralds = false;

    private long startTime;
    private long initTime;

    public static int score = 0;
    public boolean limit = false;

    public PlatformManager(int playerGap, int platformGap, int platformHeight, int color){
        this.playerGap = playerGap;
        this.platformGap = platformGap;
        this.platformHeight = platformHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        platforms = new ArrayList<>();

        populatePlatforms();
    }

    public ArrayList<Platform> getPlatforms(){
        return platforms;
    }

    public boolean playerCollide(RectanglePlayer player){
        for(Platform pl : platforms){
            if (pl.playerCollision(player)){
                return true;
            }
        }
        return false;
    }

    public boolean playerCollide2(RectanglePlayer player){
        for(Platform pl : platforms){
            limit = false;
            if (pl.playerCollision2(player) && limit == false){
                limit = true;
                if(!pl.hasBeenTouch2) {
                    score++;
                    pl.hasBeenTouch2 = true;
                }
                return true;
            }
        }
        return false;
    }


    private void populatePlatforms(){
        int currentY = -7* Constants.SCREEN_HEIGHT/4;
        while (currentY < 0){
            //Hole where the player can fall
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            if(xStart > 800){
                xStart = 650;
            }
            platforms.add(new Platform(platformHeight, color, xStart, currentY, playerGap));
            currentY += platformHeight + platformGap;
        }
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        //scalling speed
        float speed = (float)(Math.sqrt(1+(startTime - initTime)/2000.0))*Constants.SCREEN_HEIGHT/(10000.0f);
        for(Platform pl : platforms){
            pl.incY(speed * elapsedTime);
        }
        if (platforms.get(platforms.size() -1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            platforms.add(0, new Platform(platformHeight, color, xStart, platforms.get(0).getRectangle().top - platformHeight - platformGap, playerGap));
            platforms.remove(platforms.size() -1);
        }

    }

    public void draw(Canvas canvas, Drawable d, Drawable d2, Drawable d3, Drawable d4){
        for(int j=0; j<platforms.size();j++) {
            d.setBounds(platforms.get(j).getRectangle2());
            d4.setBounds(platforms.get(j).getRectangle2());
            d2.setBounds(platforms.get(j).getRectangle());
            d3.setBounds(platforms.get(j).getRectangle3());
            System.out.println(j);
            if(score == 19) {
                platforms.get(j).lolDraw(canvas, d4, d2, d3);
            }
            else
                platforms.get(j).lolDraw(canvas, d, d2, d3);


        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText("" + score, 50, 100, paint);
    }
}
