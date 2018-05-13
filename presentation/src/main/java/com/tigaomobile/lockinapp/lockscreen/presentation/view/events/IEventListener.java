package com.tigaomobile.lockinapp.lockscreen.presentation.view.events;


public interface IEventListener<T extends  IEventData> {
    void handleEvent(T data);
}
