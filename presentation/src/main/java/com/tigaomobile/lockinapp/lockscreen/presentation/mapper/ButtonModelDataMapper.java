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

import com.tigaomobile.lockinapp.lockscreen.domain.Button;
import com.tigaomobile.lockinapp.lockscreen.domain.User;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.PerActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.model.ButtonModel;
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
public class ButtonModelDataMapper {

  @Inject
  public ButtonModelDataMapper() {}

  /**
   * Transform a {@link Button} into an {@link ButtonModel}.
   *
   * @param button Object to be transformed.
   * @return {@link ButtonModel}.
   */
  public ButtonModel transform(Button button) {
    if (button == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final ButtonModel buttonModel = new ButtonModel();
    buttonModel.setAction(button.getAction());
    buttonModel.setIcon(button.getIcon());

    return buttonModel;
  }

  /**
   * Transform a Collection of {@link Button} into a Collection of {@link ButtonModel}.
   *
   * @param buttonsCollection Objects to be transformed.
   * @return List of {@link ButtonModel}.
   */
  public Collection<ButtonModel> transform(Collection<Button> buttonsCollection) {
    Collection<ButtonModel> buttonModelsCollection;

    if (buttonsCollection != null && !buttonsCollection.isEmpty()) {
      buttonModelsCollection = new ArrayList<>();
      for (Button button : buttonsCollection) {
        buttonModelsCollection.add(transform(button));
      }
    } else {
      buttonModelsCollection = Collections.emptyList();
    }

    return buttonModelsCollection;
  }
}
