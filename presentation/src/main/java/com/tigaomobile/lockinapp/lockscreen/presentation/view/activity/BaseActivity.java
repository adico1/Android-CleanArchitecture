package com.tigaomobile.lockinapp.lockscreen.presentation.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.tigaomobile.lockinapp.lockscreen.presentation.AndroidApplication;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.HasComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.components.ApplicationComponent;
import com.tigaomobile.lockinapp.lockscreen.presentation.internal.di.modules.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  /**
   * Gets a component for dependency injection by its type.
   */
  @SuppressWarnings("unchecked")
  protected <C> C getComponent(Class<C> componentType) {
    return componentType.cast(((HasComponent<C>) this).getComponent());
  }

  public void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction =
        getSupportFragmentManager().beginTransaction();

    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }
}
