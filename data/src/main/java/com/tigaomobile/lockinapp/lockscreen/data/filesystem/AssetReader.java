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
package com.tigaomobile.lockinapp.lockscreen.data.filesystem;

import com.tigaomobile.lockinapp.lockscreen.data.R;
import com.tigaomobile.lockinapp.lockscreen.data.entity.ThemeEntity;
import com.tigaomobile.lockinapp.lockscreen.data.entity.UserEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Asset Reader for retrieving data from the application asset folder.
 */
public interface AssetReader {
  // String API_BASE_URL =
  //    "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";
  //
  /** Api url for getting all users */
  int RAW_ASSET_GET_THEMES = R.raw.config;
  ///** Api url for getting a user profile: Remember to concatenate id + 'json' */
  // String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link ThemeEntity}.
   */
  Observable<List<ThemeEntity>> themeEntityList();

  ///**
  // * Retrieves an {@link Observable} which will emit a {@link ThemeEntity}.
  // *
  // * @param themeId The theme id used to get theme data.
  // */
  Observable<ThemeEntity> themeEntityById(final int themeId);
}
