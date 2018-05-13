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

/**
 * User Entity used in the data layer.
 */
public class ThemeEntity {

  @SerializedName("id")
  private int themeId;

  @SerializedName("cover_url")
  private String coverUrl;

  @SerializedName("name")
  private String name;

  @SerializedName("description")
  private String description;

  @SerializedName("buttonGroup")
  private ButtonsGroupEntity buttonsGroup;

  public ThemeEntity() {
    //empty
  }

  public int getThemeId() {
    return themeId;
  }

  public void setThemeId(int themeId) {
    this.themeId = themeId;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public ButtonsGroupEntity getButtonsGroup() {
    return buttonsGroup;
  }

  public void setButtonsGroup(ButtonsGroupEntity buttonsGroup) {
    this.buttonsGroup = buttonsGroup;
  }


}
