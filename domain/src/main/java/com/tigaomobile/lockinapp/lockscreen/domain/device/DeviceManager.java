package com.tigaomobile.lockinapp.lockscreen.domain.device;

/**
 * Created by adi on 06/05/2018.
 * Description:
 */

/**
 * Interface that represents a Device for interacting with the device.
 */
public interface DeviceManager {
  /**
   * Hook the device
   * Initialize the device for lifetime service
   * Register to required device events.
   */
  void connectDevice();
  /**
   * Unhook from the device.
   */
  void disconnectDevice();

  /**
   * Disable device keyguard.
   */
  void disableDeviceKeyGuard();
}
