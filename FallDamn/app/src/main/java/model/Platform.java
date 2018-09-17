package model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Gabin Salabert on 01/03/2018.
 */

public class Platform implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private Rect rectangle3;
    private int color;

    private boolean hasBeenTouch = false;
    public boolean hasBeenTouch2 = false;
    private boolean displayed = false;


    public Rect getRectangle(){
        return rectangle;
    }
    public Rect getRectangle2(){
        return rectangle3;
    }
    public Rect getRectangle3(){
        return rectangle2;
    }

    public void incY(float y){
        rectangle.top += y;
        rectangle.bottom += y;
        rectangle2.top += y;
        rectangle2.bottom += y;
        rectangle3.top += y;
        rectangle3.bottom += y;
    }

    public Platform(int rectHeight, int color, int startX, int startY, int playerGap){
        //l t r b
        rectangle = new Rect(0, startY, startX+(30), startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap + 30, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
        rectangle3 = new Rect(startX+(30), startY, startX + playerGap + 30, startY + rectHeight);
        this.color = color;
    }

    public boolean playerCollision(RectanglePlayer player){
        //Check rectangle's corners
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }

    public boolean playerCollision2(RectanglePlayer player){
        //Check rectangle's corners
        if(Rect.intersects(rectangle3, player.getRectangle()) && (!Rect.intersects(rectangle, player.getRectangle()) && !Rect.intersects(rectangle2, player.getRectangle()))){
            hasBeenTouch = true;
            return true;
        }
        return false;
    }


    public void lolDraw(Canvas canvas, Drawable d, Drawable d2, Drawable d3){
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint2.setColor(Color.GREEN);
        paint.setColor(color);

        d2.draw(canvas);
        d3.draw(canvas);
        //canvas.drawRect(rectangle, paint);
        //canvas.drawRect(rectangle2, paint);
        if(!hasBeenTouch)
            d.draw(canvas);
    }

    @Override
    public void draw(Canvas canvas){

    }

    @Override
    public void update(){

    }
}
