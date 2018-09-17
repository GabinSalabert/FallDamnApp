package views;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Gabin Salabert on 08/03/2018.
 */

public interface Scene {

    public void update();
    public void lelDraw(Canvas canvas, GameView gm);
    public void terminate();
    public void catchTouch(MotionEvent event);
}
