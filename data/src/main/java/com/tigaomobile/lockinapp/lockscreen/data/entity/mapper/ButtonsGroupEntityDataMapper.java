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

import com.tigaomobile.lockinapp.lockscreen.data.entity.ButtonsGroupEntity;
import com.tigaomobile.lockinapp.lockscreen.domain.ButtonsGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ButtonsGroupEntity} (in the data layer) to {@link ButtonsGroup} in the
 * domain layer.
 */
@Singleton
public class ButtonsGroupEntityDataMapper {

  @Inject ButtonsGroupEntityDataMapper() {}

  /**
   * Transform a {@link ButtonsGroupEntity} into an {@link ButtonsGroup}.
   *
   * @param buttonsGroupEntity Object to be transformed.
   * @return {@link ButtonsGroup} if valid {@link ButtonsGroupEntity} otherwise null.
   */
  public ButtonsGroup transform(ButtonsGroupEntity buttonsGroupEntity) {
    ButtonsGroup buttonsGroup = null;
    if (buttonsGroupEntity != null) {
      buttonsGroup = new ButtonsGroup();

      buttonsGroup.setControlButton(
          new ButtonEntityDataMapper().transform(buttonsGroupEntity.getControlButton()
      ));

      buttonsGroup.setLocation(buttonsGroupEntity.getLocation());
      buttonsGroup.setStyle(buttonsGroupEntity.getStyle());
      buttonsGroup.setButtons(
          new ButtonEntityDataMapper().transform(buttonsGroupEntity.getButtons())
      );
    }
    return buttonsGroup;
  }

  /**
   * Transform a List of {@link ButtonsGroupEntity} into a Collection of {@link ButtonsGroup}.
   *
   * @param buttonsGroupEntityCollection Object Collection to be transformed.
   * @return {@link ButtonsGroup} if valid {@link ButtonsGroupEntity} otherwise null.
   */
  public List<ButtonsGroup> transform(Collection<ButtonsGroupEntity> buttonsGroupEntityCollection) {
    final List<ButtonsGroup> buttonsGroupList = new ArrayList<>(20);
    for (ButtonsGroupEntity buttonsGroupEntity : buttonsGroupEntityCollection) {
      final ButtonsGroup buttonsGroup = transform(buttonsGroupEntity);
      if (buttonsGroup != null) {
        buttonsGroupList.add(buttonsGroup);
      }
    }
    return buttonsGroupList;
  }
}
