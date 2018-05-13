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
package com.tigaomobile.lockinapp.lockscreen.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;
import com.tigaomobile.lockinapp.lockscreen.domain.Theme;
import com.tigaomobile.lockinapp.lockscreen.domain.User;
import com.tigaomobile.lockinapp.lockscreen.domain.executor.PostExecutionThread;
import com.tigaomobile.lockinapp.lockscreen.domain.executor.ThreadExecutor;
import com.tigaomobile.lockinapp.lockscreen.domain.repository.ThemeRepository;
import com.tigaomobile.lockinapp.lockscreen.domain.repository.UserRepository;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Theme}.
 */
public class GetTheme extends UseCase<Theme, GetTheme.Params> {

  private final ThemeRepository themeRepository;

  @Inject GetTheme(ThemeRepository themeRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.themeRepository = themeRepository;
  }

  @Override Observable<Theme> buildUseCaseObservable(Params params) {
    Preconditions.checkNotNull(params);
    return this.themeRepository.theme(params.themeId);
  }

  public static final class Params {

    private final int themeId;

    private Params(int themeId) {
      this.themeId = themeId;
    }

    public static Params forTheme(int themeId) {
      return new Params(themeId);
    }
  }
}
