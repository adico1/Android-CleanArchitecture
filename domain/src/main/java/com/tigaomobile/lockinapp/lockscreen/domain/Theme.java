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
package com.tigaomobile.lockinapp.lockscreen.domain;


/**
 * Class that represents a User in the domain layer.
 */
public class Theme {

  private final int themeId;

  public Theme(int themeId) {
    this.themeId = themeId;
  }

  private String coverUrl;
  private String name;
  private String description;
  private ButtonsGroup buttonsGroup;

  public int getThemeId() {
    return themeId;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
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

  public void setDescription(String description) {
    this.description = description;
  }

  public ButtonsGroup getButtonsGroup() {
    return buttonsGroup;
  }

  public void setButtonsGroup(ButtonsGroup buttonsGroup) {
    this.buttonsGroup = buttonsGroup;
  }
}
