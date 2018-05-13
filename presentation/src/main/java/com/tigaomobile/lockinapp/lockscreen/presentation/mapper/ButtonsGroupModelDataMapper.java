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

import com.tigaomobile.lockinapp.lockscreen.domain.ButtonsGroup;
import com.tigaomobile.lockinapp.lockscreen.domain.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.PerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ButtonsGroupModel;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.UserModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;

/**
 * Mapper class used to transform {@link User} (in the domain layer) to {@link UserModel} in the
 * presentation layer.
 */
@PerActivity
public class ButtonsGroupModelDataMapper {

  @Inject
  public ButtonsGroupModelDataMapper() {}

  /**
   * Transform a {@link ButtonsGroup} into an {@link ButtonsGroupModel}.
   *
   * @param buttonsGroup Object to be transformed.
   * @return {@link ButtonsGroupModel}.
   */
  public ButtonsGroupModel transform(ButtonsGroup buttonsGroup) {
    if (buttonsGroup == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final ButtonsGroupModel buttonsGroupModel = new ButtonsGroupModel();
    buttonsGroupModel.setControlButton(new ButtonModelDataMapper().transform(buttonsGroup.getControlButton()));
    buttonsGroupModel.setLocation(buttonsGroup.getLocation());
    buttonsGroupModel.setStyle(buttonsGroup.getStyle());
    buttonsGroupModel.setButtons(new ButtonModelDataMapper().transform(buttonsGroup.getButtons()));

    return buttonsGroupModel;
  }

  /**
   * Transform a Collection of {@link ButtonsGroup} into a Collection of {@link ButtonsGroupModel}.
   *
   * @param buttonsGroupsCollection Objects to be transformed.
   * @return List of {@link ButtonsGroupModel}.
   */
  public Collection<ButtonsGroupModel> transform(Collection<ButtonsGroup> buttonsGroupsCollection) {
    Collection<ButtonsGroupModel> buttonsGroupModelsCollection;

    if (buttonsGroupsCollection != null && !buttonsGroupsCollection.isEmpty()) {
      buttonsGroupModelsCollection = new ArrayList<>();
      for (ButtonsGroup buttonsGroup : buttonsGroupsCollection) {
        buttonsGroupModelsCollection.add(transform(buttonsGroup));
      }
    } else {
      buttonsGroupModelsCollection = Collections.emptyList();
    }

    return buttonsGroupModelsCollection;
  }
}
