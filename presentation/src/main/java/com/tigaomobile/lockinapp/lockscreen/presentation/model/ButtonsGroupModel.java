/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tigaomobile.lockinapp.lockscreen.presentation.model;

import com.tigaomobile.lockinapp.lockscreen.domain.Button;
import java.util.Collection;
import java.util.List;

/**
 * User Entity used in the data layer.
 */
public class ButtonsGroupModel {

  private ButtonModel controlButton;
  private String location;
  private String style;
  private Collection<ButtonModel> buttons;

  public ButtonsGroupModel() {
    //empty
  }

  public ButtonModel getControlButton() {
    return controlButton;
  }

  public void setControlButton(ButtonModel controlButton) {
    this.controlButton = controlButton;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public Collection<ButtonModel> getButtons() {
    return buttons;
  }

  public void setButtons(Collection<ButtonModel> buttons) {
    this.buttons = buttons;
  }
}
