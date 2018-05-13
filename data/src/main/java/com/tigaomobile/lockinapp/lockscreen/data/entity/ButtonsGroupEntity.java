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
package com.tigaomobile.lockinapp.lockscreen.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * User Entity used in the data layer.
 */
public class ButtonsGroupEntity {

  @SerializedName("controlButton")
  private ButtonEntity controlButton;

  @SerializedName("location")
  private String location;

  @SerializedName("style")
  private String style;

  @SerializedName("buttons")
  private List<ButtonEntity> buttons;


  public ButtonsGroupEntity() {
    //empty
  }

  public ButtonEntity getControlButton() {
    return controlButton;
  }

  public void setControlButton(ButtonEntity controlButton) {
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

  public List<ButtonEntity> getButtons() {
    return buttons;
  }

  public void setButtons(List<ButtonEntity> buttons) {
    this.buttons = buttons;
  }
}
