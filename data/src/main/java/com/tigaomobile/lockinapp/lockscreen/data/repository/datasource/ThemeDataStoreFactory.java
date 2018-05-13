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
package com.tigaomobile.lockinapp.lockscreen.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import com.tigaomobile.lockinapp.lockscreen.data.cache.ThemeCache;
import com.tigaomobile.lockinapp.lockscreen.data.entity.mapper.ThemeEntityJsonMapper;
import com.tigaomobile.lockinapp.lockscreen.data.filesystem.AssetReader;
import com.tigaomobile.lockinapp.lockscreen.data.filesystem.AssetReaderImpl;
import com.tigaomobile.lockinapp.lockscreen.data.net.RestApi;
import com.tigaomobile.lockinapp.lockscreen.data.net.RestApiImpl;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link ThemeDataStore}.
 */
@Singleton
public class ThemeDataStoreFactory {

  private final Context context;
  private final ThemeCache themeCache;

  @Inject ThemeDataStoreFactory(@NonNull Context context, @NonNull ThemeCache themeCache) {
    this.context = context.getApplicationContext();
    this.themeCache = themeCache;
  }

  /**
   * Create {@link ThemeDataStore} from a theme id.
   */
  public ThemeDataStore create(int themeId) {
    ThemeDataStore themeDataStore;

    //if (!this.themeCache.isExpired() && this.themeCache.isCached(themeId)) {
    //  themeDataStore = new DiskThemeDataStore(this.themeCache);
    //} else {
      themeDataStore = createAssetDataStore();
    //}

    return themeDataStore;
  }

  /**
   * Create {@link ThemeDataStore} to retrieve data from the Cloud.
   */
  public ThemeDataStore createAssetDataStore() {
    final ThemeEntityJsonMapper themeEntityJsonMapper = new ThemeEntityJsonMapper();
    final AssetReader assetReader = new AssetReaderImpl(this.context, themeEntityJsonMapper);

    return new AssetThemeDataStore(assetReader, this.themeCache);
  }
}
