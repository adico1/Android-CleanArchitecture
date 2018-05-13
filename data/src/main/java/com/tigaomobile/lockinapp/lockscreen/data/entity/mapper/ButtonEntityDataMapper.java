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

import com.tigaomobile.lockinapp.lockscreen.data.entity.ButtonEntity;
import com.tigaomobile.lockinapp.lockscreen.domain.Button;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ButtonEntity} (in the data layer) to {@link Button} in the
 * domain layer.
 */
@Singleton
public class ButtonEntityDataMapper {

  @Inject ButtonEntityDataMapper() {}

  /**
   * Transform a {@link ButtonEntity} into an {@link Button}.
   *
   * @param buttonEntity Object to be transformed.
   * @return {@link Button} if valid {@link ButtonEntity} otherwise null.
   */
  public Button transform(ButtonEntity buttonEntity) {
    Button button = null;
    if (buttonEntity != null) {
      button = new Button();
      button.setIcon(buttonEntity.getIcon());
      button.setAction(buttonEntity.getAction());
    }
    return button;
  }

  /**
   * Transform a List of {@link ButtonEntity} into a Collection of {@link Button}.
   *
   * @param buttonEntityCollection Object Collection to be transformed.
   * @return {@link Button} if valid {@link ButtonEntity} otherwise null.
   */
  public List<Button> transform(Collection<ButtonEntity> buttonEntityCollection) {
    final List<Button> buttonList = new ArrayList<>(20);
    for (ButtonEntity buttonEntity : buttonEntityCollection) {
      final Button button = transform(buttonEntity);
      if (button != null) {
        buttonList.add(button);
      }
    }
    return buttonList;
  }
}
