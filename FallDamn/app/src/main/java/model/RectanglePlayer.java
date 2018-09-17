package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.gasalabert.falldown.R;

import static model.PlatformManager.score;

/**
 * Created by Gabin Salabert on 01/03/2018.
 */

public class RectanglePlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation rest;
    private Animation goRight;
    private Animation goLeft;
    private Animation goForward;
    private Animation goBackward;

    private Animation restSub;
    private Animation subRight;
    private Animation subLeft;
    private Animation subForward;
    private Animation subBackward;

    private Animation restSon;
    private Animation hurricanR;
    private Animation hurricanL;
    private Animation hurricanH;
    private Animation hurricanB;

    private Animation restSs;
    private Animation sshurricanR;
    private Animation sshurricanL;
    private Animation sshurricanH;
    private Animation sshurricanB;

    private Animation transformation;

    private boolean isTransformed = false;

    private AnimationManager animationManager;
    private AnimationManager animationManager2;
    //private AnimationManager animationManager3;


    public Rect getRectangle(){
        return rectangle;
    }

    public RectanglePlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap spinMario = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ah);
        Bitmap right1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.right1);
        Bitmap right2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.right2);
        Bitmap right3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.right3);
        Bitmap forward1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.forward1);
        Bitmap backward1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.backward1);

        Bitmap spinSub = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub4);
        Bitmap subr1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub1);
        Bitmap subr2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub2);
        Bitmap subr3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub3);
        Bitmap subf = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub4);
        Bitmap subb = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sub4);

        Bitmap spinSon = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spinsonic);
        Bitmap Son1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sonic1);
        Bitmap Son2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sonic2);
        Bitmap Son3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sonic3);
        Bitmap SonH = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sonich);
        Bitmap SonB = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.sonicb);

        Bitmap spinSs = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ssf);
        Bitmap ss1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ssr1);
        Bitmap ss2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ssr2);
        Bitmap ssH = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ssf);

        Bitmap transf1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tranf1);
        Bitmap transf2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.transf2);
        Bitmap transf3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.transf3);






        rest = new Animation(new Bitmap[]{spinMario}, 2);
        goRight = new Animation(new Bitmap[]{right1, right2, right3}, 0.5f);
        goForward = new Animation(new Bitmap[]{forward1}, 0.5f);
        goBackward = new Animation(new Bitmap[]{backward1}, 0.5f);

        restSub = new Animation(new Bitmap[]{spinSub}, 2);
        subRight = new Animation(new Bitmap[]{subr1, subr2, subr3}, 0.5f);
        subForward = new Animation(new Bitmap[]{subf}, 0.5f);

        restSon = new Animation(new Bitmap[]{spinSon}, 2);
        hurricanR = new Animation(new Bitmap[]{Son1, Son2, Son3}, 0.5f);
        hurricanH = new Animation(new Bitmap[]{SonH}, 0.5f);
        hurricanB = new Animation(new Bitmap[]{SonB}, 0.5f);

        transformation = new Animation(new Bitmap[]{transf1, transf2, transf3}, 150f);

        restSs = new Animation(new Bitmap[]{spinSs}, 2);
        sshurricanR = new Animation(new Bitmap[]{ss1, ss2}, 0.5f);
        sshurricanH = new Animation(new Bitmap[]{ssH}, 0.5f);




        Matrix m = new Matrix();
        m.preScale(-1, 1);
        right1 = Bitmap.createBitmap(right1, 0, 0, right1.getWidth(), right1.getHeight(), m, false);
        right2 = Bitmap.createBitmap(right2, 0, 0, right2.getWidth(), right2.getHeight(), m, false);
        Son1 = Bitmap.createBitmap(Son1, 0, 0, Son1.getWidth(), Son1.getHeight(), m, false);
        Son2 = Bitmap.createBitmap(Son2, 0, 0, Son2.getWidth(), Son2.getHeight(), m, false);
        Son3 = Bitmap.createBitmap(Son3, 0, 0, Son3.getWidth(), Son3.getHeight(), m, false);
        ss1 = Bitmap.createBitmap(ss1, 0, 0, ss1.getWidth(), ss1.getHeight(), m, false);
        ss2 = Bitmap.createBitmap(ss2, 0, 0, ss2.getWidth(), ss2.getHeight(), m, false);
        subr1 = Bitmap.createBitmap(subr1, 0, 0, subr1.getWidth(), subr1.getHeight(), m, false);
        subr2 = Bitmap.createBitmap(subr2, 0, 0, subr2.getWidth(), subr2.getHeight(), m, false);
        subr3 = Bitmap.createBitmap(subr3, 0, 0, subr3.getWidth(), subr3.getHeight(), m, false);




        goLeft = new Animation(new Bitmap[]{right1, right2, right3}, 0.5f);
        hurricanL = new Animation(new Bitmap[]{Son1, Son2, Son3}, 0.5f);
        sshurricanL = new Animation(new Bitmap[]{ss1, ss2}, 0.5f);
        sshurricanB = new Animation(new Bitmap[]{ssH}, 0.5f);
        subLeft =  new Animation(new Bitmap[]{subr1, subr2, subr3}, 0.5f);
        subBackward = new Animation(new Bitmap[]{subf}, 0.5f);



        if(Constants.MARIO) {
            animationManager2 = new AnimationManager(new Animation[]{restSub, subRight, subLeft, subForward, subBackward});
            animationManager = new AnimationManager(new Animation[]{rest, goRight, goLeft, goForward, goBackward});
        }

        if(Constants.SONIC) {
            //animationManager3 = new AnimationManager(new model.Animation[]{transformation});
            animationManager2 = new AnimationManager(new Animation[]{restSs, sshurricanR, sshurricanL, sshurricanH, sshurricanB});
            animationManager = new AnimationManager(new Animation[]{restSon, hurricanR, hurricanL, hurricanH, hurricanB});
        }


    }


    @Override
    public void draw(Canvas canvas){
//      Paint paint = new Paint();
//      paint.setColor(color);
//      canvas.drawRect(rectangle, paint);
        if(score>= 20) {
            animationManager2.draw(canvas, rectangle);
        }
        else
            animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update(){
        animationManager.update();
    }

    public void update(Point point) {
        int state = 0;
        float oldLeft = rectangle.left;
        float oldTop = rectangle.top;

        //l t r b
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

        if (rectangle.top - oldTop > 5) {
            state = 4; //3 is backward
        }

        else if (rectangle.top - oldTop < -5) {
            state = 3;//4 is forward
        }

        //if ok then player is moving to the right
        if (rectangle.left - oldLeft > 5) {
            state = 1; //1 is right
        }

        else if (rectangle.left - oldLeft < -5) {
            state = 2;//2 is left
        }


        if (score >= 20){
            animationManager2.playAnim(state);
            animationManager2.update();
        }

        else {
            animationManager.playAnim(state);
            animationManager.update();
        }
    }
}
