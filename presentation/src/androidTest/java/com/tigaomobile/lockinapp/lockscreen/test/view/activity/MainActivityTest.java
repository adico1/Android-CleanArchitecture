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
package com.tigaomobile.lockinapp.lockscreen.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import com.tigaomobile.lockinapp.lockscreen.presentation.R;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.activity.MainActivity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

  private MainActivity mainActivity;

  public MainActivityTest() {
    super(MainActivity.class);
  }

  @Override protected void setUp() throws Exception {
    super.setUp();
    this.setActivityIntent(createTargetIntent());
    mainActivity = getActivity();
  }

  @Override protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testContainsUserListFragment() {
    Fragment userListFragment =
        mainActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
    assertThat(userListFragment, is(notNullValue()));
  }

  public void testContainsProperTitle() {
    String actualTitle = this.mainActivity.getTitle().toString().trim();

    assertThat(actualTitle, is("Users List"));
  }

  private Intent createTargetIntent() {
    Intent intentLaunchActivity =
        mainActivity.getCallingIntent(getInstrumentation().getTargetContext());

    return intentLaunchActivity;
  }

  // TODO: test is clock functional:
  //        - showing proper hour
  //        - show proper format
  //        - placed in the proper position
  //        - background is loaded properly
  //        - if no internet not showing webview
  //        - if internet alive show webview
  // TODO: test button group
  //       - unlock button test
  //         - check release before time does nothing
  //         - check release on time activate the function call
  //         - make sure activity is finished, application still alive
  //       - browser button test
  //       - facebook button test
  //       - ...
  // TODO: test online/offline mode

}
