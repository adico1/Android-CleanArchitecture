package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.actions.IAction;
import javax.inject.Inject;

/**
 * Created by adi on 12/02/2018.
 * Description:
 * ButtonGroup is used to manage a group of buttons controlling the lock screen
 */

public class ButtonGroup extends RelativeLayout implements View.OnClickListener {
    public final String TAG = ButtonGroup.class.getSimpleName();

    private Config config;

    public void setConfig(Config config) {
        this.config = config;
        init(this.getContext());
    }

    // Step 2 - This variable represents the listener passed in by the owning object
    // The listener must implement the events interface and passes messages up to the parent.
    private ButtonGroupListener listener;

//    private ImageButton cameraButton = null;
//    private ImageButton unlockButton = null;
//    private ImageButton whatsappButton = null;
//    private ImageButton chromeButton = null;
//    private ImageButton phoneButton = null;
//    private ImageButton fbButton = null;

    private double startAngle = Math.PI;
    private int radius_main_btn = 90;
    private int radius_action_btn = 90;
    private int radius_main = radius_main_btn;

    private int menuInnerPadding = 170;
    private int radialCircleRadius = 90;

    int TOP_ID, NUM_BUTTONS;
    ButtonGroupCollections buttons;

    public ButtonGroup(Context context) {
        super(context);

    }
    public ButtonGroup(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    public ButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Assign the listener implementing events interface that will receive the events
    public void setCustomObjectListener(ButtonGroupListener listener) {
        this.listener = listener;
    }


//    public void drawInCircle() {
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
//        params.leftMargin = x;
//        params.topMargin = y;
//
//        int iHeight = getHeight();
//        int iWidth = getWidth();
//        int iNumberOfButtons = 10;
//        double dIncrease = Math.PI * 2 / iNumberOfButtons,
//                dAngle = 0,
//                x = 0,
//                y = 0;
//
//        for( int i = 0; i < iNumberOfButtons; i++ )
//        {
//            x = 200 * Math.cos( dAngle ) + iWidth/2;
//            y = 200 * Math.sin( dAngle ) + iHeight/2;
//            dAngle += dIncrease;
//            Button xButton = new Button(m_xContext);
//            xButton.setAdjustViewBounds(true);
//            xButton.setBackgroundResource(R.drawable.some_image);
//            LayoutParams xParams = (RelativeLayout.LayoutParams)xButton.getLayoutParams();
//            if( xParams == null )
//            {
//                xParams = new RelativeLayout.LayoutParams( xButton.getBackground().getIntrinsicWidth(), xButton.getBackground().getIntrinsicHeight() );
//            }
//            xParams.leftMargin = (int)x - ( xButton.getBackground().getIntrinsicWidth() / 2 ) ;
//            xParams.topMargin =  (int)y - ( xButton.getBackground().getIntrinsicHeight() / 2 );
//            addView( xButton, xParams );
//        }
//    }

    protected void calcPosOnShape(int i, ButtonGroupItem button, int butttonsCount) {
        int centerX = this.getWidth() / 2;
        int centerY = this.getHeight() / 2;

        double angle = 0;
        if (i == 0) {

            angle = startAngle;

        } else {

            angle = startAngle + (i * ((Math.PI) / (butttonsCount - 1)));

        }

        button.set_x((int) (centerX + Math.cos(angle) * (radius_main + menuInnerPadding + radialCircleRadius)));
        button.set_y((int) (centerY + Math.sin(angle) * (radius_main + menuInnerPadding + radialCircleRadius)));

        //canvas.drawCircle( elements.get(i).x,elements.get(i).y,radialCircleRadius,secondPaint);

        //float tW = textPaint.measureText(elements.get(i).text);
        //canvas.drawText(elements.get(i).text,elements.get(i).x-tW/2,elements.get(i).y+radialCircleRadius+textPadding,textPaint);
    }

    public void drawButton(RelativeLayout root, int id, ButtonGroupItem buttonInfo, int visibility) {
        String buttonIcon = "";
        ImageButton button = new ImageButton(getContext());

        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        layoutParams.bottomMargin = 70; // TODO: NEED 70dp

        button.setLayoutParams(layoutParams);

        // R.id won't be generated for us, so we need to create one
        button.setId(id);
        button.setTag(buttonInfo);
        button.setLayoutParams(
                new ButtonGroup.LayoutParams(
                        ButtonGroup.LayoutParams.WRAP_CONTENT,
                        ButtonGroup.LayoutParams.WRAP_CONTENT));

        button.setClickable(true);
        button.setFocusable(true);
        try {
            button.setX(buttonInfo.get_x());
            button.setY(buttonInfo.get_y());

            buttonIcon = buttonInfo.get_icon();
        } catch(Exception ex) {

        }

        int imageDrawable = getContext().getResources().getIdentifier(
                buttonIcon,
                "drawable",
                getContext().getPackageName());

        button.setImageResource(imageDrawable);
        button.setBackgroundResource(R.drawable.floating_action_button);
        button.setContentDescription("Action Button");
        button.setVisibility(visibility);
        // add our event handler (less memory than an anonymous inner class)
        // button.setOnClickListener(this);

        root.addView(button);

    }


    public void drawButtons() {
        // add generated layouts to root layout view
        RelativeLayout root = this.findViewById(R.id.root_layout);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

            TOP_ID = com.tigaomobile.lockinapp.lockscreen.presentation.view.utils.View.generateViewId();

        } else {

            TOP_ID = View.generateViewId();

        }

        root.setId(TOP_ID);

        drawButton(root, TOP_ID + 1,
                buttons.getControllerBtn(),
                VISIBLE);

        // create buttons in a loop
        for (int i = 0; i < NUM_BUTTONS; i+=1) {
            drawButton(root, TOP_ID + 2 + i, buttons.getOrderedListOfButtons().get(i), INVISIBLE);
        }
    }

