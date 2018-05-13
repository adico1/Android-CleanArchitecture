/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.tigaomobile.lockinapp.lockscreen.presentation.view;

import com.tigaomobile.lockinapp.lockscreen.presentation.model.ThemeModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.UserModel;
import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a theme {@link ThemeModel}.
 */
public interface ScreenSliderPagerView extends LoadDataView {
  /**
   * Render a theme in the UI.
   *
   * @param themeModel The {@link ThemeModel} that will be shown.
   */
  void renderTheme(ThemeModel themeModel);
  void viewTheme(ThemeModel themeModel);

}
