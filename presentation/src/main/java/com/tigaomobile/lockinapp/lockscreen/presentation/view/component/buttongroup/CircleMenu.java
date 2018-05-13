package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

/**
 * Created by adi on 04/04/2018.
 * Description:
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

public class CircleMenu extends View {

    public static interface IMenuListener{

        public void onMenuClick(MenuCircle item);
        public void onMenuSelect(MenuCircle item);
    }

    public static class MenuCircle{
        private int x,y,radius;
        public int id;
        public String text;
        public int resource;

    }

    private Paint mainPaint;
    private Paint secondPaint;
    private Paint textPaint;
    private int radius_main_btn = 90;
    private int radius_action_btn = 90;
    private int radius_main = radius_main_btn;

    private int menuInnerPadding = 170;
    private int radialCircleRadius = 90;
    private int textPadding = 25;
    private double startAngle = Math.PI;
    private ArrayList<MenuCircle> elements;
    private IMenuListener listener;

    public void setListener(IMenuListener listener){
        this.listener = listener;
    }
    public void clear(){
        elements.clear();
        listener=null;
    }
    public CircleMenu(Context context) {
        super(context);
        init();
    }

    public CircleMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        elements = new ArrayList<>();
    }
    public void addMenuItem(String text,int id){
        MenuCircle item = new MenuCircle();
        item.id = id;
        item.text=text;
        elements.add(item);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainPaint = new Paint();
        mainPaint.setColor(Color.BLUE);
        secondPaint = new Paint();
        secondPaint.setColor(Color.DKGRAY);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
    }

    private float calcPixels(int dpSize) {
        DisplayMetrics dm = getResources().getDisplayMetrics() ;
        return applyDimension(COMPLEX_UNIT_DIP, dpSize, dm);
    }

    private void drawCircleResource(Canvas canvas, int centerX, int centerY, int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        if(bmp!=null){
            canvas.drawBitmap(bmp,
                    centerX - (bmp.getWidth() / 2),
                    centerY - (bmp.getHeight() / 2), null);
        }
    }

    private void drawOval(Canvas canvas, int centerX, int centerY, int width, int height) {
        int leftx, topy, rightx, bottomy;

        int half_width = width >> 1;
        int half_height = height >> 1;

        leftx = centerX - half_width;
        rightx = centerX + half_width;
        topy = centerY - half_height;
        bottomy = centerY + half_height;

        Paint mFillPaint = new Paint();
        Paint mStrokePaint = new Paint();
        RectF ovalBounds = new RectF(leftx, topy, rightx, bottomy);

        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(Color.WHITE);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.LTGRAY);
        mStrokePaint.setStrokeWidth(calcPixels(3));

        //mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        //ovalBounds.set(0, 0, mWidth, mHeight);
        canvas.drawOval(ovalBounds, mFillPaint);
        canvas.drawOval(ovalBounds, mStrokePaint);

    }

    private void drawButton(Canvas canvas, int centerX, int centerY, int resource ) {
        drawOval(canvas, centerX, centerY, 180, 180);
        //drawCircleResource(canvas, centerX, centerY, R.drawable.floating_action_button);
        drawCircleResource(canvas, centerX, centerY, resource);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = canvas.getWidth()/2 ;
        int centerY= canvas.getHeight()/2;

        drawButton(canvas, centerX, centerY, R.drawable.lock);

        for(int i=0;i<elements.size();i++){
            double angle =0;
            if(i==0){
                angle = startAngle;
            }else{
                angle = startAngle+(i * ((Math.PI) / (elements.size()-1)));
            }
            elements.get(i).x = (int) (centerX + Math.cos(angle)*(radius_main+menuInnerPadding+radialCircleRadius));
            elements.get(i).y = (int) (centerY + Math.sin(angle)*(radius_main+menuInnerPadding+radialCircleRadius));


            drawButton(canvas, elements.get(i).x, elements.get(i).y, elements.get(i).resource);
            //canvas.drawCircle( elements.get(i).x,elements.get(i).y,radialCircleRadius,secondPaint);

            //float tW = textPaint.measureText(elements.get(i).text);
            //canvas.drawText(elements.get(i).text,elements.get(i).x-tW/2,elements.get(i).y+radialCircleRadius+textPadding,textPaint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()== MotionEvent.ACTION_DOWN){
            for(MenuCircle mc : elements){
                double distance =  Math.hypot(event.getX()-mc.x,event.getY()-mc.y);
                if(distance<= radialCircleRadius){
                    //touched
                    if(listener!=null)
                        listener.onMenuClick(mc);
                    return true;
                }
            }

        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}