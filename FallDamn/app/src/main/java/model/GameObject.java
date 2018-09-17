package model;

import android.graphics.Canvas;

/**
 * Created by Gabin Salabert on 01/03/2018.
 */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
