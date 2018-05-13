package com.tigaomobile.lockinapp.lockscreen.presentation.view.component.buttongroup;

/**
 * Created by adi on 11/04/2018.
 * Description:
 */

class ButtonCollectionLayoutFactory {
    public static IButtonCollectionLayout create(String location, String style) {
        location = location.isEmpty() ? "bottom" : location;
        style = style.isEmpty() ? "circle" : style;

        return new ButtonCircleLayout();
    }
}
