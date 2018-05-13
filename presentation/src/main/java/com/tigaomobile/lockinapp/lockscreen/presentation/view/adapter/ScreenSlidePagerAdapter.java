package com.tigaomobile.lockinapp.lockscreen.presentation.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.Config;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment.BaseFragment;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment.LockscreenPageFragment;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.fragment.ScreenSlidePageFragment;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adi on 09/05/2018.
 * Description:
 */

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
  private ViewPager mPager;
  private Config config;
  private JSONArray webviews;

  public ScreenSlidePagerAdapter(FragmentManager fm, ViewPager mPager, Config config) {
    super(fm);
    this.mPager = mPager;
    this.config = config;
    webviews = config.getwebView();
  }

  /**
   * The number of pages (wizard steps) to show in this demo.
   */
  private int NUM_PAGES = 2;

  private final ScreenSlidePageFragment.NavListener eventHandler = new ScreenSlidePageFragment.NavListener() {
    @Override
    public void onPrev() {
      synchronized (this) {
        if (mPager.getCurrentItem() <= 0) {
          return;
        }
        mPager.setCurrentItem(mPager.getCurrentItem() - 1);
      }
    }
    @Override
    public void onNext() {

      synchronized (this) {
        if (mPager.getCurrentItem() >= NUM_PAGES - 1) {
          return;
        }
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
      }
    }
  };

  @Override
  public Fragment getItem(int position) {
    BaseFragment sspf;
    if(position==0) {
      sspf = new LockscreenPageFragment();
      sspf.setConfig(this.config);
    } else {
      sspf = new ScreenSlidePageFragment();
      sspf.setConfig(this.config);

      // Step 4 - Setup the listener for this object
      ((ScreenSlidePageFragment)sspf).setCustomObjectListener(eventHandler);

      Bundle args = new Bundle();
      // Our object is just an integer :-P
      try {
        args.putString("url", ((JSONObject)webviews.get(position-1)).getString("url"));
      } catch (JSONException e) {
        e.printStackTrace();
        sspf.getParentFragment().getActivity().finish();
      }

      sspf.setArguments(args);
    }

    return sspf;
  }

  @Override
  public int getCount() {
    return NUM_PAGES;
  }
}
