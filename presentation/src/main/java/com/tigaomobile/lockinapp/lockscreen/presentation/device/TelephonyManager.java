package com.tigaomobile.lockinapp.lockscreen.presentation.device;

import android.content.Context;
import android.telephony.PhoneStateListener;
import com.tigaomobile.lockinapp.lockscreen.presentation.view.TelephonyStateChangeHandler;
import javax.inject.Inject;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by adi on 11/05/2018.
 * Description:
 */
public class TelephonyManager {
  private android.telephony.TelephonyManager telephonyManager;
  TelephonyStateChangeHandler phoneStateListener;
  Context context;

  @Inject public TelephonyManager(Context context) {
    this.context = context;

    this.telephonyManager =
        (android.telephony.TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

    if(this.telephonyManager!=null) {
      this.telephonyManager.listen(phoneStateListener,
          PhoneStateListener.LISTEN_NONE);
    }
  }

  public void detachPhoneListener() {
    // listen the events get fired during the call

    // TODO: replace with null check and throw TelephonyManager not initialized exception
    // TODO: huh! is that detach - recheck!
    if(this.telephonyManager!=null) {
      this.telephonyManager.listen(phoneStateListener,
          PhoneStateListener.LISTEN_NONE);
    }
  }

  public void attachPhoneListener() {
    // listen the events get fired during the call
    phoneStateListener = new TelephonyStateChangeHandler(this.context);

    if(telephonyManager != null) {
      telephonyManager.listen(phoneStateListener,
          PhoneStateListener.LISTEN_CALL_STATE);
    }
  }

  public void destroy() {
    detachPhoneListener();
    // TODO: what is required to destroy this object - recheck!
  }
}
