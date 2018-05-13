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
package com.tigaomobile.lockinapp.lockscreen.presentation.mapper;

import com.tigaomobile.lockinapp.lockscreen.domain.Theme;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.PerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ThemeModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Theme} (in the domain layer) to {@link ThemeModel} in the
 * presentation layer.
 */
@PerActivity
public class ThemeModelDataMapper {

  @Inject
  public ThemeModelDataMapper() {}

  /**
   * Transform a {@link Theme} into an {@link ThemeModel}.
   *
   * @param theme Object to be transformed.
   * @return {@link ThemeModel}.
   */
  public ThemeModel transform(Theme theme) {
    if (theme == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final ThemeModel themeModel = new ThemeModel(theme.getThemeId());
    themeModel.setCoverUrl(theme.getCoverUrl());
    themeModel.setName(theme.getName());
    themeModel.setDescription(theme.getDescription());
    themeModel.setButtonsGroup(new ButtonsGroupModelDataMapper().transform(theme.getButtonsGroup()));

    return themeModel;
  }

  /**
   * Transform a Collection of {@link Theme} into a Collection of {@link ThemeModel}.
   *
   * @param themesCollection Objects to be transformed.
   * @return Collection of {@link ThemeModel}.
   */
  public Collection<ThemeModel> transform(Collection<Theme> themesCollection) {
    Collection<ThemeModel> themeModelsCollection;

    if (themesCollection != null && !themesCollection.isEmpty()) {
      themeModelsCollection = new ArrayList<>();
      for (Theme theme : themesCollection) {
        themeModelsCollection.add(transform(theme));
      }
    } else {
      themeModelsCollection = Collections.emptyList();
    }

    return themeModelsCollection;
  }
}
