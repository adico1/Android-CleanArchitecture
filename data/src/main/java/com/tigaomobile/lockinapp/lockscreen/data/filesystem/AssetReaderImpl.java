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

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tigaomobile.lockinapp.lockscreen.data.entity.ThemeEntity;
import com.tigaomobile.lockinapp.lockscreen.data.entity.mapper.ThemeEntityJsonMapper;
import com.tigaomobile.lockinapp.lockscreen.data.exception.FileSystemException;
import com.tigaomobile.lockinapp.lockscreen.data.exception.NetworkConnectionException;
import com.tigaomobile.lockinapp.lockscreen.data.exception.ThemeNotFoundException;
import io.reactivex.Observable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

/**
 * {@link AssetReader} implementation for retrieving data from the application asset folder.
 */
public class AssetReaderImpl implements AssetReader {

  private final Context context;
  private final ThemeEntityJsonMapper themeEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param themeEntityJsonMapper {@link ThemeEntityJsonMapper}.
   */
  public AssetReaderImpl(Context context, ThemeEntityJsonMapper themeEntityJsonMapper) {
    if (context == null || themeEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.themeEntityJsonMapper = themeEntityJsonMapper;
  }

  @Override public Observable<List<ThemeEntity>> themeEntityList() {
    return Observable.create(emitter -> {
      try {
        String responseThemeEntities = getThemeEntitiesFromRawAsset();
        if (responseThemeEntities != null) {
          emitter.onNext(themeEntityJsonMapper.transformThemeEntityCollection(responseThemeEntities));
          emitter.onComplete();
        } else {
          emitter.onError(new FileSystemException());
        }
      } catch (Exception e) {
        emitter.onError(new FileSystemException(e.getCause()));
      }
    });
  }

  @Override public Observable<ThemeEntity> themeEntityById(final int themeId) {
    return Observable.create(emitter -> {
        try {
          String responseThemeDetails = getThemeDetails(themeId);
          if (responseThemeDetails != null) {
            emitter.onNext(themeEntityJsonMapper.transformThemeEntity(responseThemeDetails));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
    });
  }

  private String loadJSONFromAsset(Application context) throws IOException {
    InputStream in = context.getResources().openRawResource(RAW_ASSET_GET_THEMES);
    return loadJSONFromAsset(in);
  }

  private String loadJSONFromAsset(Context context) throws IOException {
    InputStream in = context.getResources().openRawResource(RAW_ASSET_GET_THEMES);
    return loadJSONFromAsset(in);
  }

  private String loadJSONFromAsset(InputStream in) throws IOException {
      int count;
      byte[] bytes = new byte[32768];
      StringBuilder builder = new StringBuilder();
      while ( (count = in.read(bytes,0, 32768)) > 0) {
        builder.append(new String(bytes, 0, count));
      }

      in.close();
      return builder.toString();
  }

  private String getThemeEntitiesFromRawAsset() throws ThemeNotFoundException, IOException {
    return loadJSONFromAsset(this.context);
  }
  private String getThemeDetails(int themeId) {
    return "Not implemented";
  }
}
