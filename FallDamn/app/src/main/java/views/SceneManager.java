package views;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Gabin Salabert on 08/03/2018.
 */

public class SceneManager {
    private static ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene());
    }

    public void catchTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).catchTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void lelDraw(Canvas canvas, GameView gm){
        scenes.get(ACTIVE_SCENE).lelDraw(canvas ,gm);
    }
}
