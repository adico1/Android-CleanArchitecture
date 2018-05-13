package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.utils.Measurements;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adi on 11/04/2018.
 * Description:
 */

public class ButtonCircleLayout implements IButtonCollectionLayout {
    public static final String TAG = ButtonCircleLayout.class.getSimpleName();

    private int radius_main_btn = 90;
    private int radius_action_btn = 90;
    private int radius_main = radius_main_btn;

    private int menuInnerPadding = 170;
    private int radialCircleRadius = 90;
    private int textPadding = 25;
    private double startAngle = Math.PI;

    private Point getImageSize(View view, int resource) {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(
                ((Activity)view.getContext()).getResources(), resource, dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        return new Point(height, width);
    }

    private Properties getPositionProps(String position) {
        Properties props = new Properties();
        switch(position){
            case "top":
            case "bottom":
            case "left":
            case "right":
            case "top-left":
            case "top-right":
            case "bottom-left":
            case "bottom-right":
                props.put("angle", Math.PI);
                props.put("angle", Math.PI);
                break;
        }

        return props;

    }

    public void arrange(View view, JSONObject controllerBtnInfo, JSONArray lockViewActions, String position ) {
        position = "bottom";

        int num_buttons = lockViewActions.length();

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity)view.getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Log.i(TAG, "height: " + height);
        Log.i(TAG, "width: " + width);

        int controllerX = width / 2;
        int controllerY = height;

        String iconName = "";

        Measurements msrmnt = new Measurements(view.getContext());

        int halfImagePixels = (int)(msrmnt.calcPixels(60) / 2);
        int bottomPadding = (int)(msrmnt.calcPixels(80) / 2);
        int horizontalPadding = (int)(msrmnt.calcPixels(20) / 2);
        int actionButtonY = 0;
        int actionButtonX = 0;

        radius_main_btn = (int)(msrmnt.calcPixels(30));
        radius_action_btn = (int)(msrmnt.calcPixels(30));
        radius_main = radius_main_btn;

        menuInnerPadding = (int)(msrmnt.calcPixels(56));
        radialCircleRadius = (int)(msrmnt.calcPixels(30));
        textPadding = (int)(msrmnt.calcPixels(8));

        if(position.equals("bottom")) {
            actionButtonY = controllerY - halfImagePixels - bottomPadding;
            actionButtonX = controllerX - halfImagePixels - horizontalPadding;
        }

        Log.i(TAG, "height: " + height);

        Log.i(TAG, "halfImagePixels: " + halfImagePixels);
        Log.i(TAG, "bottomPadding: " + bottomPadding);

        Log.i(TAG, "actionButtonY: " + actionButtonY);

        Log.i(TAG, "actionButtonX: " + actionButtonX);


        try {
            //iconName = controllerBtnInfo.getString("icon");
//            iconName = "floating_action_button";
//
//            int imageDrawable = view.getContext().getResources().getIdentifier(
//                    iconName,
//                    "drawable",
//                    view.getContext().getPackageName());
//
//            Point imageSize = getImageSize(view, imageDrawable);

            controllerBtnInfo.put("x", actionButtonX );
            controllerBtnInfo.put("y", actionButtonY );
            //controllerBtnInfo.put("x", controllerX );
            //controllerBtnInfo.put("y", controllerY );
        } catch(Exception ex) {

        }


        for(int i=0;i<num_buttons;i++){
            double angle = 0;
            if(i==0){
                angle = startAngle;
            }else{
                angle = startAngle+(i * ((Math.PI) / (num_buttons-1)));
            }

            try {
                ((JSONObject)lockViewActions.get(i)).
                        put("x",
                                (int) (controllerX + Math.cos(angle)*
                                        (radius_main+menuInnerPadding+radialCircleRadius)) - halfImagePixels - horizontalPadding);
                ((JSONObject)lockViewActions.get(i)).
                        put("y",
                                (int) (controllerY + Math.sin(angle)*
                                        (radius_main+menuInnerPadding+radialCircleRadius)) - halfImagePixels - bottomPadding);
            } catch(Exception ex) {

            }
        }
    }
}
