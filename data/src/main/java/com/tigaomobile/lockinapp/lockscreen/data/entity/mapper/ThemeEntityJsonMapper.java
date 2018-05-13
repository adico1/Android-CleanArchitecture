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
package com.tigaomobile.lockinapp.lockscreen.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tigaomobile.lockinapp.lockscreen.data.entity.ThemeEntity;
import com.tigaomobile.lockinapp.lockscreen.data.entity.UserEntity;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class ThemeEntityJsonMapper {

  private final Gson gson;

  @Inject
  public ThemeEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link ThemeEntity}.
   *
   * @param themeJsonResponse A json representing a theme profile.
   * @return {@link ThemeEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public ThemeEntity transformThemeEntity(String themeJsonResponse) throws JsonSyntaxException {
    final Type themeEntityType = new TypeToken<ThemeEntity>() {}.getType();
    return this.gson.fromJson(themeJsonResponse, themeEntityType);
  }

  /**
   * Transform from valid json string to List of {@link ThemeEntity}.
   *
   * @param themeListJsonResponse A json representing a collection of themes.
   * @return List of {@link ThemeEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<ThemeEntity> transformThemeEntityCollection(String themeListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfThemeEntityType = new TypeToken<List<ThemeEntity>>() {}.getType();
    return this.gson.fromJson(themeListJsonResponse, listOfThemeEntityType);
  }
}
