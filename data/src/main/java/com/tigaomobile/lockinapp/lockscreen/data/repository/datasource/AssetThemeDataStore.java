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
package com.tigaomobile.lockinapp.lockscreen.data.repository.datasource;

import com.tigaomobile.lockinapp.lockscreen.data.cache.ThemeCache;
import com.tigaomobile.lockinapp.lockscreen.data.entity.ThemeEntity;
import com.tigaomobile.lockinapp.lockscreen.data.filesystem.AssetReader;
import io.reactivex.Observable;
import java.util.List;

/**
 * {@link ThemeDataStore} implementation based on connections to the api (Cloud).
 */
class AssetThemeDataStore implements ThemeDataStore {

  private final AssetReader assetReader;
  private final ThemeCache themeCache;

  /**
   * Construct a {@link ThemeDataStore} based on connections to the api (Cloud).
   *
   * @param assetReader The {@link AssetReader} implementation to use.
   * @param themeCache A {@link ThemeCache} to cache data retrieved from the api.
   */
  AssetThemeDataStore(AssetReader assetReader, ThemeCache themeCache) {
    this.assetReader = assetReader;
    this.themeCache = themeCache;
  }

  @Override public Observable<List<ThemeEntity>> themeEntityList() {
    return this.assetReader.themeEntityList();
  }

  @Override public Observable<ThemeEntity> themeEntityDetails(final int themeId) {
    return this.assetReader.themeEntityById(themeId).doOnNext(AssetThemeDataStore.this.themeCache::put);
  }
}
