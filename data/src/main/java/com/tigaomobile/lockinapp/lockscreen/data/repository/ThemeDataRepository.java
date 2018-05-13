/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tigaomobile.lockinapp.lockscreen.data.repository;

import com.tigaomobile.lockinapp.lockscreen.data.entity.mapper.ThemeEntityDataMapper;
import com.tigaomobile.lockinapp.lockscreen.data.repository.datasource.ThemeDataStore;
import com.tigaomobile.lockinapp.lockscreen.data.repository.datasource.ThemeDataStoreFactory;
import com.tigaomobile.lockinapp.lockscreen.domain.Theme;
import com.tigaomobile.lockinapp.lockscreen.domain.repository.ThemeRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link ThemeRepository} for retrieving theme data.
 */
@Singleton
public class ThemeDataRepository implements ThemeRepository {

  private final ThemeDataStoreFactory themeDataStoreFactory;
  private final ThemeEntityDataMapper themeEntityDataMapper;

  /**
   * Constructs a {@link ThemeRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param themeEntityDataMapper {@link ThemeEntityDataMapper}.
   */
  @Inject ThemeDataRepository(ThemeDataStoreFactory dataStoreFactory,
      ThemeEntityDataMapper themeEntityDataMapper) {
    this.themeDataStoreFactory = dataStoreFactory;
    this.themeEntityDataMapper = themeEntityDataMapper;
  }

  @Override public Observable<List<Theme>> themes() {
    //we always get all themes from the cloud
    final ThemeDataStore themeDataStore = this.themeDataStoreFactory.createAssetDataStore();
    return themeDataStore.themeEntityList().map(this.themeEntityDataMapper::transform);
  }

  @Override public Observable<Theme> theme(int themeId) {
    final ThemeDataStore themeDataStore = this.themeDataStoreFactory.create(themeId);
    return themeDataStore.themeEntityDetails(themeId).map(this.themeEntityDataMapper::transform);
  }
}
