package model;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewManager;

import views.GameplayScene;

/**
 * Created by Gabin Salabert on 01/03/2018.
 */

public class Constants{
    public static int SCREEN_WIDTH = 1440; // Bon code pour récupérer les size dans MainActivity mais ne marche pas pour une raison liée à de la magie noire.
    public static int SCREEN_HEIGHT = 2392; // Donc je mets en dur, c'est déguelasse mais une force inconnue m'en empêche.

    public static boolean MARIO;
    public static boolean SONIC;

    public static Object VIB ;

    public static Intent INTENT;

    public static Context CURRENT_CONTEXT;

    public static GameplayScene CURRENT_GPS;

}