    private void init(Context context)
    {
        LayoutInflater mInflater = LayoutInflater.from(context);
        // set null or default listener or accept as argument to constructor
        this.listener = null;

        View v = mInflater.inflate(R.layout.button_group, this, true);
        buttons = new ButtonGroupCollections(context, this, this.config);
        NUM_BUTTONS = buttons.length();

        drawButtons();

        ImageButton controlButton = v.findViewById(TOP_ID + 1);
        controlButton.setOnTouchListener(new MyTouchListener(v));

        for(int i=0; i < NUM_BUTTONS; i += 1 ) {
            ImageButton actionButton = v.findViewById(TOP_ID + i + 2);
            if(actionButton!=null){
                actionButton.setOnDragListener(new MyDragListener(v));
            }
        }

    }

    @Override
    public void onClick(View v) {

    }

    private int currentVisibility = INVISIBLE;
    private void toggleButtons(View v) {
        currentVisibility = currentVisibility == INVISIBLE ? VISIBLE : INVISIBLE;

        toggleButtons(v, currentVisibility);
    }
    private void toggleButtons(View v, int visibility) {
        currentVisibility = visibility;

        for(int i=0; i < NUM_BUTTONS; i += 1 ) {
            int elemId = TOP_ID + 2 + i;

            ImageButton actionButton = v.findViewById(elemId);
            if(actionButton==null) {
                Log.e(TAG, "elemId not found: " + elemId);
                break;
            }

            actionButton.setVisibility(currentVisibility);
        }
    }
    private void showButtons(View v) {
        toggleButtons(v, VISIBLE);
    }
    private void hideButtons(View v) {
        toggleButtons(v, INVISIBLE);
    }
    private final class MyTouchListener implements OnTouchListener {
        private View parentView;
        public MyTouchListener(View _parentView) {
            parentView = _parentView;
        }
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();

            if (action == MotionEvent.ACTION_DOWN) {
                // unlock home button and then screen on button press
                showButtons(parentView);

                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //actionButton.setVisibility(INVISIBLE);

                view.setVisibility(View.INVISIBLE);
                return true;
            }  else if(action == MotionEvent.ACTION_UP ) {

                view.performClick();
                return true;
            } else {
                return false;
            }
        }
    }

    public interface ButtonGroupListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void onCommand(IAction command);
    }

    class MyDragListener implements OnDragListener {
//        Drawable enterShape = getResources().getDrawable(
//                R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        private View parentView;
        public MyDragListener(View _parentView) {
            parentView = _parentView;
        }

        @Override
        public boolean onDrag(final View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    final View droppedView = (View) event.getLocalState();
                    droppedView.post(new Runnable(){
                        @Override
                        public void run() {
                            droppedView.setVisibility(View.VISIBLE);

                            hideButtons(parentView);
                        }
                    });

                    break;
                case DragEvent.ACTION_DROP:
                    //Point touchPosition = getTouchPositionFromDragEvent(v, event);
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    //ViewGroup owner = (ViewGroup) view.getParent();
                    //owner.removeView(view);

                    if (listener != null) {
                        ButtonGroupItem button = (ButtonGroupItem)v.getTag();

                        IAction command = button.get_action();

                        Log.i("ButtonGroup::onDrag", "command: " + command);
                        listener.onCommand(command); // <---- fire listener here
                    }

                    // unlock home button and then screen on button press
//                    actionButton.setVisibility(VISIBLE);
//
//                    cameraButton.setVisibility(INVISIBLE);
//                    unlockButton.setVisibility(INVISIBLE);
//                    whatsappButton.setVisibility(INVISIBLE);
//                    chromeButton.setVisibility(INVISIBLE);
//                    phoneButton.setVisibility(INVISIBLE);
//                    fbButton.setVisibility(INVISIBLE);


                    // Log.i("onDrag:ACTION_DROP","OwnerId:" + dropTarget.getResources().getResourceEntryName(dropTarget.getId()));

                    //LinearLayout container = (LinearLayout) v;
                    //container.addView(view);
                    //view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    final View droppedView2 = (View) event.getLocalState();
                    droppedView2.post(new Runnable(){
                        @Override
                        public void run() {
                            droppedView2.setVisibility(View.VISIBLE);

                            hideButtons(parentView);
                        }
                    });

                    // unlock home button and then screen on button press
                    // actionButton.setVisibility(VISIBLE);

                default:
                    break;
            }
            return true;
        }

//        /**
//         * @param item  the view that received the drag event
//         * @param event the event from {@link android.view.View.OnDragListener#onDrag(View, DragEvent)}
//         * @return the coordinates of the touch on x and y axis relative to the screen
//         */
//        public Point getTouchPositionFromDragEvent(View item, DragEvent event) {
//            Rect rItem = new Rect();
//            item.getGlobalVisibleRect(rItem);
//            return new Point(rItem.left + Math.round(event.getX()), rItem.top + Math.round(event.getY()));
//        }
//
//        public boolean isTouchInsideOfView(View view, Point touchPosition) {
//            Rect rScroll = new Rect();
//            view.getGlobalVisibleRect(rScroll);
//            return isTouchInsideOfRect(touchPosition, rScroll);
//        }

        public boolean isTouchInsideOfRect(Point touchPosition, Rect rScroll) {
            return touchPosition.x > rScroll.left && touchPosition.x < rScroll.right //within x axis / width
                    && touchPosition.y > rScroll.top && touchPosition.y < rScroll.bottom; //withing y axis / height
        }
    }
}
