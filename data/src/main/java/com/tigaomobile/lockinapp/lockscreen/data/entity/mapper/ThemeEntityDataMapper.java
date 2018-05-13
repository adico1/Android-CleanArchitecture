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

import com.tigaomobile.lockinapp.lockscreen.data.entity.ThemeEntity;
import com.tigaomobile.lockinapp.lockscreen.domain.Theme;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ThemeEntity} (in the data layer) to {@link Theme} in the
 * domain layer.
 */
@Singleton
public class ThemeEntityDataMapper {

  @Inject ThemeEntityDataMapper() {}

  /**
   * Transform a {@link ThemeEntity} into an {@link Theme}.
   *
   * @param themeEntity Object to be transformed.
   * @return {@link Theme} if valid {@link ThemeEntity} otherwise null.
   */
  public Theme transform(ThemeEntity themeEntity) {
    Theme theme = null;
    if (themeEntity != null) {
      theme = new Theme(themeEntity.getThemeId());
      theme.setCoverUrl(themeEntity.getCoverUrl());
      theme.setName(themeEntity.getName());
      theme.setDescription(themeEntity.getDescription());
      theme.setButtonsGroup(
          new ButtonsGroupEntityDataMapper().transform(themeEntity.getButtonsGroup())
      );
    }
    return theme;
  }

  /**
   * Transform a List of {@link ThemeEntity} into a Collection of {@link Theme}.
   *
   * @param themeEntityCollection Object Collection to be transformed.
   * @return {@link Theme} if valid {@link ThemeEntity} otherwise null.
   */
  public List<Theme> transform(Collection<ThemeEntity> themeEntityCollection) {
    final List<Theme> themeList = new ArrayList<>(20);
    for (ThemeEntity themeEntity : themeEntityCollection) {
      final Theme theme = transform(themeEntity);
      if (theme != null) {
        themeList.add(theme);
      }
    }
    return themeList;
  }
}
